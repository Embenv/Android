package com.primary.singlesel;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	
	RadioButton radioButton1 = null;
	RadioButton radioButton2 = null;
	RadioButton radioButton3 = null;
	RadioButton radioButton4 = null;
	RadioGroup radioGroup = null;
	RadioButton currentRadioButton = null;
	
	//Handler handler = new Handler();
	
	public static String tilte = "睡你妈B起来嗨！";
	
	//Runnable titleUpdateThread = new Runnable(){
	//	public void run(){
	//		setTitle(tilte);
	//	}
	//};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainlayout);
		
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		
		radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
		radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
		radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
		radioButton4 = (RadioButton) findViewById(R.id.radioButton4);
		
		radioButton1.setClickable(true);
		
		radioGroup.setOnCheckedChangeListener(theChangeRadio);
		
		Button confirm = (Button) findViewById(R.id.button1);
		Button cancel = (Button) findViewById(R.id.button2);
		
		confirm.setOnClickListener(new confirm());
		cancel.setOnClickListener(new cancel());
	}
	
	class confirm implements OnClickListener {
		@Override
		public void onClick(View buttonView) {
			if (currentRadioButton.getText().equals("in")) {
				
				setTitle("你选择的答案是：正确的！");
				//tilte = "你选择的答案是：正确的！";
				//handler.post(titleUpdateThread);
			}
			else {
				setTitle("你选择的答案是：错误的！");
			}
		}
	}
	
	class cancel implements OnClickListener {
		@Override
		public void onClick(View buttonView) {
			radioGroup.clearCheck();
			setTitle("");
		}
	}
	
	private RadioGroup.OnCheckedChangeListener theChangeRadio = new RadioGroup.OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(RadioGroup radioGroup, int checkedById) {
			if (checkedById == radioButton1.getId()) {
				currentRadioButton=radioButton1;
			}
			else
				if (checkedById == radioButton2.getId()) {
					currentRadioButton=radioButton2;
				}
				else
					if (checkedById == radioButton3.getId()) {
						currentRadioButton=radioButton3;
					}
					else
						if (checkedById == radioButton4.getId()) {
							currentRadioButton=radioButton4;
						}
		}
	};

}
