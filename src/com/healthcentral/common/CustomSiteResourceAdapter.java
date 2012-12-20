package com.healthcentral.common;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthcentral.activity.R;

public class CustomSiteResourceAdapter extends ArrayAdapter<String> {

	private final Context context;
	private final List<String> values;
	int pos;

	public CustomSiteResourceAdapter(Context context, List<String> values) {
		super(context, R.layout.list_resource_item, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = convertView;
		if (rowView == null)
			rowView = inflater.inflate(R.layout.list_resource_item, parent,
					false);
		TextView nameTextView = (TextView) rowView.findViewById(android.R.id.text1);
		nameTextView.setTextColor(Color.parseColor("#FF0088BD"));
		nameTextView.setText(values.get(position));

		Drawable theImage;

		if (values.get(position).equals("Slideshows"))
			theImage = context.getResources().getDrawable(R.drawable.slideshow_icon);
		else
			theImage = context.getResources().getDrawable(R.drawable.quizzes_icon);

		 ImageView image = (ImageView) rowView.findViewById(R.id.resourceImage);
		 image.setImageDrawable(theImage);

		return rowView;
	}
}