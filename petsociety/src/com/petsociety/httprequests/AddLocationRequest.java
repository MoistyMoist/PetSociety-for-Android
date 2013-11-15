package com.petsociety.httprequests;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import com.petsociety.utils.JASONPaser;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

public class AddLocationRequest implements Runnable {

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		HttpClient httpclient = new DefaultHttpClient();
		 
        // Prepare a request object
        HttpGet httpget = new HttpGet("https://petsociety.azurewebsites.net/api/values"); 
        System.out.print(httpget.getRequestLine());
        // Execute the request
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            // Examine the response status
            Log.i("fuck",response.getStatusLine().toString());
 
            // Get hold of the response entity
            JASONPaser paser= new JASONPaser();
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
