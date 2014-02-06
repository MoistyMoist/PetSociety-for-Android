package com.petsociety.main.nearby;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.example.petsociety.R;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.petsociety.httprequests.RetrieveAllLocationRequest;
import com.petsociety.httprequests.RetrieveAllLostRequest;
import com.petsociety.httprequests.RetrieveLocationByTypeRequest;
import com.petsociety.main.MainBaseActivity;
import com.petsociety.main.lost.ReportLostPetActivity;

import com.petsociety.utils.StaticObjects;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.actionbarsherlock.view.MenuItem;

import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class NearbyActivity extends MainBaseActivity implements
		ConnectionCallbacks, OnConnectionFailedListener, LocationListener,
		OnMyLocationButtonClickListener {

	// private final LatLng LOCATION_NYP = new LatLng(1.379348, 103.849876);
	private GoogleMap mMap;
	Button buttonMap;
	private LocationClient mLocationClient;
	private static final LocationRequest REQUEST = LocationRequest.create()
			.setInterval(5000) // 5 seconds
			.setFastestInterval(16) // 16ms = 60fps
			.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

	ArrayList<Marker> shopMarkers = new ArrayList<Marker>();
	ArrayList<Marker> vetMarkers = new ArrayList<Marker>();

	AutoCompleteTextView textView;
	Button locateButton;
	Intent intent;
	TextView textView1;
	StaticObjects staticObjects;
	ProgressDialog progress;
	Context context = this;
	ArrayList<String> locationTypeArrayList = new ArrayList<String>();
	
	 String address,desc, type; 

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

		ViewGroup viewGroup = (ViewGroup) findViewById(R.id.nearby_map);
		viewGroup.addView(View.inflate(this, R.layout.basic_map, null));
		setUpMapIfNeeded();

	
		// etNearbySearchLocation = (EditText)
		// findViewById(R.id.etNearbySearchLocation);
		intent = new Intent();

		textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_country);
		// Get the string array

		String[] countries = getResources().getStringArray(
				R.array.countries_array);
		// Create the adapter and set it to the AutoCompleteTextView
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, countries);

		textView.setAdapter(adapter);
		textView1 = (TextView) findViewById(R.id.textView1);

		// intent.setClass(getApplication(), NearbyDetailsActivity.class);
		
	

	

		getAllNearbyListType();
		System.out.print("******************************");

	}
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.nearby, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.nearby_search:
			//Intent intent = new Intent();
			//intent.setClass(getBaseContext(), ReportLostPetActivity.class);
			//startActivity(intent);
			//this is the search icon onclick stuffs, try run
		if(textView.getText().toString().equals("")){
			textView.setVisibility(View.VISIBLE);
			return false;
		}
		else{	
			searchLocation();
		}
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

	public void getAllNearbyListType() {

		progress = ProgressDialog.show(this, "Getting all location",
				"please wait...", true);
		RetrieveAllLocationRequest retrieveAllLocationByTypeRequest = new RetrieveAllLocationRequest();

		new AllLocationBackgroundTask().execute(
				retrieveAllLocationByTypeRequest, null);

	}

	public void getNearbyList() {

		if (StaticObjects.getLocations() == null
				|| StaticObjects.getLocations().size() == 0) {
			progress = ProgressDialog.show(this, "Getting your wishes",
					"please wait...", true);
			RetrieveLocationByTypeRequest retrieveLocationByTypeRequest = new RetrieveLocationByTypeRequest(
					"Pet%20Store"); 
			// nono

			new BackgroundTask().execute(retrieveLocationByTypeRequest, null);
		}

	}

	private class AllLocationBackgroundTask extends
			AsyncTask<Runnable, Integer, Long> {

		@Override
		protected void onPostExecute(Long result) {

			super.onPostExecute(result);
			if (progress != null)
				progress.dismiss();
			for (int i = 0; i < StaticObjects.getLocations().size(); i++) {
				locationTypeArrayList.add(StaticObjects.getLocations().get(i)
						.getType());

			}

			// textView1.setTag(staticObjects.getLocations().lastIndexOf(getTitle()));

		}

		@Override
		protected void onPreExecute() {
			// Toast.makeText(getBaseContext(), "Refreshing..",
			// Toast.LENGTH_SHORT).show();
			super.onPreExecute();
		}

		@Override
		protected Long doInBackground(Runnable... task) {

			for (int i = 0; i < task.length; i++) {
				if (task[i] != null)
					task[i].run();
				if (isCancelled())
					break;
			}
			return null;
		}
	}

	private class BackgroundTask extends AsyncTask<Runnable, Integer, Long> {

		@Override
		protected void onPostExecute(Long result) {

			super.onPostExecute(result);
			if (progress != null)
				progress.dismiss();
			staticObjects = new StaticObjects();

			// can u show me? populate everyting insite locationArrayList so i
			// can reus
			// so just get get all i need like that? yeap.okay w
			for (int i = 0; i < staticObjects.getLocations().size(); i++) {
				// /staticObjects.getLocations().get(i).getX();
				// staticObjects.getLocations().get(i).getY();
				// staticObjects.getLocations().get(i).getTitle();
			}

			// textView1.setTag(staticObjects.getLocations().lastIndexOf(getTitle()));

		}

		@Override
		protected void onPreExecute() {
			// Toast.makeText(getBaseContext(), "Refreshing..",
			// Toast.LENGTH_SHORT).show();
			super.onPreExecute();
		}

		@Override
		protected Long doInBackground(Runnable... task) {

			for (int i = 0; i < task.length; i++) {
				if (task[i] != null)
					task[i].run();
				if (isCancelled())
					break;
			}
			return null;
		}
	}

	private void setUpMapIfNeeded() {
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map)).getMap();

			// Check if we were successful in obtaining the map.
			if (mMap != null) {
				// setUpMap();
				mMap.setMyLocationEnabled(true);
				mMap.setOnMyLocationButtonClickListener(this);
				LatLng singapore = new LatLng(1.37, 103.84);
				mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore,
						12.0f));

				addShopMarkers();
				//addVetMarkers();
			}
		}
	}

	private void setUpMap() {
		mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title(
				"Marker"));
	}

	public boolean onMyLocationButtonClick() {
		Toast.makeText(this, "Stalking...", Toast.LENGTH_SHORT).show();
		// Return false so that we don't consume the event and the default
		// behavior still occurs
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



	public void toggleShopMarkers() {
		boolean setVisible = true;
		if (shopMarkers.get(0).isVisible() == true) {
			setVisible = false;
		}
		for (int i = 0; i < shopMarkers.size(); i++) {
			shopMarkers.get(i).setVisible(setVisible);
		}
	}

	public void toggleVetMarkets() {
		boolean setVisible = true;
		if (vetMarkers.get(0).isVisible() == true) {
			setVisible = false;
		}
		for (int i = 0; i < vetMarkers.size(); i++) {
			vetMarkers.get(i).setVisible(setVisible);
		}
	}

    public void addShopMarkers(){
        
        List<com.petsociety.models.Location> locationList = StaticObjects.getLocations();
    	
        for (int i=0; i<locationList.size(); i++){
        	double lat = locationList.get(i).getX();
	        double lng = locationList.get(i).getY();
        	
        	if(locationList.get(i).getType().equalsIgnoreCase("accident")){}
        	else if(locationList.get(i).getType().equalsIgnoreCase("vet")){
        		
        		LevelListDrawable d=(LevelListDrawable) getResources().getDrawable(R.drawable.icon_vet);
        		d.setLevel(1234);
        		BitmapDrawable bd=(BitmapDrawable) d.getCurrent();
        		Bitmap b=bd.getBitmap();
        		Bitmap bsize=Bitmap.createScaledBitmap(b, b.getWidth()/10,b.getHeight()/10, false);
        		
        		MarkerOptions mOption = new MarkerOptions()
			                .position(new LatLng(lat, lng))
			                .title(locationList.get(i).getTitle())
			                //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
			                .icon(BitmapDescriptorFactory.fromBitmap(bsize))
			                .infoWindowAnchor(0.5f, 0.5f)
			                .snippet("About: "+locationList.get(i).getDescription()); 
        		vetMarkers.add(mMap.addMarker(mOption));
        	} 
        	else{
        		
        		LevelListDrawable d=(LevelListDrawable) getResources().getDrawable(R.drawable.icon_petstore);
        		d.setLevel(1234);
        		BitmapDrawable bd=(BitmapDrawable) d.getCurrent();
        		Bitmap b=bd.getBitmap();
        		Bitmap bsize=Bitmap.createScaledBitmap(b, b.getWidth()/10,b.getHeight()/10, false);
        		
		        MarkerOptions mOption = new MarkerOptions()
		                    .position(new LatLng(lat, lng))
		                    .title(locationList.get(i).getTitle())
		                    //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
			                .icon(BitmapDescriptorFactory.fromBitmap(bsize))
		                    .infoWindowAnchor(0.5f, 0.5f)
		                    .snippet("About: "+locationList.get(i).getDescription()); 
		        shopMarkers.add(mMap.addMarker(mOption));
        	}
        }
    }

	public void addVetMarkers() {
		MarkerOptions mOption = new MarkerOptions()
				.position(new LatLng(1.3, 103.85))
				.title("Vet")
				.snippet("We are a Vet!")
				.icon(BitmapDescriptorFactory
						.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
		vetMarkers.add(mMap.addMarker(mOption));
	}


	
	public void searchLocation(){
		
		for (int i = 0; i < StaticObjects.getLocations().size(); i++) {

			LatLng LOCATION = new LatLng(staticObjects.getLocations()
					.get(i).getX(), staticObjects.getLocations().get(i)
					.getY());
			final String title =StaticObjects.getLocations().get(i).getTitle().toString();
			final String desc = StaticObjects.getLocations().get(i).getDescription().toString();
			final String address = StaticObjects.getLocations().get(i).getAddress().toString();
			
			
			if (staticObjects.getLocations().get(i).getTitle()
					.equals(textView.getText().toString())) {
				mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				CameraUpdate update = CameraUpdateFactory
						.newLatLngZoom(LOCATION, 16);
				mMap.getMaxZoomLevel();
				mMap.animateCamera(update);
				MarkerOptions mOption = new MarkerOptions()
						.position(LOCATION)
						.title(title)
						.snippet(desc)
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
				vetMarkers.add(mMap.addMarker(mOption));
				mMap.setOnMapClickListener(new OnMapClickListener (){

					@Override
					public void onMapClick(LatLng arg0) {
						// TODO Auto-generated method stub
						//textView1.setText("Hello world");
						
						
						com.petsociety.models.Location loc = new com.petsociety.models.Location();
						loc.setTitle(title);
						StaticObjects.setSelectedLocation(loc);

						
						 Intent intent = new Intent(NearbyActivity.this,
								NearbyDetailsActivity.class);
								
					
						 intent.putExtra("nearbyTitle", title);
						
						
						startActivity(intent);
					
						
					}});

			}

			else {
			
			}

		}

}
	

	
}
