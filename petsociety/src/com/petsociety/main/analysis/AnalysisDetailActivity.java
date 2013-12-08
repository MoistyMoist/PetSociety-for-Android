package com.petsociety.main.analysis;

import com.actionbarsherlock.view.MenuItem;
import com.androidnatic.maps.HeatMapOverlay;
import com.androidnatic.maps.SimpleMapView;
import com.example.petsociety.R;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.petsociety.main.LeftListFragment;
import com.petsociety.main.MainBaseActivity;
import com.petsociety.utils.StaticObjects;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class AnalysisDetailActivity extends MainBaseActivity 
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
	ListView list;
	CustomReviewAdapter adapter;
	
	
	public AnalysisDetailActivity() {
		super(R.string.title_activity_analysis_detail);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//SETS UP THE MAP
		setContentView(R.layout.activity_main_analysis_detail);
		setSlidingActionBarEnabled(true);		
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT);
		sm.setSecondaryShadowDrawable(R.drawable.shadowright);
		sm.setShadowDrawable(R.drawable.shadow);
		ViewGroup viewGroup=(ViewGroup)findViewById(R.id.analysis_nearby_map);
		viewGroup.addView(View.inflate(this, R.layout.basic_map, null));
		
		context=this;
		
		
		
		//POPULATING THE LISTVIEW
		//adapter= new CustomReviewAdapter();
		//list=(List)findViewByID(R.id.);
		//list.setAdapter(adapter);
		
		
		//CHECK IF PREVIOUS REQUEST WAS A SUCCESS
		
		
		//IF NOT PULL DATA FROM LOCAL DATABASE
		
		
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
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.analysis, menu);
		//getSupportMenuInflater().inflate(0, null);
		return true;
	}
	
	private void ClearMap()
	{
		mMap.clear();
	}
	private void PlotMap()
	{
		 mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
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
