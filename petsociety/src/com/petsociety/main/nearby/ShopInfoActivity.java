package com.petsociety.main.nearby;

import java.util.ArrayList;
import java.util.Deque;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;
import com.petsociety.httprequests.RetrieveAllLocationRequest;
import com.petsociety.httprequests.RetrieveLocationByTypeRequest;
import com.petsociety.models.Location;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

public class ShopInfoActivity extends Activity {

	ListView lvShopDetails;
	ArrayAdapter<CharSequence> adapter;

	Context myContext;
	TextView tvShopName, tv_shopDesc, tv_shopAddress, tv_shopWebsite;
	String tv1;
	ProgressDialog progress;
	Context context = this;
	ArrayList<String> locationTypeArrayList = new ArrayList<String>();
	Location singleLocation= null;
	
	RatingBar ratingBar;
	

	String address;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_info);

		tv_shopDesc=(TextView) findViewById(R.id.tv_shopDesc);
		tv_shopAddress=(TextView)findViewById(R.id.tv_shopAddress);
		tv_shopWebsite=(TextView) findViewById(R.id.tv_shopWebsite);
		
	

		tv1= StaticObjects.getSelectedLocation().getTitle().toString();
		
		for(int i=0;i<StaticObjects.getLocations().size(); i++)
		{
			if(StaticObjects.getLocations().get(i).getTitle().toString().equals(tv1.toString()))
			{
				tv_shopDesc.setText(StaticObjects.getLocations().get(i).getDescription().toString());
				tv_shopAddress.setText(StaticObjects.getLocations().get(i).getAddress().toString());
				
			}
		}
		
//		
//		Location title = StaticObjects.getTitle();
//		tv1.setText(title.toString());
//		
//		
//		Intent intentTitle = getIntent();
//		Bundle title = intentTitle.getExtras();
//		
//		
//		for (int i=0; i<StaticObjects.getLocations().size(); i++){
//			if(title.toString().equals(StaticObjects.getLocations().get(i).getTitle()))
//			{
//				singleLocation = StaticObjects.getLocations().get(i);
//			}
//
//		}		
//		
//	   tv1.setText(singleLocation.getTitle());
//	   // singleDescription.setText(singleLocation.getDescription());
//	   // singleAddress.setText(singleLocation.getAddress());
//	    
//				
//		
	}

}
