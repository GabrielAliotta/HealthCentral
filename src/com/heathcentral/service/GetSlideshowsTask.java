package com.heathcentral.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.healthcentral.activity.SiteSlideshowsActivity;
import com.healthcentral.utils.Image;
import com.healthcentral.utils.JSONParser;
import com.heathcentral.model.Slide;
import com.heathcentral.model.Slideshow;

public class GetSlideshowsTask extends AsyncTask<String, Void, Boolean> {

	private static final String C5_HKE_TOOL_URL = "http://thcn-db01.bar.tpg.corp/index.php/tools/mobile";
	private DatabaseController databaseController;
	private ProgressDialog dialog;
	private Context context;
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
	}

	@Override
	protected void onPostExecute(final Boolean success) {
		((SiteSlideshowsActivity) context).updateList();
		if (dialog.isShowing())
			dialog.dismiss();
	}

	protected Boolean doInBackground(final String... args) {
		JSONArray slideshows, slides = null;
		JSONParser jsonParser = new JSONParser(C5_HKE_TOOL_URL + "?vertical="+this.verticalId + "&contentType=slideshow", this.context, "slideshows.txt");
		JSONObject json = jsonParser.getJSON();

		try {
			slideshows = json.getJSONArray("items");

			String imageUrl = "http://www.healthcentral.com/about/wp-content/uploads/2009/06/apple_150x150.gif";
			byte[] image = Image.getImageFromURL(imageUrl);
			
			for (int iSlideshow = 0; iSlideshow < slideshows.length(); iSlideshow++) {
				JSONObject slideshowJsonObject = slideshows.getJSONObject(iSlideshow).getJSONObject("item");
				String slideshowId = slideshowJsonObject.getString("id");
				slides = slideshowJsonObject.getJSONObject("slides").getJSONArray("slide");
				
				if (!databaseController.slideshowLoaded(slideshowId) && slides.length() > 0) {
					String description = slideshowJsonObject.getString("blurb");
					String title = slideshowJsonObject.getString("title");
//					String imageUrl = "http://www.healthcentral.com/about/wp-content/uploads/2009/06/apple_150x150.gif";
//					byte[] image = this.getImage(imageUrl);
					String verticalId = slideshowJsonObject.getString("vertical-id");
					
					Slideshow slideshowToSave = new Slideshow(slideshowId, title, description, imageUrl, image, verticalId);
					databaseController.saveSlideshow(slideshowToSave);
					
					for(int iSlide = 0; iSlide < slides.length(); iSlide++) {
						JSONObject slideJsonObject = slides.getJSONObject(iSlide);
						String slideTitle = slideJsonObject.getString("title");
						String text = slideJsonObject.getString("text");
//						String slideImageUrl = "http://www.healthcentral.com/about/wp-content/uploads/2009/06/apple_150x150.gif";
//						byte[] slideImage = this.getImage(slideImageUrl);
						String slideId = slideJsonObject.getString("id");
						
						Slide slideToSave = new Slide(slideshowId, slideId, slideTitle, text, image);
						
						databaseController.saveSlide(slideToSave);
					}
					
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return true;
	}
}