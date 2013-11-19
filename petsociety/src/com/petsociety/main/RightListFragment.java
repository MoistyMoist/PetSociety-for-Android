package com.petsociety.main;

import com.example.petsociety.R;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.petsociety.main.analysis.AnalysisActivity;
import com.petsociety.main.lost.LostActivity;
import com.petsociety.main.nearby.NearbyActivity;
import com.petsociety.main.profile.ProfileActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
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
		//for (int i = 0; i < 20; i++) {
		//	adapter.add(new SampleItem("Sample List"+i, android.R.drawable.ic_menu_search));
		//}
		adapter.add(new SampleItem("Lost Pets", android.R.drawable.ic_menu_search));
		adapter.add(new SampleItem("Found", android.R.drawable.ic_menu_search));
		adapter.add(new SampleItem("Friends", android.R.drawable.ic_menu_search));
		adapter.add(new SampleItem("Events", android.R.drawable.ic_menu_my_calendar));
		adapter.add(new SampleItem("Places", android.R.drawable.ic_menu_mapmode));
		setListAdapter(adapter);
	}
	
	public void onListItemClick(ListView lv, View v, int position, long id) {
		//Intent intent = new Intent();
		//intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		switch (position) {
		case 0: //Lost Pets			
			Intent intent = new Intent(getActivity().getBaseContext(), MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.putExtra("var", "Lost Pet Marker");
            getActivity().startActivity(intent);
			
			break;

		}
		((SlidingFragmentActivity) getActivity()).getSlidingMenu().toggle();
	};


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
