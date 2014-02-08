package com.petsociety.main.nearby;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.petsociety.httprequests.CreateLocationRequest;
import com.petsociety.httprequests.CreateLostRequest;
import com.petsociety.main.lost.ReportLostPetLocation;
import com.petsociety.utils.StaticObjects;


import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddLocationActivity extends  FragmentActivity implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {

	
	EditText etAddTitle,etAddAddress,etAddDescription, etAddWebsite, etAddPhone;
	String  type = "Vet" ;
	float x, y;
	int userID=StaticObjects.getCurrentUser().getUserID();
	
	Button btnAddLocation,btnGetAddress;
	 Location mCurrentLocation;
	 Context context = this;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_location);
		
		etAddTitle = (EditText) findViewById(R.id.etAddTitle);
		etAddAddress = (EditText) findViewById(R.id.etAddAddress);
		etAddDescription = (EditText) findViewById(R.id.etAddDescription);
		etAddWebsite = (EditText) findViewById(R.id.etAddWebsite);
		etAddPhone = (EditText) findViewById(R.id.etAddPhone);
		
		btnGetAddress=(Button) findViewById(R.id.btnGetAddress);
		btnAddLocation=(Button) findViewById(R.id.btnAddLocation);
		
		
		
		btnGetAddress.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				//intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.setClass(context, SetAddLocationActivity.class);
				//startActivity(intent);
				startActivityForResult(intent, 1);
				
			}});
		
		
		btnAddLocation.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {

				CreateLocationRequest createLocationRequest = new CreateLocationRequest(x, y
								,etAddDescription.getText().toString(), etAddTitle.getText().toString()
												,etAddAddress.getText().toString(), type, userID  );
				new CreateLocationBackgroundTask().execute(createLocationRequest, null);
			}});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_location, menu);
		return true;
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}
	
	private class CreateLocationBackgroundTask extends AsyncTask<Runnable, Integer, Long> {
	    
		@Override
		protected void onPostExecute(Long result) {
			
			super.onPostExecute(result);
			Toast.makeText(getApplicationContext(), "New Location Created", Toast.LENGTH_SHORT).show();
			finish();
						
		}

		@Override
		protected void onPreExecute() {
			//Toast.makeText(context, "Refreshing..", Toast.LENGTH_SHORT).show();
			super.onPreExecute();
		}

		@Override
		protected Long doInBackground(Runnable... task) {
			
			for(int i=0; i<task.length;i++)
			{
				if(task[i]!=null)
					task[i].run();
				if (isCancelled()) break;
			}
			return null;
		}
	 }
	
	
	

}
