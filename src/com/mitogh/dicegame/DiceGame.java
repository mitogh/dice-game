package com.mitogh.dicegame;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DiceGame extends Activity {

	// GUI Elements
	private TextView mPlayer1;
	private TextView mPlayer2;
	private TextView mPoints;
	private Button mHold;
	private Button mRol;
	
	// Internal elements
	private Random random = null;
	private int number = 1;
	private Player[] players = {
			new Player(),
			new Player() 
	};
	private int mCurrentPlayer = 0;
	private String[] mMessages = null;
	private int total = 0;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_game);
        
        mMessages = getResources().getStringArray(R.array.messages);
        random = new Random();
        mPlayer1 = (TextView) findViewById(R.id.player1);
        mPlayer1.setText("Player 1, score: " + players[0].getScore());
        mPlayer2 = (TextView) findViewById(R.id.player2);
        mPlayer2.setText("Player 2, score: " + players[1].getScore());
        
        mPoints = (TextView) findViewById(R.id.points);
        mPoints.setText("" + 0);
        
        mHold = (Button) findViewById(R.id.hold_button);
        mHold.setEnabled(false);
        mHold.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				players[mCurrentPlayer].setScore(total);
				if(players[mCurrentPlayer].getScore() >= 100){
					mPlayer1.setText("Player " + (mCurrentPlayer + 1) + " wins");
				}else{
					if(mCurrentPlayer == 0){
						mCurrentPlayer = 1;
						mPlayer1.setText("Player 1, score: " + players[0].getScore());
					}else{
						mCurrentPlayer = 0;
						mPlayer2.setText("Player 2, score: " + players[1].getScore());
					}
				}
				mHold.setEnabled(false);
				total = 0;
			}
		});
        
        mRol = (Button) findViewById(R.id.rol_button);
        mRol.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				number = random.nextInt(6) + 1;
				if(number == 1){
					mHold.setEnabled(false);
					total = 0;
				}else{
					total += number;
					mHold.setEnabled(true);
				}
				mPoints.setText("" + total);
				
				AlertDialog.Builder builder = new AlertDialog.Builder(DiceGame.this);
				builder.setMessage(mMessages[number - 1])
					.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
				builder.create();
				builder.show();
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