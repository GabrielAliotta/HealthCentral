package com.healthcentral.common;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthcentral.activity.R;
import com.heathcentral.model.QuizAnswered;

public class CustomQuizResultAdapter extends ArrayAdapter<QuizAnswered>{

	private final Context context;
	private final List<QuizAnswered> values;

	public CustomQuizResultAdapter(Context context, List<QuizAnswered> values) {
		super(context, R.layout.quiz_result_item, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.quiz_result_item, parent, false);
		
		String questionIdString = String.valueOf(values.get(position).getQuestionId() +1) + ". ";
		
		TextView questionId = (TextView) rowView.findViewById(R.id.question_id);
		
		SpannableStringBuilder questionIdText = new SpannableStringBuilder(questionIdString);
		ForegroundColorSpan fcs;
		final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
		
		ImageView validImage = (ImageView) rowView.findViewById(R.id.quiz_result_image);
		if(values.get(position).isValid()){
			fcs = new ForegroundColorSpan(Color.parseColor("#ff8dc73f"));
			validImage.setImageResource(R.drawable.checkmark);
		} else {
			fcs = new ForegroundColorSpan(Color.parseColor("#CC4848"));
			validImage.setImageResource(android.R.drawable.ic_delete);
		}
		
		questionIdText.setSpan(fcs, 0, questionIdString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		questionIdText.setSpan(bss, 0, questionIdString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		questionId.setText(questionIdText);
		
		TextView question = (TextView) rowView.findViewById(R.id.question);
		question.setText(values.get(position).getQuestion());
		TextView answer = (TextView) rowView.findViewById(R.id.answer);
		answer.setText("Correct Answer: " + values.get(position).getCorrectAnswer());
		TextView resultText = (TextView) rowView.findViewById(R.id.result_text);
		resultText.setText(values.get(position).getQuestionText());

		return rowView;
	}
}

