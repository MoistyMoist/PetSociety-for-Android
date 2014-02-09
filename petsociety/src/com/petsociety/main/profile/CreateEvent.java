package com.petsociety.main.profile;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.example.petsociety.R;
import com.petsociety.httprequests.CreateEventRequest;
import com.petsociety.httprequests.CreateLostRequest;
import com.petsociety.main.lost.ReportLostPetLocation;
import com.petsociety.main.lost.ReportLostPetActivity.DatePickerFragment;
import com.petsociety.main.lost.ReportLostPetActivity.TimePickerFragment;

import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateEvent extends FragmentActivity {
	
	/*
	 * 	private String name;
	private String desc;
	private String startDateTime;
	private String endDateTime;
	private String x;
	private String y;
	 */
	double x;
	double y;
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
				startActivityForResult(intent, 1);
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
			CreateEventRequest createEventRequest = new CreateEventRequest(et_eventTitle.getText().toString(), et_description.getText().toString(), et_start_date.getText().toString()+"T"+et_start_time.getText().toString(), et_end_date.getText().toString()+"T"+et_end_time.getText().toString(), Double.toString(x), Double.toString(y));
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
	
	
	@SuppressLint("ValidFragment")
	public class TimePickerFragment extends DialogFragment
    implements TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		
		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute,
		DateFormat.is24HourFormat(getActivity()));
		}
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), ""+hourOfDay + ","+minute, Toast.LENGTH_SHORT).show();
			et_start_time.setText(""+hourOfDay+":"+minute+":00");
		}
	}
	
	@SuppressLint("ValidFragment")
	public class TimePickerFragment2 extends DialogFragment
    implements TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		
		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute,
		DateFormat.is24HourFormat(getActivity()));
		}
		
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), ""+hourOfDay + ","+minute, Toast.LENGTH_SHORT).show();
			et_end_time.setText(""+hourOfDay+":"+minute+":00");
		}
	}
	
	public void showStartTimePickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "timePicker");
	}
	
	public void showEndTimePickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment2();
	    newFragment.show(getSupportFragmentManager(), "timePicker");
	}
	
	
	@SuppressLint("ValidFragment")
	public class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		
		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), ""+year + ","+month+","+day, Toast.LENGTH_SHORT).show();
			et_start_date.setText(""+year+"-"+(month+1)+"-"+day);
		}
	}
	
	@SuppressLint("ValidFragment")
	public class DatePickerFragment2 extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		
		@Override
		public void onDateSet(DatePicker view, int year, int month, int day) {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), ""+year + ","+month+","+day, Toast.LENGTH_SHORT).show();
			et_end_date.setText(""+year+"-"+(month+1)+"-"+day);
		}
	}
	
	public void showStartDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	}
	
	public void showEndDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment2();
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//Toast.makeText(getApplicationContext(), "passeed", Toast.LENGTH_SHORT).show();
		x = data.getDoubleExtra("x", 1.3);
		y = data.getDoubleExtra("y", 103.8);
		getMyLocationAddress();
	}
	
public void getMyLocationAddress() {
        
        Geocoder geocoder= new Geocoder(this, Locale.ENGLISH);
         
        try {
              //Place your latitude and longitude
              List<Address> addresses = geocoder.getFromLocation(x, y, 1);
              if(addresses != null) {
               
                  Address fetchedAddress = addresses.get(0);
                  StringBuilder strAddress = new StringBuilder();
                
                  for(int i=0; i<fetchedAddress.getMaxAddressLineIndex(); i++) {
                        strAddress.append(fetchedAddress.getAddressLine(i));
                        //Log.i("Address", fetchedAddress.getAddressLine(i));
                  }
                  
        	    	et_location.setText(strAddress.toString());
              }
               
              else{
            	  et_location.setText("No location found");
              }
          
        } 
        catch (IOException e) {
                 // TODO Auto-generated catch block
                 e.printStackTrace();
                 Toast.makeText(getApplicationContext(),"Could not get address..!", Toast.LENGTH_LONG).show();
        }
    }


}