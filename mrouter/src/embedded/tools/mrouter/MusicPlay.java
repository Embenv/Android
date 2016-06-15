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
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
 
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class MusicPlay extends Activity {
	
	ImageButton imageButton1;
	ImageButton imageButton2;
	ImageButton imageButton3;
	ImageButton imageButton4;
	ImageButton imageButton5;
	ImageButton imageButton6;
	ImageButton imageButton7;

	int pauseState=0;
	int volumeClickCount=0;
	int volume=90;
	int volumeButtomFlag=100;
	
	String result="";
	Session session =null;
	ChannelExec openChannel =null;
	String command="ps";
	boolean onClickCheck=false;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.musicplayview);
		
		TextView backmessage = (TextView)  findViewById(R.id.musicplayview_textview_backmessage);
		imageButton1 = (ImageButton) findViewById(R.id.musicplayview_imagebuttom_vcut);
		imageButton1.setOnClickListener(new ImageButtonVcutOnClick());
		imageButton1.setOnTouchListener(new ImageButtonVcutOnTouch());
		imageButton2 = (ImageButton) findViewById(R.id.musicplayview_imagebuttom_vadd);
		imageButton2.setOnClickListener(new ImageButtonVaddOnClick());
		imageButton2.setOnTouchListener(new ImageButtonVaddOnTouch());
		imageButton3 = (ImageButton) findViewById(R.id.musicplayview_imagebuttom_stop);
		imageButton3.setOnClickListener(new ImageButtonStopOnClick());
		imageButton3.setOnTouchListener(new ImageButtonStopOnTouch());
		imageButton4 = (ImageButton) findViewById(R.id.musicplayview_imagebuttom_pause);
		imageButton4.setOnClickListener(new ImageButtonPauseOnClick());
		imageButton4.setOnTouchListener(new ImageButtonPauseOnTouch());
		imageButton5 = (ImageButton) findViewById(R.id.musicplayview_imagebuttom_play);
		imageButton5.setOnClickListener(new ImageButtonPlayOnClick());
		imageButton5.setOnTouchListener(new ImageButtonPlayOnTouch());
		imageButton6 = (ImageButton) findViewById(R.id.musicplayview_imagebuttom_back);
		imageButton6.setOnClickListener(new ImageButtonBackOnClick());
		imageButton6.setOnTouchListener(new ImageButtonBackOnTouch());
		imageButton7 = (ImageButton) findViewById(R.id.musicplayview_imagebuttom_close);
		imageButton7.setOnClickListener(new ImageButtonCloseOnClick());
		imageButton7.setOnTouchListener(new ImageButtonCloseOnTouch());
		
		backmessage.setText(result);
		SSHControl controlthread = new SSHControl();
		controlthread.start();
	}
	
	
	class ImageButtonVcutOnClick implements OnClickListener {
		@Override
		public void onClick(View buttonview) {
			if(volumeButtomFlag==1)
			{
				volumeClickCount++;
				volumeButtomFlag=0;
			}
			volume=(volume--)-volumeClickCount;
			volumeClickCount++;

			command="amixer set Headphone "+volume+"%";
			
			onClickCheck=true;

		}
	}
	class ImageButtonVaddOnClick implements OnClickListener {
		@Override
		public void onClick(View buttonview) {
			if(volumeButtomFlag==0)
			{
				volumeClickCount--;
				volumeButtomFlag=1;
			}
			volume=(volume++)+volumeClickCount;
			volumeClickCount--;

			command="amixer set Headphone "+volume+"%";
			
			onClickCheck=true;

		}
	}
	class ImageButtonStopOnClick implements OnClickListener {
		@Override
		public void onClick(View buttonview) {

			command="killall madplay";
			
			onClickCheck=true;

		}
	}
	class ImageButtonPauseOnClick implements OnClickListener {
		@Override
		public void onClick(View buttonview) {
			if (pauseState==0)
			{
				//command="killall -STOP madplay";
				command="p";
				pauseState++;
			}
			else
			{
				//command="killall -CONT madplay";
				command="p";
				pauseState--;
			}

			onClickCheck=true;
			
		}
	}
	class ImageButtonPlayOnClick implements OnClickListener {
		@Override
		public void onClick(View buttonview) {

			command="madplay /mnt/SD-P1/music/*.mp3 -z -r2 -a-10";
			
			onClickCheck=true;
			
		}
	}
	
	class ImageButtonBackOnClick implements OnClickListener {
		@Override
		public void onClick(View buttonview) {
			Intent menu_intent = new Intent();
			menu_intent.setClass(MusicPlay.this, DrawerMenu.class);
			startActivity(menu_intent);
		}
	}
	
	class ImageButtonCloseOnClick implements OnClickListener {
		@Override
		public void onClick(View buttonview) {
			System.exit(0);
		}
	}
	
	class ImageButtonVcutOnTouch implements OnTouchListener {
		@Override
		public boolean onTouch(View buttonview, MotionEvent event) {
			if(event.getAction()==MotionEvent.ACTION_DOWN) 
            { 
				imageButton1.setImageResource(R.drawable.vcutcli);
            } 
            else if(event.getAction()==MotionEvent.ACTION_UP) 
            { 
            	imageButton1.setImageResource(R.drawable.vcutnor);
            } 
			return false;
		}
	}
	class ImageButtonVaddOnTouch implements OnTouchListener {
		@Override
		public boolean onTouch(View buttonview, MotionEvent event) {
			if(event.getAction()==MotionEvent.ACTION_DOWN) 
            { 
				imageButton2.setImageResource(R.drawable.vaddcli);
            } 
            else if(event.getAction()==MotionEvent.ACTION_UP) 
            { 
            	imageButton2.setImageResource(R.drawable.vaddnor);
            } 
			return false;
		}
	}
	class ImageButtonStopOnTouch implements OnTouchListener {
		@Override
		public boolean onTouch(View buttonview, MotionEvent event) {
			if(event.getAction()==MotionEvent.ACTION_DOWN) 
            { 
				imageButton3.setImageResource(R.drawable.mstopcli);
            } 
            else if(event.getAction()==MotionEvent.ACTION_UP) 
            { 
            	imageButton3.setImageResource(R.drawable.mstopnor);
            } 
			return false;
		}
	}
	class ImageButtonPauseOnTouch implements OnTouchListener {
		@Override
		public boolean onTouch(View buttonview, MotionEvent event) {
			if(event.getAction()==MotionEvent.ACTION_DOWN) 
            { 
				imageButton4.setImageResource(R.drawable.mpausecli);
            } 
            else if(event.getAction()==MotionEvent.ACTION_UP) 
            { 
            	imageButton4.setImageResource(R.drawable.mpausenor);
            } 
			return false;
		}
	}
	class ImageButtonPlayOnTouch implements OnTouchListener {
		@Override
		public boolean onTouch(View buttonview, MotionEvent event) {
			if(event.getAction()==MotionEvent.ACTION_DOWN) 
            { 
				imageButton5.setImageResource(R.drawable.mplaycli);
            } 
            else if(event.getAction()==MotionEvent.ACTION_UP) 
            { 
            	imageButton5.setImageResource(R.drawable.mplaynor);
            } 
			return false;
		}
	}
	
	class ImageButtonBackOnTouch implements OnTouchListener {
		@Override
		public boolean onTouch(View buttonview, MotionEvent event) {
			if(event.getAction()==MotionEvent.ACTION_DOWN) 
            { 
				imageButton6.setBackgroundColor(Color.rgb(127,127,127));
            } 
            else if(event.getAction()==MotionEvent.ACTION_UP) 
            { 
            	imageButton6.setBackgroundColor(Color.rgb(33,33,33));
            } 
			return false;
		}
	}
	class ImageButtonCloseOnTouch implements OnTouchListener {
		@Override
		public boolean onTouch(View buttonview, MotionEvent event) {
			if(event.getAction()==MotionEvent.ACTION_DOWN) 
            { 
				imageButton7.setBackgroundColor(Color.rgb(127,127,127));
            } 
            else if(event.getAction()==MotionEvent.ACTION_UP) 
            { 
            	imageButton7.setBackgroundColor(Color.rgb(33,33,33));
            } 
			return false;
		}
	}
	
	public class SSHControl extends Thread {
		@Override
		public void run () {
			try {
				JSch jsch=new JSch();
				session = jsch.getSession("root","192.168.1.203",22);
				java.util.Properties config = new java.util.Properties();
				config.put("StrictHostKeyChecking", "no");
				session.setConfig(config);
				session.setPassword("0000");
				session.connect();
				openChannel = (ChannelExec) session.openChannel("exec");
				
				
				
				
            } catch (JSchException  e) {
				result+=e.getMessage();
            }
			while (true)
			{	
				while (onClickCheck)
				{
					try{
						openChannel.setCommand(command);
						int exitstatus = openChannel.getExitStatus();
						System.out.println(exitstatus);
						openChannel.connect();
			            InputStream in = openChannel.getInputStream();  
			            BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
			            String buf = null;
			            while ((buf = reader.readLine()) != null) {
			            	result+= new String(buf.getBytes("gbk"),"UTF-8")+"    <br />rn";
			            }
					} catch (JSchException | IOException e) {
						result+=e.getMessage();
					}
				}
			}
		}
	}

	
}

