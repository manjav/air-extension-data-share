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
package com.gerantech.extensions.share;

import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREExtension;

/*
 * Initialization and finalization class of native extension.
 */
public class ShareExtension implements FREExtension {

	static final String TAG = "share-ane";
	// createContext() creates and returns an FREContext object.
	public FREContext createContext(String extId) {
		Log.i(TAG, "createContext");
		return new ShareExtensionContext();
	}

	// initialize() performs initializations for the extension.
	public void initialize() {
		Log.i(TAG, "initialize");
	}

	// dispose() cleans up extension resources.
	@Override
	public void dispose() {
		Log.i(TAG, "dispose");
	}

}
