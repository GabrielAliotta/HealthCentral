package com.heathcentral.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.healthcentral.activity.HealthCentralActivity;
import com.healthcentral.utils.Image;
import com.healthcentral.utils.JSONParser;
import com.heathcentral.model.Vertical;

public class GetVerticalsTask extends AsyncTask<String, Void, Boolean> {
	private DatabaseController databaseController;
	private ProgressDialog dialog;
	private Context context;
	private static final String VERTICAL_URL = "http://thcn-db01.bar.tpg.corp/index.php/tools/verticals/";
	
	public GetVerticalsTask(Context context, DatabaseController databaseController) {
		this.databaseController = databaseController;
		this.context = context;
		dialog = new ProgressDialog(context);
		dialog.setCancelable(false);
	}

	protected void onPreExecute() {
		this.dialog.setMessage("Loading Verticals");
		this.dialog.show();
	}

	@Override
	protected void onPostExecute(final Boolean success) {
		((HealthCentralActivity) context).updateList();
		if (dialog.isShowing())
			dialog.dismiss();
	}

	protected Boolean doInBackground(final String... args) {
		JSONArray verticals = null;
		JSONParser parser = new JSONParser(VERTICAL_URL, this.context, "verticals.txt");
		JSONObject json = parser.getJSON();
		
		try{
			verticals = json.getJSONArray("verticals");
			String verticalImageURL = "http://www.healthcentral.com/about/wp-content/uploads/2009/06/apple_150x150.gif";
			byte[] verticalImage = Image.getImageFromURL(verticalImageURL);
			
			for(int iVertical = 0; iVertical < verticals.length(); iVertical++) {
				JSONObject jsonVerticalObject = verticals.getJSONObject(iVertical).getJSONObject("vertical");
				
				String verticalId = jsonVerticalObject.getString("id");
				
				if(! databaseController.VerticalLoaded(verticalId)) {
					String verticalName = jsonVerticalObject.getString("name");
					String hasSlideshows = jsonVerticalObject.getString("hasSlideshows");
					String hasQuizzes = jsonVerticalObject.getString("hasQuizzes");
					
					Vertical vertical = new Vertical(verticalId, verticalName, verticalImageURL, verticalImage, hasSlideshows, hasQuizzes);
					databaseController.saveVertical(vertical);
				}
			}
		}catch(JSONException e) {
			e.printStackTrace();
		}
		
		return true;
	}
}
