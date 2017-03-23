package com.mingli.toms;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;


public class Map {
	byte[] data;
	char[] charData;
	static int max=12;//关卡总数
	

	public Map(int mapIndex,Context context){
		InputStream is;
		ByteArrayOutputStream baos;
		AssetManager am=context.getAssets();
		String mapFile = null;

		
		mapIndex=mapIndex%(max+1);//关卡循环机制
		mapFile="w"+mapIndex+".txt";//关卡文件位置

		try {
			is=am.open(mapFile);
			baos=new ByteArrayOutputStream();
			byte[]buffer=new byte[1024];
			int len=0;
			while((len=is.read(buffer))!=-1){
				baos.write(buffer, 0, len);
			}
			data=baos.toByteArray();
//			Log.i(new String(new String(baos.toByteArray()).toCharArray()));
			charData=new String(baos.toByteArray()).toCharArray();
			baos.close();
			is.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public Map(File f,Context context){
		InputStream is;
		ByteArrayOutputStream baos;

		try {
			is=new FileInputStream(f);
			baos=new ByteArrayOutputStream();
			byte[]buffer=new byte[1024];
			int len=0;
			while((len=is.read(buffer))!=-1){
				baos.write(buffer, 0, len);
			}
			data=baos.toByteArray();
//			Log.i(new String(new String(baos.toByteArray()).toCharArray()));
			charData=new String(baos.toByteArray()).toCharArray();
			baos.close();
			is.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void saveMap(byte[]s,File f){
		OutputStream os;
		ByteArrayInputStream bais;
		try {
			
			os=new FileOutputStream(f);
			bais=new ByteArrayInputStream(s);
			os.write(s);
			
			bais.close();
			os.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
