package com.petsociety.models;

public class Attendee {

	private int attendeeID;
	private int status;
	private int eventID;
	private int userID;
	
	private User user;
	private Event event;
	
	
	public int getAttendeeID() {
		return attendeeID;
	}
	public void setAttendeeID(int attendeeID) {
		this.attendeeID = attendeeID;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getEventID() {
		return eventID;
	}
	public void setEventID(int eventID) {
		this.eventID = eventID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	

	

}
