package com.petsociety.main.nearby;

import java.util.ArrayList;
import java.util.List;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;
import com.petsociety.utils.LoaderImageView;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class GalleryImageAdapter  extends BaseAdapter  {
	
	  private Context mContext;
	  private ArrayList<String>imageUrl;


	    
	    public GalleryImageAdapter(Context context) 
	    {
	        mContext = context;
	    }
	    public GalleryImageAdapter(Context context, ArrayList<String> imageURLs) {
	         this.mContext = context;
	        this.imageUrl=imageURLs;
	        
	         
	         
	     }

	    public int getCount() {
	        return imageUrl.size();
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }


	    // Override this method according to your need
	    public View getView(int position, View convertView, ViewGroup parent) 
	    {
	    	// 1. Create inflater 
	         LayoutInflater inflater = (LayoutInflater) mContext
	             .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	         // 2. Get rowView from inflater
	         View rowView = inflater.inflate(R.layout.activity_gallery_image_adapter, parent, false);

	         // 3. Get the two text view from the rowView
	       
	         LoaderImageView imageView=(LoaderImageView)rowView.findViewById(R.id.imageView1);
	         
	         // 4. Set the text for textView 
	        
	         imageView.setImageDrawable(imageUrl.get(position).toString());
	         // 5. retrn rowView
	         return rowView;
	    }
	


}
