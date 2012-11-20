package com.healthcentral.activity;

import java.util.ArrayList;
import java.util.List;

import org.kroz.activerecord.ActiveRecordException;

import com.healthcentral.common.CustomQuizResultAdapter;
import com.heathcentral.model.QuizAnswered;
import com.heathcentral.service.DatabaseController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class QuizResultActivity extends Activity {

	private DatabaseController databaseController;
	private List<QuizAnswered> answered = new ArrayList<QuizAnswered>();
	private ListView results;
	private TextView percentageResult;
	private TextView correctScore;
	private TextView outOfScore;
	private String validAnswers;
	private boolean nextQuizLoaded;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.quiz_results);

		results = (ListView) findViewById(R.id.list_results);
		correctScore = (TextView) findViewById(R.id.correct_score);
		percentageResult = (TextView) findViewById(R.id.percentage_results);
		outOfScore = (TextView) findViewById(R.id.out_of_score);
		
		databaseController = new DatabaseController(getApplicationContext());
		try {
			databaseController.initDatabase();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}

		answered = (List<QuizAnswered>) getIntent().getSerializableExtra("answered");
		validAnswers = getIntent().getStringExtra("validAnswers");

		correctScore.setText(validAnswers);
		outOfScore.setText(" Out of " + String.valueOf(answered.size()));

		percentageResult.setText(String.format("%.0f%% Correct",(Float.valueOf(validAnswers) / answered.size()) * 100));

		CustomQuizResultAdapter customQuizResultAdapter = new CustomQuizResultAdapter(this, answered);
		results.setAdapter(customQuizResultAdapter);
		
		if(answered.get(0).getNextQuizId() != null)
		nextQuizLoaded = databaseController.QuizLoaded(answered.get(0).getNextQuizId());
	}

	public void nextQuizButtonPressed(View view) {
		if (nextQuizLoaded) {
			Intent localIntent = new Intent(this, QuizDetailsActivity.class);
			localIntent.putExtra("QuizId", answered.get(0).getNextQuizId());
			startActivity(localIntent);
			finish();
		} else {
			finish();
		}
	}

}
