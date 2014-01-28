package com.petsociety.main;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;
import com.petsociety.httprequests.OneMapSearchRequest;
import com.petsociety.httprequests.RetrieveAllLostRequest;
import com.petsociety.utils.StaticObjects;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Activity_search_address extends Activity {

	EditText editText;
	ListView searchResult;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_address);
		searchResult=(ListView)findViewById(R.id.searchResult);
		editText=(EditText)findViewById(R.id.searchAddress);
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search_address, menu);
		return true;
	}
	
	public void searchAddress(View view)
	{
		String query=editText.getText().toString();
		OneMapSearchRequest searchRequest= new OneMapSearchRequest(query);
		 new BackgroundTask().execute(searchRequest,searchRequest);
	}

	private class BackgroundTask extends AsyncTask<Runnable, Integer, Long> {
	     
		@Override
		protected void onPostExecute(Long result) {
			
			super.onPostExecute(result);
			
		}

		@Override
		protected void onPreExecute() {
			Toast.makeText(getBaseContext(), "Searching..", Toast.LENGTH_SHORT).show();
			super.onPreExecute();
		}

		@Override
		protected Long doInBackground(Runnable... task) {
			
			for(int i=0; i<task.length;i++)
			{
				task[i].run();
				
				if (isCancelled()) break;
			}
			return null;
		}
	 }
	
	
}
