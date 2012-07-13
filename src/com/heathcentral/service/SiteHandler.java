package com.heathcentral.service;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.heathcentral.model.Site;


public class SiteHandler extends DefaultHandler{
    private List<Site> sites;
    private Site siteActual;
    private StringBuilder sbTexto;
 
    public List<Site> getSites(){
        return sites;
    }
 
    @Override
    public void characters(char[] ch, int start, int length)
                   throws SAXException {
 
        super.characters(ch, start, length);
 
        if (this.siteActual != null)
        	sbTexto.append(ch, start, length);
    }
 
    @Override
    public void endElement(String uri, String localName, String name)
                   throws SAXException {
 
        super.endElement(uri, localName, name);
 
        if (this.siteActual != null) {
 
            if (localName.equals("type")) {
            	siteActual.setType(sbTexto.toString());
            } else if (localName.equals("title")) {
            	siteActual.setTitle(sbTexto.toString());
            } else if (localName.equals("blurb")) {
            	siteActual.setBlurb(sbTexto.toString());
            } else if (localName.equals("url")) {
            	siteActual.setUrl(sbTexto.toString());
            } else if (localName.equals("imageUrl")) {
            	siteActual.setImageUrl(sbTexto.toString());
            } else if (localName.equals("published-at")) {
            	siteActual.setPublished(sbTexto.toString());
            } else if (localName.equals("updated-at")) {
            	siteActual.setUpdated(sbTexto.toString());
            } else if (localName.equals("vertical-id")) {
            	siteActual.setVertical(sbTexto.toString());
            } else if (localName.equals("id")) {
            	siteActual.setId(sbTexto.toString());
            } else if (localName.equals("contents")) {
            	siteActual.setContents(sbTexto.toString());
            } else if (localName.equals("item")) {
                sites.add(siteActual);
            }
 
            sbTexto.setLength(0);
        }
    }
 
    @Override
    public void startDocument() throws SAXException {
 
        super.startDocument();
 
        sites = new ArrayList<Site>();
        sbTexto = new StringBuilder();
    }
 
    @Override
    public void startElement(String uri, String localName,
                   String name, Attributes attributes) throws SAXException {
 
        super.startElement(uri, localName, name, attributes);
 
        if (localName.equals("item")) {
            siteActual = new Site();
        }
    }
}
