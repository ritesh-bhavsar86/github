package com.ritesh.expmgr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ritesh.expmgr.database.MgrDataSource;
import com.ritesh.expmgr.smoothanimation.ResizeWidth;

public class DashBoardActivity extends Activity {

	ViewGroup  contentView;
	TextView tv_groupName,tv_rupees,tv_CurrencySymbol,tv_mainNote;
	View myview;
	
	private MgrDataSource datasource;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		
		
		
		tv_groupName = (TextView)findViewById(R.id.tv_groupName);
		tv_rupees  = (TextView)findViewById(R.id.tv_rupees);
		tv_CurrencySymbol = (TextView)findViewById(R.id.tv_CurrencySymbol);
		tv_mainNote = (TextView)findViewById(R.id.tv_mainNote);
		
		contentView = (ViewGroup)findViewById(R.id.llv_contents);
		
		tv_groupName.setText("Aug");
		//tv_rupees.setText("2000.00");
		tv_mainNote.setText("Some note here");
		
		
		datasource = new MgrDataSource(this);
	    datasource.open();
	    Double TotalRupee = datasource.getTotRupees();
	    tv_rupees.setText(String.valueOf(TotalRupee));
		funStartAnim();
		
	}
	
	private void funStartAnim() {
		// TODO Auto-generated method stub
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenwidth = dm.widthPixels-30;
		
		int tagscount = datasource.getCountTagsGroupBy();
	
		if(tagscount > 0){
			//calculate total rupees spend on
			List<Double> lstrupee=new ArrayList<Double>();
			List<String> lsttags=new ArrayList<String>();
			List<Double> lstrupeeOrg=new ArrayList<Double>();
			List<String> lsttagsOrg=new ArrayList<String>();
			List<String> lsttagsloop=new ArrayList<String>();
			lsttagsloop = datasource.getTagsGroupBy();
			
			
			for (int i=0;i<tagscount;i++){
				Double totRupee=datasource.getTotRupeesByTags(lsttagsloop.get(i));
				if(totRupee != null){
					lsttagsOrg.add(lsttagsloop.get(i));
					lsttags.add(lsttagsloop.get(i));
					lstrupeeOrg.add(totRupee);
					lstrupee.add(totRupee);
				}
			}
			/*lstrupeeOrg = lstrupee;
			lsttagsOrg = lsttags;
			*/
			lsttags.clear();
			Collections.sort(lstrupee);
			if(lstrupee.size() > 0){
				for(int i=0;i<lstrupee.size();i++){
					Double rupee=lstrupee.get(i);
					for(int j=0;j<lstrupeeOrg.size();j++){
						Double rupeeOrg=lstrupee.get(j);
						if(rupee==rupeeOrg){
							try {
								if(lsttags!=null){
									if(lsttags.contains(lsttagsloop.get(j))){
										lsttags.add(lsttagsloop.get(++j));
									}else{
										lsttags.add(lsttagsloop.get(j));
									}
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
					}
				}
				
				Double Max_Rupee = lstrupee.get(lstrupee.size()-1);
				for(int i=lstrupee.size()-1;i>=0;i--){
					if(lstrupee.get(i)>0){
						LinearLayout llh_category = new LinearLayout(DashBoardActivity.this);
						LinearLayout.LayoutParams param_llh_cat = new LinearLayout.LayoutParams(
								(int) LayoutParams.MATCH_PARENT,
								(int) LayoutParams.WRAP_CONTENT);
						param_llh_cat.topMargin = 20;
						llh_category.setLayoutParams(param_llh_cat);
						llh_category.setOrientation(LinearLayout.HORIZONTAL);
						
						TextView mytv_category = new TextView(DashBoardActivity.this);
						
						LinearLayout.LayoutParams param_tv_rupee = new LinearLayout.LayoutParams(
								(int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);
						mytv_category.setLayoutParams(param_tv_rupee);
						//mytv_rupee.setBackgroundColor(Color.parseColor("#FF4499"));
						mytv_category.setTextColor(Color.parseColor("#FF4499"));
						mytv_category.setText(lsttags.get(i));
						
						TextView mytv_rs = new TextView(DashBoardActivity.this);
						
						LinearLayout.LayoutParams param_tv_rs = new LinearLayout.LayoutParams(
								(int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);
						param_tv_rs.leftMargin = 10;
						mytv_rs.setLayoutParams(param_tv_rs);
						//mytv_rsSymbol.setBackgroundColor(Color.parseColor("#FF4499"));
						mytv_rs.setTextColor(Color.parseColor("#FF4499"));
						String strVal=String.valueOf(lstrupee.get(i));
						mytv_rs.setText(strVal);
						
						TextView mytv_rsSymbol = new TextView(DashBoardActivity.this);
						
						LinearLayout.LayoutParams param_tv_rsSymbol = new LinearLayout.LayoutParams(
								(int) LayoutParams.WRAP_CONTENT, (int) LayoutParams.WRAP_CONTENT);
						param_tv_rsSymbol.leftMargin = 10;
						mytv_rsSymbol.setLayoutParams(param_tv_rsSymbol);
						//mytv_rsSymbol.setBackgroundColor(Color.parseColor("#FF4499"));
						mytv_rsSymbol.setTextColor(Color.parseColor("#FF4499"));
						mytv_rsSymbol.setText(R.string.Rs_Symbol);
						
						
						llh_category.addView(mytv_category);
						llh_category.addView(mytv_rs);
						llh_category.addView(mytv_rsSymbol);
						contentView.addView(llh_category);
						 
						
						myview = new View(DashBoardActivity.this);
						
						/*<View
				        android:id="@+id/view1"
				        android:layout_width="5dp"
				        android:layout_height="10dp"
				        android:layout_alignLeft="@+id/textView1"
				        android:layout_below="@+id/textView1"
				        android:layout_marginTop="19dp"
				        android:background="#FF4499"
				        
				         />*/
						LinearLayout.LayoutParams param_views = new LinearLayout.LayoutParams((int) 10, (int) 15);
						//param_views.topMargin = ;
						myview.setLayoutParams(param_views);
						myview.setBackgroundColor(Color.parseColor("#FF4499"));
						
						int int_screenwidth = (int) ((lstrupee.get(i)*screenwidth)/Max_Rupee);
						
						contentView.addView(myview);
						ResizeWidth anim = new ResizeWidth(myview, int_screenwidth);
					    anim.setDuration(1000);
					    anim.setStartOffset(1000);
					    myview.startAnimation(anim);
						
					}
				}
			}
			
			
		}
		
		    
		
		//max(txtv1)=screenwidth
		//nextscreenwidth = (txtv2*screenwidth)/max 
		
		
/*		int max=lstInt.get(lstInt.size()-1);
		for (int i = 0; i < lstInt.size(); i++){
			int int_screenwidth= (lstInt.get(i)*screenwidth)/max;
			lstScreenWidth.add(int_screenwidth);
		}
		
		ResizeWidth anim = new ResizeWidth(v1, lstScreenWidth.get(lstScreenWidth.size()-1));
	    anim.setDuration(1000);
	    v1.startAnimation(anim);
	    
	    anim = new ResizeWidth(v2, lstScreenWidth.get(lstScreenWidth.size()-2));
	    anim.setDuration(1000);
	    anim.setStartOffset(200);
	    v2.startAnimation(anim);
	    
	    anim = new ResizeWidth(v3, lstScreenWidth.get(lstScreenWidth.size()-3));
	    anim.setDuration(1000);
	    anim.setStartOffset(300);
	    v3.startAnimation(anim);
	    
	    anim = new ResizeWidth(v4, lstScreenWidth.get(lstScreenWidth.size()-4));
	    anim.setDuration(1000);
	    anim.setStartOffset(400);
	    v4.startAnimation(anim);
*/	
	
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		String str="";
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case R.id.action_settings:
			//Intent ch_setting=new Intent(this,MgrList.class);
			Intent ch_setting=new Intent(this,userSettingActivity.class);
			//startActivityForResult(ch_setting, RESULT_SETTING);
			startActivity(ch_setting);
			//finish();
			break;
			//add new expense
		case R.id.action_addNew:
			/*boolean res = datasource.isTagExists("Food");
			boolean res1 = datasource.isTagExists("other");
			boolean res2 = datasource.isTagExists("room");
			boolean res3 = datasource.isTagExists("light");
			Toast.makeText(getApplicationContext(), "Work in progress..."+res+"--"+res1+"--"+res2+"--"+res3, Toast.LENGTH_SHORT).show();*/
			Intent ch_expense=new Intent(this,AddExpense.class);
			//startActivityForResult(ch_setting, RESULT_SETTING);
			startActivity(ch_expense);
			finish();
			break;
		
		}
		
		return true;
		
		//return super.onOptionsItemSelected(item);
	}
	
}
