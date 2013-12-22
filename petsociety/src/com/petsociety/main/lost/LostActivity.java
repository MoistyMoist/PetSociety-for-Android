package com.petsociety.main.lost;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.actionbarsherlock.view.MenuItem;
import com.example.petsociety.R;
import com.petsociety.httprequests.RetrieveAllLostRequest;
import com.petsociety.main.MainBaseActivity;
import com.petsociety.models.Lost;
import com.petsociety.models.Pet;
import com.petsociety.utils.StaticObjects;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LostActivity extends MainBaseActivity {

	ListView lv_lost;
	StaticObjects staticObjects;
	LostListAdapter adapter;
	
	public LostActivity() {
		super(R.string.lost_pet);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_lost);
		
		lv_lost = (ListView) findViewById(R.id.lv_lost_pets);
		/*
		LostListAdapter adapter = new LostListAdapter(this);
		for (int i=0; i<4; i++){
			adapter.add(new LostItem("Snowy"+i, i+1, "Ang Mo Kio Ave 8", "17/08/2013 At 5.30pm"));
		} 
		lv_lost.setAdapter(adapter);;
		*/
		lv_lost.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getBaseContext(), LostProfileActivity.class);
				startActivity(intent);
			}});
		
		if(StaticObjects.getMapLost()==null||StaticObjects.getMapLost().size()==0)
        {
            new Thread(new Runnable() {
                  @Override
                  public void run()
                  {
                	  ExecutorService executor = Executors.newFixedThreadPool(1);
                	  RetrieveAllLostRequest retrieveAllProductRequest = new RetrieveAllLostRequest();
                	  executor.execute(retrieveAllProductRequest);
                	  executor.shutdown();
                	  try {
                		  executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                		  Log.i(" RESPONSE :","ENDED REQUEST");                   
                	  } catch (InterruptedException e) {}

                	  runOnUiThread(new Runnable() {
                      @Override
                      public void run()
                      {
                    	  staticObjects= new StaticObjects();
                    	  if(StaticObjects.getMapLost().size()==0||StaticObjects.getMapLost()==null)
                    	  {
                                Log.i("LOST", "NO LOST");
                    	  }
                    	  else
                    	  {
                    		  	List<Lost> lostList = StaticObjects.getMapLost();
                    		  	fillLostAdapter(lostList);    
                    	  }
                      }
                    });
                  }
                }).start();
        }
        else
        {
        	List<Lost> lostList = StaticObjects.getMapLost();
        	fillLostAdapter(lostList);    
        } 
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.lost, menu);
		return true;
	}
	
	private void fillLostAdapter(List<Lost> lostList){
    	adapter = new LostListAdapter(getBaseContext());
        for (int i=0; i<lostList.size(); i++){
                Lost lostItem = lostList.get(i);
                String lostPetName = lostItem.getPet().getName();
                int lostPetAge = Integer.parseInt(lostItem.getPet().getAge());
                String lostPetLocation = lostItem.getAddress();
                //String lostPetDate = lostItem.getDateTimeSeen().toString();
                adapter.add(new LostItem(lostPetName, lostPetAge, lostPetLocation, "99/99/99"));
                //adapter.add(new LostItem(lostPetName, lostPetAge, lostPetLocation, lostPetDate));
            }  
        lv_lost.setAdapter(adapter);
	}
	
	private class LostItem {
		public String name;
		public int age;
		public String location;
		public String date;
		public LostItem(String name, int age, String location, String date) {
			this.name = name;
			this.age = age;
			this.location = location;
			this.date = date;
		}
	}

	public class LostListAdapter extends ArrayAdapter<LostItem> {

		public LostListAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_lost_row, null);
			}
			TextView name = (TextView) convertView.findViewById(R.id.row_lost_name);
			name.setText(getItem(position).name);
			TextView age = (TextView) convertView.findViewById(R.id.row_lost_age);
			age.setText(Integer.toString(getItem(position).age));	
			TextView location = (TextView) convertView.findViewById(R.id.row_lost_location);
			location.setText(getItem(position).location);
			TextView date = (TextView) convertView.findViewById(R.id.row_lost_date);
			date.setText(getItem(position).date);

			return convertView;
		}

	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.report_lost_pet_add:
			Intent intent = new Intent();
			intent.setClass(getBaseContext(), ReportLostPetActivity.class);
			startActivity(intent);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}

}
