package aid;

import java.util.ArrayList;

import Enviroments.GrassSet;

import com.mingli.toms.Map;
import com.mingli.toms.World;

public class RandomMap {
	private World world;

	public RandomMap(World world){
		this.world = world;
		
	}
	public char[	] getRandomWholeMap(int width,int height){
		char[][] data = new char[(height)*12][width*12];
//		if(whole.isEmpty())initMapWholeItem();
	 
		int start = 0;int end = 0;
		while (end<data[0].length){
			end+=12;
			int ran=(int) (Math.random()*whole.size());
			for (int i = 0; i < 12; i++) {
				for (int j = start; j < end; j++) {
					data[i][j]=whole.get(ran)[i][j-start];
				}
			}
			start+=12;
		}
	 
		
		char[] mapsequence = array2to1(data);
		
		return mapsequence ;
	 }

	private char[] array2to1(char[][] data) {
		int mapWidth=data[0].length;
		int mapHeight=data.length;
		char[]mapsequence=new char[mapWidth*2  *mapHeight];
		for(int j=0;j<mapHeight;j++){
			for(int i=0;i<mapWidth;i++){
				mapsequence[2*(j*mapWidth+i)]=data[j][i];
				mapsequence[2*(j*mapWidth+i)+1]='	';// tab
			}
			mapsequence[2*(j*mapWidth+mapWidth-1)+1]=13;// /r/n
		}
//		String str="";
//		for(int i=0;i<mapsequence.length;i++)str+=mapsequence[i];
//		Log.i("random mapsequence"+str);
		return mapsequence;
	}
public char[	] getRandom333Map(int width,int height){
		char[][] data = new char[(height)*12][width*12];
		if(top.isEmpty()) initMap333Item();
	 
		int start = 0;int end = 0;
		while (end<data[0].length){
			end+=12;
			int ran=(int) (Math.random()*top.size());
			for (int i = 0; i < 2; i++) {
				for (int j = start; j < end; j++) {
					data[i][j]=top.get(ran)[i][j-start];
				}
			}
			ran=(int) (Math.random()*mid.size());
			for (int i = 2; i < 10; i++) {
				for (int j = start; j < end; j++) {
					data[i][j]=mid.get(ran)[i-2][j-start];
				}
			}
			ran=(int) (Math.random()*bottom.size());
			for (int i = 10; i < 12; i++) {
				for (int j = start; j < end; j++) {
					data[i][j]=bottom.	get(ran)[i-10][j-start];
				}
			}
			start+=12;
		}
	 
		
		char[] mapsequence = array2to1(data);
		
		return mapsequence ;
	 }
public void setWholeItem(Map map) {
	char[][] cm = mapToItem(map);
	initMapWholeItem();
	char[][] item=new char[12][12];
	int start = 0;int end = 0;
	while (end<cm[0].length-1){
//		Log.i("initMapATime");
		end+=12;
		if(end>=cm[0].length)break;
		for (int i = 0; i < 12; i++) {
			for (int j = start; j < end; j++) {
				item[i][j-start]=cm[i][j];
			}
		}
		whole.add(item.clone());
		item=new char[12][12];
		start+=12;
		
	}
//	for(int k=0;k<whole.size();k++){
//		char[][] a = whole.get(k);
//		for (int i = 0; i < a.length; i++) {
//			String aa="";
//			for (int j = 0; j < a[0].length; j++) {
//			aa+=" "+a[i][j];
//			}
//			Log.i("whole[][]"+aa);
//		}
//	}
}
	public void set3Item(Map map) {
		char[][] cm = mapToItem(map);
		
//		initMap333Item();
		char[][] item=new char[2][12];
		int start = 0;int end = 0;
		while (end<cm[0].length-1){
			end+=12;
			if(end>=cm[0].length)break;
//			Log.i("start+end"+start+" "+end);
			for (int i = 0; i < 2; i++) {
				for (int j = start; j < end; j++) {
					item[i][j-start]=cm[i][j];
				}
			}
			top.add(item.clone());
			start+=12;
			 item=new char[2][12];
		}
		
		 start = 0;end = 0;
		 item=new char[8][12];
		while (end<cm[0].length-1){
			end+=12;
			if(end>=cm[0].length)break;
//			Log.i("start+end"+start+" "+end);
			for (int i = 2; i < 10; i++) {
				for (int j = start; j < end; j++) {
					item[i-2][j-start]
							=cm[i][j];
				}
			}
			mid.add(item.clone());
			start+=12;
			 item=new char[8][12];//克隆也不可少 草
			 //二维数组克隆之後 里面的一位数组地址并没有变 所以添加克隆添加的也是同样的值
			 //操了
		}

		 start = 0;end = 0;
		 item=new char[2][12];
			while (end<cm[0].length-1){
				end+=12;
				if(end>=cm[0].length)break;
//				Log.i("start+end"+start+" "+end);
				for (int i = 10; i < 12; i++) {
					for (int j = start; j < end; j++) {
						item[i-10][j-start]=cm[i][j];
					}
				}
				bottom.add(item.clone());
				start+=12;
				 item=new char[2][12];
			}
		for(char[][]c:mid){
			String s="";
			for(char[] cc:c){
				for(char ccc:cc)
					s+=ccc;
			}
			Log.i(s);
		}
			
	}
	 char[][] mapToItem(Map map) {
//		Map map = new Map(itemString);
		GrassSet gs = new GrassSet(64, map.charData, world.lightningSet	,world);
		
		{
			byte[][] cba = gs.getMapArray();
			char[][]ca=new char[cba[0].length][cba.length];
			for(int i=0;i<cba[0].length;i++){
				for(int j=0;j<cba.length;j++){
					ca[12-1-i][j]=(char) cba[j][i];
				}
			}
//			if(true)
				return ca;
		}
		
	}

