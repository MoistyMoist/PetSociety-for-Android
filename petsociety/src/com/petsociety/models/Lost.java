package com.petsociety.models;

import java.util.Date;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Lost implements ClusterItem {

	private int lostID;
	private Date dateTimeSeen;
	private String address;
	private String description;
	private double x;
	private double y;
	private char found;
	private String reward;
	private Date dateTimeCreated;
	private int petID;
	private int userID;
	
	private LatLng mPosition;
	
	private Pet pet;
	private User user;
	
	
	
	public int getLostID() {
		return lostID;
	}
	public void setLostID(int lostID) {
		this.lostID = lostID;
	}
	public Date getDateTimeSeen() {
		return dateTimeSeen;
	}
	public void setDateTimeSeen(Date dateTimeSeen) {
		this.dateTimeSeen = dateTimeSeen;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public char getFound() {
		return found;
	}
	public void setFound(char found) {
		this.found = found;
	}
	public String getReward() {
		return reward;
	}
	public void setReward(String reward) {
		this.reward = reward;
	}
	public Date getDateTimeCreated() {
		return dateTimeCreated;
	}
	public void setDateTimeCreated(Date dateTimeCreated) {
		this.dateTimeCreated = dateTimeCreated;
	}
	public int getPetID() {
		return petID;
	}
	public void setPetID(int petID) {
		this.petID = petID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public Pet getPet() {
		return pet;
	}
	public void setPet(Pet pet) {
		this.pet = pet;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public LatLng getPosition() {
		// TODO Auto-generated method stub
		mPosition = new LatLng(x,y);
		return mPosition;
	}
	
	
}
