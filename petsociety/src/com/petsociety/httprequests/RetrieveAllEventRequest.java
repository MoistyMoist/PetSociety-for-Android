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

public class RetrieveAllEventRequest implements Runnable{

	private Date startDate;
	private Date endDate;
	private StaticObjects staticObjects;
	
	
	public RetrieveAllEventRequest()
	{
		this.startDate=null;
		this.endDate=null;
		staticObjects= new StaticObjects();
	}
	
	public RetrieveAllEventRequest(Date INstartDate, Date INendDate)
	{
		this.startDate=INstartDate;
		this.endDate=INendDate;
		staticObjects= new StaticObjects();
	}

	@Override
	public void run() {

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = null;
		
		//PREPARE REQUEST OBJECT
		if(this.startDate==null||this.endDate==null)
		{
			httpget = new HttpGet("http://petsociety.cloudapp.net/api/RetrieveEvent?INtoken="+staticObjects.getToken()); 
		}
		if(this.startDate!=null||this.endDate!=null)
		{
			httpget = new HttpGet("http://petsociety.cloudapp.net/api/RetrieveEvent?INtoken="+staticObjects.getToken()+"&INstartDateTime="+this.startDate+"&INendDateTime="+this.endDate);  
		}
        Log.i("RETRIEVE ALL EVENTS :",httpget.getURI().toString());
        
        
        
        
        //EXCUTE REQUEST
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            //PRINT OUT THE RESPONSE
            Log.i("RETRIEVE LOCATION RESPONSE :",response.getStatusLine().toString());
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
