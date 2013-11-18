package com.example.petsociety;

import com.petsociety.main.BaseActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class EventActivity extends BaseActivity {

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
