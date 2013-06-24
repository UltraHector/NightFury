package com.TroyEmpire.NightFury.UI.Activity;

/**
 * @author： GJ10
 * @time: 2013/5/29
 * @Usage: 外卖系统的主页，用于展示所有餐店信息，
 *         主要是一个expandableListView的展示，
 *         以及长按菜单的实现。
 */

import java.util.List;

import com.TroyEmpire.NightFury.Entity.Restaurant;
import com.TroyEmpire.NightFury.Ghost.IService.ICallInMealService;
import com.TroyEmpire.NightFury.Ghost.Service.CallInMealService;
import com.TroyEmpire.NightFury.UI.Adapter.RestaurantExpandableAdapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;


public class WaiMaiXTActivity extends Activity 
{	
	ExpandableListView restaurantELV;
	RestaurantExpandableAdapter restaurantAdapter;
	// 需要的接口服务:餐店数据库服务restService、初始化数据库服务dataService
	private ICallInMealService restService = new CallInMealService(1);
	//收藏夹和全部的位置号
	private final static int FAVORITE_GROUP_POS = 0;
	//长按菜单选项
	private final static int MENU_ADD_FAVORITE = 0;
	private final static int MENU_DELETE_FAVORITE = 1;
	private final static int MENU_CALL = 2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waimaixt);

		TextView titleText = (TextView) findViewById(R.id.title_text);
		titleText.setText(R.string.waimaixt_label);
		
		//找到ExpandableListView
		restaurantELV = (ExpandableListView)findViewById(R.id.waimaixt_expandablelistview);
		
		//配置adapter，用的是自定义的adapter,构造时加载餐店数据
		restaurantAdapter = new RestaurantExpandableAdapter(this);
		restaurantELV.setAdapter(restaurantAdapter);
		
		registerForContextMenu(restaurantELV);		//注册菜单
		
		restaurantELV.expandGroup(FAVORITE_GROUP_POS);			//自动展开收藏夹
		
		
		/*
		 * 点击事件，进入餐店主页
		 */
		restaurantELV.setOnChildClickListener(new OnChildClickListener() {  
            @Override  
            public boolean onChildClick(ExpandableListView elv, View v, 
            		int groupPosition, int childPosition, long id) {  
 
            	Intent restaurantIntent = new Intent(WaiMaiXTActivity.this,WaiMaiXTRestaurantActivity.class);
            	Long restaurantId = ((Restaurant)(elv.getExpandableListAdapter().
            			getChild(groupPosition, childPosition))).getId();
            	restaurantIntent.putExtra("restaurantId", restaurantId);
            	startActivity(restaurantIntent); 
               return false;
            }  
        });  
	}
	
	//创建长按菜单信息
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		super.onCreateContextMenu(menu, v, menuInfo);
		ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo)menuInfo;
		int type = ExpandableListView.getPackedPositionType(info.packedPosition);
		//这里只处理子项的长按
		if(type == ExpandableListView.PACKED_POSITION_TYPE_CHILD){
			String title = ((TextView) info.targetView.findViewById(R.id.waimaixt_item_title))
					.getText().toString();
			menu.setHeaderTitle(title);
			int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); 
			if(groupPos == FAVORITE_GROUP_POS){ //
				menu.add(0, MENU_DELETE_FAVORITE, 0, "从收藏夹移除");
			}else{
				menu.add(1, MENU_ADD_FAVORITE, 0, "加入收藏夹");
			}
			menu.add(0, MENU_CALL, 0, "马上拨号订餐");
		}
	}
	
	public void toastShow(String text)
	{
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}
	
	
	//菜单项选择事件处理
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		ExpandableListContextMenuInfo menuInfo = (ExpandableListContextMenuInfo)item.getMenuInfo();
		int type = ExpandableListView.getPackedPositionType(menuInfo.packedPosition);
		if(type == ExpandableListView.PACKED_POSITION_TYPE_CHILD){
			Log.i("type","child");
	
			String childName = ((TextView)menuInfo.targetView.findViewById(R.id.waimaixt_item_title)).getText().toString();
			int groupPos = ExpandableListView.getPackedPositionGroup(menuInfo.packedPosition); 
	        int childPos = ExpandableListView.getPackedPositionChild(menuInfo.packedPosition);
	        List<List<Restaurant>> restaurantChilds = this.restaurantAdapter.getRestaurantChilds();
	        Restaurant selectedItem = restaurantChilds.get(groupPos).get(childPos);
			switch(item.getItemId()){
			case MENU_ADD_FAVORITE:			//加入收藏夹
				Log.d("add","add");
				if(selectedItem.getBookmarked() == 0){  //判断是否重复
					restService.setRestautantToBookmarked(selectedItem.getId());
					selectedItem.setBookmarked(1);
					restaurantChilds.get(FAVORITE_GROUP_POS).add(selectedItem);
					toastShow(childName + " 添加成功");
				}
				else
					toastShow(childName + " 已在收藏夹");
				restaurantELV.collapseGroup(FAVORITE_GROUP_POS);
				restaurantELV.expandGroup(FAVORITE_GROUP_POS);
				Log.d("add",selectedItem.getName());
				break;
			case MENU_DELETE_FAVORITE:		//移出收藏夹
				Log.d("remove","remove");
				restService.unsetRestaurantBookmarked(selectedItem.getId());
				selectedItem.setBookmarked(0);
				restaurantChilds.get(FAVORITE_GROUP_POS).remove(selectedItem);
				toastShow(childName + "移除成功");
				restaurantELV.collapseGroup(FAVORITE_GROUP_POS);
				restaurantELV.expandGroup(FAVORITE_GROUP_POS);
				Log.d("remove",selectedItem.getName());
				break;
			case MENU_CALL:					//直接拨订餐
				Log.d("call","call");
				Intent callIntent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + selectedItem.getPhoneNumber()));
				startActivity(callIntent);
				break;
			}
		}else{
			Log.i("type", "group");
		}
		return true;
	}
	

	// 实现标题栏的返回键
	public void btnBackOnClick(View v) {
		finish();
	}

}

