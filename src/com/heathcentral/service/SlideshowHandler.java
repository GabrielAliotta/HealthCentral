package com.heathcentral.service;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.heathcentral.model.Slideshow;


public class SlideshowHandler extends DefaultHandler{
    private List<Slideshow> slideshows;
    private Slideshow slideshowActual;
    private StringBuilder sbTexto;
 
    public List<Slideshow> getSlideshows(){
        return slideshows;
    }
 
    @Override
    public void characters(char[] ch, int start, int length)
                   throws SAXException {
 
        super.characters(ch, start, length);
 
        if (this.slideshowActual != null)
        	sbTexto.append(ch, start, length);
    }
 
    @Override
    public void endElement(String uri, String localName, String name)
                   throws SAXException {
 
        super.endElement(uri, localName, name);
 
        if (this.slideshowActual != null) {
 
            if (localName.equals("type")) {
            	slideshowActual.setType(sbTexto.toString());
            } else if (localName.equals("title")) {
            	slideshowActual.setTitle(sbTexto.toString());
            } else if (localName.equals("blurb")) {
            	slideshowActual.setBlurb(sbTexto.toString());
            } else if (localName.equals("url")) {
            	slideshowActual.setUrl(sbTexto.toString());
            } else if (localName.equals("imageUrl")) {
            	slideshowActual.setImageUrl(sbTexto.toString());
            } else if (localName.equals("published-at")) {
            	slideshowActual.setPublished(sbTexto.toString());
            } else if (localName.equals("updated-at")) {
            	slideshowActual.setUpdated(sbTexto.toString());
            } else if (localName.equals("vertical-id")) {
            	slideshowActual.setVertical(sbTexto.toString());
            } else if (localName.equals("id")) {
            	slideshowActual.setId(sbTexto.toString());
            } else if (localName.equals("contents")) {
            	slideshowActual.setContents(sbTexto.toString());
            } else if (localName.equals("item")) {
                slideshows.add(slideshowActual);
            }
 
            sbTexto.setLength(0);
        }
    }
 
    @Override
    public void startDocument() throws SAXException {
 
        super.startDocument();
 
        slideshows = new ArrayList<Slideshow>();
        sbTexto = new StringBuilder();
    }
 
    @Override
    public void startElement(String uri, String localName,
                   String name, Attributes attributes) throws SAXException {
 
        super.startElement(uri, localName, name, attributes);
 
        if (localName.equals("item")) {
            slideshowActual = new Slideshow();
        }
    }
}
