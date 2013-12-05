package com.petsociety.models;

import java.sql.Date;
import java.util.List;

public class Stray {

	private int strayID;
	private double x;
	private double y;
	private String biography;
	private String title;
	private Date dateTimeSeen;
	private String type;
	private String breed;
	private int status;
	private String imageURl;
	private int userID;
	
	
	//link tables
	private List<Review> reviews;
	private User user;
	
	
	public int getStrayID() {
		return strayID;
	}
	public void setStrayID(int strayID) {
		this.strayID = strayID;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getDateTimeSeen() {
		return dateTimeSeen;
	}
	public void setDateTimeSeen(Date dateTimeSeen) {
		this.dateTimeSeen = dateTimeSeen;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBreed() {
		return breed;
	}
	public void setBreed(String breed) {
		this.breed = breed;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getImageURl() {
		return imageURl;
	}
	public void setImageURl(String imageURl) {
		this.imageURl = imageURl;
	}
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
}