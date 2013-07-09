package com.TroyEmpire.NightFury.UI.Adapter;

import java.util.List;

import com.TroyEmpire.NightFury.Entity.ExamScore;
import com.TroyEmpire.NightFury.UI.Activity.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ICourseGradeAdapter extends BaseAdapter {

	private Context context;

	private List<ExamScore> list;

	/*
	 * 以所有成绩为list，并且标题"课程名，状态，学分，成绩"也要打印出来，
	 * 所以让出第0位置给标题，
	 * list的长度应加一，每行成绩的位置也改变了，get(position - 1)才是相应的成绩
	 */
	public ICourseGradeAdapter(Context context, List<ExamScore> list) {

		this.context = context;
		this.list = list;

	}

	@Override
	public int getCount() {
		return list.size();			//加入第一行的标题，list的长度应加一
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
		if (convertView==null) {
			convertView=LayoutInflater.from(context).inflate(R.layout.icourse_grade_item, null);
			holder=new ViewHolder();
			
			convertView.setTag(holder);
			
			holder.tvCourseName=(TextView) convertView.findViewById(R.id.ischedule_score_item_coursename);
			holder.tvStatus = (TextView) convertView.findViewById(R.id.ischedule_score_item_status);
			holder.tvCreditPoints = (TextView) convertView.findViewById(R.id.ischedule_score_item_creditpoints);
			holder.tvScore = (TextView) convertView.findViewById(R.id.ischedule_score_item_score);
		}
		else{
			holder=(ViewHolder) convertView.getTag();
		}
		

			holder.tvCourseName.setText(list.get(position ).getCourceName());
			holder.tvStatus.setText(list.get(position).getStatus());
			holder.tvCreditPoints.setText(list.get(position).getCreditPoints());
			holder.tvScore.setText(list.get(position).getScore());
	
		return convertView;
	}

	static class ViewHolder {
		TextView tvCourseName;
		TextView tvStatus;
		TextView tvCreditPoints;
		TextView tvScore;
	}

}
