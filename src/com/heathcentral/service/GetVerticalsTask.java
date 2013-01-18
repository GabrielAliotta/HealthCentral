package com.heathcentral.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.healthcentral.activity.HealthCentralActivity;
import com.healthcentral.utils.JSONParser;
import com.heathcentral.model.Vertical;

public class GetVerticalsTask extends AsyncTask<String, Void, Boolean> {
	private DatabaseController databaseController;
	private ProgressDialog dialog;
	private Context context;
	boolean hasConnection;
	boolean webServiceActive = true;
	private static final String VERTICAL_URL = "http://190.3.107.106/index.php/tools/verticals/";
	
	public GetVerticalsTask(Context context, DatabaseController databaseController) {
		this.databaseController = databaseController;
		this.context = context;
		dialog = new ProgressDialog(context);
		dialog.setCancelable(false);
	}

	protected void onPreExecute() {
		this.dialog.setMessage("Loading");
		this.dialog.show();
		hasConnection = isOnline();
	}

	@Override
	protected void onPostExecute(final Boolean success) {
		if(hasConnection && webServiceActive){
			((HealthCentralActivity) context).updateList();
		} else if (!hasConnection && webServiceActive){
			((HealthCentralActivity) context).noConnection();
		} else if(!webServiceActive){
			((HealthCentralActivity) context).webServiceConnectionProblem();
		}
		
		if (dialog.isShowing())
			dialog.dismiss();
	}

	protected Boolean doInBackground(final String... args) {
		
		if(!hasConnection){
			return true;
		}
		JSONArray verticals = null;
		JSONParser parser = new JSONParser(VERTICAL_URL, this.context, "verticals.txt");
		JSONObject json = parser.getJSON();
		
		if(json == null){
			webServiceActive = false;
			return true;
		}
		
		try{
			verticals = json.getJSONArray("verticals");
			//String verticalImageURL = "http://www.healthcentral.com/about/wp-content/uploads/2009/06/apple_150x150.gif";
			
			for(int iVertical = 0; iVertical < verticals.length(); iVertical++) {
				JSONObject jsonVerticalObject = verticals.getJSONObject(iVertical).getJSONObject("vertical");
				
				String verticalId = jsonVerticalObject.getString("id");
				
				if(! databaseController.VerticalLoaded(verticalId)) {
					String verticalName = jsonVerticalObject.getString("name");
					//String verticalImageURL = jsonVerticalObject.getString("image");
					String hasSlideshows = jsonVerticalObject.getString("hasSlideshows");
					String hasQuizzes = jsonVerticalObject.getString("hasQuizzes");
					
					//Image image = new Image(verticalImageURL, context);
					
					Vertical vertical = new Vertical(verticalId, verticalName, "", new byte[]{}, hasSlideshows, hasQuizzes);
					databaseController.saveVertical(vertical);
				}
			}
		}catch(JSONException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager)  context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
	        return true;
	    }
	    return false;
	}
}