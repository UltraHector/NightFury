package com.TroyEmpire.NightFury.UI.Adapter;

import java.util.List;

import com.TroyEmpire.NightFury.Entity.Meal;
import com.TroyEmpire.NightFury.UI.Activity.R;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WaiMaiXTMealsLVAdapter extends BaseAdapter {

	private static final String TAG = "com.TroyEmpire.NightFury.UI.Adapter.WaiMaiXTMealsLVAdapter";

	private Context context;

	private List<Meal> list;

	/*
	 * 以所有成绩为list，并且标题"课程名，状态，学分，成绩"也要打印出来， 所以让出第0位置给标题，
	 * list的长度应加一，每行成绩的位置也改变了，get(position - 1)才是相应的成绩
	 */
	public WaiMaiXTMealsLVAdapter(Context context, List<Meal> list) {

		this.context = context;
		this.list = list;

	}

	@Override
	public int getCount() {
		return list.size(); // 加入第一行的标题，list的长度应加一
	}

	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {

		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.waimaixt_meals_item, null);
			holder = new ViewHolder();

			convertView.setTag(holder);

			holder.tvMealName = (TextView) convertView
					.findViewById(R.id.waimaixt_meal_item_name_textview);
			holder.tvMealPrice = (TextView) convertView
					.findViewById(R.id.waimaixt_meal_item_price_textview);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		String mealName = list.get(position).getName();
		float mealPrice = list.get(position).getPrice();
		holder.tvMealName.setText(mealName);
		if(mealName.contains("--") &&  mealPrice == 0.0){
			holder.tvMealName.setTextColor(Color.rgb(0, 0, 255));
			holder.tvMealPrice.setTextColor(Color.rgb(0, 0, 255));
			Log.d(TAG, mealName+ ":" + mealPrice);
		}else{			//需优化
			holder.tvMealName.setTextColor(Color.rgb(0, 0, 0));
			holder.tvMealPrice.setTextColor(Color.rgb(0, 0, 0));
		}
		holder.tvMealPrice.setText(list.get(position).getPrice() + "");

		return convertView;
	}

	static class ViewHolder {
		TextView tvMealName;
		TextView tvMealPrice;
	}

}
