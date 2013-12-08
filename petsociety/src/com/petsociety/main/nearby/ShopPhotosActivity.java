package com.petsociety.main.nearby;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.Menu;
import android.widget.TextView;

public class ShopPhotosActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_photos);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shop_photos, menu);
		return true;
	}

}
