package com.petsociety.main.profile;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.string;
import com.petsociety.main.MainBaseActivity;
import com.petsociety.main.profile.EventList.EventListAdapter;
import com.petsociety.models.Event;
import com.petsociety.utils.StaticObjects;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class EventActivity extends MainBaseActivity {

	TextView name, desc, time, location;

	public EventActivity() {
		super(R.string.app_name);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_event);
		Bundle extras = getIntent().getExtras();
		int eventID = extras.getInt("event");
		
		TextView name = (TextView) findViewById(R.id.tv_name);
		TextView desc = (TextView) findViewById(R.id.tv_description);
		TextView time = (TextView) findViewById(R.id.tv_time);
		TextView location = (TextView) findViewById(R.id.tv_location);

		Event oneEvent = null;
		
		for (int i=0; i<StaticObjects.getEvents().size(); i++){
			if(eventID == StaticObjects.getEvents().get(i).getEventID())
			{
				oneEvent = StaticObjects.getEvents().get(i);
			}

		}		
		
	    name.setText(oneEvent.getName());
	    desc.setText(oneEvent.getDescription());
	   // time.setText(oneEvent.getDateTimeCreated().toString());
		
	}

}
