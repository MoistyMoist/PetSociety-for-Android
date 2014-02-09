package com.petsociety.main.nearby;

import android.app.ActivityGroup;
import android.app.TabActivity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petsociety.R;
import com.petsociety.main.RouteActivity;
import com.petsociety.utils.StaticObjects;

public class NearbyDetailsActivity extends TabActivity  {

	
	ImageButton call, email;
	Context context;
	TextView tvShopName;
	ListView lvShopDetails;
	ArrayAdapter<CharSequence> adapter;
	StaticObjects staticObjects;
	Context myContext;
	TabHost tabHost;
	TabSpec tab2,tab3,tab4;
	Button btnNavigate;
	ImageView imageViewNearbyDetails;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby_details);
		
		

		tvShopName = (TextView) findViewById(R.id.tvShopName);
		call = (ImageButton) findViewById(R.id.nearbyDetailsCall);
		btnNavigate=(Button) findViewById(R.id.btnNavigate);
		imageViewNearbyDetails=(ImageView)findViewById(R.id.imageViewNearbyDetails);

		
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
         
//	TabSpec tab1 = tabHost.newTabSpec("Tab 0");
		 tab2 = tabHost.newTabSpec("First Tab");
		 tab3 = tabHost.newTabSpec("Second Tab");
		 tab4 = tabHost.newTabSpec("Third tab");

//		tab1.setIndicator("Map");
//		tab1.setContent(new Intent(this, ShopInfoActivity.class));

		tab2.setIndicator("Info");
		tab2.setContent(new Intent(this, ShopInfoActivity.class));

		tab3.setIndicator("Review");
		tab3.setContent(new Intent(this, ShopReviewActivity.class));

		tab4.setIndicator("Photos");
		tab4.setContent(new Intent(this, ShopPhotosActivity.class));

		/** Add the tabs to the TabHost to display. */
		//tabHost.addTab(tab1);
		tabHost.addTab(tab2);
		tabHost.addTab(tab3);
		tabHost.addTab(tab4);
		
		call = (ImageButton) findViewById(R.id.nearbyDetailsCall);
		

		//registerForContextMenu(email);
		
	

		
		
	
//		Intent intentTitle = getIntent();
//		Bundle title = intentTitle.getExtras();
//		tvShopName.setText(title.get("nearbyTitle").toString());
		tvShopName.setText(StaticObjects.getSelectedLocation().getTitle().toString());
	String	pass = tvShopName.getText().toString();
		

		 
		
	 
		call.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				 String staticDesc = StaticObjects.getSelectedLocation().getDescription();
			        String[]splitDesc = staticDesc.split(";");
					
			          
				// TODO Auto-generated method stub
				String phn_no = splitDesc[2];
				try {
					Intent my_callIntent = new Intent(Intent.ACTION_CALL);
					my_callIntent.setData(Uri.parse("tel:" + phn_no));
					// here the word 'tel' is important for making a call...
					startActivity(my_callIntent);
				} catch (ActivityNotFoundException e) {
					Toast.makeText(getApplicationContext(),
							"Error in your phone call" + e.getMessage(),
							Toast.LENGTH_LONG).show();
				}

			}
		});

		
		btnNavigate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.putExtra("desX", StaticObjects.getSelectedLocation().getX());
				intent.putExtra("desY", StaticObjects.getSelectedLocation().getY());
				intent.setClass(getApplicationContext(), RouteActivity.class);
				startActivity(intent);
				
			}
		});
		
	}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nearby_details, menu);
		return true;
	}

}
