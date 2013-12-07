package com.petsociety.main;

import com.example.petsociety.EventActivity;
import com.example.petsociety.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.petsociety.account.AccSettingActivity;
import com.petsociety.main.analysis.AnalysisActivity;
import com.petsociety.main.lost.LostActivity;
import com.petsociety.main.nearby.NearbyActivity;
import com.petsociety.main.profile.ProfileActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class LeftListFragment extends ListFragment {
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		
		SampleAdapter adapter = new SampleAdapter(getActivity());
		//0-4
		adapter.add(new SampleItem("1st Title", R.drawable.ic_left_menu_title)); //int 0 for title, custom set	
		adapter.add(new SampleItem("Search", android.R.drawable.ic_menu_search));
		adapter.add(new SampleItem("PetSG Home",android.R.drawable.ic_menu_search));
		adapter.add(new SampleItem("Profile", android.R.drawable.ic_menu_myplaces));
		adapter.add(new SampleItem("Events", android.R.drawable.ic_menu_my_calendar));
		//5-9
		adapter.add(new SampleItem("2nd Title", R.drawable.ic_left_menu_title)); //int 0 for title, custom set		
		adapter.add(new SampleItem("Lost Pets", android.R.drawable.ic_menu_search));
		adapter.add(new SampleItem("Strays Pets", android.R.drawable.ic_menu_search));
		adapter.add(new SampleItem("Analysis", android.R.drawable.ic_menu_mapmode));
		adapter.add(new SampleItem("Nearby", android.R.drawable.ic_menu_directions));
		//10-12
		adapter.add(new SampleItem("Settings", R.drawable.ic_left_menu_title)); //int 0 for title, custom set
		adapter.add(new SampleItem("Account", android.R.drawable.ic_menu_preferences));
		adapter.add(new SampleItem("Application", android.R.drawable.ic_menu_preferences));
		adapter.add(new SampleItem("Log Out", android.R.drawable.ic_menu_preferences));
		
		setListAdapter(adapter);
		
		//getActivity().findViewById((int) getListView().getItemIdAtPosition(2)).setBackgroundColor(Color.RED);
	}

	public void onListItemClick(ListView lv, View v, int position, long id) {
		
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		switch (position) {
		case 1: //Search
			break;
			
		case 2: //Home
			intent.setClass(getActivity(), MainActivity.class);
			intent.putExtra("var", "home");
			startActivity(intent);
			break;

		case 3: //Profile
			intent.setClass(getActivity(), ProfileActivity.class);
			startActivity(intent);
			break;

		case 4: //Events
			intent.setClass(getActivity(), EventActivity.class);
			startActivity(intent);
			break;

		case 6: //Lost and Found
			intent.setClass(getActivity(), LostActivity.class);
			startActivity(intent);
			break;

		//case7 Stray
		case 8: //Analysis
			intent.setClass(getActivity(), AnalysisActivity.class);
			startActivity(intent);
			break;
			
		case 9: //Nearby
			intent.setClass(getActivity(), NearbyActivity.class);
			startActivity(intent);
			break;
			
		case 11: //Account Settings
			intent.setClass(getActivity(), AccSettingActivity.class);
			startActivity(intent);
			break;
			
		case 12: //Settings
			intent.setClass(getActivity(), SettingActivity.class);
			startActivity(intent);
			break;
		}
		((SlidingFragmentActivity) getActivity()).getSlidingMenu().toggle();
	};
	
	private class SampleItem {
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
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);
			title.setTextColor(Color.BLUE);
			
			if (getItem(position).iconRes == R.drawable.ic_left_menu_title) {
				convertView.setBackgroundColor(Color.GRAY);  
				title.setTextColor(Color.WHITE);
			}
			else{
				//convertView.setBackgroundColor(Color.TRANSPARENT); 
				convertView.setBackgroundColor(Color.WHITE); 
			}

			return convertView;
		}
		
		

	}
}
