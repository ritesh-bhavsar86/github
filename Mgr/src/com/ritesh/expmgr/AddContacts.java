package com.ritesh.expmgr;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ritesh.expmgr.database.DatabaseSetup;

public class AddContacts extends Activity
{

    Button saveContact;
    EditText contactName, contactMail, contactPhone1, contactPhone2, contactAddress, contactNotes; 

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcontacts);


        contactName = (EditText) findViewById(R.id.editText1);
        contactMail = (EditText) findViewById(R.id.editText2);
        contactPhone1 = (EditText) findViewById(R.id.editText3);
        contactPhone2 = (EditText) findViewById(R.id.editText4);
        contactAddress = (EditText) findViewById(R.id.editText5);
        contactNotes = (EditText) findViewById(R.id.editText6);
       // saveContact = (Button) findViewById(R.id.btnSaveContact);

        Button btnSaveContact = (Button) findViewById(R.id.btn_save);
        btnSaveContact.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                boolean working = true;

                try{
                    String name = contactName.getText().toString();
                    String mail = contactMail.getText().toString();
                    String phone1 = contactPhone1.getText().toString();
                    String phone2 = contactPhone2.getText().toString();
                    String address = contactAddress.getText().toString();
                    String notes = contactNotes.getText().toString();

                    DatabaseSetup entry = new DatabaseSetup(AddContacts.this);
                    DatabaseSetup.getDb();
                    DatabaseSetup.createEntry(name, mail, phone1, phone2, address, notes);
                    entry.close();

                    }
                    catch(Exception e)
                    {
                        working = false;
                        String error = e.toString();
                        Dialog d = new Dialog(AddContacts.this);
                        d.setTitle("Error");
                        TextView tv = new TextView(AddContacts.this);
                        tv.setText(error);
                    }
                    finally
                    {
                        if(working)
                        {
                        	Dialog d = new Dialog(AddContacts.this);
                            d.setTitle("Success");
                            TextView tv = new TextView(AddContacts.this);
                            tv.setText("The database changes have succeeded.");
                            d.setContentView(tv);
                            d.show();

                        }
                    }       
            }   
        });
    }

}
