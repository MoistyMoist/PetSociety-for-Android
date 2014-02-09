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

public class RetrieveImagesByLocationIDRequest implements Runnable{

	private StaticObjects staticObjects;
	private int imgID;
	
	public RetrieveImagesByLocationIDRequest(int imgID)
	{
		this.imgID=imgID;
		staticObjects= new StaticObjects();
	}
	
	@Override
	public void run() {

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = null;
		
		//PREPARE REQUEST OBJECT
		httpget = new HttpGet("http://petsociety.cloudapp.net/api/RetrieveGallery?token="+staticObjects.getToken()
				+"&locationID="+imgID); 

        Log.i("RETRIEVE LOST BY ID :",httpget.getURI().toString());
        
        
        
        
        //EXCUTE REQUEST
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            //PRINT OUT THE RESPONSE
            Log.i("RETRIEVE LOST RESPONSE :",response.getStatusLine().toString());
            //PASS THE RESPONSE TO THE EXTRACTOR
            JSONExtractor paser= new JSONExtractor();
            paser.ExtractImageRequest(response);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
			e.printStackTrace();
		}
	}
}


