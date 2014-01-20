package com.petsociety.main.analysis;


import java.util.ArrayList;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.tools.PanListener;
import org.achartengine.tools.ZoomEvent;
import org.achartengine.tools.ZoomListener;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.petsociety.utils.StaticObjects;

@SuppressLint({ "NewApi", "CutPasteId" })
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
	
	private HeatView overlay;
	
	private boolean locationCanvasShown;
	private boolean strayCanvasShown;
	private boolean lostCanvasShown;
	private boolean eventCanvasShown;
	
	private static int[] COLORS = new int[] { Color.GREEN, Color.BLUE, Color.MAGENTA, Color.CYAN };
	/** The main series that will include all the data. */
	private CategorySeries mSeries = new CategorySeries("fuck");
	/** The main renderer for the main dataset. */

	/** The main dataset that includes all the series that go into a chart. */
	  private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
	  /** The main renderer that includes all the renderers customizing a chart. */
	  private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
	  /** The most recently added series. */
	  private XYSeries mCurrentSeries;
	  /** The most recently created renderer, customizing the current series. */
	  private XYSeriesRenderer mCurrentRenderer;
	  /** Button for creating a new series of data. */
	  private Button mNewSeries;
	  /** Button for adding entered data to the current series. */
	  private Button mAdd;
	  /** Edit text field for entering the X value of the data to be added. */
	  private EditText mX;
	  /** Edit text field for entering the Y value of the data to be added. */
	  private EditText mY;
	  /** The chart view that displays the data. */
	  private GraphicalView mChartView;
	
	public AnalysisActivity() {
		super(R.string.title_activity_analysis);
		// TODO Auto-generated constructor stub
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
		
		
		RetrieveAllEventRequest eventRequest= new RetrieveAllEventRequest();
		RetrieveAllLocationRequest locationRequest= new RetrieveAllLocationRequest();
		RetrieveAllStrayRequest strayRequest= new RetrieveAllStrayRequest();
		RetrieveAllLostRequest lostRequest= new RetrieveAllLostRequest();
	
		//retrieve lost data first as default
		
		
		
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
	
	private XYMultipleSeriesDataset getDemoDataset() {
		    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		    final int nr = 2;
		    Random r = new Random();
		    for (int i = 0; i < 2; i++) {
		      XYSeries series = new XYSeries("Demo series " + (i + 1));
		      for (int k = 0; k < nr; k++) {
		        series.add(k, 20 + r.nextInt() % 100);
		      }
		      dataset.addSeries(series);
		    }
		    return dataset;
		  }
	private XYMultipleSeriesRenderer getDemoRenderer() {
		    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		    renderer.setAxisTitleTextSize(16);
		    renderer.setChartTitleTextSize(20);
		    renderer.setLabelsTextSize(15);
		    renderer.setLegendTextSize(15);
		    renderer.setPointSize(5f);
		    renderer.setMargins(new int[] {20, 30, 15, 0});
		    XYSeriesRenderer r = new XYSeriesRenderer();
		    r.setColor(Color.BLUE);
		    r.setPointStyle(PointStyle.SQUARE);
		    r.setFillBelowLine(true);
		    r.setFillBelowLineColor(Color.WHITE);
		    r.setFillPoints(true);
		    renderer.addSeriesRenderer(r);
		    r = new XYSeriesRenderer();
		    r.setPointStyle(PointStyle.CIRCLE);
		    r.setColor(Color.GREEN);
		    r.setFillPoints(true);
		    renderer.addSeriesRenderer(r);
		    renderer.setAxesColor(Color.DKGRAY);
		    renderer.setLabelsColor(Color.LTGRAY);
		    return renderer;
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

	private void ClearHeatMap()
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

		float[][] points = new float[1][2];
		LatLng latLng = new LatLng(0,0);
		com.google.android.gms.maps.Projection projection = mMap.getProjection();


		Point p1 = new Point();
		p1=projection.toScreenLocation(latLng);
	
		points[0][0]=p1.x;
		points[0][1]=p1.y;
		overlay.addPoint(points);
			
		
	}
	
	private class BackgroundTask extends AsyncTask<Runnable, Integer, Long> {
	     
		@Override
		protected void onPostExecute(Long result) {
			
			super.onPostExecute(result);
			mMap.clear();
			if(StaticObjects.getAnalysisEvent()!=null)
			{
				DrawEventHeatMaps();
			}
			if(StaticObjects.getAnalysisStray()!=null)
			{
				DrawStrayHeatMaps();
			}
			if(StaticObjects.getAndlysisLost()!=null)
			{
				DrawLostHeatMaps();
				DrawLostChart();
			}
			if(StaticObjects.getAnslysisLocation()!=null)
			{
				DrawLocationHeatMaps();
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
//						RetrieveAllEventRequest eventRequest= new RetrieveAllEventRequest();
//						RetrieveAllLocationRequest locationRequest= new RetrieveAllLocationRequest();
//						RetrieveAllStrayRequest strayRequest= new RetrieveAllStrayRequest();
//						RetrieveAllLostRequest lostRequest= new RetrieveAllLostRequest();
//						
//						new BackgroundTask().execute(locationRequest,lostRequest,eventRequest,locationRequest,strayRequest);
						ClearHeatMap();
					}
					if(which==1)
					{
						LocationDialogFragment frag= new LocationDialogFragment();
						frag.show(getFragmentManager(), null);
					}
					if(which==2)
					{
						//stray
						StrayDialogFragment frag= new StrayDialogFragment();
						frag.show(getFragmentManager(), null);
					}
					if(which==3)
					{
						//lost
							StaticObjects.setAnalysisEvent(null);
			          	   StaticObjects.setAnalysisStray(null);
			          	   StaticObjects.setAndlysisLost(null);
			          	   StaticObjects.setAnslysisLocation(null);
			          	   
			          	   RetrieveLostByTypeRequest request= new RetrieveLostByTypeRequest("Dog");
			              	new BackgroundTask().execute(request,request);
					}
					if(which==4)
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

	private void DrawLostChart()
	{
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		   
	    int x[]={1,2,3,4,5};
	    int y[]={0,0,0,0,0};
	    
	    TimeSeries series= new TimeSeries("Line");
	    for(int i=0;i<x.length;i++)
	    {
	    	series.add(x[i], y[i]);
	    }
	    dataset.addSeries(series);
	    
		XYMultipleSeriesRenderer renderer= new XYMultipleSeriesRenderer();
		XYSeriesRenderer red= new XYSeriesRenderer();
		red.setColor(Color.BLUE);
		red.setPointStyle(PointStyle.SQUARE);
		red.setFillPoints(true);
		renderer.addSeriesRenderer(red);
        
		
		
        if (mChartView == null) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
             //mChartView = ChartFactory.getLineChartView(this, getDemoDataset(), getDemoRenderer());
            mChartView = ChartFactory.getBarChartView(this, dataset, renderer,Type.DEFAULT);
            //mChartView = ChartFactory.getPieChartView(this, mSeries, getDemoRenderer());

            // enable the chart click events
           // mRenderer.setSelectableBuffer(100);
        
            // an example of handling the zoom events on the chart
            mChartView.addZoomListener(new ZoomListener() {
              public void zoomApplied(ZoomEvent e) {
                String type = "out";
                if (e.isZoomIn()) {
                  type = "in";
                }
                Log.i("Zoom", "Zoom " + type + " rate " + e.getZoomRate());
              }

              public void zoomReset() {
                Log.i("Zoom", "Reset");
              }
            }, true, true);
            // an example of handling the pan events on the chart
            mChartView.addPanListener(new PanListener() {
              public void panApplied() {
                Log.i("Pan", "New X range=[" + mRenderer.getXAxisMin() + ", " + mRenderer.getXAxisMax()
                    + "], Y range=[" + mRenderer.getYAxisMax() + ", " + mRenderer.getYAxisMax() + "]");
              }
            });
            layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
            boolean enabled = mDataset.getSeriesCount() > 0;
           
          } else {
            mChartView.repaint();
          }
	}
	private void DrawStrayChart()
	{
		
	}
	private void DrawEventChart()
	{
		
	}
	private void DrawLocationChart()
	{
		
	}
}
