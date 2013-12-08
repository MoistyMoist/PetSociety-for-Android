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

public class RetrieveFoundByDate_N_TypeRequest implements Runnable{

	private StaticObjects staticObjects;
	private String type;
	private Date date;
	
	public RetrieveFoundByDate_N_TypeRequest(String type, Date date)
	{
		this.type=type;
		this.date=date;
		staticObjects= new StaticObjects();
	}
	
	@Override
	public void run() {

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = null;
		
		//PREPARE REQUEST OBJECT
		httpget = new HttpGet("http://petsociety.cloudapp.net/api/RetrieveLost?INtoken="+staticObjects.getToken()+"&INdateTimeCreated="+this.date.toString()+"&INtype="+this.type); 

        Log.i("RETRIEVE LOST BY TYPE N DATE:",httpget.getURI().toString());
        
        
        
        
        //EXCUTE REQUEST
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            //PRINT OUT THE RESPONSE
            Log.i("RETRIEVE LOST RESPONSE :",response.getStatusLine().toString());
            //PASS THE RESPONSE TO THE EXTRACTOR
            JSONExtractor paser= new JSONExtractor();
            paser.ExtractEventRequest(response);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
			e.printStackTrace();
		}
	}
}