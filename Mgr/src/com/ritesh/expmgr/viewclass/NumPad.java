package com.ritesh.expmgr.viewclass;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ritesh.expmgr.R;

public class NumPad {
	// flag values
	public static int NOFLAGS = 0;
	public static int HIDE_INPUT = 0;
	public static int HIDE_PROMPT = 2;
	
	static Float amountDue;
	Context mcontext;
	//static TextView prompt;
	static EditText promptValue;
	static ImageButton ibtn_back;
	
	static Button btn1;
	static Button btn2;
	static Button btn3;
	static Button btn4;
	static Button btn5;
	static Button btn6;
	static Button btn7;
	static Button btn8;
	static Button btn9;
	static Button btn0;
	static Button btnC;
	static Button btnDot;
	
	private String value = "";
	private String addl_text = "";
	private NumPad me;
	
	private int flag_hideInput = 0;
	private int flag_hidePrompt = 0;
	
	public interface numbPadInterface {
		public String numPadInputValue(String value);
		public String numPadCanceled();
	}
	public NumPad(Context context){
		this.mcontext = context;
	}
	
	public String getValue() {
		return value;
	}

	public void setAdditionalText(String inTxt) {
		addl_text = inTxt;
	}
	
	public void show(final Activity a, final String promptString, int inFlags, 
	  final numbPadInterface postrun) {
		me = this;
		flag_hideInput = inFlags % 2;
		flag_hidePrompt = (inFlags / 2) % 2;
		
		Builder dlg = new AlertDialog.Builder(a);
		if (flag_hidePrompt == 0) {
			//dlg.setTitle(promptString);
			//promptValue.setText(promptString);
		}
		// Inflate the dialog layout
		LayoutInflater inflater = a.getLayoutInflater();
		View iView = inflater.inflate(R.layout.num_pad, null, false);
		
		// create code to handle the change tender
		//prompt = (TextView) iView.findViewById(R.id.promptText);
		/*prompt.setText(addl_text);
		if (addl_text.equals("")) {
			prompt.setVisibility(View.GONE);
		}*/
		promptValue = (EditText) iView.findViewById(R.id.promptValue);
		InputMethodManager imm = (InputMethodManager) mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);  
        imm.hideSoftInputFromWindow(promptValue.getWindowToken(), 0);  

