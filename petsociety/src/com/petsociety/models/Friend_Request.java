package com.petsociety.models;

public class Friend_Request {

	private int friend_requestID;
	private int userID;
	private int friendID;
	private int accepted;
	
	private User user;
	private User friend;
	
	
	public int getFriend_requestID() {
		return friend_requestID;
	}
	public void setFriend_requestID(int friend_requestID) {
		this.friend_requestID = friend_requestID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public int getFriendID() {
		return friendID;
	}
	public void setFriendID(int friendID) {
		this.friendID = friendID;
	}
	public int getAccepted() {
		return accepted;
	}
	public void setAccepted(int accepted) {
		this.accepted = accepted;
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

	
}
