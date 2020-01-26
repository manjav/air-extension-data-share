package com.gerantech.extensions.share.functions;

import android.app.Activity;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.gerantech.extensions.share.ShareExtension;
import com.gerantech.extensions.share.ShareExtensionContext;

class BaseFunction implements FREFunction {
    Activity activity;
    private ShareExtensionContext context;

    @Override
    public FREObject call(FREContext context, FREObject[] args) {
        this.context = (ShareExtensionContext) context;
        this.activity = this.context.getActivity();
        return null;
    }
}