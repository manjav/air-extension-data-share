# air-extension-share
The Share class is a native extension for Adobe AIR. It allows AIR application developers, from ActionScript, to use some Data Sharing features of Android and iOS.

This version updated for Arm64 and x86 devices with write-external-storage request permission for Android 6.0 and above.
Read more for concept:
https://www.adobe.com/devnet/air/articles/datashare-native-extension.html

## Getting started

Download the ANE from the [releases](../../releases/) page and add it to your app's descriptor:

```xml
<extensions>
    <extensionID>com.gerantech.extensions.share</extensionID>
</extensions>
```

If you are targeting Android, add the Android Support extension from [this repository](https://github.com/marpies/android-dependency-anes/releases) as well (unless you know it is included by some other extension):

```xml
<extensions>
    <extensionID>com.gerantech.extensions.dependencies</extensionID>
</extensions>
```


## API Overview

### Show Toast
Show toast with text and duration params
duration 0 is short, 1 is medium and 2 is long 

```as3
Share.instance.showToast("Show Message...", 1);
```

### Send SMS
Send SMS in default native sending app on your device.

```as3
Share.instance.sendMessage("Heyy, How are you?", "+989121778856");
```

### Share Text
Your text can be contains url and etc. idadadadadadadIdaf you choice email organization apps, receivers field auto filled.

```as3
var emails:String = "mansurjavadi@gmail.com, fudosakurai@gmail.com";
Share.instance.sendText("Your Feedback is Valuable to us", "Write something here..", emails);
```

### Share Image
If you choice email organization apps, your image attached.

```as3
var emails:String = "mansurjavadi@gmail.com, fudosakurai@gmail.com";
Share.instance.shareImage(this.bitmapData, "Sharing some doc", "Please find the attachment", emails);
```
Furthermore, modify `manifestAdditions` element so that it contains the following `provider` element:

```xml
<android>
    <manifestAdditions>
        <![CDATA[
        <manifest android:installLocation="auto">

            <application>

                <provider
                    android:name="android.support.v4.content.FileProvider"
                    android:authorities="{APP_PACKAGE_NAME}.fileprovider"
                    android:grantUriPermissions="true"
                    android:exported="false">
                    <meta-data
                        android:name="android.support.FILE_PROVIDER_PATHS"
                        android:resource="@xml/file_paths" />
                </provider>

            </application>

        </manifest>
        ]]>
    </manifestAdditions>
</android>
```

Make sure to replace the `{APP_PACKAGE_NAME}` token with your application id (value of the `id` element in your AIR app descriptor). Remember the id is prefixed with `air.` by default (you can remove `air.` in air sdk 33).  

Add the following key-value pairs to your `InfoAdditions` to avoid crashes on iOS 10+ when saving an image to photos library or assigning it to a contact:

```xml
<iPhone>
    <InfoAdditions><![CDATA[

        <key>NSPhotoLibraryUsageDescription</key>
        <string>Access to photo library is required to save images.</string>

        <key>NSContactsUsageDescription</key>
        <string>Access to contacts is required to assign images.</string>

        <key>NSPhotoLibraryAddUsageDescription</key>
        <string>Access to photo library is required to save images.</string>

    ]]></InfoAdditions>
</iPhone>
```
