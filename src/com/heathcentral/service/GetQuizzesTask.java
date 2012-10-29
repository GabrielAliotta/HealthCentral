package com.heathcentral.service;

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
import com.heathcentral.model.QuizLearnMoreLink;
import com.heathcentral.model.QuizQuestionAnswer;
import com.heathcentral.model.Quiz;
import com.heathcentral.model.QuizQuestion;
import com.heathcentral.model.QuizResult;

public class GetQuizzesTask extends AsyncTask<String, Void, Boolean> {
	DatabaseController databaseController;
	private ProgressDialog dialog;
	List<Message> titles;
	private Context context;
	private String vertical;

	public GetQuizzesTask(Context context,
			DatabaseController databaseController, String vertical) {
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
 
		JSONArray quizzes, questions, answers, textResults, learnMoreLinks = null;
		
		// url to make request
		String url = "http://thcn-db01.bar.tpg.corp/index.php/tools/hke?contentType=quiz&vertical="
				+ vertical + "&json=true";

		JSONParser jParser = new JSONParser();

		JSONObject json = jParser.getJSONFromUrl(url);

		try {
			quizzes = json.getJSONArray("items");

			for (int iQuiz = 0; iQuiz < quizzes.length(); iQuiz++) {
				JSONObject quizJsonObject = quizzes.getJSONObject(iQuiz).getJSONObject("item");

				String quizId = quizJsonObject.getString("id");

				if (!databaseController.QuizLoaded(quizId)) {

					String description = quizJsonObject.getString("text");
					String title = quizJsonObject.getString("title");
//					String imageUrl = quizJsonObject.getString("image");
					String imageUrl = "http://www.healthcentral.com/about/wp-content/uploads/2009/06/apple_150x150.gif";
					String verticalId = quizJsonObject.getString("vertical-id");
					String nextQuizId = quizJsonObject.getString("nextQuiz");

					Quiz quizToSave = new Quiz(verticalId, "",quizId, title, description, imageUrl, nextQuizId);

					databaseController.saveQuiz(quizToSave);

					questions = quizJsonObject.getJSONObject("questions").getJSONArray("question");

					for (int iQuestion = 0; iQuestion < questions.length(); iQuestion++) {
						JSONObject questionJsonObject = questions.getJSONObject(iQuestion);

						String questionTitle = questionJsonObject.getString("title");
						String question = questionJsonObject.getString("text");
//						String questionImageUrl = questionJsonObject.getString("image");
						String questionImageUrl = "http://www.healthcentral.com/about/wp-content/uploads/2009/06/apple_150x150.gif";
						answers = questionJsonObject.getJSONObject("answers").getJSONArray("answer");

						byte[] questionImage = new byte[] { 127, -128, 0 };

						QuizQuestion questionToSave = new QuizQuestion(quizId, questionTitle, question, questionImageUrl, questionImage);

						String questionId = databaseController.saveQuizQuestion(questionToSave);

						for (int iAnswer = 0; iAnswer < answers.length(); iAnswer++) {
							JSONObject answerJsonObject = answers.getJSONObject(iQuestion);

							String answer = answerJsonObject.getString("title");
							String answerValid = answerJsonObject.getString("valid");

							QuizQuestionAnswer questionAnswer = new QuizQuestionAnswer(questionId, answer, answerValid);

							databaseController.saveQuestionAnswer(questionAnswer);
						}
					}

					textResults = quizJsonObject.getJSONObject("textResults").getJSONArray("textResult");
					
					for(int iTextResult = 0; iTextResult < textResults.length(); iTextResult++) {
						JSONObject textResultJsonObject = textResults.getJSONObject(iTextResult);
						String range = textResultJsonObject.getString("range");
						String value = textResultJsonObject.getString("value");
						
						QuizResult quizResult = new QuizResult(quizId, range, value);
						databaseController.saveQuizTextResult(quizResult);
					}
					
					learnMoreLinks = quizJsonObject.getJSONObject("learnMoreLinks").getJSONArray("learnMoreLink");
					
					for(int iLearnMoreLink = 0; iLearnMoreLink < learnMoreLinks.length(); iLearnMoreLink++){
						JSONObject learnMoreLinkJsonObject = textResults.getJSONObject(iLearnMoreLink);
						String lTitle = learnMoreLinkJsonObject.getString("title");
						String link = learnMoreLinkJsonObject.getString("link");
						QuizLearnMoreLink quizLearnMoreLink = new QuizLearnMoreLink(quizId, lTitle, link);
						databaseController.saveQuizLearnMoreLink(quizLearnMoreLink);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return true;
	}

}
