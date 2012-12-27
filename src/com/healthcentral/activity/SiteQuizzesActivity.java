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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthcentral.common.CustomQuizAdapter;
import com.heathcentral.model.Quiz;
import com.heathcentral.service.DatabaseController;
import com.heathcentral.service.GetQuizzesTask;

public class SiteQuizzesActivity extends Activity implements AdapterView.OnItemClickListener {
	
	private TextView titleTextView;
	private String verticalId = null;
	private CustomQuizAdapter customAdapter;
	private DatabaseController databaseController;
	private ListView mySitesListView;
	private TextView actionActivity;
	private List<Quiz> quizzes = new ArrayList<Quiz>();

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(1);
		setContentView(R.layout.main);
		
		((LinearLayout) findViewById(R.id.linearLayout)).setVisibility(View.GONE);
		verticalId = getIntent().getExtras().getString("vertical");
		mySitesListView = ((ListView) findViewById(R.id.list_verticals));
		titleTextView = ((TextView) findViewById(R.id.vertical_title));
		actionActivity = ((TextView) findViewById(R.id.action_activity));
		this.databaseController = new DatabaseController(getApplicationContext());
		try {
			DatabaseController.initDatabase();
			new GetQuizzesTask(this, this.databaseController, verticalId).execute(new String[0]);
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
		Intent localIntent = new Intent(this, QuizDetailsActivity.class);
		localIntent.putExtra("QuizId",	((Quiz) this.quizzes.get(paramInt)).getQuizId());
		localIntent.putExtra("nextQuizId",	((Quiz) this.quizzes.get(paramInt)).getNextQuizId());
		localIntent.putExtra("verticalTitle",	((Quiz) this.quizzes.get(paramInt)).getFriendlyTitle());
		startActivity(localIntent);
	}
	
	public void updateList(){
		quizzes = this.databaseController.getQuizzesByVertical(verticalId);
		titleTextView.setText(((Quiz) this.quizzes.get(0)).getFriendlyTitle());
		titleTextView.setVisibility(View.VISIBLE);
		actionActivity.setText("Select a quiz");
		actionActivity.setVisibility(View.VISIBLE);
		customAdapter = new CustomQuizAdapter(this, this.quizzes);
		mySitesListView.setOnItemClickListener(this);
		mySitesListView.setAdapter(this.customAdapter);
	}
}