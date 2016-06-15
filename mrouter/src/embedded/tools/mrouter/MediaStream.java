package embedded.tools.mrouter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;


public class MediaStream extends Activity {
	
	
	ImageButton imageButtonBack;
	ImageButton imageButtonClose;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.mediastreamview);

		imageButtonBack = (ImageButton) findViewById(R.id.mediastreamview_imagebuttom_back);
		imageButtonBack.setOnClickListener(new ImageButtonBackOnClick());
		imageButtonBack.setOnTouchListener(new ImageButtonBackOnCTouch());
		imageButtonClose = (ImageButton) findViewById(R.id.mediastreamview_imagebuttom_close);
		imageButtonClose.setOnClickListener(new ImageButtonCloseOnClick());
		imageButtonClose.setOnTouchListener(new ImageButtonCloseOnCTouch());
	}


	class ImageButtonBackOnClick implements OnClickListener {
		@Override
		public void onClick(View buttonview) {
			Intent menu_intent = new Intent();
			menu_intent.setClass(MediaStream.this, DrawerMenu.class);
			startActivity(menu_intent);
		}
	}
	
	class ImageButtonCloseOnClick implements OnClickListener {
		@Override
		public void onClick(View buttonview) {
			System.exit(0);
		}
	}
	
	class ImageButtonBackOnCTouch implements OnTouchListener {
		@Override
		public boolean onTouch(View buttonView, MotionEvent event) {
			if(event.getAction()==MotionEvent.ACTION_DOWN) 
            { 
				imageButtonBack.setBackgroundColor(Color.rgb(127,127,127));
            } 
            else if(event.getAction()==MotionEvent.ACTION_UP) 
            { 
            	imageButtonBack.setBackgroundColor(Color.rgb(33,33,33));
            } 
			return false;
		}
	}
	class ImageButtonCloseOnCTouch implements OnTouchListener {
		@Override
		public boolean onTouch(View buttonView, MotionEvent event) {
			if(event.getAction()==MotionEvent.ACTION_DOWN) 
            { 
				imageButtonClose.setBackgroundColor(Color.rgb(127,127,127));
            } 
            else if(event.getAction()==MotionEvent.ACTION_UP) 
            { 
            	imageButtonClose.setBackgroundColor(Color.rgb(33,33,33));
            } 
			return false;
		}
	}
}

