package com.petsociety.main.nearby;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import com.example.petsociety.R;
import com.example.petsociety.R.layout;
import com.example.petsociety.R.menu;
import com.petsociety.httprequests.RetrieveImagesByLocationIDRequest;
import com.petsociety.httprequests.UpdateLocationRequest;
import com.petsociety.httprequests.UploadImageRequest;
import com.petsociety.models.Image;
import com.petsociety.models.Location;

import com.petsociety.utils.StaticObjects;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShopPhotosActivity extends Activity {
	
	
	int RESULT_LOAD_IMAGE;
	Button uploadImageButton;
	ImageView imageView;
	 boolean imageAdded=false;
	 String base64;
	    Bitmap bitmap;
	    ProgressDialog progress;
	    StaticObjects staticObjects;
	    
	    ArrayList<String> imageURLs;
	    ListView gallery;
	    UploadImageRequest uploadImage;
	    
	    ImageView selectedImage;  
	     private Integer[] mImageIds = {
	                R.drawable.puppy,
	                R.drawable.girl,
	                R.drawable.pet,
	                R.drawable.profilepet1
	               
	        };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shop_photos);
		
		uploadImageButton=(Button)findViewById(R.id.uploadImageButton);
		imageView=(ImageView)findViewById(R.id.imageView1);
		
		uploadImageButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});
		
		RetrieveImagesByLocationIDRequest request = new RetrieveImagesByLocationIDRequest(StaticObjects.getSelectedLocation().getLocationID());;
		new BackgroundTask3().execute(request, null);
		
		
		 gallery = (ListView) findViewById(R.id.gallery1);
	        selectedImage=(ImageView)findViewById(R.id.imageView1);
	        
	        
	        
	        
		      imageURLs = new ArrayList<String>();
		     
	        
   		 

	         // clicklistener for Gallery
//	        gallery.setOnItemClickListener(new OnItemClickListener() {
//	            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//	                Toast.makeText(ShopPhotosActivity.this, "Your selected position = " + position, Toast.LENGTH_SHORT).show();
//	                // show the selected Image
//	                selectedImage.setImageResource(mImageIds[position]);
//	            }
//	        });
//		
		
		
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
        	    && null != data) {
        	   Uri selectedImage = data.getData();
        	   String[] filePathColumn = { MediaStore.Images.Media.DATA };

        	   Cursor cursor = getContentResolver().query(selectedImage,
        	     filePathColumn, null, null, null);
        	   cursor.moveToFirst();

        	   int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        	   String picturePath = cursor.getString(columnIndex);
        	   Toast.makeText(getBaseContext(), picturePath.toString(), Toast.LENGTH_SHORT).show();
        	   cursor.close();

        	 decodeFile(picturePath);
        	  }
    }
	
	
    
	public void decodeFile(String filePath) {
		 // Decode image size
		 BitmapFactory.Options o = new BitmapFactory.Options();
		 o.inJustDecodeBounds = true;
		 BitmapFactory.decodeFile(filePath, o);

		 // The new size we want to scale to
		 final int REQUIRED_SIZE = 2000;

		 // Find the correct scale value. It should be the power of 2.
		 int width_tmp = o.outWidth, height_tmp = o.outHeight;
		 int scale = 1;
		 while (true) {
		 if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
		   break;
		   width_tmp /= 2;
		   height_tmp /= 2;
		   scale *= 2;
		 }

		 // Decode with inSampleSize
		 BitmapFactory.Options o2 = new BitmapFactory.Options();
		 o2.inSampleSize = scale;
		 bitmap = BitmapFactory.decodeFile(filePath, o2);


       imageView.setImageBitmap(bitmap);
       imageAdded=true;
      
       ByteArrayOutputStream bos = new ByteArrayOutputStream();
       bitmap.compress(CompressFormat.JPEG, 1, bos);
       byte[] data = bos.toByteArray();
       base64 = Base64.encodeToString(data, Base64.NO_WRAP);
       Toast.makeText(getBaseContext(), base64.toString(), Toast.LENGTH_SHORT).show();
//       Log.i("image data",data.length+"");
//       Log.i("image data",base64.length()+"");
//       Log.i("image data",base64.charAt(base64.length()-1)+"");
//       Log.i("image data",base64);
       
       uploadImage = new UploadImageRequest(base64);
       new BackgroundTask1().execute(uploadImage,null);

      
      
		
	}
	
	
	
	
private class BackgroundTask1 extends AsyncTask<Runnable, Integer, Long> {
	    
		@Override
		protected void onPreExecute() {
		super.onPreExecute();
		}
	
		@Override
		protected void onPostExecute(Long result) {
			
		if(progress!=null)
			progress.dismiss();
	        staticObjects= new StaticObjects();
	        
	        UpdateLocationRequest update= new UpdateLocationRequest(StaticObjects.getSelectedLocation().getLocationID(),StaticObjects.getTemp());
	        new BackgroundTask2().execute(uploadImage, update);
		}

		@Override
		protected Long doInBackground(Runnable... task) {
			
			for(int i=0; i<task.length;i++)
			{
				if(task[i]!=null)
					task[i].run();
				if (isCancelled()) break;
			}
			return null;
		}
	 }

private class BackgroundTask2 extends AsyncTask<Runnable, Integer, Long> {
    
	@Override
	protected void onPreExecute() {
	super.onPreExecute();
	}

	@Override
	protected void onPostExecute(Long result) {
		
	if(progress!=null)
		progress.dismiss();
        staticObjects= new StaticObjects();
        
        RetrieveImagesByLocationIDRequest request = new RetrieveImagesByLocationIDRequest(StaticObjects.getSelectedLocation().getLocationID());;
		new BackgroundTask3().execute(request, null);
	}

	@Override
	protected Long doInBackground(Runnable... task) {
		
		for(int i=0; i<task.length;i++)
		{
			if(task[i]!=null)
				task[i].run();
			if (isCancelled()) break;
		}
		return null;
	}
 }

private class BackgroundTask3 extends AsyncTask<Runnable, Integer, Long> {
    
	@Override
	protected void onPreExecute() {
	super.onPreExecute();
}

	@Override
	protected void onPostExecute(Long result) {
		
		if(progress!=null)
		progress.dismiss();
        staticObjects= new StaticObjects();
        
        for(int i=0; i<StaticObjects.getImagesList().size();i++){
        	if(StaticObjects.getImagesList().get(i).getType().equals(Integer.toString(StaticObjects.getSelectedLocation().getLocationID())))
        		{
        			imageURLs.add(StaticObjects.getImagesList().get(i).getImageURL());
        		}
        }
        gallery.setAdapter(new GalleryImageAdapter(getBaseContext(),imageURLs));
		Log.i("String", "DONE");
	}

	@Override
	protected Long doInBackground(Runnable... task) {
		
		for(int i=0; i<task.length;i++)
		{
			if(task[i]!=null)
				task[i].run();
			if (isCancelled()) break;
		}
		return null;
	}
 }
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.shop_photos, menu);
		return true;
	}

}
