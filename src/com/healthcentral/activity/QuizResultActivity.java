package com.healthcentral.activity;

import java.util.ArrayList;
import java.util.List;

import org.kroz.activerecord.ActiveRecordException;

import com.heathcentral.model.QuizAnswered;
import com.heathcentral.service.DatabaseController;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QuizResultActivity extends Activity {

	private DatabaseController databaseController;
	private List<QuizAnswered> answered = new ArrayList<QuizAnswered>();

	private LinearLayout results;
	final String align = "<head><style>* {margin:0;padding:0;font-size:16; text-align:justify;color:A3A3A3}</style></head>";
	
	private TextView verticalTitleTextView;
	private TextView quizTitle;
	private TextView percentageResults;
	private TextView correctScore;
	private ImageView correctScoreImage;
	private TextView incorrectScore;
	private ImageView incorrectScoreImage;
	private String validAnswers;
	private String invalidAnswers;
	private boolean nextQuizLoaded;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.quiz_results);

		results = (LinearLayout) findViewById(R.id.list_results);
		verticalTitleTextView = (TextView) findViewById(R.id.vertical_title);
		quizTitle = (TextView) findViewById(R.id.quiz_title);
		percentageResults = (TextView) findViewById(R.id.percentage_results);
		correctScore = (TextView) findViewById(R.id.correct_score);
		correctScoreImage = (ImageView) findViewById(R.id.correct_image);
		incorrectScore = (TextView) findViewById(R.id.incorrect_score);
		incorrectScoreImage = (ImageView) findViewById(R.id.incorrect_image);
		
		verticalTitleTextView.setText(getIntent().getStringExtra("verticalTitle"));
		quizTitle.setText(getIntent().getStringExtra("quizTitle"));
		
		databaseController = new DatabaseController(getApplicationContext());
		try {
			databaseController.initDatabase();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}

		answered = (List<QuizAnswered>) getIntent().getSerializableExtra("answered");
		validAnswers = getIntent().getStringExtra("validAnswers");
		invalidAnswers = getIntent().getStringExtra("invalidAnswers");

		percentageResults.setText(String.format("%.0f%% Correct",(Float.valueOf(validAnswers) / answered.size()) * 100));
		
		correctScoreImage.setImageResource(R.drawable.checkmark);
		incorrectScoreImage.setImageResource(android.R.drawable.ic_delete);
		
		correctScore.setText(validAnswers);
		incorrectScore.setText(invalidAnswers);

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		for(QuizAnswered answer : answered) {
			
			View rowView = inflater.inflate(R.layout.quiz_result_item, results, false);
			
			String questionIdString = String.valueOf(answer.getQuestionId() +1) + ". ";
			
			ImageView validImage = (ImageView) rowView.findViewById(R.id.quiz_result_image);
			if(answer.isValid()){
				validImage.setImageResource(R.drawable.checkmark);
			} else {
				validImage.setImageResource(android.R.drawable.ic_delete);
			}
			
			TextView question = (TextView) rowView.findViewById(R.id.question);
			question.setText(questionIdString + answer.getQuestion());
			TextView answerView = (TextView) rowView.findViewById(R.id.answer);
			answerView.setText("Correct Answer: " + answer.getCorrectAnswer());
			WebView resultText = (WebView) rowView.findViewById(R.id.result_text);
			resultText.loadData(align + answer.getQuestionText().trim(),"text/html","utf-8");
			results.addView(rowView);
		}
		
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
