package com.mitogh.dicegame;

import java.util.Random;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
	private TextView mPointsLabelPlayer1;
	private TextView mPointsLabelPlayer2;
	private TextView mEncouragementMessage;
	private Button mHold;
	private Button mRoll;
	private EditText mPlayer1;
	private EditText mPlayer2;
	private ImageView mDice;
	
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
	private Sounds mSounds;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_game);
        
        // Search for each ID
        
        // Layouts
        mPlayerActive = (LinearLayout) findViewById(R.id.first);
        mPlayerInactive = (LinearLayout) findViewById(R.id.second);        
        // EditText
        mPlayer1 = (EditText) findViewById(R.id.editText_player1_name);
        mPlayer2 = (EditText) findViewById(R.id.editText_player2_name);
        // TextView
        mRounds = (TextView) findViewById(R.id.round);
        mPlayersGUI[0] = (TextView) findViewById(R.id.player1);
        mPlayersGUI[1] = (TextView) findViewById(R.id.player2);
        mPoints = (TextView) findViewById(R.id.points);
        mPointsLabelPlayer1 = (TextView) findViewById(R.id.player1_points_label);
        mPointsLabelPlayer2 = (TextView) findViewById(R.id.player2_points_label);
        mEncouragementMessage = (TextView) findViewById(R.id.encouragement_message);
        // Buttons
        mHold = (Button) findViewById(R.id.button_hold);
        mRoll = (Button) findViewById(R.id.button_roll);
        
        mDice =  (ImageView) findViewById(R.id.dice); 
        // Typeface
        Typeface playball = Typeface.createFromAsset(getAssets(), "fonts/playball.ttf");
        Typeface roboto_black = Typeface.createFromAsset(getAssets(), "fonts/roboto_black.ttf");
        Typeface roboto_bold = Typeface.createFromAsset(getAssets(), "fonts/roboto_bold.ttf");
        
        mRounds.setTypeface(roboto_bold);
        mPlayer1.setTypeface(playball);
        mPlayer2.setTypeface(playball);
        
        mRounds.setText(getString(R.string.round) + " " + Integer.toString(round));
        updatePlayersColor();
        
        random = new Random();
        
        mPlayersGUI[0].setText("0");
        mPlayersGUI[0].setTypeface(roboto_black);
        mPointsLabelPlayer1.setTypeface(roboto_black);
        mPointsLabelPlayer2.setTypeface(roboto_black);
        
        players[0].setName(mPlayer1.getText().toString());
        mPlayersGUI[0].setTextColor(getResources().getColor(R.color.black));
        mPointsLabelPlayer1.setTextColor(getResources().getColor(R.color.black));
        
        mPlayersGUI[1].setText("0");
        mPlayersGUI[1].setTypeface(roboto_black);
        mPlayersGUI[1].setTextColor(getResources().getColor(R.color.light_dark));
        mPointsLabelPlayer2.setTextColor(getResources().getColor(R.color.light_dark));
        
        players[0].setName(mPlayer2.getText().toString());
        mHold.setEnabled(false);
        mSounds = new Sounds(this);
        message = new Message(this);
        final AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1000);
        
        mRoll.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				// Random number from 1 to 6;
				number = random.nextInt(6) + 1;
				message.setNumber(number);
				mSounds.rollSound();
				mSounds.play();
				animateDice(number);
				mPoints.startAnimation(alphaAnimation);
				mEncouragementMessage.startAnimation(alphaAnimation);
				if(number == 1){
					total = 0;
					updateScore();
					mPoints.setText("0");
					gameFlow();
				}else{
					total += number;
					mPoints.setText("" + total);
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
    
    private boolean thereIsWinner(){
    	return players[mCurrentPlayer].isWinner(); 
    }
    

    private void gameFlow(){
    	nextPlayer();
    	total = 0;
    	// Disable hold button
    	mHold.setEnabled(false);
    }

    private void updateWinnerMessage(){
    	total = 0; 
    	mPoints.setText(players[mCurrentPlayer].getName() + " is the winner!");
    }
    
    
    private void animateDice(int number){    
    	boolean isPig = false;
    	    	
    	switch(number){
    	case 1:    		
    		isPig = true;
    		mDice.setBackgroundResource(R.drawable.dice_animation_pig);
    		break;
    	case 2:
    		mDice.setBackgroundResource(R.drawable.dice_animation_two);
    		break;
    	case 3:
    		mDice.setBackgroundResource(R.drawable.dice_animation_three);
    		break;
    	case 4:
    		mDice.setBackgroundResource(R.drawable.dice_animation_four);
    		break;
    	case 5:
    		mDice.setBackgroundResource(R.drawable.dice_animation_five);
    		break;
    	case 6:
    		mDice.setBackgroundResource(R.drawable.dice_animation_six);
    		break;
    	}
        AnimationDrawable diceAnimation = (AnimationDrawable) mDice.getBackground();    	
    	if(diceAnimation.isRunning()){
    		diceAnimation.stop();
    	}
    	diceAnimation.start();
    	if(isPig){
    		mSounds.pigSound();
    		mSounds.play();
    	}
    }
    
    private void updateScore(){
    	int number = 0; 
    	if(mCurrentPlayer == 1){
    		number = 0; 
    	}else{
    		number = 1;
    	}
    	
    	players[number].setScore(total);
    	mPlayersGUI[number].setText("" + players[number].getScore());
    }
    
    private void nextPlayer(){
    	updateRound();
    	mCurrentPlayer = (mCurrentPlayer + 1) % players.length;
    	updatePlayersColor();
    }
    
    private void updatePlayersColor(){
    	if(mCurrentPlayer == 1){
    		mPlayer1.setTextColor(getResources().getColor(R.color.white));
    		mPlayer2.setTextColor(getResources().getColor(R.color.gray));
            
    		mPlayersGUI[0].setTextColor(getResources().getColor(R.color.black));
    		mPlayersGUI[1].setTextColor(getResources().getColor(R.color.light_dark));
    		
    		mPointsLabelPlayer1.setTextColor(getResources().getColor(R.color.black));
    		mPointsLabelPlayer2.setTextColor(getResources().getColor(R.color.light_dark));
    		
    		mPlayerActive.setBackgroundDrawable(getResources().getDrawable(R.drawable.versus_background_left_active));
            mPlayerInactive.setBackgroundDrawable(getResources().getDrawable(R.drawable.versus_background_right_inactive));
    	}else{
    		mPlayer2.setTextColor(getResources().getColor(R.color.white));
    		mPlayer1.setTextColor(getResources().getColor(R.color.gray));
    		
    		mPlayersGUI[0].setTextColor(getResources().getColor(R.color.light_dark));
    		mPlayersGUI[1].setTextColor(getResources().getColor(R.color.black));
    		
    		mPointsLabelPlayer2.setTextColor(getResources().getColor(R.color.black));
    		mPointsLabelPlayer1.setTextColor(getResources().getColor(R.color.light_dark));
    		
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
}