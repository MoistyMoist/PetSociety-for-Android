package com.petsociety.models;

import java.sql.Date;

public class Advert {

	private int advertID;
	private int organizationID;
	private String title;
	private String description;
	private Date dateCreated;
	private String duration;
	
	
	public int getAdvertID() {
		return advertID;
	}
	public void setAdvertID(int advertID) {
		this.advertID = advertID;
	}
	public int getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(int organizationID) {
		this.organizationID = organizationID;
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
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	

	
}
