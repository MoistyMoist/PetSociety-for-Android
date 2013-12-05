package com.petsociety.models;

import java.util.List;

public class Location {

	private int locationID;
	private String x;
	private String y;
	private String description;
	private String title;
	private String address;
	private String type;
	private String dateCreated;
	private String timeCreated;
	
	
	

	private User user;
	private List<Review> reviews;
	private Gallery gallery;
	

	
}
