package com.primary.mdiary;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer; 
import android.media.SoundPool;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.view.MotionEvent;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Welcome extends Activity {
	
	TextView slogan;
	SoundPool soundpool;
	MediaPlayer mediaplayer;
	
	float x1 = 0;
	 float x2 = 0;
	 float y1 = 0;
	 float y2 = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomeview);
		
		SoundPool soundpool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 0);
		soundpool.load(this,R.raw.bgmusic,1);

		mediaplayer = MediaPlayer.create(this, R.raw.shot);
		
		Typeface typeface =Typeface.createFromAsset(getAssets(), "typeface1.ttf");//添加一种字体，字体放在assets目录下
		slogan =(TextView) findViewById(R.id.textview);
		slogan.setTypeface(typeface);
		
		ImageView imageview = (ImageView) findViewById(R.id.imageview);
		
		AlphaAnimation animation_imageview=new AlphaAnimation(0,1);
		TranslateAnimation animation_textview = new TranslateAnimation(800,0,0,0);

		animation_imageview.setDuration(2000);//设置动画持续时间为1秒
		animation_textview.setDuration(2000);
		
		//animation_imageview.setRepeatCount(6);
		
		//animation_textview.setInterpolator(this,android.R.anim.cycle_interpolator);
		animation_textview.setRepeatMode(Animation.REVERSE);

		animation_imageview.setFillAfter(true);//设置动画结束后保持当前的位置（即不返回到动画开始前的位置）
		animation_textview.setFillAfter(true);

		imageview.startAnimation(animation_imageview);
		slogan.startAnimation(animation_textview);
		
		Timer timer = new Timer();  
        timer.schedule(new MyTask(), 0);
        
        new ChangColorThread().start();
        

	}
	
	public class MyTask extends TimerTask {  
		  
	    @Override  
	    public void run() {
	    	
	    }
	}
	
	 public class ChangColorThread extends Thread {
		 
    	@Override
    	public void run() {
    		try 
    		{
    			Thread.sleep(2000);//线程休眠时间(阻塞态)
    			Message message = new Message();
    			changecolorhandler.sendMessage(message);
    		}
    		catch(InterruptedException e)
    		{
    			e.printStackTrace();
    		}
    	}
    }
	 
	/*创建一个Handler对象，负责与子线程通讯并在主线程中执行它的runable代码*/
	private Handler changecolorhandler = new Handler () { 
		@Override
		public void handleMessage(Message message) {
			super.handleMessage(message);
			if (true)
			{
				try {       
				     if(mediaplayer != null)  
				     {  
				    	 mediaplayer.stop();  
				     }      
				     mediaplayer.prepare();  
				     mediaplayer.start();  
				    } catch (Exception e) {  
				     e.printStackTrace();  
				    }
				slogan.setTextColor(Color.rgb(255, 0, 0));
			}
		}
	};
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//继承了Activity的onTouchEvent方法，直接监听点击事件
		if(event.getAction() == MotionEvent.ACTION_DOWN) {
			//当手指按下的时候
			x1 = event.getX();
			y1 = event.getY();
		}
		if(event.getAction() == MotionEvent.ACTION_UP) {
			//当手指离开的时候
			x2 = event.getX();
			y2 = event.getY();
			if(y1 - y2 > 80) {
				Toast.makeText(Welcome.this, "关闭背景音乐", Toast.LENGTH_SHORT).show();
				mediaplayer.stop();
			} else if(y2 - y1 > 80) {
				Toast.makeText(Welcome.this, "打开背景音乐", Toast.LENGTH_SHORT).show();
				try {       
				     if(mediaplayer != null)  
				     {  
				    	 mediaplayer.stop();  
				     }      
				     mediaplayer.prepare();  
				     mediaplayer.start();  
				    } catch (Exception e) {  
				     e.printStackTrace();  
				    }
			} else if(x1 - x2 > 80) {
				mediaplayer.stop();
				Intent intent = new Intent();
				intent.setClass(Welcome.this, DiariesList.class);
				startActivity(intent);
			} else if(x2 - x1 > 80) {
				mediaplayer.stop();
				finish();
				//System.exit(0);
			}
		}
		return super.onTouchEvent(event);
	}
	
}
