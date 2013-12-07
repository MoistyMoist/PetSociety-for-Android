package com.petsociety.models;

import java.util.List;

public class Gallery {

	private int galleryID;
	private int userID;
	private int petID;
	private int locationID;
	private int eventID;

	private List<Image> images;

	public int getGalleryID() {
		return galleryID;
	}

	public void setGalleryID(int galleryID) {
		this.galleryID = galleryID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getPetID() {
		return petID;
	}

	public void setPetID(int petID) {
		this.petID = petID;
	}

	public int getLocationID() {
		return locationID;
	}

	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}
	
}
