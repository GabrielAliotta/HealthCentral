package com.heathcentral.model;

import org.kroz.activerecord.ActiveRecordBase;
import org.kroz.activerecord.Database;

public class Vertical extends ActiveRecordBase {

	public String verticalId;
	public String verticalName;
	public String verticalImageURL;
	public byte[] verticalImage;
	public String hasSlideshows;
	public String hasQuizzes;

	public Vertical() {
	}

	public Vertical(Database db) {
		super(db);
	}

	public Vertical(String verticalId, String verticalName,
			String verticalImageURL, byte[] verticalImage,
			String hasSlideshows, String hasQuizzes) {
		this.verticalId = verticalId;
		this.verticalName = verticalName;
		this.verticalImageURL = verticalImageURL;
		this.verticalImage = verticalImage;
		this.hasSlideshows = hasSlideshows;
		this.hasQuizzes = hasQuizzes;
	}

	public String getVerticalId() {
		return verticalId;
	}

	public void setVerticalId(String verticalId) {
		this.verticalId = verticalId;
	}

	public String getVerticalName() {
		return verticalName;
	}

	public void setVerticalName(String verticalName) {
		this.verticalName = verticalName;
	}

	public String getVerticalImageURL() {
		return verticalImageURL;
	}

	public void setVerticalImageURL(String verticalImageURL) {
		this.verticalImageURL = verticalImageURL;
	}

	public byte[] getVerticalImage() {
		return verticalImage;
	}

	public void setVerticalImage(byte[] verticalImage) {
		this.verticalImage = verticalImage;
	}

	public String getHasSlideshows() {
		return hasSlideshows;
	}

	public void setHasSlideshows(String hasSlideshows) {
		this.hasSlideshows = hasSlideshows;
	}

	public String getHasQuizzes() {
		return hasQuizzes;
	}

	public void setHasQuizzes(String hasQuizzes) {
		this.hasQuizzes = hasQuizzes;
	}

}
