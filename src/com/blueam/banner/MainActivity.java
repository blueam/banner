package com.blueam.banner;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;

import com.blueam.banner.adapter.MyViewPagerAdapter;


public class MainActivity extends Activity {

    private ViewPager mViewPager;
    private LinearLayout pointsGroup;
    private int prePos = 0;
	private Handler mHandler;
	private ArrayList<ImageView> views;
	private MainActivity mActivity;
	private int[] resids = {R.drawable.ic1, R.drawable.ic2, R.drawable.ic3, R.drawable.ic4};

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mActivity = this;
        initView();
        initDatas();
    }
	
	private void initView() {
    	mViewPager = (ViewPager) findViewById(R.id.mViewPager);
    	pointsGroup = (LinearLayout) findViewById(R.id.pointsGroup);
	}

    private void initDatas() {
		views = new ArrayList<ImageView>();
		for (int i = 0; i < 4; i++) {
			ImageView view = new ImageView(this);
			view.setBackgroundResource(resids[i]);
			view.setOnTouchListener(new OnItemTouchListener());
			views.add(view);
		}
		initPoints();
		initViewPager();
	}

	private void initViewPager() {
		mViewPager.setAdapter(new MyViewPagerAdapter(views));
		mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		startCirculation();
	}

	private void initPoints() {
		pointsGroup.removeAllViews();
		View v;
		LayoutParams params;
		for (int i = 0; i < views.size(); i++) {
			v = new View(this);
			v.setBackgroundResource(R.drawable.viewpager_points_selector);
			params = new LayoutParams(dip2px(8), dip2px(8));
			if (i != 0) {
				params.leftMargin = dip2px(8);
			}
			v.setLayoutParams(params);
			v.setEnabled(false);
			pointsGroup.addView(v);
		}
		
	}
	
	private int dip2px(int dip) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (dip * scale + 0.5f);
	}
	
	private class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			
		}

		@Override
		public void onPageSelected(int position) {
			pointsGroup.getChildAt(prePos).setEnabled(false);
			pointsGroup.getChildAt(position).setEnabled(true);
			prePos = position;
			
		}
		
	}
	
	private class OnItemTouchListener implements OnTouchListener {

		private float x;
		private float x2;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				x = event.getX();
				mHandler.removeCallbacksAndMessages(null); // 移除消息队列中所有的任务和消息.
				break;
			case MotionEvent.ACTION_CANCEL: // 事件丢失
				mHandler.postDelayed(new InternalRunnable(), 3000);
				break;
			case MotionEvent.ACTION_UP:
				x2 = event.getX();
				if (Math.abs(x2 - x) < 15) {
					Toast.makeText(mActivity, "轮播图点击事件！", 10000).show();
				}
				mHandler.postDelayed(new InternalRunnable(), 3000);
				break;
			default:
				break;
			}

			return true;
		}
	}

	private void startCirculation() {
		if (mHandler == null) {
			mHandler = new InternalHandler();
		} else {
			mHandler.removeCallbacksAndMessages(null);
		}
		mHandler.postDelayed(new InternalRunnable(), 3000);
	}

	private class InternalHandler extends Handler {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			// 当前是在主线程中, 把轮播图切换到下一页面
			int currentItem = (mViewPager.getCurrentItem() + 1)
					% views.size();
			mViewPager.setCurrentItem(currentItem);

			mHandler.postDelayed(new InternalRunnable(), 2500);
		}
	}

	private class InternalRunnable implements Runnable {

		@Override
		public void run() {
			mHandler.sendEmptyMessage(0);
		}
	}
}
