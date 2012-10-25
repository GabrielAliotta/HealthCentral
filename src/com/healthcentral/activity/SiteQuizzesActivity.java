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

import com.healthcentral.common.CustomQuizAdapter;
import com.heathcentral.model.Quiz;
import com.heathcentral.service.DatabaseController;
import com.heathcentral.service.GetQuizzesTask;

public class SiteQuizzesActivity extends Activity implements
		AdapterView.OnItemClickListener {
	CustomQuizAdapter customAdapter;
	DatabaseController databaseController;
	private ListView mySitesListView;
	List<Quiz> quizzes = new ArrayList<Quiz>();
	private TextView titleTextView;
	String str = null;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(1);
		setContentView(R.layout.main);
		str = getIntent().getExtras().getString("site");
		this.mySitesListView = ((ListView) findViewById(R.id.list_sites));
		this.titleTextView = ((TextView) findViewById(R.id.title));
		this.databaseController = new DatabaseController(getApplicationContext());
		try {
			DatabaseController.initDatabase();
			new GetQuizzesTask(this, this.databaseController, str).execute(new String[0]);
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
		//localIntent.putExtra("SlideshowId",	((Site) this.quizzes.get(paramInt)).id);
		startActivity(localIntent);
	}
	
	public void updateList(){
		this.quizzes = this.databaseController.getQuizzesByVertical(str);
		this.titleTextView.setText(((Quiz) this.quizzes.get(0)).getFriendlyTitle());
		this.customAdapter = new CustomQuizAdapter(this, this.quizzes);
		this.mySitesListView.setOnItemClickListener(this);
		this.mySitesListView.setAdapter(this.customAdapter);
	}
}