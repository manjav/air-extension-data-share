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

			// Fifth Argument is service package
			String servicePackage = args[3].getAsString();

			share(idList, subject, data, null, servicePackage);
		} catch (Exception e) {
			Log.i(ShareExtension.TAG, e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
