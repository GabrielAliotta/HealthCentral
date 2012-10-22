package com.heathcentral.service;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;

import com.healthcentral.activity.HealthCentralActivity;
import com.heathcentral.model.Quiz;

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

		((HealthCentralActivity) context).updateList();

		if (dialog.isShowing())
			dialog.dismiss();
	}

	protected Boolean doInBackground(final String... args) {

		if (!databaseController.sitesLoaded()) {
			int i = 0;

			while (i != 5) {
				ParseSax saxparser = new ParseSax(
						"http://thcn-db01.bar.tpg.corp/index.php/tools/hke?contentType=quiz&vertical="
								+ vertical + "&json=true");
				List<Quiz> recivedQuizzes = saxparser.parseQuizzes();
				for (Quiz quiz : recivedQuizzes) {

					databaseController.saveQuiz(quiz);
				}
				i++;
			}
		}
		return true;
	}

}
