package com.healthcentral.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;

import com.healthcentral.activity.R;

public class Image {

	private String url;
	private Context context;

	public Image(String url, Context context) {
		this.url = url;
		this.context = context;
	}

	public byte[] getImage() {
		Boolean isLocalEnv = Boolean.parseBoolean(this.context
				.getString(R.string.app_local_env));
		return (isLocalEnv) ? this.getImageFromTxt(context, url) : this
				.getImageFromURL(url);
	}

	private byte[] getImageFromURL(String imageURL) {
		InputStream is = null;
		ByteArrayBuffer baf = null;
		byte[] image = null;
		if (imageURL != null) {
			try {
				URL url = new URL(imageURL);
				is = url.openConnection().getInputStream();
				if (is == null) {
					AssetManager am = context.getAssets();
					is = am.open("apple_150x150.gif");
				}
				BufferedInputStream bis = new BufferedInputStream(is, 128);
				baf = new ByteArrayBuffer(128);
				int current = 0;
				while ((current = bis.read()) != -1) {
					baf.append((byte) current);
				}
				image = baf.toByteArray();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return image;
	}

	private byte[] getImageFromTxt(Context context, String assetsTxt) {

		int slashIndex = assetsTxt.lastIndexOf('/');
		String fileName = assetsTxt.substring(slashIndex + 1);

		AssetManager am = context.getAssets();
		InputStream is = null;
		ByteArrayBuffer baf = null;
		byte[] image = null;
		if (assetsTxt != null) {
			try {
				is = am.open(fileName);
				BufferedInputStream bis = new BufferedInputStream(is, 128);
				baf = new ByteArrayBuffer(128);
				int current = 0;
				while ((current = bis.read()) != -1) {
					baf.append((byte) current);
				}
				image = baf.toByteArray();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {

				try {
					is = am.open("apple_150x150.gif");
					BufferedInputStream bis = new BufferedInputStream(is, 128);
					baf = new ByteArrayBuffer(128);
					int current = 0;
					while ((current = bis.read()) != -1) {
						baf.append((byte) current);
					}
					image = baf.toByteArray();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}

		return image;
	}
}
