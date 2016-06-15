package embedded.tools.mrouter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Home extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homeview);
		
		TextView slogan =(TextView)findViewById(R.id.homeview_textview);
		Button buttonin = (Button) findViewById(R.id.homeview_buttonin);
		Button buttonout = (Button) findViewById(R.id.homeview_buttonout);

		Typeface typeface =Typeface.createFromAsset(getAssets(), "typeface1.ttf");//添加一种字体，字体放在assets目录下
		
		slogan.setTypeface(typeface);//设置主页标语字体
		buttonin.setTypeface(typeface);//设置进入按钮字体
		buttonout.setTypeface(typeface);//设置退出按钮字体
		
		
		
		
		
		buttonin.setOnClickListener(new classbuttonin());//设置进入按钮的OnClick监听
		buttonout.setOnClickListener(new classbuttonout());//设置退出按钮的OnClick监听
	}
	
	class classbuttonin implements OnClickListener {     //实现OnClickListener接口
		@Override
		public void onClick(View buttonView) {
			Intent menu_intent = new Intent();
			menu_intent.setClass(Home.this, DrawerMenu.class);
			startActivity(menu_intent);
		}
	}
	
	class classbuttonout implements OnClickListener {    //实现OnClickListener接口
		@Override
		public void onClick(View buttonView) {
			System.exit(0);
		}
	}
	
}
