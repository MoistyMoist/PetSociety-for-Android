package com.petsociety.main;

import com.example.petsociety.R;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.petsociety.main.analysis.AnalysisActivity;
import com.petsociety.main.lost.LostActivity;
import com.petsociety.main.nearby.NearbyActivity;
import com.petsociety.main.profile.ProfileActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RightListFragment extends ListFragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		SampleAdapter adapter = new SampleAdapter(getActivity());
		adapter.add(new SampleItem("Legend", R.drawable.ic_action_star)); 
		adapter.add(new SampleItem("Lost Pets", R.drawable.badge_lostdog));
		adapter.add(new SampleItem("Events", R.drawable.badge_event));
		adapter.add(new SampleItem("Pet Store", R.drawable.icon_petstore));
		adapter.add(new SampleItem("Vets", R.drawable.icon_vet));
		adapter.add(new SampleItem("Accidents", R.drawable.icon_warning));
		setListAdapter(adapter);
	}
	
	
	/*
	public void onListItemClick(ListView lv, View v, int position, long id) {
		Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		switch (position) {
		case 1: //Lost Pets					
            intent.putExtra("pin", "lostpet");
            getActivity().startActivity(intent);
			break;
		case 2: //Found			
            intent.putExtra("pin", "stray");
            getActivity().startActivity(intent);
			break;
		case 3: //Event			
	        intent.putExtra("pin", "event");
	        getActivity().startActivity(intent);
			break;
		case 4: //Location			
		    intent.putExtra("pin", "location");
		    getActivity().startActivity(intent);
			break;
		}
		ImageView icon = (ImageView) v.findViewById(R.id.row_icon);
		if (icon.getDrawable().getConstantState().equals(getResources().getDrawable(R.drawable.ic_check).getConstantState()))
		{icon.setImageResource(android.R.color.transparent);}
		else {icon.setImageResource(R.drawable.ic_check);}
	};*/

	public class SampleItem {
		public String tag;
		public int iconRes;
		public SampleItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.right_row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);
			
			title.setText(getItem(position).tag); //title.setText(title.getText().toString().toUpperCase());
			title.setTextColor(Color.parseColor("#333333"));
			title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/MontserratAlternates-Regular.ttf"));
			
			if(position==1){
				icon.setBackgroundColor(Color.parseColor("#88FF0000")); 
			} else if(position==2){
				icon.setBackgroundColor(Color.parseColor("#880000FF")); //icon.setBackgroundColor(Color.BLUE);
			} else if(position==3){
				icon.setBackgroundColor(Color.parseColor("#8800FF00")); //icon.setBackgroundColor(Color.GREEN);
			} else if(position==4){
				icon.setBackgroundColor(Color.parseColor("#8800FF00")); //icon.setBackgroundColor(Color.GREEN);
			} else if(position==5){
				icon.setBackgroundColor(Color.parseColor("#8800FF00")); //icon.setBackgroundColor(Color.GREEN);
			} 
			
			
			if (position == 0) {
				convertView.setBackgroundColor(Color.BLACK);  
				title.setTextColor(Color.WHITE);
				//title.setTypeface(null, Typeface.BOLD);
			}
			else{
				convertView.setBackgroundColor(Color.WHITE);  
				//title.setTypeface(null, Typeface.NORMAL);
			}
			
			return convertView;
		}

	}

}
