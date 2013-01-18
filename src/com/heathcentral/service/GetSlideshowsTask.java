package com.heathcentral.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.healthcentral.activity.SiteSlideshowsActivity;
import com.healthcentral.utils.Image;
import com.healthcentral.utils.JSONParser;
import com.heathcentral.model.Slide;
import com.heathcentral.model.Slideshow;

public class GetSlideshowsTask extends AsyncTask<String, Void, Boolean> {

	private static final String C5_HKE_TOOL_URL = "http://190.3.107.106/index.php/tools/mobile";
	private DatabaseController databaseController;
	private ProgressDialog dialog;
	private Context context;
	boolean hasConnection;
	boolean webServiceActive = true;
	private String verticalId;

	public GetSlideshowsTask(Context context, DatabaseController databaseController, String verticalId) {
		this.databaseController = databaseController;
		this.context = context;
		this.verticalId = verticalId;
		dialog = new ProgressDialog(context);
		dialog.setCancelable(false);
	}

	protected void onPreExecute() {
		this.dialog.setMessage("Loading Slideshows");
		this.dialog.show();
		hasConnection = isOnline();
	}

	@Override
	protected void onPostExecute(final Boolean success) {
		if(hasConnection && webServiceActive){
			((SiteSlideshowsActivity) context).updateList();
		} else if (!hasConnection && webServiceActive){
			((SiteSlideshowsActivity) context).noConnection();
		} else if(!webServiceActive){
			((SiteSlideshowsActivity) context).webServiceConnectionProblem();
		}
		
		if (dialog.isShowing())
			dialog.dismiss();
	}

	protected Boolean doInBackground(final String... args) {
		
		if(!hasConnection){
			return true;
		}
		JSONArray slideshows, slides = null;
		JSONParser jsonParser = new JSONParser(C5_HKE_TOOL_URL + "?vertical="+this.verticalId + "&contentType=slideshow", this.context, "slideshows-"+ verticalId + ".txt");
		JSONObject json = jsonParser.getJSON();
		
		if(json == null){
			webServiceActive = false;
			return true;
		}

		try {
			slideshows = json.getJSONArray("items");

			String imageUrl = "";
			
			for (int iSlideshow = 0; iSlideshow < slideshows.length(); iSlideshow++) {
				JSONObject slideshowJsonObject = slideshows.getJSONObject(iSlideshow).getJSONObject("item");
				
				String slideshowId = slideshowJsonObject.getString("id");
				slides = slideshowJsonObject.getJSONObject("slides").getJSONArray("slide");
				
				if (!databaseController.slideshowLoaded(slideshowId) && slides.length() > 0) {
					String description = slideshowJsonObject.getString("blurb");
					String title = slideshowJsonObject.getString("title");
					imageUrl = slideshowJsonObject.getString("slideshowImage");
					
					Image image = new Image(imageUrl, context);
					
					String verticalId = slideshowJsonObject.getString("vertical-id");
					
					Slideshow slideshowToSave = new Slideshow(slideshowId, title, description, imageUrl, image.getImage(), verticalId);
					databaseController.saveSlideshow(slideshowToSave);
					
					for(int iSlide = 0; iSlide < slides.length(); iSlide++) {
						JSONObject slideJsonObject = slides.getJSONObject(iSlide);
						String slideTitle = slideJsonObject.getString("title");
						String text = slideJsonObject.getString("text");						
						imageUrl = slideJsonObject.getString("image");
						
						Image slideImage = new Image(imageUrl, context);
						
						String slideId = slideJsonObject.getString("id");
						
						Slide slideToSave = new Slide(slideshowId, slideId, slideTitle, text, slideImage.getImage());
						
						databaseController.saveSlide(slideToSave);
					}
					
				}
			}
		} catch (JSONException e) {
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