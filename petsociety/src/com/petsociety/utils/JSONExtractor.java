package com.petsociety.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.sql.Date;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.petsociety.models.Address;
import com.petsociety.models.Event;
import com.petsociety.models.Image;
import com.petsociety.models.Location;
import com.petsociety.models.Lost;
import com.petsociety.models.Pet;
import com.petsociety.models.Review;
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
	private static final String TAG_LOST_DATETIMESEEN="DateTimeSeen";
	private static final String TAG_LOST_ADDRESS="Address";
	private static final String TAG_LOST_DESCRIPTION="Description";
	private static final String TAG_LOST_X="X";
	private static final String TAG_LOST_Y="Y";
	private static final String TAG_LOST_FOUND="Found";
	private static final String TAG_LOST_REWARD="Reward";
	private static final String TAG_LOST_DATETIMECREATED="DateTimeCreated";
	
	//PET NODE NAMES
	private static final String TAG_PET_PETID="PetID";
	private static final String TAG_PET_NAME="Name";
	private static final String TAG_PET_BREED="Breed";
	private static final String TAG_PET_SEX="Sex";
	private static final String TAG_PET_TYPE="Type";
	private static final String TAG_PET_BIOGRAPHY="Biography";
	private static final String TAG_PET_AGE="Age";
	private static final String TAG_PET_USERID="UserID";
	private static final String TAG_PET_GALLERYID="GalleryID";
	private static final String TAG_PET_PROFILEIMAGEURL="ProfileImageURL";
	private static final String TAG_PET_DATETIMECREATED="DateTimeCreated";
	
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
		HttpEntity entity = data.getEntity();
        // If the response does not enclose an entity, there is no need to worry about connection release
        if (entity != null) {
        	 
        	InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            
            JSONObject json = null;
            json = new JSONObject(result);  Log.i("json",json.toString());
            
            //check status if all green to extract
			StaticObjects.setResponseStatus((Integer) json.get(TAG_STATUS));
			StaticObjects.setResponseMessage(json.getString(TAG_MESSAGE));
			
			if(json.isNull(TAG_DATA)){}
			else{
			
			JSONArray RawData= json.getJSONArray(TAG_DATA);
			//JSONArray errors=json.getJSONArray(TAG_ERRORS);
			
			Log.i("raw", RawData.toString());
            
			User u= new User();
			JSONObject c2=RawData.getJSONObject(0); 
			
			u.setUserID(c2.getInt(TAG_USER_USERID));
			u.setPassword(c2.getString(TAG_USER_PASSWORD));
			u.setName(c2.getString(TAG_USER_NAME));
			u.setProfileImageURL(c2.getString(TAG_USER_PROFILEIMAGEURL));
			u.setBiography(c2.getString(TAG_USER_BIOGRAPHY));
			u.setContact(c2.getString(TAG_USER_CONTACT));
			u.setCredibility(c2.getString(TAG_USER_CREDIBILITY));
			u.setEmail(c2.getString(TAG_USER_EMAIL));			
			u.setAddress(c2.getString(TAG_USER_ADDRESS));
			//u.setGalleryID(c2.getInt(TAG_USER_GALLERYID));
			u.setPrivicy(c2.getString(TAG_USER_PRIVICY));
			u.setSex(c2.getString(TAG_USER_SEX).charAt(0));
			u.setX(c2.getDouble(TAG_USER_X));
			u.setY(c2.getDouble(TAG_USER_Y));
			
			StaticObjects.setCurrentUser(u);
			
            //CLOSE THE STREAM AND THE CONNECTION
            }
            instream.close();
        }
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
					Log.i("FULL", "status==0");
					Event e= new Event();
					e.setEventID(c.getInt(TAG_EVENT_EVENTID));
					e.setName(c.getString(TAG_EVENT_NAME));
					e.setDescription(c.getString(TAG_EVENT_DESCRIPTION));
					e.setX(c.getDouble(TAG_EVENT_X));
					e.setY(c.getDouble(TAG_EVENT_Y));
					String dateTimeCreated=c.getString(TAG_EVENT_DATETIMECREATED);
					//e.setDateTimeCreated(new Date(c.get(TAG_EVENT_DATETIMECREATED)));
					Date d = new Date();
					try {
						 d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dateTimeCreated.replace("T", " "));
					} catch (ParseException pE) {
						 d = new Date();
					} finally {
						e.setDateTimeCreated(d);
					}
					String endDateTime=c.getString(TAG_EVENT_ENDDATETIME);
					try {
						 d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(endDateTime.replace("T", " "));
					} catch (ParseException pE) {
						 d = new Date();
					} finally {
						e.setEndDateTime(d);
					}
					String startDateTime=c.getString(TAG_EVENT_STARTDATETIME);
					try {
						 d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(startDateTime.replace("T", " "));
					} catch (ParseException pE) {
						 d = new Date();
					} finally {
						e.setStartDateTime(d);
					}
					
