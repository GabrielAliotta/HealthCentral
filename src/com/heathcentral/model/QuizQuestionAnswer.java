package com.heathcentral.model;

import org.kroz.activerecord.ActiveRecordBase;
import org.kroz.activerecord.Database;

public class QuizQuestionAnswer extends ActiveRecordBase{
	
	public String questionId;
	public String title;
	public String valid;
	
	public QuizQuestionAnswer(){		
	}
	
	public QuizQuestionAnswer(Database db) {
		super(db);
	}	

	public QuizQuestionAnswer(String questionId, String title, String valid){
		this.questionId = questionId;
		this.title = title;
		this.valid = valid;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

}
