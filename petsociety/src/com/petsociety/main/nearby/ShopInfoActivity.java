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
import android.widget.ListView;
import android.widget.TextView;

public class ShopInfoActivity extends Activity {

	ListView lvShopDetails;
	ArrayAdapter<CharSequence> adapter;

	Context myContext;
	TextView tvShopName, tv1;
	ProgressDialog progress;
	Context context = this;
	ArrayList<String> locationTypeArrayList = new ArrayList<String>();
	Location singleLocation= null;
	

	String address;
	String[] items = { "address", "porn.com", "shahrikins porn site" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_info);

	
	
		tv1 = (TextView) findViewById(R.id.tv1);
		tv1.setText("sa");
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
