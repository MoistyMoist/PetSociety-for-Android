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

public class CreateEventRequest implements Runnable{

	private StaticObjects staticObjects;
	private String name;
	private String desc;
	private String startDateTime;
	private String endDateTime;
	private String x;
	private String y;
	
	public CreateEventRequest(String name, String desc, String startDateTime,
			String endDateTime, String x, String y) {
		super();
		this.name = name;
		this.desc = desc;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.x = x;
		this.y = y;
	}

	@Override
	public void run() {

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = null;
		
		String url = "http://petsociety.cloudapp.net/api/AddEvent?token="+staticObjects.getToken()
				+"&INname=" + name.replace(" ", "%20") 
				+"&INdescription=" + desc.replace(" ", "%20") 
				+"&INstartDateTime=" + startDateTime
				+"&INendDateTime=" + endDateTime
				+"&INx=" + x
				+"&INy=" + y
				+"&INstatus=" + "0"
				+"&INprivacy=" + "0"
				+"&INuserID=" + StaticObjects.getCurrentUser().getUserID();
		
		Log.i("EVENT",url);
		
		httpget = new HttpGet(url);

        //Log.i("CREATE A NEW EVENT :",httpget.getURI().toString());
        
        
        
        
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
