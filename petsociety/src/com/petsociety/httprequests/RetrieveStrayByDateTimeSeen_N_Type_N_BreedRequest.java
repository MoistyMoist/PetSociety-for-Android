package com.petsociety.httprequests;

import java.io.IOException;
import java.sql.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import android.util.Log;

import com.petsociety.utils.JSONExtractor;
import com.petsociety.utils.StaticObjects;

public class RetrieveStrayByDateTimeSeen_N_Type_N_BreedRequest implements Runnable{

	private StaticObjects staticObjects;
	private String type;
	private Date dateTimeSeen;
	private String breed;
	
	public RetrieveStrayByDateTimeSeen_N_Type_N_BreedRequest(String type, Date dateTimeSeen, String breed)
	{
		this.type=type;
		this.dateTimeSeen=dateTimeSeen;
		this.breed=breed;
		staticObjects= new StaticObjects();
	}
	
	@Override
	public void run() {

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = null;
		
		//PREPARE REQUEST OBJECT
		httpget = new HttpGet("http://petsociety.cloudapp.net/api/RetrieveStray?INtoken="+staticObjects.getToken()+"&INtype="+this.type+"&INbreed="+this.breed+"&INdateTimeSeen="+this.dateTimeSeen.toString()); 

        Log.i("RETRIEVE STRAY :",httpget.getURI().toString());
        
        
        
        
        //EXCUTE REQUEST
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            //PRINT OUT THE RESPONSE
            Log.i("RETRIEVE STRAY RESPONSE :",response.getStatusLine().toString());
            //PASS THE RESPONSE TO THE EXTRACTOR
            JSONExtractor paser= new JSONExtractor();
            paser.ExtractStrayRequest(response);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
			e.printStackTrace();
		}
	}
}