		promptValue.setText(promptString);	
        promptValue.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputMethodManager imm = (InputMethodManager) mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);  
		        imm.hideSoftInputFromWindow(promptValue.getWindowToken(), 0);  

			}
		});
		// Defaults
		value = "";
		//promptValue.setText("");

		btn1 = (Button) iView.findViewById(R.id.button1);
		btn2 = (Button) iView.findViewById(R.id.button2);
		btn3 = (Button) iView.findViewById(R.id.button3);
		btn4 = (Button) iView.findViewById(R.id.button4);
		btn5 = (Button) iView.findViewById(R.id.button5);
		btn6 = (Button) iView.findViewById(R.id.button6);
		btn7 = (Button) iView.findViewById(R.id.button7);
		btn8 = (Button) iView.findViewById(R.id.button8);
		btn9 = (Button) iView.findViewById(R.id.button9);
		btn0 = (Button) iView.findViewById(R.id.button0);
		btnC = (Button) iView.findViewById(R.id.buttonC);
		btnDot = (Button) iView.findViewById(R.id.buttonDot);
		ibtn_back = (ImageButton)iView.findViewById(R.id.btn_back);

		ibtn_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					if(promptValue.length() > 0){
						//promptValue.setText( promptValue.getText().toString().substring(0, promptValue.getText().toString().length()-1));
					
						int intval=promptValue.getSelectionEnd();
						String strpvale = promptValue.getText().toString().substring(0, promptValue.getSelectionEnd());
						String inNumb = promptValue.getText().toString().substring(0, promptValue.getSelectionEnd()-1);
						String strRemain = promptValue.getText().toString().substring(promptValue.getSelectionEnd(), promptValue.length());
						promptValue.setText(inNumb + strRemain);
					//int intpos=promptValue.getText().toString().indexOf(strpvale+inNumb);
						int intpos=(inNumb).length();
						promptValue.setSelection(intpos);
					}else{
						promptValue.setText("0");
						promptValue.setSelection(promptValue.length());
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				value = promptValue.getText().toString();
				//promptValue.setSelection(promptValue.length());
			}
		});
		
		btnC.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				/*value = "";
				promptValue.setText("");*/
				appendNumber("00");
			}
		});
		btn1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				appendNumber("1");
			}
		});
		btn2.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				appendNumber("2");
			}
		});
		btn3.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				appendNumber("3");
			}
		});
		btn4.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				appendNumber("4");
			}
		});
		btn5.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				appendNumber("5");
			}
		});
		btn6.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				appendNumber("6");
			}
		});
		btn7.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				appendNumber("7");
			}
		});
		btn8.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				appendNumber("8");
			}
		});
		btn9.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				appendNumber("9");
			}
		});
		btn0.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				appendNumber("0");
			}
		});
		btnDot.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				if(promptValue.getText().toString().contains(".")){
					
				}else{
					appendNumber(".");
				}
				
			}
		});

		dlg.setView(iView);
		dlg.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dlg, int sumthin) {
				dlg.dismiss();
				postrun.numPadInputValue(me.getValue());
			}
		});
		dlg.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dlg, int sumthin) {
				dlg.dismiss();
				postrun.numPadCanceled();
			}
		});
		dlg.show();
	}
	
	void appendNumber(String inNumb) {
		
		/*if (flag_hideInput == 1) {
			promptValue.setText(promptValue.getText() + "*");
		} else {
			promptValue.setText(promptValue.getText() + inNumb);
		}*/
		/*if(promptValue.length() > 0){
			value = value + inNumb;
			if(promptValue.getText().toString().equals(".")){
				promptValue.setText("0"+ promptValue.getText() + inNumb);
				value = promptValue.getText().toString();
			}else{
				if(Double.parseDouble(promptValue.getText().toString()) > 0){
					
					if(promptValue.getText().toString().contains(".")){
						if((promptValue.length()-3) == promptValue.getText().toString().lastIndexOf(".")){
							
						}else{
							
							promptValue.setText(promptValue.getText() + inNumb);
							
						}
					}else{
						promptValue.setText(promptValue.getText() + inNumb);
					}
					
				}else{
					promptValue.setText(inNumb);
				}
			}
			
		}else{
			value = inNumb;
			promptValue.setText(inNumb);
		}
		*/
		if(promptValue.length() > 0){
			value = value + inNumb;
			if(promptValue.getText().toString().equals(".")){
				promptValue.setText("0"+ promptValue.getText() + inNumb);
				value = promptValue.getText().toString();
				promptValue.setSelection(promptValue.length());
			}else{
				if(Double.parseDouble(promptValue.getText().toString()) > 0){
					
					if(promptValue.getText().toString().contains(".")){
						if((promptValue.length()-3) == promptValue.getText().toString().lastIndexOf(".")){
							
						}else{
							int intval=promptValue.getSelectionEnd();
							String strpvale = promptValue.getText().toString().substring(0, promptValue.getSelectionEnd());
							String strRemain = promptValue.getText().toString().substring(promptValue.getSelectionEnd(), promptValue.length());
							promptValue.setText(strpvale + inNumb + strRemain);
							//int intpos=promptValue.getText().toString().indexOf(strpvale+inNumb);
							int intpos=(strpvale+inNumb).length();
							promptValue.setSelection(intpos);
						}
					}else{
						int intval=promptValue.getSelectionEnd();
						String strpvale = promptValue.getText().toString().substring(0, promptValue.getSelectionEnd());
						String strRemain = promptValue.getText().toString().substring(promptValue.getSelectionEnd(), promptValue.length());
						promptValue.setText(strpvale + inNumb + strRemain);
						//int intpos=promptValue.getText().toString().indexOf(strpvale+inNumb);
						int intpos=(strpvale+inNumb).length();
						promptValue.setSelection(intpos);
						/*promptValue.setText(promptValue.getText() + inNumb);
						promptValue.setSelection(promptValue.length());*/
					}
					
				}else{
					promptValue.setText(inNumb);
					promptValue.setSelection(promptValue.length());
				}
			}
			
		}else{
			value = inNumb;
			promptValue.setText(inNumb);
			promptValue.setSelection(promptValue.length());
		}
		//promptValue.setSelection(promptValue.length());
		
	}
	
}
