package com.petsociety.models;

import java.util.List;

public class User {

	private int userID;
	private String name;
	private String email;
	private String birthday;
	private String password;
	private String address;
	private String biography;
	private String privicy;
	private char sex;
	private String contact;
	private String credibility;
	private double x;
	private double y;
	
	private String ProfileImageURL;
	
	
	
	//linked tables
	
	private Gallery gallery;
	
	private List<Attendee> attendees;
	private List<Event> events;
	private List<Lost> lost;
	private List<Pet> pets;
	private List<Location> locations;
	private List<Stray> strays;
	private List<Friend_List> friend_list;
	
	
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}
	public String getPrivicy() {
		return privicy;
	}
	public void setPrivicy(String privicy) {
		this.privicy = privicy;
	}
	public char getSex() {
		return sex;
	}
	public void setSex(char sex) {
		this.sex = sex;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getCredibility() {
		return credibility;
	}
	public void setCredibility(String credibility) {
		this.credibility = credibility;
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
	public String getProfileImageURL() {
		return ProfileImageURL;
	}
	public void setProfileImageURL(String profileImageURL) {
		ProfileImageURL = profileImageURL;
	}
	public Gallery getGallery() {
		return gallery;
	}
	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}
	public List<Attendee> getAttendees() {
		return attendees;
	}
	public void setAttendees(List<Attendee> attendees) {
		this.attendees = attendees;
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	public List<Lost> getLost() {
		return lost;
	}
	public void setLost(List<Lost> lost) {
		this.lost = lost;
	}
	public List<Pet> getPets() {
		return pets;
	}
	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}
	public List<Location> getLocations() {
		return locations;
	}
	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
	public List<Stray> getStrays() {
		return strays;
	}
	public void setStrays(List<Stray> strays) {
		this.strays = strays;
	}
	public List<Friend_List> getFriend_list() {
		return friend_list;
	}
	public void setFriend_list(List<Friend_List> friend_list) {
		this.friend_list = friend_list;
	}
	
}
