package com.petsociety.main.nearby;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
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
import com.petsociety.main.MainBaseActivity;
import com.petsociety.main.RightListFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.location.Location;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

@SuppressLint("NewApi")
public class NearbyActivity extends MainBaseActivity 
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
	
	
	Button filterBtn;
	Button filterBtn2;
	
	
	
	public NearbyActivity() {
		super(R.string.title_activity_nearby);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main_nearby);
		setSlidingActionBarEnabled(true);
		
				
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT);
							
		sm.setSecondaryShadowDrawable(R.drawable.shadowright);
		sm.setShadowDrawable(R.drawable.shadow);


		ViewGroup viewGroup=(ViewGroup)findViewById(R.id.nearby_map);
		viewGroup.addView(View.inflate(this, R.layout.basic_map, null));
		setUpMapIfNeeded();
		
		
		//new loadPoints().doInBackground(null);
		
//		 RetrieveLocationAnalysisRequest nearbyLocation = new RetrieveLocationAnalysisRequest();
//	        RetrieveLostAnalysisRequest nearbyLost= new RetrieveLostAnalysisRequest();
//	        RetrieveStrayAnalysisRequest nearbyStray= new RetrieveStrayAnalysisRequest();
//	        RetrieveEventAnalysisRequest nearbyEvent= new RetrieveEventAnalysisRequest();
//		new loadPoints().execute(nearbyLost);
//		new loadPoints().execute(nearbyEvent);
//		new loadPoints().execute(nearbyEvent);
//		new loadPoints().execute(nearbyEvent);
//		new loadPoints().execute(nearbyEvent);
		
	}
	@Override
	public void onStart()
	{
		super.onStart();
		
		
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
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 7));
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
		// TODO Auto-geneateOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.nearby, menu);
		return true;
	}
	
//	public void nextPage(View view)
//	{
//		Intent intent= new Intent(this,AnalysisDetailActivity.class);
//		intent.setClass(getApplication(), AnalysisDetailActivity.class);
//		startActivity(intent);
//	}
	
	private class loadPoints extends AsyncTask<Runnable, Integer, Long> {
	     
		@Override
		protected void onPostExecute(Long result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		@Override
		protected Long doInBackground(Runnable... arg0) {
			
			
//			ExecutorService executor = Executors.newFixedThreadPool(10);
//	        RetrieveLocationAnalysisRequest nearbyLocation = new RetrieveLocationAnalysisRequest();
//	        RetrieveLostAnalysisRequest nearbyLost= new RetrieveLostAnalysisRequest();
//	        RetrieveStrayAnalysisRequest nearbyStray= new RetrieveStrayAnalysisRequest();
//	        RetrieveEventAnalysisRequest nearbyEvent= new RetrieveEventAnalysisRequest();
//	        
//	        executor.execute(nearbyLocation);
//	        executor.execute(nearbyLost);
//	        executor.execute(nearbyStray);
//	        executor.execute(nearbyEvent);
	        
//			executor.shutdown();
//	        try {
//	        	executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
//	       	  	Log.i(" RESPONSE :","ENDED REQUEST");
//	       	  	
//	        } catch (InterruptedException e) {
//	           
//	        }
			return null;
		}
	 }
	
}
