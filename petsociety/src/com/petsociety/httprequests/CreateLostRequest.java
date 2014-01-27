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

public class CreateLostRequest implements Runnable{

	private StaticObjects staticObjects;
	private String dateTime;
	private String address;
	private String desc;
	private String x;
	private String y;
	private String reward;
	
	public CreateLostRequest(String dateTime, String address, String desc, String x, String y, String reward)
	{
		staticObjects= new StaticObjects();
		this.dateTime = dateTime;
		this.address = address;
		this.desc = desc;
		this.x = x;
		this.y = y;
		this.reward = reward;
	}
	
	@Override
	public void run() {

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = null;
		
		//PREPARE REQUEST OBJECT
		//httpget = new HttpGet("http://petsociety.cloudapp.net/api/RetrieveLost?INtoken="+staticObjects.getToken()); 

		httpget = new HttpGet("http://petsociety.cloudapp.net/api/ReportLost?token="+staticObjects.getToken()
				
								+"&INdateTimeSeen=" + dateTime
								+"&INaddress=" + address.replace(" ", "%20") 
								+"&INDescription=" + desc
								+"&INx=" + x
								+"&INy=" + y
								+"&INfound=" + "0"
								+"&INreward=" + reward
								+"&INpetID=" + "1" 
								+"&INuserID=" + staticObjects.getCurrentUser().getUserID());
		
        Log.i("RETRIEVE ALL LOST :",httpget.getURI().toString());
        
        
        
        
        //EXCUTE REQUEST
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            //PRINT OUT THE RESPONSE
            Log.i("RETRIEVE LOST RESPONSE :",response.getStatusLine().toString());
            //PASS THE RESPONSE TO THE EXTRACTOR
            //JSONExtractor paser= new JSONExtractor();
            //paser.ExtractLostRequest(response);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }// catch (JSONException e) {e.printStackTrace();}
	}
}
