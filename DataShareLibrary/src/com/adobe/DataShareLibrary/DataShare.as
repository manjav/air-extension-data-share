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
package com.adobe.DataShareLibrary
{
	import com.adobe.images.PNGEncoder;
	
	import flash.display.BitmapData;
	import flash.external.ExtensionContext;
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	import flash.system.Capabilities;
	import flash.utils.ByteArray;

	public class DataShare
	{
		private static var extContext:ExtensionContext = null;
		public var isIOS:Boolean = (Capabilities.manufacturer.indexOf("iOS") != -1);


		public function DataShare()
		{
			// Create the instance of the ExtensionContext class if it has not been created yet.
			if (!extContext)
			{
			initExtension();
			}
		}
		private static function initExtension():void
		{
			// The extension context's context type  is NULL, because this extension
			// has only one context type.
			extContext = ExtensionContext.createExtensionContext("adobe.nativeExtension.dataShare", null);
			
		}
		
		public function sendMessage(message:String = "",phoneNumber:String = ""):void
		{
			if(isIOS)
			{
				extContext.call("shareTextFunction", phoneNumber,"",message);
			}
			else
				extContext.call("sendMessageFunction", phoneNumber,message);
		}
		public function sendData(subject:String="",text:String="",userId:String=""):void
		{
			extContext.call("shareTextFunction", userId,subject,text);
		}
		public function shareImage(bitmap:BitmapData,subject:String="",text:String="",userId:String=""):void
		{
			if(isIOS)
			{
				extContext.call("shareImageFunction",bitmap,userId,subject,text);
			}
			else
			{
				var pngBytes:ByteArray = PNGEncoder.encode(bitmap);
				var path:String="/sdcard/DataShareNativeExtImage.png";			
				var myFile:File = File.documentsDirectory.resolvePath(path);
				var fs:FileStream = new FileStream();
				fs.open(myFile, FileMode.WRITE);
				fs.writeBytes(pngBytes, 0, pngBytes.length);
				fs.close();
				extContext.call("shareImageFunction",path,userId,subject,text);
			}

		}
		
		public function showToast(message:String):void
		{
			extContext.call("showToastFunction",message);
		}
		
	}
}