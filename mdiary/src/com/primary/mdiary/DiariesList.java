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
		editor.putString("name","设置密码");
		//listrowtitle.setTypeface(typeface);
		//listrowcreated.setTypeface(typeface);
		
		Intent intent = getIntent();
		if (intent.getData() == null) {
			intent.setData(mDiariesColumns.WHICH_URI);
		}
		Cursor cursor = managedQuery(getIntent().getData(), PROJECTION, null,null, mDiariesColumns.SORT_ORDER);
		
		/* SimpleCursorAdapter允许你绑定一个游标的列到ListView上
		 * 并使用自定义的layout显示每个项目。
		 * SimpleCursorAdapter的创建，需要传入
		 * 当前的上下文、一个layout资源，一个游标和两个数组
		 * 两个数组：1使用的列的名字，2（相同大小）数组包含View中的资源ID用于显示相应列的数据值。
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
					.setTitle("请输入密码")
					//.setIcon(getResources().getDrawable(android.R.drawable.ic_lock_lock))
					.setView(textEntry)
					.setCancelable(false)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									checkPass = (EditText) textEntry
											.findViewById(R.id.checkpasswd_edittext);
									if (checkPass.getText().toString().trim()
											.equals(password)) {
										// 关闭对话框
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
										// 对话框不消失，toast提示密码错误
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
												"密码错误，请重新输入！",
												Toast.LENGTH_LONG).show();
										// 这里不要忘记把密码重新设置为空
										checkPass.setText("");
									}
								}
							});
			bulider.create().show();
		}
	}
	
	
	
	
	
    /*添加选择菜单*/
    public boolean onPrepareOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.clear();
		menu.add(Menu.NONE, MENU_ITEM_INSERT, 0, R.string.menu_insert);
		menu.add(Menu.NONE, MENU_ITEM_PASSWD, 0, preferences.getString("name", "设置密码"));
		return true;
	}
    /*添加选择菜单的选择事件*/
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		/*插入一条数据*/
		case MENU_ITEM_INSERT:
			Intent intent0 = new Intent(this, DiaryEditor.class);
			intent0.setAction(DiaryEditor.INSERT_DIARY_ACTION);
			intent0.setData(getIntent().getData());
			startActivity(intent0);
			return true;

			/*删除当前数据*/
			
		case MENU_ITEM_PASSWD:
			//if (getString(R.string.menu_passwd)=="设置密码")
			if (preferences.getString("name", "设置密码").equals("设置密码"))
			{
			LayoutInflater factory=LayoutInflater.from(DiariesList.this);
			final View textEntry=factory.inflate(R.layout.setpasswdview, null);
			AlertDialog.Builder builder=new AlertDialog.Builder(DiariesList.this)
			.setTitle("密码设置")
			.setView(textEntry)
			.setPositiveButton("设置", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					setPass=(EditText)textEntry.findViewById(R.id.setpass_edittext);
					confirmPass=(EditText)textEntry.findViewById(R.id.confirmpass_edittext);
					if (!confirmPass.getText().toString().trim().equals("")&&
							confirmPass.getText().toString().trim().equals(setPass.getText().toString().trim())) {
						//preferences=getSharedPreferences("pass", Context.MODE_PRIVATE);
						//Editor editor=preferences.edit();
						editor.putBoolean("isSet", !isSet);
						editor.putString("password", setPass.getText().toString().trim());
						editor.putString("name","撤销密码");
						editor.commit();
						dialog.dismiss();
						Toast.makeText(DiariesList.this, "密码设置成功！", Toast.LENGTH_LONG).show();
					}
					else {
						Toast.makeText(DiariesList.this,  "两次输入密码不一致，请重新输入！", Toast.LENGTH_LONG).show();
					}
				}
			})
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
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
			editor.putString("name","设置密码");
			editor.commit();
			//onBackPressed();
			Toast.makeText(DiariesList.this, "已成功取消密码！", Toast.LENGTH_LONG).show();
			
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
