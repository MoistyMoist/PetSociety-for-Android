package com.petsociety.main;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class Activity_search_address extends Activity {

	EditText editText;
	ListView searchResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_address);
		//searchResult=(ListView)findViewById(R.id.searchResult);
		//editText=(EditText)findViewById(R.id.sea)
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search_address, menu);
		return true;
	}
	
	public void searchAddress(View view)
	{
		
	}

}
