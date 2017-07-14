package com.example.finddesk;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Change extends Activity {

	protected EditText account,name,email,telenum;
	protected SharedPreferences sharepre;
	protected SharedPreferences.Editor editor;
	protected Button save,reset;
	Handler handle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_change);
		sharepre=getSharedPreferences("setting",Context.MODE_WORLD_WRITEABLE);
		editor=sharepre.edit();
		
		account=(EditText)findViewById(R.id.change_account);
		name=(EditText)findViewById(R.id.change_name);
		email=(EditText)findViewById(R.id.change_email);
		telenum=(EditText)findViewById(R.id.change_telenum);
		save=(Button)findViewById(R.id.change_change);
		reset=(Button)findViewById(R.id.change_reset);
		account.setText(sharepre.getString("account",""));
				
		name.setText(sharepre.getString("name",""));
		
		email.setText(sharepre.getString("email",""));
		
		telenum.setText(sharepre.getString("telenum",""));
	
		save.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				ConnectivityManager internetmanager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);									
				if(internetmanager.getActiveNetworkInfo()==null)
					Toast.makeText(Change.this,"当前未连接网络",Toast.LENGTH_SHORT).show();
				else {
					
					Thread t1=new Thread(){
						@Override
						public void run(){
							String url1="http://"+getResources().getString(R.string.ip)+":8080/Service/Login?";
							
							try{
								URL url=new URL(url1+"a="+account.getText().toString()+"&p="+sharepre.getString("password","")+"&i="+
										sharepre.getString("MAC","")+"&e="+email.getText().toString()
										+"&t="+telenum.getText().toString()+"&n="+name.getText().toString());							
							HttpURLConnection conn=(HttpURLConnection)url.openConnection();
							conn.setReadTimeout(8000);
							conn.setReadTimeout(8000);
							conn.setRequestMethod("GET");
							InputStream in=conn.getInputStream();
							BufferedReader reader=new BufferedReader(new InputStreamReader(in));
							StringBuilder res=new StringBuilder();
							String string;
							while((string=reader.readLine())!=null){
								res.append(string);
							}
							
							string=res.toString();
							int i=Integer.parseInt(string);
							Message msg=new Message();
							msg.what=i;
							
							handle.sendMessage(msg);
							
							}catch(Exception e){}
						}
					};
					t1.start();
				
			}
			}
		});
		
		handle=new Handler(){
			@Override
			public void handleMessage(Message msg){
				if(msg.what==1){
					Toast.makeText(Change.this,"修改成功，请重新登陆", Toast.LENGTH_SHORT);
					Intent intent=new Intent(Change.this,First.class);
					startActivity(intent);
					finish();
				}
				else if(msg.what==2){
					Toast.makeText(Change.this,"验证失败，无法修改", Toast.LENGTH_SHORT);
				}
			}
		};
		
		
		
		reset.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				account.setText("");
								
				name.setText("");
				
				email.setText("");
				
				telenum.setText("");
			
				
			}
			
		});
		
		
	}
}