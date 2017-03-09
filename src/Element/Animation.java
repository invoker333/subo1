package Element;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import element2.TexId;

public class Animation extends Draw implements Cloneable{
	public Animation(float x, float y) {
		// TODO Auto-generated constructor stub
setPosition(x, y);
		}
	public Animation() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public Object clone(){
		// TODO Auto-generated method stub
		Animation a=null;
		try {
			a=(Animation) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return a;
	}
	
	//	protected Context context;
	public float x,y;//初始坐标
	public float h=64;//物体大小
	public float w=64;
	public char mapSign;
	protected float wEdge=w;// 左边身体宽度
	protected float hEdge=h;//

	public float getwEdge() {
		return wEdge;
	}
	public void setwEdge(float w2) {
		this.wEdge = w2;
	}
	
	public float startX;
	public float startY;
	protected ByteBuffer bbSpi;
	protected ByteBuffer [][]bbtex;

	protected FloatBuffer fbSpi;

	protected FloatBuffer [][]fbtex;

	protected int spiSize=3*4;//申请书组缓冲区是格外注意大小
	protected int texSize=2*4;
	private int xCount=1;//
	private int yCount=1;

	public boolean visible=true;
	public String name="神秘物";
	public int cost=1;
	public int chancecost=1;
	public void setGoodsCost(int cost,int chancecost){
		this.cost=cost;
		this.chancecost=chancecost;
	}
	public int getIcon() {
		// TODO Auto-generated method stub
		return TexId.resIcon[getTextureId()];
	}
	
	
	private float xState;//记录动画状态 与图片上帧相对应
	private float yState;
	private float depth;
	private boolean animationFinished=true;
	protected int indexWave;
	protected double indexWaveRandom;
	public void randomWave(float maxScaleLengthX2){
		if(indexWave++>indexWaveRandom){
			indexWave=0;
			scaleTringer(maxScaleLengthX2);
			indexWaveRandom=240f*Math.random();
		}
	}
	public void setStartXY(float x,float y){
		startX=x;startY=y;
	}
	
	public void loadTexture(int textureId){
		setTextureId(textureId);
		setTexture();
	}
	public void loadTexture(int textureId,int x,int y){
		setTextureId(textureId);
		setTexture(x,y);
	}
	public  void scaleTringer(float scaleLength){
		scaleSpeed=(getW()-scaleLength)*tanxingxishu;
	}
	protected float maxScaleLengthX=0.8f*w;
	float scaleLength=w;//0 to 2*64  64 is centre
	float scaleSpeed;
	float scaleA;
	protected float tanxingxishu=1/5f;
	 private float scaleZuni=1f;//enegy loss 
	
	protected void scaleCheck() {
		// TODO Auto-generated method stub
		scaleA=(getW()-scaleLength)*tanxingxishu;
		
		scaleSpeed+=scaleA;
		
		if(scaleSpeed>scaleZuni)scaleSpeed-=scaleZuni;
		else if(scaleSpeed<-scaleZuni)scaleSpeed+=scaleZuni;
		//enegy loss
		
		scaleLength+=scaleSpeed;
		xScaleRate=scaleLength/getW();
		culYScaleRate();
	}
	protected void culYScaleRate() {
		yScaleRate = 2  - xScaleRate;
	}
	
	private float xScaleRate = 1f;
	private float yScaleRate = 1f;
	public float angle;
	protected float rotateSpeed;
//	int index;
	public void drawScale(GL10 gl) {
		angle += rotateSpeed;
		gl.glTranslatef(x, y, 0);

		scaleCheck();
		
		gl.glScalef(xScaleRate, yScaleRate, 0);
		gl.glRotatef(angle, 0, 0, 1);
		baseDrawElement(gl);
		gl.glRotatef(-angle, 0, 0, 1);
		gl.glScalef(1 / xScaleRate, 1 / yScaleRate, 0);
		gl.glTranslatef(-x, -y, 0);
		// gl.glLoadIdentity();
	}

	/*public void changeWidth(){
		xw=x+getW();_xw=x-getW();
	}
	public void changeHeight(){
		yh=y+getH();_yh=y-getH();
	}*/
	public void setTexture(int x,int y){
		bbSpi=ByteBuffer.allocateDirect(5*spiSize);//为储存顶点坐标开辟缓存
		bbSpi.order(ByteOrder.nativeOrder());
		fbSpi=bbSpi.asFloatBuffer();//顶点坐标


		bbtex=new ByteBuffer[getxCount()][getyCount()];//*2
		fbtex=new FloatBuffer[getxCount()][getyCount()];//纹理坐标

		float xCenter=1f/2/getxCount();
		float xLength=1f/getxCount();
		float yCenter=1f/2/getyCount();
		float yLength=1f/getyCount();

		bbtex[x][y]=ByteBuffer.allocateDirect(texSize*5);// 正反*2 为储存顶点坐标开辟缓存
		bbtex[x][y].order(ByteOrder.nativeOrder());
		fbtex[x][y]=bbtex[x][y].asFloatBuffer();
		fbtex[x][y].put(new float[]{
//				xCenter+x*xLength,yCenter+y*yLength,
				(x+1)*xLength,y*yLength,
				x*xLength,y*yLength,
				x*xLength,(y+1)*yLength,
				(x+1)*xLength,(y+1)*yLength,
				(x+1)*xLength,y*yLength,
		});
		fbtex[x][y].flip();
		syncTextureSize();
	}
	public void setTexture(){
		baseSetTexture();
	}

	protected void baseSetTexture() {
		bbSpi=ByteBuffer.allocateDirect(5*spiSize);//为储存顶点坐标开辟缓存
		bbSpi.order(ByteOrder.nativeOrder());
		fbSpi=bbSpi.asFloatBuffer();//顶点坐标


		bbtex=new ByteBuffer[getxCount()][getyCount()];//*2
		fbtex=new FloatBuffer[getxCount()][getyCount()];//纹理坐标

		float xCenter=1f/2/getxCount();
		float xLength=1f/getxCount();
		float yCenter=1f/2/getyCount();
		float yLength=1f/getyCount();
		for(int i=0;i<getxCount();i++){
			for(int j=0;j<getyCount();j++){//xy均等的人物图

				bbtex[i][j]=ByteBuffer.allocateDirect(texSize*5);// 正反*2 为储存顶点坐标开辟缓存
				bbtex[i][j].order(ByteOrder.nativeOrder());
				fbtex[i][j]=bbtex[i][j].asFloatBuffer();
				fbtex[i][j].put(new float[]{
//						xCenter+i*xLength,yCenter+j*yLength,
						(i+1)*xLength,j*yLength,
						i*xLength,j*yLength,
						i*xLength,(j+1)*yLength,
						(i+1)*xLength,(j+1)*yLength,
						(i+1)*xLength,j*yLength,
				});
				fbtex[i][j].flip();
			}
		}
/*		changeWidth();
		changeHeight();*/
		syncTextureSize();
	}
	public void drawElement(GL10 gl) {
		baseDrawElement(gl);
	}
	public void baseDrawElement(GL10 gl){
		fbSpi.position(0);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fbSpi);//顶点
		gl.glBindTexture(GL10.GL_TEXTURE_2D, getTextureId());//可以可以 一步将纹理设置到当前工作焦点
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, fbtex[(int) xState][(int) getyState()]);//纹理坐标映射
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 5);//绘制
	}

	public void syncTextureSize(){
		baseSynchronizedSize();
	}
	protected void baseSynchronizedSize() {
		fbSpi.clear();
		fbSpi.put(new float[]{//顶点xy数据
//				0,0,depth,
				w,h,depth,
				-w,h,depth,
				-w,-h,depth,
				w,-h,depth,
				w,h,depth,
		});
		fbSpi.flip();//将存入数据转换成写入状态
	}
