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
import com.petsociety.models.Location;
import com.petsociety.models.Stray;
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
	private static final String TAG_USER_BIOGRAPHY="Biography";
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
	private static final String TAG_USER_GALLERYID="GalleryID";
	
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
	private static final String TAG_LOCATION_TITLE="Title";
	private static final String TAG_LOCATION_DESCRIPTION="Description";
	private static final String TAG_LOCATION_ADDRESS="Address";
	private static final String TAG_LOCATION_TYPE="Type";
	private static final String TAG_LOCATION_USERID="UserID";
	private static final String TAG_LOCATION_DATETIMECREATED="DateTimeCreated";
	private static final String TAG_LOCATION_GALLERYID="GalleryID";
	private static final String TAG_LOCATION_X="X";
	private static final String TAG_LOCATION_Y="Y";
	
	//STRAY NODE NAMES
	private static final String TAG_STRAY_STRAYID="StrayID";
	private static final String TAG_STRAY_USERID="UserID";
	private static final String TAG_STRAY_TITLE="Title";
	private static final String TAG_STRAY_BIOGRAPHY="Biography";
	private static final String TAG_STRAY_DATETIMESEEN="DateTimeSeen";
	private static final String TAG_STRAY_TYPE="Type";
	private static final String TAG_STRAY_BREED="Breed";
	private static final String TAG_STRAY_STATUS="Status";
	private static final String TAG_STRAY_IMAGEURL="ImageURL";
	private static final String TAG_STRAY_X="X";
	private static final String TAG_STRAY_Y="Y";
	
	//LOST NODE NAMES
	private static final String TAG_LOST_LOSTID="LostID";
	private static final String TAG_LOST_USERID="UserID";
	
	
	
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
	
	//THIS METHOD EXTRACTS THE EVENT REQUEST DATA
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
					e.setDescription(c.getString(TAG_EVENT_DESCRIPTION));
					
					String date=c.getString(TAG_EVENT_DATETIMECREATED);
					//e.setDateTimeCreated(new Date(c.get(TAG_EVENT_DATETIMECREATED)));
					
					
					User u= new User();
					JSONObject c2=(JSONObject) c.get(TAG_USER);
					u.setUserID(c2.getInt(TAG_USER_USERID));
					u.setName(c2.getString(TAG_USER_NAME));
					u.setProfileImageURL(c2.getString(TAG_USER_PROFILEIMAGEURL));
					u.setBiography(c2.getString(TAG_USER_BIOGRAPHY));
					u.setContact(c2.getString(TAG_USER_CONTACT));
					u.setCredibility(c2.getString(TAG_USER_CREDIBILITY));
					u.setEmail(c2.getString(TAG_USER_EMAIL));			
					u.setAddress(c2.getString(TAG_USER_ADDRESS));
					u.setGalleryID(c2.getInt(TAG_USER_GALLERYID));
					u.setPrivicy(c2.getString(TAG_USER_PRIVICY));
					u.setSex(c2.getString(TAG_USER_SEX).charAt(0));
					u.setX(c2.getDouble(TAG_USER_X));
					u.setY(c2.getDouble(TAG_USER_Y));
					e.setUser(u);
					

					events.add(e);
				}
				StaticObjects.setAnalysisEvent(events);
				StaticObjects.setEvents(events);
			}
			else
			{
				Log.i("ERROR", "status==1");
				Log.i("Message",StaticObjects.getResponseMessage());
			}
            instream.close();
        }
		
	}

	//THIS METHOD EXTRACTS THE LOCATION REQUEST DATA
	public void ExtractLocationRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
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
			
			ArrayList<Location>locations= new ArrayList<Location>();
			if(StaticObjects.getResponseStatus()==0)
			{
				Log.i("LOCATION ",RawData.toString() );
				for(int i=0;i<RawData.length();i++)
				{
					JSONObject c=RawData.getJSONObject(i);
					
					Location e= new Location();
					e.setLocationID(c.getInt(TAG_LOCATION_LOCATIONID));
					e.setTitle(c.getString(TAG_LOCATION_TITLE));
					e.setDescription(c.getString(TAG_LOCATION_DESCRIPTION));
					e.setGalleryID(c.getInt(TAG_LOCATION_GALLERYID));
					e.setAddress(c.getString(TAG_LOCATION_ADDRESS));
					e.setType(c.getString(TAG_LOCATION_TYPE));
					e.setX(c.getInt(TAG_LOCATION_X));
					e.setY(c.getDouble(TAG_LOCATION_Y));
					String date=c.getString(TAG_EVENT_DATETIMECREATED);
					//e.setDateTimeCreated(new Date(c.get(TAG_EVENT_DATETIMECREATED)));
					
					
					User u= new User();
					JSONObject c2=(JSONObject) c.get(TAG_USER);
					u.setUserID(c2.getInt(TAG_USER_USERID));
					u.setName(c2.getString(TAG_USER_NAME));
					u.setProfileImageURL(c2.getString(TAG_USER_PROFILEIMAGEURL));
					u.setBiography(c2.getString(TAG_USER_BIOGRAPHY));
					u.setContact(c2.getString(TAG_USER_CONTACT));
					u.setCredibility(c2.getString(TAG_USER_CREDIBILITY));
					u.setEmail(c2.getString(TAG_USER_EMAIL));			
					u.setAddress(c2.getString(TAG_USER_ADDRESS));
					u.setGalleryID(c2.getInt(TAG_USER_GALLERYID));
					u.setPrivicy(c2.getString(TAG_USER_PRIVICY));
					u.setSex(c2.getString(TAG_USER_SEX).charAt(0));
					u.setX(c2.getDouble(TAG_USER_X));
					u.setY(c2.getDouble(TAG_USER_Y));
					e.setUser(u);
					

					locations.add(e);
				}
				StaticObjects.setAnslysisLocation(locations);
				StaticObjects.setLocations(locations);
			}
			else
			{
				Log.i("ERROR", "status==1");
				Log.i("Message",StaticObjects.getResponseMessage());
			}
            instream.close();
        }
	}

	//THIS METHOD EXTRACTS THE STRAY DATA
	public void ExtractStrayRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
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
			
			ArrayList<Stray>strays= new ArrayList<Stray>();
			if(StaticObjects.getResponseStatus()==0)
			{
				Log.i("STRAY ",RawData.toString() );
				for(int i=0;i<RawData.length();i++)
				{
					JSONObject c=RawData.getJSONObject(i);
					
					Stray e= new Stray();
					e.setStrayID(c.getInt(TAG_STRAY_STRAYID));
					e.setTitle(c.getString(TAG_STRAY_TITLE));
					e.setBiography(c.getString(TAG_STRAY_BIOGRAPHY));
					e.setImageURl(c.getString(TAG_STRAY_IMAGEURL));
					e.setBreed(c.getString(TAG_STRAY_BREED));
					e.setType(c.getString(TAG_STRAY_TYPE));
					e.setStatus(c.getInt(TAG_STRAY_STATUS));
					e.setX(c.getInt(TAG_STRAY_X));
					e.setY(c.getDouble(TAG_STRAY_Y));
					e.setUserID(c.getInt(TAG_STRAY_USERID));
					String date=c.getString(TAG_EVENT_DATETIMECREATED);
					//e.setDateTimeCreated(new Date(c.get(TAG_EVENT_DATETIMECREATED)));
					
					
					User u= new User();
					JSONObject c2=(JSONObject) c.get(TAG_USER);
					u.setUserID(c2.getInt(TAG_USER_USERID));
					u.setName(c2.getString(TAG_USER_NAME));
					u.setProfileImageURL(c2.getString(TAG_USER_PROFILEIMAGEURL));
					u.setBiography(c2.getString(TAG_USER_BIOGRAPHY));
					u.setContact(c2.getString(TAG_USER_CONTACT));
					u.setCredibility(c2.getString(TAG_USER_CREDIBILITY));
					u.setEmail(c2.getString(TAG_USER_EMAIL));			
					u.setAddress(c2.getString(TAG_USER_ADDRESS));
					u.setGalleryID(c2.getInt(TAG_USER_GALLERYID));
					u.setPrivicy(c2.getString(TAG_USER_PRIVICY));
					u.setSex(c2.getString(TAG_USER_SEX).charAt(0));
					u.setX(c2.getDouble(TAG_USER_X));
					u.setY(c2.getDouble(TAG_USER_Y));
					e.setUser(u);
					

					strays.add(e);
				}
				StaticObjects.setAnalysisStray(strays);
				StaticObjects.setStrays(strays);
			}
			else
			{
				Log.i("ERROR", "status==1");
				Log.i("Message",StaticObjects.getResponseMessage());
			}
            instream.close();
        }
	}
	
	//THIS METHOD EXTRACTS THE LOST DATA
	public void ExtractLostRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		
	}
	
	//THiS METHOD EXTRACTS THE REVIEW DATA
	public void ExtractReviewRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		
	}
}
