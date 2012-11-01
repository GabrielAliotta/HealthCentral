package com.heathcentral.model;

import org.kroz.activerecord.ActiveRecordBase;
import org.kroz.activerecord.Database;

public class QuizQuestion extends ActiveRecordBase{

	public String quizId;
	public String quizTitle;
	public String answerText;
	public String question;
	public String imageUrl;
	public byte[] image;
	
	public QuizQuestion(){		
	}
	
	public QuizQuestion(Database db){
		super(db);		
	}
	
	public QuizQuestion(String quizId, String quizTitle, String answerText, String question, String imageUrl, byte[] image){
		this.quizId = quizId;
		this.quizTitle = quizTitle;
		this.answerText = answerText;
		this.question = question;
		this.imageUrl = imageUrl;
		this.image = image;
	}

	public String getQuizTitle() {
		return quizTitle;
	}

	public void setQuizTitle(String quizTitle) {
		this.quizTitle = quizTitle;
	}

	public String getAnswerText() {
		return answerText;
	}

	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}

	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}

	public String getTitle() {
		return answerText;
	}

	public void setTitle(String title) {
		this.answerText = title;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
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

}
