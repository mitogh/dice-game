package com.mitogh.dicegame;

public class Player {
	private String mName = "";
	private int mScore = 0;
	
	public String getName() {
		return mName;
	}
	public void setName(String name) {
		mName = name;
	}
	public int getScore() {
		return mScore;
	}
	
	public void resetScore(){
		mScore = 0;
	}
	
	public void addPoints(int score) {
		if(mScore >= 0) 	mScore += score;
	}
	
	public boolean isWinner(){
		if(mScore >= 100) 	return true;
		else				return false;
	}
}