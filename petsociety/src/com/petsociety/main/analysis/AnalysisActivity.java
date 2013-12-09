package com.petsociety.main.analysis;

import java.util.List;


import com.androidnatic.maps.HeatMapOverlay;
import com.example.petsociety.R;
import com.example.petsociety.TestanaylsisActivity;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.petsociety.httprequests.RetrieveAllEventRequest;
import com.petsociety.httprequests.RetrieveAllLocationRequest;
import com.petsociety.httprequests.RetrieveAllLostRequest;
import com.petsociety.httprequests.RetrieveAllStrayRequest;
import com.petsociety.httprequests.RetrieveReviewByLocationRequest;
import com.petsociety.main.MainBaseActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

@SuppressLint({ "NewApi", "CutPasteId" })
public class AnalysisActivity extends MainBaseActivity 
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
	Context context;
	//List<HeatPoint> points;
	
	Button filterBtn;
	Button filterBtn2;
	
	//private HeatMapOverlay overlay;
	String[]analysisTypes={"Locations","Events","Lost","Strays"};
	ArrayAdapter<CharSequence> analysisTypeAdapter;
	
	private HeatMapOverlay overlay;
	
	
	public AnalysisActivity() {
		super(R.string.title_activity_analysis);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		context=this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_analysis);
		setSlidingActionBarEnabled(true);
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT);	
		sm.setSecondaryShadowDrawable(R.drawable.shadowright);
		sm.setShadowDrawable(R.drawable.shadow);
		ViewGroup viewGroup=(ViewGroup)findViewById(R.id.analysis_map);
		viewGroup.addView(View.inflate(this, R.layout.basic_map, null));
		setUpMapIfNeeded();
		
		//linking the ui objects
		CircleOptions circleOptions = new CircleOptions()
	    .center(new LatLng(1.37, 103.84))
	    .radius(1000) // In meters
	    .fillColor(0x1AFF0000)//90% transparent red
	    .strokeColor(Color.TRANSPARENT);//dont show the border to the circle
	// Get back the mutable Circle
		mMap.addCircle(circleOptions);
		
		circleOptions = new CircleOptions()
	    .center(new LatLng(1.37, 103.85))
	    .radius(1000) // In meters
	    .fillColor(0x1AFF0000)//90% transparent red
	    .strokeColor(Color.TRANSPARENT);//dont show the border to the circle
	// Get back the mutable Circle
		mMap.addCircle(circleOptions);
		
		
		//retrieve all the points (location, strays, lost, events
		RetrieveAllEventRequest eventRequest= new RetrieveAllEventRequest();
		RetrieveAllLocationRequest locationRequest= new RetrieveAllLocationRequest();
		RetrieveAllStrayRequest strayRequest= new RetrieveAllStrayRequest();
		RetrieveAllLostRequest lostRequest= new RetrieveAllLostRequest();
		
		new BackgroundTask().execute(eventRequest,locationRequest,strayRequest,lostRequest);
		
		
		
//		final SimpleMapView mapview = (SimpleMapView)findViewById(R.id.mapviewww);
//		this.overlay = new HeatMapOverlay(20000, mapview);
//		mapview.getOverlays().add(overlay);
		
		
		
	}
	
	
	
	
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
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 9));
            }
        }
    }
	@SuppressWarnings("unused")
	private void AddPin() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }
	
	private void RemovePins()
	{
		
	}
	
	 public boolean onMyLocationButtonClick() {
	        Toast.makeText(this, "Tracking...", Toast.LENGTH_SHORT).show();
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
		getSupportMenuInflater().inflate(R.menu.analysis, menu);
		return true;
	}
	
	public void nextPage(View view)
	{
		//showDialog(0);

		
		//RetrieveReviewByLocationRequest retrieveReview = new RetrieveReviewByLocationRequest(1); 
		//new BackgroundTask().execute(retrieveReview);
		
		Intent intent= new Intent(this,TestanaylsisActivity.class);
		intent.setClass(getApplication(), TestanaylsisActivity.class);
		startActivity(intent);
	}
	
	
	
	private void PlotPoint()
	{
		//overlay.update(points);
	}
	
	
	

	private class BackgroundTask extends AsyncTask<Runnable, Integer, Long> {
	     
		@Override
		protected void onPostExecute(Long result) {
			Toast.makeText(context, "Refreshed", Toast.LENGTH_LONG).show();
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			Toast.makeText(context, "Refreshing..", Toast.LENGTH_LONG).show();
			super.onPreExecute();
		}

		@Override
		protected Long doInBackground(Runnable... task) {
			
			for(int i=0; i<task.length;i++)
			{
				task[i].run();
				
				if (isCancelled()) break;
			}
			return (long) 1;
		}
	 }
	
	

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		// TODO Auto-generated method stub
		Dialog retDialog = null;
		retDialog.setContentView(R.layout.custom_analysis_location_option);
		
		return retDialog;
	}
}
