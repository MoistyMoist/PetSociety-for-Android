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

public class OneMapSearchRequest implements Runnable{

	public String query;
	@SuppressWarnings("unused")
	private StaticObjects staticObjects;
	
	public OneMapSearchRequest(String query)
	{
		this.query=query;
		staticObjects= new StaticObjects();
	}
	
	
	@Override
	public void run() {
		HttpClient httpclient = new DefaultHttpClient();
		 
        //PREPARE REQUEST OBJECT
		HttpGet httpget = new HttpGet("http://www.onemap.sg/API/services.svc/basicSearch?token=qo/s2TnSUmfLz+32CvLC4RMVkzEFYjxqyti1KhByvEacEdMWBpCuSSQ+IFRT84QjGPBCuz/cBom8PfSm3GjEsGc8PkdEEOEr&searchVal="+this.query+"&otptFlds=SEARCHVAL,CATEGORY&returnGeom=1&rset=1"); 
		
		Log.i("SEARCH REQUEST :",httpget.getURI().toString());
        //EXCUTE REQUEST
        HttpResponse response;
        try {
            response = httpclient.execute(httpget);
            //PRINT OUT THE RESPONSE
            Log.i("SEARCH RESPONSE :",response.getStatusLine().toString());
            //PASS THE RESPONSE TO THE EXTRACTOR
            JSONExtractor paser= new JSONExtractor();
           paser.ExtractOneMapSearchRequest(response);

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}	
}
