package com.petsociety.main.nearby;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.petsociety.models.Location;
import com.petsociety.utils.StaticObjects;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LocationInfoActivity extends FragmentActivity {
	
	Location singleLocation= null;
	TextView singleTitle, singleDescription, singleAddress;
	  private GoogleMap mMap;
	    
	  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location_info);
		
		Bundle extras = getIntent().getExtras();
		int locationID = extras.getInt("location");
		
		singleTitle = (TextView) findViewById(R.id.singleTitle);
		singleDescription = (TextView) findViewById(R.id.singleDescription);
		singleAddress = (TextView) findViewById(R.id.singleAddress);
		
		ViewGroup viewGroup = (ViewGroup) findViewById(R.id.nearbySingle_map);
		viewGroup.addView(View.inflate(this, R.layout.basic_map, null));
		setUpMapIfNeeded();
		

		for (int i=0; i<StaticObjects.getLocations().size(); i++){
			if(locationID == StaticObjects.getLocations().get(i).getLocationID())
			{
				singleLocation = StaticObjects.getLocations().get(i);
			}

		}		
		
	    singleTitle.setText(singleLocation.getTitle());
	    singleDescription.setText(singleLocation.getDescription());
	    singleAddress.setText(singleLocation.getAddress());
	    
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.location_info, menu);
		return true;
	}


  
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
            
        }
        private void setUpMap() {
        	
            mMap.addMarker(new MarkerOptions().position(new LatLng(1.379348, 103.849876)).title("Marker"));
        
          
        }
}
