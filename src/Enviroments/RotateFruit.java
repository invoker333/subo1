package Enviroments;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import aid.Log;
import element2.TexId;
import Mankind.Player;

public class RotateFruit extends Fruit{
	float angleSpeed=1;
	protected float angle;//起始位置
	
	public RotateFruit(char bi,float x, float y){
		super(bi,x, y);
	}
	
	public void drawElement(GL10 gl){
		gl.glEnable(GL10.GL_CULL_FACE);//背面裁剪
		angle+=angleSpeed;
		gl.glTranslatef(x, y, 0);
		gl.glRotatef(angle, 1, 1, 1);
		baseDrawElement(gl);
		gl.glRotatef(-angle, 1, 1, 1);
		gl.glTranslatef(-x, -y, 0);
		gl.glDisable(GL10.GL_CULL_FACE);//背面裁剪
	}
	
	 public void setTexture(){

		bbtex=new ByteBuffer[getxCount()][getyCount()];//*2
		fbtex=new FloatBuffer[getxCount()][getyCount()];//纹理坐标


				bbtex[0][0]=ByteBuffer.allocateDirect(texSize*18);// 正反*2 为储存顶点坐标开辟缓存
				bbtex[0][0].order(ByteOrder.nativeOrder());
				fbtex[0][0]=bbtex[0][0].asFloatBuffer();
				float w=1;
				fbtex[0][0].put(new float[]{
						0,0,
						0,1,
						1,0,
						1,1,
						
//						1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
						
						2,0,
						2,1,
						
						3,0,
						3,1,
						
						4,0,
						4,1,
						
						0,0,
						0,1,
						1,0,
						1,1,
						
						0,0,
						0,1,
						1,0,
						1,1,
				});
				fbtex[0][0].flip();
		syncTextureSize();
		
	}
	public void syncTextureSize(){
		syncTextureSize(getW(),getW(),getW());
	}
	public void syncTextureSize(float w,float h,float t){
		bbSpi=ByteBuffer.allocateDirect(18*spiSize);//为储存顶点坐标开辟缓存
		bbSpi.order(ByteOrder.nativeOrder());
		fbSpi=bbSpi.asFloatBuffer();//顶点坐标
		fbSpi.put(new float[]{//����xy���
				-w,h,t,
				-w,-h,t,
				w,h,t,
				w,-h,t,
				w,h,-t,
				w,-h,-t,
				-w,h,-t,
				-w,-h,-t,
				-w,h,t,
				-w,-h,t,
				
				
				-w,h,t,
				w,h,t,
				-w,h,-t,
				w,h,-t,
				
				w,-h,t,
				-w,-h,t,
				w,-h,-t,
				-w,-h,-t,
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
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 10);//����
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 10, 4);//����
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 14, 4);//����
	}
}
