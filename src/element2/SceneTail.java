package element2;


import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import Menu.Square;

public class SceneTail extends Tail {


	public SceneTail(int count) {
		super(count);
		tail = new float[count][6];// 他妈这个2还不能去
		// TODO Auto-generated constructor stub
	}

	public SceneTail(int count,int texId) {
		this( count);
		// TODO Auto-generated constructor stub
	      setTextureId(texId);
	}
	public void startTouch(float x, float y){
        pointCount =1;
        startIndex=tringerIndex+1;//从下一点开始
         if(startIndex>=tail.length)startIndex=0;
        drawStartIndex=startIndex;
        tringerIndex=startIndex;//全盘重启

        tail[tringerIndex][2]= (float) (x);
        tail[tringerIndex][3]= (float) (y);
        tail[tringerIndex][4]= (float) (x);
        tail[tringerIndex][5]= (float) (0);

    }
	 public void tringer(float x, float y){
	        if(++tringerIndex>=tail.length)tringerIndex=0;//新开一点
	        tail[tringerIndex][0]=x;
	        tail[tringerIndex][1]=y;
	        	
	        
	        tail[tringerIndex][2]= (float) (x);
	        tail[tringerIndex][3]= (float) (y);
	        tail[tringerIndex][4]= (float) (x);
	        tail[tringerIndex][5]= (float) (y-w);
	        if(++pointCount>tail.length){
	            pointCount=tail.length;//chu点增加
	            if(++drawStartIndex>=tail.length)drawStartIndex=0;
	        }
	        startIndex=tringerIndex;//重标头点
	        agoX=x;agoY=y;
	    }
	  public void setTexture(){
	        bbtex=new ByteBuffer[getxCount()][getyCount()];//*2
	        fbtex=new FloatBuffer[getxCount()][getyCount()];
	        int drawXYCount=2*count;//xy的对儿的数目
	        int drawRawCount=2*drawXYCount;//单个数据的数目
	        float xLength=1f/(drawRawCount-1);//
	        float yLength=1f/getyCount();
	        float[] buffer=new float[drawRawCount];//每个店x，y两个数据
	        for(int i=0;i<drawRawCount;i+=4){
	            buffer[i]=i*xLength;
	            buffer[i+1]= 0*yLength;////////////////////////////////////////////////////////////////////////
	            buffer[i+2]= i*xLength;
	            buffer[i+3]= 1*yLength;/////////////////////////////////////////////////////////////////
	        }

	        bbtex[0][0]=ByteBuffer.allocateDirect(texSize*drawXYCount);// x,y
	        bbtex[0][0].order(ByteOrder.nativeOrder());
	        fbtex[0][0]=bbtex[0][0].asFloatBuffer();
	        fbtex[0][0].put(buffer);
	        fbtex[0][0].flip();
	        syncTextureSize();
	    }
}
