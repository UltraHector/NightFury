package com.TroyEmpire.NightFury.UI.Activity;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Entity.Cell;
import com.TroyEmpire.NightFury.Ghost.IService.IMapService;
import com.TroyEmpire.NightFury.Ghost.Service.MapService;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ExpandableListView.OnChildClickListener;

public class XiaoYuanDTSearchActivity extends Activity implements OnClickListener{

	List<HashMap<String, String>> searchGroups;
	List<List<Cell>> searchChilds;
	ExpandableListView searchELV;
	IMapService mapService;
	private AutoCompleteTextView textView;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiaoyuandt_search);

		TextView titleTV = (TextView) findViewById(R.id.title_text);
		titleTV.setText(Constant.XIAOYUANDT_SEARCH_TITLE);

		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		searchELV = (ExpandableListView) findViewById(R.id.xiaoyuandt_search_expandablelistview);
		mapService = new MapService(this);
		loadData();

		SearchExpandableAdapter searchAdapter = new SearchExpandableAdapter(
				this);
		searchELV.setAdapter(searchAdapter);
		
		// set the autocomplete view
		textView = (AutoCompleteTextView) findViewById(R.id.xiaoyuandt_search_textview);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line);
		textView.setAdapter(adapter);
		textView.setThreshold(1);
		final TextWatcher textChecker = new TextWatcher() {
			public void afterTextChanged(Editable s) {
			}
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				adapter.clear();
				for (String suggestion : mapService.getSuggestPlaceName(s
						.toString())) {
					adapter.add(suggestion);
				}
			}
		};
		textView.addTextChangedListener(textChecker);
		// set the submit button 
		Button submitButton = (Button) findViewById(R.id.xiaoyuandt_search_confirm);
		submitButton.setOnClickListener(this);

		// searchELV.expandGroup(0);
		searchELV.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView elv, View v,
					int groupPosition, int childPosition, long id) {
				textView.setText(((Cell) (elv.getExpandableListAdapter()
						.getChild(groupPosition, childPosition))).getName());
				return true;
			}
		});
		
		
	}

	private void loadData() {
		searchGroups = new ArrayList<HashMap<String, String>>();
		searchChilds = new ArrayList<List<Cell>>();

		HashMap<String, String> historyGroup = new HashMap<String, String>();
		historyGroup.put("group", "历史输入记录");
		searchGroups.add(historyGroup);
		HashMap<String, String> favoriteGroup = new HashMap<String, String>();
		favoriteGroup.put("group", "常用搜索");
		searchGroups.add(favoriteGroup);

		List<Cell> historyChild
		// = new ArrayList<Cell>();
		// for(int i = 0; i < 10;i++){
		// Cell a = new Cell();
		// a.setId(i);
		// a.setName("建筑" + i);
		// a.setBuildingId(i);
		// historyChild.add(a);
		// }
		= mapService.getSearchHistory();
		searchChilds.add(historyChild);
		List<Cell> favoriteChild = new ArrayList<Cell>();
		for (int i = 0; i < 10; i++) {
			Cell a = new Cell();
			a.setId(i);
			a.setName("地点" + i);
			a.setBuildingId(i);
			favoriteChild.add(a);
		}
		// = mapService.getFrequentPlace();
		searchChilds.add(favoriteChild);
	}

	// 自定义的Adapter
	class SearchExpandableAdapter extends BaseExpandableListAdapter {
		private Context context;

		/*
		 * 构造函数: 参数1:context对象
		 */
		public SearchExpandableAdapter(Context context) {
			this.context = context;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return searchChilds.get(groupPosition).get(childPosition);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			// 取出一个餐店的显示资源
			Cell childSelected = (Cell) getChild(groupPosition, childPosition);
			String itemTitle = childSelected.getName();

			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			RelativeLayout relativeLayout = (RelativeLayout) layoutInflater
					.inflate(R.layout.xiaoyuandt_search_child, null);

			TextView titleTextView = (TextView) relativeLayout
					.findViewById(R.id.xiaoyuandt_building_tv);
			titleTextView.setText(itemTitle);

			return relativeLayout;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return searchChilds.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return searchGroups.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return searchGroups.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			String text = searchGroups.get(groupPosition).get("group");
			LayoutInflater layoutInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// 获取一级列表布局文件,设置相应元素属性
			LinearLayout linearLayout = (LinearLayout) layoutInflater.inflate(
					R.layout.waimaixt_group, null);
			TextView textView = (TextView) linearLayout
					.findViewById(R.id.waimaixt_group_textview);
			textView.setText(text + "(" + this.getChildrenCount(groupPosition)
					+ ")");

			return linearLayout;
		}

		@Override
		public boolean hasStableIds() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

	}

	// 实现标题栏的Home键
	public void btnHomeOnClick(View v) {
		startActivity(new Intent(this, MainActivity.class));
	}

	// 实现标题栏的返回键
	public void btnBackOnClick(View v) {
		finish();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}