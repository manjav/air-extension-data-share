package com.gerantech.extensions.share;

import java.util.HashMap;
import java.util.Map;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;

public class ShareExtensionContext extends FREContext {

	public ShareExtensionContext() {
		Log.i(ShareExtension.TAG, "context constructor");
	}

	// Cleans up resources associated with the extension context.
	@Override
	public void dispose() {
		Log.i(ShareExtension.TAG, "context dispose");
	}

	// Maps the string value used in ActionScript to a native FREFunction
	// reference.
	@Override
	public Map<String, FREFunction> getFunctions() {
		Log.i(ShareExtension.TAG, "context getFunctions");

		Map<String, FREFunction> functionMap = new HashMap<String, FREFunction>();
		functionMap.put("showToastFunction", new ShowToastFunction());
		functionMap.put("sendMessageFunction", new SendMessageFunction());
		functionMap.put("shareTextFunction", new ShareTextFunction());
		functionMap.put("shareImageFunction", new ShareImageFunction());
		return functionMap;
	}
}