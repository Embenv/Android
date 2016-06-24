package com.primary.mdiary;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.primary.mdiary.mDiaryAuthorityFields.mDiariesColumns;

public class DiaryEditor extends Activity {

	/*
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diaryeditorview);
	}
	*/
	private static final String TAG = "Diary";
	public static final String EDIT_DIARY_ACTION = "com.primary.mdiary.DiaryEditor.EDIT_DIARY";
	public static final String INSERT_DIARY_ACTION = "com.primary.mdiary.DiaryEditor.action.INSERT_DIARY";
	
	/*
	 * 查询cursor时候，感兴趣的那些条例。
	 */
	private static final String[] PROJECTION  = new String[] { mDiariesColumns._ID, mDiariesColumns.TITLE, mDiariesColumns.BODY, };

	private static final int STATE_EDIT = 0;
	private static final int STATE_INSERT = 1;
	private int mState;
	private Uri mUri;
	private Cursor mCursor;
	private EditText mTitleText;
	private EditText mBodyText;
	private Button confirmButton;
	private Button backButton;
	
	
	
	
	public static final int MENU_ITEM_DELETE = Menu.FIRST;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diaryeditorview);	

		setTheme(android.R.style.Theme_Black);
		
		final Intent intent = getIntent();
		final String action = intent.getAction();
		
		//Typeface typeface =Typeface.createFromAsset(getAssets(), "typeface1.ttf");
		
		mTitleText = (EditText) findViewById(R.id.diaryeditor_title);
		mBodyText = (EditText) findViewById(R.id.diaryeditor_body);
		confirmButton = (Button) findViewById(R.id.confirm_button);
		backButton = (Button) findViewById(R.id.back_button);


		if (EDIT_DIARY_ACTION.equals(action)) {
			mState = STATE_EDIT;
			mUri = intent.getData();
			mCursor = managedQuery(mUri, PROJECTION, null, null, null);
			mCursor.moveToFirst();
			
			String title = mCursor.getString(1);
			mTitleText.setTextKeepState(title);
			String body = mCursor.getString(2);
			mBodyText.setTextKeepState(body);
			
			setResult(RESULT_OK, (new Intent()).setAction(mUri.toString()));
			setTitle("编辑日记");
			
		}
		else
			if (INSERT_DIARY_ACTION.equals(action)) {
				mState = STATE_INSERT;
				setTitle("新建日记");
			}
			else {
				Log.e(TAG, "no such action error!");
				finish();
				return;
			}
		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				if (mState == STATE_INSERT) {
					insertDiary();
				} else {
					updateDiary();
				}
				Intent mIntent = new Intent();
				setResult(RESULT_OK, mIntent);
				finish();
			}
		});
		backButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
		
				//Intent intent = new Intent();
				//intent.setClass(DiaryEditor.this, DiariesList.class);
				//startActivity(intent);
				finish();
			}
		});
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, MENU_ITEM_DELETE, 0, R.string.menu_delete);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
			getContentResolver().delete(mUri, null, null);
			finish();
			return true;
		}

	private void insertDiary() {
		String title = mTitleText.getText().toString();
		String body = mBodyText.getText().toString();
		ContentValues values = new ContentValues();
		values.put(mDiariesColumns.CREATED, mDiaryContentProvider.getFormateCreatedDate());
		values.put(mDiariesColumns.TITLE, title);
		values.put(mDiariesColumns.BODY, body);
		getContentResolver().
		insert(mDiariesColumns.WHICH_URI, values);

	}

	private void updateDiary() {
		String title = mTitleText.getText().toString();
		String body = mBodyText.getText().toString();
		ContentValues values = new ContentValues();
		values.put(mDiariesColumns.CREATED, mDiaryContentProvider.getFormateCreatedDate());
		values.put(mDiariesColumns.TITLE, title);
		values.put(mDiariesColumns.BODY, body);
		getContentResolver().
		update(mUri, values,null, null);
	}
}
