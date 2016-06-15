package embedded.tools.mrouter;

/*
 * 特别说明：
 * 此部分参考慧净电子小车控制软件源码
 * http://www.hjmcu.com/
 * 
 * */
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

@SuppressLint("NewApi")/*@SuppressLint("NewApi"）作用是屏蔽android lint错误。
												Android代码中，我们有时会使用比我们在AndroidManifest中设置的android:minSdkVersion版本更高的方法。
												此时编译器会提示警告。
											*/
public class PaintVedio extends SurfaceView implements Callback, Runnable {
	/*屏幕尺寸大小变量*/
	private static int screenWidth;//获得屏幕尺寸的宽度保存在这里
	private static int screenHeight;//获得屏幕尺寸的长度保存在这里
	/*连接与线程变量*/
	private boolean runFlag = false;//可以作为连接条件，暂时没有对此变量起实际作用。
	private static SurfaceHolder holder;//赋值实际的surfaceView holder地址，只是便于调用。
	private HttpURLConnection conn;//URL HTTP地址连接类，只能作为类变量。便于销毁View时关闭。
	private Thread thread;//访问资源并绘图的线程变量，在surfaceView状态改变时启用或关闭连接。

	public PaintVedio(Context context, AttributeSet attrs) {
		super(context, attrs);
		screenValue();//屏幕尺寸赋值
		holder = this.getHolder();
		holder.addCallback(this);//启用自带的回调函数
	}

	/**
	 * 获得屏幕像素值
	 */
	private void screenValue() {
		DisplayMetrics spixels = new DisplayMetrics();
		spixels = getResources().getDisplayMetrics();
		screenWidth = spixels.widthPixels;
		screenHeight = spixels.heightPixels;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		runFlag = true;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,int height) {
		runFlag = true;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		runFlag = false;
		conn.disconnect();
	}

	@Override
	public void run() {
		Canvas canvas;
		Bitmap bmp,aimbmp;
		Matrix matrix;
		InputStream photostream;
		URL videoURL = null;
		String imageURL = "http://10.15.33.47:82/?action=snapshot";//图片快照地址，其实获取过来的是一张张的图片。
		try {
			videoURL = new URL(imageURL);;	
		} catch (Exception e) {
		}
		/*绘图质量配置*/
		BitmapFactory.Options options = new BitmapFactory.Options();//配置原图缩放值
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;//高质量
		while (runFlag) {
			canvas = null;
			try {
				synchronized (holder) {
					canvas = holder.lockCanvas();//锁定画布，一般在锁定后就可以通过其返回的画布对象Canvas，在其上面画图等操作了。

				   //连接后的请求,与流都只能用一次,需要重新连接并获得流
				 	conn = (HttpURLConnection)videoURL.openConnection();//此方法会new HttpURLConnection并调用connect()
					//conn.connect();getInputStream()会自动调用此方法.此方法一般用在new HttpURLConnection之后.(new的时候没有发送连接请求)
					photostream = conn.getInputStream(); //获图片取流
					bmp = BitmapFactory.decodeStream(photostream, null, options);
					
					matrix = new Matrix();
					matrix.postRotate(90);//顺时针旋转90°
					aimbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);//新建一个旋转后的图片
					aimbmp = Bitmap.createScaledBitmap(aimbmp, screenWidth, screenHeight, true);//把图片根据屏幕尺寸进行缩放
					canvas.drawBitmap(aimbmp, 0, 0, null);
						
					//Thread.sleep(10);//间隔时间,建议把这个参数放进配置里.肉眼约能分别42毫秒间隔的图像.					
				}
			} catch (Exception e) {
				
			}finally {
				holder.unlockCanvasAndPost(canvas);//解锁画图并提交等待下一张图片的画图
				conn.disconnect();
			}
		}
	}
}
