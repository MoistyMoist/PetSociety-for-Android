package com.petsociety.models;

public class Friend_List {

	private int friend_listID;
	private int userID;
	private int friendID;
	
	private User user;
	private User friend;
	
	
	public int getFriend_listID() {
		return friend_listID;
	}
	public void setFriend_listID(int friend_listID) {
		this.friend_listID = friend_listID;
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

