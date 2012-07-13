package com.heathcentral.model;

import org.kroz.activerecord.ActiveRecordBase;
import org.kroz.activerecord.Database;

public class SlideshowImage extends ActiveRecordBase{

	public String slideshowId;
	public String slideshowOrder;
	public byte[] image;	
	
	public SlideshowImage() {
	}
	
	public SlideshowImage(Database db) {
		super(db);
	}
	
	public SlideshowImage(String slideshowId, String order, byte[] image){
		this.slideshowId = slideshowId;
		this.slideshowOrder = order;
		this.image = image;
	}

	public String getSlideshowId() {
		return slideshowId;
	}

	public void setSlideshowId(String slideshowId) {
		this.slideshowId = slideshowId;
	}

	public String getOrder() {
		return slideshowOrder;
	}

	public void setOrder(String order) {
		this.slideshowOrder = order;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}
}
