package com.ritesh.expmgr;



import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.ritesh.expmgr.database.DatabaseSetup;
import com.ritesh.expmgr.database.MgrDataSource;
import com.ritesh.expmgr.database.UserTable;

public class MgrList extends Activity{

	private MgrDataSource datasource;
	
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;
	private static final int DELETE_ID = Menu.FIRST + 1;
	// private Cursor cursor;
	private SimpleCursorAdapter adapter;

	ListView listView;
    public static final String fields[] = { DatabaseSetup.colName};
    Cursor cursor;
    TextView empty,currdate,tags,comments,rs,rs_type;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.mgr_list);
	//this.getListView().setDividerHeight(2);
	datasource = new MgrDataSource(this);
    datasource.open();


	//fillData();
	//registerForContextMenu(getListView());
	listView = (ListView) findViewById(R.id.list);
	empty=(TextView)findViewById(R.id.empty);
	currdate=(TextView)findViewById(R.id.currdate);
	tags=(TextView)findViewById(R.id.tags);
	comments=(TextView)findViewById(R.id.comments);
	rs=(TextView)findViewById(R.id.rs);
	rs_type=(TextView)findViewById(R.id.rs_type);
	
    DatabaseSetup.init(this); 

    fillData();

	}


	
	private void fillData() {
		// TODO Auto-generated method stub
		// Fields from the database (projection)
		// Must include the _id column for the adapter to work
		String[] from = new String[] { UserTable.COLUMN_CDATE, UserTable.COLUMN_TAGS, UserTable.COLUMN_COMMENTS, UserTable.COLUMN_RS, UserTable.COLUMN_RS_TYPE };
		// Fields on the UI to which we map
		int[] to = new int[] { R.id.currdate, R.id.tags, R.id.comments, R.id.rs, R.id.rs_type };
		Cursor c=datasource.getList();
		adapter = new SimpleCursorAdapter(this, R.layout.list_row, c, from,
				to, 0);
		
		listView.setAdapter(adapter);
		if(c.getCount()<=0){
			empty.setVisibility(View.VISIBLE);
			currdate.setVisibility(View.INVISIBLE);
			tags.setVisibility(View.INVISIBLE);
			comments.setVisibility(View.INVISIBLE);
			rs.setVisibility(View.INVISIBLE);
			rs_type.setVisibility(View.INVISIBLE);
			
		}else{
			empty.setVisibility(View.INVISIBLE);
			currdate.setVisibility(View.VISIBLE);
			tags.setVisibility(View.VISIBLE);
			comments.setVisibility(View.VISIBLE);
			rs.setVisibility(View.VISIBLE);
			rs_type.setVisibility(View.VISIBLE);
		}
		//setListAdapter(adapter);
		//c.close();
		
	}

/*
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		datasource.open();
		super.onResume();
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		datasource.close();
		super.onPause();
	}
	
	*/
	
}
