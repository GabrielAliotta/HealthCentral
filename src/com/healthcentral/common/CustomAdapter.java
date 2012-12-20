package com.healthcentral.common;

import java.io.ByteArrayInputStream;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthcentral.activity.R;
import com.heathcentral.model.Slideshow;


public class CustomAdapter extends ArrayAdapter<Slideshow> {

	private final Context context;
	private final List<Slideshow> values;
	private final String valueWanted;
	int pos;

	public CustomAdapter(Context context, List<Slideshow> values, String valueWanted) {
		super(context, R.layout.list_item, values);
		this.context = context;
		this.values = values;
		this.valueWanted = valueWanted;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		ByteArrayInputStream imageStream = new ByteArrayInputStream(values.get(position).getImage());
		Bitmap theImage = BitmapFactory.decodeStream(imageStream);
		
		View rowView = convertView;
		if (rowView == null)
			rowView = inflater.inflate(R.layout.list_item, parent, false);
		TextView nameTextView = (TextView) rowView.findViewById(R.id.Title);
		
		ImageView image = (ImageView) rowView.findViewById(R.id.slideshowImage);
		image.setImageBitmap(theImage);

		pos = position;

		return rowView;
	}
}
