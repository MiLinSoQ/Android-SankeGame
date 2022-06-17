package com.demo.snakegame;

class GameException extends Exception{
   
   public static final int NONA = -1;
   public static final int INIT_ERROR = 0;
   
   private int errorCode;
   
   GameException(String message, int errorCode) {
      super(message);
      this.errorCode = errorCode;
   }
   
   GameException(String message) {
      this(message, NONA);
   }
   
   public int getErrorCode() {
      return errorCode;
   }
}
