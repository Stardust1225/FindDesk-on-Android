package com.example.finddesk;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class Firstview extends View {

	private Paint p=new Paint();
	private int i=0;
	private Handler handle;
	public Firstview(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public Firstview(Context context){
		super(context);
	}
	 @Override
	 public void onDraw(final Canvas canvas){
		 p.setColor(Color.GRAY);
		 p.setAlpha(70);
		 p.setStyle(Style.STROKE);
		 p.setStrokeWidth(10);
		 int h=canvas.getWidth();
		 canvas.drawArc(new RectF(h/4,h/4,h*3/4,h*3/4), 135,270,false,p);
		 p.setColor(Color.BLUE);
		 canvas.drawArc(new RectF(h/4,h/4,h*3/4,h*3/4), 135,i,false,p);
		 i+=4;
		 new Thread(){
			 @Override
			 public void run(){
				 	try {
						Thread.sleep(20);
					} catch (InterruptedException e) {}
				 	handle.sendMessage(new Message());
			 }
		 }.start();
		 
		 handle=new Handler(){
			 @Override 
			 public void handleMessage(Message msg){
				 if(i<=270)
					 invalidate();				 
			}
		 };
		 
	 }

}
