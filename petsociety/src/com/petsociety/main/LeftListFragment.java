package com.petsociety.main;

import com.example.petsociety.EventActivity;
import com.example.petsociety.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.petsociety.account.AccSettingActivity;
import com.petsociety.account.LoginActivity;
import com.petsociety.main.analysis.AnalysisActivity;
import com.petsociety.main.lost.LostActivity;
import com.petsociety.main.nearby.NearbyActivity;
import com.petsociety.main.profile.EventList;
import com.petsociety.main.profile.ProfileActivity;
import com.petsociety.utils.StaticObjects;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
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
	
	public static final String PREFS_LOGIN = "LOGIN";
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		
		SampleAdapter adapter = new SampleAdapter(getActivity());
		//0-1
		adapter.add(new SampleItem("Search", android.R.drawable.ic_menu_search));
		adapter.add(new SampleItem("PetSG Home",android.R.drawable.ic_menu_search));
		//2-4
		adapter.add(new SampleItem("Account", R.drawable.ic_main_account,true)); //title
		adapter.add(new SampleItem("Profile", android.R.drawable.ic_menu_myplaces));
		adapter.add(new SampleItem("Events", android.R.drawable.ic_menu_my_calendar));
		//5-9
		adapter.add(new SampleItem("Find Animals", R.drawable.ic_main_find_animals,true)); //title
		adapter.add(new SampleItem("Lost Pets", android.R.drawable.ic_menu_search));
		adapter.add(new SampleItem("Strays Pets", android.R.drawable.ic_menu_search));
		adapter.add(new SampleItem("Analysis", android.R.drawable.ic_menu_mapmode));
		adapter.add(new SampleItem("Nearby", android.R.drawable.ic_menu_directions));
		//10-13
		adapter.add(new SampleItem("Settings", R.drawable.ic_main_settings,true)); //title
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
		case 0: //Search
			break;
			
		case 1: //Home
			intent.setClass(getActivity(), MainActivity.class);
			intent.putExtra("pin", "home");
			startActivity(intent);
			break;

		case 3: //Profile
			intent.setClass(getActivity(), ProfileActivity.class);
			startActivity(intent);
			break;

		case 4: //Events
			intent.setClass(getActivity(), EventList.class);
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
		case 13: //Logout
			SharedPreferences settings = getActivity().getSharedPreferences(PREFS_LOGIN, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString("password", "");
			editor.commit();
			
			StaticObjects.setStaticEmpty();
			Intent logIntent = new Intent();
			logIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			logIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			logIntent.setClass(getActivity(), LoginActivity.class);
			startActivity(logIntent);
			break;
			
		}
		((SlidingFragmentActivity) getActivity()).getSlidingMenu().toggle();
	};
	
	private class SampleItem {
		public String tag;
		public int iconRes;
		public boolean title;
		public SampleItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
			this.title = false;
		}
		
		public SampleItem(String tag, int iconRes, boolean title) {
			this.tag = tag; 
			this.iconRes = iconRes;
			this.title = title;
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
			
			if (getItem(position).title==true) {
				convertView.setBackgroundColor(Color.GRAY);  
				title.setTextColor(Color.WHITE);
				//Typeface myTypeface = Typeface.createFromAsset(getActivity().getAssets(), "text/LuckiestGuy.ttf");
				title.setTypeface(null, Typeface.BOLD);
			}
			else{
				//convertView.setBackgroundColor(Color.TRANSPARENT); 
				convertView.setBackgroundColor(Color.WHITE); 
				title.setTypeface(null, Typeface.NORMAL);
			}

			return convertView;
		}
		
		

	}
}
