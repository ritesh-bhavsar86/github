package com.ritesh.expmgr.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class UserTable {

	// Database table
		public static final String TABLE_USER = "new_tbl_login";
		public static final String TABLE_MGR = "new_tbl_mgr";
		public static final String TABLE_TAGS = "new_tbl_tags";
		
		public static final String COLUMN_ID = "_id";
		public static final String COLUMN_USER = "lname";
		public static final String COLUMN_PWD = "passwd";
		
		public static final String COLUMN_CDATE = "curr_date";
		public static final String COLUMN_TAGS = "tags";
		public static final String COLUMN_COMMENTS = "comments";
		public static final String COLUMN_RS = "rs";
		public static final String COLUMN_RS_TYPE = "rs_type";
		public static final String COLUMN_TAG_COLOR = "tagColor";

		
		
		
		//public static final String COLUMN_DESCRIPTION = "description";

		// Database creation SQL statement
		private static final String DATABASE_CREATE = "create table " 
				+ TABLE_USER
				+ "(" + COLUMN_ID + " integer primary key autoincrement, " 
				+ COLUMN_USER + " text not null, " 
				+ COLUMN_PWD +  " text not null" + ");";

		// Database creation SQL statement
		private static final String TABLE_CREATE_MGR = "create table " 
						+ TABLE_MGR
						+ "(" + COLUMN_ID + " integer primary key autoincrement, " 
						+ COLUMN_CDATE + " text , "
						+ COLUMN_TAGS + " text , "
						+ COLUMN_COMMENTS + " text , "
						+ COLUMN_RS + " real , "
						+ COLUMN_RS_TYPE +  " text " + ");";

		
		private static final String TABLE_CREATE_TAGS = "create table " 
						+ TABLE_TAGS
						+ "(" + COLUMN_ID + " integer primary key autoincrement, " 
						+ COLUMN_TAGS +  " text, " 
						+ COLUMN_TAG_COLOR +  " integer );";

		public static void onCreate(SQLiteDatabase database) {
			database.execSQL(DATABASE_CREATE);
			database.execSQL(TABLE_CREATE_MGR);
			database.execSQL(TABLE_CREATE_TAGS);
		}

		public static void onUpgrade(SQLiteDatabase database, int oldVersion,
				int newVersion) {
			Log.w(UserTable.class.getName(), "Upgrading database from version "
					+ oldVersion + " to " + newVersion
					+ ", which will destroy all old data");
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_MGR);
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_TAGS);
			onCreate(database);
		}
}
