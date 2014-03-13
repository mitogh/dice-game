package com.mitogh.dicegame;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
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
        // Player 1 GUI
        mPlayersGUI[0] = (TextView) findViewById(R.id.player1);
        mPlayersGUI[0].setText("Score: 0");
        players[0].setName("Player 1");
        // Player 2 GUI
        mPlayersGUI[1] = (TextView) findViewById(R.id.player2);
        mPlayersGUI[1].setText("Score: 0");
        players[1].setName("Player 2");
        
        mPoints = (TextView) findViewById(R.id.points);
        
        mHold = (Button) findViewById(R.id.hold_button);
        mHold.setEnabled(false);
        
        mRol = (Button) findViewById(R.id.rol_button);
        mPlayersGUI[mCurrentPlayer].setTextColor(Color.parseColor("#CC0000"));
        
        mRol.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Random number from 1 to 6;
				number = random.nextInt(6) + 1;
				playSound();
				if(number == 1){
					total = 0;
					updateScore();
					gameFlow();
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
				updateScore();
				if(thereIsWinner()){
					updateWinnerMessage();
				}else{
					gameFlow();
				}
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
     * Check if there is a winner player
     * @return	boolean		Returns true if the current player wins the game 
     * 						false if not.
     */
    private boolean thereIsWinner(){
    	return players[mCurrentPlayer].isWinner(); 
    }
    
    /**
     * Reset the values for the next player, score and total of points
     */
    private void gameFlow(){
    	nextPlayer();
    	total = 0;
    	// Disable hold button
    	mHold.setEnabled(false);
    }
    /**
     *	Update the message of the GUI with the name of the new winner of the Game. 
     */
    private void updateWinnerMessage(){
    	total = 0; 
    	mPoints.setText(players[mCurrentPlayer].getName() + " is the winner!");
    }
    
    /**
     * Updates the current player score at the board
     * with the latest score.
     */
    private void updateScore(){
    	players[mCurrentPlayer].setScore(total);
    	mPlayersGUI[mCurrentPlayer].setText("Score: " + players[mCurrentPlayer].getScore());
    }
    
    /**
     * This function allows to change to the next player in the players
     * array, when reaches the last player restart to the first player.
     */
    private void nextPlayer(){
    	mPlayersGUI[mCurrentPlayer].setTextColor(Color.parseColor("#000000"));
    	mCurrentPlayer = (mCurrentPlayer + 1) % players.length;
    	mPlayersGUI[mCurrentPlayer].setTextColor(Color.parseColor("#CC0000"));
    }
    
    private void playSound(){
    	MediaPlayer player = MediaPlayer.create(this, R.raw.rol_dice);
    	player.start();
    	player.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
    }
}