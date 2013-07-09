package com.TroyEmpire.NightFury.UI.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.TroyEmpire.NightFury.Entity.YellowPageUnit;
import com.TroyEmpire.NightFury.Ghost.IService.IYellowPageService;
import com.TroyEmpire.NightFury.Ghost.Service.YellowPageService;

public class XiaoYuanHYActivity extends Activity {

	private IYellowPageService yellowPageService = new YellowPageService(1);

	private List<HashMap<String, String>> listItem ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_xiaoyuanhy);

		// Set the title bar text as this activity label
		TextView titleText = (TextView) findViewById(R.id.title_text);
		titleText.setText(R.string.xiaoyuanhy_label);

		ListView lv = (ListView) findViewById(R.id.xiaoyuanhy_lv);
		List<YellowPageUnit> phones = yellowPageService.getAllYellowPageUnits();
		listItem = new ArrayList<HashMap<String, String>>();
		for (YellowPageUnit phone : phones) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", phone.getName());
			map.put("phoneNum", phone.getPhoneNumber());
			listItem.add(map);
		}

		SimpleAdapter listItemAdapter = new SimpleAdapter(this, listItem,
				R.layout.xiaoyuanhy_lv_item,
				new String[] { "name", "phoneNum" }, new int[] {
						R.id.xiaoyuanhy_item_department_tv,
						R.id.xiaoyuanhy_item_phone_tv });
		lv.setAdapter(listItemAdapter);
		
		//点击打电话
		lv.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
				String restaurantPhoneNumber = listItem.get(position).get("phoneNum");
				Intent callIntent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + restaurantPhoneNumber));
				startActivity(callIntent);
			}
		});
	}
	

	// 实现标题栏的Home键
	public void btnHomeOnClick(View v) {
		startActivity(new Intent(this, MainActivity.class));
	}

	// 实现标题栏的返回键
	public void btnBackOnClick(View v) {
		finish();
	}
}
