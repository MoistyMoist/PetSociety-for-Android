package com.petsociety.account;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;
import android.widget.TextView;

public class AccSettingActivity extends PreferenceActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_acc_setting);
		addPreferencesFromResource(R.xml.acc_setting);
	}


}
