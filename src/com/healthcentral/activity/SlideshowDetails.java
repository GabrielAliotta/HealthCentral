package com.healthcentral.activity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.kroz.activerecord.ActiveRecordException;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.healthcentral.common.SimpleGestureFilter;
import com.healthcentral.common.SimpleGestureListener;
import com.heathcentral.model.Slide;
import com.heathcentral.model.Slideshow;
import com.heathcentral.service.DatabaseController;

public class SlideshowDetails extends Activity implements SimpleGestureListener {

	private Button imageView1;
	private TextView slideshowMainTitle;
	private TextView textTitle;
	private WebView textContent1;

	private Button imageView2;
	private WebView textContent2;

	private boolean isSlide1;

	private DatabaseController databaseController;
	private Slideshow slideshow = new Slideshow();
	private Integer slideIndex;

	private List<Slide> slides = new ArrayList<Slide>();
	private int[] slideshowIds = null;
	private SimpleGestureFilter detector;

	private String slideshowId;
	private Integer slideshowIndex;
	private TextView pageCounter;
	
	private Bitmap theImage;
	
	final String align = "<head><style>* {margin:0;padding:8;font-size:18; text-align:justify;color:007fb2}</style></head>";

	@SuppressWarnings({ "static-access" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.slideshow_details);

		detector = new SimpleGestureFilter(this, this);

		imageView1 = (Button) findViewById(R.id.slideshowImage1);
		slideshowMainTitle = (TextView) findViewById(R.id.slideshow_main_title);
		textTitle = (TextView) findViewById(R.id.slideshow_title);
		textContent1 = (WebView) findViewById(R.id.slideshow_article1);

		imageView2 = (Button) findViewById(R.id.slideshowImage2);
		textContent2 = (WebView) findViewById(R.id.slideshow_article2);
		pageCounter = (TextView) findViewById(R.id.question_counter);
		
		textContent1.setFocusable(false);
		textContent2.setFocusable(false);

		databaseController = new DatabaseController(getApplicationContext());
		try {
			databaseController.initDatabase();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}

		slideshowId = getIntent().getExtras().getString("slideshowId");
		slideshowIndex = getIntent().getExtras().getInt("slideshowIndex");
		slideshowIds = getIntent().getExtras().getIntArray("slideshowsIds");
		slideshow = databaseController.getSlideshowById(slideshowId);

		this.updateList();
	}

	public void updateList() {
		slides = databaseController.getSlides(slideshow.getId());
		slideIndex = 1;

		slideshowMainTitle.setText(slideshow.getTitle());
		textTitle.setText(slides.get(0).getTitle());
		//textContent1.setText(Html.fromHtml(slides.get(0).getText()));
		//textContent1.setMovementMethod(LinkMovementMethod.getInstance());
		textContent1.loadData(align + slides.get(0).getText(),"text/html","utf-8");

		pageCounter.setText("Page 1 of " + String.valueOf(slides.size()));

		ByteArrayInputStream imageStream = new ByteArrayInputStream(slides.get(0).getImage());
		theImage = BitmapFactory.decodeStream(imageStream);
		imageView1.setBackgroundDrawable(new BitmapDrawable(theImage));
		//imageView1.setImageBitmap(theImage);

		isSlide1 = true;
	}

	// Interface Methods

