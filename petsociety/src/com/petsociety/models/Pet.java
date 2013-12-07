package com.petsociety.models;

import java.sql.Date;

public class Pet {
	
	private int petID;
	private String name;
	private String breed;
	private char sex;
	private String type;
	private String age;
	private String biography;
	private int userID;
	private int galleryID;
	private String profileImageURL;
	private Date dateTimeCreated;
	
	private Gallery gallery;
	private User user;
	
	
	public int getPetID() {
		return petID;
	}
	public void setPetID(int petID) {
		this.petID = petID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBreed() {
		return breed;
	}
	public void setBreed(String breed) {
		this.breed = breed;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getGalleryID() {
		return galleryID;
	}
	public void setGalleryID(int galleryID) {
		this.galleryID = galleryID;
	}
	public String getProfileImageURL() {
		return profileImageURL;
	}
	public void setProfileImageURL(String profileImageURL) {
		this.profileImageURL = profileImageURL;
	}
	public Date getDateTimeCreated() {
		return dateTimeCreated;
	}
	public void setDateTimeCreated(Date dateTimeCreated) {
		this.dateTimeCreated = dateTimeCreated;
	}
	public Gallery getGallery() {
		return gallery;
	}
	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	

	
}
