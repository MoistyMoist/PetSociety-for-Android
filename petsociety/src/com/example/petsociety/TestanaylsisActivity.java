package com.example.petsociety;

import com.androidnatic.maps.HeatMapOverlay;
import com.androidnatic.maps.SimpleMapView;
import com.google.android.maps.MapActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TestanaylsisActivity extends MapActivity {
	private HeatMapOverlay overlay;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_testanaylsis);
		
		final SimpleMapView mapview = (SimpleMapView)findViewById(R.id.mapview);
		this.overlay = new HeatMapOverlay(20000, mapview);
		mapview.getOverlays().add(overlay);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.testanaylsis, menu);
		return true;
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}
