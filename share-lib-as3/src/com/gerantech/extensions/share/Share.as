package com.gerantech.extensions.share
{
	import flash.display.BitmapData;
	import flash.external.ExtensionContext;
	import flash.system.Capabilities;
	import flash.filesystem.File;
	import flash.filesystem.FileStream;
	import flash.filesystem.FileMode;
	import flash.utils.ByteArray;
	import flash.events.EventDispatcher;
	import flash.events.StatusEvent;
	import flash.events.Event;

	public class Share extends EventDispatcher
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
			extContext.addEventListener(StatusEvent.STATUS, context_statusHAndler);
		}

		public function showToast(message:String, duration:int = 2):void
		{
			if( Capabilities.manufacturer == "Adobe Windows" )
				return;
			extContext.call("showToastFunction", message, duration);
		}

		public function sendMessage(message:String = "", phoneNumber:String = ""):void
		{
			if( Capabilities.manufacturer == "Adobe Windows" )
				return;
			if(isIOS)
				extContext.call("shareTextFunction", phoneNumber, "", message);
			else
				extContext.call("sendMessageFunction", phoneNumber, message);
		}

		public function sendText(subject:String = "", text:String = "", userId:String = "", packageTarget:String = null):void
		{
			if( Capabilities.manufacturer == "Adobe Windows" )
				return;
			extContext.call("shareTextFunction", userId, subject, text, packageTarget);
		}

		public function shareImage(bitmap:BitmapData, subject:String = "", text:String = "", userId:String = "", packageTarget:String = null):void
		{
			if( Capabilities.manufacturer == "Adobe Windows" )
				return;
			extContext.call("shareImageFunction", bitmap, userId, subject, text, packageTarget);
		}
		
		public function encode(bitmapData:BitmapData, compression:Number = 1, url:String = null):ByteArray
		{
			// for other platforms
			if( Capabilities.manufacturer == "Adobe Windows" || isIOS )
			{
				var bytes:ByteArray = PNGEncoder2.encode(bitmapData);
				if( url != null)
				{
					var file:File = new	File(url);
					var fileStream:FileStream = new FileStream();
					fileStream.open(file, FileMode.WRITE);
					fileStream.writeBytes(bytes, 0, bytes.length);
					fileStream.close();
				}
				return bytes;
			}
			
			return extContext.call("encodeImageFunction", bitmapData, compression, url) as ByteArray;
		}

		private function context_statusHAndler( event:StatusEvent ):void 
		{
			this.dispatchEvent( new Event(event.code, false, false ) );
		}
	}
}