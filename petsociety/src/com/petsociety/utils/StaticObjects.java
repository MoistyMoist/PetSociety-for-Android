package com.petsociety.utils;
import java.util.List;

import com.petsociety.models.*;;

public class StaticObjects {
	
	final static String token="token";
	
	
	//GLOBAL OBJECTS (ALL)
	static User currentUser;
	static int ResponseStatus;
	static String ResponseMessage;
	
	//GLOBAL DEFINATIONS (privicy, status , etc)
	static String[] PET_LIST={"Dog","Cat","Bird","Diosaure"};
	static String[] LOCATION_TYPE={"PetShop", "Accidents", "Diseases","Others"};
	
	static int USER_PRIVICY_HIDDEN=1;
	static int USER_PRIVICY_NOTHIDDEN=0;
	
	
	//GLOBAL MAP OBJECTS (STUFF FOR THE MAIN MAP)
	static List<Event> mapEvent;
	static List<Location> mapLocation;
	static List<Stray> mapStray;
	static List<Lost> mapLost;
	static List<User> mapUser;
	
	//ANALYSIS OBJECTS (KAI QUAN)
	static List<Event> analysisEvent;
	static List<Location> anslysisLocation;
	static List<Stray> analysisStray;
	static List<Lost> andlysisLost;
	static Location analysisSelectedLocation;
	static Event analysisSelectedEvent;
	static Stray analysisSelectedStray;
	static Lost analysisSelectedLost;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public String getToken()
	{
		return this.token;
	}
	public static User getCurrentUser() {
		return currentUser;
	}
	public static void setCurrentUser(User currentUser) {
		StaticObjects.currentUser = currentUser;
	}
	public static int getResponseStatus() {
		return ResponseStatus;
	}
	public static void setResponseStatus(int responseStatus) {
		ResponseStatus = responseStatus;
	}
	public static String getResponseMessage() {
		return ResponseMessage;
	}
	public static void setResponseMessage(String responseMessage) {
		ResponseMessage = responseMessage;
	}
	public static List<Event> getMapEvent() {
		return mapEvent;
	}
	public static void setMapEvent(List<Event> mapEvent) {
		StaticObjects.mapEvent = mapEvent;
	}
	public static List<Location> getMapLocation() {
		return mapLocation;
	}
	public static void setMapLocation(List<Location> mapLocation) {
		StaticObjects.mapLocation = mapLocation;
	}
	public static List<Stray> getMapStray() {
		return mapStray;
	}
	public static void setMapStray(List<Stray> mapStray) {
		StaticObjects.mapStray = mapStray;
	}
	public static List<Lost> getMapLost() {
		return mapLost;
	}
	public static void setMapLost(List<Lost> mapLost) {
		StaticObjects.mapLost = mapLost;
	}
	public static List<Event> getAnalysisEvent() {
		return analysisEvent;
	}
	public static void setAnalysisEvent(List<Event> analysisEvent) {
		StaticObjects.analysisEvent = analysisEvent;
	}
	public static List<Location> getAnslysisLocation() {
		return anslysisLocation;
	}
	public static void setAnslysisLocation(List<Location> anslysisLocation) {
		StaticObjects.anslysisLocation = anslysisLocation;
	}
	public static List<Stray> getAnalysisStray() {
		return analysisStray;
	}
	public static void setAnalysisStray(List<Stray> analysisStray) {
		StaticObjects.analysisStray = analysisStray;
	}
	public static List<Lost> getAndlysisLost() {
		return andlysisLost;
	}
	public static void setAndlysisLost(List<Lost> andlysisLost) {
		StaticObjects.andlysisLost = andlysisLost;
	}
	public static Location getAnalysisSelectedLocation() {
		return analysisSelectedLocation;
	}
	public static void setAnalysisSelectedLocation(Location analysisSelectedLocation) {
		StaticObjects.analysisSelectedLocation = analysisSelectedLocation;
	}
	public static Event getAnalysisSelectedEvent() {
		return analysisSelectedEvent;
	}
	public static void setAnalysisSelectedEvent(Event analysisSelectedEvent) {
		StaticObjects.analysisSelectedEvent = analysisSelectedEvent;
	}
	public static Stray getAnalysisSelectedStray() {
		return analysisSelectedStray;
	}
	public static void setAnalysisSelectedStray(Stray analysisSelectedStray) {
		StaticObjects.analysisSelectedStray = analysisSelectedStray;
	}
	public static Lost getAnalysisSelectedLost() {
		return analysisSelectedLost;
	}
	public static void setAnalysisSelectedLost(Lost analysisSelectedLost) {
		StaticObjects.analysisSelectedLost = analysisSelectedLost;
	}
}
