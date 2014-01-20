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
		adapter.add(new SampleItem("Search", R.drawable.ic_left_search));
		adapter.add(new SampleItem("PetSG Home",R.drawable.ic_left_home));
		//2-4
		adapter.add(new SampleItem("Account", R.drawable.ic_left_title,true)); //title
		adapter.add(new SampleItem("Profile", R.drawable.ic_left_profile));
		adapter.add(new SampleItem("Events", R.drawable.ic_left_event));
		//5-7
		adapter.add(new SampleItem("Utility Services", R.drawable.ic_left_title,true)); //title
		adapter.add(new SampleItem("Nearby Locations", R.drawable.ic_left_nearby));
		adapter.add(new SampleItem("Analysis", R.drawable.ic_left_analysis));
		//8-10
		adapter.add(new SampleItem("SEARCH", R.drawable.ic_left_title,true)); //title
		adapter.add(new SampleItem("Lost Pets", R.drawable.ic_left_stray));
		adapter.add(new SampleItem("Strays Pets", R.drawable.ic_left_stray));
		//11-14
		adapter.add(new SampleItem("Settings", R.drawable.ic_left_title,true)); //title
		adapter.add(new SampleItem("Account", R.drawable.ic_left_acc));
		adapter.add(new SampleItem("Application", R.drawable.ic_left_setting));
		adapter.add(new SampleItem("Log Out", R.drawable.ic_left_logout));
		
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

		case 6: //Nearby
			intent.setClass(getActivity(), NearbyActivity.class);
			startActivity(intent);
			break;

		case 7: //Analysis
			intent.setClass(getActivity(), AnalysisActivity.class);
			startActivity(intent);
			break;
			
		case 9: //Lost
			intent.setClass(getActivity(), LostActivity.class);
			startActivity(intent);
			break;
			
		case 10: //Stray
			break;
			
		case 12: //Settings
			intent.setClass(getActivity(), AccSettingActivity.class);
			startActivity(intent);
			break;
			
		case 13: //Settings
			intent.setClass(getActivity(), SettingActivity.class);
			startActivity(intent);
			break;
			
		case 14: //Logout
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
				convertView.setBackgroundColor(Color.parseColor("#0099CC")); 
				title.setText(title.getText().toString().toUpperCase());
				title.setTextColor(Color.WHITE);
				//Typeface myTypeface = Typeface.createFromAsset(getActivity().getAssets(), "text/LuckiestGuy.ttf");
				title.setTypeface(null, Typeface.BOLD);
			}
			else{
				//convertView.setBackgroundColor(Color.WHITE); 
				convertView.setBackgroundColor(Color.parseColor("#33B5E5"));
				title.setTextColor(Color.BLACK);
				title.setTypeface(null, Typeface.NORMAL);
			}

			return convertView;
		}
		
		

	}
}
