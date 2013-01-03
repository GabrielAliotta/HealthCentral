package com.healthcentral.activity;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.kroz.activerecord.ActiveRecordException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.heathcentral.model.QuizAnswered;
import com.heathcentral.model.QuizQuestion;
import com.heathcentral.model.QuizQuestionAnswer;
import com.heathcentral.service.DatabaseController;


public class QuizDetailsActivity extends Activity implements OnItemClickListener{
	
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
	private TextView correctAnswer;
	private WebView quizText;
	private ProgressBar progressBar;
	private TextView questionCounterBar;
	private TextView verticalTitleTextView;
	private Button submitBtn;
	private List<String> answersString = new ArrayList<String>();
	private boolean resultMode = true;
	private String nextQuizId;
	private String verticalTitle;
	private Drawable quizImage;
	int questionCounter;
	int validAnswer = 0;
	int answeredValid = 0;
	int answeredInvalid = 0;
	
	final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.parseColor("#CC4848"));	
	final ForegroundColorSpan fcsCorrectAnswer = new ForegroundColorSpan(Color.parseColor("#8dc73f"));
	
	final String align = "<head><style>* {margin:0;padding:4;font-size:16; text-align:justify;color:007fb2}</style></head>"; 
	
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
		youAnswered = (TextView) findViewById(R.id.youAnswered);
		correctAnswer = (TextView) findViewById(R.id.correctAnswer);
		quizText = (WebView) findViewById(R.id.quizText);
		progressBar = (ProgressBar) findViewById(R.id.progress_bar);
		questionCounterBar = (TextView) findViewById(R.id.question_counter);
		verticalTitleTextView = (TextView) findViewById(R.id.vertical_title);
		submitBtn = (Button) findViewById(R.id.quiz_submit_btn);
		quizText.setFocusable(false);
		quizText.setBackgroundColor(0);
		
		databaseController = new DatabaseController(getApplicationContext());
		try {
			databaseController.initDatabase();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
		
		questionCounter = 0;

		String quizId = getIntent().getExtras().getString("QuizId");
		nextQuizId = getIntent().getExtras().getString("nextQuizId");
		verticalTitle = getIntent().getExtras().getString("verticalTitle");
		questions = databaseController.getQuestionsByVertical(quizId);
		progressBar.setMax(questions.size());
		adapter = new ArrayAdapter<String>(this, R.layout.list_answer_item,answersString);
		 
		questionAnswers.setAdapter(adapter);
		questionAnswers.setOnItemClickListener(this);
		quizTitle.setText(questions.get(questionCounter).getQuizTitle());
		
		verticalTitleTextView.setText(verticalTitle);
		
		updateQuestion();
	} 
	
	public void buttonPressed (View view){	
		
		if (resultMode){
			SubmitButtonPressed();
		} else {
			nextButtonPressed();
		}
	}
	
	private void updateQuestion(){
		answersString.clear();
		
		ByteArrayInputStream imageStream = new ByteArrayInputStream(questions.get(questionCounter).getImage());
		quizImage = new BitmapDrawable(BitmapFactory.decodeStream(imageStream));
		quizImage.setAlpha(80);
		
		questionCounterBar.setText("Question " + String.valueOf(questionCounter + 1) + " of " + String.valueOf(questions.size()));
		
		quizQuestion.setText(Html.fromHtml(questions.get(questionCounter).getQuestion()).toString().trim());
		
		answers = databaseController.getAnswersByQuestionId(String.valueOf(questions.get(questionCounter).getID()));
		
		for (QuizQuestionAnswer answer : answers){
			answersString.add(answer.getTitle());
		}
		
		adapter.notifyDataSetChanged();
	}
	
	private void SubmitButtonPressed(){
		
		validAnswer = 0;
		answeredValid = 0;
		answeredInvalid = 0;
		
		progressBar.incrementProgressBy(1);
		
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
		if(questionCounter +1 >= questions.size()){
			submitBtn.setText("See results");
		} else {
			submitBtn.setText("Next");
		}			
		
		quizQuestion.setVisibility(View.GONE);
		if (answers.get(answerChecked).getValid().equals("true")){
			answered.add(new QuizAnswered(questionCounter, Html.fromHtml(questions.get(questionCounter).getQuestion()).toString().trim(),
					Html.fromHtml(questions.get(questionCounter).getAnswerText()).toString().trim(), answers.get(answerChecked).getTitle(), answers.get(validAnswer).getTitle(), true, nextQuizId));
			for(QuizAnswered answ : answered){
				if(answ.isValid()){
					answeredValid ++;
				} else {
					answeredInvalid ++;
				}
			}
			SpannableStringBuilder correctString = new SpannableStringBuilder("Correct!");
			correctString.setSpan(fcsCorrectAnswer, 0, 8, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			quizScore.setVisibility(View.VISIBLE);
			correctAnswer.setVisibility(View.VISIBLE);
			correctAnswer.setTextSize(40);
			correctAnswer.setText(correctString);			
			quizText.loadData(align + questions.get(questionCounter).getAnswerText(),"text/html","utf-8");
			quizText.setVisibility(View.VISIBLE);
			correctScore.setText(String.valueOf(answeredValid));
			incorrectScore.setText(String.valueOf(answeredInvalid));
		} else {
			answered.add(new QuizAnswered(questionCounter, Html.fromHtml(questions.get(questionCounter).getQuestion()).toString().trim(),
					Html.fromHtml(questions.get(questionCounter).getAnswerText()).toString().trim(), answers.get(answerChecked).getTitle(), answers.get(validAnswer).getTitle(), false, nextQuizId));
			for(QuizAnswered answ : answered){
				if(answ.isValid()){
					answeredValid ++;
				} else {
					answeredInvalid ++;
				}
			}
			SpannableStringBuilder youAnsweredString = new SpannableStringBuilder("You answered: " + answers.get(answerChecked).getTitle());				
			youAnsweredString.setSpan(fcs, 13, youAnsweredString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			SpannableStringBuilder correctAnswerString = new SpannableStringBuilder("The correct answer is: " + answers.get(validAnswer).getTitle());			
			correctAnswerString.setSpan(fcsCorrectAnswer, 22, correctAnswerString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			quizScore.setVisibility(View.VISIBLE);
			youAnswered.setVisibility(View.VISIBLE);
			youAnswered.setText(youAnsweredString);
			correctAnswer.setVisibility(View.VISIBLE);
			correctAnswer.setText(correctAnswerString);
			quizText.loadData(align + questions.get(questionCounter).getAnswerText(),"text/html","utf-8");
			quizText.setVisibility(View.VISIBLE);
			correctScore.setText(String.valueOf(answeredValid));
			incorrectScore.setText(String.valueOf(answeredInvalid));
		}			
	
		resultMode = false;
	}
	
	private void nextButtonPressed(){
		
		submitBtn.setEnabled(false);
		questionCounter ++;
		if(questionCounter >= questions.size()){
			
			Intent localIntent = new Intent(this, QuizResultActivity.class);
			localIntent.putExtra("verticalTitle", verticalTitle);
			localIntent.putExtra("quizTitle", questions.get(0).getQuizTitle());
			localIntent.putExtra("answered", (Serializable) answered);
			localIntent.putExtra("validAnswers", String.valueOf(answeredValid));
			localIntent.putExtra("invalidAnswers", String.valueOf(answeredInvalid));
			startActivity(localIntent);
			finish();
			return;
		}		
		
		quizQuestion.setVisibility(View.VISIBLE);
		quizScore.setVisibility(View.GONE);
		youAnswered.setVisibility(View.GONE);
		correctAnswer.setVisibility(View.GONE);
		correctAnswer.setTextSize(18);
		quizText.setVisibility(View.GONE);
		questionAnswers.setVisibility(View.VISIBLE);
		submitBtn.setText("Submit");
		questionAnswers.setItemChecked(-1, true);
		resultMode = true;
		updateQuestion();
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		submitBtn.setEnabled(true);
	}

}
