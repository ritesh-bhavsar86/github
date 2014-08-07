package com.ritesh.expmgr.adapter;



import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ritesh.expmgr.R;

public class MySimpleAdapter extends BaseAdapter {
	  
	private final Context mcontext;
	private final Cursor mcursor;
	  

	  public MySimpleAdapter(Context context, Cursor cursor) {
	    super();
		this.mcontext = context;
	    this.mcursor = cursor;
	  }

	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	    LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    
	    View rowView = inflater.inflate(R.layout.category_list_row, parent, false);
	    
	    TextView textView = (TextView) rowView.findViewById(R.id.tv_categoryName);
	    View view_tag_color = (View) rowView.findViewById(R.id.view_categoryColor);
	    
	    mcursor.moveToPosition(position);
	    
	    try {
			textView.setText(mcursor.getString(1));
			int incolor= mcursor.getInt(2);
			view_tag_color.setBackgroundColor(mcursor.getInt(2));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return rowView;
	  }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mcursor.getCount();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	} 