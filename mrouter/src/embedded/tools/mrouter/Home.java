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

		Typeface typeface =Typeface.createFromAsset(getAssets(), "typeface1.ttf");//���һ�����壬�������assetsĿ¼��
		
		slogan.setTypeface(typeface);//������ҳ��������
		buttonin.setTypeface(typeface);//���ý��밴ť����
		buttonout.setTypeface(typeface);//�����˳���ť����
		
		
		
		
		
		buttonin.setOnClickListener(new classbuttonin());//���ý��밴ť��OnClick����
		buttonout.setOnClickListener(new classbuttonout());//�����˳���ť��OnClick����
	}
	
	class classbuttonin implements OnClickListener {     //ʵ��OnClickListener�ӿ�
		@Override
		public void onClick(View buttonView) {
			Intent menu_intent = new Intent();
			menu_intent.setClass(Home.this, DrawerMenu.class);
			startActivity(menu_intent);
		}
	}
	
	class classbuttonout implements OnClickListener {    //ʵ��OnClickListener�ӿ�
		@Override
		public void onClick(View buttonView) {
			System.exit(0);
		}
	}
	
}
