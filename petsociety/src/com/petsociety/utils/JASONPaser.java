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

public class JASONPaser {

	//This methods converts the response into JSON
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
	
	//======================================================//
	//					CONVETORS							//
	//======================================================//
	
	
	public void ConvertLoginRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		HttpEntity entity = data.getEntity();
        // If the response does not enclose an entity, there is no need
        // to worry about connection release
        if (entity != null) {
        	 
            // A Simple JSON Response Read
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            Log.i("Praeda",result);

            // A Simple JSONObject Creation
            JSONObject json = null;
			try {
				json = new JSONObject(result);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            Log.i("Praeda","<jsonobject>\n"+json.toString()+"\n</jsonobject>");

            // A Simple JSONObject Parsing
            JSONArray nameArray=json.names();
            JSONArray valArray=json.toJSONArray(nameArray);
            for(int i=0;i<valArray.length();i++)
            {
                Log.i("Praeda","<jsonname"+i+">\n"+nameArray.getString(i)+"\n</jsonname"+i+">\n"
                        +"<jsonvalue"+i+">\n"+valArray.getString(i)+"\n</jsonvalue"+i+">");
            }

            // A Simple JSONObject Value Pushing
            json.put("sample key", "sample value");
            Log.i("Praeda","<jsonobject>\n"+json.toString()+"\n</jsonobject>");

            // Closing the input stream will trigger connection release
            instream.close();
        }
	}
	
	public void ConvertRegisterRequest(HttpResponse data)
	{
		
	}
	
}
