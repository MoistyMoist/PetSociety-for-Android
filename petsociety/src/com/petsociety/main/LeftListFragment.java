package com.petsociety.main;

import com.example.petsociety.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.petsociety.main.analysis.AnalysisActivity;
import com.petsociety.main.lost.LostActivity;
import com.petsociety.main.nearby.NearbyActivity;
import com.petsociety.main.profile.ProfileActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class LeftListFragment extends ListFragment {

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		SampleAdapter adapter = new SampleAdapter(getActivity());
		
		//for (int i = 0; i < 20; i++) {
		//	adapter.add(new SampleItem("Sample List"+i, android.R.drawable.ic_menu_search));
		//}
		adapter.add(new SampleItem("Search", android.R.drawable.ic_menu_search));
		adapter.add(new SampleItem("Main",android.R.drawable.ic_menu_search));
		adapter.add(new SampleItem("Profile", android.R.drawable.ic_menu_myplaces));
		adapter.add(new SampleItem("Events", android.R.drawable.ic_menu_my_calendar));
		adapter.add(new SampleItem("My Pets", android.R.drawable.ic_menu_compass));
		adapter.add(new SampleItem("Lost and Found", android.R.drawable.ic_menu_search));
		adapter.add(new SampleItem("Analysis", android.R.drawable.ic_menu_mapmode));
		adapter.add(new SampleItem("Settings", android.R.drawable.ic_menu_preferences));
		adapter.add(new SampleItem("Nearby", android.R.drawable.ic_menu_directions));
		setListAdapter(adapter);
		
		//getActivity().findViewById((int) getListView().getItemIdAtPosition(2)).setBackgroundColor(Color.RED);
	}

	public void onListItemClick(ListView lv, View v, int position, long id) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		switch (position) {
		case 0: //Search
			//intent.setClass(getActivity(), MainActivity.class);
			//startActivity(intent);
			break;
			
		case 1: //Profile
			intent.setClass(getActivity(), ProfileActivity.class);
			startActivity(intent);
			break;

		case 2: //Events
			intent.setClass(getActivity(), MainActivity.class);
			startActivity(intent);
			break;

		case 3: //My Pets
			intent.setClass(getActivity(), MainActivity.class);
			startActivity(intent);
			break;

		case 4: //Lost and Found
			intent.setClass(getActivity(), LostActivity.class);
			startActivity(intent);
			/*
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.fragment_container, newFragment);
			transaction.addToBackStack(null);
			*/
			break;

		case 5: //Analysis
			intent.setClass(getActivity(), AnalysisActivity.class);
			startActivity(intent);
			break;

		case 6: //Settings
			intent.setClass(getActivity(), SettingActivity.class);
			startActivity(intent);
			break;
			
		case 7: //Nearby
			intent.setClass(getActivity(), NearbyActivity.class);
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
			title.setTextColor(999999);
			return convertView;
		}

	}


}
