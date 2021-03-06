package com.petsociety.main.nearby;

import com.actionbarsherlock.view.MenuItem;
import com.example.petsociety.R;
import com.petsociety.httprequests.RetrieveAllEventRequest;
import com.petsociety.httprequests.RetrieveAllLocationRequest;
import com.petsociety.main.MainBaseActivity;
import com.petsociety.main.lost.ReportLostPetActivity;
import com.petsociety.models.Event;
import com.petsociety.models.Location;
import com.petsociety.utils.StaticObjects;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class NearbyList extends MainBaseActivity {
	
	ListView lv_events;
	NearbyListAdapter adapter;
	StaticObjects staticObjects;
	ProgressDialog progress;
	Context context = this;

	public NearbyList(){
		super(R.string.title_activity_nearby);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);
		
		lv_events = (ListView) findViewById(R.id.lv_all_events);

		RetrieveAllLocationRequest retrieveAllPlaces = new RetrieveAllLocationRequest();
		new GetNearbyList().execute(retrieveAllPlaces, null);
		
		lv_events.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				int locationID = ((Location)arg0.getItemAtPosition(arg2)).getLocationID();
				Intent intent = new Intent();
				intent.putExtra("location", locationID);
				intent.setClass(getBaseContext(), LocationInfoActivity.class);
				startActivity(intent);
			}});
	}
	

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.lost, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.report_lost_pet_add:
			Intent intent = new Intent();
			intent.setClass(getBaseContext(), AddLocationActivity.class);
			startActivity(intent);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
private class GetNearbyList extends AsyncTask<Runnable, Integer, Long> {
	    
		@Override
		protected void onPreExecute() {
		super.onPreExecute();
		}
	
		@Override
		protected void onPostExecute(Long result) {
			
			if(progress!=null)
			progress.dismiss();
	        staticObjects= new StaticObjects();
			fillNearbyList();
			Log.i("String", "DONE");
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

	private void fillNearbyList(){
		adapter = new NearbyListAdapter(context);
		for (int i=0; i<StaticObjects.getLocations().size(); i++){
           
			if(StaticObjects.getLocations().get(i).getType().equalsIgnoreCase("Pet Store")|| StaticObjects.getLocations().get(i).getType().equalsIgnoreCase("Vet"))
			adapter.add(StaticObjects.getLocations().get(i));
		}
	    lv_events.setAdapter(adapter);
    }  
	
	public class NearbyListAdapter extends ArrayAdapter<Location> {

		public NearbyListAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_nearbylist_row, null);
			}
			TextView title = (TextView) convertView.findViewById(R.id.row_nearby_title);
			title.setText(getItem(position).getTitle());
			TextView desc = (TextView) convertView.findViewById(R.id.row_nearby_description);
			desc.setText((getItem(position).getDescription().split(";"))[0]);	
			
			
			
			return convertView;
		}

	}
	
    
	

}
