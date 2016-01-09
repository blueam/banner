package com.blueam.banner.adapter;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MyViewPagerAdapter extends PagerAdapter {
	
	private ArrayList<ImageView> mViews;

	public MyViewPagerAdapter(ArrayList<ImageView> views) {
		this.mViews = views;
	}

	@Override
	public int getCount() {
		return mViews.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View)object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView view = mViews.get(position);
		view.setScaleType(ScaleType.FIT_CENTER);
		container.addView(view);
		return view;
	}

	
}
