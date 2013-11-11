package com.petsociety.main;

import com.example.petsociety.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SampleListFragment extends ListFragment {

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
		adapter.add(new SampleItem("Profile", android.R.drawable.ic_menu_myplaces));
		adapter.add(new SampleItem("Events", android.R.drawable.ic_menu_my_calendar));
		adapter.add(new SampleItem("My Pets", android.R.drawable.ic_menu_compass));
		adapter.add(new SampleItem("Lost and Found", android.R.drawable.ic_menu_search));
		adapter.add(new SampleItem("Analysis", android.R.drawable.ic_menu_mapmode));
		adapter.add(new SampleItem("Settings", android.R.drawable.ic_menu_preferences));
		adapter.add(new SampleItem("Nearby", android.R.drawable.ic_menu_directions));
		setListAdapter(adapter);
	}

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

			return convertView;
		}

	}
}
