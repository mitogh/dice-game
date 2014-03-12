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
	private TextView[] mPlayersGUI = {
			null,
			null
	};
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
        mPlayersGUI[0] = (TextView) findViewById(R.id.player1);
        mPlayersGUI[0].setText("Player 1, score: " + players[0].getScore());
        mPlayersGUI[1] = (TextView) findViewById(R.id.player2);
        mPlayersGUI[1].setText("Player 2, score: " + players[1].getScore());
        
        mPoints = (TextView) findViewById(R.id.points);
        mPoints.setText("" + 0);
        
        mHold = (Button) findViewById(R.id.hold_button);
        mHold.setEnabled(false);
        mHold.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			}
		});
        
        mRol = (Button) findViewById(R.id.rol_button);
        mRol.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				number = random.nextInt(6) + 1;
				if(number == 1){
					total = 0;
					gameFlow();
					mHold.setEnabled(false);
				}else{
					total += number;
					mPoints.setText("Total = " + total);
					mHold.setEnabled(true);
				}
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
        
        mHold.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				gameFlow();
				
			}
		});
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dice_game, menu);
        return true;
    }
    
    /**
     * Reset the values for the next player, score and total of points
     */
    public void gameFlow(){
    	updateScore();
    	nextPlayer();
    	total = 0;
    	// Disable hold button
    	mHold.setEnabled(false);
    }
    
    /**
     * Updates the current player score at the board
     * with the latest score.
     */
    public void updateScore(){
    	players[mCurrentPlayer].setScore(total);
    	mPlayersGUI[mCurrentPlayer].setText("Score: " + players[mCurrentPlayer].getScore());
    }
    
    /**
     * This function allows to change to the next player in the players
     * array, when reaches the last player restart to the first player.
     */
    public void nextPlayer(){
    	mCurrentPlayer = (mCurrentPlayer + 1) % players.length;
    }


}