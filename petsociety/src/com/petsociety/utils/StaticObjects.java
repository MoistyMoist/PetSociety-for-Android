package com.petsociety.utils;
import java.util.List;

import com.petsociety.models.*;;

public class StaticObjects {
	
	final static String token="token";
	
	
	//GLOBAL OBJECTS (ALL)
	static User currentUser;
	static int ResponseStatus;
	static String ResponseMessage;
	
	//GLOBAL DEFINATIONS (privacy, status , etc)
	static String[] PET_LIST={"Dog","Cat","Bird","Fish","Rabbit","Hamster","Turtle"};
	static String[] LOCATION_TYPE={"PetShop", "Accidents", "Diseases","Others"};
	static String[] ANALYSIS_TYPE={"Locations","Strays","Lost Reports","Events"};
	
	static int USER_PRIVICY_HIDDEN=1;
	static int USER_PRIVICY_NOTHIDDEN=0;
	static char LOST_FOUND_YES='Y';
	static char LOST_FOUND_NO='N';
	
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
	
	
	//EVENT OBJECTS (EURU)
	static List<Event> events;
	static Event selectedEvent;
	
	//LOCATION OBJECTS (SHAHRIKIN)
	static List<Location> locations;
	static Location selectedLocation;
	static List<Stray> strays;
	static Stray selectedStray;
	static List<Review> reviews;
	static Review selectReview;
	
	//LOST OBJECTS (CHOON SENG)
	static List<Lost> losts;
	static List<Pet> pets;
	static Lost selectedLost;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static List<Review> getReviews() {
		return reviews;
	}
	public static void setReviews(List<Review> reviews) {
		StaticObjects.reviews = reviews;
	}
	public static Review getSelectReview() {
		return selectReview;
	}
	public static void setSelectReview(Review selectReview) {
		StaticObjects.selectReview = selectReview;
	}
	public static List<Pet> getPets() {
		return pets;
	}
	public static void setPets(List<Pet> pets) {
		StaticObjects.pets = pets;
	}
	public String getToken()
	{
		return StaticObjects.token;
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
	public static String[] getPET_LIST() {
		return PET_LIST;
	}
	public static void setPET_LIST(String[] pET_LIST) {
		PET_LIST = pET_LIST;
	}
	public static String[] getLOCATION_TYPE() {
		return LOCATION_TYPE;
	}
	public static void setLOCATION_TYPE(String[] lOCATION_TYPE) {
		LOCATION_TYPE = lOCATION_TYPE;
	}
	public static int getUSER_PRIVICY_HIDDEN() {
		return USER_PRIVICY_HIDDEN;
	}
	public static void setUSER_PRIVICY_HIDDEN(int uSER_PRIVICY_HIDDEN) {
		USER_PRIVICY_HIDDEN = uSER_PRIVICY_HIDDEN;
	}
	public static int getUSER_PRIVICY_NOTHIDDEN() {
		return USER_PRIVICY_NOTHIDDEN;
	}
	public static void setUSER_PRIVICY_NOTHIDDEN(int uSER_PRIVICY_NOTHIDDEN) {
		USER_PRIVICY_NOTHIDDEN = uSER_PRIVICY_NOTHIDDEN;
	}
	public static List<User> getMapUser() {
		return mapUser;
	}
	public static void setMapUser(List<User> mapUser) {
		StaticObjects.mapUser = mapUser;
	}
	public static List<Event> getEvents() {
		return events;
	}
	public static void setEvents(List<Event> events) {
		StaticObjects.events = events;
	}
	public static Event getSelectedEvent() {
		return selectedEvent;
	}
	public static void setSelectedEvent(Event selectedEvent) {
		StaticObjects.selectedEvent = selectedEvent;
	}
	public static List<Location> getLocations() {
		return locations;
	}
	public static void setLocations(List<Location> locations) {
		StaticObjects.locations = locations;
	}
	public static Location getSelectedLocation() {
		return selectedLocation;
	}
	public static void setSelectedLocation(Location selectedLocation) {
		StaticObjects.selectedLocation = selectedLocation;
	}
	public static List<Stray> getStrays() {
		return strays;
	}
	public static void setStrays(List<Stray> strays) {
		StaticObjects.strays = strays;
	}
	public static Stray getSelectedStray() {
		return selectedStray;
	}
	public static void setSelectedStray(Stray selectedStray) {
		StaticObjects.selectedStray = selectedStray;
	}
	public static List<Lost> getLosts() {
		return losts;
	}
	public static void setLosts(List<Lost> losts) {
		StaticObjects.losts = losts;
	}
	public static Lost getSelectedLost() {
		return selectedLost;
	}
	public static void setSelectedLost(Lost selectedLost) {
		StaticObjects.selectedLost = selectedLost;
	}
	public static char getLOST_FOUND_YES() {
		return LOST_FOUND_YES;
	}
	public static void setLOST_FOUND_YES(char lOST_FOUND_YES) {
		LOST_FOUND_YES = lOST_FOUND_YES;
	}
	public static char getLOST_FOUND_NO() {
		return LOST_FOUND_NO;
	}
	public static void setLOST_FOUND_NO(char lOST_FOUND_NO) {
		LOST_FOUND_NO = lOST_FOUND_NO;
	}
	public static String[] getANALYSIS_TYPE() {
		return ANALYSIS_TYPE;
	}
	public static void setANALYSIS_TYPE(String[] aNALYSIS_TYPE) {
		ANALYSIS_TYPE = aNALYSIS_TYPE;
	}
	//For logout, set all null
	public static void setStaticEmpty() {
		// TODO Auto-generated method stub
		currentUser = null;
		  mapEvent=null;
		  mapLocation=null;
		  mapStray=null;
		  mapLost=null;
		  mapUser=null;
		
		//ANALYSIS OBJECTS (KAI QUAN)
		  analysisEvent=null;
		  anslysisLocation=null;
		  analysisStray=null;
		  andlysisLost=null;
		  analysisSelectedLocation=null;
		  analysisSelectedEvent=null;
		  analysisSelectedStray=null;
		  analysisSelectedLost=null;
		
		
		//EVENT OBJECTS (EURU)
		  events=null;
		  selectedEvent=null;
		
		//LOCATION OBJECTS (SHAHRIKIN)
		  locations=null;
		  selectedLocation=null;
		  strays=null;
		  selectedStray=null;
		
		//LOST OBJECTS (CHOON SENG)
		  losts=null;
		  selectedLost=null;
	}
}
