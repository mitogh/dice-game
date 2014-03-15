package com.mitogh.dicegame;

import java.util.Random;

import android.app.Activity;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DiceGame extends Activity {

	// GUI Elements
	private TextView[] mPlayersGUI = {
			null,
			null
	};
	private LinearLayout mPlayerActive;
	private LinearLayout mPlayerInactive;
	private TextView mRounds;
	private TextView mPoints;
	private TextView mEncouragementMessage;
	private Button mHold;
	private Button mRol;
	
	private EditText mPlayer1;
	private EditText mPlayer2;
	
	// Internal elements
	private Random random = null;
	private int number = 1;
	private Player[] players = {
			new Player(),
			new Player() 
	};
	private int mCurrentPlayer = 1;
	private int total = 0;
	private int round = 1;
	private Message message;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_game);
        message = new Message(this);
        mRounds = (TextView) findViewById(R.id.round);
        Typeface playball = Typeface.createFromAsset(getAssets(), "fonts/playball.ttf");
        mEncouragementMessage = (TextView) findViewById(R.id.encouragement_message);
        
        Typeface roboto_black = Typeface.createFromAsset(getAssets(), "fonts/roboto_black.ttf");
        mRounds.setTypeface(roboto_black);
        mRounds.setText(getString(R.string.round) + " " + Integer.toString(round));
        
        mPlayer1 = (EditText) findViewById(R.id.editText_player1_name);
        mPlayer1.setTypeface(playball);
        mPlayer2 = (EditText) findViewById(R.id.editText_player2_name);
        mPlayer2.setTypeface(playball);
        
        mPlayerActive = (LinearLayout) findViewById(R.id.first);
        mPlayerInactive = (LinearLayout) findViewById(R.id.second);
        updatePlayerColor();
        
        random = new Random();
        // Player 1 GUI
        mPlayersGUI[0] = (TextView) findViewById(R.id.player1);
        mPlayersGUI[0].setText("0");
        players[0].setName(mPlayer1.getText().toString());
        // Player 2 GUI
        mPlayersGUI[1] = (TextView) findViewById(R.id.player2);
        mPlayersGUI[1].setText("0");
        players[0].setName(mPlayer2.getText().toString());
        
        mPoints = (TextView) findViewById(R.id.points);
        
        mHold = (Button) findViewById(R.id.button_hold);
        mHold.setEnabled(false);
        
        mRol = (Button) findViewById(R.id.button_roll);
        mRol.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Random number from 1 to 6;
				number = random.nextInt(6) + 1;
				message.setNumber(number);
				playRollSound();
				if(number == 1){
					total = 0;
					playPigSound();
					updateScore();
					gameFlow();
				}else{
					total += number;
					mPoints.setText("Total = " + total);
					mHold.setEnabled(true);
				}
				mEncouragementMessage.setText(message.getMessage());
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
    	mPlayersGUI[mCurrentPlayer].setText("" + players[mCurrentPlayer].getScore());
    }
    
    /**
     * This function allows to change to the next player in the players
     * array, when reaches the last player restart to the first player.
     */
    private void nextPlayer(){
    	updateRound();
    	mCurrentPlayer = (mCurrentPlayer + 1) % players.length;
    	updatePlayerColor();
    }
    
    private void updatePlayerColor(){
    	if(mCurrentPlayer == 1){
    		mPlayer1.setTextColor(getResources().getColor(R.color.white));
    		mPlayer2.setTextColor(getResources().getColor(R.color.gray));
            mPlayerActive.setBackgroundDrawable(getResources().getDrawable(R.drawable.versus_background_left_active));
            mPlayerInactive.setBackgroundDrawable(getResources().getDrawable(R.drawable.versus_background_right_inactive));
    	}else{
    		mPlayer2.setTextColor(getResources().getColor(R.color.white));
    		mPlayer1.setTextColor(getResources().getColor(R.color.gray));
            mPlayerActive.setBackgroundDrawable(getResources().getDrawable(R.drawable.versus_background_left_inactive));
            mPlayerInactive.setBackgroundDrawable(getResources().getDrawable(R.drawable.versus_background_right_active));
    	}
    }
    
    private void updateRound(){
    	if(mCurrentPlayer == 1){
    		round++;
            mRounds.setText(getString(R.string.round) + " " + Integer.toString(round));
    	}
    }

    private void playPigSound(){
    	MediaPlayer player = MediaPlayer.create(this, R.raw.pig);
    	player.start();
    	player.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
    }
    
    private void playRollSound(){
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