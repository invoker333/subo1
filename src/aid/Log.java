package aid;

public class Log{
	private static int degugId;
	public static void i(String str,String str1){
		android.util.Log.i(str, str1);
	}
	public static void i(String str){
		android.util.Log.i(" ", str);
		Client.send("i"+str);
	}
	public static void v(String str,String str1){
		android.util.Log.v(str, str1);
	}
	public static void d(String str){
		// TODO Auto-generated method stub
		android.util.Log.d("", str);
		Client.send("d"+str);
	}
	public static void waitDebug(int max){
		if(degugId++>max){
			degugId=0;
		}
	}
}
