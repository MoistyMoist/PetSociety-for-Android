package com.petsociety.account;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.example.petsociety.R;
import com.petsociety.httprequests.LoginRequest;
import com.petsociety.main.MainActivity;
import com.petsociety.utils.StaticObjects;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class LoginActivity extends Activity {

	//StaticObjects staticObjects;
	Button b_login, b_reg;
	StaticObjects staticObjects;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_login);
		
		b_login = (Button) findViewById(R.id.b_login_login);
		b_login.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//if (checkUser()){
					Intent intent = new Intent();
					intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
					intent.setClass(getBaseContext(), MainActivity.class);
					startActivity(intent);
				//}
			}
					
		});
		b_reg = (Button) findViewById(R.id.b_login_register);
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
	
	public boolean checkUser(){
		boolean validUser = false;
		
		if (b_login.getText().equals("") || b_login.getText()==null){
			//to be removed once application completed
			validUser = true;
		}
		
		/*
		if(StaticObjects.getCurrentUser()==null)
		{
		    new Thread(new Runnable() {
				  @Override
				  public void run()
				  {
					  	ExecutorService executor = Executors.newFixedThreadPool(1);
				        RetrieveAllProductRequest retrieveAllProductRequest = new RetrieveAllProductRequest();
				          
				        executor.execute(retrieveAllProductRequest);
						executor.shutdown();
				        try {
				        	executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
				       	  	Log.i(" RESPONSE :","ENDED REQUEST");
				       	  	
				        } catch (InterruptedException e) {
				           
				        }

				    runOnUiThread(new Runnable() {
				      @Override
				      public void run()
				      {
				        staticObjects= new StaticObjects();
				        if(StaticObjects.getAllProducts().size()==0||StaticObjects.getAllProducts()==null)
				        {
				        	Log.i("PRODUCT", "NO PRODUCT");
				        }
				        else
				        {
				        	//adapter = new ProductListAdapter(context, StaticObjects.getAllProducts());
						   // listview.setAdapter(adapter);
				        }
				        
				      }
				    });
				  }
				}).start();
		}
		else
		{
			Log.i("PRODUCT", "weird PRODUCT");
			//adapter = new ProductListAdapter(context, StaticObjects.getAllProducts());
		   // listview.setAdapter(adapter);
		}
		*/
		return validUser;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
