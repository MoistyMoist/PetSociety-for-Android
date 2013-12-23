package com.petsociety.httprequests;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import com.petsociety.utils.JSONExtractor;
import com.petsociety.utils.StaticObjects;

import android.util.Log;

public class LoginRequest implements Runnable{

	private String email;
	private String password;
	private StaticObjects staticObjects;	
	
	public LoginRequest(String INemail, String INpassword)
	{
		this.email=INemail;
		this.password=INpassword;
		staticObjects= new StaticObjects();
	}

	@Override
	public void run() {
		Log.i("email",this.email);
		HttpClient httpclient = new DefaultHttpClient();
		 
        //PREPARE REQUEST OBJECT
		HttpGet httpget = new HttpGet("http://petsociety.cloudapp.net/api/Login?token="+staticObjects.getToken()+"&INemail="+email+"&INpassword="+password); 
		
		Log.i("LOGIN REQUEST :",httpget.getURI().toString());
        //EXCUTE REQUEST
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            //PRINT OUT THE RESPONSE
            Log.i("LOGIN RESPONSE :",response.getStatusLine().toString());
            //PASS THE RESPONSE TO THE EXTRACTOR
            JSONExtractor paser= new JSONExtractor();
            paser.ExtractLoginRequest(response);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
			e.printStackTrace();
		}
	}
}

