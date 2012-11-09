package com.healthcentral.common;

import java.io.ByteArrayInputStream;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.healthcentral.activity.R;
import com.heathcentral.model.Quiz;

public class CustomQuizAdapter extends ArrayAdapter<Quiz>{

	private final Context context;
	private final List<Quiz> values;

	public CustomQuizAdapter(Context context, List<Quiz> values) {
		super(context, R.layout.list_quiz_item, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		//Get image
		ByteArrayInputStream imageStream = new ByteArrayInputStream(values.get(position).getImage());
		Bitmap theImage = BitmapFactory.decodeStream(imageStream);

		View rowView = inflater.inflate(R.layout.list_quiz_item, parent, false);
		((TextView) rowView.findViewById(R.id.Title)).setText(Html.fromHtml(values.get(position).getTitle()));
		((TextView) rowView.findViewById(R.id.Description)).setText(Html.fromHtml(values.get(position).getText()));
		
		BitmapDrawable image = new BitmapDrawable(theImage);
		image.setAlpha(100);
		((RelativeLayout) rowView.findViewById(R.id.relative_quiz_item)).setBackgroundDrawable(image);

		return rowView;
	}
}
