package com.mingli.toms;

public class Log{
	public static void i(String str,String str1){
		android.util.Log.i(str, str1);
	}
	public static void i(String str){
		android.util.Log.i(" ", str);
	}
	public static void v(String str,String str1){
		android.util.Log.v(str, str1);
	}
	public static void d(String str1){
		// TODO Auto-generated method stub
		android.util.Log.d("", str1);
	}
}
