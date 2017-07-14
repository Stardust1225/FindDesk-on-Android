package com.example.finddesk;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;


public class Setting extends Activity {

	protected SharedPreferences sharepre;
	protected SharedPreferences.Editor editor;
	protected CheckBox auto,frifound,perinfo,phonedata,frirecom;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting);
		sharepre=getSharedPreferences("setting",Context.MODE_WORLD_WRITEABLE);
		editor=sharepre.edit();
		auto=(CheckBox)findViewById(R.id.setting_auto);
		frifound=(CheckBox)findViewById(R.id.setting_friend);
		frirecom=(CheckBox)findViewById(R.id.setting_friendrecom);
		perinfo=(CheckBox)findViewById(R.id.setting_personalinfo);
		phonedata=(CheckBox)findViewById(R.id.setting_phonedata);
		
		auto.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
					editor.putString("auto","1");
				else
					editor.putString("auto","0");
				editor.commit();
			}
			
		});
		
		frifound.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
					editor.putString("frifound","1");
				else
					editor.putString("frifound","0");
				editor.commit();
			}
			
		});
		
		frirecom.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
					editor.putString("frirecom","1");
				else
					editor.putString("frirecom","0");
				editor.commit();
			}
			
		});
		
		perinfo.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
					editor.putString("perinfo","1");
				else
					editor.putString("perinfo","0");
				editor.commit();
			}
			
		});
		
		phonedata.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked)
					editor.putString("phonedata","1");
				else
					editor.putString("phonedata","0");
				editor.commit();
			}
			
		});
	
	}

	
}
