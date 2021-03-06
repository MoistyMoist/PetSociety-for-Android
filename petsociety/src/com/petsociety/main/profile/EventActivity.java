package com.petsociety.main.profile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import com.example.petsociety.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.petsociety.main.MainBaseActivity;
import com.petsociety.main.RouteActivity;
import com.petsociety.models.Event;
import com.petsociety.utils.StaticObjects;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EventActivity extends MainBaseActivity {

    private GoogleMap mMap;
	TextView name, desc, time, location;
	Event oneEvent = null;
	Button current, other;

	public EventActivity() {
		super(R.string.app_name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_event);
		Bundle extras = getIntent().getExtras();
		int eventID = extras.getInt("event");
		
		name = (TextView) findViewById(R.id.tv_name);
		desc = (TextView) findViewById(R.id.tv_description);
		time = (TextView) findViewById(R.id.tv_time);
		location = (TextView) findViewById(R.id.tv_location);
		current = (Button) findViewById(R.id.btn_current_location);
		
		for (int i=0; i<StaticObjects.getEvents().size(); i++){
			if(eventID == StaticObjects.getEvents().get(i).getEventID())
			{
				oneEvent = StaticObjects.getEvents().get(i);
			}
		}		
		
		current.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.putExtra("desX", oneEvent.getX());
				intent.putExtra("desY", oneEvent.getY());
				intent.setClass(getApplicationContext(), RouteActivity.class);
				startActivity(intent);
			}
			
		});
		
	    name.setText(oneEvent.getName());
	    desc.setText(oneEvent.getDescription());
	    
	    String startDate = new SimpleDateFormat("dd MMM yyyy hh:mm a").format(oneEvent.getStartDateTime())
	    		+"\n                To\n"
	    		+ new SimpleDateFormat("dd MMM yyyy hh:mm a").format(oneEvent.getEndDateTime());
	    time.setText(startDate);

	    getMyLocationAddress();
		setUpMapIfNeeded();

	}
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.event, menu);
		return true;
	}
	
	private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.event_map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                //setUpMap();
                //mMap.setMyLocationEnabled(true);
                //mMap.setOnMyLocationButtonClickListener(this);
                LatLng singapore = new LatLng(oneEvent.getX(), oneEvent.getY());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 12));
                mMap.getUiSettings().setAllGesturesEnabled(false);
                mMap.addMarker(new MarkerOptions().position(new LatLng(oneEvent.getX(), oneEvent.getY())));
                mMap.getUiSettings().setZoomControlsEnabled(false);
            }
        }
    }
	
	public void getMyLocationAddress() {
        
        Geocoder geocoder= new Geocoder(this, Locale.ENGLISH);
         
        try {
              //Place your latitude and longitude
              List<Address> addresses = geocoder.getFromLocation(oneEvent.getX(), oneEvent.getY(), 1);
              if(addresses != null) {
               
                  Address fetchedAddress = addresses.get(0);
                  StringBuilder strAddress = new StringBuilder();
                
                  for(int i=0; i<fetchedAddress.getMaxAddressLineIndex(); i++) {
                        strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
                        Log.i("Address", fetchedAddress.getAddressLine(i));
                  }
                  
        	    	location.setText(strAddress.toString());
              }
               
              else{
            	  location.setText("No location found..!");
              }
          
        } 
        catch (IOException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
                 Toast.makeText(getApplicationContext(),"Could not get address..!", Toast.LENGTH_LONG).show();
        }
    }

}
