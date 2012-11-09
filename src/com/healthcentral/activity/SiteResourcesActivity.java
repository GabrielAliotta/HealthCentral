package com.healthcentral.activity;

import java.util.ArrayList;
import java.util.List;

import org.kroz.activerecord.ActiveRecordException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.healthcentral.common.CustomResourcesAdapter;
import com.heathcentral.model.Vertical;
import com.heathcentral.service.DatabaseController;

public class SiteResourcesActivity extends Activity implements
		AdapterView.OnItemClickListener {

	private DatabaseController databaseController;
	private ListView mySitesListView;
	private List<String> resourcesList = new ArrayList<String>();
	private TextView titleTextView;
	private TextView actionActivity;
	private Vertical vertical;	
	private CustomResourcesAdapter customAdapter;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(1);
		setContentView(R.layout.main);
		
		((LinearLayout) findViewById(R.id.linearLayout)).setVisibility(View.GONE);
		String str = getIntent().getExtras().getString("vertical");
		mySitesListView = (ListView) findViewById(R.id.list_verticals);
		titleTextView = (TextView) findViewById(R.id.vertical_title);
		actionActivity = (TextView) findViewById(R.id.action_activity);
		databaseController = new DatabaseController(getApplicationContext());

		resourcesList.add("Slideshows");
		resourcesList.add("Quizzes");

		try {
			DatabaseController.initDatabase();
			vertical = this.databaseController.getVerticalById(str);
			titleTextView.setText(vertical.getVerticalName());
			titleTextView.setVisibility(View.VISIBLE);
			actionActivity.setText("Select a Resource");
			actionActivity.setVisibility(View.VISIBLE);
			customAdapter = new CustomResourcesAdapter(this, resourcesList);
			mySitesListView.setOnItemClickListener(this);
			mySitesListView.setAdapter(this.customAdapter);			
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
		if (paramInt == 0) {
			Intent localIntent = new Intent(this, SiteSlideshowsActivity.class);
			localIntent.putExtra("vertical", vertical.getVerticalId());
			startActivity(localIntent);
		} else {
			Intent localIntent = new Intent(this, SiteQuizzesActivity.class);
			localIntent.putExtra("vertical", vertical.getVerticalId());
			startActivity(localIntent);
		}
	}
}
