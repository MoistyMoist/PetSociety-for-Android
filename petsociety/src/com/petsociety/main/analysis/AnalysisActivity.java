package com.petsociety.main.analysis;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.petsociety.httprequests.RetrieveCurrentEventRequest;
import com.petsociety.httprequests.RetrieveEventByDateRequest;
import com.petsociety.httprequests.RetrieveLocationByTypeRequest;
import com.petsociety.httprequests.RetrieveReviewByLocationRequest;
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
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
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
	
	
	Button filterBtn;
	Button filterBtn2;
	
	ArrayList<Integer> selectedLocationType;
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
		
		
		//retrieve all the points (location, strays, lost, events
		RetrieveAllEventRequest eventRequest= new RetrieveAllEventRequest();
		RetrieveAllLocationRequest locationRequest= new RetrieveAllLocationRequest();
		RetrieveAllStrayRequest strayRequest= new RetrieveAllStrayRequest();
		RetrieveAllLostRequest lostRequest= new RetrieveAllLostRequest();
		
		new BackgroundTask().execute(locationRequest,lostRequest,eventRequest,locationRequest,strayRequest);
		
		
		
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
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 11));
            }
        }
    }
	@SuppressWarnings("unused")
	private void AddPin() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
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
		//LocationDialogFragment frag= new LocationDialogFragment();
		//frag.show(getFragmentManager(), null);
		AnalysisList option= new AnalysisList();
		option.show(getFragmentManager(), null);
		
		
		//RetrieveReviewByLocationRequest retrieveReview = new RetrieveReviewByLocationRequest(1); 
		//new BackgroundTask().execute(retrieveReview);
