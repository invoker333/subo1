package Enviroments;

import java.util.ArrayList;

import com.mingli.toms.MenuActivity;
import com.mingli.toms.R;
import com.mingli.toms.World;

import Mankind.Player;
import Weapon.Gun;
import Weapon.HookGun;
import Weapon.BoomGun;
import Weapon.ShotGun;
import Weapon.TailGun;
import element2.TexId;

public class FruitGun extends RotateFruit{

	public FruitGun(char bi,float x, float y,int textureId) {
		super(bi,x, y);
		mapSign=bi;
		// TODO Auto-generated constructor stub
		setTextureId(textureId);
		name="泡泡枪";
		nameCheck(getIcon());
		kind="fruitgun";
		
	}
	private void nameCheck(int textureId) {
		// TODO Auto-generated method stub
		switch(textureId){
		case R.drawable.s:
			name="霰弹枪";
			setGoodsCost(20,0);
			break;
		case R.drawable.putongdan:
			name="光弹枪";
			setGoodsCost(10,0);
			break;
		case R.drawable.jujidan:
			name="狙击枪";
			setGoodsCost(30,0);
			break;
		case R.drawable.huck:
			setGoodsCost(15,0);
			name="钩爪";
			break;
		case R.drawable.daodan:
			setGoodsCost(40,0);
			name="火箭筒";
			break;
		}
	}
	public boolean loadAble(Player player){
		for(Fruit f:FruitSet.pickedList)
			if(f.getTextureId()==getTextureId()){
				MenuActivity.showDialog("", "限购一个", getIcon());
				return false;// can't get more blade cause it is useless
			}
		
		super.loadAble(player);
		return true;
	}
	public void use( Player player,ArrayList<Fruit> pickedList){
		player.changeGun(getTextureId());
	}
}
