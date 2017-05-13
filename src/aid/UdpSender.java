package aid;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class UdpSender {
	private String str="";
	DatagramSocket datagramSocket;
	private DatagramPacket datagramPacket;

	public void connect(String address,int port) {
		InetSocketAddress inetSocketAddress = new InetSocketAddress(
				address, port);
		// 定义一个UDP的Socket来发送数据
		// 定义一个UDP的数据发送包来发送数据，inetSocketAddress表示要接收的地址
		try {
			datagramSocket = new DatagramSocket();
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
			datagramSocket.send(datagramPacket);
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
		if(datagramSocket!=null)datagramSocket.close();
	}
}
