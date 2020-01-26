package com.gerantech.extensions.share.functions;

import android.app.Activity;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.gerantech.extensions.share.ShareExtension;
import com.gerantech.extensions.share.ShareExtensionContext;

class BaseFunction implements FREFunction, IShareResultCallback {
    Activity activity;
    private ShareExtensionContext context;

    @Override
    public FREObject call(FREContext context, FREObject[] args) {
        this.context = (ShareExtensionContext) context;
        this.activity = this.context.getActivity();
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.i(ShareExtension.TAG, "requestCode: " + requestCode + " resultCode: " + resultCode + " intent: " + intent);
        if (requestCode != SHARING_REQUEST_CODE)
    }
    }
    }
}