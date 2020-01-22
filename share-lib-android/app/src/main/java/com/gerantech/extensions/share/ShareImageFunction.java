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
			Bitmap bmp = EncodeImageFunction.getBitmap((FREBitmapData) args[0]);

			Log.i(ShareExtension.TAG, bmp.getWidth()+ " : " + bmp.getHeight());

			File cachePath = new File(activity.getApplicationContext().getCacheDir(), "images");
			if( !cachePath.exists())
				cachePath.mkdirs(); // don't forget to make the directory
			File image = new File(cachePath, "image.png");
			FileOutputStream stream = new FileOutputStream(image); // overwrites this image every time
			bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            stream.close();

			Uri uri = android.support.v4.content.FileProvider.getUriForFile(activity.getApplicationContext(), activity.getApplicationContext().getPackageName() + ".provider", image);
			Log.i(ShareExtension.TAG, uri.getPath() + " saved exists:" + image.exists());

			// Second Argument is for the user ids
			FREObject user = args[1];
			String userId = user.getAsString();
			ArrayList<String> idList = new ArrayList<>();
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