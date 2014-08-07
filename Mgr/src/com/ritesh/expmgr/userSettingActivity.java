package com.ritesh.expmgr;

import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;

/**
 *  CR-IA-CFS-999.000-2216-117931-Feedback Form Ver 1.0
 *  //Created By     : Ritesh Bhavsar
 *  //Created Date   : 15/05/2014
 *  //Change Type    : Client
 *  //Client Name & ID: Shawman-999.000
 *  //Change Reason   : Create settings page for application
 *  
 */

public class userSettingActivity extends PreferenceActivity{

	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		
		
		Preference category = (Preference)getPreferenceManager().findPreference("pref_category");
		if(category!=null)
		{
			category.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
				
				@Override
				public boolean onPreferenceClick(Preference preference) {
					// TODO Auto-generated method stub
					//Toast.makeText(getApplicationContext(), "Sync ...", Toast.LENGTH_SHORT).show();
					Intent ch_setting=new Intent(userSettingActivity.this, CategoryList.class);
					
					startActivity(ch_setting);
					//finish();
					return true;
				}
			});
		}
		
		ListPreference group = (ListPreference)getPreferenceManager().findPreference("pref_group");
	    
	}
	

	
	
	
}
