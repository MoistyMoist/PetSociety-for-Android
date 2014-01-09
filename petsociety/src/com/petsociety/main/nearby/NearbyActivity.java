package com.petsociety.main.nearby;

import java.util.ArrayList;
import java.util.Random;

import com.example.petsociety.R;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import com.petsociety.httprequests.RetrieveAllLostRequest;
import com.petsociety.httprequests.RetrieveLocationByTypeRequest;
import com.petsociety.main.MainBaseActivity;
import com.petsociety.utils.StaticObjects;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;


public class NearbyActivity extends MainBaseActivity 
implements 
ConnectionCallbacks,
OnConnectionFailedListener,
LocationListener, 
OnMyLocationButtonClickListener{

	private final LatLng LOCATION_NYP = new LatLng(1.379348, 103.849876); 
	private GoogleMap mMap;
	Button buttonMap;
    private LocationClient mLocationClient;
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	
    ArrayList<Marker>shopMarkers = new ArrayList<Marker>();
    ArrayList<Marker>vetMarkers = new ArrayList<Marker>();
    
    
    AutoCompleteTextView textView;
	Button locateButton;
	Intent intent;
	//lets try weird, hmm..my guess was because u made a copy of the project when improting thats why there is 2 copys.... ohhhh ok
	StaticObjects staticObjects;
	ProgressDialog progress;
	Context context = this; 
	
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
		
		locateButton = (Button) findViewById(R.id.locateButton);
	//	etNearbySearchLocation = (EditText) findViewById(R.id.etNearbySearchLocation);
		intent = new Intent();
		
		textView = (AutoCompleteTextView) findViewById(R.id.autocomplete_country);
		// Get the string array
		String[] countries = getResources().getStringArray(R.array.countries_array);
		// Create the adapter and set it to the AutoCompleteTextView 
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
		textView.setAdapter(adapter);
		
		/*
		//intent.setClass(getApplication(), NearbyDetailsActivity.class);
		locateButton.setOnClickListener( new OnClickListener()
		{

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				 Intent intent = new Intent(NearbyActivity.this, NearbyDetailsActivity.class);
//				startActivity(intent);
				
//				mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//				CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_NYP, 16);
//				mMap.getMaxZoomLevel();
//				mMap.animateCamera(update);
//				MarkerOptions mOption = new MarkerOptions()
//				.position(LOCATION_NYP)
//				.title("Nanyang Polytechnic")
//				.snippet("A school located in ang mo kio")
//				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
//				vetMarkers.add(mMap.addMarker(mOption));
				
				String input = textView.getText().toString();
				String type = "Pet Store";
				RetrieveLocationByTypeRequest retrieveLocationByTypeRequest = new RetrieveLocationByTypeRequest(type);
				StaticObjects.getSelectedLocation().getTitle();
				StaticObjects.getSelectedLocation().getX();
				StaticObjects.getSelectedLocation().getY();
				
				if(StaticObjects.getSelectedLocation().getType().toString() =="Pet Store" )
				{
					
				}// i tried clicking search button but nthing happen?.. that null pointer isee
				
			}
			
		}); */
		
		getNearbyList(); //so this method ok ar?yes
				
	}
	
	
public void getNearbyList(){
		
		if(StaticObjects.getLocations()==null ||StaticObjects.getLocations().size()==0)
		{
			//progress = ProgressDialog.show(this, "Getting your wishes","please wait...", true);
			//RetrieveLocationByTypeRequest retrieveLocationByTypeRequest = new RetrieveLocationByTypeRequest("Pet Store"); 
			
			progress = ProgressDialog.show(this, "Setting up map","please wait...", true);
			RetrieveAllLostRequest retrieveAllLostRequest = new RetrieveAllLostRequest();
			//UploadImageRequest upload= new UploadImageRequest();
			new BackgroundTask().execute( retrieveAllLostRequest,null);
		}
		else
		{
			//lost has be retrieved
		}
	}
	
	private class BackgroundTask extends AsyncTask<Runnable, Integer, Long> {
	    
		@Override
		protected void onPostExecute(Long result) {
			
			super.onPostExecute(result);
			if(progress!=null)
				progress.dismiss();
	        staticObjects= new StaticObjects();		
		}

		@Override
		protected void onPreExecute() {
			//Toast.makeText(getBaseContext(), "Refreshing..", Toast.LENGTH_SHORT).show();
			super.onPreExecute();
		}

		@Override
		protected Long doInBackground(Runnable... task) {
			
			for(int i=0; i<task.length;i++)
			{
				if(task[i]!=null)
					task[i].run();
				if (isCancelled()) break;
			}
			return null;
		}
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
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 12.0f));
               
                
                for (int i=0; i<3; i++){
    				addShopMarkers();
    				addVetMarkers();
            }
            }
        }}
    
	
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
	
	
	 public void toggleShopMarkers(){
	    	boolean setVisible = true;
	    	if (shopMarkers.get(0).isVisible()==true){
	    		setVisible = false;
	    	}
			for (int i=0; i<shopMarkers.size(); i++){
				shopMarkers.get(i).setVisible(setVisible);
			}
	    }
	    
	    public void toggleVetMarkets(){
	    	boolean setVisible = true;
	    	if (vetMarkers.get(0).isVisible()==true){
	    		setVisible = false;
	    	}
			for (int i=0; i<vetMarkers.size(); i++){
				vetMarkers.get(i).setVisible(setVisible);
			}
	    }
	
	public double[] randomMarkerPos(){
    	double[] pos = new double[2];
    	Random generator = new Random(); 
    	double d_lat = generator.nextInt(9) + 1; // 1~9
    	double d_lng = generator.nextInt(9) + 1; // 1~9
    	double lat = 1.30 + d_lat/100;
    	double lng = 103.80 + d_lng/100;
    	pos[0] = lat;
    	pos[1] = lng;
    	return pos;
    }
	
	 public void addShopMarkers(){
	    	double[] pos = randomMarkerPos();
	    	double lat = pos[0];
	    	double lng = pos[1];	
			//Toast.makeText(getApplicationContext(), "L:"+lat+","+lng, Toast.LENGTH_SHORT).show();
	    	MarkerOptions mOption = new MarkerOptions()
				.position(new LatLng(lat, lng))
				.title("Pet Shop")
				.snippet("Cat & Dog Pet Shop");
	    	shopMarkers.add(mMap.addMarker(mOption));
	    	
	    	
	    	
	    }
	    
	 
	    public void addVetMarkers(){
	    	double[] pos = randomMarkerPos();
	    	double lat = pos[0];
	    	double lng = pos[1];
			//Toast.makeText(getApplicationContext(), "L:"+lat+","+lng, Toast.LENGTH_SHORT).show();
	    	MarkerOptions mOption = new MarkerOptions()
				.position(new LatLng(lat, lng))
				.title("Vet")
				.snippet("We are a Vet!")
				.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
	    	vetMarkers.add(mMap.addMarker(mOption));
	    }
	    
	
	
}
