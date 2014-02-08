package com.petsociety.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.petsociety.httprequests.*;
import com.petsociety.main.lost.LostActivity;
import com.petsociety.main.nearby.NearbyList;
import com.petsociety.models.Event;
import com.petsociety.models.Lost;
import com.petsociety.models.Pet;
import com.petsociety.utils.StaticObjects;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.petsociety.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class RouteActivity extends MainBaseActivity 
        implements 
        ConnectionCallbacks,
        OnConnectionFailedListener,
        LocationListener, 
        OnMyLocationButtonClickListener,
        OnMapLoadedCallback{

        public GoogleMap mMap;
        Button buttonMap;
        private LocationClient mLocationClient;
    
        String LINE;
        StaticObjects staticObjects;
        ProgressDialog progress;
        Context context = this;
        
        double srcX, srcY;
        double desX, desY;
    
        private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                
        public RouteActivity() {
                super(R.string.app_name);
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                setContentView(R.layout.activity_main);
                setSlidingActionBarEnabled(true);
                
                RightListFragment rightFrag = new RightListFragment();                
                SlidingMenu sm = getSlidingMenu();
                sm.setMode(SlidingMenu.LEFT_RIGHT);
                
                sm.setSecondaryMenu(R.layout.menu_frame_two);
                getSupportFragmentManager().beginTransaction()
                .replace(R.id.menu_frame_two, rightFrag).commit();                                        
                sm.setSecondaryShadowDrawable(R.drawable.shadowright);
                sm.setShadowDrawable(R.drawable.shadow);
                
        setContentView(R.layout.basic_map);
        
        Bundle extras = getIntent().getExtras();
		desX = extras.getDouble("desX");
		desY = extras.getDouble("desY");
        
        setUpMapIfNeeded();

        }
        
        
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
                getSupportMenuInflater().inflate(R.menu.lost_location, menu);
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
                //setUpMap();
                                        
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationButtonClickListener(this);
                mMap.setOnMapLoadedCallback(this);
                LatLng singapore = new LatLng(1.37, 103.84);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 11));
            }
        }
    }
        
    public void getRoute(){

                progress = ProgressDialog.show(this, "Finding Route","please wait...", true);      
                
                Location location = getMap().getMyLocation();
                if (location == null){
                	Toast.makeText(getApplicationContext(), "Unable to get current location", Toast.LENGTH_SHORT).show();
                	srcX = 1.379531;
                    srcY = 103.849928;
                }
                else {
                	srcX = location.getLatitude();
                    srcY = location.getLongitude(); 
                }
                
                GoogleRouteRequest retrieveAllLostRequest = new GoogleRouteRequest(srcX,srcY,desX,desY);
                new RouteBackgroundTask().execute( retrieveAllLostRequest,null);
               
        }
        

        
    private class RouteBackgroundTask extends AsyncTask<Runnable, Integer, Long> {
            
	    @Override
	    protected void onPostExecute(Long result) {  
	            super.onPostExecute(result);
	           
	            if(progress!=null)
	            	progress.dismiss();
	            
	            LINE = StaticObjects.getPolyline();
	            routeMap();
	    }
	    
	    @Override
	    protected void onPreExecute() {super.onPreExecute();}
	
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
    
    public void routeMap(){
    	//Toast.makeText(getApplicationContext(), "Line: "+LINE, Toast.LENGTH_SHORT).show();
    	if (LINE != null){
    		List<LatLng> decodedPath = PolyUtil.decode(LINE);
        	getMap().addPolyline(new PolylineOptions().addAll(decodedPath));
    	}
    }
    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    protected GoogleMap getMap() {
        setUpMapIfNeeded();
        return mMap;
    }       

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
        setUpLocationClientIfNeeded();
        mLocationClient.connect();
    }
     
    @Override
    protected void onNewIntent(Intent intent) {
                // TODO Auto-generated method stub
                super.onNewIntent(intent);
                
                String pinSelect = "none";
                pinSelect = intent.getStringExtra("pin");
                if (pinSelect.equals("lostpet")){
                        //toggleLostPet();
                }
                else if (pinSelect.equals("stray")){
                        //toggleFound();
                }
                else if (pinSelect.equals("event")){
                        //toggleEvent();
                }
                else if (pinSelect.equals("location")){
                        //toggleLocation();
                }
                
        }
    


        @SuppressLint("NewApi")
        @Override
    public void onPause() {
        super.onPause();
        if (mLocationClient != null) {
            mLocationClient.disconnect();
        }
    }
    
    private void setUpLocationClientIfNeeded() {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(
                    getApplicationContext(),
                    this,  // ConnectionCallbacks
                    this); // OnConnectionFailedListener
        }
    }
    
    @Override
    public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub  
    }
    
    public void onConnected(Bundle connectionHint) {
        mLocationClient.requestLocationUpdates(REQUEST,this);  // LocationListener
    }
    
    @Override
    public void onDisconnected() {
            // TODO Auto-generated method stub
            
    }

    @Override
    public void onConnectionFailed(ConnectionResult arg0) {
            // TODO Auto-generated method stub
            
    }
        
    public boolean onMyLocationButtonClick() {
        //Toast.makeText(this, "Moving to your location", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        Location location = getMap().getMyLocation();
        Toast.makeText(getApplicationContext(), location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        return false;
    }

	@Override
	public void onMapLoaded() {
		// TODO Auto-generated method stub
		getRoute();
	}


        
}