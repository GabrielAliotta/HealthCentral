package com.healthcentral.activity;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.kroz.activerecord.ActiveRecordException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MotionEvent;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.healthcentral.common.SimpleGestureFilter;
import com.healthcentral.common.SimpleGestureListener;
import com.heathcentral.model.Slideshow;
import com.heathcentral.model.SlideshowImage;
import com.heathcentral.service.DatabaseController;
import com.heathcentral.service.GetSlideshowImagesTask;

public class SlideshowDetails extends Activity implements SimpleGestureListener {

	private ImageView imageView1;
	private TextView textTitle1;
	private TextView textContent1;

	private ImageView imageView2;
	private TextView textTitle2;
	private TextView textContent2;

	private boolean isSlide1;

	private TextView slidePage;
	private DatabaseController databaseController;
	private Slideshow slideshow = new Slideshow();
	private Integer imageIndex;

	private List<String> titles = new ArrayList<String>();
	private List<String> contents = new ArrayList<String>();
	private List<SlideshowImage> slideshowImages;

	private SimpleGestureFilter detector;

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

		slidePage = (TextView) this.findViewById(R.id.slideshow_page);

		databaseController = new DatabaseController(getApplicationContext());
		try {
			databaseController.initDatabase();
		} catch (ActiveRecordException e) {
			e.printStackTrace();
		}

		String slideId = getIntent().getExtras().getString("SlideshowId");
		slideshow = databaseController.getSiteById(slideId);

		new GetSlideshowImagesTask(this, databaseController, slideshow).execute();
	}

	public void updateList() {
		slideshowImages = databaseController.getSlideshowImagesById(slideshow.id);

		Pattern titlePattern = Pattern.compile("<b>(.*?)</b>");
		Matcher titleMatcher = titlePattern.matcher(slideshow.getContents());

		while (titleMatcher.find()) {
			titles.add(titleMatcher.group(1));
		}

		Pattern p = Pattern.compile("<p><p>(.*?)</p></p>", Pattern.DOTALL);
		Matcher m = p.matcher(slideshow.getContents());

		while (m.find()) {
			contents.add(m.group(1));
		}

		imageIndex = 1;

		textContent1.setText(Html.fromHtml(contents.get(0)));
		textTitle1.setText(titles.get(0));
		textContent1.setMovementMethod(LinkMovementMethod.getInstance());

		slidePage.setText(String.valueOf(imageIndex) + "/"
				+ String.valueOf(contents.size()));

		ByteArrayInputStream imageStream = new ByteArrayInputStream(
				slideshowImages.get(1).getImage());
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
			if (imageIndex > 1) {
				imageIndex--;

				// Get the ViewFlipper from the layout
				ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);

				// Set an animation from res/anim: I pick push left in
				vf.setInAnimation(AnimationUtils.loadAnimation(this,
						R.anim.push_right_in));
				vf.setOutAnimation(AnimationUtils.loadAnimation(this,
						R.anim.push_right_out));
				vf.showNext();

				slidePage.setText(String.valueOf(imageIndex) + "/"
						+ String.valueOf(contents.size()));

				if (!isSlide1) {
					textContent1.setText(Html.fromHtml(contents
							.get(imageIndex - 1)));
					textTitle1.setText(titles.get(imageIndex - 1));

					ByteArrayInputStream imageStream = new ByteArrayInputStream(
							slideshowImages.get(imageIndex).getImage());
					Bitmap theImage = BitmapFactory.decodeStream(imageStream);

					imageView1.setImageBitmap(theImage);

					isSlide1 = true;
				} else {
					textContent2.setText(Html.fromHtml(contents
							.get(imageIndex - 1)));
					textTitle2.setText(titles.get(imageIndex - 1));

					ByteArrayInputStream imageStream = new ByteArrayInputStream(
							slideshowImages.get(imageIndex).getImage());
					Bitmap theImage = BitmapFactory.decodeStream(imageStream);

					imageView2.setImageBitmap(theImage);

					isSlide1 = false;
				}

			}
			break;
		case SimpleGestureFilter.SWIPE_LEFT:
			if (imageIndex < slideshowImages.size() - 2) {
				imageIndex++;

				// Get the ViewFlipper from the layout
				ViewFlipper vf = (ViewFlipper) findViewById(R.id.details);

				// Set an animation from res/anim: I pick push left in
				vf.setInAnimation(AnimationUtils.loadAnimation(this,
						R.anim.push_left_in));
				vf.setOutAnimation(AnimationUtils.loadAnimation(this,
						R.anim.push_left_out));
				vf.showNext();

				slidePage.setText(String.valueOf(imageIndex) + "/"
						+ String.valueOf(contents.size()));

				if (!isSlide1) {
					textContent1.setText(Html.fromHtml(contents
							.get(imageIndex - 1)));
					textTitle1.setText(titles.get(imageIndex - 1));

					ByteArrayInputStream imageStream = new ByteArrayInputStream(
							slideshowImages.get(imageIndex).getImage());
					Bitmap theImage = BitmapFactory.decodeStream(imageStream);

					imageView1.setImageBitmap(theImage);

					isSlide1 = true;
				} else {

					textContent2.setText(Html.fromHtml(contents
							.get(imageIndex - 1)));
					textTitle2.setText(titles.get(imageIndex - 1));

					ByteArrayInputStream imageStream = new ByteArrayInputStream(
							slideshowImages.get(imageIndex).getImage());
					Bitmap theImage = BitmapFactory.decodeStream(imageStream);

					imageView2.setImageBitmap(theImage);
					isSlide1 = false;
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
