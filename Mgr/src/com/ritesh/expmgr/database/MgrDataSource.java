package com.ritesh.expmgr.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MgrDataSource {

	// Database fields
	  private SQLiteDatabase database;
	  private DatabaseHelper dbHelper;
	  private String[] allColumns = { UserTable.COLUMN_ID, UserTable.COLUMN_CDATE, UserTable.COLUMN_TAGS,
	      UserTable.COLUMN_COMMENTS, UserTable.COLUMN_RS, UserTable.COLUMN_RS_TYPE};
	  private String[] allColumns_Tags = { UserTable.COLUMN_ID, UserTable.COLUMN_TAGS, UserTable.COLUMN_TAG_COLOR};
	  private String[] allColumns_Users = { UserTable.COLUMN_ID, UserTable.COLUMN_USER, UserTable.COLUMN_PWD};

	  public MgrDataSource() {
		// TODO Auto-generated constructor stub
	  }
	  
	  public MgrDataSource(Context context) {
		    dbHelper = new DatabaseHelper(context);
	  }
	  
	  public void open() throws SQLException {
		    database = dbHelper.getWritableDatabase();
	  }
	  
	  public void close() {
		    dbHelper.close();
		
	  }

	  public ExpManager createManager(String curr_date, String tags, String comments,Double in,String out) {
		    ContentValues values = new ContentValues();
		    values.put(UserTable.COLUMN_CDATE, curr_date);
		    values.put(UserTable.COLUMN_TAGS, tags);
		    values.put(UserTable.COLUMN_COMMENTS, comments);
		    values.put(UserTable.COLUMN_RS, in);
		    values.put(UserTable.COLUMN_RS_TYPE, out);
		    long insertId = database.insert(UserTable.TABLE_MGR, null,
		        values);
		    
		    Cursor cursor = database.query(UserTable.TABLE_MGR,
		        allColumns, UserTable.COLUMN_ID + " = " + insertId, null,
		        null, null, null);
		    cursor.moveToFirst();
		    ExpManager expMgr = cursorToExpMgr(cursor);
		    cursor.close();
		    return expMgr;
	  }

	  public Table_Tags createTags(String tags, int tagColor) {
		    ContentValues values = new ContentValues();
		    values.put(UserTable.COLUMN_TAGS, tags);
		    values.put(UserTable.COLUMN_TAG_COLOR, tagColor);
		    
		    long insertId = database.insert(UserTable.TABLE_TAGS, null,
		        values);
		    
		    Cursor cursor = database.query(UserTable.TABLE_TAGS,
		        allColumns_Tags, UserTable.COLUMN_ID + " = " + insertId, null,
		        null, null, null);
		    cursor.moveToFirst();
		    Table_Tags expMgr = cursorToTableTags(cursor);
		    cursor.close();
		    return expMgr;
	  }

	  
	  public Users createUsers(String uname,String pwd) {
		    ContentValues values = new ContentValues();
		    values.put(UserTable.COLUMN_USER, uname);
		    values.put(UserTable.COLUMN_PWD, pwd);
		    long insertId = database.insert(UserTable.TABLE_USER, null,
		        values);
		    
		    Cursor cursor = database.query(UserTable.TABLE_USER,
		        allColumns_Users, UserTable.COLUMN_ID + " = " + insertId, null,
		        null, null, null);
		    cursor.moveToFirst();
		    Users expMgr = cursorToUsers(cursor);
		    cursor.close();
		    return expMgr;
	  }
	private Users cursorToUsers(Cursor cursor) {
		// TODO Auto-generated method stub
		Users mgr = new Users();
	    mgr.setId(cursor.getInt(0));
	    mgr.setUname(cursor.getString(1));
	    mgr.setPwd(cursor.getString(2));
	    return mgr;
	}

	private Table_Tags cursorToTableTags(Cursor cursor) {
		// TODO Auto-generated method stub
		Table_Tags mgr = new Table_Tags();
	    mgr.setId(cursor.getInt(0));
	    mgr.setTags(cursor.getString(1));
	    mgr.setTagColor(cursor.getInt(2));
	    return mgr;

	}

	private ExpManager cursorToExpMgr(Cursor cursor) {
		// TODO Auto-generated method stub
		ExpManager mgr = new ExpManager();
	    mgr.setId(cursor.getInt(0));
	    mgr.setCurr_date(cursor.getString(1));
	    mgr.setTags(cursor.getString(2));
	    mgr.setComments(cursor.getString(3));
	    mgr.setRs(cursor.getDouble(4));
	    mgr.setRs_type(cursor.getString(5));
	    return mgr;

	}

	public void deleteManager(ExpManager mgr) {
	    int id = mgr.getId();
	    System.out.println("manager deleted with id: " + id);
	    database.delete(UserTable.TABLE_MGR, UserTable.COLUMN_ID
	        + " = " + id, null);
	  }

	public void deleteTags(Table_Tags mgr) {
	    int id = mgr.getId();
	    System.out.println("tags deleted with id: " + id);
	    database.delete(UserTable.TABLE_TAGS, UserTable.COLUMN_ID
	        + " = " + id, null);
	  }
	public void deleteTagsBytagName(String tag) {
	    database.delete(UserTable.TABLE_TAGS, UserTable.COLUMN_TAGS + " = " + tag, null);
	  }
	
	public List<ExpManager> getAllComments() {
	    List<ExpManager> mgr_list = new ArrayList<ExpManager>();

	    Cursor cursor = database.query(UserTable.TABLE_MGR,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      ExpManager expMgr = cursorToExpMgr(cursor);
	      mgr_list.add(expMgr);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return mgr_list;
	  }

	  public Cursor getList() {
		// Select All Query
		  String selectQuery = "SELECT  * FROM " + UserTable.TABLE_MGR;
		  Cursor cursor = database.rawQuery(selectQuery, null);
		   // Cursor cursor = database.query(UserTable.TABLE_MGR,
		  //allColumns, null, null, null, null, null);

		    //cursor.close();
		    return cursor;
		  }

	  public Cursor getList_Tags() {
			// Select All Query
			  /*String selectQuery = "SELECT  * FROM " + UserTable.TABLE_TAGS;
			  Cursor cursor = database.rawQuery(selectQuery, null);*/
			   // Cursor cursor = database.query(UserTable.TABLE_MGR,
			  //allColumns, null, null, null, null, null);

			    //cursor.close();
		  		Cursor cursor = database.query(UserTable.TABLE_TAGS,
			        allColumns_Tags, null, null, UserTable.COLUMN_TAGS, null, null);
			    return cursor;
			  }

		public List<Table_Tags> getAll_Tags() {
		    List<Table_Tags> mgr_list = new ArrayList<Table_Tags>();

		    Cursor cursor = database.query(UserTable.TABLE_TAGS,
		        allColumns_Tags, null, null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
		      Table_Tags expMgr = cursorToTableTags(cursor);
		      mgr_list.add(expMgr);
		      cursor.moveToNext();
		    }
		    // make sure to close the cursor
		    cursor.close();
		    return mgr_list;
		  }
		
		  public int getCount_Tags() {
				// Select All Query
				  String selectQuery = "SELECT  * FROM " + UserTable.TABLE_TAGS;
				  Cursor cursor = database.rawQuery(selectQuery, null);
					    //cursor.close();
			    return cursor.getCount();
		  }
		  public List<Double> getListTotRupeesByTags() {
			    List<Double> rupee_list = new ArrayList<Double>();

			    Cursor cursor = database.query(UserTable.TABLE_TAGS,
			        allColumns_Tags, null, null, UserTable.COLUMN_TAGS, null, null);

			    cursor.moveToFirst();
			    while (!cursor.isAfterLast()) {
			      Table_Tags tbl_tags = cursorToTableTags(cursor);
			      String tag_tag=tbl_tags.getTags();
			      
			      String selectQuery = "SELECT sum("+ UserTable.COLUMN_RS +") FROM " + UserTable.TABLE_MGR 
			    		  +" where "+ UserTable.COLUMN_TAGS +"='"+tag_tag+"'";
			      Cursor cursor_tag = database.rawQuery(selectQuery, null);
			      int cnt=cursor_tag.getCount();
			      int cntclos=cursor_tag.getColumnCount();
			      cursor_tag.moveToFirst();
			      try{
			    	  int k=cursor_tag.getColumnIndex("sum(rs)");
			    	  int j=cursor_tag.getType(0);
			    	  String str=cursor_tag.getString(0);
			    	  String strrs=cursor_tag.getString(cursor_tag.getColumnIndex("sum(rs)"));
			    	  rupee_list.add(Double.parseDouble(strrs));
			      }catch(Exception e){
			    	  
			      }
			      cursor.moveToNext();
			    }
			    // make sure to close the cursor
			    cursor.close();
			    return rupee_list;
			  }

		  public Double getTotRupeesByTags(String strTag) {
			  Double str = null;
			  String selectQuery = "SELECT sum("+ UserTable.COLUMN_RS +") FROM " + UserTable.TABLE_MGR 
			    		  +" where "+ UserTable.COLUMN_TAGS +"='"+strTag+"' and "+ UserTable.COLUMN_RS_TYPE +"='OUT'";
			      
			  Cursor cursor_tag = database.rawQuery(selectQuery, null);
			  int cnt=cursor_tag.getCount();
			  int cntclos=cursor_tag.getColumnCount();
			  cursor_tag.moveToFirst();
			  try{
				  //int k=cursor_tag.getColumnIndex("sum(rs)");
				  //int j=cursor_tag.getType(0);
				  str=cursor_tag.getDouble(0);
				  //String strrs=cursor_tag.getString(cursor_tag.getColumnIndex("sum(rs)"));
				  }catch(Exception e){
			  }
			  return str;
		  }
		  
		  public Double getTotRupees() {
			  Double str = null;
			  String selectQuery = "SELECT sum("+ UserTable.COLUMN_RS +") FROM " + UserTable.TABLE_MGR //+""; 
			    		  +" where "+ UserTable.COLUMN_RS_TYPE +"='OUT'";
			      
			  Cursor cursor_tag = database.rawQuery(selectQuery, null);
			  int cnt=cursor_tag.getCount();
			  int cntclos=cursor_tag.getColumnCount();
			  cursor_tag.moveToFirst();
			  try{
				  //int k=cursor_tag.getColumnIndex("sum(rs)");
				  //int j=cursor_tag.getType(0);
				  str=cursor_tag.getDouble(0);
				  //String strrs=cursor_tag.getString(cursor_tag.getColumnIndex("sum(rs)"));
				  }catch(Exception e){
			  }
			  return str;
		  }
		  public int getColorByTags(String strTag) {
			  int str = 0;
			  String selectQuery = "SELECT "+ UserTable.COLUMN_TAG_COLOR +" FROM " + UserTable.TABLE_TAGS 
			    		  +" where "+ UserTable.COLUMN_TAGS +"='"+strTag+"'";
			      
			  Cursor cursor_tag = database.rawQuery(selectQuery, null);
			  int cnt=cursor_tag.getCount();
			  int cntclos=cursor_tag.getColumnCount();
			  cursor_tag.moveToFirst();
			  try{
				  //int k=cursor_tag.getColumnIndex("sum(rs)");
				  //int j=cursor_tag.getType(0);
				  str=cursor_tag.getInt(0);
				  //String strrs=cursor_tag.getString(cursor_tag.getColumnIndex("sum(rs)"));
				  }catch(Exception e){
			  }
			  return str;
		  }
		  
		  public List<String> getTagsGroupBy() {
			  List<String> rupee_list = new ArrayList<String>();
			  Cursor cursor = database.query(UserTable.TABLE_TAGS,
			        allColumns_Tags, null, null, UserTable.COLUMN_TAGS, null, null);
			    cursor.moveToFirst();
			    while (!cursor.isAfterLast()) {
			      Table_Tags tbl_tags = cursorToTableTags(cursor);
			      String tag_tag=tbl_tags.getTags();
			      rupee_list.add(tag_tag);
			      cursor.moveToNext();
			    }	
			  return rupee_list;
		  }
		  public int getCountTagsGroupBy() {
			  Cursor cursor = database.query(UserTable.TABLE_TAGS,
			        allColumns_Tags, null, null, UserTable.COLUMN_TAGS, null, null);
			    cursor.moveToFirst();
			  return cursor.getCount();
		  }
		  
		  public boolean isTagExists(String tag) {
			  Cursor cursor = database.query(UserTable.TABLE_TAGS,
			        allColumns_Tags, UserTable.COLUMN_TAGS + " = '" + tag +"'", null, null, null, null);
			    cursor.moveToFirst();
			  if(cursor.getCount() < 1){
				  return false;
			  }else{
				  return true;
			  }
			  
		  }
		  
		  public boolean updateTags(String tags, int tagColor) {
			    ContentValues values = new ContentValues();
			    //values.put(UserTable.COLUMN_TAGS, tags);
			    values.put(UserTable.COLUMN_TAG_COLOR, tagColor);
			    
			    int i = database.update(UserTable.TABLE_TAGS, values, UserTable.COLUMN_TAGS + " = '" + tags +"'", null);
			    return i>0;
			    
			    
			    /*Cursor cursor = database.query(UserTable.TABLE_TAGS,
			        allColumns_Tags, UserTable.COLUMN_ID + " = " + insertId, null,
			        null, null, null);
			    cursor.moveToFirst();
			    Table_Tags expMgr = cursorToTableTags(cursor);
			    cursor.close();
			    return expMgr;*/
		  }
		  
		  

			  
}
		  
