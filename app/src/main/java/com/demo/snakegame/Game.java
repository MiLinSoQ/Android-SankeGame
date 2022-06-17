package com.demo.snakegame;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import com.demo.snakegame.role.*;

import java.util.Iterator;
import java.util.TreeMap;

class Game {
   
   private final String TAG = "SnakeGame";
   private Context context;
   private int deviceWidth;
   private int deviceHeight;
   
   private GameCanvas mGameCanvas;
   private HandleController mHandleController;
   
   private GameTask mGameTask;
   
   private Snake mSnake;
   private Food mFood;
   
   private int FPS = 50;
   private int waitTime;
   private final long loopSpeed = 9;
   
   
   
   public Game(Context context) {
      this.context = context;
      try {
         Init(context);
         setFPS(60);
      } catch (GameException exception) {
         Log.d(TAG, exception.getMessage());
      }
   }
   
   public boolean isRunning() {
      if (mGameTask == null) return false;
      return mGameTask.isRunning;
   }
   
   public boolean isAutorun() {
      return mSnake.isAutorun();
   }
   
   public int getFPS() {
      return FPS;
   }
   
   public void setFPS(int FPS) {
      this.FPS = FPS;
      this.waitTime = 1000 / this.FPS;
   }
   
   private void Init(Context context) throws GameException {
      this.context = context;
      
   
      DisplayMetrics displayMetrics = new DisplayMetrics();
      ((Activity) this.context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
      deviceHeight = displayMetrics.heightPixels;
      deviceWidth = displayMetrics.widthPixels;
      
      mGameCanvas = ((Activity) this.context).findViewById(R.id.main_game_canvas);
      if (mGameCanvas == null) {
         throw new GameException("Game Canvas undefined.", GameException.INIT_ERROR);
      }
      mGameCanvas.setDrawListener(drawListener);
      
      mHandleController = ((Activity) this.context).findViewById(R.id.main_game_handler_controller);
      if (mHandleController == null) {
         throw new GameException("Handle Controller undefined.", GameException.INIT_ERROR);
      }
      
      mGameTask = new GameTask();
      
      
      InitRole();
      Log.d(TAG, "Width: " + deviceWidth + ", Height: " + deviceHeight);
   
      mHandleController.setControllerListener(mSnake);
   }
   
   private void InitRole() {
      int mForwardFactor = 20;
      mSnake = new Snake(deviceWidth, deviceHeight);
      mSnake.setForwardFactor(mForwardFactor);
      
      mFood = new Food(deviceWidth, deviceHeight);
      mFood.setForwardFactor(mForwardFactor);
   
   }
   
   public void Start() throws GameException {
      
      if (mGameTask == null) {
         throw new GameException("Game Thread is null.");
      }
      
      if (mGameTask.isAlive()) {
         return;
      }
   
      mGameCanvas.setVisibility(View.VISIBLE);
      mGameTask.start();
   }
   
   private void UpdateRole() {
      if (mSnake == null) return;
      Log.d(TAG, "autorun: " + isAutorun());
      if (isAutorun()) {
         
         mSnake.forwardTo(mFood.getX(), mFood.getY());
      } else {
         mSnake.forward();
      }
      
      if (mSnake.isEatingSelf()) {
         Log.d(TAG, "Game stop: " + mSnake.toString());
         mGameTask.interrupt();
      }
   
      if (mSnake.comparePosition(mFood)) {
         mSnake.growUp();
         do {
            mFood.randomPosition();
         } while (mSnake.comparePosition(mFood));
      }
   }
   
   private final GameCanvas.CanvasDrawListener drawListener = new GameCanvas.CanvasDrawListener() {
      private Paint paint;
      private Canvas canvas;
      
      @Override
      public void onDraw(Canvas canvas, Paint paint) {
         this.paint = paint;
         this.canvas = canvas;
         
         DrawSnake();
         DrawFood();
      }
      
      private void DrawSnake() {
         if (mSnake == null) return;
         
         paint.setStrokeWidth(mSnake.getForwardFactor());
         paint.setStyle(Paint.Style.STROKE);
         for (int i = 0; i < mSnake.getSize(); i ++) {
            Snake.SnakeBody body = mSnake.getBody(i);
            if (i == 0) {
               paint.setColor(mGameCanvas.mColorGreen);
            } else {
               paint.setColor(mGameCanvas.mColorWhite);
            }
            canvas.drawPoint(body.getX(), body.getY(), paint);
         }
      }
      
      private void DrawFood() {
         if (mFood == null) return;
         paint.setStrokeWidth(mFood.getForwardFactor());
         paint.setColor(mGameCanvas.mColorRed);
         canvas.drawPoint(mFood.getX(), mFood.getY(), paint);
      }
   };
   
   private class GameTask extends Thread implements Runnable {
      public boolean isRunning = false;
      private long startTime = System.currentTimeMillis() / 1000;
   
      @Override
      public void interrupt() {
         super.interrupt();
         isRunning = false;
      }
   
      public void run() {
         isRunning = true;
         mFood.setVisible(View.VISIBLE);
         mFood.randomPosition();
         
         while (isRunning) {
            try {
               Thread.sleep(waitTime);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
 
            if ((System.currentTimeMillis())  - startTime >= (1000 - loopSpeed * 100)) {
               UpdateRole();
               startTime = System.currentTimeMillis();
            }
            mGameCanvas.postInvalidate();
         }
      }
   }
}
