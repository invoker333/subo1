package com.mingli.toms;

import java.util.ArrayList;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class UserAdapter extends BaseAdapter {

	private BattleActivity acti;
	public ArrayList<UserRoom>userList=new ArrayList<UserRoom>();

	public UserAdapter(BattleActivity acti) {
		this.acti = acti;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return userList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return userList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v=null;
		if(convertView==null){
			v=acti.getLayoutInflater().inflate(R.layout.roomuseritem	,null);
		}else v=convertView;
		try{
			UserRoom ur=userList.get(position);
			
			TextView roomUserName=(TextView) v.findViewById(R.id.roomUserName);
			TextView roomUserScore=(TextView) v.findViewById(R.id.roomUserScore);
			roomUserName.setText(""+ur.name);
			roomUserScore.setText(""+ur.score);
		}catch(Exception e){
			e.printStackTrace();
		}
	
		
		return v;
	}

}
