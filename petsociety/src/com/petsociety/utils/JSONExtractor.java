package com.petsociety.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.btrading.models.Product;
import com.btrading.models.User;
import com.btrading.utils.StaticObjects;

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
HttpEntity entity = data.getEntity();
        
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            
            JSONObject json = null;
            json = new JSONObject(result);
	
            //check status if all green to extract
			StaticObjects.setRequestStatus((Integer) json.get(TAG_STATUS));
			StaticObjects.setRequestMessage(json.getString(TAG_MESSAGE));
			JSONArray RawData= json.getJSONArray(TAG_DATA);
			//JSONArray errors=json.getJSONArray(TAG_ERRORS);
			
			ArrayList<Product>products= new ArrayList<Product>();
			if(StaticObjects.getRequestStatus()==0)
			{
				Log.i("product ",RawData.toString() );
				for(int i=0;i<RawData.length();i++)
				{
					JSONObject c=RawData.getJSONObject(i);
					
					Product p= new Product();
					p.setProductID(c.getInt(TAG_PRODUCT_ID));
					p.setName(c.getString(TAG_PRODUCT_NAME));
					p.setDescription(c.getString(TAG_PRODUCT_DESCRIPTION));
					p.setImageURL(c.getString(TAG_PRODUCT_IMAGEURL));
					p.setQty(c.getString(TAG_PRODUCT_QTY));
					p.setQuality(c.getString(TAG_PRODUCT_QUALITY));
					p.setX(c.getString(TAG_PRODUCT_XLOCATION));
					p.setY(c.getString(TAG_PRODUCT_YLOCATION));
					
					User u= new User();
//					JSONObject c2=(JSONObject) c.get(TAG_USER);
					
					u.setUserID(c.getInt(TAG_USER_ID));
					
					p.setUser(u);
					products.add(p);
					
					//Log.i("product "+i,c.toString() );
				}
				StaticObjects.setAllProducts(products);
			}
			else
			{
				Log.i("ERROR", "status==1");
				Log.i("Message",StaticObjects.getRequestMessage());
			}
            instream.close();
        }
		
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
