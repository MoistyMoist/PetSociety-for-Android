package com.petsociety.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;

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
	
	
	public void ConvertLoginRequest(HttpResponse data)
	{
		
	}
	
	public void ConvertRegisterRequest(HttpResponse data)
	{
		
	}
	
}
