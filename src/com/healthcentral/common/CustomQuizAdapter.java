package com.healthcentral.common;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.healthcentral.activity.R;
import com.heathcentral.model.Quiz;

public class CustomQuizAdapter extends ArrayAdapter<Quiz>{

	private final Context context;
	private final List<Quiz> values;
	int pos;

	public CustomQuizAdapter(Context context, List<Quiz> values) {
		super(context, R.layout.list_quiz_item, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.list_quiz_item, parent, false);
		TextView nameTextView = (TextView) rowView.findViewById(R.id.Title);
		nameTextView.setText(values.get(position).getTitle());
		TextView descriptionTextView = (TextView) rowView.findViewById(R.id.Description);
		descriptionTextView.setText(values.get(position).getText());
		
		pos = position;

		return rowView;
	}
}
