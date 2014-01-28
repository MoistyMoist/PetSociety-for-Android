package com.petsociety.main.analysis;


import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.tools.ZoomEvent;
import org.achartengine.tools.ZoomListener;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.petsociety.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.petsociety.httprequests.RetrieveAllEventRequest;
import com.petsociety.httprequests.RetrieveAllLocationRequest;
import com.petsociety.httprequests.RetrieveAllLostRequest;
import com.petsociety.httprequests.RetrieveAllStrayRequest;
import com.petsociety.httprequests.RetrieveCurrentEventRequest;
import com.petsociety.httprequests.RetrieveLocationByTypeRequest;
import com.petsociety.httprequests.RetrieveLostByTypeRequest;
import com.petsociety.httprequests.RetrieveStrayByTypeRequest;
import com.petsociety.main.MainBaseActivity;
import com.petsociety.main.lost.LostActivity.LostListAdapter;
import com.petsociety.utils.StaticObjects;

@SuppressLint({ "NewApi", "CutPasteId", "SetJavaScriptEnabled" })
public class AnalysisActivity extends MainBaseActivity 
implements 
ConnectionCallbacks,
OnConnectionFailedListener,
LocationListener, 
OnMyLocationButtonClickListener{

	
	private GoogleMap mMap;
	Button buttonMap;
    private LocationClient mLocationClient;
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
	Context context;
	
	Button filterBtn;
	Button filterBtn2;
	
	ArrayList<Integer> selectedLocationType;
	ArrayList<Integer> selectedLostType;
	ArrayList<Integer> selectedStrayType;
	ArrayAdapter<CharSequence> analysisTypeAdapter;
	
	ListView listView;
	
	private HeatView overlay;
	
	private boolean locationCanvasShown;
	private boolean strayCanvasShown;
	private boolean lostCanvasShown;
	private boolean eventCanvasShown;

	private GraphicalView mChartView;
	
	public AnalysisActivity() {
		super(R.string.title_activity_analysis);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		context=this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_analysis);
		setSlidingActionBarEnabled(true);
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT);	
		sm.setSecondaryShadowDrawable(R.drawable.shadowright);
		sm.setShadowDrawable(R.drawable.shadow);
		overlay=(HeatView)findViewById(R.id.heatmap);
		ViewGroup viewGroup=(ViewGroup)findViewById(R.id.analysis_map);
		viewGroup.addView(View.inflate(this, R.layout.basic_map, null));
		setUpMapIfNeeded();
		
		RetrieveAllLostRequest lostRequest= new RetrieveAllLostRequest();
		
		//retrieve lost data first as default
		StaticObjects.setAnalysisEvent(null);
 	   	StaticObjects.setAnalysisStray(null);
 	   //	StaticObjects.setAndlysisLost(null);
 	   	StaticObjects.setAnslysisLocation(null);
		// new BackgroundTask().execute(lostRequest,lostRequest);
		
		
		mMap.setOnCameraChangeListener(new OnCameraChangeListener(){
			@Override
			public void onCameraChange(CameraPosition arg0) {
				// TODO Auto-generated method stub
				if(locationCanvasShown)
				{
					DrawLocationHeatMaps();
				}
				if(strayCanvasShown)
				{
					DrawStrayHeatMaps();
				}
				if(lostCanvasShown)
				{
					DrawLostHeatMaps();
				}
				if(eventCanvasShown)
				{
					DrawEventHeatMaps();
				}
			}});
	
		
		
	
	}
	
	public void chartBtn(View view)
	{
		
	}

	
	private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                //setUpMap();
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationButtonClickListener(this);
                LatLng singapore = new LatLng(1.37, 103.84);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 11));
            }
        }
    }
	
	public boolean onMyLocationButtonClick() {
	        Toast.makeText(this, "Tracking...", Toast.LENGTH_SHORT).show();
	        return false;
	    }

	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-geneateOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		getSupportMenuInflater().inflate(R.menu.analysis, menu);
		return true;
	}
	
	public void nextPage(View view)
	{
		AnalysisList option= new AnalysisList();
		option.show(getFragmentManager(), null);
	}
	
	private void DrawLostHeatMaps()
	{
		strayCanvasShown=false;
		lostCanvasShown=true;
		locationCanvasShown=false;
		eventCanvasShown=false;
		overlay.clearMap();

		for(int i=0;i<StaticObjects.getAndlysisLost().size();i++)
		{
			float[][] points = new float[1][2];
			LatLng latLng = new LatLng(StaticObjects.getAndlysisLost().get(i).getX(), StaticObjects.getAndlysisLost().get(i).getY());
			com.google.android.gms.maps.Projection projection = mMap.getProjection();


			Point p1 = new Point();
			p1=projection.toScreenLocation(latLng);
		
			points[0][0]=p1.x;
			points[0][1]=p1.y;
			overlay.addPoint(points);
		}
	}
	
	private void DrawStrayHeatMaps()
	{
		strayCanvasShown=true;
		lostCanvasShown=false;
		locationCanvasShown=false;
		eventCanvasShown=false;
		overlay.clearMap();

		for(int i=0;i<StaticObjects.getAnalysisStray().size();i++)
		{
			float[][] points = new float[1][2];
			LatLng latLng = new LatLng(StaticObjects.getAnalysisStray().get(i).getX(), StaticObjects.getAnalysisStray().get(i).getY());
			com.google.android.gms.maps.Projection projection = mMap.getProjection();


			Point p1 = new Point();
			p1=projection.toScreenLocation(latLng);
		
			points[0][0]=p1.x;
			points[0][1]=p1.y;
			overlay.addPoint(points);
		}
	}
	
	private void DrawLocationHeatMaps()
	{
		strayCanvasShown=false;
		lostCanvasShown=false;
		locationCanvasShown=true;
		eventCanvasShown=false;
		overlay.clearMap();
		for(int i=0;i<StaticObjects.getAnslysisLocation().size();i++)
		{
			float[][] points = new float[1][2];
			LatLng latLng = new LatLng(StaticObjects.getAnslysisLocation().get(i).getX(), StaticObjects.getAnslysisLocation().get(i).getY());
			com.google.android.gms.maps.Projection projection = mMap.getProjection();


			Point p1 = new Point();
			p1=projection.toScreenLocation(latLng);

			points[0][0]=p1.x;
			points[0][1]=p1.y;
			overlay.addPoint(points);
		}
	}
	
	private void DrawEventHeatMaps()
	{
		strayCanvasShown=false;
		lostCanvasShown=false;
		locationCanvasShown=false;
		eventCanvasShown=true;
		overlay.clearMap();

		for(int i=0;i<StaticObjects.getAnalysisEvent().size();i++)
		{
			float[][] points = new float[1][2];
			LatLng latLng = new LatLng(StaticObjects.getAnalysisEvent().get(i).getX(), StaticObjects.getAnalysisEvent().get(i).getY());
			com.google.android.gms.maps.Projection projection = mMap.getProjection();


			Point p1 = new Point();
			p1=projection.toScreenLocation(latLng);
		
			points[0][0]=p1.x;
			points[0][1]=p1.y;
			overlay.addPoint(points);
		}
	}

	private void ClearHeatMap(View view)
	{
		StaticObjects.setAnalysisEvent(null);
   	   StaticObjects.setAnalysisStray(null);
   	   StaticObjects.setAndlysisLost(null);
   	   StaticObjects.setAnslysisLocation(null);
		strayCanvasShown=false;
		lostCanvasShown=false;
		locationCanvasShown=false;
		eventCanvasShown=false;
		overlay.clearMap();

//		float[][] points = new float[1][2];
//		LatLng latLng = new LatLng(0,0);
//		com.google.android.gms.maps.Projection projection = mMap.getProjection();


//		Point p1 = new Point();
//		p1=projection.toScreenLocation(latLng);
//	
//		points[0][0]=p1.x;
//		points[0][1]=p1.y;
//		overlay.addPoint(points);
			
		
	}
	
	private class BackgroundTask extends AsyncTask<Runnable, Integer, Long> {
	     
		@Override
		protected void onPostExecute(Long result) {
			
			super.onPostExecute(result);
			mMap.clear();
			if(StaticObjects.getAnalysisEvent()!=null)
			{
				DrawEventHeatMaps();
				//DrawEventChart();
			}
			if(StaticObjects.getAnalysisStray()!=null)
			{
				DrawStrayHeatMaps();
				//DrawStrayChart();
			}
			if(StaticObjects.getAndlysisLost()!=null)
			{
				DrawLostHeatMaps();
				//DrawLostChart();
			}
			if(StaticObjects.getAnslysisLocation()!=null)
			{
				DrawLocationHeatMaps();
				//DrawLocationChart();
			}
		}

		@Override
		protected void onPreExecute() {
			Toast.makeText(context, "Refreshing..", Toast.LENGTH_SHORT).show();
			super.onPreExecute();
		}

		@Override
		protected Long doInBackground(Runnable... task) {
			
			for(int i=0; i<task.length;i++)
			{
				task[i].run();
				
				if (isCancelled()) break;
			}
			return null;
		}
	 }
	
	@SuppressLint("ValidFragment")
	private class LocationDialogFragment extends DialogFragment {
		@Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	    	selectedLocationType = new ArrayList<Integer>();  // Where we track the selected items
	        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        // Set the dialog title
	        builder.setTitle("Select Location Type");
	        // Specify the list array, the items to be selected by default (null for none),
	        // and the listener through which to receive callbacks when items are selected
	        builder.setMultiChoiceItems(StaticObjects.getLOCATION_TYPE(), null,
	                          new DialogInterface.OnMultiChoiceClickListener() {
	                   @Override
	                   public void onClick(DialogInterface dialog, int which,
	                           boolean isChecked) {
	                       if (isChecked) {
	                           // If the user checked the item, add it to the selected items
	                    	   selectedLocationType.add(which);
	                       } else if (selectedLocationType.contains(which)) {
	                           // Else, if the item is already in the array, remove it 
	                    	   selectedLocationType.remove(Integer.valueOf(which));
	                       }
	                   }
	               })
	               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                   @Override
	                   public void onClick(DialogInterface dialog, int id) {
	                      
	                	   StaticObjects.setAnalysisEvent(null);
	                	   StaticObjects.setAnalysisStray(null);
	                	   StaticObjects.setAndlysisLost(null);
	                	   StaticObjects.setAnslysisLocation(null);
	                	   
	                	   RetrieveLocationByTypeRequest request;
	                      for(int i=0;i<selectedLocationType.size();i++)
	                      {
	                    	  request= new RetrieveLocationByTypeRequest(StaticObjects.getLOCATION_TYPE()[selectedLocationType.get(i)]);
	                    	  new BackgroundTask().execute(request,request);
	                      }
	                   }
	               })
	               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                   @Override
	                   public void onClick(DialogInterface dialog, int id) {
	                      
	                   }
	               });

	        return builder.create();
	    }
	}

	@SuppressLint("ValidFragment")
	private class AnalysisList extends DialogFragment {
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public Dialog onCreateDialog(Bundle savedInstanceState){
			
			//String [] data= StaticObjects.getANALYSIS_TYPE();
			//data[data.length]="Clear Map";
			
			ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,StaticObjects.getANALYSIS_TYPE());
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		    builder.setTitle("Select Option");builder.setAdapter(spinnerArrayAdapter, new OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(which==0)
					{
						LocationDialogFragment frag= new LocationDialogFragment();
						frag.show(getFragmentManager(), null);
					}
					if(which==1)
					{
						//stray
						StrayDialogFragment frag= new StrayDialogFragment();
						frag.show(getFragmentManager(), null);
					}
					if(which==2)
					{
						//lost
						StaticObjects.setAnalysisEvent(null);
			          	StaticObjects.setAnalysisStray(null);
			          	StaticObjects.setAndlysisLost(null);
			          	StaticObjects.setAnslysisLocation(null);
			          	   
			          	RetrieveLostByTypeRequest request= new RetrieveLostByTypeRequest("Dog");
			            new BackgroundTask().execute(request,request);
					}
					if(which==3)
					{
						//event
						EventDialogFragment frag= new EventDialogFragment();
						frag.show(getFragmentManager(), null);
					}
					
				}});
		    return builder.create();
		}
	}

	@SuppressLint("ValidFragment")
	private class EventDialogFragment extends DialogFragment
	{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			String[] eventList={"All Events","Upcoming Events","By Date"};
			ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,eventList);
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		    builder.setTitle("Select Option");builder.setAdapter(spinnerArrayAdapter, new OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(which==0)
					{
						StaticObjects.setAnalysisEvent(null);
			          	   StaticObjects.setAnalysisStray(null);
			          	   StaticObjects.setAndlysisLost(null);
			          	   StaticObjects.setAnslysisLocation(null);
						RetrieveAllEventRequest eventRequest= new RetrieveAllEventRequest();
						new BackgroundTask().execute(eventRequest,eventRequest);
					}
					if(which==1)
					{
						StaticObjects.setAnalysisEvent(null);
			          	   StaticObjects.setAnalysisStray(null);
			          	   StaticObjects.setAndlysisLost(null);
			          	   StaticObjects.setAnslysisLocation(null);
						RetrieveCurrentEventRequest eventRequest= new RetrieveCurrentEventRequest();
						new BackgroundTask().execute(eventRequest,eventRequest);
					}
					if(which==2)
					{
						//event
						EventDateTimeDialogFragment frag= new EventDateTimeDialogFragment();
						frag.show(getFragmentManager(), null);
					}	
				}});
	    return builder.create();
		}
	}
	
	@SuppressLint("ValidFragment")
	private class LostDialogFragment extends DialogFragment
	{

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			selectedLostType= new ArrayList<Integer>();
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        // Set the dialog title
	        builder.setTitle("Select Pet Type");
	        builder.setMultiChoiceItems(StaticObjects.getPET_LIST(), null,
                    new DialogInterface.OnMultiChoiceClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which,
                     boolean isChecked) {
                 if (isChecked) {
                	selectedLostType.add(which);
                 } else if (selectedLostType.contains(which)) {
                	 selectedLostType.remove(Integer.valueOf(which));
                 }
             }
         })
         .setPositiveButton("OK", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int id) {
                
          	   StaticObjects.setAnalysisEvent(null);
          	   StaticObjects.setAnalysisStray(null);
          	   StaticObjects.setAndlysisLost(null);
          	   StaticObjects.setAnslysisLocation(null);
          	   
          	   RetrieveLostByTypeRequest request;
                for(int i=0;i<selectedLostType.size();i++)
                {
              	  request= new RetrieveLostByTypeRequest(StaticObjects.getPET_LIST()[selectedLostType.get(i)]);
              	  new BackgroundTask().execute(request,request);
                }
             }
         })
         .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int id) {
                
             }
         });
			return builder.create();
		}
	}
	
	@SuppressLint("ValidFragment")
	private class StrayDialogFragment extends DialogFragment
	{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			selectedStrayType= new ArrayList<Integer>();
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	        // Set the dialog title
	        builder.setTitle("Select Stray Type");
	        builder.setMultiChoiceItems(StaticObjects.getPET_LIST(), null,
                    new DialogInterface.OnMultiChoiceClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which,
                     boolean isChecked) {
                 if (isChecked) {
                     // If the user checked the item, add it to the selected items
                	 selectedStrayType.add(which);
                 } else if (selectedStrayType.contains(which)) {
                     // Else, if the item is already in the array, remove it 
                	 selectedStrayType.remove(Integer.valueOf(which));
                 }
             }
         })
         .setPositiveButton("OK", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int id) {
                
          	   StaticObjects.setAnalysisEvent(null);
          	   StaticObjects.setAnalysisStray(null);
          	   StaticObjects.setAndlysisLost(null);
          	   StaticObjects.setAnslysisLocation(null);
          	   
          	   RetrieveStrayByTypeRequest request;
                for(int i=0;i<selectedStrayType.size();i++)
                {
              	  request= new RetrieveStrayByTypeRequest(StaticObjects.getPET_LIST()[selectedStrayType.get(i)]);
              	  new BackgroundTask().execute(request,request);
                }
             }
         })
         .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int id) {
                
             }
         });
			return builder.create();
		}
	}

	@SuppressLint("ValidFragment")
	private class EventDateTimeDialogFragment extends DialogFragment
	{
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	        // Get the layout inflater
	        LayoutInflater inflater = getActivity().getLayoutInflater();

	        // Inflate and set the layout for the dialog
	        // Pass null as the parent view because its going in the dialog layout
	        builder.setView(inflater.inflate(R.layout.custom_analysis_datepicker_option, null));

	        // Set title of dialog
	        builder.setMessage("Set Date")
	                // Set Ok button
	                .setPositiveButton("Ok",
	                        new DialogInterface.OnClickListener() {
	                            public void onClick(DialogInterface dialog, int id) {
	                                // User ok the dialog
	                            }
	                        })
	                // Set Cancel button
	                .setNegativeButton("Cancel",
	                        new DialogInterface.OnClickListener() {
	                            public void onClick(DialogInterface dialog, int id) {
	                                // User cancelled the dialog
	                            }
	                        })
	                // Set Neutral button (reset Time / Date)
	                .setNeutralButton("Reset",
	                        new DialogInterface.OnClickListener() {
	                            @Override
	                            public void onClick(DialogInterface dialog, int id) {
	                                // User reset the dialog
	                                // reset();
	                            }
	                        });

	        // Create the AlertDialog object and return it
	        return builder.create();
		}
	}
	

	private void loadLostWebView() 
	{
		AlertDialog.Builder alert = new AlertDialog.Builder(this); 
		alert.setTitle("Interesting Facts");

		WebView wv = new WebView(this);
		
		wv.loadUrl("http://petsociety.azurewebsites.net/lostchart.aspx");
		wv.getSettings().setJavaScriptEnabled(true);
		wv.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		wv.setWebViewClient(new WebViewClient() {
		    @Override
		    public boolean shouldOverrideUrlLoading(WebView view, String url) {
		        view.loadUrl(url);

		        return true;
		    }
		});
		alert.setView(wv);
		alert.setNegativeButton("Close", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int id) {
		        dialog.dismiss();
		    }
		});
		alert.show();
	}
	
	//private class StrayChartPopup

	private void DrawLostChart()
	{
		int foundPets=0;
		int notFoundPets=0;

		for(int i=0;i<StaticObjects.getAndlysisLost().size();i++)
		{
			String found=StaticObjects.getAndlysisLost().get(i).getFound()+"";
			
			if(found.equals("0"))
			{
				foundPets=foundPets+1;
			}
			else
			{
				notFoundPets=notFoundPets+1;
			}
		}
		foundPets=foundPets/2;
		notFoundPets= notFoundPets/2;
		
		CategorySeries dataset= new CategorySeries("Lost Pets");
		
		dataset.add("Missing",foundPets);
		dataset.add("Found", notFoundPets);

		int colors[]={Color.RED, Color.BLUE};
		DefaultRenderer renderer= new DefaultRenderer();
		renderer.setChartTitleTextSize(50);
		renderer.setChartTitle("Lost Statictistic");
		renderer.setDisplayValues(true);
		renderer.setExternalZoomEnabled(false);
		renderer.setLabelsTextSize(18);
		renderer.setLegendTextSize(40);
		renderer.setFitLegend(true);
		renderer.setScale(0.8f);

		for(int color:colors)
		{
			SimpleSeriesRenderer r= new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
	//	LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
    //    mChartView = ChartFactory.getPieChartView(this, dataset, renderer);

     //   layout.removeAllViews();
     //   layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
	}
	
	private void DrawStrayChart()
	{
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		   
	    int top[]={1,2,3,4,5,6,7};
	    int y[]={0,0,0,0,0,0,0};
	    
	    for(int x=0;x<StaticObjects.getPET_LIST().length;x++)
	    {
	    	for(int i=0;i<StaticObjects.getAnalysisStray().size();i++)
	    	{
	    		if(StaticObjects.getAnalysisStray().get(i).getType().equalsIgnoreCase(StaticObjects.getPET_LIST()[x]))
	    		{
	    			y[x]=y[x]+1;
	    		}
	    	}
	    }
	    for(int i=0;i<StaticObjects.getPET_LIST().length;i++)
	    {
	    	if(y[i]>=2)
	    	{
	    		y[i]=y[i]/2;
	    	}
	    }
	    
	    TimeSeries Dog= new TimeSeries("Dogs");
	   
	    //	Dog.add(0, y[0]);
	    	//Dog.add(0, 0);
	    //dataset.addSeries(Dog);
	    
	    for(int i=0;i<y.length;i++)
	    {
	    	Dog.add(y[i], y[i]);
	    }
	    dataset.addSeries(Dog);
	    
	    
	    TimeSeries Cat= new TimeSeries("Cats");
	    dataset.addSeries(Cat);
	    
	    TimeSeries Bird= new TimeSeries("Bird");
	    dataset.addSeries(Bird);
	    
	    TimeSeries Fish= new TimeSeries("Fish");
	    dataset.addSeries(Fish);
	  
	    
		XYMultipleSeriesRenderer renderer= new XYMultipleSeriesRenderer();
		
		
		XYSeriesRenderer blue= new XYSeriesRenderer();
		blue.setColor(Color.BLUE);
		blue.setPointStyle(PointStyle.SQUARE);
		blue.setFillPoints(true);
		blue.setDisplayChartValuesDistance(100);
		renderer.addSeriesRenderer(blue);
		
		XYSeriesRenderer red= new XYSeriesRenderer();
		red.setColor(Color.RED);
		red.setPointStyle(PointStyle.SQUARE);
		red.setFillPoints(true);
		renderer.addSeriesRenderer(red);
		
		XYSeriesRenderer green= new XYSeriesRenderer();
		green.setColor(Color.GREEN);
		green.setPointStyle(PointStyle.SQUARE);
		green.setFillPoints(true);
		renderer.addSeriesRenderer(green);
		
		XYSeriesRenderer yellow= new XYSeriesRenderer();
		yellow.setColor(Color.YELLOW);
		yellow.setPointStyle(PointStyle.SQUARE);
		yellow.setFillPoints(true);
		renderer.addSeriesRenderer(yellow);
		
		
       // if (mChartView == null) {
    //        LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
             //mChartView = ChartFactory.getLineChartView(this, getDemoDataset(), getDemoRenderer());
    //        mChartView = ChartFactory.getBarChartView(this, dataset, renderer,Type.DEFAULT);
            //mChartView=ChartFactory.getDoughnutChartView(context, dataset, renderer);
           // mChartView = ChartFactory.getPieChartView(this, mSeries, getDemoRenderer());

     //       layout.removeAllViews();
      //      layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
	}
	

	private void DrawEventChart()
	{
		
	}
	
	private void DrawLocationChart()
	{
		
//		listView=(ListView)findViewById(R.id.analysis_list);
//		Log.i("locationcahrt",listView.getVisibility()+"");
//		mChartView.setVisibility(2);
//		listView.setVisibility(1);
//		Log.i("locationcahrt",listView.getVisibility()+"");
//		ArrayList<com.petsociety.models.Location> list= new ArrayList<com.petsociety.models.Location>();
//		
//        for (int i=0; i<StaticObjects.getAnslysisLocation().size(); i++){
//        	if(StaticObjects.getAnslysisLocation().get(i).getType().equalsIgnoreCase("Accidents"))
//        	{
//        		list.add(StaticObjects.getAnslysisLocation().get(i));
//        		Log.i("locationcahrt",StaticObjects.getAnslysisLocation().get(i).getType()+"");
//        	}
//        		
//        }  
       // CustomAccidentListAdapter adapter = new CustomAccidentListAdapter(getBaseContext(),list);
       // listView.setAdapter(adapter);
	}
}
