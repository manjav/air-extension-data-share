package com.gerantech.extensions.share;

import android.graphics.Bitmap;
import android.util.Log;

import com.adobe.fre.FREBitmapData;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;

//The purpose of this class to share the Image and text data through the Applications installed on user device.
//The passedArgs array in call() contains arguments for user id, subject, text and Image Attachment.
public class EncodeImageFunction implements FREFunction {

  @Override
  public FREObject call(FREContext context, FREObject[] args) {

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
		stream.flush();
		stream.close();

		Log.i(ShareExtension.TAG, "image " + path + " saved with format: " + format + " and quality: " + quality);
	} catch (Exception e) {
		Log.i(ShareExtension.TAG, e.getMessage());
		e.printStackTrace();
	}

	  return null;
  }

  static Bitmap getBitmap(FREBitmapData bitmapData)
  {
	  Bitmap bmp = null;
	  try {
		  bitmapData.acquire();

		  // convert ARGB to ARGB
		  int len = bitmapData.getWidth() * bitmapData.getHeight();
		  ByteBuffer buffer = bitmapData.getBits();
		  ByteBuffer newBuffer = ByteBuffer.allocate(len * 4);
		  for (int i = 0; i < len; i++) {
			  byte r = buffer.get();
			  byte g = buffer.get();
			  byte b = buffer.get();
			  newBuffer.put(buffer.get());
			  newBuffer.put(b);
			  newBuffer.put(g);
			  newBuffer.put(r);
		  }
		  newBuffer.rewind();

		  // create java bitmap from as3 bitmapData
		  bmp = Bitmap.createBitmap(bitmapData.getWidth(), bitmapData.getHeight(), Bitmap.Config.ARGB_8888);
		  bmp.copyPixelsFromBuffer(newBuffer);
		  bitmapData.release();
			
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
	  return bmp;
  }

}