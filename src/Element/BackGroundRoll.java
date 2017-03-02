package Element;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.BackGround;

public class BackGroundRoll extends BackGround{
	float x;
	float y;
	public BackGroundRoll(int mapIndex) {
		super(mapIndex);
	}
	void rollBackGround(){
		y+=1/720f;
		fbtex[0][0].clear();
		fbtex[0][0].put(new float[]{
				x,y+1,
				x+1,y+1,
				x+1,y,
				x,y,
				x,y+1,
		});
		fbtex[0][0].flip();
	}
	//	int backScr;
	public void drawElement(GL10 gl){
//		int a=(int) (Render.py+backScr++);
//		gl.glTranslatef(Render.px, a, 0);		
		rollBackGround();//滚动几乎不影响帧率
		super.drawElement(gl);//全屏显示才是影响帧率的关键因素
//		gl.glTranslatef(-Render.px, -a, 0);
	}
	public void setTexture(){
		bbSpi=ByteBuffer.allocateDirect(5*spiSize);//为储存顶点坐标开辟缓存
		bbSpi.order(ByteOrder.nativeOrder());
		fbSpi=bbSpi.asFloatBuffer();

		bbtex=new ByteBuffer[getxCount()][getyCount()];//*2
		fbtex=new FloatBuffer[getxCount()][getyCount()];

		float xLength=1f/getxCount();
		float yLength=1f/getyCount();
		for(int i=0;i<getxCount();i++){
			for(int j=0;j<getyCount();j++){//xy均等的人物图

				bbtex[i][j]=ByteBuffer.allocateDirect(texSize*5);// 正反*2 为储存顶点坐标开辟缓存
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
}
