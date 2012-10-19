package com.healthcentral.activity;

import java.util.ArrayList;
import java.util.List;

import org.kroz.activerecord.ActiveRecordException;

import com.healthcentral.common.CustomAdapter;
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

	CustomAdapter customAdapter;
	DatabaseController databaseController;
	private ListView mySitesListView;
	List<Site> sites = new ArrayList();

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(1);
		setContentView(R.layout.main);
		mySitesListView = (ListView) this.findViewById(R.id.list_sites);
		this.mySitesListView.setOnItemClickListener(this);
		((TextView) findViewById(R.id.titleTwo)).setText("Central");
		this.databaseController = new DatabaseController(
				getApplicationContext());
		try {
			DatabaseController.initDatabase();
			new GetSitesTask(this, this.databaseController)
					.execute(new String[0]);
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
		Intent localIntent = new Intent(this, SiteSlideshowsActivity.class);
		localIntent
				.putExtra("site", ((Site) this.sites.get(paramInt)).vertical);
		startActivity(localIntent);
	}

	public void updateList() {
		this.sites = this.databaseController.getSites();
		CustomAdapter localCustomAdapter = new CustomAdapter(this, this.sites,
				"vertical");
		this.mySitesListView.setAdapter(localCustomAdapter);
	}
}