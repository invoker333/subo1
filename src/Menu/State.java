package Menu;


public class State	extends Square {
	protected float x1;
	protected float y1;
	protected float x2;
	protected float y2;
	
	public State(){
		
	}
	public State(float x1,float y1,float x2,float y2){
		setPosition(x1, y1, x2, y2);
	}
	public void  setPosition(float x1,float y1,float x2,float y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public void syncTextureSize(){//ȫ������
		fbSpi.clear();
		fbSpi.put(new float[]{
				x1,y1,getDepth(),
				x2,y1,getDepth(),
				x2,y2,getDepth(),
				x1,y2,getDepth(),
				x1,y1,getDepth()	
			}
		);
		fbSpi.flip();
	}
	
	public void setFrameCount(int i, int j) {
		setxCount(i);
		setyCount(j);
	}
	public void animation(float dr, float dg, float db) {
		// TODO Auto-generated method stub
		
	}
	public float getY2() {
		return y2;
	}
	public void setY2(float y2) {
		this.y2 = y2;
	}
	public float getY1() {
		return y1;
	}
	public void setY1(float y1) {
		this.y1 = y1;
	}
	public float getX2() {
		return x2;
	}
	public void setX2(float x2) {
		this.x2 = x2;
	}
	public float getX1() {
		return x1;
	}
	public void setX1(float x1) {
		this.x1 = x1;
	}
	
}
