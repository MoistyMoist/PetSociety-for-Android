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
		adapter.add(new SampleItem("Pins Filter", R.drawable.ic_map_pin, R.drawable.pin_red)); 
		adapter.add(new SampleItem("Lost Pets", R.drawable.ic_check, R.drawable.pin_red));
		adapter.add(new SampleItem("Stray Animals", R.drawable.ic_check, R.drawable.pin_grey));
		adapter.add(new SampleItem("Events", R.drawable.ic_check, R.drawable.pin_blue));
		adapter.add(new SampleItem("Places", R.drawable.ic_check, R.drawable.pin_green));
		setListAdapter(adapter);
	}
	
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
	};

	public class SampleItem {
		public String tag;
		public int iconRes;
		public int pinIcon;
		public SampleItem(String tag, int iconRes, int pinIcon) {
			this.tag = tag; 
			this.iconRes = iconRes;
			this.pinIcon = pinIcon;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);
			ImageView pinIcon = (ImageView) convertView.findViewById(R.id.row_pin_icon);	
			pinIcon.setImageResource(getItem(position).pinIcon);
			if (getItem(position).iconRes == R.drawable.ic_map_pin) {
				convertView.setBackgroundColor(Color.GRAY);  
				title.setTextColor(Color.WHITE);
				title.setTypeface(null, Typeface.BOLD);
				pinIcon.setImageResource(android.R.color.transparent);
			}
			else{
				convertView.setBackgroundColor(Color.WHITE);  
				title.setTypeface(null, Typeface.NORMAL);
			}

			return convertView;
		}

	}

}
