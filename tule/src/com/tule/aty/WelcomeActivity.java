package com.tule.aty;

import java.util.ArrayList;
import java.util.List;

import com.tule.Config;
import com.tule.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 一个学长写的，经同意引用过来
 * @date 2015年8月1日
 */
public class WelcomeActivity extends Activity {
	private ImageView[] dots;
	/** 已知的引导页个数 */

	/** 保存每次引导上一种状态变量 */
	private int currentIndex = 0;
	private LinearLayout linear_guide_dots;

	ViewPager mViewPager;
	public int[] images =Config.GUIDE_IMAGES;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		mViewPager = (ViewPager) findViewById(R.id.viewFlipper1);

		initWithPageGuideMode();

	}

	public void initWithPageGuideMode() {
		List<View> mList = new ArrayList<View>();
		LayoutInflater inflat = LayoutInflater.from(this);
		// 先添加一个最左侧空的view
		View item = inflat.inflate(R.layout.activity_welcome, null);

		for (int index : images) {
			item = inflat.inflate(R.layout.activity_welcome, null);
			item.setBackgroundResource(index);
			mList.add(item);
		}

		// btn.setOnClickListener(this)，设置最后一个页面上button的监听
		// 再添加一个最右侧空的view

		dots = new ImageView[3];
		linear_guide_dots = (LinearLayout) findViewById(R.id.linear_guide_dots);
		for (int i = 0; i < 3; i++) // 循环取得小点图片
			dots[i] = (ImageView) linear_guide_dots.getChildAt(i);
		dots[1].setEnabled(true);
		dots[0].setEnabled(false);
		dots[2].setEnabled(true);
		// ViewPager最重要的设置Adapter，这和ListView一样的原理
		MViewPageAdapter adapter = new MViewPageAdapter(mList);
		mViewPager.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(adapter);
		mViewPager.setCurrentItem(0);

	}

	class MViewPageAdapter extends PagerAdapter implements OnPageChangeListener {

		private List<View> mViewList;

		public MViewPageAdapter(List<View> views) {
			mViewList = views;
		}

		@Override
		public int getCount() {
			return mViewList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {

			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mViewList.get(position), 0);
			return mViewList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mViewList.get(position));
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int position) {

			if (position == 0) {
				mViewPager.setCurrentItem(0);

				dots[1].setEnabled(true);
				dots[0].setEnabled(false);
				dots[2].setEnabled(true);
			} else if (position == 1) {
				dots[1].setEnabled(false);
				dots[0].setEnabled(true);
				dots[2].setEnabled(true);
			} else if (position == 2) {
				dots[1].setEnabled(true);
				dots[0].setEnabled(true);
				dots[2].setEnabled(false);
				Button gobutton = (Button) findViewById(R.id.go);
				gobutton.setVisibility(View.VISIBLE);
				gobutton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
						startActivity(intent);
						finish();
					}
				});

			}

		}

	}
}
