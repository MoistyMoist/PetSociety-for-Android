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

public class RetrieveLocationByTypeRequest implements Runnable{

	private StaticObjects staticObjects;
	private String type;
	
	public RetrieveLocationByTypeRequest(String type)
	{
		this.type=type;
		staticObjects= new StaticObjects();
	}
	
	@Override
	public void run() {

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = null;
		
		//PREPARE REQUEST OBJECT
		httpget = new HttpGet("http://petsociety.cloudapp.net/api/RetrieveLocation?INtoken="+staticObjects.getToken()+"&INtype="+type); 

        Log.i("RETRIEVE LOCATION :",httpget.getURI().toString());
        
        //yeap its the variabble type. i dun get it
        //basically u have a String to search which is "Pet Store"
        //the url u are sending now is xxx/api/RetrieveLocation?INtoken=token()&INtype=Pet Store
        //but url standards do not allow spacing in url
        ///so the url u so send now is xxx/api/RetrieveLocation?INtoken=token()&INtype=Pet%20Store
        //so u mean i must do this? yeaps either that way or
        //u can put this before the url > type = type.replace(" ", "%20"); i think this works too <
        //okay shud i try again?
        
        //EXCUTE REQUEST
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            //PRINT OUT THE RESPONSE
            Log.i("RETRIEVE LOCATION RESPONSE :",response.getStatusLine().toString());
            //PASS THE RESPONSE TO THE EXTRACTOR
            JSONExtractor paser= new JSONExtractor();
            paser.ExtractLocationRequest(response);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
