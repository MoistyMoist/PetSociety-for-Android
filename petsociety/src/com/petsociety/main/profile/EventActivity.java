package com.petsociety.main.profile;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.string;
import com.petsociety.main.MainBaseActivity;
import com.petsociety.main.profile.EventList.EventListAdapter;
import com.petsociety.utils.StaticObjects;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class EventActivity extends MainBaseActivity {

	public EventActivity() {
		super(R.string.app_name);
		// TODO Auto-generated constructor stub
	}

	EventListAdapter adapter;
	TextView name, desc, time, location;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_event);
		Bundle extras = getIntent().getExtras();
		int eventID = extras.getInt("event");
		
		name = (TextView) findViewById(R.id.tv_name);
		desc = (TextView) findViewById(R.id.tv_description);
		time = (TextView) findViewById(R.id.tv_time);
		location = (TextView) findViewById(R.id.tv_location);

		for (int i=0; i<StaticObjects.getEvents().size(); i++){
            adapter.add(StaticObjects.getEvents().get(i));
		}		
		
	}

}
