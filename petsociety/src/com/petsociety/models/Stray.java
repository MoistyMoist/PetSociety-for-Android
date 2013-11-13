package com.petsociety.models;

import java.util.List;

public class Stray {

	private int strayID;
	private String x;
	private String y;
	private String biography;
	private String title;
	private String timeSeen;
	private String DateSeen;
	private String type;
	private String breed;
	
	private Image image;
	private Pin pin;
	private List<Review> reviews;
	private User user;
	
	
	public int getStrayID() {
		return strayID;
	}
	public void setStrayID(int strayID) {
		this.strayID = strayID;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
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
	public String getTimeSeen() {
		return timeSeen;
	}
	public void setTimeSeen(String timeSeen) {
		this.timeSeen = timeSeen;
	}
	public String getDateSeen() {
		return DateSeen;
	}
	public void setDateSeen(String dateSeen) {
		DateSeen = dateSeen;
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
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	public Pin getPin() {
		return pin;
	}
	public void setPin(Pin pin) {
		this.pin = pin;
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
	
}
