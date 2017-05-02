package Menu;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import Element.Animation;

import com.mingli.toms.Render;

public class Square extends Animation {
	public Square(){
//		setxCount(1);
		setDepth(-100);
	}
	public void loadTexture(int textureId,int x,int y){
		setTextureId(textureId);
		setTexture(x,y);
		setxState(x);setyState(y);
	}
	public void setTexture(int x,int y){
		bbSpi=ByteBuffer.allocateDirect(5*spiSize);//Ϊ���涥�����꿪�ٻ���
		bbSpi.order(ByteOrder.nativeOrder());
		fbSpi=bbSpi.asFloatBuffer();
		
		bbtex=new ByteBuffer[getxCount()][getyCount()];//*2
		fbtex=new FloatBuffer[getxCount()][getyCount()];
		
		float xLength=1f/getxCount();
		float yLength=1f/getyCount();
		bbtex[x][y]=ByteBuffer.allocateDirect(texSize*5);// ����*2 Ϊ���涥�����꿪�ٻ���
		bbtex[x][y].order(ByteOrder.nativeOrder());
		fbtex[x][y]=bbtex[x][y].asFloatBuffer();	
		fbtex[x][y].put(new float[]{
			x*xLength,(y+1)*yLength,	
			(x+1)*xLength,(y+1)*yLength,
			(x+1)*xLength,y*yLength,
			x*xLength,y*yLength,
			x*xLength,(y+1)*yLength,		
		});
		fbtex[x][y].flip();
		syncTextureSize();
	}
	public void setTexture(){
		bbSpi=ByteBuffer.allocateDirect(5*spiSize);//Ϊ���涥�����꿪�ٻ���
		bbSpi.order(ByteOrder.nativeOrder());
		fbSpi=bbSpi.asFloatBuffer();
		
		bbtex=new ByteBuffer[getxCount()][getyCount()];//*2
		fbtex=new FloatBuffer[getxCount()][getyCount()];
		
		float xLength=1f/getxCount();
		float yLength=1f/getyCount();
		for(int i=0;i<getxCount();i++){	
			for(int j=0;j<getyCount();j++){//xy���ȵ�����ͼ	
							
				bbtex[i][j]=ByteBuffer.allocateDirect(texSize*5);// ����*2 Ϊ���涥�����꿪�ٻ���
				bbtex[i][j].order(ByteOrder.nativeOrder());
				fbtex[i][j]=bbtex[i][j].asFloatBuffer();	
				fbtex[i][j].put(new float[]{
		
					i*xLength,(j+1)*yLength,	
					(i+1)*xLength,(j+1)*yLength,
					(i+1)*xLength,j*yLength,
					i*xLength,j*yLength,
					i*xLength,(j+1)*yLength,		
				});
			fbtex[i][j].flip();
			}
		}
		syncTextureSize();
}

	public void drawElement(GL10 gl){
		baseDrawElement(gl);
	}
	public void baseDrawElement(GL10 gl){
		fbSpi.position(0);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fbSpi);		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, getTextureId());//���Կ��� һ�����������õ���ǰ��������
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, fbtex[(int) getxState()][(int) getyState()]);//��������ӳ��
//		gl.glDrawArrays(gl.GL_LINE_LOOP, 0, 5);//����
//		gl.glDrawArrays(gl.GL_LINE_STRIP, 0, 5);//����
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, 5);//����
	}

	 public void syncTextureSize() {
		fbSpi.clear();
		fbSpi.put(new float[]{
				0,0,getDepth(),
				0+Render.width,0,getDepth(),
				0+Render.width,0+Render.height,getDepth(),
				0,0+Render.height,getDepth(),
				0,0,getDepth()
			}
		);
		fbSpi.flip();
	}

}
