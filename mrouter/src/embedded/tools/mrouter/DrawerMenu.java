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
	
	DrawerLayout drawerLayout;//����һ�����벼��
	RelativeLayout leftDrawer;//����һ����Բ���Ϊ��߻����Ĳ˵�(�����)
	
	Builder menuDialog;//����һ���Ի���
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawermenuview);
        
        menuDialog = new AlertDialog.Builder(this);//����һ��������Ի������
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
		
		relativeLayout1.setClickable(true);//��������Բ��ֵ�������Ϊ�ɵ��(���Ƿ���һ��������ǲ����ڵ�imagebuttom�����ǲ����Ե����)
		relativeLayout1.setOnClickListener(new RelativeLayout1Onclick());//����relativeLayout1��Onclick����
		relativeLayout1.setOnTouchListener(new RelativeLayout1OnTouch());//����relativeLayout1��OnTouch����
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
			drawerLayout.closeDrawer(leftDrawer);//���벼�ֵ�һ��ʱ�䣬����ߵĻ����˵��ص�
			menuDialog.setTitle("��ܰ��ʾ").setMessage("ƴ��������......").setPositiveButton("�˳�", null).show();//���þ���Ի���ı��⣬��ʾ��Ϣ��ȷ����ť�����öԻ���
		}
	}
	class RelativeLayout2Onclick implements OnClickListener {
		@Override
		public void onClick(View buttonview) {
			drawerLayout.closeDrawer(leftDrawer);
			menuDialog.setTitle("��ܰ��ʾ").setMessage("ƴ��������......").setPositiveButton("�˳�", null).show();
		}
	}
	class RelativeLayout3Onclick implements OnClickListener {
		@Override
		public void onClick(View buttonview) {
			drawerLayout.closeDrawer(leftDrawer);
			menuDialog.setTitle("��ܰ��ʾ").setMessage("ƴ��������......").setPositiveButton("�˳�", null).show();
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
	
	class RelativeLayout1OnTouch implements OnTouchListener {  //RelativeLayout1OnTouchʵ��OnTouch������
		@Override
		public boolean onTouch(View buttonview, MotionEvent event) {
			
			if(event.getAction()==MotionEvent.ACTION_DOWN) /*�Դ����Ķ��������жϣ���Ϊ���°�ťʱ(ACTION_DOWN)������������һ��������ɫ
																								  *��Ϊ����ťʱ(ACTION_UP)��Ϊ��һ����ɫ...
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
	    			Thread.sleep(1000);//�߳�����ʱ��(����̬)
	    			Message changetimemessage = new Message();
	    			//changetimemessage.what = 1;//��message�ĳ�Ա������ֵΪ1
	    			changetimehandler.sendMessage(changetimemessage);
	    		}
	    		catch(InterruptedException e) {
	    			e.printStackTrace();
	    		}
    		}
    	}
    }
    
    private Handler changetimehandler = new Handler () { //����һ��Handler���󣬸��������߳�ͨѶ�������߳���ִ������runable����
    	@Override
        public void handleMessage(Message changetimemessage) {
    		super.handleMessage(changetimemessage);
    		if (true)//changetimemessage.what==1
    		{
    			Calendar getdate = Calendar.getInstance();//��ȡ��ǰϵͳʱ��
    	        
    	        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy��MM��dd�� HH:mm:ss");//���û�ȡʱ��ĸ�ʽ
    	        String stringdate = dateformat.format(getdate.getTime());//���ַ������浱ǰʱ��
    	        
    	        TextView time = (TextView)findViewById(R.id.drawermenuview_textview_time);

    	        time.setText("����ʱ��\n"+stringdate);
    		}
    	}
    };
}



