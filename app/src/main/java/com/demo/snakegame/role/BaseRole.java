package com.demo.snakegame.role;

import android.view.View;

class BaseRole {
   
   enum MoveDirection {
      
      NONE(0, "NONE"),
      UP(1, "UP"),
      DOWN(2, "DOWN"),
      LEFT(3, "LEFT"),
      RIGHT(4, "RIGHT");
   
      public int value;
      public String name;
      
      MoveDirection(int value, String name) {
         this.value = value;
         this.name = name;
      }
   }
   
   protected String TAG = "Role";
   
   protected int x;
   protected int y;
   protected int speed;
   protected int visible;
   protected MoveDirection direction;
   protected int forwardFactor;
   protected int maxWidthPosition;
   protected int maxHeightPosition;
   
   public BaseRole() {
      Init();
   }
   
   protected void Init() {
      this.x = 0;
      this.y = 0;
      this.speed = 0;
      this.forwardFactor = 20;
      this.direction = MoveDirection.NONE;
   }
   
   public int getX() {
      return x;
   }
   
   public int getY() {
      return y;
   }
   
   public boolean isVisible() {
      return  (visible == View.VISIBLE);
   }
   
   public void setVisible(int visible) {
      this.visible = visible;
   }
   
   public void setForwardFactor(int forwardFactor) {
      this.forwardFactor = forwardFactor;
   }
   
   public int getForwardFactor() {
      return forwardFactor;
   }
   
   public boolean setDirection(MoveDirection direction) {
      if (this.direction == direction) return false;
      this.direction = direction;
      return true;
   }
   
   public void setPosition(int x, int y) {
      this.x = x;
      this.y = y;
   }
   
   public String toString() {
      return TAG + "-> x: " + x + ", y: " + y + ", speed: " + speed + ", direction: " + direction.name;
   }
}
