package com.example.finddesk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class PersonalInformation extends Activity {

	protected EditText account,password,name,email,telenum;
	protected SharedPreferences sharepre;
	protected SharedPreferences.Editor editor;
	protected Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_personal_information);
		sharepre=getSharedPreferences("setting",Context.MODE_WORLD_WRITEABLE);
		editor=sharepre.edit();
		
		account=(EditText)findViewById(R.id.personal_account);
		password=(EditText)findViewById(R.id.personal_password);
		name=(EditText)findViewById(R.id.personal_name);
		email=(EditText)findViewById(R.id.personal_email);
		telenum=(EditText)findViewById(R.id.personal_telenum);
		button=(Button)findViewById(R.id.personal_change);
		
		account.setText(sharepre.getString("account",""));
		account.setEnabled(false);
		
		password.setText(sharepre.getString("password",""));
		password.setEnabled(false);
		
		name.setText(sharepre.getString("name",""));
		name.setEnabled(false);
		
		email.setText(sharepre.getString("email",""));
		email.setEnabled(false);
		
		telenum.setText(sharepre.getString("telenum",""));
		telenum.setEnabled(false);
	
		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
					Intent intent=new Intent(PersonalInformation.this,Change.class);
					startActivity(intent);
					finish();
			}
			
		});
		
	}

	
}
