package com.mitogh.dicegame;

import java.util.Random;

public class Avatars {
	private Random mRandom = null;
	private int mAvatarNumber = 0;
	private int mActive = 0;
	private int mInactive = 0;
	
	public Avatars(){
		mRandom = new Random();
		update(1);
	}
	
	public void newAvatar(){
		mAvatarNumber = mRandom.nextInt(20) + 1;
		update(mAvatarNumber);
	}
	public int getAvatarActive(){
		return mActive;
	}
	
	public int getAvatarInactive(){
		return mInactive;
	}
	
	public void update(int number){
		switch(number){
		
		case 1:
			mActive = R.drawable.avatar1_active;
			mInactive = R.drawable.avatar1_inactive;
		break;
		
		case 2:
			mActive = R.drawable.avatar3_active;
			mInactive = R.drawable.avatar3_inactive;
		break;
		
		case 3:
			mActive = R.drawable.avatar3_active;
			mInactive = R.drawable.avatar3_inactive;
		break;
		
		case 4:
			mActive = R.drawable.avatar4_active;
			mInactive = R.drawable.avatar4_inactive;
		break;
		
		case 5:
			mActive = R.drawable.avatar5_active;
			mInactive = R.drawable.avatar5_inactive;
		break;
		
		case 6:
			mActive = R.drawable.avatar6_active;
			mInactive = R.drawable.avatar6_inactive;
		break;
		
		case 7:
			mActive = R.drawable.avatar7_active;
			mInactive = R.drawable.avatar7_inactive;
		break;
		
		case 8:
			mActive = R.drawable.avatar8_active;
			mInactive = R.drawable.avatar8_inactive;
		break;
		
		case 9:
			mActive = R.drawable.avatar9_active;
			mInactive = R.drawable.avatar9_inactive;
		break;
		
		case 10:
			mActive = R.drawable.avatar10_active;
			mInactive = R.drawable.avatar10_inactive;
		break;
		
		case 11:
			mActive = R.drawable.avatar11_active;
			mInactive = R.drawable.avatar11_inactive;
		break;
		
		case 12:
			mActive = R.drawable.avatar12_active;
			mInactive = R.drawable.avatar12_inactive;
		break;

		case 13:
			mActive = R.drawable.avatar13_active;
			mInactive = R.drawable.avatar13_inactive;
		break;
		
		case 14:
			mActive = R.drawable.avatar14_active;
			mInactive = R.drawable.avatar14_inactive;
		break;
		
		case 15:
			mActive = R.drawable.avatar15_active;
			mInactive = R.drawable.avatar15_inactive;
		break;
		
		case 16:
			mActive = R.drawable.avatar16_active;
			mInactive = R.drawable.avatar16_inactive;
		break;
		
		case 17:
			mActive = R.drawable.avatar17_active;
			mInactive = R.drawable.avatar17_inactive;
		break;
		
		case 18:
			mActive = R.drawable.avatar18_active;
			mInactive = R.drawable.avatar18_inactive;
		break;
		
		case 19:
			mActive = R.drawable.avatar19_active;
			mInactive = R.drawable.avatar19_inactive;
		break;
		
		case 20:
			mActive = R.drawable.avatar20_active;
			mInactive = R.drawable.avatar20_inactive;
		break;
		}
	}
}
