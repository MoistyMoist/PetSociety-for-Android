package com.petsociety.httprequests;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.util.Log;

import com.petsociety.utils.JSONExtractor;
import com.petsociety.utils.StaticObjects;


public class UpdateLocationRequest implements Runnable{

	private StaticObjects staticObjects;
	
	int LocationID;
	String ImageURL;
	
	public UpdateLocationRequest( int LocationID, String imageURL)
	{
		this.LocationID=LocationID;
		this.ImageURL=imageURL;
		
	}
	@Override
	public void run() {

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = null;
		
		//PREPARE REQUEST OBJECT
		httpget = new HttpGet("http://petsociety.cloudapp.net/api/UpdateLocation?token=token&INlocationID="+this.LocationID+"&INimageURL="+this.ImageURL); 

        Log.i("RETRIEVE REVIEW :",httpget.getURI().toString());
        
        
        
        
        //EXCUTE REQUEST
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            //PRINT OUT THE RESPONSE
            Log.i("UPLOAD LOCATION RESPONSE :",response.toString());
            //PASS THE RESPONSE TO THE EXTRACTOR
           JSONExtractor paser= new JSONExtractor();
            paser.ExtractUpdateLocation(response);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

