package com.TroyEmpire.NightFury.UI.Activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Entity.News;
import com.TroyEmpire.NightFury.Enum.NewsType;
import com.TroyEmpire.NightFury.Ghost.IService.IInformationPlatformService;
import com.TroyEmpire.NightFury.Ghost.Service.InformationPlatformSerivce;
import com.TroyEmpire.NightFury.UI.Adapter.XinXiPTTitleAdapter;
import com.TroyEmpire.NightFury.UI.Entity.PullDownView;
import com.TroyEmpire.NightFury.UI.Entity.PullDownView.OnPullDownListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @author Hector
 * 
 */
public class XinXiPTActivity extends Activity implements OnPullDownListener,
		OnItemClickListener {

	private static final int WHAT_DID_LOAD_DATA = 0;
	private static final int WHAT_DID_REFRESH = 1;
	private static final int WHAT_DID_MORE = 2;

	private TextView titleText;

	private PopupWindow popupWindow;
	private ListView groupListView;
	private View view;
	private List<String> groups;

	private ListView pullListView;
	private XinXiPTTitleAdapter pullAdapter;
	private PullDownView pullDownView;

	private IInformationPlatformService infoService;
	private List<News> listNews = new ArrayList<News>();

	private NewsType newsType;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xinxipt);

		// Set the title bar text as this activity label
		titleText = (TextView) findViewById(R.id.title_text);
		titleText.setText(R.string.xinxipt_label);
		Drawable drawableTip = getResources().getDrawable(
				R.drawable.xinxipt_titlebar_tips);
		drawableTip.setBounds(0, 0, 15, 15);
		titleText.setCompoundDrawables(null, null, null, drawableTip);
		// 点击标题，显示下拉框
		View titleView = (View) findViewById(R.id.title_view);
		titleView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				showWindow(v);
			}
		});

		infoService = new InformationPlatformSerivce(1, this);
		newsType = null;
		/*
		 * 1.使用PullDownView 2.设置OnPullDownListener 3.从mPullDownView里面获取ListView
		 */
		pullDownView = (PullDownView) findViewById(R.id.xinxipt_pull_down_view);
		pullDownView.setOnPullDownListener(this);

		pullListView = pullDownView.getListView();
		pullListView.setOnItemClickListener(this);

		pullAdapter = new XinXiPTTitleAdapter(this, listNews);
		pullListView.setAdapter(pullAdapter);

		pullDownView.enableAutoFetchMore(true, 1);

		loadData();
	}

	private Handler pullUIHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_DID_LOAD_DATA:
				pullAdapter.notifyDataSetChanged();
				pullDownView.notifyDidLoad();
				break;

			case WHAT_DID_REFRESH:
				pullAdapter.notifyDataSetChanged();
				pullDownView.notifyDidRefresh();
				break;

			case WHAT_DID_MORE:
				pullAdapter.notifyDataSetChanged();
				// 告诉它获取更多完毕
				pullDownView.notifyDidMore();
				break;
			}

		}
	};

	private void loadData() {
		listNews.clear();
		listNews.addAll(infoService.getNewsFromStorage(newsType, null,
				Constant.NEWS_NUMBER_ONE_TIME_UPDATE__LIMIT));
		Log.d("load data", "" + newsType);
		if (listNews.isEmpty()) {
			Thread child = new Thread(new Runnable() {

				@Override
				public void run() {
					infoService.updateNews(newsType);
					listNews.clear();
					listNews.addAll(infoService.getNewsFromStorage(newsType,
							null, Constant.NEWS_NUMBER_ONE_TIME_UPDATE__LIMIT));
					Log.d("news size after update", "" + listNews.size());
					Message msg = pullUIHandler
							.obtainMessage(WHAT_DID_LOAD_DATA);
					msg.sendToTarget();
				}
			});
			child.start();
		} else {
			Log.d("news size", "" + listNews.size());
			Message msg = pullUIHandler.obtainMessage(WHAT_DID_LOAD_DATA);
			msg.sendToTarget();
		}

	}

	@Override
	public void onRefresh() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				infoService.updateNews(newsType);
				listNews.clear();
				listNews.addAll(infoService.getNewsFromStorage(newsType, null,
						Constant.NEWS_NUMBER_ONE_TIME_UPDATE__LIMIT));
				Log.d("news size after update", "" + listNews.size());
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg = pullUIHandler.obtainMessage(WHAT_DID_REFRESH);
				msg.sendToTarget();
			}
		}).start();
	}

	@Override
	public void onMore() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (newsType != null)
					Log.d("more:type", newsType.toString());
				Date oldestDate;
				if (listNews.size() == 0)
					oldestDate = null;
				else
					oldestDate = listNews.get(listNews.size() - 1)
							.getPublishDate();
				listNews.addAll(infoService
						.getNewsFromStorage(newsType, oldestDate,
								Constant.NEWS_NUMBER_ONE_TIME_UPDATE__LIMIT));
				Log.d("news size after load more", "" + listNews.size());
				Message msg = pullUIHandler.obtainMessage(WHAT_DID_MORE);
				msg.sendToTarget();
			}
		}).start();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Toast.makeText(this,
				listNews.get(position).getNewsType().toString() + position,
				Toast.LENGTH_SHORT).show();
		Intent displayNewsIntent = new Intent(this,
				XinXiPTDisplayNewsActivity.class);
		News news = listNews.get(position);
		displayNewsIntent.putExtra("newsSelected", news);
		startActivity(displayNewsIntent);
	}

	// 显示分组框
	private void showWindow(View v) {

		if (popupWindow == null) {
			LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			view = layoutInflater.inflate(R.layout.xinxipt_group, null);

			groupListView = (ListView) view
					.findViewById(R.id.xinxipt_group_listview);

			groups = new ArrayList<String>();
			groups.add("全部");
			// groups.add("关注排序");
			groups.add("教务处");
			groups.add("学生处");
			// groups.add("校内通知");

			ArrayAdapter<String> groupAdapter = new ArrayAdapter<String>(this,
					R.layout.xinxipt_group_item, groups);
			// XinXiPTGroupAdapter groupAdapter = new
			// XinXiPTGroupAdapter(this,groups);
			groupListView.setAdapter(groupAdapter);
			popupWindow = new PopupWindow(view, 250, 310);
		}

		popupWindow.setFocusable(true); // 使其聚焦
		popupWindow.setOutsideTouchable(true); // 在下拉框外点击，下拉框消失
		popupWindow.setBackgroundDrawable(new BitmapDrawable(this
				.getResources())); // 返回键消失
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		// 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
		int xPos = windowManager.getDefaultDisplay().getWidth() / 2
				- popupWindow.getWidth() / 2;

		Log.i("coder", "windowManager.getDefaultDisplay().getWidth()/2:"
				+ windowManager.getDefaultDisplay().getWidth() / 2);
		//
		Log.i("coder", "popupWindow.getWidth()/2:" + popupWindow.getWidth() / 2);

		Log.i("coder", "xPos:" + xPos);

		popupWindow.showAsDropDown(v, xPos, 0); // 显示之

		// 选择分组信息
		groupListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {

				String selectedDep = groups.get(position);
				Toast.makeText(XinXiPTActivity.this,
						"groups.get(position)" + selectedDep, 1000).show();
				if ("全部".equals(selectedDep)) {
					newsType = null;
				} else if ("教务处".equals(selectedDep)) {
					newsType = NewsType.教务处;
				} else if ("学生处".equals(selectedDep)) {
					newsType = NewsType.学生处;
				} else {
					newsType = null;
				}
				titleText.setText(selectedDep);

				loadData();

				if (popupWindow != null) {
					popupWindow.dismiss();
				}
			}
		});
	}


	// 实现标题栏的返回键
	public void btnBackOnClick(View v) {
		finish();
	}

}
