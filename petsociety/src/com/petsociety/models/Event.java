package com.petsociety.models;

import java.util.List;

public class Event {

	private int eventID;
	private String name;
	private String description;
	private String date;
	private String time;
	private String duration;
	private String createdDate;
	private String x;
	private String y;
	private String status;
	private String privicy;
	

	private User user;
	private List<Attendee> attendees;
	private Gallery gallery;

	
}
