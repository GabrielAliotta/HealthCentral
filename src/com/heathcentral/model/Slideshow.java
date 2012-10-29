package com.heathcentral.model;

import org.kroz.activerecord.ActiveRecordBase;
import org.kroz.activerecord.Database;

public class Slideshow extends ActiveRecordBase {

	public String type;
	public String friendlyTitle;
	public String title;
	public String blurb;
	public String url;
	public String imageUrl;
	public byte[] image;
	public String published;
	public String updated;
	public String vertical;
	public String id;
	public String contents;

	public Slideshow() {
	}

	public Slideshow(Database db) {
		super(db);
	}

	public Slideshow(String type, String friendlyTitle, String title,
			String blurb, String url, String imageUrl, String published,
			String updated, String vertical, String id, String contents) {
		this.type = type;
		this.friendlyTitle = friendlyTitle;
		this.title = title;
		this.blurb = blurb;
		this.url = url;
		this.imageUrl = imageUrl;
		this.published = published;
		this.updated = updated;
		this.vertical = vertical;
		this.id = id;
		this.contents = contents;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getVertical() {
		return vertical;
	}

	public void setVertical(String vertical) {
		this.vertical = vertical;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getFriendlyTitle() {
		return friendlyTitle;
	}

	public void setFriendlyTitle(String friendlyTitle) {
		this.friendlyTitle = friendlyTitle;
	}

}
