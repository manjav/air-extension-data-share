package {

	import com.adobe.DataShareLibrary.DataShare;
	import com.gerantech.extensions.NativeAbilities;
	import com.gerantech.extensions.events.AndroidEvent;

	import flash.display.Bitmap;
	import flash.display.BitmapData;
	import flash.display.Loader;
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.net.URLRequest;
	import flash.text.TextField;

	public class DataShareTest extends Sprite {
		private var button_img:Sprite = new Sprite();
		private var button_text:Sprite = new Sprite();
		private var button_sendsms:Sprite = new Sprite();
		private var bitmap:Bitmap;
		private var bitmapData:BitmapData;

		public function DataShareTest() {
			super();
			drawButtonForShareImage()
			drawButtonToSendData();
			drawButtonToSendMessage();

			var loader:Loader = new Loader();
			loader.contentLoaderInfo.addEventListener(Event.COMPLETE, onComplete);
			loader.load(new URLRequest("assets/adobe.gif"));
			function onComplete(event:Event):void {
				bitmapData = event.target.content.bitmapData;
			}
			bitmap = new Bitmap(bitmapData);

			addChild(button_img);
			addChild(button_text);
			addChild(button_sendsms);

			button_img.addEventListener(MouseEvent.MOUSE_DOWN, imageHandler);
			button_text.addEventListener(MouseEvent.MOUSE_DOWN, textHandler);
			button_sendsms.addEventListener(MouseEvent.MOUSE_DOWN, msgHandler);

			// support autoOrients
			stage.align = StageAlign.TOP_LEFT;
			stage.scaleMode = StageScaleMode.NO_SCALE;
		}

		private function drawButtonForShareImage():void {
			var textLabel:TextField = new TextField()
			button_img.graphics.clear();
			button_img.graphics.beginFill(0xD4D4D4); // grey color
			button_img.graphics.drawRoundRect(150, 100, 150, 150, 10, 10); // x, y, width, height, ellipseW, ellipseH
			button_img.graphics.endFill();
			textLabel.text = "Share Image";
			textLabel.x = 170;
			textLabel.y = 120;
			textLabel.selectable = false;
			button_img.addChild(textLabel)
		}

		private function drawButtonToSendData():void {
			var textLabel:TextField = new TextField()
			button_text.graphics.clear();
			button_text.graphics.beginFill(0xD4D4D4); // grey color
			button_text.graphics.drawRoundRect(150, 300, 150, 150, 10, 10); // x, y, width, height, ellipseW, ellipseH
			button_text.graphics.endFill();
			textLabel.text = "Send Data";
			textLabel.x = 170;
			textLabel.y = 320;
			textLabel.selectable = false;
			button_text.addChild(textLabel)
		}

		private function drawButtonToSendMessage():void {
			var textLabel:TextField = new TextField()
			button_sendsms.graphics.clear();
			button_sendsms.graphics.beginFill(0xD4D4D4); // grey color
			button_sendsms.graphics.drawRoundRect(150, 500, 150, 150, 10, 10); // x, y, width, height, ellipseW, ellipseH
			button_sendsms.graphics.endFill();
			textLabel.text = "Send Message";
			textLabel.x = 170;
			textLabel.y = 520;
			textLabel.selectable = false;
			button_sendsms.addChild(textLabel)
		}

		private function imageHandler(event:MouseEvent):void {

			NativeAbilities.instance.addEventListener(AndroidEvent.PERMISSION_REQUEST, nativeAbilities_requestPermissionHandler);
			if( NativeAbilities.instance.requestPermission("android.permission.WRITE_EXTERNAL_STORAGE", 1361) )
				shareImage();
		}
		private function nativeAbilities_requestPermissionHandler(event:Object):void
		{
			NativeAbilities.instance.removeEventListener(AndroidEvent.PERMISSION_REQUEST , nativeAbilities_requestPermissionHandler);
			if( String(event.data).search("WRITE_EXTERNAL_STORAGE") > -1 )
				shareImage();
		}

		private function textHandler(event:MouseEvent):void {
			var txt_share:DataShare = new DataShare();
			txt_share.showToast("Please share some Feedback to us...");
			var userid:String = new String();
			userid = "adobeairnoida@gmail.com,test@gmail.com";
			txt_share.sendData("Your Feedback is Valuable to us", "Write something here..", userid);
		}

		private function msgHandler(event:MouseEvent):void {
			var msg_share:DataShare = new DataShare();
			msg_share.showToast("Sending Message..");
			msg_share.sendMessage("Heyy, How are you?", "12345678");
		}

		private function shareImage():void
		{
			var img_share:DataShare = new DataShare(); // Create object of DataShare class
			img_share.showToast("Attaching Image...");
			var email_id:String = new String();
			email_id = "adobeairnoida@gmail.com,test@gmail.com";
			img_share.shareImage(bitmapData, "Sharing some doc", "Please find the attachment", email_id);
		}
	}
}
