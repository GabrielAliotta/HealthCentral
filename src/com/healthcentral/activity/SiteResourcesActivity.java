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
import android.widget.AdapterView.OnItemClickListener;

import com.healthcentral.common.CustomSiteResourceAdapter;
import com.heathcentral.model.Vertical;
import com.heathcentral.service.DatabaseController;

public class SiteResourcesActivity extends Activity implements OnItemClickListener{

	private DatabaseController databaseController;
	private TextView titleTextView;
	private Vertical vertical;
	private String hasSlideshows;
	private String hasQuizzes;
	private ListView listResources;
	private List<String> resources = new ArrayList<String>();

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(1);
		setContentView(R.layout.site_resources);
		
		//Animation animationRightIn = AnimationUtils.loadAnimation(this, R.anim.resource_item_left);
		//Animation animationRightOut = AnimationUtils.loadAnimation(this, R.anim.resource_item_right);
		
		String str = getIntent().getExtras().getString("vertical");
		hasSlideshows = getIntent().getExtras().getString("hasSlideshows");
		hasQuizzes = getIntent().getExtras().getString("hasQuizzes");
		titleTextView = (TextView) findViewById(R.id.vertical_title);
		listResources = (ListView) findViewById(R.id.list_resources);
		databaseController = new DatabaseController(getApplicationContext());

		listResources.setOnItemClickListener(this);
		
		if (hasSlideshows.equals("true")){
			resources.add("Slideshows");
		} 
		
		if (hasQuizzes.equals("true")){
			resources.add("Quizzes");
		}

		try {
			DatabaseController.initDatabase();
			vertical = this.databaseController.getVerticalById(str);
			titleTextView.setText(vertical.getVerticalName());
			titleTextView.setVisibility(View.VISIBLE);
		} catch (ActiveRecordException localActiveRecordException) {
			while (true)
				localActiveRecordException.printStackTrace();
		}
		
		CustomSiteResourceAdapter customSiteResourceAdapter = new CustomSiteResourceAdapter(this, resources);
		listResources.setAdapter(customSiteResourceAdapter);
		
		//slideshowImage.startAnimation(animationRightIn);
		//quizImage.startAnimation(animationRightOut);
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
		if (paramInt == 0)
			slideshowPressed();
		else
			quizPressed();
	}
	
	public void slideshowPressed(){
		Intent localIntent = new Intent(this, SiteSlideshowsActivity.class);
		localIntent.putExtra("vertical", vertical.getVerticalId());
		startActivity(localIntent);
	}
	
	public void quizPressed(){
		Intent localIntent = new Intent(this, SiteQuizzesActivity.class);
		localIntent.putExtra("vertical", vertical.getVerticalId());
		startActivity(localIntent);
	}
}
