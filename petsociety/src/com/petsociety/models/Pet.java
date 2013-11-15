package com.petsociety.models;

public class Pet {
	
	private int petID;
	private String name;
	private String breed;
	private String sex;
	private String type;
	private String age;
	private String biography;
	
	private Gallery gallery;
	private User user;
	private Pin pin;
	private Image profileImage;
	
	
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
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
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
	public Pin getPin() {
		return pin;
	}
	public void setPin(Pin pin) {
		this.pin = pin;
	}
	public Image getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(Image profileImage) {
		this.profileImage = profileImage;
	}
	
	
}
