#import "FlashRuntimeExtensions.h"
@import CoreGraphics;
@import UIKit;

#define SYSTEM_VERSION_LESS_THAN(v)    ([[[UIDevice currentDevice] systemVersion] compare:v options:NSNumericSearch] == NSOrderedAscending)

// To share the Image and text data through the Applications installed on user device.
FREObject shareImage(FREContext ctx, void* funcData, uint32_t argc, FREObject* argv) {
    
    FREObject       BitmapDataObject = argv[ 0 ];
    FREBitmapData2  bitmap;
    
    uint32_t subjectLength;
    const uint8_t *subject;
    FREGetObjectAsUTF8(argv[2], &subjectLength, &subject);
    NSString *subjectText = [NSString stringWithFormat:@"%s", subject];
    
    uint32_t bodyLength;
    const uint8_t *body;
    FREGetObjectAsUTF8(argv[3], &bodyLength, &body);
    NSString *bodyText = [NSString stringWithFormat:@"%s", body];
    
    FREAcquireBitmapData( BitmapDataObject, &bitmap );
    
    
    int width       = bitmap.width;
    int height      = bitmap.height;
    
    // create CGDataProvider
    CGDataProviderRef CGprovider = CGDataProviderCreateWithData(NULL, bitmap.bits32, (width * height * 4), NULL);
    
    int                     bitsPerComponent    = 8;
    int                     bitsPerPixel        = 32;
    int                     bytesPerRow         = 4 * width;
    CGColorSpaceRef         colorSpaceRef       = CGColorSpaceCreateDeviceRGB();
    CGBitmapInfo            CGbitmap        = kCGBitmapByteOrder32Little | kCGImageAlphaPremultipliedFirst;
    
    CGColorRenderingIntent  Intent     = kCGRenderingIntentDefault;
    CGImageRef              imageRef            = CGImageCreate(width, height, bitsPerComponent, bitsPerPixel, bytesPerRow, colorSpaceRef, CGbitmap, CGprovider, NULL, "NO", Intent);
    
    // make UIImage from CGImage
    UIImage *myImage = [UIImage imageWithCGImage:imageRef];
    //Create Image in PNG format
    NSData* pngData  = UIImagePNGRepresentation( myImage);
    UIActivityViewController *activityViewController = [[UIActivityViewController alloc] initWithActivityItems:@[bodyText, pngData] applicationActivities:nil];
    [activityViewController setValue:subjectText forKey:@"subject"];
    
    //if iPhone
    if (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPhone) {
        [[[[UIApplication sharedApplication] keyWindow] rootViewController] presentViewController:activityViewController animated:YES completion:nil];
    }
    //if iPad
    else if(UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad){
        // check iPad Version
        if (SYSTEM_VERSION_LESS_THAN(@"8.0")) {
            [[[[UIApplication sharedApplication] keyWindow] rootViewController] presentViewController:activityViewController animated:YES completion:nil];
        } else {
            UIPopoverController *popup = [[UIPopoverController alloc] initWithContentViewController:activityViewController];
            [popup presentPopoverFromRect:CGRectMake([[[UIApplication sharedApplication] keyWindow] rootViewController].view.frame.size.width, [[[UIApplication sharedApplication] keyWindow] rootViewController].view.frame.size.height, 0, 0)inView:[[[UIApplication sharedApplication] keyWindow] rootViewController].view permittedArrowDirections:UIPopoverArrowDirectionAny animated:YES];
        }
    }
    FREReleaseBitmapData(BitmapDataObject);
    return NULL;
}

