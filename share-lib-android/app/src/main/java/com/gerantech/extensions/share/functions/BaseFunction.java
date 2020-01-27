package com.gerantech.extensions.share.functions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.adobe.air.AndroidActivityWrapper;
import com.adobe.air.IShareResultCallback;
import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.gerantech.extensions.share.ShareExtension;
import com.gerantech.extensions.share.ShareExtensionContext;

import java.util.ArrayList;

class BaseFunction implements FREFunction, IShareResultCallback {
    Activity activity;
    private ShareExtensionContext context;
    static final int SHARING_REQUEST_CODE = 1361;

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
            return;

         AndroidActivityWrapper.GetAndroidActivityWrapper().removeActivityResultListener(this);
        // Dispatch event, it may be 'cancel' even if the operation succeeded
        String eventName = (resultCode == Activity.RESULT_OK) ? "complete" : "cancel";
        context.dispatchStatusEventAsync(eventName, "");
    }

    // Function to launch intent to Share data
    void share(ArrayList<String> id, String subject, String text, Uri uri, String servicePackage) {
        try {
            Intent share = new Intent(Intent.ACTION_SEND);
            // select shring service
            if( servicePackage != null )
                share.setPackage(servicePackage);
            // contains user ids
            share.putExtra(Intent.EXTRA_EMAIL, id.toArray(new String[id.size()])); // get string array
            share.putExtra(Intent.EXTRA_SUBJECT, subject); // get subject
            share.putExtra(Intent.EXTRA_TEXT, text); // get text to show on mail
            // body
            if( uri == null ){
                share.setType("text/plain");
            } else {
                share.setType("image/*");
                share.putExtra(Intent.EXTRA_STREAM, uri); // get image path from sdcard
            }
            AndroidActivityWrapper.GetAndroidActivityWrapper().addActivityResultListener(this);
            activity.startActivityForResult(Intent.createChooser(share, subject), SHARING_REQUEST_CODE);
            context.dispatchStatusEventAsync("init", "");
        } catch (Exception e) {
//            Log.i(ShareExtension.TAG, e.getLocalizedMessage());
            e.printStackTrace();
            context.dispatchStatusEventAsync("close", e.getLocalizedMessage());
        }
    }
}