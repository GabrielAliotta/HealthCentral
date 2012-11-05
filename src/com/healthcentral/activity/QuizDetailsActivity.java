package com.healthcentral.activity;

import java.util.ArrayList;
import java.util.List;

import org.kroz.activerecord.ActiveRecordException;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.ScrollingMovementMethod;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.heathcentral.model.QuizAnswered;
import com.heathcentral.model.QuizQuestion;
import com.heathcentral.model.QuizQuestionAnswer;
import com.heathcentral.service.DatabaseController;


public class QuizDetailsActivity extends Activity{
	
	private DatabaseController databaseController;
	private List<QuizQuestion> questions = new ArrayList<QuizQuestion>();
	private List<QuizQuestionAnswer> answers = new ArrayList<QuizQuestionAnswer>();
	private List<QuizAnswered> answered = new ArrayList<QuizAnswered>();
	private ArrayAdapter<String> adapter;
	private TextView quizTitle;
	private TextView quizQuestion;
	private ListView questionAnswers;
	private LinearLayout quizScore;
	private TextView correctScore;
	private ImageView correctScoreImage;
	private TextView incorrectScore;
	private ImageView incorrectScoreImage;
	private TextView youAnswered;
	private TextView correctAnswerTitle;
	private TextView correctAnswer;
	private TextView quizText;
	private Button submitBtn;
	private List<String> answersString = new ArrayList<String>();
	private boolean resultMode = true;
	int questionCounter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.quiz_details);
		
		quizTitle = (TextView) findViewById(R.id.quiz_title);
		quizQuestion = (TextView) findViewById(R.id.quiz_question);
		questionAnswers = (ListView) findViewById(R.id.list_answers);
		quizScore = (LinearLayout) findViewById(R.id.quiz_score_layout);
		correctScore = (TextView) findViewById(R.id.correct_score);
		correctScoreImage = (ImageView) findViewById(R.id.correct_image);
		incorrectScore = (TextView) findViewById(R.id.incorrect_score);
		incorrectScoreImage = (ImageView) findViewById(R.id.incorrect_image);
		correctAnswerTitle = (TextView) findViewById(R.id.correctAnswerTitle);
		youAnswered = (TextView) findViewById(R.id.youAnswered);
		correctAnswer = (TextView) findViewById(R.id.correctAnswer);
		quizText = (TextView) findViewById(R.id.quizText);
		submitBtn = (Button) findViewById(R.id.quiz_submit_btn);
		
		quizText.setMovementMethod(new ScrollingMovementMethod());
		
		databaseController = new DatabaseController(getApplicationContext());
		try {
			databaseController.initDatabase();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		
		questionCounter = 0;

		String quizId = getIntent().getExtras().getString("QuizId");
		questions = databaseController.getQuestionsByVertical(quizId);		

		adapter = new ArrayAdapter<String>(this, R.layout.list_answer_item,answersString);
		 
		questionAnswers.setAdapter(adapter);
		
		updateQuestion();
	}
	
	public void buttonPressed (View view){	
		
		if(questionAnswers.getCheckedItemPosition() == -1){
			Toast.makeText(this, "You must answer the question", Toast.LENGTH_SHORT).show();
			return;
		}
		
		if (resultMode){

			int validAnswer = 0;
			int answeredValid = 0;
			int answeredInvalid = 0;
			
			correctScoreImage.setImageResource(R.drawable.checkmark);
			incorrectScoreImage.setImageResource(android.R.drawable.ic_delete);
			for(int i = 0; i < answers.size(); i++){
				if (answers.get(i).getValid().equals("true")){
					validAnswer = i;
					break;
				}
			}
			
			int answerChecked = questionAnswers.getCheckedItemPosition();
			questionAnswers.setVisibility(View.GONE);
			submitBtn.setText("Next");
			
			quizQuestion.setVisibility(View.GONE);
			if (answers.get(answerChecked).getValid().equals("true")){
				answered.add(new QuizAnswered(questionCounter, Html.fromHtml(questions.get(questionCounter).getQuestion()).toString().trim(),
						Html.fromHtml(questions.get(questionCounter).getAnswerText()).toString().trim(), answers.get(answerChecked).getTitle(), answers.get(validAnswer).getTitle(), true));
				for(QuizAnswered answ : answered){
					if(answ.isValid()){
						answeredValid ++;
					} else {
						answeredInvalid ++;
					}
				}
				quizScore.setVisibility(View.VISIBLE);
				correctAnswer.setVisibility(View.VISIBLE);
				correctAnswer.setTextSize(55);
				correctAnswer.setText("Correct!");
				quizText.setVisibility(View.VISIBLE);
				quizText.setText(Html.fromHtml(questions.get(questionCounter).getAnswerText()));
				correctScore.setText(String.valueOf(answeredValid));
				incorrectScore.setText(String.valueOf(answeredInvalid));
			} else {
				answered.add(new QuizAnswered(questionCounter, Html.fromHtml(questions.get(questionCounter).getQuestion()).toString().trim(),
						Html.fromHtml(questions.get(questionCounter).getAnswerText()).toString().trim(), answers.get(answerChecked).getTitle(), answers.get(validAnswer).getTitle(), false));
				for(QuizAnswered answ : answered){
					if(answ.isValid()){
						answeredValid ++;
					} else {
						answeredInvalid ++;
					}
				}
				SpannableStringBuilder youAnsweredString = new SpannableStringBuilder("You answered: " + answers.get(answerChecked).getTitle());
				final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD); 
				youAnsweredString.setSpan(bss, 0, 12, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
				quizScore.setVisibility(View.VISIBLE);
				youAnswered.setVisibility(View.VISIBLE);
				youAnswered.setText(youAnsweredString);
				correctAnswerTitle.setVisibility(View.VISIBLE);
				correctAnswer.setVisibility(View.VISIBLE);
				correctAnswer.setText(answers.get(validAnswer).getTitle());
				quizText.setVisibility(View.VISIBLE);
				quizText.setText(Html.fromHtml(questions.get(questionCounter).getAnswerText()));
				correctScore.setText(String.valueOf(answeredValid));
				incorrectScore.setText(String.valueOf(answeredInvalid));
			}			
		
			resultMode = false;
			
		} else {
			questionCounter ++;
			
			//TODO no more questions
			if(questionCounter >= questions.size()){
				return;
			}
			
			quizQuestion.setVisibility(View.VISIBLE);
			quizScore.setVisibility(View.GONE);
			youAnswered.setVisibility(View.GONE);
			correctAnswer.setVisibility(View.GONE);
			correctAnswerTitle.setVisibility(View.GONE);
			quizText.setVisibility(View.GONE);
			correctAnswer.setTextSize(30);
			questionAnswers.setVisibility(View.VISIBLE);
			submitBtn.setText("Submit");
			questionAnswers.setItemChecked(-1, true);
			resultMode = true;
			updateQuestion();
		}
	}
	
	private void updateQuestion(){
		answersString.clear();
		
		quizTitle.setText(questions.get(questionCounter).getQuizTitle());
		quizQuestion.setText(Html.fromHtml(questions.get(questionCounter).getQuestion()).toString().trim());
		
		answers = databaseController.getAnswersByQuestionId(String.valueOf(questions.get(questionCounter).getID()));
		
		for (QuizQuestionAnswer answer : answers){
			answersString.add(answer.getTitle());
		}
		
		adapter.notifyDataSetChanged();
	}
}
