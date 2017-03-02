package Element;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;


public class BoomSet extends LightSpotSet{

	ArrayList<Boom> boomList;
//	private int count;

	public BoomSet(int count) {
		super(count);
	}
	public BoomSet(int count,int textureId) {
		super(count);
		for(Animation boom:boomList)
			boom.setTextureId(textureId);
	}
	public void resume(){		
	}
//	public void loadTexture() {
//		for(Animation boom:boomList)
//			boom.loadTexture(TexId.BOOM);
//	}

	 void init(int count) {
		 boomList = new ArrayList<Boom>(count);
		for (int i = 0; i < count; i++) {
			boomList.add(new Boom());
		}
	}

	public void tringer(float x, float y) {
		if(tringerIndex>=boomList.size())tringerIndex=0;
		LightSpot boom = boomList.get(tringerIndex++);
		boom.tringer(x, y);
	}

	public void drawElement(GL10 gl) {
		if (!boomList.isEmpty())
			for (Boom boom : boomList)
					boom.drawElement(gl);
	}
	public void tringer(double d, double e) {
		tringer((float) d, (float) e) ;
	}
}