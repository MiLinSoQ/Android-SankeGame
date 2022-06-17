package com.demo.snakegame.role;

import android.util.Log;
import android.view.View;

public class Food extends BaseRole {
	
	public Food(int maxWidthPosition, int maxHeightPosition) {
		super();
		setVisible(View.GONE);
		this.TAG = "Food";
		
		this.maxWidthPosition = maxWidthPosition;
		this.maxHeightPosition = maxHeightPosition;
	}
	
	public void randomPosition() {
		this.x = (int) (Math.random() * maxWidthPosition + this.forwardFactor);
		this.y = (int) (Math.random() * maxHeightPosition + this.forwardFactor);
		
		this.x -= (this.x % this.forwardFactor);
		this.y -= (this.y % this.forwardFactor);
		
		Log.d(TAG, "Rand -> x: " + this.x + ", y: " + this.y);
	}
}
