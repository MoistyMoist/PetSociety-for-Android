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

public class CreateLocationRequest implements Runnable{

	private StaticObjects staticObjects;
	private String title;
	private String desc;
	private String address;
	private String type;
	private String x;
	private String y;
	private String userID;
	
	public CreateLocationRequest(String x, String y, String desc, String title, String address, String type, String userID) {
		super();
		this.title = title;
		this.desc = desc;
		this.address = address;
		this.type = type;
		this.x = x;
		this.y = y;
		this.userID = userID;
	}

	@Override
	public void run() {

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = null;
		
		//PREPARE REQUEST OBJECT
		//httpget = new HttpGet("http://petsociety.cloudapp.net/api/RetrieveLost?INtoken="+staticObjects.getToken()); 

		httpget = new HttpGet("http://petsociety.cloudapp.net/api/AddLocation?token="+staticObjects.getToken()
	
								+"&INx=" + x
								+"&INy=" + y
								+"&INdescription=" + desc.replace(" ", "%20") 
								+"&INtitle=" + title.replace(" ", "%20") 
								+"&INaddress=" + address
								+"&INtype=" + type
								+"&INuserID=" + staticObjects.getCurrentUser().getUserID());
		
        Log.i("Retrieve  All  Location :",httpget.getURI().toString());
        
        
        
        
        //EXCUTE REQUEST
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            //PRINT OUT THE RESPONSE
            Log.i("Create Location RESPONSE :",response.getStatusLine().toString());
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


