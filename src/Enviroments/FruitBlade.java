package Enviroments;

import java.util.ArrayList;

import element2.TexId;
import Mankind.Player;

public class FruitBlade extends RotateFruit{

	public FruitBlade(char bi,float x, float y) {
		super(bi,x, y);
		name="桃木剑";
		cost=50;
		chancecost=50;
		// TODO Auto-generated constructor stub
		setTextureId(TexId.K);
	}
	public boolean loadAble(Player player){
		
		for(Fruit f:FruitSet.pickedList)
			if(f.getTextureId()==getTextureId())return false;// can't get more blade cause it is useless
		
		super.loadAble(player);
		return true;
	}
	public void use( Player player,ArrayList<Fruit> pickedList){
//		super.use(player, pickedList);
		player.changeBlade(getTextureId());
	}
	
	
	
	
	public void syncTextureSize(){
		syncTextureSize(getW(),getW()/2,getW()/3);
	}
	
	
	
	
}
