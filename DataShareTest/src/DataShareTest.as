package
{

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
	import com.adobe.DataShareLibrary.DataShare;	

	public class DataShareTest extends Sprite
	{
		private var button_txt:Sprite = new Sprite();
		private var button_attach:Sprite = new Sprite();
		private var button_sendsms:Sprite = new Sprite();
		private var bitmap:Bitmap;
		private var bitmapData:BitmapData;

		public function DataShareTest()
		{
			super();
			drawButtonForShareImage()
			drawButtonToSendData();
			drawButtonToSendMessage();
			

			
			var loader:Loader = new Loader();
			loader.contentLoaderInfo.addEventListener(Event.COMPLETE, onComplete);
			loader.load(new URLRequest("adobe.gif"));
			
			function onComplete (event:Event):void
			{
				bitmapData = event.target.content.bitmapData;
			}
			bitmap = new Bitmap(bitmapData);
			
			
			addChild(button_txt);
			addChild(button_attach);
			addChild(button_sendsms);
			
			button_txt.addEventListener(MouseEvent.MOUSE_DOWN, imageHandler);
			button_attach.addEventListener(MouseEvent.MOUSE_DOWN, textHandler);
			button_sendsms.addEventListener(MouseEvent.MOUSE_DOWN, msgHandler);

			// support autoOrients
			stage.align = StageAlign.TOP_LEFT;
			stage.scaleMode = StageScaleMode.NO_SCALE;
		}
		
		private function drawButtonForShareImage():void {
			var textLabel:TextField = new TextField()
			button_txt.graphics.clear();
			button_txt.graphics.beginFill(0xD4D4D4); // grey color
			button_txt.graphics.drawRoundRect(150, 100, 150, 150, 10, 10); // x, y, width, height, ellipseW, ellipseH
			button_txt.graphics.endFill();
			textLabel.text = "Share Image";
			textLabel.x = 170;
			textLabel.y = 120;
			textLabel.selectable = false;
			button_txt.addChild(textLabel)
		}
		private function drawButtonToSendData():void {
			var textLabel:TextField = new TextField()
			button_attach.graphics.clear();
			button_attach.graphics.beginFill(0xD4D4D4); // grey color
			button_attach.graphics.drawRoundRect(150, 300, 150, 150, 10, 10); // x, y, width, height, ellipseW, ellipseH
			button_attach.graphics.endFill();
			textLabel.text = "Send Data";
			textLabel.x = 170;
			textLabel.y = 320;
			textLabel.selectable = false;
			button_attach.addChild(textLabel)
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
			var img_share:DataShare = new DataShare();		// Create object of DataShare class
			img_share.showToast("Attaching Image...");
			var email_id:String = new String();
			email_id="adobeairnoida@gmail.com,test@gmail.com";
			img_share.shareImage(bitmapData,"Sharing some doc","Please find the attachment",email_id);

		}
		private function textHandler(event:MouseEvent):void {
			var txt_share:DataShare = new DataShare();
			txt_share.showToast("Please share some Feedback to us...");
			var userid:String = new String();
			userid="adobeairnoida@gmail.com,test@gmail.com";
			txt_share.sendData("Your Feedback is Valuable to us","Write something here..",userid);
			
		}
		private function msgHandler(event:MouseEvent):void {
			var msg_share:DataShare = new DataShare();
			msg_share.showToast("Sending Message..");
			msg_share.sendMessage("Heyy, How are you?","12345678");
			
			
		}
		
	}
}