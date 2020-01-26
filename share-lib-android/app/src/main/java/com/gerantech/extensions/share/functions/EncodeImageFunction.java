package com.gerantech.extensions.share.functions;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.Log;

import com.adobe.fre.FREBitmapData;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREObject;
import com.gerantech.extensions.share.ShareExtension;

import java.io.FileOutputStream;

//The purpose of this class to share the Image and text data through the Applications installed on user device.
//The args array in call() contains arguments for user id, subject, text and Image Attachment.
public class EncodeImageFunction extends BaseFunction {

  @Override
  public FREObject call(FREContext context, FREObject[] args) {
	super.call(context, args);
	try {
		// create bitmap from bitmapData
		Bitmap bmp = EncodeImageFunction.getBitmap((FREBitmapData) args[0]);
		Log.i(ShareExtension.TAG, bmp.getWidth()+ " : " + bmp.getHeight());

		// define image format
		String path = args[1].getAsString();
		int quality = (int) Math.floor(args[2].getAsDouble() * 100);
		Bitmap.CompressFormat format = Bitmap.CompressFormat.PNG;
		int dotIndex = path.lastIndexOf('.');
		if( dotIndex > -1) {
		  String ext = path.substring(dotIndex + 1).toLowerCase();
		  if (ext.equals("jpg") || ext.equals("jpeg"))
			format = Bitmap.CompressFormat.JPEG;
		  else if (ext.equals("webp"))
			format = Bitmap.CompressFormat.JPEG;
		}


		// encode and save in target directory
		FileOutputStream stream = new FileOutputStream(path); // overwrites this image every time
		bmp.compress(format, quality, stream);
		stream.close();

		Log.i(ShareExtension.TAG, "image " + path + " saved with format: " + format + " and quality: " + quality);
	} catch (Exception e) {
		Log.i(ShareExtension.TAG, e.getMessage());
		e.printStackTrace();
	}

	return null;
	}

	private static final float[] mBGRToRGBColorTransform =
	{
		0, 0, 1f, 0, 0,
		0, 1f, 0, 0, 0,
		1f, 0, 0, 0, 0,
		0, 0, 0, 1f, 0
	};
	private static final ColorMatrixColorFilter mColorFilter = new ColorMatrixColorFilter(
			new ColorMatrix( mBGRToRGBColorTransform )
	);

	static Bitmap getBitmap(FREBitmapData bitmapData)
    {
	  Bitmap bitmap = null;
	  try {
		  bitmapData.acquire();
		  bitmap = Bitmap.createBitmap(bitmapData.getWidth(), bitmapData.getHeight(), Bitmap.Config.ARGB_8888);

		  Canvas canvas = new Canvas( bitmap );
		  Paint paint = new Paint();
		  paint.setColorFilter( mColorFilter );
		  bitmap.copyPixelsFromBuffer( bitmapData.getBits() );
		  bitmapData.release();
		  canvas.drawBitmap( bitmap, 0, 0, paint );

	  } catch (Exception e) {
		  e.printStackTrace();
	  }
	  return bitmap;
    }
}