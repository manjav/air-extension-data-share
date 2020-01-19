package com.gerantech.extensions.share;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;

//The purpose of this class to show message toast on Android device screen.

//The passedArgs array in call() contains argument for the string to show on Toast.
public class ShowToastFunction implements FREFunction {

	@Override
	public FREObject call(FREContext context, FREObject[] args) {
		ShareExtensionContext ExtContext = (ShareExtensionContext) context;
		Activity activity = ExtContext.getActivity();
		try {
			// show message to user device screen
			Toast.makeText(activity.getApplicationContext(), args[0].getAsString(), args[1].getAsInt()).show();
		} catch (Exception e) {
			Log.i(ShareExtension.TAG, e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}
