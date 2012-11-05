package com.heathcentral.model;

import org.kroz.activerecord.ActiveRecordBase;
import org.kroz.activerecord.Database;

public class Slideshow extends ActiveRecordBase {

	public String id;
	public String title;
	public String blurb;
	public String imageUrl;
	public byte[] image;
	public String vertical;

	public Slideshow() {
	}

	public Slideshow(Database db) {
		super(db);
	}

	public Slideshow(String id, String title, String blurb,
			String imageUrl, byte[] image, String vertical) {
		this.id = id;
		this.title = title;
		this.blurb = blurb;
		this.imageUrl = imageUrl;
		this.image = image;
		this.vertical = vertical;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBlurb() {
		return blurb;
	}

	public void setBlurb(String blurb) {
		this.blurb = blurb;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getVertical() {
		return vertical;
	}

	public void setVertical(String vertical) {
		this.vertical = vertical;
	}

}
