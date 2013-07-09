package com.TroyEmpire.NightFury.UI.Activity;

import java.util.ArrayList;
import java.util.List;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.TroyEmpire.NightFury.Enum.JwcAction;
import com.TroyEmpire.NightFury.Enum.WeekDay;
import com.TroyEmpire.NightFury.Ghost.IService.IScheduleService;
import com.TroyEmpire.NightFury.Ghost.Service.ScheduleService;
import com.TroyEmpire.NightFury.UI.Fragment.UserJwcInfoDialogFragment;

import com.TroyEmpire.NightFury.Util.Util;

public class ICourseScheduleActivity extends FragmentActivity {

	private static final String TAG = "com.TroyEmpire.NightFury.UI.Activity.ICourseScheduleActivity";
	int weekday;
	IScheduleService scheduleService = new ScheduleService(this);

	private RadioGroup radioGroup;
	private RadioButton rb1;

	// 页卡内容
	private ViewPager mPager;
	// Tab页面列表
	private List<View> listViews;
	// 当前页卡编号
	private LocalActivityManager manager = null;

	private MyPagerAdapter mpAdapter = null;
	private int index;

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "start " + index);
	}

	// 更新intent传过来的值
	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {

	}

	@Override
	public void onBackPressed() {
		Log.i("", "onBackPressed()");
		super.onBackPressed();
	}

	@Override
	protected void onPause() {
		Log.i("", "onPause()");
		super.onPause();
	}

	@Override
	protected void onStop() {
		Log.i("", "onStop()");
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.i("", "onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		index = weekday;
		mPager.setCurrentItem(index);
//		Log.d(TAG, "onResume " + index);
//		if (getIntent() != null) {
//			index = 0;
//			mPager.setCurrentItem(index);
//			setIntent(null);
//			Log.d(TAG, "getIntent() != null " + index);
//		} else {
//			if (index < 6) {
//				index = index + 1;
//				mPager.setCurrentItem(index);
//				index = index - 1;
//				mPager.setCurrentItem(index);
//
//			} else if (index == 6) {
//				index = index - 1;
//				mPager.setCurrentItem(index);
//				index = index + 1;
//				mPager.setCurrentItem(index);
//			}
//		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icourse_schedule);

		// No JWC information has been saved
		if (!scheduleService.isAllScheduleSaved()
				&& !scheduleService.isUserJwcPasswordHasBeenSaved()) {
			UserJwcInfoDialogFragment userInfoDialog;
			userInfoDialog = new UserJwcInfoDialogFragment(this,
					JwcAction.UPDATE_SCHEDULE);
			userInfoDialog.show(getSupportFragmentManager(), "getUserJwcInfo");
		}

		weekday = Util.getWeekdayInt();

		mPager = (ViewPager) findViewById(R.id.vPager);

		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		InitViewPager();
		Log.d(TAG, "" + index);
		radioGroup = (RadioGroup) findViewById(R.id.icourse_schedule_day_rg);
		Log.d(TAG, "radioGroup find  " + index);
		// rb1 = (RadioButton)findViewById(R.id.day_radio_btn2);
		// rb1.setChecked(true);
		radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						Log.d(TAG, "onChecked " + index);
						switch (checkedId) {

						case R.id.day_radio_btn0:
							index = 0;
							// listViews.set(4, getView("E", new
							// Intent(TabHostActivity.this,
							// OneDomeActivity.class)));
							// mpAdapter.notifyDataSetChanged();
							Log.d(TAG, "checkedId " + index);
							mPager.setCurrentItem(0);
							break;

						case R.id.day_radio_btn1:
							index = 1;
							// listViews.set(0, getView("A", new
							// Intent(TabHostActivity.this,
							// OneDomeActivity.class)));
							// mpAdapter.notifyDataSetChanged();
							mPager.setCurrentItem(1);
							break;

						case R.id.day_radio_btn2:
							index = 2;
							// listViews.set(1, getView("B", new
							// Intent(TabHostActivity.this,
							// OneDomeActivity.class)));
							// mpAdapter.notifyDataSetChanged();
							mPager.setCurrentItem(2);
							break;

						case R.id.day_radio_btn3:
							index = 3;
							// listViews.set(2, getView("C", new
							// Intent(TabHostActivity.this,
							// OneDomeActivity.class)));
							// mpAdapter.notifyDataSetChanged();
							mPager.setCurrentItem(3);
							break;

						case R.id.day_radio_btn4:
							index = 4;
							// listViews.set(3, getView("D", new
							// Intent(TabHostActivity.this,
							// OneDomeActivity.class)));
							// mpAdapter.notifyDataSetChanged();
							mPager.setCurrentItem(4);
							break;

						case R.id.day_radio_btn5:
							index = 5;
							// listViews.set(4, getView("E", new
							// Intent(TabHostActivity.this,
							// OneDomeActivity.class)));
							// mpAdapter.notifyDataSetChanged();
							mPager.setCurrentItem(5);
							break;

						case R.id.day_radio_btn6:
							index = 6;
							// listViews.set(4, getView("E", new
							// Intent(TabHostActivity.this,
							// OneDomeActivity.class)));
							// mpAdapter.notifyDataSetChanged();
							mPager.setCurrentItem(6);
							break;

						default:
							break;
						}
					}
				});
	}

	/**
	 * 初始化ViewPager
	 */
	private void InitViewPager() {
		Intent intent = null;
		listViews = new ArrayList<View>();
		mpAdapter = new MyPagerAdapter(listViews);
		intent = new Intent(ICourseScheduleActivity.this,
				ICourseScheduleDisplayActivity.class);
		intent.putExtra("weekday", 0);
		listViews.add(getView("G", intent));
		intent = new Intent(ICourseScheduleActivity.this,
				ICourseScheduleDisplayActivity.class);
		intent.putExtra("weekday", 1);
		listViews.add(getView("A", intent));
		intent = new Intent(ICourseScheduleActivity.this,
				ICourseScheduleDisplayActivity.class);
		intent.putExtra("weekday", 2);
		listViews.add(getView("B", intent));
		intent = new Intent(ICourseScheduleActivity.this,
				ICourseScheduleDisplayActivity.class);
		intent.putExtra("weekday", 3);
		listViews.add(getView("C", intent));
		intent = new Intent(ICourseScheduleActivity.this,
				ICourseScheduleDisplayActivity.class);
		intent.putExtra("weekday", 4);
		listViews.add(getView("D", intent));
		intent = new Intent(ICourseScheduleActivity.this,
				ICourseScheduleDisplayActivity.class);
		intent.putExtra("weekday", 5);
		listViews.add(getView("E", intent));
		intent = new Intent(ICourseScheduleActivity.this,
				ICourseScheduleDisplayActivity.class);
		intent.putExtra("weekday", 6);
		listViews.add(getView("F", intent));

		mPager.setOffscreenPageLimit(1);
		mPager.setAdapter(mpAdapter);
		Log.d(TAG, "init " + index);
		mPager.setCurrentItem(1);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * ViewPager适配器
	 */
	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	/**
	 * 页卡切换监听，ViewPager改变同样改变TabHost内容
	 */
	public class MyOnPageChangeListener implements OnPageChangeListener {

		public void onPageSelected(int arg0) {
			manager.dispatchResume();
			Log.d(TAG, "onPage " + index);
			switch (arg0) {
			case 0:
				index = 0;
				radioGroup.check(R.id.day_radio_btn0);
				// listViews.set(0, getView("A", new
				// Intent(ICourseScheduleActivity.this,
				// OneDomeActivity.class)));
				// mpAdapter.notifyDataSetChanged();
				Log.d(TAG, "onpageselected 0 " + index);
				break;
			case 1:
				index = 1;
				radioGroup.check(R.id.day_radio_btn1);
				// listViews.set(1, getView("B", new
				// Intent(ICourseScheduleActivity.this,
				// OneDomeActivity.class)));
				// mpAdapter.notifyDataSetChanged();
				break;
			case 2:
				index = 2;
				radioGroup.check(R.id.day_radio_btn2);
				// listViews.set(2, getView("C", new
				// Intent(ICourseScheduleActivity.this,
				// OneDomeActivity.class)));
				// mpAdapter.notifyDataSetChanged();
				break;
			case 3:
				index = 3;
				radioGroup.check(R.id.day_radio_btn3);
				// listViews.set(3, getView("D", new
				// Intent(ICourseScheduleActivity.this,
				// OneDomeActivity.class)));
				// mpAdapter.notifyDataSetChanged();
				break;
			case 4:
				index = 4;
				radioGroup.check(R.id.day_radio_btn4);
				// listViews.set(4, getView("E", new
				// Intent(ICourseScheduleActivity.this,
				// OneDomeActivity.class)));
				// mpAdapter.notifyDataSetChanged();
				break;
			case 5:
				index = 5;
				radioGroup.check(R.id.day_radio_btn5);
				// listViews.set(3, getView("D", new
				// Intent(ICourseScheduleActivity.this,
				// OneDomeActivity.class)));
				// mpAdapter.notifyDataSetChanged();
				break;
			case 6:
				index = 6;
				radioGroup.check(R.id.day_radio_btn6);
				// listViews.set(4, getView("E", new
				// Intent(ICourseScheduleActivity.this,
				// OneDomeActivity.class)));
				// mpAdapter.notifyDataSetChanged();
				break;
			}
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageScrollStateChanged(int arg0) {
		}
	}

	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

}
