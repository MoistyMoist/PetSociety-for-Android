package com.petsociety.models;

public class Achievement {

	private int achievementID;
	private String title;
	private String type;
	private String rank;
	private String condition;
	
	
	public int getAchievementID() {
		return achievementID;
	}
	public void setAchievementID(int achievementID) {
		this.achievementID = achievementID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
}
