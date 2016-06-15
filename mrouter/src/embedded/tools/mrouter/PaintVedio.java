package embedded.tools.mrouter;

/*
 * �ر�˵����
 * �˲��ֲο��۾�����С���������Դ��
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

@SuppressLint("NewApi")/*@SuppressLint("NewApi"������������android lint����
												Android�����У�������ʱ��ʹ�ñ�������AndroidManifest�����õ�android:minSdkVersion�汾���ߵķ�����
												��ʱ����������ʾ���档
											*/
public class PaintVedio extends SurfaceView implements Callback, Runnable {
	/*��Ļ�ߴ��С����*/
	private static int screenWidth;//�����Ļ�ߴ�Ŀ�ȱ���������
	private static int screenHeight;//�����Ļ�ߴ�ĳ��ȱ���������
	/*�������̱߳���*/
	private boolean runFlag = false;//������Ϊ������������ʱû�жԴ˱�����ʵ�����á�
	private static SurfaceHolder holder;//��ֵʵ�ʵ�surfaceView holder��ַ��ֻ�Ǳ��ڵ��á�
	private HttpURLConnection conn;//URL HTTP��ַ�����ֻ࣬����Ϊ���������������Viewʱ�رա�
	private Thread thread;//������Դ����ͼ���̱߳�������surfaceView״̬�ı�ʱ���û�ر����ӡ�

	public PaintVedio(Context context, AttributeSet attrs) {
		super(context, attrs);
		screenValue();//��Ļ�ߴ縳ֵ
		holder = this.getHolder();
		holder.addCallback(this);//�����Դ��Ļص�����
	}

	/**
	 * �����Ļ����ֵ
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
		String imageURL = "http://10.15.33.47:82/?action=snapshot";//ͼƬ���յ�ַ����ʵ��ȡ��������һ���ŵ�ͼƬ��
		try {
			videoURL = new URL(imageURL);;	
		} catch (Exception e) {
		}
		/*��ͼ��������*/
		BitmapFactory.Options options = new BitmapFactory.Options();//����ԭͼ����ֵ
		options.inPreferredConfig = Bitmap.Config.ARGB_8888;//������
		while (runFlag) {
			canvas = null;
			try {
				synchronized (holder) {
					canvas = holder.lockCanvas();//����������һ����������Ϳ���ͨ���䷵�صĻ�������Canvas���������滭ͼ�Ȳ����ˡ�

				   //���Ӻ������,������ֻ����һ��,��Ҫ�������Ӳ������
				 	conn = (HttpURLConnection)videoURL.openConnection();//�˷�����new HttpURLConnection������connect()
					//conn.connect();getInputStream()���Զ����ô˷���.�˷���һ������new HttpURLConnection֮��.(new��ʱ��û�з�����������)
					photostream = conn.getInputStream(); //��ͼƬȡ��
					bmp = BitmapFactory.decodeStream(photostream, null, options);
					
					matrix = new Matrix();
					matrix.postRotate(90);//˳ʱ����ת90��
					aimbmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);//�½�һ����ת���ͼƬ
					aimbmp = Bitmap.createScaledBitmap(aimbmp, screenWidth, screenHeight, true);//��ͼƬ������Ļ�ߴ��������
					canvas.drawBitmap(aimbmp, 0, 0, null);
						
					//Thread.sleep(10);//���ʱ��,�������������Ž�������.����Լ�ֱܷ�42��������ͼ��.					
				}
			} catch (Exception e) {
				
			}finally {
				holder.unlockCanvasAndPost(canvas);//������ͼ���ύ�ȴ���һ��ͼƬ�Ļ�ͼ
				conn.disconnect();
			}
		}
	}
}
