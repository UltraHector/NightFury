package com.TroyEmpire.NightFury.UI.Activity;

import java.util.ArrayList;
import java.util.List;

import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.UI.Adapter.MyViewPagerAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

public class IntroductionActivity extends Activity {

	private ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_introduction);

		viewPager = (ViewPager) findViewById(R.id.introduction_viewpager);

		LayoutInflater mLi = LayoutInflater.from(this);
		View view1 = mLi.inflate(R.layout.introduction_flip01, null);
		View view2 = mLi.inflate(R.layout.introduction_flip02, null);
		View view3 = mLi.inflate(R.layout.introduction_flip03, null);
		View view4 = mLi.inflate(R.layout.introduction_flip04, null);

		final List<View> views = new ArrayList<View>();
		views.add(view1);
		views.add(view2);
		views.add(view3);
		views.add(view4);

		MyViewPagerAdapter mPagerAdapter = new MyViewPagerAdapter(views);

		viewPager.setAdapter(mPagerAdapter);
	}
	
	/** 
     *  
     * method desc：设置已经引导过了，下次启动不用再次引导 
     */  
    private void setGuided() {  
        SharedPreferences preferences = this.getSharedPreferences(  
                Constant.SHARED_PREFERENCE_NIGHT_FURY, Context.MODE_PRIVATE);  
        if(preferences.getBoolean(Constant.FIRST_TIME_TO_USE_KEY, false) == false){
        	Editor editor = preferences.edit();  
            // 存入数据  
            editor.putBoolean(Constant.FIRST_TIME_TO_USE_KEY, true);  
            // 提交修改  
            editor.commit();  
        }
    }  

	// 实现标题栏的Home键
	public void letsGoOnClick(View v) {
		setGuided();
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}
}