	@Override
	public boolean dispatchTouchEvent(MotionEvent me) {
		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	public void onSwipe(int direction) {

		switch (direction) {

		case SimpleGestureFilter.SWIPE_RIGHT:
			if (slideIndex > 1) {
				slideIndex--;

				// Get the ViewFlipper from the layout
				ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);

				// Set an animation from res/anim: I pick push left in
				vf.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_in));
				vf.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_right_out));
				vf.showNext();

				pageCounter.setText("Page " + String.valueOf(slideIndex) + " of " + String.valueOf(slides.size()));

				if (!isSlide1) {
					//textContent1.setText(Html.fromHtml(slides.get(slideIndex - 1).getText()));
					textContent1.loadData(align + slides.get(slideIndex - 1).getText(),"text/html","utf-8");
					
					textTitle.setText(slides.get(slideIndex - 1).getTitle());

					ByteArrayInputStream imageStream = new ByteArrayInputStream(slides.get(slideIndex - 1).getImage());
					theImage = BitmapFactory.decodeStream(imageStream);

					imageView1.setBackgroundDrawable(new BitmapDrawable(theImage));
					//imageView1.setImageBitmap(theImage);

					isSlide1 = true;
				} else {
					//textContent2.setText(Html.fromHtml(slides.get(slideIndex - 1).getText()));
					textContent2.loadData(align + slides.get(slideIndex - 1).getText(),"text/html","utf-8");
					textTitle.setText(slides.get(slideIndex - 1).getTitle());

					ByteArrayInputStream imageStream = new ByteArrayInputStream(
							slides.get(slideIndex - 1).getImage());
					theImage = BitmapFactory.decodeStream(imageStream);

					imageView2.setBackgroundDrawable(new BitmapDrawable(theImage));
					//imageView2.setImageBitmap(theImage);

					isSlide1 = false;
				}

			}
			break;
		case SimpleGestureFilter.SWIPE_LEFT:
			if (slideIndex < slides.size()) {
				slideIndex++;

				// Get the ViewFlipper from the layout
				ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);

				// Set an animation from res/anim: I pick push left in
				vf.setInAnimation(AnimationUtils.loadAnimation(this,
						R.anim.push_left_in));
				vf.setOutAnimation(AnimationUtils.loadAnimation(this,
						R.anim.push_left_out));
				vf.showNext();

				pageCounter.setText("Page " + String.valueOf(slideIndex) + " of " + String.valueOf(slides.size()));

				if (!isSlide1) {
					//textContent1.setText(Html.fromHtml(slides.get(slideIndex - 1).getText()));
					textContent1.loadData(align + slides.get(slideIndex - 1).getText(),"text/html","utf-8");
					textTitle.setText(slides.get(slideIndex - 1).getTitle());

					ByteArrayInputStream imageStream = new ByteArrayInputStream(slides.get(slideIndex - 1).getImage());
					theImage = BitmapFactory.decodeStream(imageStream);

					imageView1.setBackgroundDrawable(new BitmapDrawable(theImage));
					//imageView1.setImageBitmap(theImage);

					isSlide1 = true;
				} else {
					//textContent2.setText(Html.fromHtml(slides.get(slideIndex - 1).getText()));
					textContent2.loadData(align + slides.get(slideIndex - 1).getText(),"text/html","utf-8");
					textTitle.setText(slides.get(slideIndex - 1).getTitle());

					ByteArrayInputStream imageStream = new ByteArrayInputStream(slides.get(slideIndex - 1).getImage());
					theImage = BitmapFactory.decodeStream(imageStream);

					imageView2.setBackgroundDrawable(new BitmapDrawable(theImage));
					//imageView2.setImageBitmap(theImage);
					isSlide1 = false;
				}
			} else if (slideIndex == slides.size()) {
				if (slideshowIndex < slideshowIds.length) {
					Intent localIntent = new Intent(this,
							SlideshowDetails.class);
					localIntent.putExtra("slideshowId",
							String.valueOf(slideshowIds[++slideshowIndex]));
					localIntent.putExtra("slideshowIndex", slideshowIndex);
					localIntent.putExtra("slideshowsIds", slideshowIds);
					startActivity(localIntent);
				}
			}
			break;
		}
	}

	public void onDoubleTap() {
	}
	
	public void slideshowImage1Pressed (View view){
		new Dialog(this, android.R.style.Theme_Light_NoTitleBar) {
		    @Override
		    public void onCreate(Bundle unused) {
		        ImageView myImage = new ImageView(getContext());
		        myImage.setImageBitmap(theImage);		        
		        setContentView(myImage);
		    }
		}.show();
	}
	
	public void slideshowImage2Pressed (View view){
		new Dialog(this, android.R.style.Theme_Light_NoTitleBar) {
		    @Override
		    public void onCreate(Bundle unused) {
		        ImageView myImage = new ImageView(getContext());
		        myImage.setImageBitmap(theImage);
		        setContentView(myImage);
		    }
		}.show();
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
