package Enviroments;

import javax.microedition.khronos.opengles.GL10;

import Element.AnimationGrass;

import element2.HikariSet;
import element2.LightningSet;


public class BigGrass extends Grass{
	
	public void drawElement(GL10 gl){
		if(!notBroken)return;
		super.drawScale(gl);
	}
	private float edge;
	public BigGrass(char bi,float[] data, int texId,boolean canBeBreak,float edge) {
		super(bi,data, texId,canBeBreak);
		this.edge = edge;
	}
	public BigGrass(char bi,float[] data, int texId,float edge) {
		super(bi,data, texId);
		this.edge = edge;
	}
	public void syncTextureSize(){
//		fbSpi.clear();
//		fbSpi.put(new float[]{
//				data[0]-edge,data[1]-edge,getDepth(),
//				data[2]+edge,data[1]-edge,getDepth(),
//				data[2]+edge,data[3]+edge,getDepth(),
//				data[0]-edge,data[3]+edge,getDepth(),
//				data[0]-edge,data[1]-edge,getDepth(),
//			}
//		);
		fbSpi.clear();
		fbSpi.put(new float[]{//顶点xy数据
				w+edge,h+edge,getDepth(),
				-w-edge,h+edge,getDepth(),
				-w-edge,-h-edge,getDepth(),
				w+edge,-h-edge,getDepth(),
				w+edge,h+edge,getDepth(),
		});
		fbSpi.flip();//将存入数据转换成写入状态
	}
	public boolean tooHigh(AnimationGrass animationGrass) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean tooLeft(AnimationGrass animationGrass) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean tooRight(AnimationGrass animationGrass) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean turnDown(AnimationGrass creature) {
		// TODO Auto-generated method stub
		return true;
	}
}
class Fog extends BigGrass{
	float dr,dg,db;
	private float time=100;
	int maxLightningTime=1*60;
	int lightningTime;
	private LightningSet lns;
	public Fog(char bi,float[] data, int texId, float edge,LightningSet lns) {
		super(bi,data, texId, edge);
		// TODO Auto-generated constructor stub
		this.lns = lns;
		canBeBreak=true;
	}
	public void setRgb(float red,float green,float blue){
		super.setRgb(red, green, blue);
		if(lightningTime<=1)lightningTime=maxLightningTime;
		dr=red/time;
		dg=green/time;
		db=blue/time;
	}
	static float max=1.5f;
	static float min=0.5f;
	void colorInc(){
		setRed(getRed() + dr);
		setGreen(getGreen() + dg);
		setBlue(getBlue() + db);
		if(getRed()>max||getRed()<min)dr=-dr;
		if(getGreen()>max||getGreen()<min)dg=-dr;
		if(getBlue()>max||getBlue()<min)db=-db;
	}
	public void drawElement(GL10 gl){
			if(!notBroken)return;
//		setRed(getRed() + getRed());
//		setGreen(getGreen() + getGreen());
//		setBlue(getBlue() + getBlue());
		if(lightningTime>1){
			colorInc();
			lightningTime--;
		}else if(lightningTime==1){
			lightning();lightningTime--;
		}else {
			if(getRed()<0.9f){
				setRed(getRed() + 0.1f);
			}else if(getGreen()>1.1f){
				setRed(getRed() - 0.1f);
			}else setRed(1);
			
			if(getGreen()<0.9f){
				setGreen(getGreen() + 0.1f);
			}else if(getGreen()>1.1f){
				setGreen(getGreen() - 0.1f);
			}else setGreen(1);
			
			if(getBlue()<0.9f){
				setBlue(getBlue() + 0.1f);
			}else if(getGreen()>1.1f){
				setBlue(getBlue() - 0.1f);
			}else setBlue(1);
				
		}
		gl.glColor4f(getRed(), getGreen(), getBlue(), 1);
		super.drawElement(gl);
		gl.glColor4f(1, 1, 1, 1);
	}
	private void lightning() {
		float length=data[2]-data[0];
		float height=data[3]-data[1];
		lns.tringer(data[0]+Math.random()*length, data[1]+Math.random()*height,1);
	}
	
}
class Burrow extends Grass{

	private float edge;
	
	public Burrow(char bi,float[] data, int texId,float edge,int xState,int yState) {
		super(bi,data, texId);
		// TODO Auto-generated constructor stub
		setxCount(2);setyCount(2);
		setxState(xState);
		setyState(yState);
		this.edge = edge;
		setIsburrow(true);
		
	}
//	public Burrow( float[] data, int texId,float edge,int xState) {
//		
//	}
	public void loadTexture(){
		setTexture((int)getxState(),(int)getyState());
	}
	public void syncTextureSize(){
		fbSpi.clear();
		fbSpi.put(new float[]{
				w,h+edge,getDepth(),
				-w,h+edge,getDepth(),
				-w,-h-edge,getDepth(),
				w,-h-edge,getDepth(),
				w,h+edge,getDepth(),
				
				
//				data[0],data[1]-edge,getDepth(),
//				data[2],data[1]-edge,getDepth(),
//				data[2],data[3]+edge,getDepth(),
//				data[0],data[3]+edge,getDepth(),
//				data[0],data[1]-edge,getDepth(),
			}
		);
		float w=getW()*(3f/5);
		if(getxState()==0){
			data[2]-=w;
		}
		else if(getxState()==1){
			data[0]+=w;
		}
	}	
		
			
}
