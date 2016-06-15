package embedded.tools.mrouter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class DrawerMenu extends Activity {
	RelativeLayout relativeLayout1;
	RelativeLayout relativeLayout2;
	RelativeLayout relativeLayout3;
	RelativeLayout relativeLayout4;
	RelativeLayout relativeLayout5;
	RelativeLayout relativeLayout6;
	RelativeLayout relativeLayout7;
	
	DrawerLayout drawerLayout;//声明一个抽屉布局
	RelativeLayout leftDrawer;//声明一个相对布局为左边滑动的菜单(左抽屉)
	
	Builder menuDialog;//声明一个对话框
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawermenuview);
        
        menuDialog = new AlertDialog.Builder(this);//创建一个警告类对话框对象
        drawerLayout = (DrawerLayout) findViewById(R.id.drawermenuview_totallayout);
        leftDrawer = (RelativeLayout) findViewById(R.id.drawermenuview_nestedlayout2);
        TextView slogan =(TextView)findViewById(R.id.drawermenuview_textview_slogan);
        TextView time =(TextView)findViewById(R.id.drawermenuview_textview_time);
		Typeface typeface =Typeface.createFromAsset(getAssets(),"typeface1.ttf");
		
		relativeLayout1 = (RelativeLayout) findViewById(R.id.drawermenuview_nestedlayout2_2);
		relativeLayout2 = (RelativeLayout) findViewById(R.id.drawermenuview_nestedlayout2_3);
		relativeLayout3 = (RelativeLayout) findViewById(R.id.drawermenuview_nestedlayout2_4);
		relativeLayout4 = (RelativeLayout) findViewById(R.id.drawermenuview_nestedlayout2_5);
		relativeLayout5 = (RelativeLayout) findViewById(R.id.drawermenuview_nestedlayout2_6);
		relativeLayout6 = (RelativeLayout) findViewById(R.id.drawermenuview_nestedlayout2_7);
		relativeLayout7 = (RelativeLayout) findViewById(R.id.drawermenuview_nestedlayout2_8);
		
		relativeLayout1.setClickable(true);//把整个相对布局的区域设为可点击(但是发现一个问题就是布局内的imagebuttom区域是不可以点击的)
		relativeLayout1.setOnClickListener(new RelativeLayout1Onclick());//设置relativeLayout1的Onclick监听
		relativeLayout1.setOnTouchListener(new RelativeLayout1OnTouch());//设置relativeLayout1的OnTouch监听
		relativeLayout2.setClickable(true);
		relativeLayout2.setOnClickListener(new RelativeLayout2Onclick());
		relativeLayout2.setOnTouchListener(new RelativeLayout2OnTouch());
		relativeLayout3.setClickable(true);
		relativeLayout3.setOnClickListener(new RelativeLayout3Onclick());
		relativeLayout3.setOnTouchListener(new RelativeLayout3OnTouch());
		relativeLayout4.setClickable(true);
		relativeLayout4.setOnClickListener(new RelativeLayout4Onclick());
		relativeLayout4.setOnTouchListener(new RelativeLayout4OnTouch());
		relativeLayout5.setClickable(true);
		relativeLayout5.setOnClickListener(new RelativeLayout5Onclick());
		relativeLayout5.setOnTouchListener(new RelativeLayout5OnTouch());
		relativeLayout6.setClickable(true);
		relativeLayout6.setOnClickListener(new RelativeLayout6Onclick());
		relativeLayout6.setOnTouchListener(new RelativeLayout6OnTouch());
		relativeLayout7.setClickable(true);
		relativeLayout7.setOnClickListener(new RelativeLayout7Onclick());
		relativeLayout7.setOnTouchListener(new RelativeLayout7OnTouch());
		
		new TimeThread().start();
		
		slogan.setTypeface(typeface);
		time.setTypeface(typeface);
        
    }
    
	class RelativeLayout1Onclick implements OnClickListener {
		@Override
		public void onClick(View buttonview) {
			drawerLayout.closeDrawer(leftDrawer);//抽屉布局的一个时间，把左边的滑动菜单关掉
			menuDialog.setTitle("温馨提示").setMessage("拼命开发中......").setPositiveButton("退出", null).show();//设置警告对话框的标题，显示信息，确定按钮并调用对话框
		}
	}
	class RelativeLayout2Onclick implements OnClickListener {
		@Override
		public void onClick(View buttonview) {
			drawerLayout.closeDrawer(leftDrawer);
			menuDialog.setTitle("温馨提示").setMessage("拼命开发中......").setPositiveButton("退出", null).show();
		}
	}
	class RelativeLayout3Onclick implements OnClickListener {
		@Override
		public void onClick(View buttonview) {
			drawerLayout.closeDrawer(leftDrawer);
			menuDialog.setTitle("温馨提示").setMessage("拼命开发中......").setPositiveButton("退出", null).show();
		}
	}
	class RelativeLayout4Onclick implements OnClickListener {
		@Override
		public void onClick(View buttonview) {
			drawerLayout.closeDrawer(leftDrawer);
			Intent menu_intent = new Intent();
			menu_intent.setClass(DrawerMenu.this, MusicPlay.class);
			startActivity(menu_intent);
		}
	}
	class RelativeLayout5Onclick implements OnClickListener {
		@Override
		public void onClick(View buttonview) {
			drawerLayout.closeDrawer(leftDrawer);
			Intent menu_intent = new Intent();
			menu_intent.setClass(DrawerMenu.this, MediaStream.class);
			startActivity(menu_intent);
		}
	}
	class RelativeLayout6Onclick implements OnClickListener {
		@Override
		public void onClick(View buttonview) {
			drawerLayout.closeDrawer(leftDrawer);
			Intent menu_intent = new Intent();
			menu_intent.setClass(DrawerMenu.this, Information.class);
			startActivity(menu_intent);
		}
	}
	class RelativeLayout7Onclick implements OnClickListener {
		@Override
		public void onClick(View buttonview) {
			drawerLayout.closeDrawer(leftDrawer);
			Intent menu_intent = new Intent();
			menu_intent.setClass(DrawerMenu.this, Home.class);
			startActivity(menu_intent);
		}
	}
	
	class RelativeLayout1OnTouch implements OnTouchListener {  //RelativeLayout1OnTouch实现OnTouch监听的
		@Override
		public boolean onTouch(View buttonview, MotionEvent event) {
			
			if(event.getAction()==MotionEvent.ACTION_DOWN) /*对触发的动作进行判断，当为按下按钮时(ACTION_DOWN)设置整个布局一个背景颜色
																								  *当为弹起按钮时(ACTION_UP)设为另一个颜色...
																								  */
            { 
				relativeLayout1.setBackgroundColor(Color.rgb(127,127,127));
            } 
            else if(event.getAction()==MotionEvent.ACTION_UP) 
            { 
            	relativeLayout1.setBackgroundColor(Color.rgb(17, 17, 17));
            } 
			return false;
		}
	}
	class RelativeLayout2OnTouch implements OnTouchListener {
		@Override
		public boolean onTouch(View buttonview, MotionEvent event) {
			
			if(event.getAction()==MotionEvent.ACTION_DOWN) 
            { 
				relativeLayout2.setBackgroundColor(Color.rgb(127,127,127)); 
            } 
            else if(event.getAction()==MotionEvent.ACTION_UP) 
            { 
            	relativeLayout2.setBackgroundColor(Color.rgb(17, 17, 17));
            } 
			return false;
		}
	}
	class RelativeLayout3OnTouch implements OnTouchListener {
		@Override
		public boolean onTouch(View buttonview, MotionEvent event) {
			
			if(event.getAction()==MotionEvent.ACTION_DOWN) 
            { 
				relativeLayout3.setBackgroundColor(Color.rgb(127,127,127)); 
            } 
            else if(event.getAction()==MotionEvent.ACTION_UP) 
            { 
            	relativeLayout3.setBackgroundColor(Color.rgb(17, 17, 17)); 
            } 
			return false;
		}
	}
	class RelativeLayout4OnTouch implements OnTouchListener {
		@Override
		public boolean onTouch(View buttonview, MotionEvent event) {
			
			if(event.getAction()==MotionEvent.ACTION_DOWN) 
            { 
				relativeLayout4.setBackgroundColor(Color.rgb(127,127,127)); 
            } 
            else if(event.getAction()==MotionEvent.ACTION_UP) 
            { 
            	relativeLayout4.setBackgroundColor(Color.rgb(17, 17, 17)); 
            } 
			return false;
		}
	}
	class RelativeLayout5OnTouch implements OnTouchListener {
		@Override
		public boolean onTouch(View buttonview, MotionEvent event) {
			
			if(event.getAction()==MotionEvent.ACTION_DOWN) 
            { 
				relativeLayout5.setBackgroundColor(Color.rgb(127,127,127)); 
            } 
            else if(event.getAction()==MotionEvent.ACTION_UP) 
            { 
            	relativeLayout5.setBackgroundColor(Color.rgb(17, 17, 17));
            } 
			return false;
		}
	}
	class RelativeLayout6OnTouch implements OnTouchListener {
		@Override
		public boolean onTouch(View buttonview, MotionEvent event) {
			
			if(event.getAction()==MotionEvent.ACTION_DOWN) 
            { 
				relativeLayout6.setBackgroundColor(Color.rgb(127,127,127)); 
            } 
            else if(event.getAction()==MotionEvent.ACTION_UP) 
            { 
            	relativeLayout6.setBackgroundColor(Color.rgb(17, 17, 17));
            } 
			return false;
		}
	}
	class RelativeLayout7OnTouch implements OnTouchListener {
		@Override
		public boolean onTouch(View buttonview, MotionEvent event) {
			
			if(event.getAction()==MotionEvent.ACTION_DOWN) 
            { 
				relativeLayout7.setBackgroundColor(Color.rgb(127,127,127)); 
            } 
            else if(event.getAction()==MotionEvent.ACTION_UP) 
            { 
            	relativeLayout7.setBackgroundColor(Color.rgb(17, 17, 17));
            } 
			return false;
		}
	}

	 public class TimeThread extends Thread {
    	@Override
    	public void run() {
    		while (true)
    		{
	    		try {
	    			Thread.sleep(1000);//线程休眠时间(阻塞态)
	    			Message changetimemessage = new Message();
	    			//changetimemessage.what = 1;//把message的成员变量赋值为1
	    			changetimehandler.sendMessage(changetimemessage);
	    		}
	    		catch(InterruptedException e) {
	    			e.printStackTrace();
	    		}
    		}
    	}
    }
    
    private Handler changetimehandler = new Handler () { //创建一个Handler对象，负责与子线程通讯并在主线程中执行它的runable代码
    	@Override
        public void handleMessage(Message changetimemessage) {
    		super.handleMessage(changetimemessage);
    		if (true)//changetimemessage.what==1
    		{
    			Calendar getdate = Calendar.getInstance();//获取当前系统时间
    	        
    	        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");//设置获取时间的格式
    	        String stringdate = dateformat.format(getdate.getTime());//把字符串保存当前时间
    	        
    	        TextView time = (TextView)findViewById(R.id.drawermenuview_textview_time);

    	        time.setText("北京时间\n"+stringdate);
    		}
    	}
    };
}



