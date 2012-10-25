package com.heathcentral.model;

import org.kroz.activerecord.ActiveRecordBase;
import org.kroz.activerecord.Database;

public class QuizLearnMoreLink extends ActiveRecordBase {

	public String quizId;
	public String title;
	public String link;

	public QuizLearnMoreLink() {
	}

	public QuizLearnMoreLink(Database db) {
		super(db);
	}

	public QuizLearnMoreLink(String quizId, String title, String link) {
		this.quizId = quizId;
		this.title = title;
		this.link = link;
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

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

}
