package com.TroyEmpire.NightFury.UI.Activity;

import java.util.ArrayList;
import java.util.List;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Entity.ExamScore;
import com.TroyEmpire.NightFury.Enum.JwcAction;
import com.TroyEmpire.NightFury.UI.Adapter.ICourseGradeAdapter;
import com.TroyEmpire.NightFury.UI.Fragment.UserJwcInfoDialogFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ICourseDisplayGradeActivity extends FragmentActivity{

	private static final String TAG = "com.TroyEmpire.NightFury.UI.Activity.ICourseDisplayGradeActivity";

	private ListView listView;
	private List<ExamScore> listScores;
	private TextView tvTips;
	private View btnTips;
	private View lvTips;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icourse_display_grade);

		UserJwcInfoDialogFragment userInfoDialog;
		userInfoDialog = new UserJwcInfoDialogFragment(this,
				JwcAction.GET_EXAM_SCORE);
		userInfoDialog.show(getSupportFragmentManager(), "getUserJwcInfo");

		tvTips = (TextView)findViewById(R.id.icourse_display_grade_tips_tv);
		btnTips = findViewById(R.id.icourse_display_greae_tips_btn);
		lvTips = findViewById(R.id.icourse_display_grade_tips_lv);
	}

	private List<ExamScore> getExamScoresFromIntent(Intent intent) {
		List<ExamScore> examScores = new ArrayList<ExamScore>();
		int numberOfExamScores = intent.getExtras().getInt(
				Constant.NUMBER_OF_EXAM_SCORES);
		for (int i = numberOfExamScores; i > 0; i--) {
			
			examScores.add((ExamScore) intent.getExtras()
					.get("examScores_" + i));
		}
		return examScores;
	}

	// 实现标题栏的Home键
	public void btnHomeOnClick(View v) {
		startActivity(new Intent(this, MainActivity.class));
	}

	// 实现标题栏的返回键
	public void btnBackOnClick(View v) {
		finish();
	}

	// 定义广播接受处理事件
	private BroadcastReceiver newGetExamActionReceiver = new BroadcastReceiver() {
		public void onReceive(Context context, Intent intent) {
			// 配置listView的adapter
			listView = (ListView) findViewById(R.id.ischedule_score_lv);
			listScores = getExamScoresFromIntent(intent);
			if(listScores.isEmpty()){
				tvTips.setText("没有课程信息！");   //没有课程的情况
				btnTips.setVisibility(8);		//让按钮消失
			}else{							
					//登录成功，应使提示消失
				tvTips.setVisibility(8);		
				btnTips.setVisibility(8);
				lvTips.setVisibility(8);
			}
			ICourseGradeAdapter adapter = new ICourseGradeAdapter(context,
					listScores);
			listView.setAdapter(adapter);
		}
	};

	@Override
	public void onResume() {
		super.onResume();
		// Activity获得焦点时，注册接受广播
		IntentFilter getExamScoreActionFilter = new IntentFilter(
				Constant.BROADCAST_GET_EXAM_ACTION);
		registerReceiver(newGetExamActionReceiver, getExamScoreActionFilter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// Activity失去焦点时，注销广播
		unregisterReceiver(newGetExamActionReceiver);
	}

	public void btnOnClickLogin(View v){
		UserJwcInfoDialogFragment userInfoDialog;
		userInfoDialog = new UserJwcInfoDialogFragment(this,
				JwcAction.GET_EXAM_SCORE);
		userInfoDialog.show(getSupportFragmentManager(), "getUserJwcInfo");
	}

}
