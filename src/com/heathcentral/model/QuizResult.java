package com.heathcentral.model;

import org.kroz.activerecord.ActiveRecordBase;
import org.kroz.activerecord.Database;

public class QuizResult extends ActiveRecordBase {

	public String quizId;
	public String range;
	public String value;
	
	public QuizResult() {
	}

	public QuizResult(Database db) {
		super(db);
	}

	public QuizResult(String quizId, String range, String value) {
		this.quizId = quizId;
		this.range = range;
		this.value = value;
	}

	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
