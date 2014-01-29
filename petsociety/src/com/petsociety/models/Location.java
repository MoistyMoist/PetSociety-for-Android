package com.petsociety.models;

import java.sql.Date;
import java.util.List;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class Location implements ClusterItem {

	private int locationID;
	private double x;
	private double y;
	private String description;
	private String title;
	private String address;
	private String type;
	private Date dateTImeCreated;
	private int userID;
	private int galleryID;
	
	private LatLng mPosition;
	
	private User user;
	private List<Review> reviews;
	private Gallery gallery;
	

	public int getLocationID() {
		return locationID;
	}
	public void setLocationID(int locationID) {
		this.locationID = locationID;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Date getDateTImeCreated() {
		return dateTImeCreated;
	}
	public void setDateTImeCreated(Date dateTImeCreated) {
		this.dateTImeCreated = dateTImeCreated;
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<Review> getReviews() {
		return reviews;
	}
	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	public Gallery getGallery() {
		return gallery;
	}
	public void setGallery(Gallery gallery) {
		this.gallery = gallery;
	}
	@Override
	public LatLng getPosition() {
		// TODO Auto-generated method stub
		mPosition = new LatLng(x,y);
		return mPosition;
	}

	
}
