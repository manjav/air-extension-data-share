/*********************************************************************************************************
 * ADOBE SYSTEMS INCORPORATED
 * Copyright 2015 Adobe Systems Incorporated
 * All Rights Reserved.
 *
 * NOTICE:  Adobe permits you to use, modify, and distribute this file in accordance with the
 * terms of the Adobe license agreement accompanying it.  If you have received this file from a
 * source other than Adobe, then your use, modification, or distribution of it requires the prior
 * written permission of Adobe.
 *
 *********************************************************************************************************/

package air.extensions;

import java.util.ArrayList;
import java.util.Collections;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

//The purpose of this class to share the Image and text data through the Applications installed on user device.

//The passedArgs array in call() contains arguments for user id, subject, text and Image Attachment.
public class ShareImageFunction implements FREFunction {

	Activity activity;

	@Override
	public FREObject call(FREContext context, FREObject[] passedArgs) {

		DataShareExtensionContext ExtContext = (DataShareExtensionContext) context;
		activity = ExtContext.getActivity();

		try {

			//First Argument is for the Image Path of sdcard
			FREObject path = passedArgs[0];
			String uriPath = path.getAsString();
			Uri uri = Uri.parse("file://" + uriPath);

			
			// Second Argument is for the user ids
			FREObject user = passedArgs[1];
			String userId = user.getAsString();
			ArrayList<String> idList = new ArrayList<String>();
			Collections.addAll(idList, userId.split(","));

			// Third Argument is for the Subject parameter
			FREObject sub = passedArgs[2];
			String subject = sub.getAsString();

			// Fourth Argument is for the Body text
			FREObject txt = passedArgs[3];
			String data = txt.getAsString();

			shareImage(idList, subject, data, uri);

		} catch (FRETypeMismatchException e) {
			Log.i("####ShareImageFunction####FRETypeMismatchException",
					e.getMessage());
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			Log.i("####ShareImageFunction####FREInvalidObjectException",
					e.getMessage());
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			Log.i("####ShareImageFunction####FREWrongThreadException",
					e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			Log.i("####ShareImageFunction####Exception", e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	// Function to launch intent to share image
	private void shareImage(ArrayList<String> id, String subject, String text,
			Uri uri) {

		Intent share = new Intent(Intent.ACTION_SEND);
		share.setType("image/*");
		String[] userIdList = id.toArray(new String[id.size()]);
		share.putExtra(Intent.EXTRA_STREAM, uri); // get image path from sdcard
		share.putExtra(Intent.EXTRA_EMAIL, userIdList); // get string array
														// contains user ids
		share.putExtra(Intent.EXTRA_SUBJECT, subject); // get subject
		share.putExtra(Intent.EXTRA_TEXT, text); // get text to show on mail
													// body

		activity.startActivity(Intent.createChooser(share, "Share Image!!!"));

	}

}
