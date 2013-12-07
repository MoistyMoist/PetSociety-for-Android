package com.petsociety.models;

import java.util.List;

public class Organization {

	private int organizationID;
	private String name;
	private String description;
	private String email;
	private String password;
	private String imageURL;
	
	private List<Advert> adverts;

	public int getOrganizationID() {
		return organizationID;
	}

	public void setOrganizationID(int organizationID) {
		this.organizationID = organizationID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public List<Advert> getAdverts() {
		return adverts;
	}

	public void setAdverts(List<Advert> adverts) {
		this.adverts = adverts;
	}

	
}
