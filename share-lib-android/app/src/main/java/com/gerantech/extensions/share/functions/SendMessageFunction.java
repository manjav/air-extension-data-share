package com.gerantech.extensions.share.functions;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.adobe.air.AndroidActivityWrapper;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREObject;
import com.gerantech.extensions.share.ShareExtension;

//The purpose of this class is to send messages to any contact number.

//The passedArgs array in call() contains two argument: Number and the Message to send.
public class SendMessageFunction extends BaseFunction {

	@Override
	public FREObject call(FREContext context, FREObject[] args) {
		super.call(context, args);

		try {
			// First Argument is for the number
			String phoneNumber = args[0].getAsString();

			// Second Argument is for message
			String message = args[1].getAsString();

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
