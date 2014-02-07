package com.petsociety.account;

import com.example.petsociety.R;
import com.petsociety.httprequests.LoginRequest;
import com.petsociety.main.MainActivity;
import com.petsociety.models.User;
import com.petsociety.utils.StaticObjects;

import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	public static final String PREFS_LOGIN = "LOGIN";
	
	StaticObjects staticObjects;
	Button b_login, b_reg;
	EditText et_username, et_pass;
	Boolean validUser = false;
	String message = "Please check your internet connection.";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_login);
		
		b_login = (Button) findViewById(R.id.b_login_login);
		b_reg = (Button) findViewById(R.id.b_login_register);
		et_username = (EditText) findViewById(R.id.et_login_username);
		et_pass = (EditText) findViewById(R.id.et_login_pass);
		
		
		SharedPreferences app = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        boolean passwordPreference = app.getBoolean("prefUserLogin", false);
		
		SharedPreferences settings = getSharedPreferences(PREFS_LOGIN, 0);
	    String username = settings.getString("username", "");
	    et_username.setText(username);
	    
	    if(passwordPreference){
	    	String password = settings.getString("password", "");
	    	et_pass.setText(password);
	    }
	    if (et_pass.getText().toString().isEmpty() || et_username.getText().toString().isEmpty()){}
	    else{
	    	b_login.setEnabled(false);
	    	b_reg.setEnabled(false);
	    	LoginRequest retrieveUserRequest = new LoginRequest(et_username.getText().toString(),et_pass.getText().toString());
			new GetUserLogin().execute( retrieveUserRequest,null);
	    }
	    
		b_login.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				b_login.setEnabled(false);

				  SharedPreferences settings = getSharedPreferences(PREFS_LOGIN, 0);
				  SharedPreferences.Editor editor = settings.edit();
				  editor.putString("username", et_username.getText().toString());
				  editor.putString("password", et_pass.getText().toString());
				  editor.commit();
		        LoginRequest retrieveUserRequest = new LoginRequest(et_username.getText().toString(),et_pass.getText().toString());
				new GetUserLogin().execute( retrieveUserRequest,null);
			}		
		});
		
		b_reg.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.setClass(getBaseContext(), RegisterActivity.class);
				startActivity(intent);
			}
		});
		
		staticObjects = new StaticObjects();
	}
	
    @Override
    protected void onStop(){
       super.onStop();
    }
	
	public void checkingUser(){
		User user = StaticObjects.getCurrentUser();
		if (user==null){message = "Wrong Username/Password.";}
		else if(et_pass.getText().toString().equals(user.getPassword())){
			validUser=true;        
		}	
	  	else{
	  		message = "Wrong Password.";
	  	}
	}
	
	private class GetUserLogin extends AsyncTask<Runnable, Integer, Long> {
	    
		@Override
		protected void onPostExecute(Long result) {
			//super.onPostExecute(result);		
			new CheckLogin().execute("email");
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
	
	private class CheckLogin extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            /*    
        	for(int i=0;i<2;i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }*/
                return "Okay";
        }        

		@Override
        protected void onPostExecute(String result) {   
			checkingUser();
			b_login.setEnabled(true);
            if (validUser){
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
				intent.setClass(getBaseContext(), MainActivity.class);
				startActivity(intent);
            }
            else {
            	Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }

}
