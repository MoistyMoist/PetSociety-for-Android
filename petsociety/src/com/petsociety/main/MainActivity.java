package com.petsociety.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

import com.petsociety.httprequests.*;
import com.petsociety.main.lost.LostActivity;
import com.petsociety.main.lost.LostProfileActivity;
import com.petsociety.main.nearby.NearbyActivity;
import com.petsociety.main.nearby.NearbyDetailsActivity;
import com.petsociety.main.nearby.NearbyList;
import com.petsociety.main.profile.EventActivity;
import com.petsociety.main.profile.PetProfile;
import com.petsociety.models.Address;
import com.petsociety.models.Event;
import com.petsociety.models.Lost;
import com.petsociety.models.Pet;
import com.petsociety.utils.MapPin;
import com.petsociety.utils.MultiDrawable;
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
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends MainBaseActivity 
	implements 
	ConnectionCallbacks,
	OnConnectionFailedListener,
	LocationListener, 
	OnMyLocationButtonClickListener,
	OnInfoWindowClickListener,
	OnMapLoadedCallback,
	
	ClusterManager.OnClusterClickListener<MapPin>,
	ClusterManager.OnClusterInfoWindowClickListener<MapPin>,
	ClusterManager.OnClusterItemClickListener<MapPin>, 
	ClusterManager.OnClusterItemInfoWindowClickListener<MapPin>


