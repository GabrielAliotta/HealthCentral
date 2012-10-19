package com.healthcentral.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.healthcentral.common.CustomSlideshowAdapter;
import com.heathcentral.model.Site;
import com.heathcentral.service.DatabaseController;
import java.util.ArrayList;
import java.util.List;
import org.kroz.activerecord.ActiveRecordException;

public class SiteSlideshowsActivity extends Activity implements
		AdapterView.OnItemClickListener {
	CustomSlideshowAdapter customAdapter;
	DatabaseController databaseController;
	private ListView mySitesListView;
	List<Site> sites = new ArrayList();
	private TextView titleTextView;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(1);
		setContentView(R.layout.main);
		String str = getIntent().getExtras().getString("site");
		this.mySitesListView = ((ListView) findViewById(R.id.list_sites));
		this.titleTextView = ((TextView) findViewById(R.id.title));
		this.databaseController = new DatabaseController(
				getApplicationContext());
		try {
			DatabaseController.initDatabase();
			this.sites = this.databaseController.getSlideshows(str);
			this.titleTextView.setText(((Site) this.sites.get(0))
					.getFriendlyTitle());
			this.customAdapter = new CustomSlideshowAdapter(this, this.sites,
					"titles");
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
		Intent localIntent = new Intent(this, SlideshowDetails.class);
		localIntent.putExtra("SlideshowId",
				((Site) this.sites.get(paramInt)).id);
		startActivity(localIntent);
	}
}