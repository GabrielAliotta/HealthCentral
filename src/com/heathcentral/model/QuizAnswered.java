package com.heathcentral.model;

import java.io.Serializable;

public class QuizAnswered implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1398287680082338287L;
	int questionId;
	String question;
	String questionText;
	String answered;
	String correctAnswer;
	String nextQuizId;
	boolean valid;
	
	public QuizAnswered(int questionId, String question, String questionText, String answered, String correctAnswer, boolean valid, String nextQuizid){
		this.questionId = questionId;
		this.question = question;
		this.questionText = questionText;
		this.answered = answered;
		this.correctAnswer = correctAnswer;
		this.valid = valid;
		this.nextQuizId = nextQuizid;
	}

	public String getNextQuizId() {
		return nextQuizId;
	}


	public void setNextQuizId(String nextQuizId) {
		this.nextQuizId = nextQuizId;
	}


	public String getQuestionText() {
		return questionText;
	}



	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswered() {
		return answered;
	}

	public void setAnswered(String answered) {
		this.answered = answered;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public boolean isValid() {
		return valid;
	}

}
