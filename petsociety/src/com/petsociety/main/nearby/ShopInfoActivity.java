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

public class ShopInfoActivity extends Activity {
	
	ListView lvShopDetails;	
	ArrayAdapter<CharSequence> adapter;
	
	Context myContext;
	
	String[] items = {"Address: \n\n Lot 1 Shopper's Mall \n Choa Chu Kang Ave 4 \n", "Website : \n\n www.petstore.com \n", "Descriptions : \n\n We sell all kinds of Dog and cat Food do check us out! \n"
			, "Ratings :\n \n \n"};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_info);
		
		
    
        myContext = this;
		lvShopDetails = (ListView) findViewById(R.id.lvShopDetails);
		
		
		
		adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, items);
		lvShopDetails.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shop_info, menu);
		return true;
	}

}