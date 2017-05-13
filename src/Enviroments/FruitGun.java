package Enviroments;

import java.util.ArrayList;

import Mankind.BattleMan;

import com.mingli.toms.MenuActivity;
import com.mingli.toms.R;

public class FruitGun extends RotateFruit{

	public FruitGun(char bi,float x, float y,int textureId) {
		super(bi,x, y);
		mapSign=bi;
		// TODO Auto-generated constructor stub
		setTextureId(textureId);
		name="泡泡枪";
		kind="fruitgun";
		instruction="用来攻击敌人的武器";
		nameCheck(getIcon());
		
	}
	private void nameCheck(int textureId) {
		// TODO Auto-generated method stub
		switch(textureId){
		case R.drawable.boomgun:
			name="炸弹";
			instruction="扔出去炸倒一片敌人";
			setGoodsCost(35,0);
			break;
		case R.drawable.autobulletgun:
			name="自动泡泡枪";
			instruction="子弹可以自动跟踪最近目标，射速较慢，也可以点击任意目标发射";
			setGoodsCost(20,0);
			break;
		case R.drawable.shufudan:
			name="束缚枪";
			instruction="可以将敌人束缚在原地，点击任意目标发射，或者自动寻找敌人";
			setGoodsCost(20,0);
			break;
		case R.drawable.s:
			name="霰弹枪";
			instruction="大范围发射子弹";
			setGoodsCost(20,0);
			break;
		case R.drawable.guangdan:
			name="光弹枪";
			instruction="威力大 范围广 射速慢";
			setGoodsCost(10,0);
			break;
		case R.drawable.jujidan:
			name="狙击枪";
			instruction="射出的子弹可以穿透弱小目标，射速慢";
			setGoodsCost(30,0);
			break;
		case R.drawable.huck:
			setGoodsCost(15,0);
			instruction="或者将自己拉向地形边缘，点击地形发射或解除。\n如果拉到敌人也会把他拉过来";
			name="钩爪";
			break;
		case R.drawable.daodan:
			setGoodsCost(40,0);
			name="火箭筒";
			instruction="直线运行，造成范围伤害";
			break;
		case R.drawable.putongdan:
			setGoodsCost(20,0);
			name="步枪";
			instruction="快速射击的步枪，威力一般射速较快。";
			break;
		}
	}
	public boolean loadAble(BattleMan player){
		for(Fruit f:FruitSet.pickedList)
			if(f.getTextureId()==getTextureId()){
				MenuActivity.showDialog("", "限购一个", getIcon());
				return false;// can't get more blade cause it is useless
			}
		
		super.loadAble(player);
		return true;
	}
	public void use( BattleMan player,ArrayList<Fruit> pickedList){
		player.changeGun(getTextureId());
	}
}
