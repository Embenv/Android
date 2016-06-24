package com.primary.mdiary;

import java.util.Calendar;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.primary.mdiary.mDiaryAuthorityFields.mDiariesColumns;

public class mDiaryContentProvider extends ContentProvider {
	
	private static final String MDIARYDB_NAME = "database";
	private static final int MDIARYDB_VERSION = 3;
	private static final String MDIARYTABLE_NAME = "diary";
	private static final int ALLDIARIES = 1;
	private static final int EACHDIARY= 2;
	
	/*Universal Resource Identifier统一资源标识符
	 *matcher匹配程序
	 */
	private static final UriMatcher mDbUriMatcher;
	static {
		/*把需要匹配的路径全部注册上
		 * Uri代表要操作的数据
		 * addURI第三个参数为匹配码
		 */
		mDbUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mDbUriMatcher.addURI(mDiaryAuthorityFields.AUTHORITY, "diaries", ALLDIARIES);    //全部数据请求
		mDbUriMatcher.addURI(mDiaryAuthorityFields.AUTHORITY, "diaries/#", EACHDIARY);//单一数据请求
	}
	
	/*辅助类DatabaseHelper
	 * DatabaseHelper继承SQLiteOpenHelper
	 * SQLiteOpenHelper是一个抽象类，有3个函数 onCreate、onUpdate、onOpen
	 */
	private DatabaseHelper mDbOpenHelper;
	private static class DatabaseHelper extends SQLiteOpenHelper {

		DatabaseHelper(Context context) {
			super(context, MDIARYDB_NAME, null, MDIARYDB_VERSION);       //调用父类的构造方法
			
			Log.i("log: ", "DATABASE NAME" + MDIARYDB_NAME + "&" + "DATABASE VERSION=" + MDIARYDB_VERSION);
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.i("log: ", "onCreate(SQLiteDatabase db)");
			String sql = "CREATE TABLE " + MDIARYTABLE_NAME + " ("
					+ mDiariesColumns._ID + " INTEGER PRIMARY KEY,"
					+ mDiariesColumns.TITLE + " TEXT," + mDiariesColumns.BODY
					+ " TEXT," + mDiariesColumns.CREATED + " TEXT" + ");";

			sql ="CREATE TABLE " + MDIARYTABLE_NAME + " ("
			+ mDiariesColumns._ID + " INTEGER PRIMARY KEY,"
			+ mDiariesColumns.TITLE + " varchar(255)," + mDiariesColumns.BODY
			+ " TEXT," + mDiariesColumns.CREATED + " TEXT" + ");";

			Log.i("log: ", "sql="+sql);
			db.execSQL(sql); 		
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.i("log: ", " onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)=" + newVersion);
			db.execSQL("DROP TABLE IF EXISTS DIARY!");
			onCreate(db);
		}
		
	}
	
	public static String getFormateCreatedDate() {
		Calendar calendar = Calendar.getInstance();
		String created = calendar.get(Calendar.YEAR) + "."
				+ calendar.get(Calendar.MONTH) + "."
				+ calendar.get(Calendar.DAY_OF_MONTH) + "	"
				+ calendar.get(Calendar.HOUR_OF_DAY) + ":"
				+ calendar.get(Calendar.MINUTE);
		return created;
	}
	
	@Override
	public boolean onCreate() {
		mDbOpenHelper = new DatabaseHelper(getContext());
		return true;
	}
	/*query查询
	 * Cursor游标
	 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		
		/*SQLiteQueryBuilder是一个构造sql查询语句的辅助类*/
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		
		/*UriMatcher.match(uri)根据返回值可以判断这次查询请求时
		 *他是请求的全部数据还是某个id的数据
		 */
		switch (mDbUriMatcher.match(uri)) {
		case ALLDIARIES:
			qb.setTables(MDIARYTABLE_NAME);
			break;
		case EACHDIARY:
			qb.setTables(MDIARYTABLE_NAME);
			qb.appendWhere(mDiariesColumns._ID + "="
					+ uri.getPathSegments().get(1));
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		String orderBy;
		if (TextUtils.isEmpty(sortOrder)) {
			orderBy = mDiariesColumns.SORT_ORDER;
		} else {
			orderBy = sortOrder;
		}
		
		SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
		return c;
		
	}
	
	/*返回一个给Uri制定数据的MIME类型
	 */
	public String getType(Uri uri) {
		switch (mDbUriMatcher.match(uri)) {
		case ALLDIARIES:
			return mDiariesColumns.THROUGH_DIR;

		case EACHDIARY:
			return mDiariesColumns.THROUGH_ITEM;

		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {

		if (mDbUriMatcher.match(uri) != ALLDIARIES) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		} else {
			values = new ContentValues();
		}

		if (values.containsKey(mDiariesColumns.CREATED) == false) {
			values.put(mDiariesColumns.CREATED, getFormateCreatedDate());
		}
		
		if (values.containsKey(mDiariesColumns.TITLE) == false) {
			Resources res = Resources.getSystem();
			values.put(mDiariesColumns.TITLE, res.getString(android.R.string.untitled));//R.string.untitled***应该是未命名标题
		}

		if (values.containsKey(mDiariesColumns.BODY) == false) {
			values.put(mDiariesColumns.BODY, "");
		}
		

		
		/*得到一个SQLiteDatabase的实例*/
		SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
		
		long columnId = db.insert(MDIARYTABLE_NAME, mDiariesColumns.BODY, values);//插入一条记录到数据库当中
		if (columnId > 0) {
			Uri thisDiaryUri = ContentUris.withAppendedId(mDiariesColumns.WHICH_URI, columnId);//注意：insert（）返回的是一个uri，而不是一个记录id.
			
			return thisDiaryUri;
		}

		throw new SQLException("Failed to insert column into " + uri);
	}
	
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		
		SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
		String columnId = uri.getPathSegments().get(1);

		return db.delete(MDIARYTABLE_NAME, mDiariesColumns._ID  + "=" + columnId, null);
	}

	@Override
	public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {

		SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
		String columnId = uri.getPathSegments().get(1);

		return db.update(MDIARYTABLE_NAME, values, mDiariesColumns._ID  + "="+ columnId, null);
	}

}
