package aid;

import java.util.ArrayList;

import Enviroments.ChanceFruit;
import Enviroments.Fruit;
import Enviroments.FruitSet;
import Mankind.Creature;
import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mingli.toms.MenuActivity;
import com.mingli.toms.R;
import com.mingli.toms.Render;
import com.mingli.toms.StateWindow;
import com.mingli.toms.World;

/**
 * Created by Administrator on 2016/7/10.
 */

public class Shop {
	private PopupWindow popupWindow;
	MenuActivity acti;
	private ItemAdapter itemadapter;
	int itemcount = 4;
	Fruit selectedItem;
	private View selectedView;
	private OnItemClickListener gridListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			selected(v, position);
		}
	};
	private void selected(View v, int position) {
		if(selectedView!=null)selectedView.setBackgroundResource(R.drawable.whitestroke);
		v.setBackgroundResource(R.drawable.greenrect);
		selectedItem = FruitSet.shopList.get(position);
		instruction.setText(selectedItem.instruction);
		// itemadapter.notifyDataSetChanged();
		selectedView = v;
	}
	private World world;
	// private ArrayList<Fruit> fruList;
	private FruitSet fs;
	private ViewGroup shopadcontainer;
	private TextView instruction;
	private GridView gridView;

	public Shop(MenuActivity acti, World world) {
		this.acti = acti;
		this.world = world;
	}

	public void showWindow(View v) {
		long tim = System.currentTimeMillis();
		if (popupWindow != null && popupWindow.isShowing())
			return;
		else if (popupWindow == null) {
			// TODO Auto-generated method stub
			// 获取自定义布局文件activity_popupwindow_left.xml的视图
			View popupWindow_view = acti.getLayoutInflater().inflate(
					R.layout.shop, null);
			// 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
			popupWindow = new PopupWindow(popupWindow_view,
					WindowManager.LayoutParams.MATCH_PARENT,
					WindowManager.LayoutParams.MATCH_PARENT, true);
			gridView = (GridView) popupWindow_view
					.findViewById(R.id.shopGridView);

			initButton(popupWindow_view);
			shopadcontainer=(ViewGroup) popupWindow_view.findViewById(R.id.shopadcontainer);

			gridView.setNumColumns(itemcount);// ////
			int space = 30;
			gridView.setHorizontalSpacing(space);
			gridView.setVerticalSpacing(space);
			
		

			instruction=(TextView)popupWindow_view.findViewById(R.id.instruction);
			
			fs = world.getFruitSet();
			// fs.setitemWindow(this);
			itemadapter = new ItemAdapter(acti, fs.shopList);

			gridView.setAdapter(itemadapter);
			gridView.setOnItemClickListener(gridListener);
			// gridView.setOnItemLongClickListener(longGridListener);

			// if(itemadapter.fruList==null)itemadapter.fruList=
			// 设置动画效果
			popupWindow.setAnimationStyle(R.style.AnimationFade);

			// 点击其他地方消失
			// if(touchEvent!=null)

			View.OnTouchListener otl = new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					hideCheck();
				
					return true;
				}
			};
			popupWindow_view.setOnTouchListener(otl);
			// 这里是位置显示方式,在屏幕的左侧
		}
		acti.showBanner(shopadcontainer);
		popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
		// popupWindow.showAsDropDown(v);
		Log.i("itempopTime", "" + (System.currentTimeMillis() - tim));
	}

	private void initButton(View v) {
		// TODO Auto-generated method stub
		Button buy = (Button) v.findViewById(R.id.buy);
		
		
		OnClickListener click = new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Creature player=world.player;
				if(selectedItem instanceof ChanceFruit&&player!=null&&!player.isDead){
					setNoneSelected();
					MenuActivity.showDialog("店老板", "现在不用复活", R.drawable.egg);
					return;
				}
				if (selectedItem == null) {
					MenuActivity.showDialog("店老板", "请选择要买的东西", R.drawable.cap);
					return;
				}
				if (acti.coinCount - selectedItem.cost >= 0
						&& acti.chance - selectedItem.chancecost >= 0) {
					fs.buyItem(selectedItem);
					
					fs.useItem(selectedItem);
					
					setNoneSelected();
					hideCheck();
//					popupWindow.dismiss();
				} else {
					if (acti.coinCount - selectedItem.cost < 0)
						MenuActivity.showDialog("", "金币不够！",R.drawable.coinicon);
					else if (acti.chance - selectedItem.chancecost < 0)
						MenuActivity.showDialog("", "生命能量不够！",R.drawable.egg);
				}
//				selectedItem = null;
				itemadapter.notifyDataSetChanged();
			}

			
			
		};
		buy.setOnClickListener(click);
	}
	private void setNoneSelected() {
		selectedItem = null;
		if(selectedView!=null)selectedView.setBackgroundResource(R.drawable.whitestroke);
		instruction.setText("请选择一个商品查看说明");
	}

	public void hideCheck() {
		if (popupWindow != null && popupWindow.isShowing()) {
			popupWindow.dismiss();
			// popupWindow = null;
			acti.resumeGame();
			acti.ad.hideBanner(shopadcontainer);
		}
	}

	public ItemAdapter getItemadapter() {
		return itemadapter;
	}

	public void setItemadapter(ItemAdapter itemadapter) {
		this.itemadapter = itemadapter;
	}

	private int buyLifePosition=-1;
	class ItemAdapter extends BaseAdapter {

		private Context context;
		ArrayList<Fruit> shopList;
//		private View selectedView;

		public ItemAdapter(Context context, ArrayList<Fruit> arrayList) {
			this.context = context;
			shopList = arrayList;

		}

		public int getCount() {
			// TODO Auto-generated method stub
			return shopList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return shopList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
		
			
			View v = null;
			View icon = null;
			TextView name = null;
			TextView cost = null;
			TextView chancecost = null;
			if (convertView == null) {
				v = acti.getLayoutInflater().inflate(R.layout.shopitem, null);

				// convertView.setLayoutParams(new
				// GridView.LayoutParams(ItemWindow.itemWidth,
				// ItemWindow.itemWidth));// bug

			} else {
				v = convertView;
			}
//			selectedView = v;
			icon = v.findViewById(R.id.goodsicon);
			name = (TextView) v.findViewById(R.id.goodsname);
			cost = (TextView) v.findViewById(R.id.goodscost);
			chancecost = (TextView) v.findViewById(R.id.chancecost);
			
			View goodsLayout = v.findViewById(R.id.goodscostlayout);
			View chanceLayout=v.findViewById(R.id.chancecostlayout);
			// 设置大小

			/*
			 * LayoutParams param = v.getLayoutParams(); int
			 * itemWidth=popupWindow.getWidth()/itemcount;
			 * param.width=itemWidth; param.height=itemWidth;
			 * v.setLayoutParams(param);// nobug
			 */

			Fruit f = shopList.get(position);
			icon.setBackgroundResource(f.getIcon());
			name.setText(f.name);
			if(f.cost>0) {
				cost.setText("" + f.cost);
				goodsLayout.setVisibility(View.VISIBLE);
			} else {
				goodsLayout.setVisibility(View.GONE);
			}
		
			if (f.chancecost > 0) {
				chanceLayout.setVisibility(View.VISIBLE);
				chancecost.setText("" + f.chancecost);
			} else {
				chanceLayout.setVisibility(View.GONE);
			}

			if(position==buyLifePosition){
				selected(v,position);
				buyLifePosition=-1;
			}
				
			
			return v;

		}

	}

	public void toBuyLife() {
		// TODO Auto-generated method stub
		buyLifePosition=0;
		gridView.setSelection(0);
	}
}
