package com.healthcentral.common;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.healthcentral.activity.R;

public class CustomResourcesAdapter extends ArrayAdapter<String>{

	private final Context context;
	private final List<String> values;
	int pos;

	public CustomResourcesAdapter(Context context, List<String> values) {
		super(context, R.layout.site_resources, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.site_resources, parent, false);
		TextView resourceNameTextView = (TextView) rowView.findViewById(R.id.Title);

		resourceNameTextView.setText(values.get(position));

		pos = position;

		return rowView;
	}
	
}
