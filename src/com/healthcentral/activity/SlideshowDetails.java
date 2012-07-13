package com.healthcentral.activity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.kroz.activerecord.ActiveRecordException;

import com.healthcenter.common.SimpleGestureFilter;
import com.heathcentral.model.Site;
import com.heathcentral.model.SlideshowImage;
import com.heathcentral.service.DatabaseController;
import com.heathcentral.service.GetSlideshowImagesTask;
import com.healthcenter.common.SimpleGestureListener;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class SlideshowDetails extends Activity implements SimpleGestureListener {

	private ImageView imageView;
	private TextView textTitle;
	private TextView textContent;
	private TextView slidePage;
	DatabaseController databaseController;
	Site site = new Site();
	Integer imageIndex;

	List <String> titles = new ArrayList<String>();
	List <String> contents = new ArrayList<String>();
	
	List<SlideshowImage> slideshowImages;

	private SimpleGestureFilter detector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.slideshow_details);
		
		detector = new SimpleGestureFilter(this, this);		

		imageView = (ImageView) this.findViewById(R.id.slideshowImage);
		textTitle = (TextView) this.findViewById(R.id.slideshow_title);
		textContent = (TextView) this.findViewById(R.id.slideshow_article);
		slidePage = (TextView) this.findViewById(R.id.slideshow_page);
		
		databaseController = new DatabaseController(getApplicationContext());
		try {
			databaseController.initDatabase();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}

		String slideId = getIntent().getExtras().getString("SlideshowId");
		site = databaseController.getSiteById(slideId);
		
		new GetSlideshowImagesTask(this, databaseController, site).execute();
	}
	
	public void updateList(){
		slideshowImages = databaseController.getSlideshowImagesById(site.id);
		
		Pattern titlePattern = Pattern.compile("<b>(.*?)</b>");
		Matcher titleMatcher = titlePattern.matcher(site.getContents());
		
		while (titleMatcher.find()) {
			titles.add(titleMatcher.group(1));
		}
		
		Pattern p = Pattern.compile("<p><p>(.*?)</p></p>", Pattern.DOTALL);
		Matcher m = p.matcher(site.getContents());
		
		while (m.find()) {
			contents.add(m.group(1));
		}
		
		imageIndex = 1;
		
		textContent.setText(Html.fromHtml(contents.get(0)));
		textTitle.setText(titles.get(0));
		textContent.setMovementMethod(LinkMovementMethod.getInstance());
		
		slidePage.setText(String.valueOf(imageIndex)+ "/" + String.valueOf(contents.size()));

		ByteArrayInputStream imageStream = new ByteArrayInputStream(slideshowImages.get(1).getImage());
		Bitmap theImage = BitmapFactory.decodeStream(imageStream);

		imageView.setImageBitmap(theImage);
	}

	//Interface Methods
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent me) {
		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	public void onSwipe(int direction) {

		switch (direction) {

		case SimpleGestureFilter.SWIPE_RIGHT:
			if (imageIndex > 1) {
				imageIndex--;
				
				slidePage.setText(String.valueOf(imageIndex)+ "/" + String.valueOf(contents.size()));
				
				textContent.setText(Html.fromHtml(contents.get(imageIndex - 1)));
				textTitle.setText(titles.get(imageIndex - 1));

				ByteArrayInputStream imageStream = new ByteArrayInputStream(slideshowImages.get(imageIndex).getImage());
				Bitmap theImage = BitmapFactory.decodeStream(imageStream);

				imageView.setImageBitmap(theImage);
			}
			break;
		case SimpleGestureFilter.SWIPE_LEFT:
			if (imageIndex < slideshowImages.size() - 2) {
				imageIndex++;
				
				slidePage.setText(String.valueOf(imageIndex)+ "/" + String.valueOf(contents.size()));

				textContent.setText(Html.fromHtml(contents.get(imageIndex - 1)));
				textTitle.setText(titles.get(imageIndex - 1));
				
				ByteArrayInputStream imageStream = new ByteArrayInputStream(slideshowImages.get(imageIndex).getImage());
				Bitmap theImage = BitmapFactory.decodeStream(imageStream);

				imageView.setImageBitmap(theImage);
			}
			break;
		}
	}

	public void onDoubleTap() {
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		try {
			if (databaseController.getIsOpenDatabase() != false) {
				databaseController.closeConnection();
			}
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}
	}

}
