package com.example.finddesk;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class NormalArc extends View{

	private int part=0;
	Paint p;
	public NormalArc(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
	public NormalArc(Context context){
		super(context);
		
	}
	
	@Override
	protected void onDraw(Canvas canvas){
		super.onDraw(canvas);
		p=new Paint();
		p.setColor(Color.GRAY);
		p.setAntiAlias(true);
		p.setAlpha(70);
		p.setStyle(Paint.Style.STROKE);
		p.setStrokeWidth(8);
		int h=canvas.getWidth();
		canvas.drawArc(new RectF(h/4,h/4,h*3/4,h*3/4), 135,270,false,p);
		p.setColor(Color.RED);
		canvas.drawArc(new RectF(h/4,h/4,h*3/4,h*3/4), 135,270*part/100,false,p);
		
		
		
		
	}
	
	public synchronized void setProcess(int pro){
		part=pro;
		
	}
	
}
