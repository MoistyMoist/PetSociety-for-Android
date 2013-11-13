package com.petsociety.httprequests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.util.Log;

public class LoginRequest {

	public void Login(String INtoken, String INemail, String INpassword) throws ClientProtocolException, IOException
	{
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpPost = new HttpGet("https://petsociety.azurewebsites.net/api/values");
		 
		//httpPost.setHeader("content-type", "application/json");
		JSONObject data = new JSONObject();
		 
//		data.put("message", dailyMilePost.getMessage());
//		JSONObject workoutData = new JSONObject();
//		data.put(&quot;workout&quot;, workoutData);
//		workoutData.put(&quot;activity_type&quot;, dailyMilePost.getActivityType());
//		workoutData.put(&quot;completed_at&quot;, dailyMilePost.getCompletedAt());
//		JSONObject distanceData = new JSONObject();
//		workoutData.put(&quot;distance&quot;, distanceData);
//		distanceData.put(&quot;value&quot;, dailyMilePost.getDistanceValue());
//		distanceData.put(&quot;units&quot;, dailyMilePost.getDistanceUnits());
//		workoutData.put(&quot;duration&quot;, dailyMilePost.getDurationInSeconds());
//		workoutData.put(&quot;title&quot;, dailyMilePost.getTitle());
//		workoutData.put(&quot;felt&quot;, dailyMilePost.getFelt());
//		 
//		StringEntity entity = new StringEntity(data.toString());
//		httpPost.setEntity(entity);
		 
		HttpResponse response = httpClient.execute(httpPost); 
		System.out.print(response.toString());
	}
	
	public void test()
	{
		 HttpClient httpclient = new DefaultHttpClient();
		 
	        // Prepare a request object
	        HttpGet httpget = new HttpGet("https://petsociety.azurewebsites.net/api/values"); 
	        System.out.print(httpget.getRequestLine());
	        // Execute the request
	        HttpResponse response;
	        try {
	            response = httpclient.execute(httpget);
	            // Examine the response status
	            Log.i("fuck",response.getStatusLine().toString());
	 
	            // Get hold of the response entity
	            HttpEntity entity = response.getEntity();
	            // If the response does not enclose an entity, there is no need
	            // to worry about connection release
	 
	           
	 
	 
	        } catch (ClientProtocolException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	}
	
	private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
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
	
	public void fuck()
	{
		System.out.println("fuck");
	}
}

