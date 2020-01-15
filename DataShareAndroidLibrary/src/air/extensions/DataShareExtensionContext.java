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

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

public class DataShareExtensionContext extends FREContext {

	public DataShareExtensionContext() {

		Log.i("DataShareExtensionContext", "constructor");

	}

	// Cleans up resources associated with the extension context.
	@Override
	public void dispose() {
		Log.i("DataShareExtensionContext", "dispose");

	}

	// Maps the string value used in ActionScript to a native FREFunction
	// reference.
	@Override
	public Map<String, FREFunction> getFunctions() {

		Log.i("DataShareExtensionContext", "getFunctions");

		Map<String, FREFunction> functionMap = new HashMap<String, FREFunction>();
		functionMap.put("shareImageFunction", new ShareImageFunction());
		functionMap.put("shareTextFunction", new ShareTextFunction());
		functionMap.put("sendMessageFunction", new SendMessageFunction());
		functionMap.put("showToastFunction", new ShowToastFunction());
		return functionMap;
	}

}
