package com.petsociety.main;

import com.example.petsociety.R;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.petsociety.account.LoginActivity;
import com.petsociety.main.analysis.AnalysisActivity;
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
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class LeftListFragment extends ListFragment {
	
	public static final String PREFS_LOGIN = "LOGIN";
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.list, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		
		super.onActivityCreated(savedInstanceState);
		
		SampleAdapter adapter = new SampleAdapter(getActivity());
		//0-7
		adapter.add(new SampleItem("Search", R.drawable.ic_left_search));
		adapter.add(new SampleItem("PetSG Home",R.drawable.ic_left_home));
		adapter.add(new SampleItem("Profile", R.drawable.ic_left_profile));
		adapter.add(new SampleItem("Events", R.drawable.ic_left_event));
		adapter.add(new SampleItem("Nearby Places", R.drawable.ic_left_nearby));
		adapter.add(new SampleItem("Analysis", R.drawable.ic_left_analysis));
		adapter.add(new SampleItem("Settings", R.drawable.ic_left_setting));
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

		case 2: //Profile
			intent.setClass(getActivity(), ProfileActivity.class);
			startActivity(intent);
			break;

		case 3: //Events
			intent.setClass(getActivity(), EventList.class);
			startActivity(intent);
			break;

		case 4: //Nearby
			intent.setClass(getActivity(), NearbyActivity.class);
			startActivity(intent);
			break;

		case 5: //Analysis
			intent.setClass(getActivity(), AnalysisActivity.class);
			startActivity(intent);
			break;
			
		case 6: //Settings
			intent.setClass(getActivity(), SettingActivity.class);
			startActivity(intent);
			break;
			
		case 7: //Logout
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
			
			//convertView.setBackgroundColor(Color.parseColor("#80C8B5"));
			convertView.setBackgroundColor(Color.WHITE);
			
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag); //title.setText(title.getText().toString().toUpperCase());
			title.setTextColor(Color.parseColor("#333333"));
			//title.setTypeface(null, Typeface.NORMAL);
			title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/MontserratAlternates-Regular.ttf"));
			//title.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), "fonts/HappyMonkey-Regular.ttf")); 
			return convertView;
		}
		
		

	}
}
