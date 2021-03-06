package com.demo.snakegame.role;

import android.util.Log;

import com.demo.snakegame.HandleController;
import java.util.LinkedList;

public class Snake extends BaseRole implements ForwardInterface , HandleController.ActionControllerListener {
   
   private boolean isAutorun;
   private LinkedList<SnakeBody> body;
   
   public Snake(int maxWidthPosition, int maxHeightPosition) {
      super();
      this.TAG = "Snake";
      this.isAutorun = false;
      
      this.speed = 20;
      this.body = new LinkedList<>();
      this.body.add(new SnakeBody(this.forwardFactor, this.forwardFactor));
      
      this.maxWidthPosition = maxWidthPosition;
      this.maxHeightPosition = maxHeightPosition;
   }
   
   public void setAutorun(boolean autorun) {
      isAutorun = autorun;
   }
   
   public boolean isAutorun() {
      return this.isAutorun;
   }
   
   public SnakeBody getHead() {
      if (this.body.isEmpty()) return null;
      
      return this.body.get(0);
   }
   
   public SnakeBody getTail() {
      if (this.body.isEmpty()) return null;
      return this.body.get(this.body.size() -1);
   }
   
   public SnakeBody takeTail() {
      if (this.body.isEmpty()) return null;
      return this.body.remove(this.body.size() - 1);
   }
   
   public int getSize() {
      return this.body.size();
   }
   
   public SnakeBody getBody(int index) {
      return this.body.get(index);
   }
   
   public void growUp() {
      SnakeBody tail = getTail();
      this.body.add(new SnakeBody(tail.x, tail.y));
   }
   
   public boolean isEatingSelf() {
      SnakeBody head = getHead();
      for (int i = 1; i < getSize(); i ++) {
         SnakeBody body = getBody(i);
         if (head.comparePosition(body.x, body.y)) return true;
      }
      return false;
   }
   
   public boolean comparePosition(BaseRole role) {
      if (role == null) return false;
      for (int i = 0; i < getSize(); i ++) {
         SnakeBody snakeBody = getBody(i);
         if (snakeBody.comparePosition(role.x, role.y)) return true;
      }
      return false;
   }
   
   @Override
   public void forward() {
      SnakeBody head = getHead();
      SnakeBody newHead = takeTail();
      
      switch (this.direction) {
         case UP:
            newHead.x = head.x;
            if (head.y <= this.forwardFactor) {
               newHead.y = this.maxHeightPosition - (this.maxHeightPosition % this.forwardFactor);
            } else {
               newHead.y = head.y - speed;
            }
            break;
         case DOWN:
            newHead.x = head.x;
            if (head.y >= this.maxHeightPosition) {
               newHead.y = this.forwardFactor;
            } else {
               newHead.y = head.y + speed;
            }
            break;
         case LEFT:
            if (head.x <= this.forwardFactor) {
               newHead.x = this.maxWidthPosition - (this.maxWidthPosition % this.forwardFactor);
            } else {
               newHead.x = head.x - speed;
            }
            newHead.y = head.y;
            break;
         case RIGHT:
            if (head.x >= this.maxWidthPosition) {
               newHead.x = this.forwardFactor;
            } else {
               newHead.x = head.x + speed;
            }
            newHead.y = head.y;
            break;
      }
      
      this.x = newHead.x;
      this.y = newHead.y;
      
      this.body.add(0, newHead);
   }
   
   @Override
   public void forwardTo(int x, int y) {
      
      if (this.x > x) {
         onLeft();
      } else if (this.x < x) {
         onRight();
      } else if (this.y < y) {
         onDown();
      } else if (this.y > y) {
         onUp();
      }
      this.forward();
   }
   
   @Override
   public boolean onUp() {
      if (this.getSize() > 1 && this.direction == MoveDirection.DOWN) return false;
      Log.d(TAG, "on up");
      return setDirection(MoveDirection.UP);
   }
   
   @Override
   public boolean onDown() {
      if (this.getSize() > 1 && this.direction == MoveDirection.UP) return false;
      Log.d(TAG, "on down");
      return setDirection(MoveDirection.DOWN);
   }
   
   @Override
   public boolean onLeft() {
      if (this.getSize() > 1 && this.direction == MoveDirection.RIGHT) return false;
      Log.d(TAG, "on left");
      return setDirection(MoveDirection.LEFT);
   }
   
   @Override
   public boolean onRight() {
      if (this.getSize() > 1 && this.direction == MoveDirection.LEFT) return false;
      Log.d(TAG, "on right");
      return setDirection(MoveDirection.RIGHT);
   }
   
   @Override
   public boolean onCenter() {
      setAutorun(!this.isAutorun);
      return true;
   }
   public static class SnakeBody extends BaseRole {
      public SnakeBody(int x, int y) {
         super();
         this.TAG = "SnakeBody";
         this.x = x;
         this.y = y;
      }
      
      public boolean comparePosition(int x, int y) {
         return (this.x == x && this.y == y);
      }
   }
}
