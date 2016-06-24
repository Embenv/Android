package com.primary.mdiary;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

import com.primary.mdiary.mDiaryAuthorityFields.mDiariesColumns;

public class DiariesList extends ListActivity {
	
	private boolean isSet = false;
	private EditText setPass=null;
	private EditText confirmPass=null;
	private EditText checkPass=null;
	
	private SharedPreferences preferences;
	
	Editor editor;
	
	public static final int MENU_ITEM_INSERT = Menu.FIRST;
	public static final int MENU_ITEM_PASSWD = Menu.FIRST + 1;
	private static final String[] PROJECTION = new String[] { mDiariesColumns._ID,mDiariesColumns.TITLE, mDiariesColumns.CREATED };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diarieslistview);
		
		//View view = View.inflate(getApplicationContext(), R.layout.listrowview, null);
		
		//Typeface typeface =Typeface.createFromAsset(getAssets(), "typeface1.ttf");
		
		//TextView listrowtitle =(TextView) view.findViewById(R.id.listrow_title);
		//TextView listrowcreated =(TextView) view.findViewById(R.id.listrow_created);
		
		private SharedPreferences preferences;
		preferences=getSharedPreferences("pass", Context.MODE_PRIVATE);
		editor=preferences.edit();
		editor.putString("name","��������");
		//listrowtitle.setTypeface(typeface);
		//listrowcreated.setTypeface(typeface);
		
		Intent intent = getIntent();
		if (intent.getData() == null) {
			intent.setData(mDiariesColumns.WHICH_URI);
		}
		Cursor cursor = managedQuery(getIntent().getData(), PROJECTION, null,null, mDiariesColumns.SORT_ORDER);
		
		/* SimpleCursorAdapter�������һ���α���е�ListView��
		 * ��ʹ���Զ����layout��ʾÿ����Ŀ��
		 * SimpleCursorAdapter�Ĵ�������Ҫ����
		 * ��ǰ�������ġ�һ��layout��Դ��һ���α����������
		 * �������飺1ʹ�õ��е����֣�2����ͬ��С���������View�е���ԴID������ʾ��Ӧ�е�����ֵ��
		 */
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.listrowview, cursor, new String[]
				{mDiariesColumns.TITLE,mDiariesColumns.CREATED }, 
				new int[] {R.id.listrow_title,R.id.listrow_created });
		setListAdapter(adapter);
		
		checkPass();
    }
	
	
	private void checkPass() {
		final String password = preferences.getString("password", null);
		boolean check = preferences.getBoolean("isSet", false);
		if (check) {
			LayoutInflater factory = LayoutInflater.from(DiariesList.this);
			final View textEntry = factory.inflate(R.layout.checkpasswdview, null);
			AlertDialog.Builder bulider = new AlertDialog.Builder(this)
					.setTitle("����������")
					//.setIcon(getResources().getDrawable(android.R.drawable.ic_lock_lock))
					.setView(textEntry)
					.setCancelable(false)
					.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									checkPass = (EditText) textEntry
											.findViewById(R.id.checkpasswd_edittext);
									if (checkPass.getText().toString().trim()
											.equals(password)) {
										// �رնԻ���
										try {
											Field field = dialog
													.getClass()
													.getSuperclass()
													.getDeclaredField(
															"mShowing");
											field.setAccessible(true);
											field.set(dialog, true);
										} catch (Exception e) {
											e.printStackTrace();
										}
										dialog.dismiss();
									} else {
										// �Ի�����ʧ��toast��ʾ�������
										try {
											Field field = dialog
													.getClass()
													.getSuperclass()
													.getDeclaredField(
															"mShowing");
											field.setAccessible(true);
											field.set(dialog, false);
										} catch (Exception e) {
											e.printStackTrace();
										}
										Toast.makeText(DiariesList.this,
												"����������������룡",
												Toast.LENGTH_LONG).show();
										// ���ﲻҪ���ǰ�������������Ϊ��
										checkPass.setText("");
									}
								}
							});
			bulider.create().show();
		}
	}
	
	
	
	
	
    /*���ѡ��˵�*/
    public boolean onPrepareOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.clear();
		menu.add(Menu.NONE, MENU_ITEM_INSERT, 0, R.string.menu_insert);
		menu.add(Menu.NONE, MENU_ITEM_PASSWD, 0, preferences.getString("name", "��������"));
		return true;
	}
    /*���ѡ��˵���ѡ���¼�*/
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/*����һ������*/
		case MENU_ITEM_INSERT:
			Intent intent0 = new Intent(this, DiaryEditor.class);
			intent0.setAction(DiaryEditor.INSERT_DIARY_ACTION);
			intent0.setData(getIntent().getData());
			startActivity(intent0);
			return true;

			/*ɾ����ǰ����*/
			
		case MENU_ITEM_PASSWD:
			//if (getString(R.string.menu_passwd)=="��������")
			if (preferences.getString("name", "��������").equals("��������"))
			{
			LayoutInflater factory=LayoutInflater.from(DiariesList.this);
			final View textEntry=factory.inflate(R.layout.setpasswdview, null);
			AlertDialog.Builder builder=new AlertDialog.Builder(DiariesList.this)
			.setTitle("��������")
			.setView(textEntry)
			.setPositiveButton("����", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					setPass=(EditText)textEntry.findViewById(R.id.setpass_edittext);
					confirmPass=(EditText)textEntry.findViewById(R.id.confirmpass_edittext);
					if (!confirmPass.getText().toString().trim().equals("")&&
							confirmPass.getText().toString().trim().equals(setPass.getText().toString().trim())) {
						//preferences=getSharedPreferences("pass", Context.MODE_PRIVATE);
						//Editor editor=preferences.edit();
						editor.putBoolean("isSet", !isSet);
						editor.putString("password", setPass.getText().toString().trim());
						editor.putString("name","��������");
						editor.commit();
						dialog.dismiss();
						Toast.makeText(DiariesList.this, "�������óɹ���", Toast.LENGTH_LONG).show();
					}
					else {
						Toast.makeText(DiariesList.this,  "�����������벻һ�£����������룡", Toast.LENGTH_LONG).show();
					}
				}
			})
			.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			});
			builder.create().show();
			//R.string.menu_passwd
			}
			else
			{
			
			//preferences=getSharedPreferences("pass", Context.MODE_PRIVATE);
			//Editor editor=preferences.edit();
			editor.putBoolean("isSet", false);
			editor.putString("name","��������");
			editor.commit();
			//onBackPressed();
			Toast.makeText(DiariesList.this, "�ѳɹ�ȡ�����룡", Toast.LENGTH_LONG).show();
			
			}
			
			return true;

		}
		return super.onOptionsItemSelected(item);
	} 
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Uri uri = ContentUris.withAppendedId(getIntent().getData(), id);
		startActivity(new Intent(DiaryEditor.EDIT_DIARY_ACTION, uri));
	}

	protected void onActivityResult(int requestCode, int resultCode,Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		//renderListView();
	}
	private void renderListView() {
		Cursor cursor = managedQuery(getIntent().getData(), PROJECTION,null,null, mDiariesColumns.SORT_ORDER);

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
			R.layout.diaryeditorview, cursor, new String[] { mDiariesColumns.TITLE,
			mDiariesColumns.CREATED }, new int[] { R.id.listrow_title,R.id.listrow_created });
		setListAdapter(adapter);
	}

		
}
