package com.mitogh.dicegame;

import java.util.Random;

import android.content.Context;
import android.util.Log;

public class Message{
	private Random random;
	private int mNumber = 1;
	private Context mContext;
	private String[] messages = null;
	
	public Message(Context context){
		random = new Random();
		mContext = context;
	}
	
	public void setNumber(int number){
		if(number >= 1 && number <= 6) mNumber = number;
	}
	
	public String getMessage(){
		
		
		switch(mNumber) {
		case 1:
			messages = mContext.getResources().getStringArray(R.array.messages_one);
			break;
			
		case 2:
			messages = mContext.getResources().getStringArray(R.array.messages_two);
			break;
			
		case 3:
			messages = mContext.getResources().getStringArray(R.array.messages_three);
			break;
			
		case 4:
			messages = mContext.getResources().getStringArray(R.array.messages_four);
			break;

		case 5:
			messages = mContext.getResources().getStringArray(R.array.messages_five);
			break;
			
		case 6:
			messages = mContext.getResources().getStringArray(R.array.messages_six);
			break;
		}
	
		Log.d("MITO", "" + messages.length);	
		return messages[random.nextInt(messages.length)];
	}
}
