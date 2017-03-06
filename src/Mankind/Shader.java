package Mankind;

import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import com.mingli.toms.Log;

import element2.Tail;
import Enviroments.GrassSet;

public class Shader extends JointCreature{

	private Creature master;
	private Tail tail;
	public Shader(GrassSet gra,float time,Creature master) {
		super(gra);
		this.master = master;
		// TODO Auto-generated constructor stub
		int frame=(int) (time*60f);
		position=new float [frame][2];
		for(int i=0;i<position.length;i++){
			position[i][0]=master.x;
			position[i][1]=master.y;
		}
		tail=new Tail(frame);
		tail.width=(int) (master.getW());
		tail.startTouch(master.x, master.y);
	}
	float [][]position;
	int id;

	public void attacked(int attack){
		
	}
	public void move(){
		tail.tringer(master.x, master.y);
		
		
		if(id>=position.length)id=0;
		setPosition(position[id][0],position[id][1]);
		position[id][0]=master.x;
		position[id][1]=master.y;
//		Log.i("Shader.x+y"+x+"  "+y);
		id++;
		
		direction=master.direction;
		
//		super.move();
	}
	public void drawElement(GL10 gl){
		gl.glColor4f(1, 1, 1, 0.5f);
		move();
		int id2=id;
		for(int i=0;i<position.length;i++){
			
			if(id2>=position.length)id2=0;
			setPosition(position[id2][0],position[id2][1]);
			id2++;
			
//			if(id2%2==0)continue;
			gl.glTranslatef(x, y, 0);
			baseDrawElement(gl);
			gl.glTranslatef(-x, -y, 0);
		}
//		tail.drawElement(gl);
		
		gl.glColor4f(1, 1, 1, 1f);
	}
}