//是否移动位置的顶点方法	
	public void setTexturePosition(){
		float w=this.w+x;
		float h=this.h+y;
		float _w=x-this.w;
		float _h=y-this.h;
		fbSpi.clear();
		fbSpi.put(new float[]{//顶点xy数据
//				x,y,depth,
				w,h,depth,
				_w,h,depth,
				_w,_h,depth,
				w,_h,depth,
				w,h,depth,
		});
		fbSpi.flip();//将存入数据转换成写入状态
	}

	public void changeState(float step){
		xState+=step;
		if(xState+step>=xCount){
			setxState(0);
			animationFinished=true;
		}
	}

	public void changeState(int  time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(xState++>=xCount-1)setxState(0);
	}

	public float getH() {
		return h;
	}
	public void setH(float h) {
		this.h = h;
	}
	public float getW() {
		return w;
	}
	public void setW(float w) {
		this.w = w;
	}
	public  void setPosition(float x,float y) {
		this.x=x;this.y=y;
	}
	public int getxCount() {
		return xCount;
	}
	public void setxCount(int xCount) {
		this.xCount = xCount;
	}
	public float getxState() {
		return xState;
	}
	public void setxState(float state) {
		this.xState = state;
	}
	public void setState(float x,float y){
		if(x<xCount&&y<yCount){
			xState = x;
			yState = y;
		}

	}
	public int getyCount() {
		return yCount;
	}
	public void setyCount(int yCount) {
		this.yCount = yCount;
	}
	private int frameCount;
	public int getFrameCount() {
		return frameCount;
	}
	public void setFrameCount(int xCount,int yCount) {
		setxCount(xCount);
		setyCount(yCount);
		frameCount = xCount*yCount;
	}
	public float getyState() {
		return yState;
	}
	public void setyState(float yState) {
		this.yState = yState;
	}

//	public float  {
//		return xet;
//	}
//
//	public void setXet(float xet) {
//		this.xet = xet;
//	}
//
//	public float getYet() {
//		return yet;
//	}
//
//	public void setYet(float yet) {
//		this.yet = yet;
//	}

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

	public boolean isAnimationFinished() {
		return animationFinished;
	}

	public void setAnimationFinished(boolean animationFinished) {
		this.animationFinished = animationFinished;
	}

	public float getxScaleRate() {
		return xScaleRate;
	}

	public void setxScaleRate(float xScaleRate) {
		this.xScaleRate = xScaleRate;
	}

	public float getyScaleRate() {
		return yScaleRate;
	}

	public void setyScaleRate(float yScaleRate) {
		this.yScaleRate = yScaleRate;
	}
	public float getScaleZuni() {
		return scaleZuni;
	}
	public void setScaleZuni(float scaleZuni) {
		this.scaleZuni = scaleZuni;
	}
	public float getAngle() {
		return angle;
	}
	public void setAngle(float angle) {
		this.angle = angle;
	}
	public void toStartPosition() {
		// TODO Auto-generated method stub
		x=startX;y=startY;
	}
	public float gethEdge() {
		return hEdge;
	}
	public void sethEdge(float hEdge) {
		this.hEdge = hEdge;
	}



}

