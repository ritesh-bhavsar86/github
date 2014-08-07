package com.ritesh.expmgr;



import yuku.ambilwarna.AmbilWarnaDialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ritesh.expmgr.adapter.MySimpleAdapter;
import com.ritesh.expmgr.database.DatabaseSetup;
import com.ritesh.expmgr.database.MgrDataSource;
import com.ritesh.expmgr.database.Table_Tags;
import com.ritesh.expmgr.database.UserTable;

public class CategoryList extends Activity{

	private MgrDataSource datasource;
	
	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_EDIT = 1;
	private static final int DELETE_ID = Menu.FIRST + 1;
	// private Cursor cursor;
	private MySimpleAdapter adapter;

	ListView listView;
    public static final String fields[] = { DatabaseSetup.colName};
    Cursor cursor;
    TextView catetgoryName, empty;
    View view_categoryColor;

    View select_tag_color = null;
    final Context ctx=this;
    int color = 0xffffff00;
    

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.category_list);
	//this.getListView().setDividerHeight(2);
	datasource = new MgrDataSource(this);
    datasource.open();

	//fillData();
	//registerForContextMenu(getListView());
	listView = (ListView) findViewById(R.id.listV_category);
	empty=(TextView)findViewById(R.id.empty);
	
	listView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View container, int position,
				long arg3) {
			// TODO Auto-generated method stub
			// Getting the Container Layout of the ListView                
			LinearLayout linearLayoutParent = (LinearLayout) container;
			// Getting the Country TextView 
            View view_tagColor = (View) linearLayoutParent.getChildAt(0);
			TextView tvCategoryName = (TextView) linearLayoutParent.getChildAt(1);
			final String catName = tvCategoryName.getText().toString();
			
			int tagColor = Color.TRANSPARENT;
            Drawable background = view_tagColor.getBackground();
            if (background instanceof ColorDrawable)
            	tagColor = ((ColorDrawable) background).getColor();
            
            final int final_tagColor = tagColor;
			//int tagColor = Integer.parseInt(view_tagColor.getBackground());
			
			LayoutInflater layoutInflater = LayoutInflater.from(ctx);
			View promptView = layoutInflater.inflate(R.layout.add_tags, null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
			//set prompts.xml to be the layout file of the alertdialog builder
			alertDialogBuilder.setView(promptView);
			final EditText input = (EditText) promptView.findViewById(R.id.et_tag);
			input.setText(catName);
			
			select_tag_color = (View) promptView.findViewById(R.id.view_selectColor);
			select_tag_color.setBackgroundColor(final_tagColor);

			select_tag_color.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					openDialog(false);

					
				}
			});
			
			alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					//add to database
					boolean comment = false;
					//add Tag Color 
					int tagchangeColor = final_tagColor;
		            Drawable background = select_tag_color.getBackground();
		            if (background instanceof ColorDrawable)
		            	tagchangeColor = ((ColorDrawable) background).getColor();
		            
		            final int final_tagchangeColor = tagchangeColor;
		            boolean res = datasource.isTagExists(catName);
					if(res == true && !catName.equals(R.string.hint_category_Name)){
						comment = datasource.updateTags(catName, final_tagchangeColor);
						Toast.makeText(getApplicationContext(), "tag updated "+ comment, 3000).show();
					}else{
						Toast.makeText(getApplicationContext(), catName+" cannot be updated."+ comment, 3000).show();
					}
					//loadSpinnerTags();
					
				}
			}).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int arg1) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});/*.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
					AlertDialog builder=new AlertDialog.Builder(CategoryList.this).create();
					builder.setMessage("Are you sure !!!");
					
					builder.setButton(Dialog.BUTTON_POSITIVE,"Yes",new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//txtLoginId.requestFocus();
							boolean res = datasource.isTagExists(catName);
							if(res == true){
								datasource.deleteTagsBytagName(catName);
								Toast.makeText(getApplicationContext(), catName+" deleted successfully.", 3000).show();
							}
							
						}
					});
					builder.setButton(Dialog.BUTTON_NEGATIVE,"No",new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//txtLoginId.requestFocus();
							dialog.cancel();
						}
					});
					builder.show();
					
					
					
				}
			});*/
			
			//create dialog
			AlertDialog alertD=alertDialogBuilder.create();
			alertD.show();
			
			
		}
		
	});
	
	
    DatabaseSetup.init(this); 

    fillData();

	}


	
	private void fillData() {
		// TODO Auto-generated method stub
		// Fields from the database (projection)
		// Must include the _id column for the adapter to work
		String[] from = new String[] { UserTable.COLUMN_TAG_COLOR, UserTable.COLUMN_TAGS };
		// Fields on the UI to which we map
		//int[] to = new int[] { R.id.view_categoryColor, R.id.tv_categoryName };
		Cursor c=datasource.getList_Tags();
		adapter = new MySimpleAdapter(this, c);
		
		listView.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		if(c.getCount()<=0){
			empty.setVisibility(View.VISIBLE);
			/*currdate.setVisibility(View.INVISIBLE);
			tags.setVisibility(View.INVISIBLE);
			comments.setVisibility(View.INVISIBLE);
			rs.setVisibility(View.INVISIBLE);
			rs_type.setVisibility(View.INVISIBLE);*/
			
		}else{
			empty.setVisibility(View.INVISIBLE);
			/*currdate.setVisibility(View.VISIBLE);
			tags.setVisibility(View.VISIBLE);
			comments.setVisibility(View.VISIBLE);
			rs.setVisibility(View.VISIBLE);
			rs_type.setVisibility(View.VISIBLE);*/
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
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		String str="";
		getMenuInflater().inflate(R.menu.category_add, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case R.id.action_addNew:
			//Intent ch_setting=new Intent(this,MgrList.class);
			/*Intent ch_setting=new Intent(this,userSettingActivity.class);
			//startActivityForResult(ch_setting, RESULT_SETTING);
			startActivity(ch_setting);*/
			//finish();
			LayoutInflater layoutInflater = LayoutInflater.from(ctx);
			View promptView = layoutInflater.inflate(R.layout.add_tags, null);
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ctx);
			//set prompts.xml to be the layout file of the alertdialog builder
			alertDialogBuilder.setView(promptView);
			final EditText input = (EditText) promptView.findViewById(R.id.et_tag);
			
			select_tag_color = (View) promptView.findViewById(R.id.view_selectColor);
			select_tag_color.setBackgroundColor(color);

			select_tag_color.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					openDialog(false);

					
				}
			});
			
			alertDialogBuilder.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					//add to database
					Table_Tags comment = null;
					//add Tag Color 
					boolean res = datasource.isTagExists(input.getText().toString());
					if(res == false && !input.getText().toString().equals(R.string.hint_category_Name)){
						comment = datasource.createTags( input.getText().toString(),0);
						Toast.makeText(getApplicationContext(), "category added "+ comment, 3000).show();
					}else{
						Toast.makeText(getApplicationContext(), input.getText().toString()+" already exists", 3000).show();
					}
						
					
					//loadSpinnerTags();
					
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
			
			//Toast.makeText(getApplicationContext(), "work in progress...", Toast.LENGTH_SHORT).show();
			fillData();
			break;
			
		
		}
		
		return true;
		
		//return super.onOptionsItemSelected(item);
	}
	
	void openDialog(boolean supportsAlpha) {
		AmbilWarnaDialog dialog = new AmbilWarnaDialog(CategoryList.this, color, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
			@Override
			public void onOk(AmbilWarnaDialog dialog, int color) {
				Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
				CategoryList.this.color = color;
				//displayColor();
				select_tag_color.setBackgroundColor(color);
			}

			@Override
			public void onCancel(AmbilWarnaDialog dialog) {
				Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
			}
		});
		dialog.show();
	}
	
}
