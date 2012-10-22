package com.heathcentral.service;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.heathcentral.model.Quiz;

public class QuizHandler extends DefaultHandler{
	private List<Quiz> quizzes;
	private Quiz quizActual;
	private StringBuilder sbTexto;
	
	public List<Quiz> getQuizzes(){
		return quizzes;
	}
	
	@Override
    public void characters(char[] ch, int start, int length)
                   throws SAXException {
 
        super.characters(ch, start, length);
 
        if (this.quizActual != null)
        	sbTexto.append(ch, start, length);
    }
 
    @Override
    public void endElement(String uri, String localName, String name)
                   throws SAXException {
 
        super.endElement(uri, localName, name);
 
        if (this.quizActual != null) {
 
            if (localName.equals("title")) {
            	quizActual.setTitle(sbTexto.toString());
            } else if (localName.equals("text")) {
            	quizActual.setText(sbTexto.toString());
            } else if (localName.equals("image")) {
            	quizActual.setImageUrl(sbTexto.toString());
            } else if (localName.equals("vertical-id")) {
            	quizActual.setVertical(sbTexto.toString());
            } else if (localName.equals("id")) {
            	quizActual.setQuizId(sbTexto.toString());
            } else if (localName.equals("nextQuiz")) {
            	quizActual.setNextQuizId(sbTexto.toString());
            } else if (localName.equals("item")) {
                quizzes.add(quizActual);
            }
 
            sbTexto.setLength(0);
        }
    }
 
    @Override
    public void startDocument() throws SAXException {
 
        super.startDocument();
 
        quizzes = new ArrayList<Quiz>();
        sbTexto = new StringBuilder();
    }
 
    @Override
    public void startElement(String uri, String localName,
                   String name, Attributes attributes) throws SAXException {
 
        super.startElement(uri, localName, name, attributes);
 
        if (localName.equals("item")) {
        	quizActual = new Quiz();
        }
    }
}
