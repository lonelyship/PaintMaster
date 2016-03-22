/*
 * Copyright 2013 David Schreiber
 *           2013 John Paul Nalog
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.aiiistudent.paintmaster;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

class FancyCoverFlowSampleAdapter extends FancyCoverFlowAdapter {

	 int width  ;	
	 int height  ;
	 int imageWidth;
	 int imageHeight;
	
	// =============================================================================
	// Private members
	// =============================================================================
	private Activity a;
	FancyCoverFlowSampleAdapter (Activity a){
		this.a = a;
		Display mDisplay = a.getWindowManager().getDefaultDisplay(); 
		 width  = mDisplay.getWidth(); 
		 height = mDisplay.getHeight();
		//Toast.makeText(a, "width:"+width+" height:"+height, Toast.LENGTH_LONG).show();
		 imageWidth=width/4;
		 imageHeight=height*2/3;
		
	}
	
	private int[] images = { R.drawable.cup_index, R.drawable.painter_index, R.drawable.card_index,
			R.drawable.choose_index, R.drawable.camera_index, R.drawable.gallery_index,R.drawable.help_index };

	// =============================================================================
	// Supertype overrides
	// =============================================================================

	@Override
	public int getCount() {
		return images.length;
	}
	
	@Override
	public Integer getItem(int i) {
		return images[i];
	}

	@Override
	public long getItemId(int i) {
		return i;
	}
 

	@Override
	public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
		CustomViewGroup customViewGroup = null;

		if (reuseableView != null) {
			customViewGroup = (CustomViewGroup) reuseableView;
		} else {
			customViewGroup = new CustomViewGroup(viewGroup.getContext());
			customViewGroup.setLayoutParams(new FancyCoverFlow.LayoutParams(
					imageWidth, imageHeight));
		}

		customViewGroup.getImageView().setImageResource(this.getItem(i));

		switch (i) {
		case 0:

			customViewGroup.getTextView().setText(R.string.race_mode);
			customViewGroup.getTextView().setTextColor(Color.RED);
			break;
		case 1:

			customViewGroup.getTextView().setText(R.string.practice_mode);
			customViewGroup.getTextView().setTextColor(Color.YELLOW);

			break;

		case 2:

			customViewGroup.getTextView().setText(R.string.free_mode);
			customViewGroup.getTextView().setTextColor(Color.GREEN);
			break;

		case 3:

			customViewGroup.getTextView().setText(R.string.pick_mode);
			customViewGroup.getTextView().setTextColor(Color.MAGENTA);

			break;

		case 4:

			customViewGroup.getTextView().setText(R.string.camera_mode);
			customViewGroup.getTextView().setTextColor(Color.WHITE);

			break;

		case 5:

			customViewGroup.getTextView().setText(R.string.gallery_mode);
			customViewGroup.getTextView().setTextColor(Color.CYAN);

			break;
		case 6:

			customViewGroup.getTextView().setText(R.string.about);
			customViewGroup.getTextView().setTextColor(Color.BLUE);

			break;
			
		default:
			break;
		}

		customViewGroup.getTextView().setTextSize(30);
		customViewGroup.getTextView().setBackgroundColor(Color.BLACK);


		
		
		return customViewGroup;
	}
}

class CustomViewGroup extends LinearLayout {

	// =============================================================================
	// Child views
	// =============================================================================

	private TextView textView;

	private ImageView imageView;

	// =============================================================================
	// Constructor
	// =============================================================================

	CustomViewGroup(Context context) {
		super(context);

		this.setOrientation(VERTICAL);

		this.textView = new TextView(context);
		this.imageView = new ImageView(context);

		LinearLayout.LayoutParams layoutParams = new LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		this.textView.setLayoutParams(layoutParams);
		this.imageView.setLayoutParams(layoutParams);
		this.textView.setGravity(Gravity.CENTER);

		this.imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		this.imageView.setAdjustViewBounds(true);

		this.addView(this.imageView);
		this.addView(this.textView);
	}

	// =============================================================================
	// Getters
	// =============================================================================

	TextView getTextView() {
		return textView;
	}

	ImageView getImageView() {
		return imageView;
	}
}
