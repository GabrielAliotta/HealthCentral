package com.heathcentral.model;

public class QuizAnswered {
	
	int questionId;
	String question;
	String questionText;
	String answered;
	String correctAnswer;
	boolean valid;
	
	public QuizAnswered(int questionId, String question, String questionText, String answered, String correctAnswer, boolean valid){
		this.questionId = questionId;
		this.question = question;
		this.questionText = questionText;
		this.answered = answered;
		this.correctAnswer = correctAnswer;
		this.valid = valid;
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
