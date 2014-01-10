package com.petsociety.main.nearby;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;
import com.petsociety.utils.StaticObjects;



import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import android.widget.Toast;

	
public class NearbyDetailsActivity extends TabActivity {
	
	ImageButton call, email;
	Context context;
	TextView tvShopName;
	ListView lvShopDetails;	
	ArrayAdapter<CharSequence> adapter;
	StaticObjects staticObjects;
	Context myContext;
	
	



	
	
	
	@SuppressWarnings("deprecation")
	@Override	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby_details);
		
		
		tvShopName =(TextView)findViewById(R.id.tvShopName); 
		

		
		
		TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);
		
		tabHost.setup();
		
		tabHost.setup(this.getLocalActivityManager());
		
		TabSpec tab1 = tabHost.newTabSpec("Tab 0");
		 TabSpec tab2 = tabHost.newTabSpec("First Tab");
         TabSpec tab3 = tabHost.newTabSpec("Second Tab");
         TabSpec tab4 = tabHost.newTabSpec("Third tab");
         
         tab1.setIndicator("XX");
         tab1.setContent(new Intent(this, ShopMapActivity.class));
        
       
        
         tab2.setIndicator("Info");
         tab2.setContent(new Intent(this, ShopInfoActivity.class));
    
         
         tab3.setIndicator("Review");
         tab3.setContent(new Intent(this,ShopReviewActivity.class));

         tab4.setIndicator("Photos");
         tab4.setContent(new Intent(this,ShopPhotosActivity.class));
         
         /** Add the tabs  to the TabHost to display. */
         tabHost.addTab(tab1);
         tabHost.addTab(tab2);
         tabHost.addTab(tab3);
         tabHost.addTab(tab4);
         call = (ImageButton) findViewById(R.id.nearbyDetailsCall);
         email = (ImageButton) findViewById(R.id.nearbyDetailsMail);
         
         registerForContextMenu(email);
         
         Intent intentTitle= getIntent();
 	    Bundle title = intentTitle.getExtras();
 	    tvShopName.setText(title.get("title").toString());
         
         call.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				int phn_no = 11111111;
				try {
				    Intent my_callIntent = new Intent(Intent.ACTION_CALL);
				    my_callIntent.setData(Uri.parse("tel:"+phn_no));
				    //here the word 'tel' is important for making a call...
				    startActivity(my_callIntent);
				} catch (ActivityNotFoundException e) {
				    Toast.makeText(getApplicationContext(), "Error in your phone call"+e.getMessage(), Toast.LENGTH_LONG).show();
				}
				
			}});
    
		
	}

	




@Override    
public void onCreateContextMenu(ContextMenu menu, View v,
    		   ContextMenuInfo menuInfo) {
    		  super.onCreateContextMenu(menu, v, menuInfo);
    		  menu.setHeaderTitle("Context Menu");
    		  menu.add(0, v.getId(), 0, "Action 1");
    		  menu.add(0, v.getId(), 0, "Action 2");
    		  menu.add(0, v.getId(), 0, "Action 3");
    		 }
	
	
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nearby_details, menu);
		return true;
	}

}
