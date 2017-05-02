package Enviroments;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import Mankind.Player;

public class SixFruit extends Fruit{
	public SixFruit(char bi,float x, float y) {
		super(bi,x,y);
		
		// TODO Auto-generated constructor stub
	}
	protected int angleSpeed=3;
	private int angle;
	public void drawElement(GL10 gl){
		gl.glEnable(GL10.GL_CULL_FACE);//背面裁剪
		angle+=angleSpeed;
		gl.glTranslatef(x, y, 0);
		gl.glRotatef(angle, 0, 1, 0);
//		gl.glRotatef(angle, 1, 0, 0);
		baseDrawElement(gl);
//		gl.glRotatef(-angle, 1, 0, 0);
		gl.glRotatef(-angle, 0, 1, 0);
		gl.glTranslatef(-x, -y, 0);
		gl.glDisable(GL10.GL_CULL_FACE);//背面裁剪
	}
	public boolean loadAble(Player player){
		super.loadAble(player);
		return false;
	}
	
	 public void setTexture(){

		bbtex=new ByteBuffer[getxCount()][getyCount()];//*2
		fbtex=new FloatBuffer[getxCount()][getyCount()];//纹理坐标


				bbtex[0][0]=ByteBuffer.allocateDirect(texSize*12);// 正反*2 为储存顶点坐标开辟缓存
				bbtex[0][0].order(ByteOrder.nativeOrder());
				fbtex[0][0]=bbtex[0][0].asFloatBuffer();
				float i1=0.289f;
				fbtex[0][0].put(new float[]{
						0,i1,
						0,1f-i1,
						1/2f,0,
						1/2f,1,
						1,i1,
						1,1f-i1,
						
						1,i1,
						1,1-i1,
						
						1+1/2f,0,
						1+1/2f,1,
						2,i1,
						2,1f-i1,
						
						
				});
				fbtex[0][0].flip();
		syncTextureSize();
		
	}
	public void syncTextureSize(){
		bbSpi=ByteBuffer.allocateDirect(24*spiSize);//为储存顶点坐标开辟缓存
		bbSpi.order(ByteOrder.nativeOrder());
		fbSpi=bbSpi.asFloatBuffer();//顶点坐标
		float w=getW();//�������쳤��
		 float w2=w/1.73f;
		 float h=w/3f;//houdu
		fbSpi.put(new float[]{//����xy���
				-w,w2,h,
				-w,-w2,h,
				0,w,h,
				0,-w,h,
				w,w2,h,
				w,-w2,h,
				
				w,w2,-h,
				w,-w2,-h,
				0,w,-h,
				0,-w,-h,
				-w,w2,-h,
				-w,-w2,-h,
				
				w,w2,h,
				w,w2,-h,
				0,w,h,
				0,w,-h,
				-w,w2,h,
				-w,w2,-h,
				
				-w,-w2,h,
				-w,-w2,-h,
				0,-w,h,
				0,-w,-h,
				w,-w2,h,
				w,-w2,-h,
				
			});
		fbSpi.flip();//���������ת����д��״̬
	}
	public void baseDrawElement(GL10 gl){
//		Log.i("rotax"+x,"rotay"+y);
		fbSpi.position(0);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fbSpi);//顶点
		gl.glBindTexture(GL10.GL_TEXTURE_2D, getTextureId());//可以可以 一步将纹理设置到当前工作焦点
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, fbtex[(int) getxState()][(int) getyState()]);//纹理坐标映射
//		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 16);//绘制
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 12);//����
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 12, 12);//����
	}
}
