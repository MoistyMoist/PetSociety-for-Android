package com.petsociety.main.profile;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;
import com.petsociety.account.CreatePetActivity;
import com.petsociety.httprequests.RetrieveAllEventRequest;
import com.petsociety.main.lost.ReportLostPetLocation;
import com.petsociety.utils.StaticObjects;

import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ProfileActivity extends Activity {
		
	TextView name, location;
	Button b_add_pet;
	ListView petListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_profile);
		
		name = (TextView) findViewById(R.id.profile_name);
		location = (TextView) findViewById(R.id.profile_location);
		b_add_pet = (Button) findViewById(R.id.b_add_pet);
		petListView = (ListView) findViewById(R.id.listView999);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
		
	    name.setText(StaticObjects.getCurrentUser().getName());
	    double x = (StaticObjects.getCurrentUser().getX());
	    double y = (StaticObjects.getCurrentUser().getY());
	    getMyLocationAddress(x, y);

	    for(int i=0; i<StaticObjects.getCurrentUser().getPets().size();i++){
	    	adapter.add(StaticObjects.getCurrentUser().getPets().get(i).getName());
	    }
	    if(StaticObjects.getCurrentUser().getPets().size() == 0){
	    	adapter.add("None");
	    }
	    petListView.setAdapter(adapter);
	    
	    petListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getBaseContext(), PetProfile.class);
				
				int petID = 1;
				for(int i=0; i<StaticObjects.getPets().size(); i++){
					if( arg0.getItemAtPosition(arg2).equals(StaticObjects.getPets().get(i).getName()) ){
						petID = StaticObjects.getPets().get(i).getPetID();
					}
				}
				
				
				intent.putExtra("pet", petID);
				startActivity(intent);
			}
	    	
	    });
	    
	    b_add_pet.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				//intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.setClass(getApplicationContext(), CreatePetActivity.class);
				startActivity(intent);
			}
	    	
	    });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	
	public void getMyLocationAddress(double x, double y) {
        
        Geocoder geocoder= new Geocoder(this, Locale.ENGLISH);
         
        try {
              //Place your latitude and longitude
              List<Address> addresses = geocoder.getFromLocation(x, y, 1);
              if(addresses != null) {
               
                  Address fetchedAddress = addresses.get(0);
                  StringBuilder strAddress = new StringBuilder();
                
                  for(int i=0; i<fetchedAddress.getMaxAddressLineIndex(); i++) {
                        strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
                        Log.i("Address", fetchedAddress.getAddressLine(i));
                  }
                  
        	    	location.setText(strAddress.toString());
              }
               
              else{
            	  location.setText("No location found..!");
              }
          
        } 
        catch (IOException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
                 Toast.makeText(getApplicationContext(),"Could not get address..!", Toast.LENGTH_LONG).show();
        }
    }
	
}
