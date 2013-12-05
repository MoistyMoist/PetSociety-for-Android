package com.petsociety.models;

import java.sql.Date;

public class Review {

	private int reviewID;
	private String description;
	private String title;
	private String likes;
	private String dislikes;
	private Date dateTimeCreated;
	private int locationID;
	private int userID;
	private int strayID;
	
	
	//link tables
	private Location location;
	public int getReviewID() {
		return reviewID;
	}
	public void setReviewID(int reviewID) {
		this.reviewID = reviewID;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLikes() {
		return likes;
	}
	public void setLikes(String likes) {
		this.likes = likes;
	}
	public String getDislikes() {
		return dislikes;
	}
	public void setDislikes(String dislikes) {
		this.dislikes = dislikes;
	}
	public Date getDateTimeCreated() {
		return dateTimeCreated;
	}
	public void setDateTimeCreated(Date dateTimeCreated) {
		this.dateTimeCreated = dateTimeCreated;
	}
	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getStrayID() {
		return strayID;
	}
	public void setStrayID(int strayID) {
		this.strayID = strayID;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Stray getStray() {
		return stray;
	}
	public void setStray(Stray stray) {
		this.stray = stray;
	}
	private User user;
	private Stray stray;
	
	
	
}