//The purpose of this function to share text data through the Apps installed on user device.
FREObject shareText(FREContext ctx, void* funcData, uint32_t argc, FREObject* argv) {
       
    uint32_t subjectLength;
    const uint8_t *subject;
    FREGetObjectAsUTF8(argv[1], &subjectLength, &subject);
    NSString *subjectText = [NSString stringWithFormat:@"%s", subject];
    
    uint32_t bodyLength;
    const uint8_t *body;
    FREGetObjectAsUTF8(argv[2], &bodyLength, &body);
    NSString *bodyText = [NSString stringWithFormat:@"%s", body];
    
    UIActivityViewController *activityViewController = [[UIActivityViewController alloc] initWithActivityItems:@[bodyText] applicationActivities:nil];
    [activityViewController setValue:subjectText forKey:@"subject"];
    
    //if iPhone
    if (UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPhone) {
        [[[[UIApplication sharedApplication] keyWindow] rootViewController] presentViewController:activityViewController animated:YES completion:nil];
    }
    // if iPad
    else if(UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad) {
    // check iPad Version
        if (SYSTEM_VERSION_LESS_THAN(@"8.0")) {
            [[[[UIApplication sharedApplication] keyWindow] rootViewController] presentViewController:activityViewController animated:YES completion:nil];
        } else {
            UIPopoverController *popup = [[UIPopoverController alloc] initWithContentViewController:activityViewController];
            [popup presentPopoverFromRect:CGRectMake([[[UIApplication sharedApplication] keyWindow] rootViewController].view.frame.size.width, [[[UIApplication sharedApplication] keyWindow] rootViewController].view.frame.size.height, 0, 0)inView:[[[UIApplication sharedApplication] keyWindow] rootViewController].view permittedArrowDirections:UIPopoverArrowDirectionAny animated:YES];
        }
    }
    return NULL;
}

//The purpose of this function is to show the Toast Message on user device screen.
FREObject showToast(FREContext ctx, void* funcData, uint32_t argc,FREObject* argv) {
        
    uint32_t toastLength;
    const uint8_t *toast;
    FREGetObjectAsUTF8(argv[0], &toastLength, &toast);
    NSString *toastText = [NSString stringWithFormat:@"%s", toast];
    
    UIWindow * keyWindow = [[UIApplication sharedApplication] keyWindow];
    UILabel *toastView = [[UILabel alloc] init];
    toastView.text = toastText;
    toastView.textAlignment = NSTextAlignmentCenter;
    toastView.frame = CGRectMake(0.0, 0.0, keyWindow.frame.size.width, 100.0);
    toastView.layer.cornerRadius = 10;
    toastView.layer.masksToBounds = YES;
    toastView.center = keyWindow.center;
    
    [keyWindow addSubview:toastView];
    
    [UIView animateWithDuration: 6.0f
                          delay: 0.0
                        options: UIViewAnimationOptionCurveEaseOut
                     animations: ^{
                         toastView.alpha = 0.0;
                     }
                     completion: ^(BOOL finished) {
                         [toastView removeFromSuperview];
                     }];
    
    return NULL;
}




// ContextInitializer()
//
// The context initializer is called when the runtime creates the extension context instance.

void ShareContextInitializer(void* extData, const uint8_t* ctxType, FREContext ctx, uint32_t* numFunctionsToTest, const FRENamedFunction** functionsToSet) {
    
    *numFunctionsToTest = 3;
    
    FRENamedFunction* func = (FRENamedFunction*) malloc(sizeof(FRENamedFunction) * (*numFunctionsToTest));
    
    func[0].name = (const uint8_t*) "shareImageFunction";
    func[0].functionData = NULL;
    func[0].function = &shareImage;
    
    func[1].name = (const uint8_t*) "shareTextFunction";
    func[1].functionData = NULL;
    func[1].function = &shareText;
    
    func[2].name = (const uint8_t*) "showToastFunction";
    func[2].functionData = NULL;
    func[2].function = &showToast;
    
    *functionsToSet = func;
}



// ContextFinalizer()
//
// The context finalizer is called when the extension's ActionScript code
// calls the ExtensionContext instance's dispose() method.
// If the AIR runtime garbage collector disposes of the ExtensionContext instance, the runtime also calls
// ContextFinalizer().

void ShareContextFinalizer(FREContext ctx) {
    // Nothing to clean up.
    return;
}



// ExtInitializer()
//
// The extension initializer is called the first time the ActionScript side of the extension
// calls ExtensionContext.createExtensionContext() for any context.

void ShareExtInitializer(void** extDataToSet, FREContextInitializer* ctxInitializerToSet, FREContextFinalizer* ctxFinalizerToSet) {
    *extDataToSet = NULL;
    *ctxInitializerToSet = &ShareContextInitializer;
    *ctxFinalizerToSet = &ShareContextFinalizer;
}


// ExtFinalizer()
//
// The extension finalizer is called when the runtime unloads the extension. However, it is not always called.

void ShareExtFinalizer(void* extData) {
    // Nothing to clean up.
    return;
}

