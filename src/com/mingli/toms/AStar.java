package com.mingli.toms;


import java.util.ArrayList;
import java.util.List;


public class AStar {
	int map[][];
	List<Node> openList = new ArrayList<Node>(205);
	List<Node> closeList = new ArrayList<Node>(205);
	private int zero;// ling zhi
	public Node goal;
	int openCount;
	private Node curNode;
	private long endTime;
	private long startTime;

	public AStar(int map[][], int zero) {
		this.map = map;
		this.zero = zero;
	}

	public void search(Node start, Node target) {
		startTime = System.currentTimeMillis();//kaishihsijian
		
		curNode=start;
		goal=target;
		closeList.clear();
		openList.clear();
		
		openList.add(curNode);
		
		int findTime=0;
		openCount=0;
		while(openList.size()!=0){
			
			findMin();
			openList.remove(curNode);
			closeList.add(curNode); 
			allCheck();
			
			Log.i("findTime", ""+findTime);
			
			
			for (int i=0;i< openList.size();i++) {
				Node openNode=openList.get(i);
				if (openNode.x == goal.x && openNode.y == goal.y) {// kaiqiliebiaozhongcunzai
					goal=openNode;
				end();return;
				}
			}
			if(findTime++>200){
				end();return;
			}
		}end();
	}

	private void findMin() {
		curNode=openList.get(0);
		for(Node openNode:openList){
			if(openNode.f<curNode.f){
				curNode=openNode;
				Log.i("minx "+curNode.x,"miny "+curNode.y);
				break;
			}
		}
	}

	void allCheck() {
		int x = curNode.x;
		int y = curNode.y;
		nodeCheck(x - 1, y,  10);
		nodeCheck(x - 1, y - 1, 14);
		nodeCheck(x - 1, y + 1, 14);
		nodeCheck(x, y - 1, 10);
		nodeCheck(x, y + 1,  10);
		nodeCheck(x + 1, y,  10);
		nodeCheck(x + 1, y - 1,  14);
		nodeCheck(x + 1, y + 1,  14);
}

	private void end() {
//		goal = closeList.get(closeList.size()-1 );
		Log.i("openSize "+openList.size(), "closeSize "+closeList.size());
		 endTime = System.currentTimeMillis();
		 System.out.println("程序运行时间："+(endTime-startTime)+"ms");
	}

	void nodeCheck(int x, int y,int cost) {
		if (x < 0 || y < 0 || x >= map.length || y >= map[0].length){//zhangaiwu
			Log.i("edgeChecked"," ");
			return;// yuejie
		}
		if(map[x][y]!=zero){
			Log.i("zeroChecked"," "+map[x][y]);
			return;
		}
		Log.i("Normalpoint"," ");
		for (Node closeNode : closeList) {
			if (closeNode.x == x && closeNode.y == y) {
				Log.i("closeChecked"," ");
				return;// close
			}
		}
		Log.i("closeNoHas"," ");
		for (int i=0;i<openList.size();i++) {
			Node openNode = openList.get(i);
			if (openNode.x == x && openNode.y == y) {// kaiqiliebiaozhongcunzai
				int g = curNode.g + cost;
				if (g < openNode.g) {
					openNode.father = curNode;
					openNode.g = g;
					openNode.f = g + openNode.h;
				}
				Log.i("openExsit"," "+openCount);
				return;
			} 
		}
		Node newN;
		openList.add(newN=new Node(x, y, curNode));
		newN.caculate(goal, cost);
		openCount++;
		Log.i("openNoExsit"," "+openCount);
		
	}
}

class Node {
	public int x;
	public int y;
	public Node father;
	int f, g, h;

	public Node(int x, int y, Node father) {
		this.x = x;
		this.y = y;
		this.father = father;
	}
	void caculate(Node target, int cost) {
		h = Math.abs(target.y - y) + Math.abs(target.x - x);// yu ji hua fei
		g = father.g + cost;// jinguo de hua fei
		f = g + h;// zong hua fei
	}
}