package com.healthcentral.common;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.healthcentral.activity.R;

public class CustomResourcesAdapter extends ArrayAdapter<String>{

	private final Context context;
	private final List<String> values;

	public CustomResourcesAdapter(Context context, List<String> values) {
		super(context, R.layout.site_resources, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.site_resources, parent, false);
		
		ImageView resourceImage = (ImageView) rowView.findViewById(R.id.resource_image);
		if(position == 0){
			resourceImage.setImageResource(R.drawable.slideshow_icon);
		} else {
			resourceImage.setImageResource(R.drawable.quiz_icon);
		}
		
		Animation animationRightIn = AnimationUtils.loadAnimation(context, R.anim.resource_item_left);
		Animation animationRightOut = AnimationUtils.loadAnimation(context, R.anim.resource_item_right);
		
		if (position % 2 == 0){
			rowView.startAnimation(animationRightIn);
		} else {
			rowView.startAnimation(animationRightOut);
		}

		return rowView;
	}
	
}
