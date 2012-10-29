package com.heathcentral.service;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;

import com.healthcentral.activity.SlideshowDetails;
import com.heathcentral.model.Slideshow;
import com.heathcentral.model.SlideshowImage;

public class GetSlideshowImagesTask extends AsyncTask<String, Void, Boolean> {
	DatabaseController databaseController;
	private ProgressDialog dialog;
	Slideshow site;
	List<Message> titles;
	private Context context;

	public GetSlideshowImagesTask(Context context, DatabaseController databaseController, Slideshow site) {
		this.databaseController = databaseController;
		this.context = context;
		this.site = site;
		dialog = new ProgressDialog(context);
		dialog.setCancelable(false);
	}

	protected void onPreExecute() {
		this.dialog.setMessage("Loading Slideshow");
		this.dialog.show();
	}

	@Override
	protected void onPostExecute(final Boolean success) {

		((SlideshowDetails) context).updateList();

		if (dialog.isShowing())
			dialog.dismiss();
	}

	protected Boolean doInBackground(final String... args) {

		if (!databaseController.SlideshowImagesLoaded(site.getId())) {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(site.url);
			HttpResponse response = null;
			try {
				response = client.execute(request);
			} catch (ClientProtocolException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			InputStream in;
			StringBuilder str = null;
			try {
				in = response.getEntity().getContent();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(in));
				str = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					str.append(line);
				}
				in.close();
			} catch (IllegalStateException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			Pattern p = Pattern.compile("http://www.healthcentral.com/common/cf/files/cache/([^\"]*)");
			Matcher m = p.matcher(str.toString());

			List<String> imageUrls = new ArrayList<String>();
			while (m.find()) {
				imageUrls.add(m.group());
			}
			for (String image : imageUrls) {
				InputStream is = null;
				ByteArrayBuffer baf = null;
				if (image != null) {
					try {
						URL url = new URL(image);
						is = url.openConnection().getInputStream();
						BufferedInputStream bis = new BufferedInputStream(is,
								128);
						baf = new ByteArrayBuffer(128);
						int current = 0;
						while ((current = bis.read()) != -1) {
							baf.append((byte) current);
						}
						SlideshowImage slideshowImage = new SlideshowImage(
								site.getId(), String.valueOf(imageUrls
										.indexOf(image)), baf.toByteArray());
						databaseController.saveSlideshowImage(slideshowImage);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return true;
	}
}
