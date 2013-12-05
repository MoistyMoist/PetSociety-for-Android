package com.petsociety.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class JSONExtractor {

	
	//JSON NODE NAMES
	private static final String TAG_MESSAGE ="Message";
	private static final String TAG_DATA = "Data";
	private static final String TAG_STATUS="Status";
	private static final String TAG_ERRORS="Errors";
	private static final String TAG_USER="USER";
	
	//EVENT NODE NAMES
	
		
		
	//THIS METHOD CONVERTS THE HTTP RESPONSE TO JSON.
	//DO NOT EDIT OR REMOVE THIS METHOD
	private static String convertStreamToString(InputStream is) 
	{
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
			
	//THIS METHOD EXTRACTS THE LOGIN REQUEST DATA
	public void ExtractLoginRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		HttpEntity entity = data.getEntity();
        // If the response does not enclose an entity, there is no need
        // to worry about connection release
        if (entity != null) {
        	 
            // A Simple JSON Response Read
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            //Log.i("FULL LOGIN RESPONSE",result);

            // A Simple JSONObject Creation
            JSONObject json = null;
			try {
				json = new JSONObject(result);
			} catch (JSONException e) {
				e.printStackTrace();
			}
           // Log.i("JSON TO STRING","<jsonobject>\n"+json.toString()+"\n</jsonobject>");

            //PARSING THE JSON STUFF
            JSONArray nameArray=json.names();
            JSONArray valArray=json.toJSONArray(nameArray);
            for(int i=0;i<valArray.length();i++)
            {
                Log.i("value","<jsonname"+i+">\n"+nameArray.getString(i)+"\n</jsonname"+i+">\n"
                        +"<jsonvalue"+i+">\n"+valArray.getString(i)+"\n</jsonvalue"+i+">");
            }

            // A Simple JSONObject Value Pushing
            //json.put("sample key", "sample value");
          //  Log.i("Praeda","<jsonobject>\n"+json.toString()+"\n</jsonobject>");

            //CLOSE THE STREAM AND THE CONNECTION
            instream.close();
        }
	}
	
	//THIS METHOD ECTRACTS THE LOCATION REQUEST DATA
	public void ExtractEventRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		
		
	}
	
	
	//THIS METHODS EXTRACTS THE REGISTER USER REQUEST DATA
	public void ExtractRegisterUserRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
	}
	
	//THIS METHODS EXTRACTS THE UPDATE USER REQUEST DATA
	public void ExtractUpdateUserRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
	}
}
