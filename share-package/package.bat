echo f | xcopy /f /y ..\share-lib-android\app\build\intermediates\bundles\release\classes.jar android\
"adt.bat" -package -storetype pkcs12 -keystore cert.p12 -storepass 111111 -target ane "com.gerantech.extensions.share.ane" extension.xml -swc DataShareLibrary.swc -platform Android-ARM -C android . -platform Android-ARM64 -C android . -platform Android-x86 -C android . -platform iPhone-ARM -C ios . -platform iPhone-x86 -C ios . -platform default -C default . && echo f | xcopy /f /y com.gerantech.extensions.share.ane ..\share-test\exts\