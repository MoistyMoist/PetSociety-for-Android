package com.petsociety.main.nearby;

import com.example.petsociety.R;
import com.petsociety.httprequests.RetrieveAllEventRequest;
import com.petsociety.httprequests.RetrieveAllLocationRequest;
import com.petsociety.httprequests.RetrieveAllReviewRequest;
import com.petsociety.main.MainBaseActivity;
import com.petsociety.models.Event;
import com.petsociety.models.Location;
import com.petsociety.models.Review;
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

public class ShopReviewActivity extends MainBaseActivity {
	
	ListView lv_events;
	ReviewListAdapter adapter;
	StaticObjects staticObjects;
	ProgressDialog progress;
	Context context = this;

	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_review);
		
		lv_events = (ListView) findViewById(R.id.lv_all_events);

		RetrieveAllReviewRequest retrieveAllReviewRequest = new RetrieveAllReviewRequest();
		new GetReviewList().execute(retrieveAllReviewRequest, null);
		
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
		getSupportMenuInflater().inflate(R.menu.event, menu);
		return true;
	}
	
private class GetReviewList extends AsyncTask<Runnable, Integer, Long> {
	    
		@Override
		protected void onPreExecute() {
		super.onPreExecute();
		}
	
		@Override
		protected void onPostExecute(Long result) {
			
			if(progress!=null)
			progress.dismiss();
	        staticObjects= new StaticObjects();
			fillList();
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

	private void fillList(){
		adapter = new ReviewListAdapter(context);
		for (int i=0; i<StaticObjects.getReviews().size(); i++){
           
			
			adapter.add(StaticObjects.getReviews().get(i));
		}
	    lv_events.setAdapter(adapter);
    }  
	
	public class ReviewListAdapter extends ArrayAdapter<Review> {

		public ReviewListAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.custom_event_row, null);
			}
			TextView title = (TextView) convertView.findViewById(R.id.row_event_title);
			title.setText(getItem(position).getTitle());
//			TextView desc = (TextView) convertView.findViewById(R.id.row_event_description);
//			desc.setText(getItem(position).getDescription());	
			return convertView;
		}

	}

}
