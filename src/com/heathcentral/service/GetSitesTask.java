package com.heathcentral.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.apache.http.util.ByteArrayBuffer;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Message;

import com.healthcentral.activity.HealthCentralActivity;
import com.healthcentral.utils.ParseSax;
import com.heathcentral.model.Site;

public class GetSitesTask extends AsyncTask<String, Void, Boolean> {
	DatabaseController databaseController;
	private ProgressDialog dialog;
	List<Message> titles;
	private Context context;

	public GetSitesTask(Context context, DatabaseController databaseController) {
		this.databaseController = databaseController;
		this.context = context;
		dialog = new ProgressDialog(context);
		dialog.setCancelable(false);
	}

	protected void onPreExecute() {
		this.dialog.setMessage("Loading Sites");
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
				ParseSax saxparser = new ParseSax("http://c5admin.hcaws.net/index.php/tools/hke?pageSize=100&pageNum="
								+ i + "&contentType=slideshow");
				List<Site> recivedSites = saxparser.parseSites();
				for (Site site : recivedSites) {
					
					
					
 				InputStream is = null;
					ByteArrayBuffer baf = null;
					if (site.imageUrl != null && !site.imageUrl.equals("DEFAULT_IMAGE_URL")) {
						try {
							URL url = new URL(site.imageUrl);
							is = url.openConnection().getInputStream();
							BufferedInputStream bis = new BufferedInputStream(is, 20000);
							baf = new ByteArrayBuffer(20000);
							int current = 0;
							while ((current = bis.read()) != -1) {
								baf.append((byte) current);
							}
							
							ByteArrayInputStream imageStream = new ByteArrayInputStream(baf.toByteArray());
							Bitmap imageRaw = BitmapFactory.decodeStream(imageStream);
							if (imageRaw != null) {
								Bitmap theImage = Bitmap.createScaledBitmap(imageRaw, 150, 150, false);
								ByteArrayOutputStream baos = new ByteArrayOutputStream();
								theImage.compress(Bitmap.CompressFormat.PNG, 0, baos);

								site.image = baos.toByteArray();
							} else
								site.image = baf.toByteArray();
							
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					databaseController.saveSite(site);
				}
				i++;
			}
		}
		return true;
	}
	
}
