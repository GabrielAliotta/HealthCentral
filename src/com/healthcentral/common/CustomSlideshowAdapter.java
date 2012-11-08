package com.healthcentral.common;

import java.io.ByteArrayInputStream;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthcentral.activity.R;
import com.heathcentral.model.Slideshow;

public class CustomSlideshowAdapter extends ArrayAdapter<Slideshow>{

	private final Context context;
	private final List<Slideshow> values;
	int pos;

	public CustomSlideshowAdapter(Context context, List<Slideshow> values) {
		super(context, R.layout.list_slideshow_item, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		ByteArrayInputStream imageStream = new ByteArrayInputStream(values.get(position).getImage());
		Bitmap theImage = BitmapFactory.decodeStream(imageStream);
		
		View rowView = inflater.inflate(R.layout.list_slideshow_item, parent, false);
		TextView nameTextView = (TextView) rowView.findViewById(R.id.Title);
//		if(valueWanted.equals("vertical")){
//			nameTextView.setText(values.get(position).getFriendlyTitle());
//		} else {
//			nameTextView.setText(values.get(position).getTitle());
//		}
		nameTextView.setText(values.get(position).getTitle());
		
		((RelativeLayout) rowView.findViewById(R.id.relative_slideshow_item)).setBackgroundDrawable(new BitmapDrawable(theImage));
		//ImageView image = (ImageView) rowView.findViewById(R.id.slideshowImage);
		//image.setImageBitmap(theImage);

		pos = position;

		return rowView;
	}
}
