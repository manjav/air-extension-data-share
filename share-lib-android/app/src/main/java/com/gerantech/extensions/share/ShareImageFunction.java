package com.gerantech.extensions.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.adobe.fre.FREBitmapData;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

//The purpose of this class to share the Image and text data through the Applications installed on user device.
//The passedArgs array in call() contains arguments for user id, subject, text and Image Attachment.
public class ShareImageFunction implements FREFunction {

	Activity activity;

  @Override
	public FREObject call(FREContext context, FREObject[] args) {

		ShareExtensionContext ExtContext = (ShareExtensionContext) context;
		activity = ExtContext.getActivity();

		try {
			FREBitmapData bitmapData= (FREBitmapData) args[0];

			bitmapData.acquire();
			Log.i(ShareExtension.TAG, bitmapData.getWidth()+ " " + bitmapData.getHeight() + " " + bitmapData.getBits());

			// create java bitmap from as3 bitmapData
			Bitmap bmp = Bitmap.createBitmap(bitmapData.getWidth(), bitmapData.getHeight(), Bitmap.Config.ARGB_8888);
			bmp.copyPixelsFromBuffer(bitmapData.getBits());
			bitmapData.release();

			Log.i(ShareExtension.TAG, bmp.getWidth()+ " : " + bmp.getHeight());

			File cachePath = new File(activity.getApplicationContext().getCacheDir(), "images");
			if( !cachePath.exists())
				cachePath.mkdirs(); // don't forget to make the directory
			FileOutputStream stream = new FileOutputStream(cachePath + "/image.png"); // overwrites this image every time
			bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
//            stream.flush();
            stream.close();

			File imagePath = new File(activity.getApplicationContext().getCacheDir(), "images");
			Uri uri = android.support.v4.content.FileProvider.getUriForFile(activity.getApplicationContext(), activity.getApplicationContext().getPackageName() + ".provider", new File(imagePath, "image.png"));
			Log.i(ShareExtension.TAG, uri.getPath() + " saved " + imagePath.exists());

			// Second Argument is for the user ids
			FREObject user = args[1];
			String userId = user.getAsString();
			ArrayList<String> idList = new ArrayList<String>();
			Collections.addAll(idList, userId.split(","));

			// Third Argument is for the Subject parameter
			String subject = args[2].getAsString();

			// Fourth Argument is for the Body text
			String data = args[3].getAsString();

			shareImage(idList, subject, data, uri);
		} catch (Exception e) {
			Log.i(ShareExtension.TAG, e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	// Function to launch intent to share image
	private void shareImage(ArrayList<String> id, String subject, String text, Uri uri) {

		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("image/*");
		String[] userIdList = id.toArray(new String[id.size()]);
		share.putExtra(Intent.EXTRA_STREAM, uri); // get image path from sdcard
		share.putExtra(Intent.EXTRA_EMAIL, userIdList); // get string array
		
		share.putExtra(Intent.EXTRA_SUBJECT, subject); // get subject
		share.putExtra(Intent.EXTRA_TEXT, text); // get text to show on mail

		activity.startActivity(Intent.createChooser(share, "Share Image!!!"));
	}
}