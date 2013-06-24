package com.TroyEmpire.NightFury.UI.Adapter;
/*
 *  @Author: GJ10
 *  @Time: 2013/5/29
 *  @Usage: 外卖系统的所有餐店展示ExpandableListView的Adapter，
 *          这里会先从数据库中加载所有餐店的信息，来展示外卖系统的restaurantELV，
 *          并且管理收藏夹
 */
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.TroyEmpire.NightFury.Entity.Restaurant;
import com.TroyEmpire.NightFury.Ghost.IService.ICallInMealService;
import com.TroyEmpire.NightFury.Ghost.Service.CallInMealService;
import com.TroyEmpire.NightFury.UI.Activity.R;

public class RestaurantExpandableAdapter extends BaseExpandableListAdapter {
	private Context context;
	List<String> restaurantGroups; // 分类信息
	List<List<Restaurant>> restaurantChilds; // 对应每个分类下的子项
	// 需要的接口服务:餐店数据库服务restService
	private ICallInMealService restService = new CallInMealService(1);

	
	/*
	 * 构造函数: 参数1:context对象
	 */
	public RestaurantExpandableAdapter(Context context) {
		this.context = context;
		prepareData();			//加载数据库数据
	}
	
	public List<List<Restaurant>> getRestaurantChilds() {
		return restaurantChilds;
	}

	public void setRestaurantChilds(List<List<Restaurant>> restaurantChilds) {
		this.restaurantChilds = restaurantChilds;
	}
	
	//准备一级列表显示的分组数据restaurantGroups和restaurantChilds，分别有“收藏夹”和“全部餐店”
	private void prepareData()
	{
		restaurantGroups = new ArrayList<String>();
		restaurantChilds = new ArrayList<List<Restaurant>>();
		//收藏夹
		restaurantGroups.add("收藏夹");
		//全部
		restaurantGroups.add("全部餐店");
			
		//restaurantChilds存放收藏夹和全部的显示菜单
		List<Restaurant> favoriteChild = restService.getRestaurantsBookmarked();
		restaurantChilds.add(favoriteChild);
		List<Restaurant> allChild = restService.getAllRestaurants();
		restaurantChilds.add(allChild);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return restaurantChilds.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// 取出一个餐店的显示资源
		Restaurant childSelected = (Restaurant) getChild(groupPosition,
				childPosition);
		String itemTitle = childSelected.getName();
		String itemSubTitle = childSelected.getType() + "(起送价："
				+ childSelected.getMinimumOrder() + "元)";
		// 餐店Logo路径
		String itemImagePath = restService.getRestaurantLogoPath(childSelected
				.getId());
		Log.d("rest logo uri", itemImagePath);

		LayoutInflater layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		RelativeLayout relativeLayout = (RelativeLayout) layoutInflater
				.inflate(R.layout.waimaixt_child, null);

		ImageView imageView = (ImageView) relativeLayout
				.findViewById(R.id.waimaixt_item_image);
		File temp = new File(itemImagePath);
		if (temp.exists()) {
			imageView.setImageBitmap(BitmapFactory.decodeFile(itemImagePath));
		} else {
			imageView.setImageResource(R.drawable.restaurant_default_logo);
		}

		TextView titleTextView = (TextView) relativeLayout
				.findViewById(R.id.waimaixt_item_title);
//		if(itemTitle.startsWith("山"))
//			titleTextView.setTextColor(Color.rgb(0, 0, 255));
		titleTextView.setText(itemTitle);
		TextView subTitleTextView = (TextView) relativeLayout
				.findViewById(R.id.waimaixt_item_subtitle);
		subTitleTextView.setText(itemSubTitle);

		return relativeLayout;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return restaurantChilds.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return restaurantGroups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return restaurantGroups.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String text = restaurantGroups.get(groupPosition);
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
