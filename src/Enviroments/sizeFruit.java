package Enviroments;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import element2.TexId;

import Mankind.Player;

public class sizeFruit extends Fruit{
	public sizeFruit(){
		this(' ',0,0);
	}
	public sizeFruit(char bi,float x, float y) {
		super(bi,x,y);
		name="变形瓜";
		setGoodsCost(0, 5);
		loadTexture(TexId.H);
		maxScaleLengthX=1.5f*getW();
		setScaleZuni(0.5f);
	}
	
	public void drawElement(GL10 gl){
		randomWave(maxScaleLengthX);
		
		super.drawScale(gl);
		
	}
	public boolean loadAble(Player player){
		super.loadAble(player);
		return true;
	}
	protected void culYScaleRate() {
		setyScaleRate(getxScaleRate());
	}
	float growSpeed=1.2f;
	private float time=32/64f;
	 public void use( Player player,ArrayList<Fruit> pickedList){
		 setAnimationFinished(false);
		 if(player.growSpeed>=0){
//				player.noBlade();
				player.growSpeed=-growSpeed;
			}
			else if(player.growSpeed<0){
//				player.haveBlade();
				player.growSpeed=growSpeed;
			}
		 super.use(player, pickedList);
	}
	public void effectCheck(Player c,ArrayList<Fruit>pickedList){
		if(c.growSpeed>0&&c.getH()<c.getAgoMax()[1]||c.growSpeed<0&&c.getH()>c.getAgoMax()[1]*time)//变形
			c.changeSize(( c.getH()+c.growSpeed)  / c.getAgoMax()[1]);

		else {
			if(c.getH()>c.getAgoMax()[1]) {
				c.changeSize(1);
			}
			else if(c.getH()<c.getAgoMax()[1]*time) {
				c.changeSize(time);
			}
//			c.resume();
			pickedList.remove(this);
		}
	}
}

