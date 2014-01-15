package com.petsociety.main.profile;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;
import com.petsociety.httprequests.LoginRequest;
import com.petsociety.main.MainActivity;
import com.petsociety.main.lost.LostProfileActivity;
import com.petsociety.utils.StaticObjects;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateEvent extends Activity implements Runnable {

	Button b_create;
	EditText et_eventTitle, et_description, et_location;
	TimePicker timePicker1,timePicker2;
	StaticObjects staticObjects;
	
	public CreateEvent(String string, String string2, String string3) {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event);
		
		b_create = (Button) findViewById(R.id.b_create);
		et_eventTitle = (EditText) findViewById(R.id.et_eventTitle);
		et_description = (EditText) findViewById(R.id.et_description);
		et_location = (EditText) findViewById(R.id.et_location);
		timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
		timePicker2 = (TimePicker) findViewById(R.id.timePicker2);
		
		
		b_create.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			}
			
		});
	}
	
	public void checkEvent() {

		et_eventTitle = (EditText) findViewById(R.id.et_eventTitle);
		et_description = (EditText) findViewById(R.id.et_description);
		et_location = (EditText) findViewById(R.id.et_location);
		timePicker1 = (TimePicker) findViewById(R.id.timePicker1);
		timePicker2 = (TimePicker) findViewById(R.id.timePicker2);
			
		
		 if (et_eventTitle.getText().toString().isEmpty()
				|| et_description.getText().toString().isEmpty()|| et_location.getText().toString().isEmpty())
			
		{
			Toast.makeText(
					CreateEvent.this,
					"Please fill in the empty fields",
					Toast.LENGTH_LONG).show();
		}
		 else{
			 
		new Thread(new Runnable() {
			@Override
			public void run() {
				ExecutorService executor = Executors.newFixedThreadPool(1);

				CreateEvent createEventRequest = new CreateEvent(
						et_eventTitle.getText().toString(), et_description.getText()
								.toString(), et_location.getText().toString());

				executor.execute(createEventRequest);
				executor.shutdown();
				try {
					executor.awaitTermination(Long.MAX_VALUE,
							TimeUnit.NANOSECONDS);
					Log.i(" RESPONSE :", "ENDED REQUEST");

				} catch (InterruptedException e) {
				}

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						staticObjects = new StaticObjects();
						if (StaticObjects.getCurrentUser() == null) {
							Log.i("USER", "NO USER");
						} else {
							// User user = StaticObjects.getCurrentUser();
							// if(et_pass.getText().toString().equals(user.getPassword())){
							// validUser=true;
							// }
						}
					}
				});
			}
		}).start();
		Intent intent = new Intent();
		intent.setClass(getBaseContext(), CreateEvent.class);
		startActivity(intent);
		finish();
	}

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_event, menu);
		return true;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}