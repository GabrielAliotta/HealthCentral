package com.healthcentral.common;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.healthcentral.activity.R;
import com.heathcentral.model.Vertical;

public class CustomVerticalAdapter extends ArrayAdapter<Vertical> {

	private final Context context;
	private final List<Vertical> values;

	public CustomVerticalAdapter(Context context, List<Vertical> values) {
		super(context, android.R.layout.simple_list_item_1, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

//		Bitmap theImage = null;
//		if(values.get(position).getVerticalImage() != null){
//		ByteArrayInputStream imageStream = new ByteArrayInputStream(values.get(
//				position).getVerticalImage());
//		theImage = BitmapFactory.decodeStream(imageStream);
//		}
		
		View rowView = convertView;
		if (rowView == null)
			rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
		
		TextView nameTextView = (TextView) rowView.findViewById(android.R.id.text1);
		nameTextView.setText(values.get(position).getVerticalName());
		
//		ImageView image = (ImageView) rowView.findViewById(R.id.slideshowImage);
//		image.setImageBitmap(theImage);

		return rowView;
	}
}
