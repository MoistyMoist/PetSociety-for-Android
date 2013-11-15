package com.petsociety.models;

public class Attendee {

	private int attendeeID;
	private String status;
	
	private Event event;
	private User user;
	
	public int getAttendeeID() {
		return attendeeID;
	}
	public void setAttendeeID(int attendeeID) {
		this.attendeeID = attendeeID;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
