package com.healthcentral.activity;


import java.util.ArrayList;
import java.util.List;

import org.kroz.activerecord.ActiveRecordException;

import com.healthcentral.common.CustomPagerAdapter;
import com.heathcentral.model.Slide;
import com.heathcentral.model.Slideshow;
import com.heathcentral.service.DatabaseController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Window;
import android.widget.TextView;

public class SlideshowPagerActivity extends Activity {
	
	private DatabaseController databaseController;
	private List<Slide> slides = new ArrayList<Slide>();
	private ViewPager viewPager;
	private CustomPagerAdapter customPagerAdapter;
	private String slideshowId;
	private Slideshow slideshow = new Slideshow();
	private Integer slideshowIndex;
	private int[] slideshowIds = null;
	private String verticalName;
	private boolean slideshowIsLoaded;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.slideshow_details_viewpager);
        
		databaseController = new DatabaseController(getApplicationContext());
		try {
			databaseController.initDatabase();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}

		slideshowId = getIntent().getExtras().getString("slideshowId");
		slideshowIndex = getIntent().getExtras().getInt("slideshowIndex");
		slideshowIds = getIntent().getExtras().getIntArray("slideshowsIds");
		verticalName = getIntent().getExtras().getString("verticalName");
		slideshow = databaseController.getSlideshowById(slideshowId);
		
		slideshowIsLoaded = databaseController.slideshowLoaded(String.valueOf(slideshowIds[slideshowIndex + 1]));
        
		updateList();
    }
    
    public void updateList() {
    	
		slides = databaseController.getSlides(slideshow.getId());
		customPagerAdapter = new CustomPagerAdapter(this, slides);
		
		((TextView) findViewById(R.id.slideshow_main_title)).setText(verticalName);
		((TextView) findViewById(R.id.slideshow_title)).setText(slideshow.getTitle());
		
        viewPager = (ViewPager) findViewById(R.id.slides_pager);
        viewPager.setAdapter(customPagerAdapter);
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

        	int state;
        	boolean breakIntent = false;
        	
			public void onPageScrollStateChanged(int arg0) {
				state = arg0;
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {
				if (arg0 + 1 == slides.size() && arg2 == 0 && state != 2 && !breakIntent) {
					if(slideshowIsLoaded){
					Intent localIntent = new Intent(getApplicationContext(), SlideshowPagerActivity.class);
					localIntent.putExtra("slideshowId",	String.valueOf(slideshowIds[++slideshowIndex]));
					localIntent.putExtra("slideshowIndex", slideshowIndex);
					localIntent.putExtra("slideshowsIds", slideshowIds);
					localIntent.putExtra("verticalName", verticalName);
					startActivity(localIntent);
					finish();
					breakIntent = true;
					} else {
						finish();
					}
				}
			}

			public void onPageSelected(int arg0) {
			}
        	
        });
	}
    
}
