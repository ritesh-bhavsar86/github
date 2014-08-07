package com.ritesh.expmgr;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;

import com.ritesh.expmgr.database.ExpManager;
import com.ritesh.expmgr.database.MgrDataSource;
import com.ritesh.expmgr.viewclass.NumPad;

public class AddExpense extends Activity {

	TextSwitcher txtSw;
	TextView tv_rupees,tv_CurrencySymbol,tv_selectCategory,tv_categoryName,tv_date,tv_btn_save,tv_btn_cancel;
	EditText edtxt_comment;
	View view_catColor;
	ImageButton ib_date;
	
	
	public static int currentIndex = 0;
	public static String strTextSwInOut[] = {"+","-"};
	final int LIST_ALERTDIALOG=0,CHECKBOX_ALERTDIALOG=1,RADIOBUTTON_ALERTDIALOG=2;
	CharSequence[] category_radio;
	//items in the alertdialog that displays radiobuttons 
	private MgrDataSource datasource;
	int color = 0xffffff00;
	final Context ctx=this;
	
	static final int DATE_DIALOG_ID_DOB = 999;
	private int myear;
	private int month;
	private int day;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_expense);
		
		datasource = new MgrDataSource(this);
	    datasource.open();
		
		txtSw = (TextSwitcher)findViewById(R.id.txtSw_inOut);
		txtSw.setFactory(new ViewFactory() {
            
            public View makeView() {
                // TODO Auto-generated method stub
                // create new textView and set the properties like clolr, size etc
                TextView myText = new TextView(AddExpense.this);
                myText.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
                myText.setTextSize(32);
                myText.setTextColor(Color.BLACK);
                return myText;
            }
        });
		
		tv_rupees = (TextView)findViewById(R.id.txt_rupees);
		tv_CurrencySymbol = (TextView)findViewById(R.id.tv_CurrencySymbol);
		tv_selectCategory = (TextView)findViewById(R.id.tv_selectCategory);
		tv_categoryName = (TextView)findViewById(R.id.tv_categoryName);
		tv_date = (TextView)findViewById(R.id.txt_date);
		edtxt_comment = (EditText)findViewById(R.id.edtxt_comment);
		tv_btn_save = (TextView)findViewById(R.id.btn_save);
		tv_btn_cancel = (TextView)findViewById(R.id.btn_cancel);
		ib_date = (ImageButton)findViewById(R.id.btn_date);
		view_catColor = (View)findViewById(R.id.view_categoryColor);
		
		String strDate = null;
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
        strDate = dateFormatter.format(Calendar.getInstance().getTime());
		tv_date.setText(strDate);
		
		txtSw.setText("+");
		tv_CurrencySymbol.setText(R.string.Rs_Symbol);
		tv_rupees.setText("0");
		txtSw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(currentIndex == 1)
					currentIndex--;
				else
					currentIndex++;
				((TextSwitcher)v).setText(strTextSwInOut[currentIndex]);
				
			}
		});
		
		tv_rupees.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				funShowInputNumPad(tv_rupees.getText().toString());
			}
		});
		funShowInputNumPad(tv_rupees.getText().toString());
		tv_selectCategory.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(RADIOBUTTON_ALERTDIALOG);
				//showDialog(LIST_ALERTDIALOG);
				/*String str = "";
				LayoutInflater layoutInflater = LayoutInflater.from(AddExpense.this);
				View promptView = layoutInflater.inflate(R.layout.cal_view, null);
				
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddExpense.this);
				//set prompts.xml to be the layout file of the alertdialog builder
				alertDialogBuilder.setView(promptView);
				//AlertDialog.Builder builder1=new AlertDialog.Builder(AddExpense.this)
				//.setTitle("Select a Day");
				AlertDialog alertdialog1=alertDialogBuilder.create();
				alertdialog1.show();*/
			}
		});
		tv_btn_save.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			      //String[] str_comments = new String[] { "Cool", "Very nice", "Hate it" };
			      //int nextInt = new Random().nextInt(3);
			      // save the new comment to the database
				String strRs_type="OUT";
				if(tv_categoryName.equals(R.string.hint_category_Name)){
					AlertDialog builder=new AlertDialog.Builder(AddExpense.this).create();
					builder.setMessage("Please select category.  Try Again!!!");
					builder.setButton(Dialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
												
						}
					});
					builder.show();
					
				}else{
					if(strTextSwInOut[currentIndex].equals("+")){
						strRs_type = "IN";
					}else{
						strRs_type="OUT";
					}
					ExpManager expMgr=datasource.createManager(tv_date.getText().toString(), tv_categoryName.getText().toString(), 
				    		  edtxt_comment.getText().toString(), Double.parseDouble(tv_rupees.getText().toString()), 
				    		  strRs_type);
				      //adapter.add(comment);
				      Toast.makeText(getApplicationContext(), "Record added...", 3000).show();
				      Intent ch_expense=new Intent(AddExpense.this,DashBoardActivity.class);
						//startActivityForResult(ch_setting, RESULT_SETTING);
				      startActivity(ch_expense);
				      finish();
				 
				}
				     
			}
		});
		tv_btn_cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent ch_expense=new Intent(AddExpense.this,DashBoardActivity.class);
				//startActivityForResult(ch_setting, RESULT_SETTING);
				startActivity(ch_expense);
				finish();
			}
		});
		final Calendar c = Calendar.getInstance();
		myear = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		ib_date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID_DOB);
			}
		});
		
				
	}

	private void funShowInputNumPad(String strVal) {
		// TODO Auto-generated method stub
		// create an instance of NumbPad
				NumPad np = new NumPad(AddExpense.this);
				// optionally set additional title
				//np.setAdditionalText("Need da pin");
				// show the NumbPad to capture input.
				np.show(this, strVal, NumPad.HIDE_INPUT, 
				  new NumPad.numbPadInterface() {
					// This is called when the user click the 'Ok' button on the dialog
					// value is the captured input from the dialog.
					public String numPadInputValue(String value) {
						/*if (value.equals("1234")) {
							// do something here
							Toast.makeText(getApplicationContext(), 
							  "Pin is CORRECT! What do you want me to do?", 1).show();
						} else {
							// generate a toast message to inform the user that 
							// the captured input is not valid
							Toast.makeText(getApplicationContext(), 
							  "Manager Pin is incorrect", 1).show();
						}*/
						if(value.equals("") || value == null){
							tv_rupees.setText("0");
						}else{
							tv_rupees.setText(value);
						}
						
						return null;
					}
					
					// This is called when the user clicks the 'Cancel' button on the dialog
					public String numPadCanceled() {
						// generate a toast message to inform the user that the pin 
						// capture was canceled
						//Toast.makeText(getApplicationContext(), 
						 // "Pin capture canceled!", 0).show();
						return null;
					}
				});
	}
	
	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		//TODO Auto-generated method stub
		switch (id) {
		case RADIOBUTTON_ALERTDIALOG:
				List<String> category_list = datasource.getTagsGroupBy();
				category_radio = new String[category_list.size()];
				for(int i = 0;i<category_list.size();i++){
					category_radio[i]=category_list.get(i);
				}
				AlertDialog.Builder builder2=new AlertDialog.Builder(AddExpense.this)
				.setTitle("Choose a Category")
				.setSingleChoiceItems(category_radio, -1, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast.makeText(getApplicationContext(), "The selected category is "+category_radio[which], Toast.LENGTH_LONG).show();
						tv_categoryName.setText(category_radio[which]);
						color = datasource.getColorByTags(category_radio[which].toString());
						view_catColor.setBackgroundColor(color);
						dialog.dismiss();
					}
				});
				AlertDialog alertdialog2=builder2.create();
				return alertdialog2;
				
		case LIST_ALERTDIALOG:
			LayoutInflater layoutInflater = LayoutInflater.from(AddExpense.this);
			View promptView = layoutInflater.inflate(R.layout.cal_view, null);
			
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AddExpense.this);
			//set prompts.xml to be the layout file of the alertdialog builder
			alertDialogBuilder.setView(promptView);
			AlertDialog.Builder builder1=new AlertDialog.Builder(AddExpense.this)
			.setTitle("Select a Day");
			AlertDialog alertdialog1=builder1.create();
			return alertdialog1;
			

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
		//return super.onCreateDialog(id);
	}
	@Override
	@Deprecated
	protected void onPrepareDialog(int id, Dialog dialog) {
		// TODO Auto-generated method stub
		switch(id){
		case RADIOBUTTON_ALERTDIALOG:
			AlertDialog prepare_radio_dialog=(AlertDialog)dialog;
			ListView list_radio=prepare_radio_dialog.getListView();
			for(int i=0;i<list_radio.getCount();i++){
				list_radio.setItemChecked(i, false);
			}
			break;
				
		}
		//super.onPrepareDialog(id, dialog);
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
            tv_date.setText(new StringBuilder().append(strDate));
		}
 
	};
 

}
