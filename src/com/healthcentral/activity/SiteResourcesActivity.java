package com.healthcentral.activity;

import org.kroz.activerecord.ActiveRecordException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heathcentral.model.Vertical;
import com.heathcentral.service.DatabaseController;

public class SiteResourcesActivity extends Activity {

	private DatabaseController databaseController;
	private TextView titleTextView;
	private Vertical vertical;
	private String hasSlideshows;
	private String hasQuizzes;
	private ImageView slideshowImage;
	private ImageView quizImage;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(1);
		setContentView(R.layout.site_resources);
		
		Animation animationRightIn = AnimationUtils.loadAnimation(this, R.anim.resource_item_left);
		Animation animationRightOut = AnimationUtils.loadAnimation(this, R.anim.resource_item_right);
		
		((LinearLayout) findViewById(R.id.linearLayout)).setVisibility(View.GONE);
		String str = getIntent().getExtras().getString("vertical");
		hasSlideshows = getIntent().getExtras().getString("hasSlideshows");
		hasQuizzes = getIntent().getExtras().getString("hasQuizzes");
		titleTextView = (TextView) findViewById(R.id.vertical_title);
		slideshowImage = (ImageView) findViewById(R.id.slideshows_image);
		quizImage = (ImageView) findViewById(R.id.quizzes_image);
		databaseController = new DatabaseController(getApplicationContext());

		if (hasSlideshows.equals("true")){
			slideshowImage.setVisibility(View.VISIBLE);
			slideshowImage.startAnimation(animationRightIn);
		} 
		
		if (hasQuizzes.equals("true")){
			quizImage.setVisibility(View.VISIBLE);
			quizImage.startAnimation(animationRightOut);
		}

		try {
			DatabaseController.initDatabase();
			vertical = this.databaseController.getVerticalById(str);
			titleTextView.setText(vertical.getVerticalName());
			titleTextView.setVisibility(View.VISIBLE);
			((TextView) findViewById(R.id.action_activity)).setText("Select a Resource");
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
	
	public void slideshowPressed(View view){
		Intent localIntent = new Intent(this, SiteSlideshowsActivity.class);
		localIntent.putExtra("vertical", vertical.getVerticalId());
		startActivity(localIntent);
	}
	
	public void quizPressed(View view){
		Intent localIntent = new Intent(this, SiteQuizzesActivity.class);
		localIntent.putExtra("vertical", vertical.getVerticalId());
		startActivity(localIntent);
	}
}
