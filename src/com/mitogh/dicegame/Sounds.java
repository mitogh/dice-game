package com.mitogh.dicegame;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class Sounds {
	private Context mContext = null;
	private int mResID;
	
	public Sounds(Context context){
		mContext = context;
	}
	
	public void rollSound(){
		mResID = R.raw.roll_dice;
	}
	
	public void pigSound(){
		mResID = R.raw.pig;
	}
	
	public void play(){
    	MediaPlayer player = MediaPlayer.create(mContext, mResID);
    	player.start();
    	player.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
	}
}
