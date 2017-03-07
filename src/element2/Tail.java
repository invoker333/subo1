package element2;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import Menu.Square;

/**
 * Created by Administrator on 2016/7/8.
 */
public class Tail extends Square {
	public int  count;
    float tail[][];
    protected int tringerIndex;
    protected int startIndex;
    public int width=50;
    int pointCount=1;//一点生两点
    protected int drawStartIndex;
    float agoX,agoY;
    public Tail(int count,float x,float y){
        this(count);
        startTouch(x,y);
    }
    public Tail(int count,int texId){
        this(count);
        setTextureId(texId);
    }
    public Tail(int count){
        tail=new float[count][6];//他妈这个6还不能去
        // x,y, x1,y1,x2,y2
        this.count=count;
        loadTexture();
    }

    public Tail(int i, float x, float y, int i1) {
        this(i,x,y);
        width=i1;
    }

    public void startTouch(float x, float y){
        pointCount =1;
        startIndex=tringerIndex+1;//从下一点开始
         if(startIndex>=tail.length)startIndex=0;
        drawStartIndex=startIndex;
        tringerIndex=startIndex;//全盘重启

        tail[startIndex][0]=x;
        tail[startIndex][1]=y;
        agoX=x;agoY=y;

    }
    public void tringer(float x, float y,float angle){
//    	tringer(x,y,Math.cos/./..)
    }
    public void tringer(float x, float y,float cos,float sin){
        if(++tringerIndex>=tail.length)tringerIndex=0;//新开一点
        tail[tringerIndex][0]=x;
        tail[tringerIndex][1]=y;
//        float dx=x-agoX;
//        float dy=y-agoY;
//        float s= (float) Math.sqrt(dx*dx+dy*dy);// s 为零 比较尴尬 除数为零
//        	
        float xWidth,yWidth;
//        if(s<1){// 让步s<0 的bug
//        	 xWidth=1;
//             yWidth=1;
//        }else{
        	xWidth=(float) (width*cos);
        	yWidth=(float) (width*sin);
//        }
        
        
        
//        if(dx<0){//sin<0
//            dx=-dx;dy=-dy;//为了成一个圈
//        }
        if(pointCount ==1){        //开头点
            tail[startIndex][2]= tail[startIndex][0]-xWidth;
            tail[startIndex][3]= (float) ( tail[startIndex][1]+yWidth);
            tail[startIndex][4]= (float) ( tail[startIndex][0]+xWidth);
            tail[startIndex][5]= (float) ( tail[startIndex][1]-yWidth);
        }
        tail[tringerIndex][2]= (float) (x-xWidth);
        tail[tringerIndex][3]= (float) (y+yWidth);
        tail[tringerIndex][4]= (float) (x+xWidth);
        tail[tringerIndex][5]= (float) (y-yWidth);
        if(++pointCount>tail.length){
            pointCount=tail.length;//chu点增加
            if(++drawStartIndex>=tail.length)drawStartIndex=0;
        }
        startIndex=tringerIndex;//重标头点
        agoX=x;agoY=y;
    }
    public void tringer(float x, float y){
        if(++tringerIndex>=tail.length)tringerIndex=0;//新开一点
        tail[tringerIndex][0]=x;
        tail[tringerIndex][1]=y;
        float dx=x-agoX;
        float dy=y-agoY;
        float s= (float) Math.sqrt(dx*dx+dy*dy);// s 为零 比较尴尬 除数为零
        	
        float xWidth,yWidth;
        if(s<1){// 让步s<0 的bug
        	 xWidth=1;
             yWidth=1;
        }else{
        	xWidth=width*dy/s;
        	yWidth=width*dx/s;
        }
        
        
        
//        if(dx<0){//sin<0
//            dx=-dx;dy=-dy;//为了成一个圈
//        }
        if(pointCount ==1){        //开头点
            tail[startIndex][2]= tail[startIndex][0]-xWidth;
            tail[startIndex][3]= (float) ( tail[startIndex][1]+yWidth);
            tail[startIndex][4]= (float) ( tail[startIndex][0]+xWidth);
            tail[startIndex][5]= (float) ( tail[startIndex][1]-yWidth);
        }
        tail[tringerIndex][2]= (float) (x-xWidth);
        tail[tringerIndex][3]= (float) (y+yWidth);
        tail[tringerIndex][4]= (float) (x+xWidth);
        tail[tringerIndex][5]= (float) (y-yWidth);
        if(++pointCount>tail.length){
            pointCount=tail.length;//chu点增加
            if(++drawStartIndex>=tail.length)drawStartIndex=0;
        }
        startIndex=tringerIndex;//重标头点
        agoX=x;agoY=y;
    }
//    boolean isNegative(double a){
//        if(a>0)return true;
//        else return false;
//    }
    public void syncTextureSize(){
        int dataCount= pointCount *4;
        bbSpi= ByteBuffer.allocateDirect(pointCount *2 * spiSize);//Ϊ���涥�����꿪�ٻ���
        bbSpi.order(ByteOrder.nativeOrder());
        fbSpi=bbSpi.asFloatBuffer();
        float[] buffer=new float[dataCount];
        int loadIndex=drawStartIndex;
        for(int i=0;i<dataCount;i+=4){
            buffer[i]=tail[loadIndex][2];
            buffer[i+1]=tail[loadIndex][3];
            buffer[i+2]=tail[loadIndex][4];
            buffer[i+3]=tail[loadIndex][5];
            if(++loadIndex>=tail.length)loadIndex=0;
        }
        fbSpi.put(buffer);
        fbSpi.flip();
    }
    public void drawScale(GL10 gl){
        if(drawStartIndex!=tringerIndex)
            syncTextureSize();
        super.drawScale(gl);
    }
    public void drawElement(GL10 gl){
        if(drawStartIndex!=tringerIndex)
            syncTextureSize();
        
        baseDrawElement(gl);
    }
    public void baseDrawElement(GL10 gl){
    	   fbSpi.position(0);
           gl.glVertexPointer(2, GL10.GL_FLOAT, 0, fbSpi);
           gl.glBindTexture(GL10.GL_TEXTURE_2D, getTextureId());//���Կ��� һ�����������õ���ǰ��������
           gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, fbtex[(int) getxState()][(int) getyState()]);//��������ӳ��
           gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 2*pointCount);//画的是三角形 每次两个三角形 成2
//           gl.glDrawArrays(gl.GL_LINE_STRIP, 0, 2*pointCount);//����
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
            buffer[i+1]= (0+1)*yLength;
            buffer[i+2]= i*xLength;
            buffer[i+3]= 0*yLength;
        }

        bbtex[0][0]=ByteBuffer.allocateDirect(texSize*drawXYCount);// x,y
        bbtex[0][0].order(ByteOrder.nativeOrder());
        fbtex[0][0]=bbtex[0][0].asFloatBuffer();
        fbtex[0][0].put(buffer);
        fbtex[0][0].flip();
        syncTextureSize();
    }
}
