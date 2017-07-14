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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Register extends Activity {

	protected SharedPreferences sharepre;
	protected SharedPreferences.Editor editor;
	protected Button login,register,more;
	protected EditText account,password,name,passagain,email,telenum;
	protected LinearLayout moreinfo;
	Handler handle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		
		sharepre=getSharedPreferences("setting",Context.MODE_WORLD_WRITEABLE);
		editor=sharepre.edit();
		
		moreinfo=(LinearLayout)findViewById(R.id.register_moreinfo);
		
		login=(Button)findViewById(R.id.register_login);
		register=(Button)findViewById(R.id.register_register);
		more=(Button)findViewById(R.id.register_more);
		
		account=(EditText)findViewById(R.id.register_account);
		password=(EditText)findViewById(R.id.register_password);
		passagain=(EditText)findViewById(R.id.register_passwordagain);
		name=(EditText)findViewById(R.id.register_name);
		email=(EditText)findViewById(R.id.register_email);
		telenum=(EditText)findViewById(R.id.register_telenum);
		
		moreinfo.setVisibility(View.GONE);
		
		more.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				 if(moreinfo.getVisibility()==View.VISIBLE){
				        moreinfo.setVisibility(View.GONE);
				    }else{
				        moreinfo.setVisibility(View.VISIBLE);
				    }
			}
		});
		
		
		login.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v)
			{
				Intent intent=new Intent(Register.this,First.class);
				startActivity(intent);
			}
		});
			
		register.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					ConnectivityManager internetmanager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);									
					if(internetmanager.getActiveNetworkInfo()==null)
						Toast.makeText(Register.this,"当前未连接网络",Toast.LENGTH_SHORT).show();
					else{
						
	
						final String s1=account.getText().toString();
						final String s2=password.getText().toString();
						String s3=passagain.getText().toString();
						
						if(s1.length()<=0)
							Toast.makeText(Register.this,"请输入用户名" ,Toast.LENGTH_SHORT).show();
						else if(!s2.equals(s3))
							Toast.makeText(Register.this,"两次密码不同" ,Toast.LENGTH_SHORT).show();
						else{
							Thread t1=new Thread(){
								@Override
								public void run(){
									String url1="http://"+getResources().getString(R.string.ip)+":8080/Service/Register?";
									editor.putString("account",s1);
									editor.putString("password", s2);
									editor.putString("email",email.getText().toString());
									editor.putString("tele",telenum.getText().toString());
									editor.putString("name", name.getText().toString());
									editor.commit();
									
									try{
									URL url=new URL(url1+"a="+s1+"&p="+s2+"&i="+sharepre.getString("MAC","")+"&e="+email.getText().toString()
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
				}
			});
		
		handle=new Handler(){
			
			@Override
			public void handleMessage(Message msg){
				if(msg.what==1){
					Toast.makeText(Register.this,"注册成功，请登录",Toast.LENGTH_SHORT).show();
					Intent intent=new Intent(Register.this,First.class);
					startActivity(intent);
				}
				else if(msg.what==2)
					Toast.makeText(Register.this,"该用户名已存在",Toast.LENGTH_SHORT).show();
			}
		};
		
	}

	
}
