package com.mingli.toms;

import java.util.ArrayList;

import aid.Client;
import aid.ConsWhenConnecting;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class BattleActivity extends Activity {
	protected static final int REFRESH_ROOMSET = 0;
	protected static final int REFRESH_THIS_ROOM = 1;
	ArrayList<RoomClient>rcList=new ArrayList<RoomClient>();
	ArrayList<UserRoom>userList=new ArrayList<UserRoom>();
	private RoomSetAdapter roomSetAdapter;
	private UserAdapter userAdapter;
	private Handler handler;
	public  static int roomId;
	private TabHost th;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_battle);
		th = (TabHost) findViewById(R.id.tabhost);
		th.setup();
		th.addTab(th.newTabSpec("tab1")
				.setIndicator("房间",getResources().getDrawable(R.drawable.ic_launcher))
				.setContent(R.id.tab1));
		th.addTab(th.newTabSpec("tab2").setIndicator("阵营", null)
				.setContent(R.id.tab2));
		
		View v = findViewById(R.id.tab2);
		v.setAnimation(new TranslateAnimation(-100, 0, 0, 0));
		
		MenuActivity.battleActi=this;
		
		initRoomSet();
		initOneRoom();
		
		
		initHandler();
	}

	private void initOneRoom() {
		// TODO Auto-generated method stub
		ListView friendListView=(ListView) findViewById(R.id.friendListView);
		friendListView.setAdapter(userAdapter=new UserAdapter(this));
		
		
		View start=findViewById(R.id.startInRoomModle);
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Client.send(ConsWhenConnecting.REQUEST_START_BATTLE+roomId);
				finish();
			}
		});
	}
	public void finish(){
		super.finish();
		
	}

	private void initHandler() {
		if(handler==null)handler = new Handler(){
			public void handleMessage(Message msg){
				switch(msg.what){
				case REFRESH_ROOMSET:roomSetAdapter.notifyDataSetChanged();break;
				case REFRESH_THIS_ROOM:userAdapter.notifyDataSetChanged();break;
				}
			}
		};
	}

	private void initRoomSet() {
		// TODO Auto-generated method stub
		ListView roomView = (ListView)findViewById(R.id.roomSetView);
		roomView.setAdapter(roomSetAdapter=new RoomSetAdapter(this));
		
		roomView.setOnItemClickListener(new OnItemClickListener() {


			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				RoomClient room = rcList.get(position);
				roomId = room.roomId;
				TextView teamTitle=(TextView) findViewById(R.id.roomNameAndTeamTitle);
				teamTitle.setText(roomId+"号房间");
				
				Client.send(ConsWhenConnecting.REQUEST_THIS_ROOM+roomId);
				th.setCurrentTab(1);//跳转
			}
		});
		Client.send(ConsWhenConnecting.REQUEST_ALL_ROOM_INFO);
	}

	public void roomSetMessage(String ss) {
		// TODO Auto-generated method stub
		rcList.clear();
		String[] strSet = ss.split(" ");
		for(int i=1;i<strSet.length-1;i+=2){// i=1 because " 1" 空格" "spilt has 2
			RoomClient rc=new RoomClient(strSet[i],strSet[i+1]);
			rcList.add(rc);
		}
		roomSetAdapter.rcList=(ArrayList<RoomClient>) rcList.clone();
		handler.sendEmptyMessage(REFRESH_ROOMSET);
	}

	public void roomOneInfo(String ss) {
		// TODO Auto-generated method stub
		userList.clear();
		String[] strSet = ss.split(" ");
		for(int i=1;i<strSet.length-1;i+=2){// i=1 because " 1" 空格" "spilt has 2
			UserRoom ur=new UserRoom(strSet[i],strSet[i+1]);
			userList.add(ur);
		}
		userAdapter.userList=(ArrayList<UserRoom>) userList.clone();
		handler.sendEmptyMessage(REFRESH_THIS_ROOM);
	}
}
class UserRoom{
	public UserRoom(String name, String score) {
		this.name = name;
		this.score = score;
	}
	String name;
	String score;
}