package com.petsociety.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Date;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.petsociety.models.Event;
import com.petsociety.models.User;
import android.util.Log;

@SuppressWarnings("unused")
public class JSONExtractor {

	
	//JSON NODE NAMES
	private static final String TAG_MESSAGE ="Message";
	private static final String TAG_DATA = "Data";
	private static final String TAG_STATUS="Status";
	private static final String TAG_ERRORS="Errors";
	private static final String TAG_USER="USER";
	private static final String TAG_GALLERies="GALLERies";
	private static final String TAG_GALLERY="GALLERY";
	private static final String TAG_ATTENDEEs="ATTENDEEs";
	private static final String TAG_PETs="PETs";
	private static final String TAG_LOSTs="LOSTs";
	private static final String TAG_REVIEWs="REVIEWs";
	private static final String TAG_FRIENDREQUEST="FRIEND_REQUEST";
	private static final String TAG_STRAYs="STRAYs";
	private static final String TAG_LOCATIONs="LOCATIONs";
	
	//USER NODE NAMES
	private static final String TAG_USER_USERID="UserID";
	private static final String TAG_USER_PASSWORD="Password";
	private static final String TAG_USER_BIOGRAPHY="Biograpyh";
	private static final String TAG_USER_BIRTHDAY="Birthday";
	private static final String TAG_USER_PROFILEIMAGEURL="ProfileImageURL";
	private static final String TAG_USER_X="X";
	private static final String TAG_USER_Y="Y";
	private static final String TAG_USER_SEX="Sex";
	private static final String TAG_USER_PRIVICY="Privicy";
	private static final String TAG_USER_CONTACT="Contact";
	private static final String TAG_USER_NAME="Name";
	private static final String TAG_USER_EMAIL="Email";
	private static final String TAG_USER_ADDRESS="Address";
	private static final String TAG_USER_CREDIBILITY="Credibility";
	
	//EVENT NODE NAMES
	private static final String TAG_EVENT_USERID="UserID";
	private static final String TAG_EVENT_EVENTID="EventID";
	private static final String TAG_EVENT_DESCRIPTION="Description";
	private static final String TAG_EVENT_DATETIMECREATED="DateTimeCreated";
	private static final String TAG_EVENT_ENDDATETIME="EndDateTime";
	private static final String TAG_EVENT_STARTDATETIME="StartDateTime";
	private static final String TAG_EVENT_NAME="Name";
	private static final String TAG_EVENT_GALLERYID="GalleryID";
	private static final String TAG_EVENT_X="X";
	private static final String TAG_EVENT_Y="Y";
	private static final String TAG_EVENT_STATUS="Status";
	private static final String TAG_EVENT_PRIVACY="Privacy";
	
	//LOCATION NODE NAMES
	private static final String TAG_LOCATION_LOCATIONID="LocationID";
	
	//LOST NODE NAMES
	private static final String TAG_LOST_LOSTID="LostID";
	
	//STRAY NODE NAMES
	private static final String TAG_STRAY_STRAYID="StrayID";
	
	//GALLERY NODE NAMES
	private static final String TAG_GALLERY_GALLERYID="GalleryID";
	
	//IMAGE NODE NAMES
	private static final String TAG_IMAGE_IMAGEID="ImageID";
	
	
	
		
		
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
//		HttpEntity entity = data.getEntity();
//        // If the response does not enclose an entity, there is no need
//        // to worry about connection release
//        if (entity != null) {
//        	 
//            // A Simple JSON Response Read
//            InputStream instream = entity.getContent();
//            String result= convertStreamToString(instream);
//            //Log.i("FULL LOGIN RESPONSE",result);
//
//            // A Simple JSONObject Creation
//            JSONObject json = null;
//			try {
//				json = new JSONObject(result);
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//           // Log.i("JSON TO STRING","<jsonobject>\n"+json.toString()+"\n</jsonobject>");
//
//            //PARSING THE JSON STUFF
//            JSONArray nameArray=json.names();
//            JSONArray valArray=json.toJSONArray(nameArray);
//            for(int i=0;i<valArray.length();i++)
//            {
//                Log.i("value","<jsonname"+i+">\n"+nameArray.getString(i)+"\n</jsonname"+i+">\n"
//                        +"<jsonvalue"+i+">\n"+valArray.getString(i)+"\n</jsonvalue"+i+">");
//            }
//
//            // A Simple JSONObject Value Pushing
//            //json.put("sample key", "sample value");
//          //  Log.i("Praeda","<jsonobject>\n"+json.toString()+"\n</jsonobject>");
//
//            //CLOSE THE STREAM AND THE CONNECTION
//            instream.close();
//        }
	}
	
	//THIS METHOD ECTRACTS THE EVENT REQUEST DATA
	public void ExtractEventRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		HttpEntity entity = data.getEntity();
        
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            
            JSONObject json = null;
            json = new JSONObject(result);
	
            //check status if all green to extract
			StaticObjects.setResponseStatus((Integer) json.get(TAG_STATUS));
			StaticObjects.setResponseMessage(json.getString(TAG_MESSAGE));
			JSONArray RawData= json.getJSONArray(TAG_DATA);
			//JSONArray errors=json.getJSONArray(TAG_ERRORS);
			
			ArrayList<Event>events= new ArrayList<Event>();
			if(StaticObjects.getResponseStatus()==0)
			{
				Log.i("EVENT ",RawData.toString() );
				for(int i=0;i<RawData.length();i++)
				{
					JSONObject c=RawData.getJSONObject(i);
					
					Event e= new Event();
					e.setEventID(c.getInt(TAG_EVENT_EVENTID));
					e.setName(c.getString(TAG_EVENT_NAME));
					e.setDateTimeCreated((Date) c.get(TAG_EVENT_DATETIMECREATED));
					
					
					User u= new User();
					JSONObject c2=(JSONObject) c.get(TAG_USER);
					u.setUserID(c2.getInt(TAG_USER_USERID));
					u.setName(c2.getString(TAG_USER_NAME));
					u.setProfileImageURL(c2.getString(TAG_USER_PROFILEIMAGEURL));
					u.setBiography(c2.getString(TAG_USER_BIOGRAPHY));
					u.setContact(c2.getString(TAG_USER_CONTACT));
					u.setCredibility(c2.getString(TAG_USER_CREDIBILITY));
					u.setEmail(c2.getString(TAG_USER_EMAIL));					
					e.setUser(u);
					
				//	ArrayList<>
					
					
					events.add(e);
				}
				StaticObjects.setAnalysisEvent(events);
			}
			else
			{
				Log.i("ERROR", "status==1");
				Log.i("Message",StaticObjects.getResponseMessage());
			}
            instream.close();
        }
		
	}
	
	
	
}
