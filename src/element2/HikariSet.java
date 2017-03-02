package element2;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Element.AnimationMove;
import Mankind.Player;

import com.mingli.toms.Render;

public class HikariSet extends Set{
	ArrayList<AnimationMove> hikariList = new ArrayList<AnimationMove>(10);
	private  boolean isCastle;

	public HikariSet(int count,boolean isCastle) {
		this.isCastle = isCastle;
		init(count);
		loadTexture();
	}
	public void resume(){
	}
	public void loadTexture() {
		for(AnimationMove hikari:hikariList){
			hikari.w=16;
			hikari.h=16;
			hikari.loadTexture(TexId.HIKARI);
		}
	}

	private void init(int count) {
		for (int i = 0; i < count; i++)
			hikariList.add(new AnimationMove());
	}

	int tringerIndex;
	public void tringer(float x, float y, float speedMin, float speedMax, int count) {
		float dSpeed=speedMax-speedMin;
		for (int i = 0; i < count; i++) {
			if(tringerIndex>=hikariList.size())tringerIndex=0;
			AnimationMove hikari = hikariList.get(tringerIndex++);
			hikari.x = x;
			hikari.y = y;
			hikari.setxSpeed((float) ((2*Math.random()-1)*dSpeed));
			hikari.setySpeed((float) ((2*Math.random()-1)*dSpeed));
		}
	}

	public void drawElement(GL10 gl) {
			for (AnimationMove hikari : hikariList){
				if (hikari.x > Player.gx1 && hikari.x < Player.gx2 && hikari.y > Player.gy1
    					&& hikari.y < Player.gy2) {
            		
//             		if(boom.alpha>-1)
            		hikari.drawElement(gl);
            	}
            	
            	else{
            		
            		float x,y;
            		if(isCastle){
            			 x=(float) (Render.px+Math.random()*Render.width);
                    	 y=Math.random()>.5?Player.gy1:Player.gy2;
            		}else {
            			 x=Math.random()>.5?Player.gx1:Player.gx2;
                    	 y=(float) (Render.py+Math.random()*Render.height);
            		}
            		
					tringer(x, y,5, 10, 1);
            	}
			}
				
	}
	public void tringer(double d, double e, double random, double random2,
						int count) {
		tringer((float)d,(float)(e),(float)random,(float)random2,count);
	}
}

//class Hikari extends Animation {

//	public void changePosition() {
//		float xw = x + getW();
//		float _xw = x - getW();
//		float yh = y + getW();
//		float _yh = y - getW();
//		fbSpi.clear();
//		fbSpi.put(new float[] {// 顶点xy数据
//		x, y, getDepth(),
//		xw, y, getDepth(),
//		x, yh, getDepth(),
//		_xw, y, getDepth(),
//		x, _yh,getDepth(),
//		xw, y, getDepth(), });
//		fbSpi.flip();// 将存入数据转换成写入状态
//	}
//}
//class Hikari implements SoundDraw {
//	protected ByteBuffer bbSpi;
//	protected FloatBuffer fbSpi;
//	protected int spiSize = (3+4) * 4;// 申请书组缓冲区是格外注意大小
//	private float depth;
//	float y;
//	float x;
//	private int w=32;
//	private int h=32;
//	float xSpeed, ySpeed;
//
//	public void setTexture() {
//		bbSpi = ByteBuffer.allocateDirect(6 * spiSize);// 为储存顶点坐标开辟缓存
//		bbSpi.order(ByteOrder.nativeOrder());
//		fbSpi = bbSpi.asFloatBuffer();
//		changePosition();
//	}
//
//	public void changePosition() {
//		float xw = x + getW();
//		float _xw = x - getW();
//		float yh = y + getW();
//		float _yh = y - getW();
//		fbSpi.clear();
//		fbSpi.put(new float[] {// 顶点xy数据
//		x, y, depth, 1,1,1,1,
//		xw, y, depth,1,1,1,0,
//		x, yh, depth,1,1,1,0,
//		_xw, y, depth,1,1,1,0,
//		x, _yh,depth, 1,1,1,0,
//		xw, y, depth,1,1,1,0, });
//		fbSpi.flip();// 将存入数据转换成写入状态
//	}
//
//	@Override
//	public void loadSound() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void playSound() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void playSound(int soundId) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void drawElement(GL10 gl) {
//		x += xSpeed;
//		y += ySpeed;
//		gl.glTranslatef(x, y, 0);
//		fbSpi.position(0);
//		gl.glVertexPointer(3, GL10.GL_FLOAT, spiSize, fbSpi);
//		fbSpi.position(3);// 定义的数组一定要使用 要不然会黑屏
//		gl.glColorPointer(4, GL10.GL_FLOAT, spiSize, fbSpi);
//		gl.glDrawArrays(gl.GL_TRIANGLE_FAN, 0, 6);
//		gl.glTranslatef(-x, -y, 0);
//	}
//
//	public int getW() {
//		return w;
//	}
//
//	public void setW(int w) {
//		this.w = w;
//	}
//
//	public int getH() {
//		return h;
//	}
//
//	public void setH(int h) {
//		this.h = h;
//	}
//}
