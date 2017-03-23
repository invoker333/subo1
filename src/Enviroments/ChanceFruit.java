package Enviroments;

import java.util.ArrayList;

import aid.Log;

import com.mingli.toms.MenuActivity;
import com.mingli.toms.World;

import element2.TexId;
import Mankind.Player;

public class ChanceFruit extends ShakeFruit{

	public ChanceFruit(char bi,float x, float y) {
		super(bi,x, y);
		name="复活蛋";
		setGoodsCost(0, 10);
		instruction="能够复活，增加时间，并且无敌一会儿";
		// TODO Auto-generated constructor stub
		loadTexture(TexId.EGG,0,0);
	}
	public void use(Player player,ArrayList<Fruit> pickedList){
		final int max=80;
		if(cost<max&&chancecost<max){
			cost+=cost;
			chancecost+=chancecost;
		}
		super.use(player, pickedList);
	}
	public boolean loadAble(Player player){
		if(World.rpgMode)
		for(Fruit f:FruitSet.pickedList)
			if(f.getTextureId()==getTextureId()){
				MenuActivity.showDialog("提示：", "限购一个", getIcon());
				return false;// can't get more blade cause it is useless
			}
		
		
	
		 super.loadAble(player);
		 return true;
	}
	public void effectCheck(Player p,ArrayList<Fruit>pickedList) {
		p.increaseChanceBy(1);
		p.reLife();
		
		pickedList.remove(this);
		super.effectCheck(p, pickedList);
	}
}
