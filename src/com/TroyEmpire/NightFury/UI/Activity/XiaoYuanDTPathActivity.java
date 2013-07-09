package com.TroyEmpire.NightFury.UI.Activity;



import com.TroyEmpire.NightFury.Constant.Constant;
import com.TroyEmpire.NightFury.Entity.Building;
import com.TroyEmpire.NightFury.Ghost.IService.IBuildingService;
import com.TroyEmpire.NightFury.Ghost.IService.IMapService;
import com.TroyEmpire.NightFury.Ghost.Service.BuildingService;
import com.TroyEmpire.NightFury.Ghost.Service.MapService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class XiaoYuanDTPathActivity extends Activity implements OnClickListener {

	private ArrayAdapter<String> adapter;
	private IMapService mapService;
	private AutoCompleteTextView sourceTextView;
	private AutoCompleteTextView destTextView;
	private IBuildingService buildingService;
	private TextView noticeTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme);
		setContentView(R.layout.activity_xiaoyuandt_path);

		TextView titleTV = (TextView) findViewById(R.id.title_text);
		titleTV.setText(Constant.XIAOYUANDT_PATH_TITLE);

		this.mapService = new MapService(this);
		this.buildingService = new BuildingService(1);
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line);

		// set the click ȷ�� Button
		Button submitButton = (Button) findViewById(R.id.xiaoyuandt_path_submit_button);
		submitButton.setOnClickListener(this);
		noticeTextView = (TextView) findViewById(R.id.xiaoyuandt_path_notice_textview);

		// the the auto text view
		adapter.setNotifyOnChange(true);
		sourceTextView = (AutoCompleteTextView) findViewById(R.id.xiaoyuandt_path_source_edittext);
		destTextView = (AutoCompleteTextView) findViewById(R.id.xiaoyuandt_path_dest_edittext);
		sourceTextView.setAdapter(adapter);
		destTextView.setAdapter(adapter);
		sourceTextView.setThreshold(1);
		destTextView.setThreshold(1);
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
		sourceTextView.addTextChangedListener(textChecker);
		destTextView.addTextChangedListener(textChecker);
	}

	// ʵ�ֱ�������Home��
	public void btnHomeOnClick(View v) {
		startActivity(new Intent(this, MainActivity.class));
	}

	// ʵ�ֱ������ķ��ؼ�
	public void btnBackOnClick(View v) {
		finish();
	}

	// submit search source and destinations
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.xiaoyuandt_path_submit_button: {
			String sourceName = sourceTextView.getText().toString();
			String destName = destTextView.getText().toString();
			Building sourceBuilding = buildingService
					.getBuildingByCellName(sourceName);
			Building destBuilding = buildingService
					.getBuildingByCellName(destName);
			if (sourceBuilding.getId() == 0 && destBuilding.getId() == 0) {
				noticeTextView.setText("δ֪����" + sourceName + "\nδ֪����"
						+ destName);
				return;
			} else if (sourceBuilding.getId() == 0) {
				noticeTextView.setText("δ֪����" + sourceName);
				return;
			} else if (destBuilding.getId() == 0) {
				noticeTextView.setText("δ֪����" + destName);
				return;
			}
			Intent displayPathIntent = new Intent(this,
					XiaoYuanDTDisplayPathActivity.class);
			displayPathIntent.putExtra("sourceBuilding", sourceBuilding);
			displayPathIntent.putExtra("destBuilding", destBuilding);
			startActivity(displayPathIntent);
		}
		}

	}
}