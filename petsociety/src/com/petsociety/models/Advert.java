package com.petsociety.models;

public class Advert {

	private int advertID;
	private Organization organization;
	private String title;
	private String description;
	private String dateCreated;
	private String duration;
	
	
	public int getAdvertID() {
		return advertID;
	}
	public void setAdvertID(int advertID) {
		this.advertID = advertID;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	
}
