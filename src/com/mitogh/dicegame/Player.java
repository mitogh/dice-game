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
	public void setScore(int score) {
		if(score >= 0 && score <= 6)	mScore = score;
	}
}