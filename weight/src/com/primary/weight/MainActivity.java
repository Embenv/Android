package com.primary.weight;

//import android.support.v7.app.ActionBarActivity;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;

import android.app.Activity;
import android.os.Bundle;

import android.widget.Button;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.content.Intent;
import android.database.CursorJoiner.Result;

//public class MainActivity extends ActionBarActivity {
public class MainActivity extends Activity {

	public static String sex="Boy";
	@Override
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 载入Layout(布局) main.xml */
		setContentView(R.layout.homepage_layout);
		/* 包含于 android.widget.Button 类中 */
		Button getButtonValue = (Button) findViewById(R.id.Button_calculate_result);
		/* 包含于 android.view.View android.view.View.OnClickListener 类中 */
		getButtonValue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				/* 获取体重信息 */
				EditText getEditTextValue = (EditText)findViewById(R.id.Edittext_height);
				double height=Double.parseDouble(getEditTextValue.getText().toString());
				
				/* 获取选择的性别信息 */
				RadioGroup getRadioGroupValue = (RadioGroup)findViewById(R.id.Radiogroup_sex);
				getRadioGroupValue.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					/* 第一个修改的地方 */
					public void onCheckedChanged(RadioGroup arg0, int checkedId) {
						if(checkedId==R.id.Radiobutton_boy) {
							sex="Boy";
						}
						else {
							sex="Girl";
						}
					}
				});
				/* 创建一个Intent对象，相当于快递员 */
				Intent courier = new Intent();
				/* 第二个修改的地方 */
				courier.setClass(MainActivity.this, ComputeActivity.class);
				/* 创建一个Bundle对象，相当于包裹 */
				Bundle dataPackage = new Bundle();
				dataPackage.putDouble("dataPackageHeight", height);
				dataPackage.putString("dataPackageSex", sex);
				/* 将包裹交给快递员 */
				courier.putExtras(dataPackage);
				/* 快递员出发 */
				startActivity(courier);
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compute, menu);
		return true;
	}
	
}
