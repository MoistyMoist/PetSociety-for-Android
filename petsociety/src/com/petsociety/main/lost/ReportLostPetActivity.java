package com.petsociety.main.lost;

import java.util.Calendar;

import com.example.petsociety.R;
import com.petsociety.httprequests.CreateLostRequest;
import com.petsociety.main.MainActivity;
import com.petsociety.utils.StaticObjects;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


public class ReportLostPetActivity extends FragmentActivity {
	
	Spinner spin_type;
	Button report_lost, b_lost_location;
	TextView tv_date, tv_time;
	EditText et_desc, et_reward;
	//ArrayAdapter<CharSequence> adapter;
	//String[] spinItems = {"Dog","Cat","Bird"};
	Context context = this;
	double x,y;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_report_lost_pet);
		
		//spin_type = (Spinner) findViewById(R.id.spinner_type);
		//adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, spinItems);
		//spin_type.setAdapter(adapter);
		
		report_lost = (Button) findViewById(R.id.b_lost_report);
		b_lost_location = (Button) findViewById(R.id.b_lost_location);
		tv_time = (TextView) findViewById(R.id.tv_lost_time);
		tv_date = (TextView) findViewById(R.id.tv_lost_date);
		et_reward = (EditText) findViewById(R.id.et_lost_reward);	
		et_desc = (EditText) findViewById(R.id.et_lost_desc);	
		
		b_lost_location.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				//intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.setClass(context, ReportLostPetLocation.class);
				//startActivity(intent);
				startActivityForResult(intent, 1);
			}
		});
		
		report_lost.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {	
				CreateLostRequest createLostRequest = new CreateLostRequest(tv_date.getText().toString(), "amk", et_desc.getText().toString(), Double.toString(x),Double.toString(y),et_reward.getText().toString());
				new LostBackgroundTask().execute(createLostRequest, null);
			}
		});
		
	}
	
private class LostBackgroundTask extends AsyncTask<Runnable, Integer, Long> {
	    
		@Override
		protected void onPostExecute(Long result) {
			
			super.onPostExecute(result);
			Toast.makeText(getApplicationContext(), "Reported", Toast.LENGTH_SHORT).show();
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
			tv_time.setText(""+hourOfDay+":"+minute+":00");
		}
	}
	
	public void showTimePickerDialog(View v) {
	    DialogFragment newFragment = new TimePickerFragment();
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
			tv_date.setText(""+day+"/"+(month+1)+"/"+year);
		}
	}
	
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getSupportFragmentManager(), "datePicker");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		//Toast.makeText(getApplicationContext(), "passeed", Toast.LENGTH_SHORT).show();
		x = data.getDoubleExtra("x", 1.3);
		y = data.getDoubleExtra("y", 103.8);
	}
	

}
