package com.gerantech.extensions.share;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

//The purpose of this class is to send messages to any contact number.

//The passedArgs array in call() contains two argument: Number and the Message to send.
public class SendMessageFunction implements FREFunction {

	Activity activity;

	@Override
	public FREObject call(FREContext context, FREObject[] passedArgs) {

		ShareExtensionContext ExtContext = (ShareExtensionContext) context;
		activity = ExtContext.getActivity();

		try {
			// First Argument is for the number
			FREObject numberObject = passedArgs[0];
			String phoneNumber = numberObject.getAsString();

			// Second Argument is for message
			FREObject msgObject = passedArgs[1];
			String message = msgObject.getAsString();

			sendMessage(phoneNumber, message);
		} catch (Exception e) {
			Log.i(ShareExtension.TAG, e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	// Function to launch intent to send a message
	private void sendMessage(String number, String msg) {
		Intent msgIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + number));
		msgIntent.putExtra("sms_body", msg);
		msgIntent.putExtra(Intent.EXTRA_TEXT, msg);
		activity.startActivity(msgIntent);
	}
}
