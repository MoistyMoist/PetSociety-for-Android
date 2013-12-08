package com.petsociety.main.nearby;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShopReviewActivity extends Activity {
	
	ArrayAdapter<CharSequence> adapter;
	ListView myList;
	Context myContext;
	
	String[] items = {" \n", "The Shop Has All that i Want!", "Quite Big", "Not Bad", "The Dog food lack variety but cat food is awesome!"};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_review);
		
		myContext = this;
		myList = (ListView) findViewById(R.id.list);
		
		adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, items);
		myList.setAdapter(adapter);
		
    }
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shop_review, menu);
		return true;
	}

}
