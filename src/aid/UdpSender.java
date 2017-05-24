package aid;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;

public class UdpSender implements Runnable{
	private String str="";
	DatagramSocket dsSend;
	private DatagramSocket dsReceive;
	private DatagramPacket datagramPacket;
	
	private String strReceive;
	private boolean connected;

	public void connect(String address,int port) {
		InetSocketAddress inetSocketAddress = new InetSocketAddress(
				address, port);
		// 定义一个UDP的Socket来发送数据
		// 定义一个UDP的数据发送包来发送数据，inetSocketAddress表示要接收的地址
		try {
			dsSend = new DatagramSocket();
			datagramPacket = new DatagramPacket(str.getBytes(),
					str.getBytes().length, inetSocketAddress);
			

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void send(String str) {
		datagramPacket.setData(str.getBytes());
		try {
			dsSend.send(datagramPacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		UdpSender us=new UdpSender();
		us.connect("172.0.0.1",8080);
		for(int i=0;i<10;i++){
			us.str="str"+i;
			us.send(us.str);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void closeStream() {
		// TODO Auto-generated method stub
		if(dsSend!=null)dsSend.close();
		connected=false;
	}
	public void receiveAlways( DatagramSocket ds ){
		 connected=true;
	        try { 
	            //UDP接收端  
	           
	            //定义将UDP的数据包接收到什么地方  
	            byte[] buf = new byte[1024];  
	            //定义UDP的数据接收包  
	            DatagramPacket dp = new DatagramPacket(buf, buf.length);  
	           dp.getSocketAddress();
	            while (connected) {  
	                //接收数据包  
	                ds.receive(dp);  
	                strReceive = new String(dp.getData(), 0, dp.getLength());  
	                handleDatagramPacket(strReceive);  
	            }  
	        } catch (SocketException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        } finally {  
	        	connected=false;
	            if (ds != null)   
	                ds.close();  
	        }  
	 }
	 protected void handleDatagramPacket(String strReceive2) {
		
		System.out.println("length:" + strReceive2.length()+ "->" + strReceive);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		receiveAlways(dsReceive);
	}

	public void startReceive(DatagramSocket dsReceive) {
		this.dsReceive = dsReceive;
		// TODO Auto-generated method stub
		new Thread(this).start();//start receive
	}
}
