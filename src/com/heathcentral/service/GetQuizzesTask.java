package com.heathcentral.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;

import com.healthcentral.activity.SiteQuizzesActivity;
import com.healthcentral.utils.JSONParser;
import com.heathcentral.model.QuizQuestionAnswer;
import com.heathcentral.model.Quiz;
import com.heathcentral.model.QuizQuestion;

public class GetQuizzesTask extends AsyncTask<String, Void, Boolean> {
	DatabaseController databaseController;
	private ProgressDialog dialog;
	List<Message> titles;
	private Context context;
	private String vertical;

	public GetQuizzesTask(Context context, DatabaseController databaseController, String vertical) {
		this.databaseController = databaseController;
		this.context = context;
		this.vertical = vertical;
		dialog = new ProgressDialog(context);
		dialog.setCancelable(false);
	}

	protected void onPreExecute() {
		this.dialog.setMessage("Loading Quizzes");
		this.dialog.show();
	}

	@Override
	protected void onPostExecute(final Boolean success) {

		((SiteQuizzesActivity) context).updateList();

		if (dialog.isShowing())
			dialog.dismiss();
	}

	protected Boolean doInBackground(final String... args) {

	    JSONArray quizzes = null;
	    JSONArray questions = null;
	    JSONArray answers = null;
	    
    	// url to make request
        String url = "http://thcn-db01.bar.tpg.corp/index.php/tools/hke?contentType=quiz&vertical=" + vertical + "&json=true";
        
        JSONParser jParser = new JSONParser();
 
        JSONObject json = jParser.getJSONFromUrl(url);        
 
        try {
        	quizzes = json.getJSONArray("items");
 
            for(int i = 0; i < quizzes.length(); i++){
                JSONObject quizJsonObject = quizzes.getJSONObject(i).getJSONObject("item");
 
                String quizId = quizJsonObject.getString("id");
                String description = quizJsonObject.getString("text");
                String title = quizJsonObject.getString("title");
                String imageUrl = quizJsonObject.getString("image");
                String verticalId = quizJsonObject.getString("vertical-id");                
                String nextQuizId = quizJsonObject.getString("nextQuiz"); 
 
                Quiz quizToSave = new Quiz(verticalId, quizId, title, description, imageUrl, nextQuizId);
                
                databaseController.saveQuiz(quizToSave);
                
                questions = quizJsonObject.getJSONObject("questions").getJSONArray("question");
            
                for(int e = 0; e < questions.length(); e++){
                	JSONObject questionJsonObject = questions.getJSONObject(e);
                	 
                    String questionTitle = questionJsonObject.getString("title");
                    String question = questionJsonObject.getString("text");
                    String questionImageUrl = questionJsonObject.getString("image");
                    answers = questionJsonObject.getJSONObject("answers").getJSONArray("answer");
                    
                    byte[] questionImage = new byte[]{127,-128,0};              
                    
                    QuizQuestion questionToSave = new QuizQuestion(quizId, questionTitle, question, questionImageUrl, questionImage);
                    
                    String questionId = databaseController.saveQuizQuestion(questionToSave);
                    
                    for(int a = 0; a < answers.length(); a++){
                    	JSONObject answerJsonObject = answers.getJSONObject(e);
                    	 
                        String answer = answerJsonObject.getString("title");
                        String answerValid = answerJsonObject.getString("valid");
                        
                        QuizQuestionAnswer questionAnswer = new QuizQuestionAnswer(questionId, answer, answerValid);
                        
                        databaseController.saveQuestionAnswer(questionAnswer);
                        
                    }
                }
                
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
		return true;
	}

}
