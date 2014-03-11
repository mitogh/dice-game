package com.mitogh.dicegame;

import java.util.Random;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DiceGame extends Activity {

	// GUI Elements
	private TextView mPlayer1;
	private TextView mPlayer2;
	private Button mHold;
	private Button mRol;
	
	// Internal elements
	private Random random = null;
	private int number = 1;
	private int[] score = {0, 0};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_game);
        
        random = new Random();
        mPlayer1 = (TextView) findViewById(R.id.player1);
        mPlayer2 = (TextView) findViewById(R.id.player2);
        
        mHold = (Button) findViewById(R.id.hold_button);
        mRol = (Button) findViewById(R.id.rol_button);
        mRol.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dice_game, menu);
        return true;
    }
    
}