{

	public GoogleMap mMap;
	Button buttonMap;
    private LocationClient mLocationClient;
    List<Lost> lostList = new ArrayList<Lost>();
    List<Pet> petList = new ArrayList<Pet>(); 
    List<Event> eventList = new ArrayList<Event>();
    List<com.petsociety.models.Location> locationList = new ArrayList<com.petsociety.models.Location>(); 
    List<MapPin> mapPinList;
	ProgressDialog progress;
	Context context = this;
	
	private ClusterManager<MapPin> mClusterManager;
    
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
  
        if(StaticObjects.getSelectedAddress()==null)
        {
        	getAllList();       
        }
        else
        {
        	//set searched adress pin
        	plotAdressSearchResult();
        	StaticObjects.setSelectedAddress(null);
        }
	}
	
	public void plotAdressSearchResult()
	{
		//Toast.makeText(getBaseContext(), ""+StaticObjects.getSelectedAddress().getX()+","+StaticObjects.getSelectedAddress().getY(), Toast.LENGTH_LONG).show();
		
		MarkerOptions mOption = new MarkerOptions()
         .position(new LatLng(1.379531, 103.849928))
         .title(StaticObjects.getSelectedAddress().getAddress());
		setUpMapIfNeeded();
		mMap.addMarker(mOption);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
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
			getAllList();  
			return true;
			
		case R.id.main_camera:
			//Intent intent = new Intent();
			//intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			//intent.setClass(getApplicationContext(), RouteActivity.class);
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
            	
            	//mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());
            	//mMap.setOnInfoWindowClickListener(this);
            	
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationButtonClickListener(this);
                mMap.setOnMapLoadedCallback(this);
                LatLng singapore = new LatLng(1.37, 103.84);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 11));
                
            }
        }
    }
	
	public void getAllList(){
		
		clearAllMarkers();

		progress = ProgressDialog.show(this, "Setting up map","please wait...", true);
		
		RetrieveAllPetRequest retrieveAllPetRequest = new RetrieveAllPetRequest();
		new PetListBackgroundTask().execute( retrieveAllPetRequest,null);
		
		RetrieveAllLostRequest retrieveAllLostRequest = new RetrieveAllLostRequest();
		//UploadImageRequest upload= new UploadImageRequest();
		new LostListBackgroundTask().execute( retrieveAllLostRequest,null);
		
		RetrieveAllEventRequest retrieveAllEventRequest = new RetrieveAllEventRequest();
		new EventListBackgroundTask().execute( retrieveAllEventRequest,null);
		 
		RetrieveAllLocationRequest retrieveAllLocationRequest = new RetrieveAllLocationRequest();
		new LocationListBackgroundTask().execute( retrieveAllLocationRequest,null);	

	}
	
	public void clearAllMarkers(){
		mapPinList = new ArrayList<MapPin>();
		for (int i=0; i<lostList.size(); i++){
			lostList.remove(i);
		}
		for (int i=0; i<eventList.size(); i++){
			eventList.remove(i);
		}
	}
	
	private class LostListBackgroundTask extends AsyncTask<Runnable, Integer, Long> {
	    
		@Override
		protected void onPostExecute(Long result) {
			//super.onPostExecute(result);
			if(progress!=null)
				progress.dismiss();
	        lostList = StaticObjects.getLosts();
	        
	    	for (int i=0; i<lostList.size(); i++){
	    		for (int p=0; p<petList.size(); p++){
	    			if (lostList.get(i).getPetID()==petList.get(p).getPetID()){
	    				lostList.get(i).setPet(petList.get(p));
	    			}
	    		}
	    		MapPin pin = new MapPin();
	    		pin.setLost(lostList.get(i));
	    		pin.setType("lost");
	    		mapPinList.add(pin);
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
	        petList = StaticObjects.getPets();
	        
	        List<Pet> userPet = new ArrayList<Pet>();
	        for(int i=0; i<petList.size();i++){
	        	if(petList.get(i).getUserID()==StaticObjects.getCurrentUser().getUserID()){
	        		userPet.add(petList.get(i));
	        	}
	        }
	        StaticObjects.getCurrentUser().setPets(userPet);
		}

		@Override
		protected void onPreExecute() {super.onPreExecute();}

		@Override
		protected Long doInBackground(Runnable... task) {
			for(int i=0; i<task.length;i++){
				if(task[i]!=null)
					task[i].run();
				if (isCancelled()) break;
			}
			return null;
		}
	 }

	private class EventListBackgroundTask extends AsyncTask<Runnable, Integer, Long> {
	    
		@Override
		protected void onPostExecute(Long result) {
			//super.onPostExecute(result);
			if(progress!=null)
				progress.dismiss();
	        eventList = StaticObjects.getEvents();
	        
	        for (int i=0; i<eventList.size(); i++){
	    		MapPin pin = new MapPin();
	    		pin.setEvent(eventList.get(i));
	    		pin.setType("event");
	    		mapPinList.add(pin);
	    	}
	        //Toast.makeText(getApplicationContext(), ""+lostList.size()+"+"+eventList.size()+"="+mapPinList.size(), Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onPreExecute() {super.onPreExecute();}

		@Override
		protected Long doInBackground(Runnable... task) {
			for(int i=0; i<task.length;i++){
				if(task[i]!=null)
					task[i].run();
				if (isCancelled()) break;
			}
			return null;
		}
	 }
	
	private class LocationListBackgroundTask extends AsyncTask<Runnable, Integer, Long> {
	    
		@Override
		protected void onPostExecute(Long result) {
			//super.onPostExecute(result);
			if(progress!=null)
				progress.dismiss();
	        locationList = StaticObjects.getLocations();
	        for (int i=0; i<locationList.size(); i++){
	    		MapPin pin = new MapPin();
	    		pin.setLocation(locationList.get(i));
	    		pin.setType("location");
	    		mapPinList.add(pin);
	        }
	        invokeCluster();
		}

		@Override
		protected void onPreExecute() {super.onPreExecute();}

		@Override
		protected Long doInBackground(Runnable... task) {
			for(int i=0; i<task.length;i++){
				if(task[i]!=null)
					task[i].run();
				if (isCancelled()) break;
			}
			return null;
		}
	 }

    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

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
        
    boolean lostVisible = true;
    public void toggleLostPet(){
    	if(lostVisible){
    		lostVisible = false;
    		//show cluster
    	}
    	else {
    		lostVisible = true;
    		//show cluster
    	}
    	//re force cluster
    }
    
    public void toggleEvent(){
    	boolean setVisible = true;
    }
    
    public void toggleLocation(){
    	boolean setVisible = true;
    }
    
    public void toggleStray(){
    	boolean setVisible = true;
    }

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
	
    protected GoogleMap getMap() {
        setUpMapIfNeeded();
        return mMap;
    }
	
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "Moving to your location", Toast.LENGTH_SHORT).show();
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
			
			String[] mapList = {"Lost Cases","Nearby Places"};
			ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,mapList);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		    builder.setTitle("List Category");builder.setAdapter(spinnerArrayAdapter, new OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent();
					intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					if(which==0){
						intent.putExtra("list", "lost");
						intent.setClass(getActivity(), LostActivity.class);
						startActivity(intent);
					}
					if(which==1){
						intent.putExtra("list", "location");
						intent.setClass(getActivity(), NearbyList.class);
						startActivity(intent);
					}
					
				}});
		    return builder.create();
		}
	}
	
	public String getLocationAddress(double x, double y) {
        Geocoder geocoder= new Geocoder(this, Locale.ENGLISH);
        try {
              //Place your latitude and longitude
              List<android.location.Address> addresses = geocoder.getFromLocation(x, y, 1);
              if(addresses != null) {
                  android.location.Address fetchedAddress = addresses.get(0);
                  StringBuilder strAddress = new StringBuilder();
                  for(int i=0; i<fetchedAddress.getMaxAddressLineIndex(); i++) {
                        strAddress.append(fetchedAddress.getAddressLine(i));
                  } 
                  return strAddress.toString();
              }
              else{
            	  return("Address");
              }
        } 
        catch (IOException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
                 Toast.makeText(getApplicationContext(),"Could not get address..!", Toast.LENGTH_LONG).show();
        }
        
        return("Address");
    }
	
	
    private class MapRenderer extends DefaultClusterRenderer<MapPin> {
        private final IconGenerator mIconGenerator = new IconGenerator(getApplicationContext());
        private final IconGenerator mClusterIconGenerator = new IconGenerator(getApplicationContext());
        private final ImageView mImageView;
        private final ImageView mClusterImageView;
        private final int mDimension;

        public MapRenderer() {
            super(getApplicationContext(), getMap(), mClusterManager);

            View multiProfile = getLayoutInflater().inflate(R.layout.multi_profile, null);
            multiProfile.setBackgroundColor(Color.WHITE);
            mClusterIconGenerator.setContentView(multiProfile);
            mClusterImageView = (ImageView) multiProfile.findViewById(R.id.image);

            mImageView = new ImageView(getApplicationContext());
            mImageView.setBackgroundColor(Color.YELLOW);
            mDimension = (int) getResources().getDimension(R.dimen.custom_profile_image);
            mImageView.setLayoutParams(new ViewGroup.LayoutParams(mDimension, mDimension));
            int padding = (int) getResources().getDimension(R.dimen.custom_profile_padding);
            mImageView.setPadding(padding, padding, padding, padding);
            mIconGenerator.setContentView(mImageView);
        }

		@Override
        protected void onBeforeClusterItemRendered(MapPin pin, MarkerOptions markerOptions) {
            // Draw a single object and set the info window to show their name.
			String type = pin.getType().toString();
			double x = 0,y = 0;
			String title = "Default";
            if(type.equalsIgnoreCase("lost")){
            	mImageView.setImageResource(R.drawable.badge_lostdog);
            	mImageView.setBackgroundColor(Color.parseColor("#88FF0000"));
            	title = pin.getLost().getPet().getName();
            	x = pin.getLost().getX();
            	y = pin.getLost().getY();
            }
            if(type.equalsIgnoreCase("event")){
            	mImageView.setImageResource(R.drawable.badge_event);
            	mImageView.setBackgroundColor(Color.parseColor("#880000FF"));
            	title = pin.getEvent().getName();
            	x = pin.getEvent().getX();
            	y = pin.getEvent().getY();
            }
            if(type.equalsIgnoreCase("location")){
            	if(pin.getLocation().getType().equalsIgnoreCase("accidents")){mImageView.setImageResource(R.drawable.icon_warning);}
            	else if(pin.getLocation().getType().equalsIgnoreCase("vet")){mImageView.setImageResource(R.drawable.icon_vet);}
            	else {mImageView.setImageResource(R.drawable.icon_petstore);}
            	mImageView.setBackgroundColor(Color.parseColor("#8800FF00"));
            	title = pin.getLocation().getTitle();
            	x = pin.getLocation().getX();
            	y = pin.getLocation().getY();
            }
			
			//mImageView.setImageResource(R.drawable.badge_lostdog);
			Bitmap icon = mIconGenerator.makeIcon();
			markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon)).title(title);
        }

        @Override
        protected void onBeforeClusterRendered(Cluster<MapPin> cluster, MarkerOptions markerOptions) {
            // Draw multiple people.
            // Note: this method runs on the UI thread. Don't spend too much time in here (like in this example).
            List<Drawable> profilePhotos = new ArrayList<Drawable>(Math.min(4, cluster.getSize()));
            int width = mDimension;
            int height = mDimension;

            for (MapPin p : cluster.getItems()) {
                // Draw 4 at most.
                if (profilePhotos.size() == 4) break;
                Drawable drawable = null;
                String type = p.getType().toString();
                if(type.equalsIgnoreCase("lost")){
                	drawable = getResources().getDrawable(R.drawable.badge_lostdog);
                }
                if(type.equalsIgnoreCase("event")){
                	drawable = getResources().getDrawable(R.drawable.badge_event);
                }
                if(type.equalsIgnoreCase("location")){
                	if(p.getLocation().getType().equalsIgnoreCase("accidents")){drawable = getResources().getDrawable(R.drawable.icon_warning);}
                	else if(p.getLocation().getType().equalsIgnoreCase("vet")){drawable = getResources().getDrawable(R.drawable.icon_vet);}
                	else {drawable = getResources().getDrawable(R.drawable.icon_petstore);}
                }
            	drawable.setBounds(0, 0, width, height);
                profilePhotos.add(drawable);
            }
            MultiDrawable multiDrawable = new MultiDrawable(profilePhotos);
            multiDrawable.setBounds(0, 0, width, height);

            mClusterImageView.setImageDrawable(multiDrawable);
            Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }

        @Override
        protected boolean shouldRenderAsCluster(Cluster cluster) {
            // Always render clusters.
            return cluster.getSize() > 1;
        }

    }

    public boolean onClusterClick(Cluster<MapPin> cluster) {
        // Show a toast with some info when the cluster is clicked.
        //String firstName = cluster.getItems().iterator().next().name;
    	int lost=0, event=0, location=0;
    	List<MapPin> mp = (List<MapPin>) cluster.getItems();
    	
    	for (int i=0; i<cluster.getSize(); i++){
    		String type = mp.get(i).getType();   		
    		if (type.equals("lost")){lost++;}
    		if (type.equals("event")){event++;}
    		if (type.equals("location")){location++;}
    	}
    	String toastString = lost + " Lost Pets, " + event + " Events, " + location + " Places ";
    	
    	
        Toast.makeText(getApplicationContext(), toastString + "in this area", Toast.LENGTH_LONG).show();
        return true;
    }

    public void onClusterInfoWindowClick(Cluster<MapPin> cluster) {
        // Does nothing, but you could go to a list of the users.
    }
    
    public boolean onClusterItemClick(MapPin item) {
        // Does nothing, but you could go into the user's profile page, for example.
    	String type = item.getType();
    	double x=0, y=0;
    	if (type.equals("lost")){
    		x=item.getLost().getX();
    		y=item.getLost().getY();
    	}
		if (type.equals("event")){
			x=item.getEvent().getX();
    		y=item.getEvent().getY();
			}
		if (type.equals("location")){
			x=item.getLocation().getX();
    		y=item.getLocation().getY();
    	}
    	
        Toast.makeText(getApplicationContext(), getLocationAddress(x,y) , Toast.LENGTH_LONG).show();
        return false;
    }

    public void onClusterItemInfoWindowClick(MapPin item) {
        // Does nothing, but you could go into the user's profile page, for example.
    	String type = item.getType();
		if(type.equalsIgnoreCase("lost")){
			Intent intent = new Intent();
			intent.setClass(getBaseContext(), PetProfile.class);
			int petID = item.getLost().getPet().getPetID();
			intent.putExtra("pet", petID);
			startActivity(intent);
	    }
	    if(type.equalsIgnoreCase("event")){
			Intent intent = new Intent();
			intent.putExtra("event", item.getEvent().getEventID());
			intent.setClass(this, EventActivity.class);
			startActivity(intent);
	    }
	    if(type.equalsIgnoreCase("location")){
	    	if(item.getLocation().getType().equalsIgnoreCase("accidents")){
	     		Toast.makeText(getApplicationContext(), item.getLocation().getDescription(), Toast.LENGTH_SHORT).show();
	     	}
	     	else {
	     		StaticObjects.setSelectedLocation(item.getLocation());
				Intent intent = new Intent(this, NearbyDetailsActivity.class);				
				startActivity(intent);
	     	}
	    }
    }

    protected void invokeCluster() {
        //getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.503186, -0.126446), 9.5f));
    	
    	if(mClusterManager==null){
    		mClusterManager = new ClusterManager<MapPin>(this, getMap());
    	}
    	mClusterManager.clearItems();
        
        mClusterManager.setRenderer(new MapRenderer());
        getMap().setOnCameraChangeListener(mClusterManager);
        getMap().setOnMarkerClickListener(mClusterManager);
        getMap().setOnInfoWindowClickListener(mClusterManager);
        mClusterManager.setOnClusterClickListener(this);
        mClusterManager.setOnClusterInfoWindowClickListener(this);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        mClusterManager.addItems(mapPinList);
        mClusterManager.cluster();
        
    }

	@Override
	public void onMapLoaded() {
		// TODO Auto-generated method stub
        //Location location = mMap.getMyLocation();
        //StaticObjects.getCurrentUser().setX(location.getLatitude());
        //StaticObjects.getCurrentUser().setY(location.getLongitude()); 
	}

}
