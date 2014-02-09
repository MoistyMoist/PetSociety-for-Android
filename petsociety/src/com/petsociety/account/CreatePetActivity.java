package com.petsociety.account;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.example.petsociety.R;
import com.petsociety.httprequests.CreateLostRequest;
import com.petsociety.httprequests.CreatePetRequest;
import com.petsociety.main.MainActivity;
import com.petsociety.main.lost.ReportLostPetLocation;
import com.petsociety.utils.StaticObjects;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
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


public class CreatePetActivity extends FragmentActivity {
	
	Spinner spin_type;
	Button b_create;
	EditText et_name, et_breed, et_type, et_bio, et_age, et_sex;
	//ArrayAdapter<CharSequence> adapter;
	//String[] spinItems = {"Dog","Cat","Bird"};
	Context context = this;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_create_pet);
		
		//spin_type = (Spinner) findViewById(R.id.spinner_type);
		//adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, spinItems);
		//spin_type.setAdapter(adapter);
		
		b_create = (Button) findViewById(R.id.b_pet_create);
		et_name = (EditText) findViewById(R.id.et_pet_name);
		et_breed = (EditText) findViewById(R.id.et_pet_breed);
		et_type = (EditText) findViewById(R.id.et_pet_type);	
		et_bio = (EditText) findViewById(R.id.et_pet_bio);
		et_age = (EditText) findViewById(R.id.et_pet_age);
		et_sex = (EditText) findViewById(R.id.et_pet_sex);
		
		b_create.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				CreatePetRequest createPetRequest = new CreatePetRequest(et_name.getText().toString(),et_breed.getText().toString(),et_type.getText().toString(),et_sex.getText().toString(),et_age.getText().toString(),et_bio.getText().toString());
			}
		});
		
	}
	
private class BackgroundTask extends AsyncTask<Runnable, Integer, Long> {
	    
		@Override
		protected void onPostExecute(Long result) {
			
			super.onPostExecute(result);
			Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
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
