package com.demo.snakegame;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
	
	private final String TAG = "MainActivity";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Game game = new Game(this);
		try {
			game.Start();
		} catch (GameException exception) {
			Log.d(TAG, exception.toString());
		}
		
	}
}