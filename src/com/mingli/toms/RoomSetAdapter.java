package com.mingli.toms;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


public class RoomSetAdapter extends BaseAdapter {
	ArrayList<RoomClient>rcList=new ArrayList<RoomClient>();
	private BattleActivity acti;
	public RoomSetAdapter(BattleActivity acti) {
		this.acti = acti;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rcList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return rcList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v=null;
		if(convertView==null){
			v=acti.getLayoutInflater().inflate(R.layout.roomsetitem	,null);
		}else v=convertView;
		try{
			RoomClient room=rcList.get(position);
			TextView roomId=(TextView) v.findViewById(R.id.roomId);
			TextView roomSize=(TextView) v.findViewById(R.id.roomSize);
			roomId.setText(""+room.roomId);
			roomSize.setText(""+room.roomSize+"/"+room.roomMax);
		}catch(Exception e){
			e.printStackTrace();
		}
		return v;
	}

}
