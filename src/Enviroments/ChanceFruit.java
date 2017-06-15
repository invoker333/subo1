package Enviroments;

import java.util.ArrayList;

import Mankind.BattleMan;

import com.mingli.toms.MenuActivity;
import com.mingli.toms.World;

import element2.TexId;

public class ChanceFruit extends ShakeFruit{

	private int time;

	public ChanceFruit(char bi,float x, float y,int time) {
		super(bi,x, y);
		this.time = time;
		name="复活蛋";
		setGoodsCost(0, 10);
		instruction="能够复活，增加时间，并且无敌一会儿";
		// TODO Auto-generated constructor stub
		loadTexture(TexId.EGG,0,0);
	}
	public void use(BattleMan player,ArrayList<Fruit> pickedList){
		final int max=80;
		doubleCost(max);
		if(player.isDead)player.reLife(time);
		else player.increaseChanceBy(1);
		super.use(player, pickedList);
	}

	public boolean loadAble(BattleMan player){
		if(World.rpgMode)
		for(Fruit f:FruitSet.pickedList)
			if(f.getTextureId()==getTextureId()){
				MenuActivity.showDialog("提示：", "限购一个", getIcon());
				return false;// can't get more blade cause it is useless
			}
		
		
	
		 super.loadAble(player);
		 return true;
	}
}
