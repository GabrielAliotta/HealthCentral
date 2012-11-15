package com.healthcentral.activity;

import java.util.ArrayList;
import java.util.List;

import org.kroz.activerecord.ActiveRecordException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.healthcentral.common.CustomSlideshowAdapter;
import com.heathcentral.model.Slideshow;
import com.heathcentral.model.Vertical;
import com.heathcentral.service.DatabaseController;
import com.heathcentral.service.GetSlideshowsTask;

public class SiteSlideshowsActivity extends Activity implements
		AdapterView.OnItemClickListener {
	
	private CustomSlideshowAdapter customAdapter;
	private DatabaseController databaseController;
	private ListView mySitesListView;
	private List<Slideshow> slideshows = new ArrayList<Slideshow>();
	private TextView titleTextView;
	private String verticalId;
	private TextView actionActivity;
	
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(1);
		setContentView(R.layout.main);
		
		((LinearLayout) findViewById(R.id.linearLayout)).setVisibility(View.GONE);
		verticalId = getIntent().getExtras().getString("vertical");
		mySitesListView = ((ListView) findViewById(R.id.list_verticals));
		titleTextView = ((TextView) findViewById(R.id.vertical_title));
		actionActivity = ((TextView) findViewById(R.id.action_activity));
		databaseController = new DatabaseController(getApplicationContext());
		try {
			DatabaseController.initDatabase();
			new GetSlideshowsTask(this, this.databaseController, this.verticalId).execute(new String[0]);
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
		Intent localIntent = new Intent(this, SlideshowPagerActivity.class);
		localIntent.putExtra("slideshowId",	((Slideshow) this.slideshows.get(paramInt)).getId());
		localIntent.putExtra("slideshowsIds", this.getSlideshowsIds());
		localIntent.putExtra("slideshowIndex", paramInt);
		startActivity(localIntent);
	}
	
	public void updateList(){
		slideshows = this.databaseController.getSlideshows(this.verticalId);
		Vertical vertical = this.databaseController.getVerticalById(this.verticalId);
		titleTextView.setText(vertical.getVerticalName());
		titleTextView.setVisibility(View.VISIBLE);
		actionActivity.setText("Select a slideshow");
		actionActivity.setVisibility(View.VISIBLE);
		customAdapter = new CustomSlideshowAdapter(this, this.slideshows);
		mySitesListView.setOnItemClickListener(this);
		mySitesListView.setAdapter(this.customAdapter);
	}
	
	private int[] getSlideshowsIds() {
		int[] ids = new int[100];
		int index = 0;
		for(Slideshow slideshow : this.slideshows) {
			ids[index] = Integer.parseInt(slideshow.getId());
			index++;
		}
		return ids;
	}
}