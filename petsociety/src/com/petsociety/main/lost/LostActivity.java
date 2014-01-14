package com.petsociety.main.lost;

import java.util.List;

import com.actionbarsherlock.view.MenuItem;
import com.example.petsociety.R;
import com.petsociety.httprequests.RetrieveAllLostRequest;
import com.petsociety.main.MainBaseActivity;
import com.petsociety.models.Lost;
import com.petsociety.models.Pet;
import com.petsociety.utils.StaticObjects;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
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
	LostListAdapter adapter;
	StaticObjects staticObjects;
	ProgressDialog progress;
	Context context = this;
	
	List<Lost> lostList;
	List<Pet> petList;
	
	public LostActivity() {
		super(R.string.lost_pet);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_lost);
		
		lv_lost = (ListView) findViewById(R.id.lv_lost_pets);
		
		lv_lost.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getBaseContext(), LostProfileActivity.class);
				startActivity(intent);
			}});
		
		petList = StaticObjects.getPets();
		lostList = StaticObjects.getMapLost();
		fillLostAdapter();
		
  	  	//RetrieveAllLostRequest retrieveAllProductRequest = new RetrieveAllLostRequest();
		//new GetLostList().execute( retrieveAllProductRequest,null);
		
	}
	
private class GetLostList extends AsyncTask<Runnable, Integer, Long> {
	    
		@Override
		protected void onPostExecute(Long result) {
			
			super.onPostExecute(result);
			if(progress!=null)
				progress.dismiss();
	        staticObjects= new StaticObjects();
			if(StaticObjects.getLosts()==null||StaticObjects.getLosts().size()==0){}
			else{
				
			}			
		}

		@Override
		protected void onPreExecute() {
			//Toast.makeText(context, "Refreshing..", Toast.LENGTH_SHORT).show();
			super.onPreExecute();
		}

		@Override
		protected Long doInBackground(Runnable... task) {
			
			for(int i=0; i<task.length;i++)
			{
				if(task[i]!=null)
					task[i].run();
				if (isCancelled()) break;
			}
			return null;
		}
	 }
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.lost, menu);
		return true;
	}
	
	private void fillLostAdapter(){
    	adapter = new LostListAdapter(getBaseContext());
        for (int i=0; i<lostList.size(); i++){
        	
        	String lostPetName = "";
        	int lostPetAge = 0;
    		for (int p=0; p<petList.size(); p++){
    			if (lostList.get(i).getPetID()==petList.get(p).getPetID()){
    				lostPetName = petList.get(p).getName();
    				lostPetAge = Integer.parseInt(petList.get(p).getAge());
    			}
    		}
        	
                //Lost lostItem = lostList.get(i);
                //String lostPetName = lostItem.getPet().getName();
                //int lostPetAge = Integer.parseInt(lostItem.getPet().getAge());
                String lostPetLocation = lostList.get(i).getAddress();
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
