package com.healthcentral.activity;

import java.util.ArrayList;
import java.util.List;

import org.kroz.activerecord.ActiveRecordException;

import com.healthcenter.common.CustomSlideshowAdapter;
import com.heathcentral.model.Site;
import com.heathcentral.service.DatabaseController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class SiteTitleActivity extends Activity implements OnItemClickListener{
	
	DatabaseController databaseController;
	
	private ListView mySitesListView;
	private TextView titleTextView;
	
	CustomSlideshowAdapter customAdapter;
	List<Site> sites = new ArrayList<Site>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        
        String site = getIntent().getExtras().getString("site");
        
        mySitesListView = (ListView) this.findViewById(R.id.list_sites);
        titleTextView = (TextView) this.findViewById(R.id.title);
        
        
        
        databaseController = new DatabaseController(getApplicationContext());
		try {
			databaseController.initDatabase();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		
		sites = databaseController.getSlideshows(site);
		
		titleTextView.setText(sites.get(0).getFriendlyTitle());
		
		customAdapter = new CustomSlideshowAdapter(this, sites, "titles");
		mySitesListView.setOnItemClickListener(this);
		mySitesListView.setAdapter(customAdapter);
    }

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent intent= new Intent(SiteTitleActivity.this, SlideshowDetails.class);
    	intent.putExtra("SlideshowId", sites.get(arg2).id);
    	startActivity(intent);
	}
	
	@Override
	protected void onDestroy() {
	    super.onDestroy();
	    try {
			if (databaseController.getIsOpenDatabase() != false) {
				databaseController.closeConnection();
			}
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
	}

}
