package com.healthcentral.activity;

import java.util.ArrayList;
import java.util.List;

import org.kroz.activerecord.ActiveRecordException;

import com.healthcentral.common.CustomResourcesAdapter;
import com.heathcentral.model.Site;
import com.heathcentral.service.DatabaseController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

public class SiteResourcesActivity extends Activity implements
AdapterView.OnItemClickListener {
	
	DatabaseController databaseController;
	private ListView mySitesListView;
	List<Site> sites = new ArrayList();
	List<String> resourcesList = new ArrayList<String>();
	private TextView titleTextView;
	CustomResourcesAdapter customAdapter;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(1);
		setContentView(R.layout.main);
		String str = getIntent().getExtras().getString("site");
		this.mySitesListView = ((ListView) findViewById(R.id.list_sites));
		this.titleTextView = ((TextView) findViewById(R.id.title));
		this.databaseController = new DatabaseController(getApplicationContext());
		
		resourcesList.add("Slideshows");
		resourcesList.add("Quizzes");
		
		try {
			DatabaseController.initDatabase();
			this.sites = this.databaseController.getSlideshows(str);
			this.titleTextView.setText(((Site) this.sites.get(0)).getFriendlyTitle());
			this.customAdapter = new CustomResourcesAdapter(this, resourcesList);
			this.mySitesListView.setOnItemClickListener(this);
			this.mySitesListView.setAdapter(this.customAdapter);
			return;
		} catch (ActiveRecordException localActiveRecordException) {
			while (true)
				localActiveRecordException.printStackTrace();
		}
	}
	
	protected void onDestroy() {
		super.onDestroy();
		try {
			if (this.databaseController.getIsOpenDatabase())
				this.databaseController.closeConnection();
			return;
		} catch (ActiveRecordException localActiveRecordException) {
			while (true)
				localActiveRecordException.printStackTrace();
		}
	}

	public void onItemClick(AdapterView<?> paramAdapterView, View paramView,
			int paramInt, long paramLong) {
		//Intent localIntent = new Intent(this, SiteSlideshowsActivity.class);
		//localIntent.putExtra("site", ((Site) this.sites.get(paramInt)).vertical);
		//startActivity(localIntent);
		
		if (paramInt == 0){
			Intent localIntent = new Intent(this, SiteSlideshowsActivity.class);
			localIntent.putExtra("site", ((Site) this.sites.get(paramInt)).vertical);
			startActivity(localIntent);
		} else {
			Intent localIntent = new Intent(this, SiteQuizzesActivity.class);
			localIntent.putExtra("site", ((Site) this.sites.get(paramInt)).vertical);
			startActivity(localIntent);
		}
	}
}
