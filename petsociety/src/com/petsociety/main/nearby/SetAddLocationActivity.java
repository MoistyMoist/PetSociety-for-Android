package com.petsociety.main.nearby;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.petsociety.main.MainActivity;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.example.petsociety.R;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This shows how to listen to some {@link GoogleMap} events.
 */
public class SetAddLocationActivity extends SherlockFragmentActivity 
        implements OnMapClickListener {

    private GoogleMap mMap;
    ArrayList<Marker>mAddLocation = new ArrayList<Marker>();
    LatLng locationMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_add_location);

        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.lost_location_map);
		viewGroup.addView(View.inflate(this, R.layout.basic_map, null));
        setUpMapIfNeeded();
    }
    
    
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.add_location, menu);
		return true;
	}
	
	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		//return super.onOptionsItemSelected(item);
		Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
		Intent returnIntent = new Intent();
		returnIntent.putExtra("x",locationMarker.latitude);
		returnIntent.putExtra("y",locationMarker.longitude);
		setResult(RESULT_OK,returnIntent);     
		finish();
		return true;
	}


	@Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
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
                //mMap.setMyLocationEnabled(true);
                mMap.setOnMapClickListener(this);
                LatLng singapore = new LatLng(1.37, 103.84);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 11));
            }
        }
    }

    @Override
    public void onMapClick(LatLng point) {
        //mTapTextView.setText("tapped, point=" + point);
    	//Toast.makeText(getApplicationContext(), "tapped, point=" + point, Toast.LENGTH_SHORT).show();
    	locationMarker = point;
    	addLocationMarker();
    }
    
public void addLocationMarker(){
    	
	for (int i=0; i<mAddLocation.size(); i++){
		mAddLocation.get(i).remove();
	}
	//Toast.makeText(getApplicationContext(), "L:"+lat+","+lng, Toast.LENGTH_SHORT).show();
	MarkerOptions mOption = new MarkerOptions().position(locationMarker);
	mAddLocation.add(mMap.addMarker(mOption));
    	
    }
}
