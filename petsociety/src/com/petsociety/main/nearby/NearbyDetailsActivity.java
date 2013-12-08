package com.petsociety.main.nearby;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;

import com.petsociety.main.lost.LostActivity.SampleAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

	
public class NearbyDetailsActivity extends ActivityGroup {
	
	

	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby_details);
		
		
		TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
		tabHost.setup();
		
		tabHost.setup(this.getLocalActivityManager());
		
		 TabSpec tab1 = tabHost.newTabSpec("First Tab");
         TabSpec tab2 = tabHost.newTabSpec("Second Tab");
         TabSpec tab3 = tabHost.newTabSpec("Third tab");
         
         tab1.setIndicator("Info");
         tab1.setContent(new Intent(this,ShopInfoActivity.class));
         
         tab2.setIndicator("Review");
         tab2.setContent(new Intent(this,ShopReviewActivity.class));

         tab3.setIndicator("Photos");
         tab3.setContent(new Intent(this,ShopPhotosActivity.class));
         
         /** Add the tabs  to the TabHost to display. */
         tabHost.addTab(tab1);
         tabHost.addTab(tab2);
         tabHost.addTab(tab3);
		
     
		
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nearby_details, menu);
		return true;
	}

}
