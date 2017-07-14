package com.example.finddesk;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

public class Normal extends Activity {

	protected View view1,view2,view3,view4;
	protected PagerTabStrip pagertitle;
	protected List<View> viewlist;
	protected List<String> titlelist;
	protected SharedPreferences sharepre;
	protected SharedPreferences.Editor editor;
	protected View button;
	protected ViewPager viewpager;
	protected List<NormalArc> arclist;
	NormalArc nor1,nor2,nor3,nor4;
	int i=10;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_normal);
		sharepre=getSharedPreferences("setting",Context.MODE_WORLD_WRITEABLE);
		editor=sharepre.edit();
		button=(View)findViewById(R.id.normal_menu);
		TextView title=(TextView)findViewById(R.id.normal_title);
		Typeface type=Typeface.createFromAsset(getAssets(), "fonts/gangbixingshu.ttf");
		title.setTypeface(type);
		TextView state=(TextView)findViewById(R.id.normal_state);
		state.setText("欢迎你，"+sharepre.getString("account",""));
		
		LayoutInflater lf=getLayoutInflater().from(this);
		viewpager=(ViewPager)findViewById(R.id.normal_viewpager);
		
		
		new Thread(){
			@Override
			public void run(){
				try {
					Thread.sleep(3000);
					nor1.setProcess(1);
				} catch (InterruptedException e) {
				}
			}
		}.start();
		

		
		
		
		
		
		
		view1=lf.inflate(R.layout.normal1,null);
		view2=lf.inflate(R.layout.normal2,null);
		view3=lf.inflate(R.layout.normal3,null);
		view4=lf.inflate(R.layout.normal4,null);
		
		
		arclist=new ArrayList<NormalArc>();
		nor1=(NormalArc)view1.findViewById(R.id.normal_1);
		nor2=(NormalArc)view2.findViewById(R.id.normal_2);
		nor3=(NormalArc)view3.findViewById(R.id.normal_3);
		nor4=(NormalArc)view4.findViewById(R.id.normal_4);
		arclist.add(nor1);
		arclist.add(nor2);
		arclist.add(nor3);
		arclist.add(nor4);
		
	
		nor1.setProcess(1);
		viewlist = new ArrayList<View>();
		viewlist.add(view1);
		viewlist.add(view2);
		viewlist.add(view3);
		viewlist.add(view4);
		
		titlelist=new ArrayList<String>();
		titlelist.add("文理学部图书馆");
		titlelist.add("工学部图书馆");
		titlelist.add("信息学部图书馆");
		titlelist.add("医学部图书馆");
		
		pagertitle=(PagerTabStrip)findViewById(R.id.normal_pagertitle);
		
		PagerAdapter pagerAdapter=new PagerAdapter(){

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return viewlist.size();
			}

			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0==arg1;
			}
			@Override
			public Object instantiateItem(ViewGroup container,int position)
			{
				container.addView(viewlist.get(position));
				
				return viewlist.get(position);
			}
			@Override
			public CharSequence getPageTitle(int position)
			{
				new Thread(){
					@Override
					public void run(){
							i+=5;
					}
					
				}.start();
				arclist.get(position).setProcess(i);
				return titlelist.get(position);
				
			}
			@Override
			public void destroyItem(ViewGroup container,int position,Object object)
			{
				container.removeView(viewlist.get(position));
			}
		};
		
		viewpager.setAdapter(pagerAdapter);
		button.setOnClickListener(new OnClickListener(){

			public void onClick(View v) {
				openOptionsMenu();
				
			}
			
		});
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,1,0,"个人信息");
		menu.add(0,2,0,"查看好友");
		menu.add(0,3,0,"预约座位");
		menu.add(0,4,0,"退出登录");
		menu.add(0,5,0,"设置");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
				case 1:Intent intent1=new Intent(Normal.this,PersonalInformation.class);startActivity(intent1);break;
				case 2:Intent intent2=new Intent(Normal.this,FriendBooking.class);startActivity(intent2);break;
				case 3:Intent intent3=new Intent(Normal.this,Book.class);startActivity(intent3);break;
				case 4: editor.putString("auto","" );
						editor.commit();
						Intent intent4=new Intent(Normal.this,First.class);
						startActivity(intent4);
						finish();
						break;
				case 5:Intent intent5=new Intent(Normal.this,Setting.class);
						startActivity(intent5);
						finish();
						break;
		}
		return super.onOptionsItemSelected(item);
	}
}
