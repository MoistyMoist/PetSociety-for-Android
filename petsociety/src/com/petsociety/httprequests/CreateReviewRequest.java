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

public class CreateReviewRequest implements Runnable{

	private StaticObjects staticObjects;
	private String title;
	private String desc;

	
	public CreateReviewRequest(String title, String desc)
	{
		staticObjects= new StaticObjects();
		this.desc = desc;
		this.title = title;
	}
	
	@Override
	public void run() {

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = null;
		
		//PREPARE REQUEST OBJECT
		//httpget = new HttpGet("http://petsociety.cloudapp.net/api/RetrieveLost?INtoken="+staticObjects.getToken()); 
//api/AddReview?Intoken={Intoken}&INlocationID={INlocationID}&INstrayID={INstrayID}&INdescription={INdescription}&INtitle={INtitle}&INuserID={INuserID}
		httpget = new HttpGet("http://petsociety.cloudapp.net/api/AddReview?Intoken="+staticObjects.getToken()
				
								+"&INlocationID=" + StaticObjects.getSelectedLocation().getLocationID()
								+"&INstrayID=" + "1"
								+"&INdescription=" + desc.replace(" ", "%20") 
								+"&INtitle=" + title.replace(" ", "%20") 
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