//					User u= new User();
//					JSONObject c2=(JSONObject) c.get(TAG_USER);
//					u.setUserID(c2.getInt(TAG_USER_USERID));
//					u.setName(c2.getString(TAG_USER_NAME));
//					u.setProfileImageURL(c2.getString(TAG_USER_PROFILEIMAGEURL));
//					u.setBiography(c2.getString(TAG_USER_BIOGRAPHY));
//					u.setContact(c2.getString(TAG_USER_CONTACT));
//					u.setCredibility(c2.getString(TAG_USER_CREDIBILITY));
//					u.setEmail(c2.getString(TAG_USER_EMAIL));			
//					u.setAddress(c2.getString(TAG_USER_ADDRESS));
//					u.setGalleryID(c2.getInt(TAG_USER_GALLERYID));
//					u.setPrivicy(c2.getString(TAG_USER_PRIVICY));
//					u.setSex(c2.getString(TAG_USER_SEX).charAt(0));
//					u.setX(c2.getDouble(TAG_USER_X));
//					u.setY(c2.getDouble(TAG_USER_Y));
//					e.setUser(u);
					

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
            
            StaticObjects staticObject= new StaticObjects();
            
            //check status if all green to extract
			StaticObjects.setResponseStatus((Integer) json.get(TAG_STATUS));
			StaticObjects.setResponseMessage(json.getString(TAG_MESSAGE));
			
			//JSONArray errors=json.getJSONArray(TAG_ERRORS);
			
			ArrayList<Location>locations= new ArrayList<Location>();
			if(StaticObjects.getResponseStatus()==0)
			{
				JSONArray RawData= json.getJSONArray(TAG_DATA);
				Log.i("LOCATION ",RawData.toString() );
				for(int i=0;i<RawData.length();i++)
				{
					JSONObject c=RawData.getJSONObject(i);
					
					Location e= new Location();
					e.setLocationID(c.getInt(TAG_LOCATION_LOCATIONID));
					e.setTitle(c.getString(TAG_LOCATION_TITLE));
					e.setDescription(c.getString(TAG_LOCATION_DESCRIPTION));
					//e.setGalleryID(c.getInt(TAG_LOCATION_GALLERYID));
					e.setAddress(c.getString(TAG_LOCATION_ADDRESS));
					e.setType(c.getString(TAG_LOCATION_TYPE));
					e.setX(c.getDouble(TAG_LOCATION_X));
					e.setY(c.getDouble(TAG_LOCATION_Y));
					String date=c.getString(TAG_EVENT_DATETIMECREATED);
					//e.setDateTimeCreated(new Date(c.get(TAG_EVENT_DATETIMECREATED)));
					
					
//					User u= new User();
//					JSONObject c2=(JSONObject) c.get(TAG_USER);
//					u.setUserID(c2.getInt(TAG_USER_USERID));
//					u.setName(c2.getString(TAG_USER_NAME));
//					u.setProfileImageURL(c2.getString(TAG_USER_PROFILEIMAGEURL));
//					u.setBiography(c2.getString(TAG_USER_BIOGRAPHY));
//					u.setContact(c2.getString(TAG_USER_CONTACT));
//					u.setCredibility(c2.getString(TAG_USER_CREDIBILITY));
//					u.setEmail(c2.getString(TAG_USER_EMAIL));			
//					u.setAddress(c2.getString(TAG_USER_ADDRESS));
//	
//					//u.setGalleryID(c2.getInt(TAG_USER_GALLERYID));
//					u.setPrivicy(c2.getString(TAG_USER_PRIVICY));
//					u.setSex(c2.getString(TAG_USER_SEX).charAt(0));
//					u.setX(c2.getDouble(TAG_USER_X));
//					u.setY(c2.getDouble(TAG_USER_Y));
//					e.setUser(u);
					

					locations.add(e);
					Log.i("LOCATIONwwww ",e.getDescription() );
				}

				StaticObjects staticObjects= new StaticObjects();
				StaticObjects.setAnslysisLocation(locations);
				StaticObjects.setLocations(locations);
				Log.i("LOCATIONdsa ",StaticObjects.getLocations().get(0).getDescription() );
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
			
			//JSONArray errors=json.getJSONArray(TAG_ERRORS);
			
			ArrayList<Stray>strays= new ArrayList<Stray>();
			if(StaticObjects.getResponseStatus()==0)
			{
				JSONArray RawData= json.getJSONArray(TAG_DATA);
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
					e.setX(c.getDouble(TAG_STRAY_X));
					e.setY(c.getDouble(TAG_STRAY_Y));
					e.setUserID(c.getInt(TAG_STRAY_USERID));
					//String date=c.getString(TAG_EVENT_DATETIMECREATED);
					//e.setDateTimeCreated(new Date(c.get(TAG_EVENT_DATETIMECREATED)));
					
					

					strays.add(e);
				}
				if(StaticObjects.getAnalysisStray()!=null)
				{
					Log.i("ERROR3", "status==3");
					for(int i=0;i<strays.size();i++)
						StaticObjects.getAnalysisStray().add(strays.get(i));
				}
				else
				{
					Log.i("ERROR3", "status==4");
					StaticObjects.setAnalysisStray(strays);
				}
				
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
		HttpEntity entity = data.getEntity();
        
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            
            JSONObject json = null;
            json = new JSONObject(result);
	
            //check status if all green to extract
            StaticObjects.setResponseStatus((Integer) json.get(TAG_STATUS));
			StaticObjects.setResponseMessage(json.getString(TAG_MESSAGE));
			
			//JSONArray errors=json.getJSONArray(TAG_ERRORS);
			
			ArrayList<Lost>lost= new ArrayList<Lost>();
			if(StaticObjects.getResponseStatus()==0)
			{
				
				JSONArray RawData= json.getJSONArray(TAG_DATA);
				Log.i("LOST ",RawData.toString() );
				
				for(int i=0;i<RawData.length();i++)
				{
					
					JSONObject c=RawData.getJSONObject(i); //Log.i("c ",c.toString() );
					Lost l= new Lost();
					l.setPetID(c.getInt(TAG_PET_PETID));
					l.setLostID(c.getInt(TAG_LOST_LOSTID));
					String dateLastSeen = c.getString(TAG_LOST_DATETIMESEEN);
					//l.setDateTimeSeen();
					l.setAddress(c.getString(TAG_LOST_ADDRESS));
					l.setDescription(c.getString(TAG_LOST_DESCRIPTION));
					l.setX(c.getDouble(TAG_LOST_X));
					l.setY(c.getDouble(TAG_LOST_Y));
					l.setFound(c.getString(TAG_LOST_FOUND).charAt(0));
					l.setReward(c.getString(TAG_LOST_REWARD));
					String dateLastCreated = c.getString(TAG_LOST_DATETIMECREATED);
					//l.setDateTimeCreated();
					
					Date d = new Date();
					try {
						 d = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(dateLastSeen.replace("T", " "));
					} catch (ParseException e) {
						 d = new Date();
					} finally {
						l.setDateTimeSeen(d);
					}
					
					lost.add(l); 
				} 
				
				StaticObjects.setLosts(lost);
				
				if(StaticObjects.getAndlysisLost()!=null)
				{
					for(int i=0;i<lost.size();i++)
						StaticObjects.getAndlysisLost().add(lost.get(i));
				}
				else
				{
					StaticObjects.setAndlysisLost(lost);
				}
				StaticObjects.setMapLost(lost);
			}
			else
			{
				Log.i("ERROR", "status==1");
				Log.i("Message",StaticObjects.getResponseMessage());
			}
            instream.close();
        }
	}
	
	//THiS METHOD EXTRACTS THE REVIEW DATA
	public void ExtractReviewRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		// TODO Auto-generated method stub
		HttpEntity entity = data.getEntity();
        
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            
            JSONObject json = null;
            json = new JSONObject(result);
	
            //check status if all green to extract
            StaticObjects.setResponseStatus((Integer) json.get(TAG_STATUS));
			StaticObjects.setResponseMessage(json.getString(TAG_MESSAGE));
			
			//JSONArray errors=json.getJSONArray(TAG_ERRORS);
			
			ArrayList<Review>review= new ArrayList<Review>();
			if(StaticObjects.getResponseStatus()==0)
			{
				
				JSONArray RawData= json.getJSONArray(TAG_DATA);
				Log.i("Review ",RawData.toString() );
				
				for(int i=0;i<RawData.length();i++){
					
					JSONObject c2=RawData.getJSONObject(i); Log.i("c ",c2.toString() );
					
					
					Review r= new Review();
					
					
					r.setReviewID(c2.getInt("ReviewID"));
					r.setDescription(c2.getString("Description"));
					r.setLocationID(c2.getInt("LocationID"));
					r.setReviewID(c2.getInt("ReviewID"));
					r.setDislikes(c2.getString("Dislikes"));
					r.setUserID(c2.getInt("UserID"));
					r.setLikes(c2.getString("Likes"));
					r.setTitle(c2.getString("Title"));
					//r.setDislikes(c2.getInt("Dislikes"));
					
					
					
					review.add(r); 
					
					//Log.i("pet "+i,c.toString() );
				} 
				
				StaticObjects.setReviews(review);
				//StaticObjects.setPets(pet);

			}
			else
			{
				Log.i("ERROR", "status==1");
				Log.i("Message",StaticObjects.getResponseMessage());
			}
            instream.close();
        }
	}
	
	//THIS METHODS EXTRACTS THE IMAGE URL AFTER UPLOADING THE BASE64 DATA
	public void ExtractUploadImage(HttpResponse data) throws IllegalStateException, IOException, JSONException
	{
		HttpEntity entity = data.getEntity();
        
        if (entity != null) {
            InputStream instream = entity.getContent();
            String result= convertStreamToString(instream);
            String string = result;
            String[] parts = string.split("</Data>");
            String part1 = parts[0]; // 004
           Log.i("JSON", part1.length()+"");
            String url=part1.replace("<ImageModel xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://schemas.datacontract.org/2004/07/BarterTradingWebServices.Model\"><Data>", "");
            
            Log.i("JSON", url);
            if(StaticObjects.getSelectedLocation().getGallery()!=null)
            {
            	Image im= new Image();
            	im.setImageURL(url.replace(" ", "%2520"));
            	ArrayList<Image> images = new ArrayList<Image>();
            	images.add(im);
            	StaticObjects.getSelectedLocation().getGallery().setImages(images);
            	
            }
            instream.close();
        }
	}

		public void ExtractPetRequest(HttpResponse data) throws IllegalStateException, IOException, JSONException
		{
			// TODO Auto-generated method stub
			HttpEntity entity = data.getEntity();
	        
	        if (entity != null) {
	            InputStream instream = entity.getContent();
	            String result= convertStreamToString(instream);
	            
	            JSONObject json = null;
	            json = new JSONObject(result);
		
	            //check status if all green to extract
	            StaticObjects.setResponseStatus((Integer) json.get(TAG_STATUS));
				StaticObjects.setResponseMessage(json.getString(TAG_MESSAGE));
				
				//JSONArray errors=json.getJSONArray(TAG_ERRORS);
				
				ArrayList<Pet>pet= new ArrayList<Pet>();
				if(StaticObjects.getResponseStatus()==0)
				{
					
					JSONArray RawData= json.getJSONArray(TAG_DATA);
					Log.i("PET ",RawData.toString() );
					
					for(int i=0;i<RawData.length();i++)
					{
						
						JSONObject c2=RawData.getJSONObject(i); Log.i("c ",c2.toString() );
						
						
						Pet p= new Pet();
						//JSONObject c2=(JSONObject) c.get(TAG_PETs);
						//JSONObject c2=(JSONObject) c.get("PET");
												
						p.setPetID(c2.getInt(TAG_PET_PETID));
						p.setName(c2.getString(TAG_PET_NAME)); 
						p.setBreed(c2.getString(TAG_PET_BREED));
						p.setSex(c2.getString(TAG_PET_SEX).charAt(0));
						p.setType(c2.getString(TAG_PET_TYPE));
						p.setBiography(c2.getString(TAG_PET_BIOGRAPHY));
						p.setAge(c2.getString(TAG_PET_AGE));
						p.setUserID(c2.getInt(TAG_PET_USERID));
						//p.setGalleryID(c2.getInt(TAG_PET_GALLERYID));
						p.setProfileImageURL(c2.getString(TAG_PET_PROFILEIMAGEURL));
						String petDateLastCreated = c2.getString(TAG_PET_DATETIMECREATED);
						//p.setDateTimeCreated(petDateLastCreated);		
						
						pet.add(p); 
						
						//Log.i("pet "+i,c.toString() );
					} 
					
					StaticObjects.setPets(pet);

				}
				else
				{
					Log.i("ERROR", "status==1");
					Log.i("Message",StaticObjects.getResponseMessage());
				}
	            instream.close();
	        }
		}

		public void ExtractOneMapSearchRequest(HttpResponse data)throws IllegalStateException, IOException, JSONException
		{
			HttpEntity entity = data.getEntity();
	        
	        if (entity != null) {
	            InputStream instream = entity.getContent();
	            String result= convertStreamToString(instream);
	            
	            JSONObject json = null;
	            json = new JSONObject(result);
	            Log.i("Search result",result);	        
	            
	            JSONArray RawData= json.getJSONArray("SearchResults");
				
				ArrayList<Address> addresses= new ArrayList<Address>();
				for(int i=1;i<RawData.length();i++)
				{
					JSONObject c2=RawData.getJSONObject(i); 
					
					Address p= new Address();
					p.setAddress(c2.getString("SEARCHVAL"));
					p.setX(c2.getDouble("X"));
					p.setY(c2.getDouble("Y"));
					Log.i("c ",p.getAddress().toString() );
					addresses.add(p); 
				} 
				StaticObjects.setAddress_results(addresses);
	            instream.close();
	        }
		}
		
		public void ExtractUpdateLocation(HttpResponse data)throws IllegalStateException, IOException, JSONException {
			
			HttpEntity entity = data.getEntity();
	        
	        if (entity != null) {
	            InputStream instream = entity.getContent();
	            String result= convertStreamToString(instream);
	            
	            JSONObject json = null;
	            json = new JSONObject(result);
	            Log.i("Search result",result);	        
	            
	           instream.close();
	        }
		}
		
		
		public void ExtractPolyLineRequest(HttpResponse data)throws IllegalStateException, IOException, JSONException {
			
			HttpEntity entity = data.getEntity();
	        
	        if (entity != null) {
	            InputStream instream = entity.getContent();
	            String result= convertStreamToString(instream);
	            
	            JSONObject json = null;
	            json = new JSONObject(result);
	            //Log.i("Search result",result);	        
	            
	            JSONArray routeArray = json.getJSONArray("routes");
	            JSONObject routes = routeArray.getJSONObject(0);
	            JSONObject overviewPolylines = routes.getJSONObject("overview_polyline");
	            String encodedString = overviewPolylines.getString("points");
				
				
				StaticObjects.setPolyline(encodedString);
	            instream.close();
	        }
		}
}
