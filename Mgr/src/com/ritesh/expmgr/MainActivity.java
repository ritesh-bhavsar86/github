package com.ritesh.expmgr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ritesh.expmgr.database.DatabaseSetup;
import com.ritesh.expmgr.database.ExpManager;
import com.ritesh.expmgr.database.MgrDataSource;
import com.ritesh.expmgr.database.Table_Tags;

public class MainActivity extends Activity {

	private MgrDataSource datasource;
	private TextView curr_date;
	private Spinner tags, rs_type;
	private EditText comments, rs ;
	private ImageButton btn_date;
	private Button btn_add,btn_add_tag;

	final Context ctx=this;
	
	static final int DATE_DIALOG_ID_DOB = 999;
	private int myear;
	private int month;
	private int day;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		datasource = new MgrDataSource(this);
	    datasource.open();

	    curr_date=(TextView)findViewById(R.id.txt_date);
	    tags=(Spinner)findViewById(R.id.spin_tags);
	    comments=(EditText)findViewById(R.id.et_comment);
	    rs=(EditText)findViewById(R.id.rs);
	    rs_type=(Spinner)findViewById(R.id.spin_rs_type);
	    btn_date=(ImageButton)findViewById(R.id.btn_date);
	    btn_add=(Button)findViewById(R.id.btn_add);
	    btn_add_tag=(Button)findViewById(R.id.btn_add_tags);
	    DatabaseSetup.init(this);
	    loadSpinnerTags();
	    	    
	    List<String> myArraySpinner_rstype = new ArrayList<String>();

	    myArraySpinner_rstype.add("IN");
	    myArraySpinner_rstype.add("OUT");
	    
	    ArrayAdapter<String> spinnerArrayAdapter_rstype = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, myArraySpinner_rstype);
	    spinnerArrayAdapter_rstype.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
	    rs_type.setAdapter(spinnerArrayAdapter_rstype);
	       

	    
	    final Calendar c = Calendar.getInstance();
		myear = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		btn_date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID_DOB);
			}
		});
		btn_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ExpManager comment = null;
				String in_rs=null,out_rs=null;
				Double in_rs_d;
				if(!rs.getText().toString().equals("")){
					in_rs_d=Double.parseDouble(rs.getText().toString());
				}else{
					in_rs_d=null;
				}
				
				comment = datasource.createManager(curr_date.getText().toString(), tags.getSelectedItem().toString(), comments.getText().toString(), 
						in_rs_d, rs_type.getSelectedItem().toString() );
				Toast.makeText(getApplicationContext(), "added "+ comment, 3000).show();
			}
		});
		
		
		btn_add_tag.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// get prompts.xml view

				LayoutInflater layoutInflater = LayoutInflater.from(ctx);
				View promptView = layoutInflater.inflate(R.layout.add_tags, null);
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
				//set prompts.xml to be the layout file of the alertdialog builder
				alertDialogBuilder.setView(promptView);
				final EditText input = (EditText) promptView.findViewById(R.id.et_tag);
				
				alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						//add to database
						Table_Tags comment = null;
						//add Tag Color 
						comment = datasource.createTags( input.getText().toString(),0);
						Toast.makeText(getApplicationContext(), "tag added "+ comment, 3000).show();
						loadSpinnerTags();
						
					}
				}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int arg1) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
				
				//create dialog
				AlertDialog alertD=alertDialogBuilder.create();
				alertD.show();
								
			}
		});

	}

	private void loadSpinnerTags() {
		// TODO Auto-generated method stub
		List<String> myArraySpinner = new ArrayList<String>();
		/*
			    myArraySpinner.add("red");
			    myArraySpinner.add("green");
			    myArraySpinner.add("blue");     
			*/
			    myArraySpinner.add("--select--");
			    Cursor cursor_tag=datasource.getList_Tags();
			    cursor_tag.moveToFirst();
			    while (!cursor_tag.isAfterLast()) {
			      myArraySpinner.add(cursor_tag.getString(1));
			      cursor_tag.moveToNext();
			    }
			    // make sure to close the cursor
			    cursor_tag.close();

			    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, myArraySpinner);
			    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down vieww
			    tags.setAdapter(spinnerArrayAdapter);

		
	}

	// Will be called via the onClick attribute
	  // of the buttons in main.xml
	  public void onClick(View view) {
	    @SuppressWarnings("unchecked")
	    //ArrayAdapter<Comment> adapter = (ArrayAdapter<Comment>) getListAdapter();
	    ExpManager comment = null;
	    switch (view.getId()) {
	    case R.id.btn_add:
	      String[] str_comments = new String[] { "Cool", "Very nice", "Hate it" };
	      int nextInt = new Random().nextInt(3);
	      // save the new comment to the database
	      comment = datasource.createManager(curr_date.getText().toString(), tags.getSelectedItem().toString(), 
	    		  comments.getText().toString(), Double.parseDouble(rs.getText().toString()), 
	    		  rs_type.getSelectedItem().toString());
	      //adapter.add(comment);
	      Toast.makeText(getApplicationContext(), "added "+ comment, 3000).show();
	      break;
	    /*case R.id.btn_delete:
	      if (getListAdapter().getCount() > 0) {
	        comment = (Comment) getListAdapter().getItem(0);
	        datasource.deleteComment(comment);
	        adapter.remove(comment);
	      }
	      break;*/
	    }
	    //adapter.notifyDataSetChanged();
	  }
	  
	  @Override
		protected Dialog onCreateDialog(int id) {
			// TODO Auto-generated method stub
			switch (id) {
			case DATE_DIALOG_ID_DOB:
				// set date picker as current date
				return new DatePickerDialog(this, datePickerListener_cdate, myear, month, day){
					@Override
					public void onDateChanged(DatePicker view, int year, int monthOfYear,
							int dayOfMonth) {
						// TODO Auto-generated method stub
						if (year > myear)
	                        view.updateDate(myear, month, day);

	                    if (monthOfYear > month && year == myear)
	                        view.updateDate(myear, month,day);

	                    if (dayOfMonth > day && year == myear && monthOfYear == month)
	                        view.updateDate(myear, month, day);
						//super.onDateChanged(view, year, month, day);
					}
				};
			}
			return null;
		}
		
		private DatePickerDialog.OnDateSetListener datePickerListener_cdate = new DatePickerDialog.OnDateSetListener() {

			// when dialog box is closed, below method will be called.
			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
				// TODO Auto-generated method stub
				Date selectedDate = new Date(year-1900, monthOfYear, dayOfMonth);
	            String strDate = null;
	            SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
	            strDate = dateFormatter.format(selectedDate);
				
					/*dob.setText(new StringBuilder().append(dayOfMonth)
							   .append("/").append(monthOfYear + 1).append("/").append(year)
							   .append(" ").append(strDate));
			
					*/
	            curr_date.setText(new StringBuilder().append(strDate));
			}
	 
		};
	 

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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case R.id.action_settings:
			Intent ch_setting=new Intent(this,MgrList.class);
			//startActivityForResult(ch_setting, RESULT_SETTING);
			startActivity(ch_setting);
			//finish();
			break;
			
		
		}
		
		return true;
		
		//return super.onOptionsItemSelected(item);
	}

}
