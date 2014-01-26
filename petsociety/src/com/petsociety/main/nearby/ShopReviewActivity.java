package com.petsociety.main.nearby;

import java.util.ArrayList;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;
import com.petsociety.httprequests.RetrieveAllLocationRequest;
import com.petsociety.httprequests.RetrieveReviewByLocationRequest;
import com.petsociety.utils.StaticObjects;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ShopReviewActivity extends Activity {
	
	ArrayAdapter<CharSequence> adapter;
	ListView myList;
	Context myContext;
	EditText et_review;
	Button btn_review;
	ProgressDialog progress;
	ArrayList<String> reviews = new ArrayList<String>();
	
	String[] items = {"The Shop Has All that i Want!", "Quite Big", "Not Bad", "The Dog food lack variety but cat food is awesome!"};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_review);
		
		myContext = this;
		myList = (ListView) findViewById(R.id.list);
		
		adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_list_item_1, items);
		myList.setAdapter(adapter);
		et_review = (EditText)findViewById(R.id.et_review);
		btn_review = (Button)findViewById(R.id.btn_review);
		
		getAllReviews();
		
    }
	
	public void getAllReviews()
	{
		progress = ProgressDialog.show(this, "Collecting Reviews",
				"please wait...", true);
		RetrieveReviewByLocationRequest retrieveReviewByLocationRequest = new RetrieveReviewByLocationRequest(2);

		new AllReviewBackgroundTask().execute(
				retrieveReviewByLocationRequest, null);

	}
	private class AllReviewBackgroundTask extends
	AsyncTask<Runnable, Integer, Long> {

@Override
protected void onPostExecute(Long result) {

	super.onPostExecute(result);
	if (progress != null)
		progress.dismiss();
	for (int i = 0; i < StaticObjects.getLocations().size(); i++) {
	StaticObjects.getLocations().get(i).getReviews();
	}

	// textView1.setTag(staticObjects.getLocations().lastIndexOf(getTitle()));

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
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shop_review, menu);
		return true;
	}

}
