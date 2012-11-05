package com.heathcentral.model;

import org.kroz.activerecord.ActiveRecordBase;
import org.kroz.activerecord.Database;

public class Slide extends ActiveRecordBase {

	public String slideshowId;
	public String id;
	public byte[] image;
	public String text;
	public String title;

	public Slide() {
	}

	public Slide(Database db) {
		super(db);
	}

	public Slide(String slideshowId, String id, String title, String text, byte[] image) {
		this.slideshowId = slideshowId;
		this.id = id;
		this.title = title;
		this.text = text;
		this.image = image;
	}

	public String getSlideshowId() {
		return slideshowId;
	}

	public void setSlideshowId(String slideshowId) {
		this.slideshowId = slideshowId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
