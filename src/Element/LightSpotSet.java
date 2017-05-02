package Element;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Mankind.Player;

import com.mingli.toms.Render;

import element2.Set;

/**
 * Created by Administrator on 2016/7/7.
 */
public class LightSpotSet extends Set{
    ArrayList<LightSpot> boomList;
//	private boolean isCastle;
//	private int count;

    public LightSpotSet(int count) {
//		this.count = count;
        init(count);
        loadTexture();
    }
    public LightSpotSet(int count,int textureId) {
    	this(count);
    	setTextureId(textureId);
    }
	public void resume(){
    }
//    public void loadTexture() {
//        for(Animation boom:boomList)
//            boom.loadTexture(TexId.LIGHTSPOT);
//    }

    void init(int count) {
        boomList = new ArrayList<LightSpot>(count);
        LightSpot l;
        for (int i = 0; i < count; i++) {
			boomList.add(l=new LightSpot());
            l.tringer((float)(Render.px+Math.random()*Render.width), (float) (Render.py+Math.random()*Render.height));
        }
    }

    int tringerIndex;

    public void tringerInColor(float x, float y) {
        if(tringerIndex>=boomList.size())tringerIndex=0;
        LightSpot boom = boomList.get(tringerIndex++);
        boom.tringerInColor(x, y);
    }
    public void tringer(float x, float y) {
        if(tringerIndex>=boomList.size())tringerIndex=0;
        LightSpot boom = boomList.get(tringerIndex++);
        boom.tringer(x, y);
    }

    public void drawElement(GL10 gl) {
//        if (!boomList.isEmpty())
            for (LightSpot boom : boomList){
            	if (boom.x > Player.gx1 && boom.x < Player.gx2 && boom.y > Player.gy1
    					&& boom.y < Player.gy2) {
            		
//             		if(boom.alpha>-1)
            		boom.drawElement(gl);
            	}
            	
            	else{
            		
            		float x,y;
            		double bool = Math.random()-0.5;
            		if(bool>0){
            			 x=(float) (Render.px+Math.random()*Render.width);
                    	 y=Math.random()>.5?Player.gy1:Player.gy2;
            		}else {
            			 x=Math.random()>.5?Player.gx1:Player.gx2;
                    	 y=(float) (Render.py+Math.random()*Render.height);
            		}
            		
					boom.tringerInColor(x, y);
            	}
            }
    }
    public void tringer(double d, double e) {
        tringer((float)d,  (float)e) ;
    }
}

