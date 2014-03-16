package com.mitogh.dicegame;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DiceGame extends Activity {

	// GUI Elements
	private TextView mPlayer1Points;
	private TextView mPlayer2Points;
	private LinearLayout mPlayerActive;
	private LinearLayout mPlayerInactive;
	private TextView mRounds;
	private TextView mPoints;
	private TextView mPointsLabelPlayer1;
	private TextView mPointsLabelPlayer2;
	private TextView mEncouragementMessage;
	private Button mHold;
	private Button mRoll;
	private ImageButton mAvatarPLayer1;
	private ImageButton mAvatarPLayer2;
	private EditText mPlayer1Name;
	private EditText mPlayer2Name;
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
	private Avatars avatarPlayer1;
	private Avatars avatarPlayer2;
	private Message message;
	private Sounds mSounds;
	private AlphaAnimation alphaAnimation;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice_game);

        // Layouts
        mPlayerActive = (LinearLayout) findViewById(R.id.first);
        mPlayerInactive = (LinearLayout) findViewById(R.id.second);        
        // EditText
        mPlayer1Name = (EditText) findViewById(R.id.editText_player1_name);
        mPlayer2Name = (EditText) findViewById(R.id.editText_player2_name);
        // TextView
        mRounds = (TextView) findViewById(R.id.round);
        mPoints = (TextView) findViewById(R.id.points);
        
        mPlayer1Points = (TextView) findViewById(R.id.player1);
        mPlayer2Points = (TextView) findViewById(R.id.player2);
        
        mPointsLabelPlayer1 = (TextView) findViewById(R.id.player1_points_label);
        mPointsLabelPlayer2 = (TextView) findViewById(R.id.player2_points_label);
        
        mEncouragementMessage = (TextView) findViewById(R.id.encouragement_message);
        // Buttons
        mHold = (Button) findViewById(R.id.button_hold);
        mRoll = (Button) findViewById(R.id.button_roll);
        
        mAvatarPLayer1 = (ImageButton) findViewById(R.id.avatar_player1);
        mAvatarPLayer2 = (ImageButton) findViewById(R.id.avatar_player2);
        // Animation
        mDice =  (ImageView) findViewById(R.id.dice); 
        
        setUP();

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
    	return (players[0].isWinner() || players[1].isWinner()); 
    }
    
    private void gameFlow(){
    	total = 0;    	
    	mHold.setEnabled(false);
    	new AlertDialog.Builder(this)
        .setTitle(getResources().getString(R.string.next_turn))
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
            	nextPlayer();
            }
         })
        .setIcon(R.drawable.next)
        .show();
    }

    private void updateWinnerMessage(){
    	String winner = (players[0].isWinner()) ? mPlayer1Name.getText().toString() : mPlayer2Name.getText().toString();
    	
    	new AlertDialog.Builder(this)
        .setTitle(getResources().getString(R.string.have_winner))
        .setMessage(getResources().getString(R.string.congratulations) + " " + winner)
        .setPositiveButton(getResources().getString(R.string.new_game), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) { 
            	clearGame();
            }
         })
        .setIcon(R.drawable.winner)
        .show();
    }
    
    private void updateRound(){
    	if(mCurrentPlayer == 1){
    		round++;
            mRounds.setText(getString(R.string.round) + " " + Integer.toString(round));
    	}
    }
    
    private void updateScore(){
    	if(mCurrentPlayer == 1){ 
    		players[0].setScore(total);
    		mPlayer1Points.setText("" + players[0].getScore());
    	}else{
    		players[1].setScore(total);
    		mPlayer2Points.setText("" + players[1].getScore());
    	}
    }
    
    private void nextPlayer(){
    	updateRound();
    	mCurrentPlayer = (mCurrentPlayer + 1) % players.length;
    	updatePlayersColor();
    }
    
    private void clearGame(){
        mPlayer1Points.setText("0");
        mPlayer2Points.setText("0");
        round = 1;
        mCurrentPlayer = 1;
        mHold.setEnabled(false);
    }
    
    public void setUP(){        
    	setTypeFace();
        avatarPlayer1 = new Avatars();
        avatarPlayer1.newAvatar();
        avatarPlayer2 = new Avatars();
        avatarPlayer2.newAvatar();
    	updatePlayersColor();
    	mRounds.setText(getString(R.string.round) + " " + Integer.toString(round));
    	clearGame();

        mSounds = new Sounds(this);
        message = new Message(this);
        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(1000);
        random = new Random();
    }
    
    public void setTypeFace(){
        Typeface playball = Typeface.createFromAsset(getAssets(), "fonts/playball.ttf");
        Typeface roboto_black = Typeface.createFromAsset(getAssets(), "fonts/roboto_black.ttf");
        Typeface roboto_bold = Typeface.createFromAsset(getAssets(), "fonts/roboto_bold.ttf");
        Typeface numbers = Typeface.createFromAsset(getAssets(), "fonts/pixel.ttf");
        
        mRounds.setTypeface(roboto_bold);
        mPoints.setTypeface(numbers);
        mPlayer1Name.setTypeface(playball);
        mPlayer2Name.setTypeface(playball);
        
        mPlayer1Points.setTypeface(roboto_black);
        mPlayer2Points.setTypeface(roboto_black);
        
        mPointsLabelPlayer1.setTypeface(roboto_black);
        mPointsLabelPlayer2.setTypeface(roboto_black);
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
       
	private void updatePlayersColor(){
    	if(mCurrentPlayer == 1){
    		mPlayer1Name.setTextColor(getResources().getColor(R.color.white));
    		mPlayer2Name.setTextColor(getResources().getColor(R.color.gray));
            
    		mPlayer1Points.setTextColor(getResources().getColor(R.color.black));
    		mPlayer2Points.setTextColor(getResources().getColor(R.color.light_dark));
    		
    		mPointsLabelPlayer1.setTextColor(getResources().getColor(R.color.black));
    		mPointsLabelPlayer2.setTextColor(getResources().getColor(R.color.light_dark));
    		
    		mPlayerActive.setBackgroundResource(R.drawable.versus_background_left_active);
            mPlayerInactive.setBackgroundResource(R.drawable.versus_background_right_inactive);
            mAvatarPLayer1.setBackgroundResource(avatarPlayer1.getAvatarActive());
            mAvatarPLayer2.setBackgroundResource(avatarPlayer2.getAvatarInactive());
    	}else{
    		mPlayer2Name.setTextColor(getResources().getColor(R.color.white));
    		mPlayer1Name.setTextColor(getResources().getColor(R.color.gray));
    		
    		mPlayer1Points.setTextColor(getResources().getColor(R.color.light_dark));
    		mPlayer2Points.setTextColor(getResources().getColor(R.color.black));
    		
    		mPointsLabelPlayer2.setTextColor(getResources().getColor(R.color.black));
    		mPointsLabelPlayer1.setTextColor(getResources().getColor(R.color.light_dark));
    		
    		mPlayerActive.setBackgroundResource(R.drawable.versus_background_left_inactive);
            mPlayerInactive.setBackgroundResource(R.drawable.versus_background_right_active);
            
            mAvatarPLayer1.setBackgroundResource(avatarPlayer1.getAvatarInactive());
            mAvatarPLayer2.setBackgroundResource(avatarPlayer2.getAvatarActive());
    	}
    }
}