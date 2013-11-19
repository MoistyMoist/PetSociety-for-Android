package com.petsociety.main.lost;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;
import com.petsociety.main.BaseActivity;
import com.petsociety.main.LeftListFragment;
import com.petsociety.main.MainBaseActivity;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class LostActivity extends MainBaseActivity {

	public LostActivity() {
		super(R.string.app_name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_lost);
		
		
	}

}
