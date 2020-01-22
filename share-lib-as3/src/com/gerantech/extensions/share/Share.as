package com.gerantech.extensions.share
{
	import flash.display.BitmapData;
	import flash.external.ExtensionContext;
	import flash.system.Capabilities;
	import flash.filesystem.File;
	import flash.filesystem.FileStream;
	import flash.filesystem.FileMode;
	import flash.utils.ByteArray;

	public class Share
	{
		static private var _instance:Share;
		static private var extContext:ExtensionContext = null;
		static public function get instance ():Share
		{
			if( _instance == null )
				_instance = new Share();
			return _instance;
		}
		public var isAndroid:Boolean =	Capabilities.version.substr(0, 3).toUpperCase() == "AND";
		public var isIOS:Boolean =			Capabilities.version.substr(0, 3).toUpperCase() == "IPH";

		public function Share()
		{
			// Create the instance of the ExtensionContext class if it has not been created yet.
			// The extension context's context type is NULL, because this extension
			// has only one context type.
			extContext = ExtensionContext.createExtensionContext("com.gerantech.extensions.share", null);
		}

		public function showToast(message:String, duration:int = 2):void
		{
			extContext.call("showToastFunction", message, duration);
		}

		public function sendMessage(message:String = "", phoneNumber:String = ""):void
		{
			if(isIOS)
				extContext.call("shareTextFunction", phoneNumber, "", message);
			else
				extContext.call("sendMessageFunction", phoneNumber, message);
		}

		public function sendText(subject:String = "", text:String = "", userId:String = ""):void
		{
			extContext.call("shareTextFunction", userId, subject, text);
		}

		public function shareImage(bitmap:BitmapData, subject:String = "", text:String = "", userId:String = ""):void
		{
			extContext.call("shareImageFunction", bitmap, userId, subject, text);
		}
		
		public function encode(bitmapData:BitmapData, url:String, compression:Number = 1):void
		{
			if( isAndroid ) {
				extContext.call("encodeImageFunction", bitmapData, url, compression);
				return;
			}
			
			var bytes:ByteArray = PNGEncoder2.encode(bitmapData);
			var file:File = new	File(url);
			var fileStream:FileStream = new FileStream();
			fileStream.open(file, FileMode.WRITE);
			fileStream.writeBytes(bytes, 0, bytes.length);
			fileStream.close();
		}
	}
}