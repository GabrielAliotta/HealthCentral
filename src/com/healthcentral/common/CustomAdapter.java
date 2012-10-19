package com.healthcentral.common;


import java.io.ByteArrayInputStream;
import java.util.List;

import com.healthcentral.activity.R;
import com.heathcentral.model.Site;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomAdapter extends ArrayAdapter<Site> {

	private final Context context;
	private final List<Site> values;
	private final String valueWanted;
	int pos;

	public CustomAdapter(Context context, List<Site> values, String valueWanted) {
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
		
		View rowView = inflater.inflate(R.layout.list_item, parent, false);
		TextView nameTextView = (TextView) rowView.findViewById(R.id.Title);
		if(valueWanted.equals("vertical")){
			nameTextView.setText(values.get(position).getFriendlyTitle());
		} else {
			nameTextView.setText(values.get(position).getTitle());
		}
		
		ImageView image = (ImageView) rowView.findViewById(R.id.slideshowImage);
		image.setImageBitmap(theImage);

		pos = position;

		return rowView;
	}
}
