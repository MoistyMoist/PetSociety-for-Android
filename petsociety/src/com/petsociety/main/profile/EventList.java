package com.petsociety.main.profile;

import java.util.List;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;
import com.petsociety.httprequests.RetrieveAllEventRequest;
import com.petsociety.main.MainBaseActivity;
import com.petsociety.main.lost.LostProfileActivity;
import com.petsociety.main.lost.LostActivity.LostListAdapter;
import com.petsociety.models.Event;
import com.petsociety.models.Lost;
import com.petsociety.models.Pet;
import com.petsociety.utils.StaticObjects;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class EventList extends Activity {
	
	ListView lv_events;
	EventListAdapter adapter;
	StaticObjects staticObjects;
	ProgressDialog progress;
	Context context = this;

	List<Event> eventList;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);
		
		lv_events = (ListView) findViewById(R.id.lv_all_events);

		RetrieveAllEventRequest createAllEvents = new RetrieveAllEventRequest();
		GetEventList.execute(createAllEvents);
		
		lv_events.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getBaseContext(), EventActivity.class);
				startActivity(intent);
			}});
	}
	
private class GetEventList extends AsyncTask<Runnable, Integer, Long> {
	    
		@Override
		protected void onPostExecute(Long result) {
			
			super.onPostExecute(result);
			if(progress!=null)
				progress.dismiss();
			fillEventList();
		}

		@Override
		protected void onPreExecute() {
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

	private void fillEventList(){
		adapter = new EventListAdapter(context);
		for (int i=0; i<eventList.size(); i++){
            adapter.add(eventList.get(i));
		}
	    lv_events.setAdapter(adapter);
    }  
	
	public class EventListAdapter extends ArrayAdapter<Event> {

		public EventListAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_event_row, null);
			}
			TextView title = (TextView) convertView.findViewById(R.id.row_event_title);
			title.setText(getItem(position).getName());
			TextView desc = (TextView) convertView.findViewById(R.id.row_event_description);
			desc.setText(getItem(position).getDescription());	
			TextView date = (TextView) convertView.findViewById(R.id.row_event_date);
			date.setText(getItem(position).getName());	
			return convertView;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.event_list, menu);
		return true;
	}

}
