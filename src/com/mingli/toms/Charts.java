package com.mingli.toms;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Charts {
	ListView lv;
	private chartsAdapter shopadapter;
	private  View acti;
	Charts(View acti){
		this.acti = acti;
		lv=(ListView) acti.findViewById(R.id.charts);
		lv.setAdapter(shopadapter);
	}
	class chartsAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int arg0, View v, ViewGroup arg2) {
			// TODO Auto-generated method stub
			View v1;
			if(v==null){
//				v1=Layout.findViewById(R.layout.chartitem);
			}
			return null;
		}
		
	}
}
