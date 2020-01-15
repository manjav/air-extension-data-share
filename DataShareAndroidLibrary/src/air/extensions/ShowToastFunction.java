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
import android.util.Log;
import android.widget.Toast;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREInvalidObjectException;
import com.adobe.fre.FREObject;
import com.adobe.fre.FRETypeMismatchException;
import com.adobe.fre.FREWrongThreadException;

//The purpose of this class to show message toast on Android device screen.

//The passedArgs array in call() contains argument for the string to show on Toast.
public class ShowToastFunction implements FREFunction {

	Activity activity;

	@Override
	public FREObject call(FREContext context, FREObject[] passedArgs) {
		DataShareExtensionContext ExtContext = (DataShareExtensionContext) context;
		activity = ExtContext.getActivity();
		FREObject toast = passedArgs[0];
		try {
			String toastMsg = toast.getAsString();

			// show message to user device screen
			Toast.makeText(activity.getApplicationContext(), toastMsg,
					Toast.LENGTH_LONG).show();

		} catch (IllegalStateException e) {
			Log.i("####ShowToastFunction####IllegalStateException",
					e.getMessage());
			e.printStackTrace();
		} catch (FRETypeMismatchException e) {
			Log.i("####ShowToastFunction####FRETypeMismatchException",
					e.getMessage());
			e.printStackTrace();
		} catch (FREInvalidObjectException e) {
			Log.i("####ShowToastFunction####FREInvalidObjectException",
					e.getMessage());
			e.printStackTrace();
		} catch (FREWrongThreadException e) {
			Log.i("####ShowToastFunction####FREWrongThreadException",
					e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			Log.i("####ShowToastFunction####Exception", e.getMessage());
			e.printStackTrace();
		}

		return null;

	}

}
