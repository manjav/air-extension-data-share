package com.gerantech.extensions.share.functions;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.gerantech.extensions.share.ShareExtension;
import com.gerantech.extensions.share.ShareExtensionContext;

import java.util.ArrayList;

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

    // Function to launch intent to Share data
    void share(ArrayList<String> id, String subject, String text, Uri uri) {
        try {
            Intent share = new Intent(Intent.ACTION_SEND);
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
            activity.startActivity(Intent.createChooser(share, subject));
        } catch (Exception e) {
//            Log.i(ShareExtension.TAG, e.getLocalizedMessage());
            e.printStackTrace();
    }
    }
}