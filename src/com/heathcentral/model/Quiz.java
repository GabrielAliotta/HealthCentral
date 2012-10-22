package com.heathcentral.model;

import org.kroz.activerecord.ActiveRecordBase;
import org.kroz.activerecord.Database;

public class Quiz extends ActiveRecordBase{
	
	public String vertical;
	public String quizId;
	public String title;
	public String text;
	public String imageUrl;
	public String nextQuizId;
	
	public Quiz(){		
	}
	
	public Quiz(Database db){
		super(db);		
	}
	
	public Quiz(String vertical, String quizId, String title, String text, String imageUrl, String nextQuizId){
		this.vertical = vertical;
		this.quizId = quizId;
		this.title = title;
		this.text = text;
		this.imageUrl = imageUrl;
		this.nextQuizId = nextQuizId;		
	}

	public String getVertical() {
		return vertical;
	}

	public void setVertical(String vertical) {
		this.vertical = vertical;
	}

	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getNextQuizId() {
		return nextQuizId;
	}

	public void setNextQuizId(String nextQuizId) {
		this.nextQuizId = nextQuizId;
	}	
}
