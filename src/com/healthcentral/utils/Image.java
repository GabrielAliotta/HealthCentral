package com.healthcentral.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.util.ByteArrayBuffer;

public class Image {

	public static byte[] getImageFromURL(String imageURL) {		
		InputStream is = null;
		ByteArrayBuffer baf = null;
		byte[] image = null;
		if (imageURL != null) {
			try {
				URL url = new URL(imageURL);
				is = url.openConnection().getInputStream();
				BufferedInputStream bis = new BufferedInputStream(is,
						128);
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
}