	ArrayList<char[][]> top = new ArrayList<char[][]>();
	ArrayList<char[][]> mid = new ArrayList<char[][]>();
	ArrayList<char[][]> bottom = new ArrayList<char[][]>();
	ArrayList<char[][]> whole = new ArrayList<char[][]>();
	void initMapWholeItem() {
		// TODO Auto-generated method stub
		whole.clear();
		mid.add(new char[][] {
				{ ' ', ' ', ' ', ' ', ' ', ' ', 't', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', 't', 't', 't', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', 'w', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', 'w', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', 't', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', 't', 't', 't', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', 'w', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', 'w', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', 'w', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', 'w', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', 'w', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', 'w', ' ', ' ', ' ', ' ', ' ' } });
	}
	void initMap333Item() {
		top.clear();
		mid.clear();
		bottom.clear();
		top.add(new char[][] {
				{ '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' },
				{ '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1' } });
		top.add(new char[][] {
				{ '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' },
				{ 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's', 's' } });
		top.add(new char[][] {
				{ '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' },
				{ 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r', 'r' } });
		mid.add(new char[][] {
				{ ' ', ' ', ' ', ' ', ' ', ' ', 't', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', 't', 't', 't', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', 'w', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', 'w', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', 'w', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', 'w', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' } });
		mid.add(new char[][] {
				{ ' ', ' ', ' ', ' ', ' ', ' ', 't', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', 't', 't', 't', ' ', ' ', ' ', ' ' },
				{ ' ', 't', ' ', ' ', ' ', ' ', 'w', ' ', ' ', ' ', ' ', ' ' },
				{ 't', 't', 't', ' ', ' ', ' ', 'w', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', 'w', ' ', ' ', ' ', ' ', 'w', ' ', ' ', ' ', 't', ' ' },
				{ ' ', 'w', ' ', ' ', ' ', ' ', 'w', ' ', ' ', 't', 't', 't' },
				{ ' ', 't', ' ', ' ', ' ', ' ', 't', ' ', ' ', ' ', 'w', ' ' },
				{ ' ', 't', ' ', ' ', ' ', ' ', 't', ' ', ' ', ' ', 'w', ' ' } });
		mid.add(new char[][] {
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', 'r', 't', 'e', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', 'r', 'w', 'e', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', 'r', 'w', 'e', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', 'r', 't', 'e', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', 'r', 't', 'e', ' ', ' ', ' ', ' ' },
				{ ' ', ' ', ' ', ' ', ' ', 'r', 'w', 'e', ' ', ' ', ' ', ' ' } });
		bottom.add(new char[][] {
				{ 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g', 'g' },
				{ '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' } });
		bottom.add(new char[][] {
				{ 'q', 'g', 'q', 'g', 'q', 'g', 'q', 'g', 'q', 'g', 'q', 'g' },
				{ '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' } });
		bottom.add(new char[][] {
				{ 'q', 'q', 'q', 'q', 'q', 'q', 'q', 'q', 'q', 'q', 'q', 'q' },
				{ '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' } });
	}

}
