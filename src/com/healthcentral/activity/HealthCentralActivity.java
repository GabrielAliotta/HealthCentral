package com.healthcentral.activity;

import java.util.ArrayList;
import java.util.List;

import org.kroz.activerecord.ActiveRecordException;

import com.healthcenter.common.CustomAdapter;
import com.heathcentral.model.Site;
import com.heathcentral.service.DatabaseController;
import com.heathcentral.service.GetSitesTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HealthCentralActivity extends Activity implements
		OnItemClickListener {

	DatabaseController databaseController;
	
	private ListView mySitesListView;

	CustomAdapter customAdapter;
	List<Site> sites = new ArrayList<Site>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		
		mySitesListView = (ListView) this.findViewById(R.id.list_sites);
		mySitesListView.setOnItemClickListener(this);
		
		TextView title2 = (TextView) this.findViewById(R.id.titleTwo);
		title2.setText("Central");

		databaseController = new DatabaseController(getApplicationContext());
		try {
			databaseController.initDatabase();
		} catch (ActiveRecordException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new GetSitesTask(this, databaseController).execute();
		
	}
	
	public void updateList(){
		sites = databaseController.getSites();

		CustomAdapter customAdapter = new CustomAdapter(this, sites, "vertical");
		mySitesListView.setAdapter(customAdapter);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		 Intent intent= new Intent(HealthCentralActivity.this, SiteTitleActivity.class);
		 intent.putExtra("site", sites.get(arg2).vertical);
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