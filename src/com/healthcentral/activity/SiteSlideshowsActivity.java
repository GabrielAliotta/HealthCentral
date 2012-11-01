package com.healthcentral.activity;

import java.util.ArrayList;
import java.util.List;

import org.kroz.activerecord.ActiveRecordException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.healthcentral.common.CustomSlideshowAdapter;
import com.heathcentral.model.Slideshow;
import com.heathcentral.service.DatabaseController;
import com.heathcentral.service.GetSlideshowsTask;

public class SiteSlideshowsActivity extends Activity implements
		AdapterView.OnItemClickListener {
	
	private CustomSlideshowAdapter customAdapter;
	private DatabaseController databaseController;
	private ListView mySitesListView;
	private List<Slideshow> sites = new ArrayList<Slideshow>();
	private TextView titleTextView;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(1);
		setContentView(R.layout.main);
		String str = getIntent().getExtras().getString("vertical");
		this.mySitesListView = ((ListView) findViewById(R.id.list_verticals));
		this.titleTextView = ((TextView) findViewById(R.id.title));
		this.databaseController = new DatabaseController(getApplicationContext());
		try {
			DatabaseController.initDatabase();
			new GetSlideshowsTask(this, this.databaseController, str).execute(new String[0]);
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
		localIntent.putExtra("SlideshowId",	((Slideshow) this.sites.get(paramInt)).id);
		startActivity(localIntent);
	}
	
	public void updateList(){
		this.sites = this.databaseController.getSites();
		this.titleTextView.setText(((Slideshow) this.sites.get(0)).getFriendlyTitle());
		this.customAdapter = new CustomSlideshowAdapter(this, this.sites, "titles");
		this.mySitesListView.setOnItemClickListener(this);
		this.mySitesListView.setAdapter(this.customAdapter);
	}
}