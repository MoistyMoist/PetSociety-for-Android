package com.petsociety.main.nearby;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Locale;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;
import com.petsociety.httprequests.RetrieveAllLocationRequest;
import com.petsociety.httprequests.RetrieveLocationByTypeRequest;
import com.petsociety.models.Location;
import com.petsociety.utils.StaticObjects;

import android.location.Geocoder;
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
import android.widget.Toast;

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
	

	String address, website, phone;

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
				tv_shopAddress.setText(getLocationAddress(StaticObjects.getLocations().get(i).getX(),StaticObjects.getLocations().get(i).getY()));
		        String staticDesc = StaticObjects.getLocations().get(i).getDescription().toString();
		        String[]splitDesc = staticDesc.split(";");
				tv_shopDesc.setText(splitDesc[0]);
				tv_shopWebsite.setText(splitDesc[1]);
		        phone = splitDesc[2];
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
	
	
}
