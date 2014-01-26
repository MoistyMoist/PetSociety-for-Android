package com.petsociety.main.profile;

import com.example.petsociety.R;
import com.petsociety.httprequests.RetrieveAllEventRequest;
import com.petsociety.main.MainBaseActivity;
import com.petsociety.models.Event;
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

public class EventList extends MainBaseActivity {
	
	ListView lv_events;
	EventListAdapter adapter;
	StaticObjects staticObjects;
	ProgressDialog progress;
	Context context = this;

	public EventList(){
		super(R.string.title_activity_event);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event_list);
		
		lv_events = (ListView) findViewById(R.id.lv_all_events);

		RetrieveAllEventRequest retrieveAllEvents = new RetrieveAllEventRequest();
		new GetEventList().execute(retrieveAllEvents, null);
		
		lv_events.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// TODO Auto-generated method stub
				int eventID = ((Event)arg0.getItemAtPosition(arg2)).getEventID();
				Intent intent = new Intent();
				intent.putExtra("event", eventID);
				intent.setClass(getBaseContext(), EventActivity.class);
				startActivity(intent);
			}});
	}
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.event, menu);
		return true;
	}
	
private class GetEventList extends AsyncTask<Runnable, Integer, Long> {
	    
		@Override
		protected void onPreExecute() {
		super.onPreExecute();
		}
	
		@Override
		protected void onPostExecute(Long result) {
			
			if(progress!=null)
			progress.dismiss();
	        staticObjects= new StaticObjects();
			fillEventList();
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

	private void fillEventList(){
		adapter = new EventListAdapter(context);
		for (int i=0; i<StaticObjects.getEvents().size(); i++){
            adapter.add(StaticObjects.getEvents().get(i));
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
			return convertView;
		}

	}

}
