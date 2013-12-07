package com.petsociety.main.analysis;

import java.util.ArrayList;

import com.example.petsociety.R;
import com.petsociety.models.Review;
import com.petsociety.utils.LoaderImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomReviewAdapter extends ArrayAdapter<Review>{
	
	 private final Context context;
    private final ArrayList<Review> reviewList;

    public CustomReviewAdapter(Context context, ArrayList<Review> reviewList) {

        super(context, R.layout.custom_analysis_listrow, reviewList);

        this.context = context;
        this.reviewList = reviewList;
        
        if(reviewList==null)
        {
       	 
        }
    }
    
    @SuppressLint("NewApi")
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater 
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.custom_analysis_listrow, parent, false);

        // 3. Get the two text view from the rowView
        TextView comments = (TextView) rowView.findViewById(R.id.comment);
        LoaderImageView profileImage=(LoaderImageView)rowView.findViewById(R.id.profileImage);
        TextView likes=(TextView)rowView.findViewById(R.id.like);
        TextView dislikes=(TextView)rowView.findViewById(R.id.dislike);
        TextView date=(TextView)rowView.findViewById(R.id.date);
        
        // 4. Set the text for textView 
        comments.setText(reviewList.get(position).getDescription());
        profileImage.setImageDrawable(reviewList.get(position).getUser().getProfileImageURL());
        date.setText(reviewList.get(position).getDateTimeCreated().toString());
        if(reviewList.get(position).getLikes().isEmpty()||reviewList.get(position).getLikes()==null||reviewList.get(position).getLikes().equals(""))
        {
        	likes.setText("LIKE");
        }
        if(!reviewList.get(position).getLikes().isEmpty()&&reviewList.get(position).getLikes()!=null||!reviewList.get(position).getLikes().equals(""))
        {
        	likes.setText(reviewList.get(position).getLikes()+"LIKES");
        }
        if(reviewList.get(position).getDislikes().isEmpty()||reviewList.get(position).getDislikes()==null||reviewList.get(position).getDislikes().equals(""))
        {
        	dislikes.setText("LIKE");
        }
        if(!reviewList.get(position).getDislikes().isEmpty()&&reviewList.get(position).getDislikes()!=null||!reviewList.get(position).getDislikes().equals(""))
        {
        	dislikes.setText(reviewList.get(position).getDislikes()+"DISLIKES");
        }
        
        // 5. retrn rowView
        return rowView;
    }
}
