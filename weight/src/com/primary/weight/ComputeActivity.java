package com.primary.weight;

//import android.support.v7.app.ActionBarActivity;
//import android.os.Bundle;
//import android.view.Menu;
//import android.view.MenuItem;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

//public class ComputeActivity extends ActionBarActivity {

public class ComputeActivity extends Activity 
{
  /* Called when the activity is first created. */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    /* 载入Layout(布局) result.xml */
    setContentView(R.layout.compute_result_layout);
    /* 取得Intent中的Bundle对象(取得快递员手中的快递) */
    Bundle receiveDataPackage = this.getIntent().getExtras();
    
    String receiveDataPackageSex = receiveDataPackage.getString("dataPackageSex");
    double receiveDataPackageHeight = receiveDataPackage.getDouble("dataPackageHeight");
    
    String sexText="";
    if(receiveDataPackageSex.equals("Boy")) {
    	sexText="男性";
    	}
    else {
    	sexText="女性";
    }
    
    String standardWeight=this.getWeight(receiveDataPackageSex, receiveDataPackageHeight);
    
    TextView setTextViewValue = (TextView) findViewById(R.id.Result_show_textview);
    setTextViewValue.setText("你好：\n根据你输入的数据可知你是一位"+sexText+"；\n而且你的身高为"+receiveDataPackageHeight+"CM。\n你的标准体重应为"+standardWeight+"KG。");
  }
  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

  
  private String formatControlAndTransform(double doubleNumber) {
	  NumberFormat formatControl = new DecimalFormat("0.00");
	  String formatTransform=formatControl.format(doubleNumber);
	  return formatTransform;
  }
  
  private String getWeight(String sex, double height){
	  String weight="";
	  if(sex.equals("Boy")) {
		  weight=formatControlAndTransform((height-80)*0.7);
	  }
	  else {
		  weight=formatControlAndTransform((height-70)*0.6);
	  }
	  return weight;
  }
}




