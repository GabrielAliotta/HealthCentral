package com.healthcentral.common;

import java.io.ByteArrayInputStream;
import java.util.List;

import com.healthcentral.activity.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heathcentral.model.Slide;

public class CustomPagerAdapter extends PagerAdapter {
	
	private Context context;
	private LayoutInflater inflater;
	final String align = "<head><style>* {margin:0;padding-left:10;padding-right:4; text-align:justify;font-size:16;color:006d98}</style></head>";
	private List<Slide> slides;
	
	public CustomPagerAdapter(Context context, List<Slide> slides){
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.context = context;
		this.slides = slides;
	}
	
	@Override
	public int getCount() {
		return slides.size();
	}

    /**
     * Create the page for the given position.  The adapter is responsible
     * for adding the view to the container given here, although it only
     * must ensure this is done by the time it returns from
     * {@link #finishUpdate()}.
     *
     * @param container The containing View in which the page will be shown.
     * @param position The page position to be instantiated.
     * @return Returns an Object representing the new page.  This does not
     * need to be a View, but can be some other container of the page.
     */
	@Override
	public Object instantiateItem(View collection, int position) {
		
		View rowView = inflater.inflate(R.layout.slide_pager_view, null, false);
		
		TextView slideTitle = (TextView) rowView.findViewById(R.id.slideshow_title);
		slideTitle.setText(String.valueOf(position +1) + ". " + slides.get(position).getTitle());
		
		ImageView slideImage = (ImageView) rowView.findViewById(R.id.slideshowImage);
		ByteArrayInputStream imageStream = new ByteArrayInputStream(slides.get(position).getImage());
		slideImage.setImageDrawable(new BitmapDrawable(BitmapFactory.decodeStream(imageStream)));
		
		WebView slideText = (WebView) rowView.findViewById(R.id.slideshow_article);
		slideText.setFocusable(false);
		slideText.setClickable(false);
		String slideContent = slides.get(position).getText();
		slideText.loadData(align + slideContent.replace("%", "&#37"),"text/html","utf-8");
		
		((TextView) rowView.findViewById(R.id.question_counter)).setText(String.valueOf(position +1) + " of " + String.valueOf(slides.size()));

		((ViewPager) collection).addView(rowView,0);
		
		return rowView;
	}

    /**
     * Remove a page for the given position.  The adapter is responsible
     * for removing the view from its container, although it only must ensure
     * this is done by the time it returns from {@link #finishUpdate()}.
     *
     * @param container The containing View from which the page will be removed.
     * @param position The page position to be removed.
     * @param object The same object that was returned by
     * {@link #instantiateItem(View, int)}.
     */
	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((RelativeLayout) view);
	}

	
	
	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view==((RelativeLayout)object);
	}

	
    /**
     * Called when the a change in the shown pages has been completed.  At this
     * point you must ensure that all of the pages have actually been added or
     * removed from the container as appropriate.
     * @param container The containing View which is displaying this adapter's
     * page views.
     */
	@Override
	public void finishUpdate(View arg0) {}
	

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {}
	
}
