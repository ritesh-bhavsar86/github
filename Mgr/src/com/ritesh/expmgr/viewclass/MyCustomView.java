package com.ritesh.expmgr.viewclass;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class MyCustomView extends View{

	Context mContext;
	
	public MyCustomView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
	}

	public MyCustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
		
	}
	
	public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		mContext = context;
	}
	

	
}
