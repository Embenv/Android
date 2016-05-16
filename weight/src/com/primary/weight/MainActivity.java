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
		/* ����Layout(����) main.xml */
		setContentView(R.layout.homepage_layout);
		/* ������ android.widget.Button ���� */
		Button getButtonValue = (Button) findViewById(R.id.Button_calculate_result);
		/* ������ android.view.View android.view.View.OnClickListener ���� */
		getButtonValue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				/* ��ȡ������Ϣ */
				EditText getEditTextValue = (EditText)findViewById(R.id.Edittext_height);
				double height=Double.parseDouble(getEditTextValue.getText().toString());
				
				/* ��ȡѡ����Ա���Ϣ */
				RadioGroup getRadioGroupValue = (RadioGroup)findViewById(R.id.Radiogroup_sex);
				getRadioGroupValue.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					/* ��һ���޸ĵĵط� */
					public void onCheckedChanged(RadioGroup arg0, int checkedId) {
						if(checkedId==R.id.Radiobutton_boy) {
							sex="Boy";
						}
						else {
							sex="Girl";
						}
					}
				});
				/* ����һ��Intent�����൱�ڿ��Ա */
				Intent courier = new Intent();
				/* �ڶ����޸ĵĵط� */
				courier.setClass(MainActivity.this, ComputeActivity.class);
				/* ����һ��Bundle�����൱�ڰ��� */
				Bundle dataPackage = new Bundle();
				dataPackage.putDouble("dataPackageHeight", height);
				dataPackage.putString("dataPackageSex", sex);
				/* �������������Ա */
				courier.putExtras(dataPackage);
				/* ���Ա���� */
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
