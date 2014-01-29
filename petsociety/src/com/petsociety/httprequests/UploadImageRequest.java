package com.petsociety.httprequests;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.json.JSONException;
import org.json.JSONObject;

import com.petsociety.utils.JSONExtractor;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;


public class UploadImageRequest implements Runnable {

	String base64="";	
	
	
	public UploadImageRequest(String base64)
	{
		this.base64=base64;
	}
	
	
	@Override
	public void run() {

		HttpClient client = new DefaultHttpClient();  
		HttpPost post = new HttpPost("http://bartertrading.cloudapp.net/api/UploadImage");   
		String SOAPRequestXML;
		StringEntity se = null;
		try
		{
			SOAPRequestXML="<string xmlns=\"http://schemas.microsoft.com/2003/10/Serialization/\">"+this.base64+"</string>";
			try {
				se= new StringEntity(SOAPRequestXML,HTTP.UTF_8);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			se.setContentType("text/xml");
			post.setHeader("Content-Type", "application/xml;charset=UTF-8");
			post.setEntity(se);
			Log.i("UPLOAD IMAGE Request",se.getContentLength()+"");
			HttpResponse response = null;
			try {
				response = client.execute(post);
			} catch (ClientProtocolException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Log.i("UPLOAD IMAGE RESPONSE",response.getStatusLine().toString());
			//PASS THE RESPONSE TO THE EXTRACTOR
	        JSONExtractor paser= new JSONExtractor();
	        try {
				paser.ExtractUploadImage(response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
			} catch (IllegalStateException e) {
				Log.i("UPLOAD IMAGE error1",e.getMessage().toString());
				e.printStackTrace();
			} catch (JSONException e) {
				Log.i("UPLOAD IMAGE error2",e.getMessage().toString());
				e.printStackTrace();
			}
		
		
	}

}
