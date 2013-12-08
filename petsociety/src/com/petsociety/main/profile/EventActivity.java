package com.petsociety.main.profile;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.string;
import com.petsociety.main.MainBaseActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EventActivity extends MainBaseActivity {

	public EventActivity() {
		super(R.string.app_name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_event);
	}

}
