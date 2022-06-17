package com.demo.snakegame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

class GameCanvas extends LinearLayout {
	
	private final String TAG = "GameCanvas";
	private Context context;
	private Paint mPaint;
	
	public int mColorRed;
	public int mColorWhite;
	public int mColorGreen;
	
	private CanvasDrawListener drawListener;
	
	public GameCanvas(Context context) {
		super(context);
		Init(context);
	}
	
	public GameCanvas(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		Init(context);
	}
	
	public GameCanvas(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		Init(context);
	}
	
	private void Init(Context context) {
		Log.d(TAG, "Game Canvas Init.");
		this.context = context;
		this.drawListener = null;
		
		mPaint = new Paint();
		
		mColorRed = this.context.getColor(R.color.red);
		mColorWhite = this.context.getColor(R.color.white);
		mColorGreen = this.context.getColor(R.color.green);
		
//		getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//			@Override
//			public void onGlobalLayout() {
//				getViewTreeObserver().removeOnGlobalLayoutListener(this);
//				mWidth = getWidth();
//				mHeight = getHeight();
//
//				Log.d(TAG, "WIDTH: " + mWidth + ", HEIGHT: " + mHeight);
//			}
//		});
		
	}
	
	public void setDrawListener(CanvasDrawListener drawListener) {
		this.drawListener = drawListener;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (drawListener != null) {
			drawListener.onDraw(canvas, mPaint);
		}
	}
	
	interface CanvasDrawListener{
		void onDraw(Canvas canvas, Paint paint);
	}
}
