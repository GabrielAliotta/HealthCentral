package com.healthcentral.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import com.heathcentral.model.Quiz;
import com.heathcentral.model.Slideshow;
import com.heathcentral.service.SlideshowHandler;


public class ParseSax {
	
	private URL Url;
	 
    public ParseSax(String url)
    {
        try
        {
            this.Url = new URL(url);
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException(e);
        }
    }
 
    public List<Slideshow> parseSites()
    {
        SAXParserFactory factory = SAXParserFactory.newInstance();
 
        try
        {
            SAXParser parser = factory.newSAXParser();
            SlideshowHandler handler = new SlideshowHandler();
            parser.parse(this.getInputStream(), handler);
            return handler.getSlideshows();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
    
 
    private InputStream getInputStream()
    {
        try
        {
            return Url.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
