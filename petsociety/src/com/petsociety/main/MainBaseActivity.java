package com.petsociety.main;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.example.petsociety.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MainBaseActivity extends SlidingFragmentActivity {

	private int mTitleRes;
	protected ListFragment mFrag;

	public MainBaseActivity(int titleRes) {
		mTitleRes = titleRes;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(mTitleRes);

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = new LeftListFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = (ListFragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
		}

		// customize the SlidingMenu
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		LeftListFragment leftFrag = new LeftListFragment();
		sm.setMenu(R.layout.menu_frame);
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.menu_frame, leftFrag).commit();	
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN); //getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
			
		case R.id.main_right:
			getSlidingMenu().showSecondaryMenu(true);
			return true;
		
		case R.id.main_camera:
		    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		    //fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
		    //intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

		    //startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		    startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main_base, menu);
		//getActionBar().setLogo(R.drawable.ic_drawer);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		return true;
	}
}
