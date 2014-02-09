package com.petsociety.main.profile;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;
import com.petsociety.models.Pet;
import com.petsociety.utils.StaticObjects;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

public class PetProfile extends Activity {

	TextView name, bio, sex,type,age;
	Pet onePet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pet_profile);
		
		Bundle extras = getIntent().getExtras();
		int petID = extras.getInt("pet");
		
		
		name = (TextView) findViewById(R.id.tv_pp_name);
		bio = (TextView) findViewById(R.id.tv_pp_bio);
		sex = (TextView) findViewById(R.id.tv_pp_gender);
		type = (TextView) findViewById(R.id.tv_pp_type);
		age = (TextView) findViewById(R.id.tv_pp_age);
		
		for (int i=0; i<StaticObjects.getPets().size(); i++){
			if(petID == StaticObjects.getPets().get(i).getPetID())
			{
				onePet = StaticObjects.getPets().get(i);
			}
		}	
		
		name.setText(onePet.getName());
		bio.setText(onePet.getBiography());
		sex.setText(Character.toString(onePet.getSex()));
		type.setText(onePet.getType());
		age.setText(onePet.getAge());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pet_profile, menu);
		return true;
	}

}
