package com.petsociety.main.analysis;

import com.actionbarsherlock.view.MenuInflater;
import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.petsociety.main.BaseActivity;
import com.petsociety.main.MainBaseActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.location.Location;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

public class AnalysisActivity extends BaseActivity 
implements 
ConnectionCallbacks,
OnConnectionFailedListener,
LocationListener, 
OnMyLocationButtonClickListener{

	
	private GoogleMap mMap;
	Button buttonMap;
    private LocationClient mLocationClient;
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	
	
	
	
	
	
	public AnalysisActivity() {
		super(R.string.title_activity_analysis);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_analysis);
		
		//setContentView(R.layout.basic_map);
	}

	
	
	
	@SuppressWarnings("unused")
	private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                //setUpMap();
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationButtonClickListener(this);
                LatLng singapore = new LatLng(1.37, 103.84);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 11));
            }
        }
    }
	@SuppressWarnings("unused")
	private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
	
	 public boolean onMyLocationButtonClick() {
	        Toast.makeText(this, "Stalking...", Toast.LENGTH_SHORT).show();
	        // Return false so that we don't consume the event and the default behavior still occurs
	        // (the camera animates to the user's current position).
	        return false;
	    }

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

}
