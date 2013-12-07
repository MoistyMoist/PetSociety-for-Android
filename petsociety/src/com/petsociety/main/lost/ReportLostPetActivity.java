package com.petsociety.main.lost;

import com.example.petsociety.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


public class ReportLostPetActivity extends Activity {
	
	Spinner spin_type;
	Button report_lost;
	ArrayAdapter<CharSequence> adapter;
	String[] spinItems = {"Dog","Cat","Bird"};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_report_lost_pet);
		
		spin_type = (Spinner) findViewById(R.id.spinner_type);
		adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_dropdown_item, spinItems);
		spin_type.setAdapter(adapter);
		
		report_lost = (Button) findViewById(R.id.b_lost_report);
		report_lost.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Toast.makeText(getApplicationContext(), "Reported", Toast.LENGTH_SHORT).show();			
			}
			
		});
		
		
	}
	
	

}
