package com.healthcentral.activity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.kroz.activerecord.ActiveRecordException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.healthcentral.common.SimpleGestureFilter;
import com.healthcentral.common.SimpleGestureListener;
import com.heathcentral.model.Slide;
import com.heathcentral.model.Slideshow;
import com.heathcentral.service.DatabaseController;

public class SlideshowDetails extends Activity implements SimpleGestureListener {

	private ImageView imageView1;
	private TextView textTitle1;
	private TextView textContent1;

	private ImageView imageView2;
	private TextView textTitle2;
	private TextView textContent2;

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

	@SuppressWarnings({ "static-access" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.slideshow_details);

		detector = new SimpleGestureFilter(this, this);

		imageView1 = (ImageView) this.findViewById(R.id.slideshowImage1);
		textTitle1 = (TextView) this.findViewById(R.id.slideshow_title1);
		textContent1 = (TextView) this.findViewById(R.id.slideshow_article1);

		imageView2 = (ImageView) this.findViewById(R.id.slideshowImage2);
		textTitle2 = (TextView) this.findViewById(R.id.slideshow_title2);
		textContent2 = (TextView) this.findViewById(R.id.slideshow_article2);
		pageCounter = (TextView) findViewById(R.id.question_counter);

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

		textTitle1.setText(slides.get(0).getTitle());
		textContent1.setText(Html.fromHtml(slides.get(0).getText()));
		textContent1.setMovementMethod(LinkMovementMethod.getInstance());

		pageCounter.setText("Page 1 of " + String.valueOf(slides.size()));

		ByteArrayInputStream imageStream = new ByteArrayInputStream(slides.get(0).getImage());
		Bitmap theImage = BitmapFactory.decodeStream(imageStream);
		imageView1.setImageBitmap(theImage);

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
					textContent1.setText(Html.fromHtml(slides.get(slideIndex - 1).getText()));
					textTitle1.setText(slides.get(slideIndex - 1).getTitle());

					ByteArrayInputStream imageStream = new ByteArrayInputStream(slides.get(slideIndex - 1).getImage());
					Bitmap theImage = BitmapFactory.decodeStream(imageStream);

					imageView1.setImageBitmap(theImage);

					isSlide1 = true;
				} else {
					textContent2.setText(Html.fromHtml(slides.get(
							slideIndex - 1).getText()));
					textTitle2.setText(slides.get(slideIndex - 1).getTitle());

					ByteArrayInputStream imageStream = new ByteArrayInputStream(
							slides.get(slideIndex - 1).getImage());
					Bitmap theImage = BitmapFactory.decodeStream(imageStream);

					imageView2.setImageBitmap(theImage);

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
					textContent1.setText(Html.fromHtml(slides.get(slideIndex - 1).getText()));
					textTitle1.setText(slides.get(slideIndex - 1).getTitle());

					ByteArrayInputStream imageStream = new ByteArrayInputStream(
							slides.get(slideIndex - 1).getImage());
					Bitmap theImage = BitmapFactory.decodeStream(imageStream);

					imageView1.setImageBitmap(theImage);

					isSlide1 = true;
				} else {
					textContent2.setText(Html.fromHtml(slides.get(slideIndex - 1).getText()));
					textTitle2.setText(slides.get(slideIndex - 1).getTitle());

					ByteArrayInputStream imageStream = new ByteArrayInputStream(
							slides.get(slideIndex - 1).getImage());
					Bitmap theImage = BitmapFactory.decodeStream(imageStream);

					imageView2.setImageBitmap(theImage);
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
