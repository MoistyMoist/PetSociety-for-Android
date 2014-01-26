 package com.petsociety.main.nearby;

import java.util.ArrayList;
import java.util.Deque;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;
import com.petsociety.httprequests.RetrieveAllLocationRequest;
import com.petsociety.httprequests.RetrieveLocationByTypeRequest;
import com.petsociety.utils.StaticObjects;



import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShopInfoActivity extends NearbyActivity {
	
	ListView lvShopDetails;	
	ArrayAdapter<CharSequence> adapter;

	Context myContext;
	TextView tvShopName, tv1;
	ProgressDialog progress;
	Context context = this;
	ArrayList<String> locationTypeArrayList = new ArrayList<String>();

	
	String address;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_info);
		
		ArrayList<String> locationTypeArrayList = new ArrayList<String>();

		
		
		tv1 =(TextView)findViewById(R.id.tv); 
		
	
		 Intent intentAdd= getIntent();
	 	    Bundle title = intentAdd.getExtras();
	 	    tv1.setText(title.get("address").toString());
	         
		tv1.setText("address");
		
				
		
		String[] items = {address.toString(), "porn.com", "shahrikins porn site"};
		  myContext = this;
		//	lvShopDetails = (ListView) findViewById(R.id.lvShopDetails);
			
			
			
			adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, items);
			lvShopDetails.setAdapter(adapter);
		
		
		
		/*items = {address.getString("address"), "Website : \n\n www.petstore.com \n", desc.getString("desc")
				, "Ratings :\n \n \n"};*/
		
			}
	    
      	


	

	

	}


	

