package com.petsociety.models;

public class Friend_Request {

	private int friend_requestID;
	private User user;
	private User friend;
	private String accepted;
	
	public int getFriend_requestID() {
		return friend_requestID;
	}
	public void setFriend_requestID(int friend_requestID) {
		this.friend_requestID = friend_requestID;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getFriend() {
		return friend;
	}
	public void setFriend(User friend) {
		this.friend = friend;
	}
	public String getAccepted() {
		return accepted;
	}
	public void setAccepted(String accepted) {
		this.accepted = accepted;
	}
	
}
