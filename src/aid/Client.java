package aid;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import Mankind.Player;

import com.mingli.toms.MenuActivity;
import com.mingli.toms.World;

public class Client implements Runnable{
	
	Socket s;
	static DataInputStream dis;
	static DataOutputStream dos;
	boolean connected;
	Thread recThread;
	private MenuActivity acti;
	public Client(MenuActivity acti){
		this.acti = acti;
		
	}
	public void connect(){
		while(!connected){
			World.timeRush(1000);
			if(!MenuActivity.isNetworkAvailable(acti))continue;
			Log.i("尝试连接网络");
			try {
//				s=new Socket("127.0.0.1",8888);
//				s=new Socket("192.168.137.1",8888);//祖传wifi
//				s=new Socket("192.168.25.123",8888);
//				s=new Socket("23.105.206.67",8888);
//				s=new Socket("192.168.46.28",8888);
//				s=new Socket("192.168.47.134",8888);
//				s=new Socket("192.168.47.251",8888);
//				s=new Socket("192.168.47.176",8888);
//				s=new Socket("192.168.47.73",8888);
//				s=new Socket("23.105.206.67",8888);//雷志豪
//				s=new Socket("192.168.65.146",8888);
				s=new Socket("192.168.4.243",8888);
				if(s!=null){
					Log.i("已连接","");
					connected=true;
					dis=new DataInputStream(s.getInputStream());
					dos=new DataOutputStream(s.getOutputStream());
					send(ConsWhenConnecting.THIS_IS_USER_ID_AND_NAME+acti.userId+" "+acti.userName);//sendUserNameFirst
					(recThread=new Thread(this)).start();
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			
		}
	}

	int threadCount=0;
	public void run(){
		String s;
		while(connected){
			try {
				if((s=dis.readUTF())!=null){
//					Log.i("",s+"\n");				
					handleReceiveMessage(s);
//					World.timeRush(100);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				connected=false;
			}
			//ClientWindow.timeRush();//��ʱ����
		}
		Log.i("断开连接");
	}
	private void handleReceiveMessage(String s) {
		
		if(s.startsWith(ConsWhenConnecting.THIS_IS_NEW_USER_ID)) {
			if(MenuActivity.userId<10)acti.saveUserId(s.substring(ConsWhenConnecting.THIS_IS_NEW_USER_ID.length()));
			else Log.i("该用户已经有id了");
		}
		else 	if(s.startsWith(ConsWhenConnecting.THIS_IS_PAIMING)) {
			acti.savePaiming(s.substring(ConsWhenConnecting.THIS_IS_PAIMING.length()));
			acti.initPaimingInfo();
		}	
		else 	if(s.startsWith(ConsWhenConnecting.THIS_IS_ONLINE_STAGE)) {
			String ss=s.substring(ConsWhenConnecting.THIS_IS_ONLINE_STAGE.length());
			acti.showOnlineStage(ss);
		}	
		else 	if(s.startsWith(ConsWhenConnecting.THIS_IS_THE_SELECTED_ONLINE_STAGE)) {
			String ss=s.substring(ConsWhenConnecting.THIS_IS_THE_SELECTED_ONLINE_STAGE.length());
			acti.getTheOnlineStage(ss);
		}else 	if(s.startsWith(ConsWhenConnecting.BATTLE_ACTION_MESSAGE)) {
			String ss=s.substring(ConsWhenConnecting.BATTLE_ACTION_MESSAGE.length());
			acti.battleAction(ss);
		}
		else 	if(s.startsWith(ConsWhenConnecting.THIS_IS_ROOMSET_MESSAGE)) {
			String ss=s.substring(ConsWhenConnecting.THIS_IS_ROOMSET_MESSAGE.length());
			acti.roomSetMessage(ss);
		}else 	if(s.startsWith(ConsWhenConnecting.THIS_IS_SELECTED_ROOM_MESSAGE)) {
			String ss=s.substring(ConsWhenConnecting.THIS_IS_SELECTED_ROOM_MESSAGE.length());
			acti.roomOneInfo(ss);
		}else 	if(s.startsWith(ConsWhenConnecting.THIS_ID_IS_BLUE_TEAM)) {
			String strRes=s.substring(ConsWhenConnecting.THIS_ID_IS_BLUE_TEAM.length());
			int userId=Integer.parseInt(strRes);
			acti.addForce(World.BLUE_FORCE,userId);
		}
		else 	if(s.startsWith(ConsWhenConnecting.THIS_ID_IS_RED_TEAM)) {
			String strRes=s.substring(ConsWhenConnecting.THIS_ID_IS_RED_TEAM.length());
			int userId=Integer.parseInt(strRes);
			acti.addForce(World.RED_FORCE,userId);
		}else 	if(s.startsWith(ConsWhenConnecting.THIS_IS_BATTLE_MESSAGE)) {
			String strRes=s.substring(ConsWhenConnecting.THIS_IS_BATTLE_MESSAGE.length());
			acti.battleAction(strRes);
		}
		
		
		String substring = s.substring(0, 2);
//		Log.i("subString"+substring);
		if (substring.equals("da")) {
			Player.downData[0]=true;
			Player.downData[1]=false;
		} else if (substring.equals("dd")) {
			Player.downData[1]=true;
			Player.downData[0]=false;
		} else if (substring.equals("ua")) {
			Player.downData[0]=false;
		} else if (substring.equals("ud")) {
			Player.downData[1]=false;
		} else if (substring.equals("d7")) {
			Player.downData[2]=true;
		} else if (substring.equals("u7")) {
			Player.downData[2]=false;
		}
		 else if (substring.equals("d8")) {
				Player.downData[3]=true;
				Player.jumpProgress=100;
			} else if (substring.equals("u8")) {
				Player.downData[3]=false;
			}
	}
	public static void  send(String str){
//		if(MenuActivity.userId<10)str=ConsWhenConnecting.REQUEST_NEW_USER_ID;
		try {
			if(dos!=null)dos.writeUTF(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void closeStream(){
		try {
			if(s==null)return;
			s.close();
			dis.close();
			dos.close();
			connected=false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