//		
//		Intent intent= new Intent(this,TestanaylsisActivity.class);
//		intent.setClass(getApplication(), TestanaylsisActivity.class);
//		startActivity(intent);
	}
	
	
	
	private void PlotPoint()
	{
		//linking the ui objects
		CircleOptions circleOptions = new CircleOptions()
	    .center(new LatLng(1.37, 103.84))
	    .radius(100) // In meters
	    .fillColor(0x2AFF00FF)//90% transparent red
	    .strokeColor(Color.TRANSPARENT);//dont show the border to the circle
		// Get back the mutable Circle
		mMap.addCircle(circleOptions);
		
		
		circleOptions = new CircleOptions()
	    .center(new LatLng(1.37, 103.84))
	    .radius(900) // In meters
	    .fillColor(0x2AFF00FF)//90% transparent red
	    .strokeColor(Color.TRANSPARENT);//dont show the border to the circle
		// Get back the mutable Circle
		mMap.addCircle(circleOptions);
		
	}
	
	
	

	private class BackgroundTask extends AsyncTask<Runnable, Integer, Long> {
	     
		@Override
		protected void onPostExecute(Long result) {
			
			super.onPostExecute(result);
			mMap.clear();
			if(StaticObjects.getAnalysisEvent()!=null)
			{
				PlotPoint();
			}
			if(StaticObjects.getAnalysisStray()!=null)
			{
				PlotPoint();
			}
			if(StaticObjects.getAndlysisLost()!=null)
			{
				PlotPoint();
			}
			if(StaticObjects.getAnslysisLocation()!=null)
			{
				PlotPoint();
			}
			else
			{
				Toast.makeText(context,"No Record Exists", Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected void onPreExecute() {
			Toast.makeText(context, "Refreshing..", Toast.LENGTH_SHORT).show();
			super.onPreExecute();
		}

		@Override
		protected Long doInBackground(Runnable... task) {
			
			for(int i=0; i<task.length;i++)
			{
				task[i].run();
				
				if (isCancelled()) break;
			}
			return null;
		}
	 }
	
	@SuppressLint("ValidFragment")
	private class LocationDialogFragment extends DialogFragment {
		@Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	    	selectedLocationType = new ArrayList<Integer>();  // Where we track the selected items
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        // Set the dialog title
	        builder.setTitle("Select Location Type");
	        // Specify the list array, the items to be selected by default (null for none),
	        // and the listener through which to receive callbacks when items are selected
	        builder.setMultiChoiceItems(StaticObjects.getLOCATION_TYPE(), null,
	                          new DialogInterface.OnMultiChoiceClickListener() {
	                   @Override
	                   public void onClick(DialogInterface dialog, int which,
	                           boolean isChecked) {
	                       if (isChecked) {
	                           // If the user checked the item, add it to the selected items
	                    	   selectedLocationType.add(which);
	                       } else if (selectedLocationType.contains(which)) {
	                           // Else, if the item is already in the array, remove it 
	                    	   selectedLocationType.remove(Integer.valueOf(which));
	                       }
	                   }
	               })
	               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                   @Override
	                   public void onClick(DialogInterface dialog, int id) {
	                      
	                	   StaticObjects.setAnalysisEvent(null);
	                	   StaticObjects.setAnalysisStray(null);
	                	   StaticObjects.setAndlysisLost(null);
	                	   StaticObjects.setAnslysisLocation(null);
	                	   
	                	   RetrieveLocationByTypeRequest request;
	                      for(int i=0;i<selectedLocationType.size();i++)
	                      {
	                    	  request= new RetrieveLocationByTypeRequest(StaticObjects.getLOCATION_TYPE()[selectedLocationType.get(i)]);
	                    	  new BackgroundTask().execute(request,request);
	                      }
	                   }
	               })
	               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                   @Override
	                   public void onClick(DialogInterface dialog, int id) {
	                      
	                   }
	               });

	        return builder.create();
	    }
	}

	@SuppressLint("ValidFragment")
	private class AnalysisList extends DialogFragment {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Dialog onCreateDialog(Bundle savedInstanceState){
			
			ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,StaticObjects.getANALYSIS_TYPE());
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		    builder.setTitle("Select Option");builder.setAdapter(spinnerArrayAdapter, new OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(which==0)
					{
						RetrieveAllEventRequest eventRequest= new RetrieveAllEventRequest();
						RetrieveAllLocationRequest locationRequest= new RetrieveAllLocationRequest();
						RetrieveAllStrayRequest strayRequest= new RetrieveAllStrayRequest();
						RetrieveAllLostRequest lostRequest= new RetrieveAllLostRequest();
						
						new BackgroundTask().execute(locationRequest,lostRequest,eventRequest,locationRequest,strayRequest);
					}
					if(which==1)
					{
						LocationDialogFragment frag= new LocationDialogFragment();
						frag.show(getFragmentManager(), null);
					}
					if(which==2)
					{
						//stray
						StrayDialogFragment frag= new StrayDialogFragment();
						frag.show(getFragmentManager(), null);
					}
					if(which==3)
					{
						//lost
						LostDialogFragment frag= new LostDialogFragment();
						frag.show(getFragmentManager(), null);
					}
					if(which==4)
					{
						//event
						EventDialogFragment frag= new EventDialogFragment();
						frag.show(getFragmentManager(), null);
					}
					
				}});
	    return builder.create();
		}
	}

	@SuppressLint("ValidFragment")
	private class EventDialogFragment extends DialogFragment
	{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			String[] eventList={"All Events","Upcoming Events","By Date"};
			ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,eventList);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		    builder.setTitle("Select Option");builder.setAdapter(spinnerArrayAdapter, new OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(which==0)
					{
						RetrieveAllEventRequest eventRequest= new RetrieveAllEventRequest();
						new BackgroundTask().execute(eventRequest,eventRequest);
					}
					if(which==1)
					{
						RetrieveCurrentEventRequest eventRequest= new RetrieveCurrentEventRequest();
						new BackgroundTask().execute(eventRequest,eventRequest);
					}
					if(which==2)
					{
						//event
//						EventDialogFragment frag= new EventDialogFragment();
//						frag.show(getFragmentManager(), null);
					}	
				}});
	    return builder.create();
		}
	}
	
	@SuppressLint("ValidFragment")
	private class LostDialogFragment extends DialogFragment
	{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        // Set the dialog title
	        builder.setTitle("Select Location Type");
			return builder.create();
		}
	}
	
	@SuppressLint("ValidFragment")
	private class StrayDialogFragment extends DialogFragment
	{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        // Set the dialog title
	        builder.setTitle("Select Location Type");
			return builder.create();
		}
	}
}
