package com.petsociety.main.lost;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import android.os.Bundle;
import com.example.petsociety.R;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This shows how to listen to some {@link GoogleMap} events.
 */
public class ReportLostPetLocation extends FragmentActivity
        implements OnMapClickListener {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_map);

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    private void setUpMap() {
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng point) {
        //mTapTextView.setText("tapped, point=" + point);
    	Toast.makeText(getApplicationContext(), "tapped, point=" + point, Toast.LENGTH_SHORT).show();
    }
}
