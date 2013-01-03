package com.healthcentral.common;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.healthcentral.activity.R;
import com.heathcentral.model.Vertical;

public class CustomVerticalAdapter extends ArrayAdapter<Vertical> {

	private LayoutInflater inflater;
	private final List<Vertical> values;

	public CustomVerticalAdapter(Context context, List<Vertical> values) {
		super(context, android.R.layout.simple_list_item_1, values);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View rowView = convertView;
		if (rowView == null)
			rowView = inflater.inflate(R.layout.list_item, parent, false);
		
		TextView nameTextView = (TextView) rowView.findViewById(android.R.id.text1);
		nameTextView.setText(values.get(position).getVerticalName());
		
		return rowView;
	}
}
