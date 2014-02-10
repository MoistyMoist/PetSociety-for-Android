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

public class CreatePetRequest implements Runnable{

	private StaticObjects staticObjects;
	private String name;
	private String breed;
	private String type;
	private String sex;
	private String age;
	private String bio;
	
	
	public CreatePetRequest(String name, String breed, String type, String sex, String age, String bio) {
		staticObjects= new StaticObjects();
		this.name = name;
		this.breed = breed;
		this.type = type;
		this.sex = sex;
		this.age = age;
		this.bio = bio;
	}


	@Override
	public void run() {

		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = null;
		
		//PREPARE REQUEST OBJECT
		//httpget = new HttpGet("http://petsociety.cloudapp.net/api/RetrieveLost?INtoken="+staticObjects.getToken()); 

		/*
		 
		 
		 /AddPet?INname={INname}&INbreed={INbreed}&INtype={INtype}&INsex={INsex}&INage={INage}&INbiography={INbiography}&INUserID={INUserID}&INimageID={INimageID}&INpinID={INpinID}
		 
		 
		 */
		
		
		httpget = new HttpGet("http://petsociety.cloudapp.net/api/AddPet?INname="+ name
								+"&INbreed=" + breed.replace(" ", "%20")
								+"&INtype=" + type.replace(" ", "%20")
								+"&INsex=" + sex
								+"&INage=" + age
								+"&INbiography=" + bio.replace(" ", "%20")
								+"&INUserID=" + staticObjects.getCurrentUser().getUserID()
								+"&INimageID=" + null
								+"&INpinID=" + null);
		
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
