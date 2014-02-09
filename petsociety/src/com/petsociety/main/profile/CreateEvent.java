package com.petsociety.main.profile;

import com.example.petsociety.R;
import com.petsociety.httprequests.CreateEventRequest;
import com.petsociety.main.lost.ReportLostPetLocation;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
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

public class CreateEvent extends Activity {
	
	/*
	 * 	private String name;
	private String desc;
	private String startDateTime;
	private String endDateTime;
	private String x;
	private String y;
	 */
	
	Context context = this;
	Button b_create_event, b_select_location;
	EditText et_eventTitle, et_description, et_location;
	EditText et_start_date, et_start_time, et_end_date, et_end_time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_event);
		
		b_create_event = (Button) findViewById(R.id.b_create_event);
		b_select_location = (Button) findViewById(R.id.b_select_location);
		et_eventTitle = (EditText) findViewById(R.id.et_eventTitle);
		et_description = (EditText) findViewById(R.id.et_description);
		et_location = (EditText) findViewById(R.id.et_location);
		et_start_date = (EditText) findViewById(R.id.et_start_date);
		et_start_time = (EditText) findViewById(R.id.et_start_time);
		et_end_date = (EditText) findViewById(R.id.et_end_date);
		et_end_time = (EditText) findViewById(R.id.et_end_time);

		b_select_location.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				//intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.setClass(context, ReportLostPetLocation.class);
				//startActivity(intent);
				startActivityForResult(intent, 1);
			}
		});

		
		b_create_event.setOnClickListener(new OnClickListener(){

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


		if (et_eventTitle.getText().toString().isEmpty()
				|| et_description.getText().toString().isEmpty()
				|| et_location.getText().toString().isEmpty())

		{
			Toast.makeText(CreateEvent.this, "Please fill in the empty fields",Toast.LENGTH_LONG).show();
		} else {
			CreateEventRequest createEventRequest = new CreateEventRequest(et_eventTitle.getText().toString(), et_description.getText().toString(), et_location.getText().toString(), null, null, null);
            new CreateEventBackgroundTask().execute( createEventRequest,null);
		}

	}
	
	 private class CreateEventBackgroundTask extends AsyncTask<Runnable, Integer, Long> {
         
         @Override
         protected void onPostExecute(Long result) {
                 super.onPostExecute(result);
                 //create success
         }

         @Override
         protected void onPreExecute() {super.onPreExecute();}

         @Override
         protected Long doInBackground(Runnable... task) {
                 for(int i=0; i<task.length;i++){
                         if(task[i]!=null)
                                 task[i].run();
                         if (isCancelled()) break;
                 }
                 return null;
         }
	 }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_event, menu);
		return true;
	}

}