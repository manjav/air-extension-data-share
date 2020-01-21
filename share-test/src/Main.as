package {

	import flash.display.BitmapData;
	import flash.display.Loader;
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.net.URLRequest;
	import flash.text.TextField;
	import com.gerantech.extensions.share.Share;
	import flash.filesystem.File;

	public class Main extends Sprite {
		private var button_img:Sprite = new Sprite();
		private var button_text:Sprite = new Sprite();
		private var button_sendsms:Sprite = new Sprite();
		private var button_saveimage:Sprite = new Sprite();
		private var bitmapData:BitmapData;

		public function Main() {
			super();
			stage.align = StageAlign.TOP_LEFT;
			stage.scaleMode = StageScaleMode.NO_SCALE;

			this.addButton("Share Image", 50, 50, imageHandler);
			this.addButton("Encode Image", 250, 50, encodeHandler);
			this.addButton("Send Text", 50, 250, textHandler);
			this.addButton("Send Message", 50, 450, msgHandler);

			var loader:Loader = new Loader();
			loader.contentLoaderInfo.addEventListener(Event.COMPLETE, onComplete);
			loader.load(new URLRequest("assets/logo.png"));
			function onComplete(event:Event):void {
				bitmapData = event.target.content.bitmapData;
			}
		}

		private function addButton(label:String, x:Number, y:Number, handler:Function):void {
			var btn:Sprite = new Sprite();
			btn.x = x;
			btn.y = y;
			btn.graphics.beginFill(0xD4D4D4); // grey color
			btn.graphics.drawRoundRect(0, 0, 150, 150, 10, 10); // x, y, width, height, ellipseW, ellipseH
			btn.graphics.endFill();
			btn.addEventListener(MouseEvent.MOUSE_DOWN, handler);
			this.addChild(btn);

			var textLabel:TextField = new TextField()
			textLabel.selectable = false;
			textLabel.text = label;
			btn.addChild(textLabel);
		}

		private function msgHandler(event:MouseEvent):void
		{
			Share.instance.showToast("Sending Message...");
			Share.instance.sendMessage("Heyy, How are you?", "+989121778856");
		}

		private function textHandler(event:MouseEvent):void
		{
			Share.instance.showToast("Please share some Feedback to us...");
			var emails:String = "adobeairnoida@gmail.com,test@gmail.com";
			Share.instance.sendText("Your Feedback is Valuable to us", "Write something here..", emails);
		}

		private function imageHandler(event:MouseEvent):void {
			Share.instance.showToast("Sending Image...");
			var emails:String = "adobeairnoida@gmail.com,test@gmail.com";
			Share.instance.shareImage(this.bitmapData, "Sharing some doc", "Please find the attachment", emails);
		}

		private function encodeHandler(event:MouseEvent):void
		{
			Share.instance.showToast("Encoding Image...");
			Share.instance.encode(this.bitmapData, File.documentsDirectory.resolvePath("encoded.png").nativePath, 1);
		}
	}
}