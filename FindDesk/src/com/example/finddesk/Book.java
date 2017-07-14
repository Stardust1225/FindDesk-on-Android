package com.example.finddesk;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Book extends Activity {

	protected SharedPreferences sharepre;
	protected SharedPreferences.Editor editor;
	protected Spinner library,part,floor,column;
	protected Button select,friend,fresh;
	protected DrawerLayout draw;
	protected TextView text;
	Handler handle1,handle2;
	int flag=0,j;
	String i=null;
	AlertDialog.Builder builder;
	Button[] buttonarray;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_book);
		
		library=(Spinner)findViewById(R.id.book_library);
		part=(Spinner)findViewById(R.id.book_part);
		floor=(Spinner)findViewById(R.id.book_floor);
		column=(Spinner)findViewById(R.id.book_column);
		
		
		select=(Button)findViewById(R.id.book_select);
		friend=(Button)findViewById(R.id.book_friend);
		fresh=(Button)findViewById(R.id.book_fresh);
		draw=(DrawerLayout)findViewById(R.id.book_drawer);
		
		
		sharepre=getSharedPreferences("setting",Context.MODE_WORLD_WRITEABLE);
		editor=sharepre.edit();
		
		select.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				draw.openDrawer(Gravity.START);
			}
		});
		
		friend.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent intent=new Intent(Book.this,FriendBooking.class);
				startActivity(intent);
			}
		});

		
		List<String> libres=new ArrayList<String>();
		libres.add("文理学部图书馆");
		libres.add("工学部图书馆");
		libres.add("信息学部图书馆");
		libres.add("医学部图书馆");
		
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,libres);
		library.setAdapter(adapter);
		
		List<String> partrec1=new ArrayList<String>();
		partrec1.add("A区");
		partrec1.add("B区");
		partrec1.add("C区");
		partrec1.add("D区");
		partrec1.add("E区");
		final ArrayAdapter<String> adapter1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,partrec1);
		
		List<String> floor11=new ArrayList<String>();
		floor11.add("1楼");
		floor11.add("2楼");
		floor11.add("3楼");
		floor11.add("4楼");
		floor11.add("5楼");
		final ArrayAdapter<String> adapter11=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,floor11);
		
		List<String> floor12=new ArrayList<String>();
		floor12.add("1楼");
		floor12.add("2楼");
		floor12.add("3楼");
		final ArrayAdapter<String> adapter12=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,floor12);
		
		List<String> column1=new ArrayList<String>();
		for(int i=1;i<=16;i++)
			column1.add("第"+String.valueOf(i)+"列");
		final ArrayAdapter<String> adapter21=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,column1);
		column.setAdapter(adapter21);
		
		buttonarray=new Button[16];
		buttonarray[0]=(Button)findViewById(R.id.book_desk1);
		
		for(int i=1;i<16;i++){
			buttonarray[i]=(Button)findViewById(R.id.book_desk1+i);		
		}
		final int firstId=buttonarray[0].getId();
		
		
		for(int i=0;i<16;i++)
			buttonarray[i].setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					  builder = new Builder(Book.this);
					  builder.setMessage("确认预约吗？");
					  builder.setTitle("提示");
					  final int deskId=v.getId()-firstId+1;
					  editor.putString("seat",String.valueOf(deskId));
					  editor.commit();
					  builder.setPositiveButton("确认", new DialogInterface.OnClickListener(){
					  
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if(flag==0){
								flag=1;
								Thread t1=new Thread(){
									@Override
									public void run(){
										String url1="http://"+getResources().getString(R.string.ip)+":8080/Service/Book?";
										try{
										URL url=new URL(url1+"l="+sharepre.getString("library","")+"&p="+sharepre.getString("part","")+"&f="
												+sharepre.getString("floor","")+"&s="+deskId+"&i="+sharepre.getString("MAC","")+"&b=0");
										
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
										Log.i("zhangxin",String.valueOf(i));
										handle1.sendMessage(msg);
										
											
										}catch(Exception e){}
									}
								};
								t1.start();
							}
							else
								Toast.makeText(Book.this,"已发送预约请求，请等待",Toast.LENGTH_SHORT).show();
							}
						
						});
					builder.setNegativeButton("取消", new DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}});
					builder.create().show();
						
				}
		});
		handle1=new Handler(){
			@Override
			public void handleMessage(Message msg){
				if(msg.what==1){
					String s=null;
					
					View v=getLayoutInflater().inflate(R.layout.bookdialog,null);
					
					TextView text1,text2,text3,text4,text5;
					text1=(TextView)v.findViewById(R.id.bookdialog_library);
					text2=(TextView)v.findViewById(R.id.bookdialog_part);
					text3=(TextView)v.findViewById(R.id.bookdialog_floor);
					text4=(TextView)v.findViewById(R.id.bookdialog_column);
					text5=(TextView)v.findViewById(R.id.bookdialog_seat);
					
					flag=0;
					builder = new Builder(Book.this);

					
					switch(sharepre.getString("library","").charAt(0)){
						case '1':s="文理学部图书馆    ";break;
						case '2':s="工学部图书馆    ";break;
						case '3':s="信息学部图书馆    ";break;
						case '4':s="医学部图书馆    ";break;
					}
					
					text1.setText(s);
					
					switch(sharepre.getString("part","").charAt(0)){
						case '1':s="A区   ";break;
						case '2':s="B区   ";break;
						case '3':s="C区   ";break;
						case '4':s="D区   ";break;
						case '5':s="E区   ";break;
					}
					
					text2.setText(s);
					text3.setText(sharepre.getString("floor","")+"楼   ");
					text4.setText(sharepre.getString("column","")+"列   ");
					text5.setText(sharepre.getString("seat","")+"号座位   ");
					
					builder.setView(v);
					
					builder.setPositiveButton("确认预约", new DialogInterface.OnClickListener(){

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							Intent intent=new Intent(Book.this,Normal.class);
							startActivity(intent);
							finish();
							
						}
				
						
						});
					builder.setNegativeButton("取消预约", new DialogInterface.OnClickListener(){
						@Override
						public void onClick(DialogInterface dialog, int which) {
							new Thread(){
								@Override
								public void run(){
									String url1="http://"+getResources().getString(R.string.ip)+":8080/Service/Book?";
									try{
										URL url=new URL(url1+"b=1");
										HttpURLConnection conn=(HttpURLConnection)url.openConnection();
										conn.setReadTimeout(8000);
										conn.setReadTimeout(8000);
										conn.setRequestMethod("GET");
										InputStream in=conn.getInputStream();
									}catch(Exception e){}
								}
							}.start();
							dialog.dismiss();
							
						}});
					builder.create().show();
						
					
					
				}
				else
					Toast.makeText(Book.this,"预约失败",Toast.LENGTH_SHORT).show();
			}
		};
		
		
		library.setOnItemSelectedListener(new OnItemSelectedListener(){

			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				switch(position){
					case 0:part.setAdapter(adapter1);editor.putString("library","1");editor.commit();break;
					case 1:part.setAdapter(adapter1);editor.putString("library","2");editor.commit();break;
					case 2:part.setAdapter(adapter1);editor.putString("library","3");editor.commit();break;
					case 3:part.setAdapter(adapter1);editor.putString("library","4");editor.commit();break;
						
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		
		part.setOnItemSelectedListener(new OnItemSelectedListener(){

			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				switch(position){
					case 0:floor.setAdapter(adapter11);editor.putString("part","1");editor.commit();break;
					case 1:floor.setAdapter(adapter12);editor.putString("part","2");editor.commit();break;
					case 2:floor.setAdapter(adapter12);editor.putString("part","3");editor.commit();break;
					case 3:floor.setAdapter(adapter12);editor.putString("part","4");editor.commit();break;
					case 4:floor.setAdapter(adapter12);editor.putString("part","5");editor.commit();break;
						
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		
		floor.setOnItemSelectedListener(new OnItemSelectedListener(){

			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				switch(position){
					case 0:editor.putString("floor","1");editor.commit();break;
					case 1:editor.putString("floor","2");editor.commit();break;
					case 2:editor.putString("floor","3");editor.commit();break;
					case 3:editor.putString("floor","4");editor.commit();break;
					case 4:editor.putString("floor","5");editor.commit();break;
						
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		
		column.setOnItemSelectedListener(new OnItemSelectedListener(){

			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					editor.putString("column", String.valueOf(position+1));
					editor.commit();
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
	
		
		fresh.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				for(j=0;j<=16;j++){
					buttonarray[j].setText("无人");
					buttonarray[j].setTextColor(Color.RED);
					i=String.valueOf(j);
					Thread t1=new Thread(){
						@Override
						public void run(){
							String url1="http://"+getResources().getString(R.string.ip)+":8080/Service/SeatInformation?";
							try{
								URL url=new URL(url1+"l="+sharepre.getString("library","")+"&p="+sharepre.getString("part","")+"&f="
										+sharepre.getString("floor","")+"&s="+i);
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
								if(i=='1'){
									msg.what=j;
									handle2.sendMessage(msg);
								}
						
							
						}catch(Exception e){}
					}
				};
				t1.start();
				}
				
			}
		});
		
		handle2=new Handler(){
			@Override
			public void handleMessage(Message msg){
				buttonarray[msg.what].setText("有人");
				buttonarray[msg.what].setTextColor(Color.RED);
			}
		};
	
	}	

}





