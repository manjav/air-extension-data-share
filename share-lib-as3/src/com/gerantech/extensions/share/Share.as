package com.gerantech.extensions.share {
	import flash.display.BitmapData;
	import flash.external.ExtensionContext;
	import flash.system.Capabilities;

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
		
		public var isIOS:Boolean = (Capabilities.manufacturer.indexOf("iOS") != -1);

		public function Share()
		{
			// Create the instance of the ExtensionContext class if it has not been created yet.
			// The extension context's context type is NULL, because this extension
			// has only one context type.
			extContext = ExtensionContext.createExtensionContext("com.gerantech.extensions.share", null);
		}

	}
}