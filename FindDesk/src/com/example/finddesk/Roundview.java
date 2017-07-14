package com.example.finddesk;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

public class Roundview extends View{

	Paint p=new Paint();
	int i=5,flag=0;
	public Roundview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public Roundview(Context context){
		super(context);
	}
	
	public void onDraw(Canvas canvas){
		
		
		p.setColor(Color.RED);
		p.setStyle(Style.STROKE);
		if(flag==0&&i<=canvas.getWidth()/2)
			i+=2;
		else
			i=0;
		canvas.drawCircle(canvas.getWidth()/2, canvas.getWidth()/2, 10+i, p);
		final Handler myhandler=new Handler(){
			@Override
			public void handleMessage(Message msg){
				
				invalidate();
				
				super.handleMessage(msg);
			}
		};
		
		new Thread(){
			@Override
			public void run(){
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {}
				myhandler.sendMessage(new Message());
			}
		}.start();
	}

}
