package com.petsociety.main.nearby;

import java.util.ArrayList;
import java.util.Deque;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;
import com.petsociety.httprequests.RetrieveAllLocationRequest;
import com.petsociety.httprequests.RetrieveLocationByTypeRequest;
import com.petsociety.utils.StaticObjects;



import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ShopInfoActivity extends NearbyActivity {
	
	ListView lvShopDetails;	
	ArrayAdapter<CharSequence> adapter;

	Context myContext;
	TextView tvShopName, tv1;
	ProgressDialog progress;
	Context context = this;
	ArrayList<String> locationTypeArrayList = new ArrayList<String>();

	
	String address;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_info);
		
		ArrayList<String> locationTypeArrayList = new ArrayList<String>();

		
		tvShopName =(TextView)findViewById(R.id.tvShopName); 
		tv1 =(TextView)findViewById(R.id.tv); 
		
	
		getNearbyList();
		
		
				
		
		//String[] items = {address.toString(), "porn.com", "shahrikins porn site"};
//		  myContext = this;
//			lvShopDetails = (ListView) findViewById(R.id.lvShopDetails);
//			
//			
//			
//			adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, items);
//			lvShopDetails.setAdapter(adapter);
//		
		
		
		/*items = {address.getString("address"), "Website : \n\n www.petstore.com \n", desc.getString("desc")
				, "Ratings :\n \n \n"};*/
		
			}
	    
      	


	

	public void getNearbyList() {

		if (StaticObjects.getLocations() == null
				|| StaticObjects.getLocations().size() == 0) {
			progress = ProgressDialog.show(this, "Getting your wishes",
					"please wait...", true);
			RetrieveLocationByTypeRequest retrieveLocationByTypeRequest = new RetrieveLocationByTypeRequest(
					"Pet%20Store");
			new BackgroundTask().execute(retrieveLocationByTypeRequest, null);
		}
		
			}

	private class BackgroundTask extends
			AsyncTask<Runnable, Integer, Long> {

		@Override
		protected void onPostExecute(Long result) {

			super.onPostExecute(result);
			if (progress != null)
				progress.dismiss();
			for (int i = 0; i < StaticObjects.getLocations().size(); i++) {
				locationTypeArrayList.add(StaticObjects.getLocations().get(i)
						.getType());
				
				if(StaticObjects.getLocations().get(i).getTitle().toString().equals(tvShopName.getText()))
				{
				String add = StaticObjects.getLocations().get(i).getDescription();
				tv1.setText(add);
				}

			}

			

		}

		@Override
		protected void onPreExecute() {
			// Toast.makeText(getBaseContext(), "Refreshing..",
			// Toast.LENGTH_SHORT).show();
			super.onPreExecute();
		}

		@Override
		protected Long doInBackground(Runnable... task) {

			for (int i = 0; i < task.length; i++) {
				if (task[i] != null)
					task[i].run();
				if (isCancelled())
					break;
			}
			return null;
		}
	}
	
	
	}


	

