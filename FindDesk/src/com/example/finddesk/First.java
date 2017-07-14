package com.example.finddesk;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class First extends Activity{

		protected SharedPreferences sharepre;
		protected SharedPreferences.Editor editor;
		private LinearLayout layout;
		private Button register,login;
		CheckBox autologin = null,remeacc=null;
		Handler handle;
		Builder build;
		AlertDialog dialog;
		TextView account,password,welcome;
		@Override
		public void onCreate(Bundle save){
			super.onCreate(save);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_first);
			
			
			login=(Button)findViewById(R.id.first_login);
			register=(Button)findViewById(R.id.first_register);
			register.setVisibility(View.INVISIBLE);
			login.setVisibility(View.INVISIBLE);
			
			welcome=(TextView)findViewById(R.id.first_welcome);
			Typeface face=Typeface.createFromAsset(getAssets(),"fonts/gangbixingshu.ttf");
			welcome.setTypeface(face);
			welcome.setVisibility(View.INVISIBLE);
			
			sharepre=getSharedPreferences("setting",MODE_WORLD_WRITEABLE);
			editor=sharepre.edit();
			

			WifiManager wifi = (WifiManager) getSystemService(WIFI_SERVICE);
			WifiInfo info = wifi.getConnectionInfo();
			editor.putString("MAC",info.getMacAddress());
			editor.commit();
			
	
			
			Thread t1=new Thread(){
				@Override
				public void run(){
					String string = null;
					try {
						if(sharepre.getString("autologin","").equals("true")){
							String url1="http://"+getResources().getString(R.string.ip)+":8080/Service/AutoLogin?";
							try{
							URL url=new URL(url1+"a="+account.getText().toString()+"&p="+password.getText().toString()
											+"&i="+sharepre.getString("MAC",""));
							
							HttpURLConnection conn=(HttpURLConnection)url.openConnection();
							conn.setReadTimeout(8000);
							conn.setReadTimeout(8000);
							conn.setRequestMethod("GET");
							InputStream in=conn.getInputStream();
							BufferedReader reader=new BufferedReader(new InputStreamReader(in));
							StringBuilder res=new StringBuilder();
							while((string=reader.readLine())!=null){
								res.append(string);
							string=res.toString();
							}
							}catch(Exception e){}
						}
						Thread.sleep(2500);
					} catch (Exception e) {}
					Message msg=new Message();
					msg.what=string.charAt(0);
					handle.sendMessage(msg);
				}
			};
			t1.start();
			
			handle=new Handler(){
				@Override
				public void handleMessage(Message msg){
					if(msg.what==6){
							register.setVisibility(View.VISIBLE);
							login.setVisibility(View.VISIBLE);
							View v=(View)findViewById(R.id.first_view);
							v.setVisibility(View.INVISIBLE);
							welcome.setVisibility(View.VISIBLE);
					}
					else if(msg.what==1){
						Toast.makeText(First.this,"登陆成功",Toast.LENGTH_SHORT).show();
						Intent intent=new Intent(First.this,Normal.class);
						startActivity(intent);
						finish();
					}
					else if(msg.what==2)
						Toast.makeText(First.this,"密码错误",Toast.LENGTH_SHORT).show();
					else if(msg.what==3)
						Toast.makeText(First.this,"用户不存在",Toast.LENGTH_SHORT).show();
					else if(msg.what==4)
						Toast.makeText(First.this,"该用户已在其它地方登陆",Toast.LENGTH_SHORT).show();
						
					
					Intent intent=new Intent(First.this,Normal.class);
					startActivity(intent);
					finish();
				}
			};
			
			
			register.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(First.this,Register.class);
					startActivity(intent);
				}
			});
			
			login.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v) {
					
					build=new AlertDialog.Builder(First.this);
					View view=getLayoutInflater().inflate(R.layout.activity_login,null);
					build.setView(view);
					build.setCancelable(false);
					
					Button login_login,exit;
					final EditText account,password;

					login_login=(Button)view.findViewById(R.id.login_login);
					exit=(Button)view.findViewById(R.id.login_exit);
					
					account=(EditText)view.findViewById(R.id.login_account);
					password=(EditText)view.findViewById(R.id.login_password);
					
					autologin=(CheckBox)view.findViewById(R.id.login_autologin);
					remeacc=(CheckBox)view.findViewById(R.id.login_remeacc);
					
					login_login.setOnClickListener(new OnClickListener(){
						
						@Override
						public void onClick(View v) {
							
							if(autologin.isChecked())
								editor.putString("autologin","true");
							if(remeacc.isChecked())
								editor.putString("rememberaccount","true");
							editor.commit();
							
							Thread t1=new Thread(){
								@Override
								public void run(){
									String url1="http://"+getResources().getString(R.string.ip)+":8080/Service/Login?";
									
									try{
									URL url=new URL(url1+"a="+account.getText().toString()+"&p="+password.getText().toString()
												+"&i="+sharepre.getString("MAC",""));
									
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
							
							
						}});
					
						exit.setOnClickListener(new OnClickListener(){

							@Override
							public void onClick(View v) {
								dialog.dismiss();
							}
							
						});
						dialog=build.create();
						dialog.show();
				}
			});
			
			
		
	}	
}

