package com.petsociety.main.analysis;

import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.petsociety.R;
import com.petsociety.models.Location;

public class CustomAccidentListAdapter extends ArrayAdapter<Location> {

	private final Context context;
    private final ArrayList<Location> accidentList;

    public CustomAccidentListAdapter(Context context, ArrayList<Location> accidentList) {

        super(context, R.layout.custome_analysis_accident_list, accidentList);

        this.context = context;
        this.accidentList = accidentList;
        
        if(accidentList==null)
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
        TextView name = (TextView) rowView.findViewById(R.id.row_accident_name);
        TextView tv_days = (TextView) convertView.findViewById(R.id.row_accident_days);
        TextView location = (TextView) convertView.findViewById(R.id.row_accident_description);
        
        // 4. Set the text for textView 

		name.setText(getItem(position).getTitle());
		
		Date today = new Date();
		long MILLISECS_PER_DAY = 24 * 60 * 60 * 1000;
		long days = (today.getTime() - getItem(position).getDateTImeCreated().getTime())/MILLISECS_PER_DAY; 
		//tv_days.setText(Long.toString(days));	
		
		//location.setText(getItem(position).getDescription());
		
		return convertView;
        
    }
}
