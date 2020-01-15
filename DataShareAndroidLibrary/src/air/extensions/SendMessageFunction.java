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

//The purpose of this class is to send messages to any contact number.

//The passedArgs array in call() contains two argument: Number and the Message to send.
public class SendMessageFunction implements FREFunction {

	Activity activity;

	@Override
	public FREObject call(FREContext context, FREObject[] passedArgs) {

		DataShareExtensionContext ExtContext = (DataShareExtensionContext) context;
		activity = ExtContext.getActivity();

		try {

			// First Argument is for the number
			FREObject numberObject = passedArgs[0];
			String phoneNumber = numberObject.getAsString();

			// Second Argument is for message
			FREObject msgObject = passedArgs[1];
			String message = msgObject.getAsString();

			sendMessage(phoneNumber, message);

		} catch (FRETypeMismatchException e) {
			Log.i("####SendMessageFunction####FRETypeMismatchException",
					e.getMessage());
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			Log.i("####SendMessageFunction####FREInvalidObjectException",
					e.getMessage());
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			Log.i("####SendMessageFunction####FREWrongThreadException",
					e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			Log.i("####SendMessageFunction####Exception", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	// Function to launch intent to send a message
	private void sendMessage(String number, String msg) {

		Intent msgIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:"
				+ number));
		msgIntent.putExtra("sms_body", msg);
		msgIntent.putExtra(Intent.EXTRA_TEXT, msg);
		activity.startActivity(msgIntent);
	}

}
