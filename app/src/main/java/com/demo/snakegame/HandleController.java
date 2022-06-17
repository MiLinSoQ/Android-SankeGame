package com.demo.snakegame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.demo.snakegame.role.ForwardInterface;

public class HandleController extends ConstraintLayout {

   private final String TAG = "Handle Controller";
   private Context context;
   
   private Button mUpButton;
   private Button mDownButton;
   private Button mLeftButton;
   private Button mRightButton;
   private Button mCenterButton;
   
   private ActionControllerListener controllerListener;
   
   public HandleController(@NonNull Context context) {
      super(context);
      Init();
   }
   
   public HandleController(@NonNull Context context, @Nullable AttributeSet attrs) {
      super(context, attrs);
      Init();
   }
   
   public HandleController(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
      super(context, attrs, defStyleAttr);
      Init();
   }
   
   @SuppressLint("ClickableViewAccessibility")
   private void Init() {
      View.inflate(getContext(), R.layout.handle_control, this);
   
      controllerListener = null;
      
      mUpButton = findViewById(R.id.handle_control_up);
      mDownButton = findViewById(R.id.handle_control_down);
      mLeftButton = findViewById(R.id.handle_control_left);
      mRightButton = findViewById(R.id.handle_control_right);
      mCenterButton= findViewById(R.id.handle_control_center);
   
      
      mUpButton.setOnTouchListener(upListener);
      mDownButton.setOnTouchListener(downListener);
      mLeftButton.setOnTouchListener(leftListener);
      mRightButton.setOnTouchListener(rightListener);
      mCenterButton.setOnTouchListener(centerListener);
      
   }
   
   public void setControllerListener(ActionControllerListener controlListener) {
      this.controllerListener = controlListener;
   }
   
   private final OnTouchListener upListener = new OnTouchListener() {
      @SuppressLint("ClickableViewAccessibility")
      @Override
      public boolean onTouch(View view, MotionEvent motionEvent) {
         if (controllerListener != null) {
            controllerListener.onUp();
         }
         return false;
      }
   };
   
   private final OnTouchListener downListener = new OnTouchListener() {
      @SuppressLint("ClickableViewAccessibility")
      @Override
      public boolean onTouch(View view, MotionEvent motionEvent) {
         if (controllerListener != null) {
            controllerListener.onDown();
         }
         return false;
      }
   };
   
   private final OnTouchListener leftListener = new OnTouchListener() {
      @SuppressLint("ClickableViewAccessibility")
      @Override
      public boolean onTouch(View view, MotionEvent motionEvent) {
         if (controllerListener != null) {
            controllerListener.onLeft();
         }
         return false;
      }
   };
   
   private final OnTouchListener rightListener = new OnTouchListener() {
      @SuppressLint("ClickableViewAccessibility")
      @Override
      public boolean onTouch(View view, MotionEvent motionEvent) {
         if (controllerListener != null) {
            controllerListener.onRight();
         }
         return false;
      }
   };
   
   private final OnTouchListener centerListener = new OnTouchListener() {
      @Override
      public boolean onTouch(View view, MotionEvent motionEvent) {
         if (controllerListener != null) {
            controllerListener.onCenter();
         }
         return false;
      }
   };
   
   public interface ActionControllerListener {
      boolean onUp();
      boolean onDown();
      boolean onLeft();
      boolean onRight();
      boolean onCenter();
   }
   
}





