package com.petsociety.httprequests;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import com.petsociety.utils.JASONExtractor;
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
	}
	
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient();
		 
        // Prepare a request object
        HttpGet httpget = new HttpGet("https://petsociety.azurewebsites.net/api/Login?token="+staticObjects.getToken()+"&INemail="+this.email+"&INpassword="+this.password); 
        System.out.print(httpget.getRequestLine());
        // Execute the request
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            // Examine the response status
            Log.i("fuck",response.getStatusLine().toString());
            // pass the result to the extractor
            JASONExtractor paser= new JASONExtractor();
            paser.ConvertLoginRequest(response);

        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	
	
}

