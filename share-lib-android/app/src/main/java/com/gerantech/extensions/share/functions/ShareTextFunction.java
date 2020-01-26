package com.gerantech.extensions.share.functions;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREObject;
import com.gerantech.extensions.share.ShareExtension;

import java.util.ArrayList;
import java.util.Collections;

//The purpose of this class to share text data through the Apps installed on user device.

//The args array in call() contains arguments for userids, subject, text.
public class ShareTextFunction extends BaseFunction {
	@Override
	public FREObject call(FREContext context, FREObject[] args) {
		super.call(context, args);
		try {
			// First Argument is for the user ids
			String userId = args[0].getAsString();
			ArrayList<String> idList = new ArrayList<String>();
			Collections.addAll(idList, userId.split(","));

			// Second Argument is for the Subject parameter
			String subject = args[1].getAsString();

			// Third Argument is for the Body text
			String data = args[2].getAsString();

		} catch (Exception e) {
			Log.i(ShareExtension.TAG, e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	// Function to launch intent to Share text data
	private void shareText(ArrayList<String> id, String subject, String text) {

		Intent share = new Intent(android.content.Intent.ACTION_SEND);
		share.setType("text/plain");
		String[] userIdList = id.toArray(new String[id.size()]);
		share.putExtra(Intent.EXTRA_EMAIL, userIdList); // get string array
														// contains user ids
		share.putExtra(Intent.EXTRA_SUBJECT, subject); // get subject
		share.putExtra(Intent.EXTRA_TEXT, text); // get text to show on mail
													// body
		activity.startActivity(Intent.createChooser(share, "Share Message!!!"));
	}

}
