package aid;
/**
 *UDPClient
 *@author Winty wintys@gmail.com
 *@version 2008-12-15
 */
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
public class UdpReceiver{
	 private String strReceive;
	private boolean connected;
	public static void main(String[] args) {  
		UdpReceiver ur = new UdpReceiver();
		try {
			ur.receiveAlways( new DatagramSocket(8080)  );
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }  
	 public void receiveAlways( DatagramSocket ds ){
		 connected=true;
	        try {  
	            //UDP接收端  
	           
	            //定义将UDP的数据包接收到什么地方  
	            byte[] buf = new byte[1024];  
	            //定义UDP的数据接收包  
	            DatagramPacket dp = new DatagramPacket(buf, buf.length);  
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
	public void closeStream() {
		// TODO Auto-generated method stub
		connected=false;
	}
}