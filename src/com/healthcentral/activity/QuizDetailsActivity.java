package com.healthcentral.activity;

import java.util.ArrayList;
import java.util.List;

import org.kroz.activerecord.ActiveRecordException;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.heathcentral.model.QuizQuestion;
import com.heathcentral.model.QuizQuestionAnswer;
import com.heathcentral.service.DatabaseController;


public class QuizDetailsActivity extends Activity{
	
	private DatabaseController databaseController;
	private List<QuizQuestion> questions = new ArrayList<QuizQuestion>();
	private List<QuizQuestionAnswer> answers = new ArrayList<QuizQuestionAnswer>();
	private TextView quizTitle;
	private TextView quizQuestion;
	private ListView questionAnswers;
	private TextView quizScore;
	private TextView youAnswered;
	private TextView correctAnswer;
	private TextView quizText;
	private Button submitBtn;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.quiz_details);
		
		quizTitle = (TextView) findViewById(R.id.quiz_title);
		quizQuestion = (TextView) findViewById(R.id.quiz_question);
		questionAnswers = (ListView) findViewById(R.id.list_answers);
		quizScore = (TextView) findViewById(R.id.quizScore);
		youAnswered = (TextView) findViewById(R.id.youAnswered);
		correctAnswer = (TextView) findViewById(R.id.correctAnswer);
		quizText = (TextView) findViewById(R.id.quizText);
		submitBtn = (Button) findViewById(R.id.quiz_submit_btn);
		
		
		
		databaseController = new DatabaseController(getApplicationContext());
		try {
			databaseController.initDatabase();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}

		String quizId = getIntent().getExtras().getString("QuizId");
		questions = databaseController.getQuestionsByVertical(quizId);		
		answers = databaseController.getAnswersByQuestionId(String.valueOf(questions.get(0).getID()));
		
		quizTitle.setText(questions.get(0).getQuizTitle());
		quizQuestion.setText(Html.fromHtml(questions.get(0).getQuestion()).toString().trim());
		
		List<String> answersString = new ArrayList<String>();
		for (QuizQuestionAnswer answer : answers){
			answersString.add(answer.getTitle());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_answer_item,answersString);
		 
		questionAnswers.setAdapter(adapter);
		
	}
	
	public void buttonPressed (View view){
		int answered = questionAnswers.getCheckedItemPosition();
	}
}
