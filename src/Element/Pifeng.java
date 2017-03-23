package Element;

import javax.microedition.khronos.opengles.GL10;

import Mankind.Creature;
import element2.Tail;
import element2.TexId;

public class Pifeng extends Draw{
	Creature master;
	AnimationMove[]nodes;
	public Tail clothTail;
	private float dsmax;
	public Pifeng(Creature master,int count){
		this.master=master;
		nodes=new AnimationGravity[count];
		
		AnimationMove temp;
		for(int i=0;i<nodes.length;i++){
			temp=nodes[i]=new AnimationGravity();
			temp.setPosition(master.x, master.y);
		}
        clothTail=new Tail(count,TexId.PIFENG);
        clothTail.width=42;
        dsmax = master.h*1.2f/nodes.length;
	}
	public void timerTask(int angle,float masterx,float mastery){
		timerTask(masterx,mastery);
	}
	public void timerTask(){
		
		 float masterx = master.getDirection()==1?master.x-master.w:master.x+master.w;
		float mastery = master.y+master.h/2;
		timerTask(masterx,mastery);
	}
	public void timerTask(float masterx,float mastery){
		AnimationMove head =nodes[0];
//		if(head==null)return;
		AnimationMove tail;
		
		
		final float tanxingxishu=0.3f;
		final float zuni=0.9f;
		 head.setPosition(masterx,mastery);
		
		clothTail.startTouch(head.x,head.y);
		
		for(int i=1;i<nodes.length;i++){
			tail=nodes[i];
			head.stringCheck2(tail, dsmax, tanxingxishu, zuni);
			head.move();
			head.gravity();
			
			clothTail.tringer(tail.x, tail.y);
			
			head=tail;
		}
		head.move();
		head.gravity();
	}
	public void drawElement(GL10 gl){
		clothTail.drawElement(gl);
	}
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub
		for(int i=0;i<nodes.length;i++){
			nodes[i].setPosition(x,y);
		}
	}
}
