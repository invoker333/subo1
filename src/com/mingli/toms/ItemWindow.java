package com.mingli.toms;

import java.util.ArrayList;

import Enviroments.Fruit;
import Enviroments.FruitSet;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

/**
 * Created by Administrator on 2016/7/10.
 */

public class ItemWindow {
	private GridView slideWindow;
	 Activity acti;
	private ItemAdapter itemadapter;
	final  int itemCount = 5;
	private OnItemClickListener gridListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			fs.useItem(position);
			itemadapter.notifyDataSetChanged();
		}
	};
	private World world;
	// private ArrayList<Fruit> fruList;
	private FruitSet fs;
	SlidingDrawer sd;
	private OnDrawerOpenListener onDrawerOpenListener=new OnDrawerOpenListener() {
		
		@Override
		public void onDrawerOpened() {
			// TODO Auto-generated method stub
			initWindow( );
		}
	};

	ItemWindow(MenuActivity acti, SlidingDrawer sd,World world) {
		this.acti = acti;
		this.world = world;
		this.sd=sd;
		FruitSet.initItemList(acti.item);
//		sd.setOnDrawerOpenListener(onDrawerOpenListener);
	}

	public void initWindow() {
//		long tim = System.currentTimeMillis();
		
		slideWindow = (GridView) sd.findViewById(R.id.itemGridView);
		slideWindow.setNumColumns(itemCount);// ////
		
		int space = 3;
		slideWindow.setHorizontalSpacing(space);
		slideWindow.setVerticalSpacing(space);
		
		Log.i("world.fs"+world.getFruitSet());
		fs = world.getFruitSet();
		itemadapter = new ItemAdapter(slideWindow.getContext(),
				fs.getItemList());
		
		slideWindow.setAdapter(itemadapter);
		slideWindow.setOnItemClickListener(gridListener);
		
		
//		slideWindow.setOnTouchListener(new OnTouchListener() {
//			@Override
//			public boolean onTouch(View arg0, MotionEvent arg1) {
//				// TODO Auto-generated method stub
//				sd.close();
//				return false;
//			}
//		});
//		Log.i("itempopTime", "" + (System.currentTimeMillis() - tim));
	}

	public ItemAdapter getItemadapter() {
		return itemadapter;
	}

	public void setItemadapter(ItemAdapter itemadapter) {
		this.itemadapter = itemadapter;
	}
	class ItemAdapter extends BaseAdapter {

		private Context context;
		ArrayList<Fruit> fruList;

		public ItemAdapter(Context context, ArrayList<Fruit> arrayList) {
			this.context = context;
			fruList = arrayList;
		}

		public int getCount() {
			// TODO Auto-generated method stub
			return fruList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return fruList.get(position);
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
			View im = null ;
			if (convertView == null) {
				v = ((Activity) context).getLayoutInflater().inflate(
						R.layout.biankuang, null);

				// convertView.setLayoutParams(new
				// GridView.LayoutParams(ItemWindow.itemWidth,
				// ItemWindow.itemWidth));// bug

				// 设置大小


			} else {
				v=convertView;
			}
			
			im =  v.findViewById(R.id.iv_image);
			LayoutParams param = im.getLayoutParams();
			int itemWidth = (MenuActivity.screenWidth-slideWindow.getWidth())/(itemCount-1);
			param.width = itemWidth;
			param.height = itemWidth;
			// im.setBackground("#111111");
			im.setLayoutParams(param);// nobug
			
			im.setBackgroundResource((fruList.get(position).getIcon()));
			return v;

		}

	}
	public void closeDrawer() {
		// TODO Auto-generated method stub
		if(sd!=null)sd.close();
	}
	void hide(){
		((ViewGroup) sd.getParent()).removeView(sd);
	}

	public void freshItem() {
		// TODO Auto-generated method stub
		itemadapter.notifyDataSetChanged();
	}
}

