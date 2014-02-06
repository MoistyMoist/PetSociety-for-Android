package com.petsociety.main.nearby;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;
import com.google.android.gms.maps.CameraUpdateFactory;
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
		

		for (int i=0; i<StaticObjects.getLocations().size(); i++){
			if(locationID == StaticObjects.getLocations().get(i).getLocationID())
			{
				singleLocation = StaticObjects.getLocations().get(i);
			}

		}		
		
		//singleTitle.setText( (singleLocation.getDescription().split(";"))[2] );
		
	    singleTitle.setText(singleLocation.getTitle()); //here
	    singleDescription.setText(singleLocation.getDescription());
	    singleAddress.setText(singleLocation.getAddress());
	    
	    setUpMapIfNeeded();
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
            	//mMap.setMyLocationEnabled(true);
                //mMap.setOnMyLocationButtonClickListener(this);
                LatLng singapore = new LatLng(1.37, 103.84);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 11));
                //mMap.addMarker(new MarkerOptions().position(new LatLng(oneEvent.getX(), oneEvent.getY())));
                mMap.addMarker(new MarkerOptions().position(new LatLng(singleLocation.getX(), singleLocation.getY())).title("Marker"));
            }
        }
            
        }

}
