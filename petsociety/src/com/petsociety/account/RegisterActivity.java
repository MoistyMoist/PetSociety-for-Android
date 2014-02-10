package com.petsociety.account;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;
import com.petsociety.httprequests.CreateUserRequest;
import com.petsociety.httprequests.LoginRequest;
import com.petsociety.main.MainActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	Button b_reg;
	EditText name,pass,email,sex;
	String username, password;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_register);
		
		b_reg  = (Button) findViewById(R.id.b_reg);
		name = (EditText) findViewById(R.id.et_reg_name);
		pass = (EditText) findViewById(R.id.et_reg_pass);
		email = (EditText) findViewById(R.id.et_reg_email);
		sex = (EditText) findViewById(R.id.et_reg_sex);
		
		b_reg.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getBaseContext(), "Registered", Toast.LENGTH_SHORT).show();
				
				username = email.getText().toString();
				password = pass.getText().toString();
				
				CreateUserRequest createUserRequest = new CreateUserRequest(name.getText().toString(),password,username,sex.getText().toString());
				new BackgroundTask().execute(createUserRequest, null);
				
				
			}
			
		});
	}
	
	private class BackgroundTask extends AsyncTask<Runnable, Integer, Long> {
	    
		@Override
		protected void onPostExecute(Long result) {
			
			super.onPostExecute(result);
			Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
			LoginRequest retrieveUserRequest = new LoginRequest(username,password);
			new GetUserLogin().execute( retrieveUserRequest,null);
						
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
	
private class GetUserLogin extends AsyncTask<Runnable, Integer, Long> {
	    
		@Override
		protected void onPostExecute(Long result) {
			//super.onPostExecute(result);		
			finish();
			Intent intent = new Intent();
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.setClass(getBaseContext(), MainActivity.class);
			startActivity(intent);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
