echo f | xcopy /f /y ..\share-lib-as3\bin\share.swc
"C:\Program Files\7-Zip\7z.exe" e share.swc -odefault library.swf -aoa
echo f | xcopy /f /y default\library.swf android\
echo f | xcopy /f /y default\library.swf ios\
echo f | xcopy /f /y ..\share-lib-android\app\build\intermediates\bundles\release\classes.jar android\
"C:\_projects\AIRSDK_Win_33.0.2.330\bin\adt.bat" -package -storetype pkcs12 -keystore cert.p12 -storepass 111111 -target ane ..\share-test\exts\com.gerantech.extensions.share.ane extension.xml -swc share.swc -platform Android-ARM -C android . -platform Android-ARM64 -C android . -platform Android-x86 -C android . -platform iPhone-ARM -C ios . -platform iPhone-x86 -C ios . -platform default -C default .