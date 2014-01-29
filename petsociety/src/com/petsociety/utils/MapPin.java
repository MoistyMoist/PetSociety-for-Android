package com.petsociety.utils;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;
import com.petsociety.models.Event;
import com.petsociety.models.Location;
import com.petsociety.models.Lost;

public class MapPin implements ClusterItem{

	String type;
	Lost lost;
	Event event;
	Location location;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Lost getLost() {
		return lost;
	}
	public void setLost(Lost lost) {
		this.lost = lost;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	@Override
	public LatLng getPosition() {
		if(lost != null){return lost.getPosition();}
		if(event != null){return event.getPosition();}
		if(location != null){return location.getPosition();}
		return null;
	}
	
	
	
}
