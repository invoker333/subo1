package aid;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import Mankind.Player;

import com.mingli.toms.World;

public class Client implements Runnable{
	Socket s;
	static DataInputStream dis;
	static DataOutputStream dos;
	boolean connected;
	Thread recThread;
	
	public void connect(){
		while(!connected){
			try {
//				s=new Socket("127.0.0.1",8888);
				s=new Socket("192.168.137.1",8888);
//				s=new Socket("192.168.47.134",8888);
				if(s!=null){
					Log.i("已连接","");
					connected=true;
					dis=new DataInputStream(s.getInputStream());
					dos=new DataOutputStream(s.getOutputStream());
					(recThread=new Thread(this)).start();
				}
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			World.timeRush(1000);
			Log.i("尝试连接网络");
		}
	}
	public void run(){
		String s;
		while(connected){
			try {
				if((s=dis.readUTF())!=null){
					Log.i("",s+"\n");				

					handleReceiveMessage(s);
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				connected=false;
			}
			//ClientWindow.timeRush();//��ʱ����
		}
		
	}
	private void handleReceiveMessage(String s) {
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
				Player.jumpRate=2;
			} else if (substring.equals("u8")) {
				Player.downData[3]=false;
			}
	}
	static void  send(String str){
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
