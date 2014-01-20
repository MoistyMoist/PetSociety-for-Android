package com.petsociety.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.petsociety.httprequests.*;
import com.petsociety.main.lost.LostActivity;
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
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends MainBaseActivity 
	implements 
	ConnectionCallbacks,
	OnConnectionFailedListener,
	LocationListener, 
	OnMyLocationButtonClickListener,
	OnInfoWindowClickListener{

	public GoogleMap mMap;
	Button buttonMap;
    private LocationClient mLocationClient;
    ArrayList<Marker>mLostPet = new ArrayList<Marker>();
    ArrayList<Marker>mFound = new ArrayList<Marker>();
    List<Lost> lostList;
    List<Pet> petList;
	StaticObjects staticObjects;
	ProgressDialog progress;
	Context context = this;
    
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	
	
	public MainActivity() {
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
  
        
        getLostList();       
        
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			
		case R.id.main_right:
			getSlidingMenu().showSecondaryMenu(true);
			return true;
		
		case R.id.main_list:
			MapListDialog option= new MapListDialog();
			option.show(getFragmentManager(), null);
			return true;
			
		case R.id.main_refresh:
			getLostList();  
			return true;
			
		case R.id.main_camera:
		    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		    //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
		    //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

		    //startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		    startActivity(intent);
		    return true;
		}
		return super.onOptionsItemSelected(item);
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
            	
            	mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
            	mMap.setOnInfoWindowClickListener(this);
            	
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationButtonClickListener(this);
                LatLng singapore = new LatLng(1.37, 103.84);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 11));
            }
        }
    }
	
	public void getLostList(){
		
			progress = ProgressDialog.show(this, "Setting up map","please wait...", true);
			
			RetrieveAllPetRequest retrieveAllPetRequest = new RetrieveAllPetRequest();
			new PetListBackgroundTask().execute( retrieveAllPetRequest,null);
			
			RetrieveAllLostRequest retrieveAllLostRequest = new RetrieveAllLostRequest();
			//UploadImageRequest upload= new UploadImageRequest();
			new LostListBackgroundTask().execute( retrieveAllLostRequest,null);
			 


	}
	
	private class LostListBackgroundTask extends AsyncTask<Runnable, Integer, Long> {
	    
		@Override
		protected void onPostExecute(Long result) {
			
			super.onPostExecute(result);
			if(progress!=null)
				progress.dismiss();
	        staticObjects= new StaticObjects();
	        lostList = StaticObjects.getLosts();
			if(StaticObjects.getLosts()==null||StaticObjects.getLosts().size()==0){}
			else{
				addLostPetMarker();
			}			
		}

		@Override
		protected void onPreExecute() {
			//Toast.makeText(context, "Refreshing..", Toast.LENGTH_SHORT).show();
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
	
private class PetListBackgroundTask extends AsyncTask<Runnable, Integer, Long> {
	    
		@Override
		protected void onPostExecute(Long result) {
			
			super.onPostExecute(result);
			if(progress!=null)
				progress.dismiss();
	        staticObjects= new StaticObjects();
	        petList = StaticObjects.getPets();
		
		}

		@Override
		protected void onPreExecute() {
			//Toast.makeText(context, "Refreshing..", Toast.LENGTH_SHORT).show();
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


    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

	
    /** Demonstrates customizing the info window and/or its contents. */
    class CustomInfoWindowAdapter implements InfoWindowAdapter {

        // These a both viewgroups containing an ImageView with id "badge" and two TextViews with id "title" and "snippet".
        //private final View mWindow;
        private final View mContents;

        CustomInfoWindowAdapter() {
            //mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
            mContents = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoContents(Marker marker) {
            render(marker, mContents);
            return mContents;
        }
        
		@Override
		public View getInfoWindow(Marker arg0) {
			return null;
            //render(marker, mWindow);
            //return mWindow;
		}

        private void render(Marker marker, View view) {
            int badge = R.drawable.badge_lostdog;
            /*
            if (marker.equals(mBrisbane)) {
                badge = R.drawable.badge_qld;
            } else if (marker.equals(mAdelaide)) {
                badge = R.drawable.badge_sa;
            } else if (marker.equals(mSydney)) {
                badge = R.drawable.badge_nsw;
            } else if (marker.equals(mMelbourne)) {
                badge = R.drawable.badge_victoria;
            } else if (marker.equals(mPerth)) {
                badge = R.drawable.badge_wa;
            } */
            
            ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);

            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
           
            SpannableString titleText = new SpannableString(title);
            titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
            titleUi.setText(titleText);
            
            if (title.equals("Found")) {
                // Spannable string allows us to edit the formatting of the text.
                titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.GREEN), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            } 

            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            if (snippet != null && snippet.length() > 12) {
                SpannableString snippetText = new SpannableString(snippet);
                //snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
                snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 11, snippet.length(), 0);
                snippetUi.setText(snippetText);
            } else {
                snippetUi.setText("");
            }
        }
		
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
			toggleLostPet();
		}
		else if (pinSelect.equals("found")){
			toggleFound();
		}
		
	}
    
    public void addLostPetMarker(){
    	
    	for (int i=0; i<lostList.size(); i++){
    	
    		String lostPetName = "";
    		for (int p=0; p<petList.size(); p++){
    			if (lostList.get(i).getPetID()==petList.get(p).getPetID()){
    				lostPetName = petList.get(p).getName();
    			}
    		}
    		
    	double lat = lostList.get(i).getX();
    	double lng = lostList.get(i).getY();
    	//Toast.makeText(getApplicationContext(), "L:"+lat+","+lng, Toast.LENGTH_SHORT).show();
    	MarkerOptions mOption = new MarkerOptions()
			.position(new LatLng(lat, lng))
			.title("[Lost Pet] "+lostPetName)
			.snippet("Reward: "+lostList.get(i).getReward()); 
    	mLostPet.add(mMap.addMarker(mOption));
    	}
    }
    
    public void addFoundMarker(){
    	double[] pos = randomMarkerPos();
    	double lat = pos[0];
    	double lng = pos[1];
		//Toast.makeText(getApplicationContext(), "L:"+lat+","+lng, Toast.LENGTH_SHORT).show();
    	MarkerOptions mOption = new MarkerOptions()
			.position(new LatLng(lat, lng))
			.title("Found")
			.snippet("Last Seen: Today 9:48am")
			.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
    	mFound.add(mMap.addMarker(mOption));
    }
    
    public void toggleLostPet(){
    	boolean setVisible = true;
    	if (mLostPet.get(0).isVisible()==true){
    		setVisible = false;
    	}
		for (int i=0; i<mLostPet.size(); i++){
			mLostPet.get(i).setVisible(setVisible);
		}
    }
    
    public void toggleFound(){
    	boolean setVisible = true;
    	if (mFound.get(0).isVisible()==true){
    		setVisible = false;
    	}
		for (int i=0; i<mFound.size(); i++){
			mFound.get(i).setVisible(setVisible);
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
    
    public void showMyLocation(View view) {
        if (mLocationClient != null && mLocationClient.isConnected()) {
            String msg = "Location = " + mLocationClient.getLastLocation();
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
    
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub		
	}
    
    public void onConnected(Bundle connectionHint) {
        mLocationClient.requestLocationUpdates(
                REQUEST,
                this);  // LocationListener
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
        Toast.makeText(this, "Stalking...", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Opening profile...", Toast.LENGTH_SHORT).show();
	}

	@SuppressLint("ValidFragment")
	private class MapListDialog extends DialogFragment {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Dialog onCreateDialog(Bundle savedInstanceState){
			
			String[] mapList = {"Lost","Stray","Events","Locations"};
			ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,mapList);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		    builder.setTitle("List Category");builder.setAdapter(spinnerArrayAdapter, new OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent();
					intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.setClass(getActivity(), LostActivity.class);
					if(which==0){intent.putExtra("list", "lost");}
					if(which==1){intent.putExtra("list", "stray");}
					if(which==2){intent.putExtra("list", "event");}
					if(which==3){intent.putExtra("list", "location");}
					startActivity(intent);
				}});
		    return builder.create();
		}
	}
	
}
