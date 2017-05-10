package Mankind;

import aid.Client;
import aid.ConsWhenConnecting;

import com.mingli.toms.MenuActivity;
import com.mingli.toms.World;

import element2.TexId;
import Enviroments.GrassSet;
import Weapon.AutoBubbleGun;
import Weapon.AutoBulletGun;
import Weapon.BoomGun;
import Weapon.GuangDanGun;
import Weapon.Gun;
import Weapon.HookGun;
import Weapon.MissileGun;
import Weapon.ShotGun;
import Weapon.TailGun;

public class BattleMan extends JointCreature{
	public int userId;//userId
	int force_in_battle;
	
	protected static final int _4 = 4;
	private  final float baseGunLength = 64;
	public    float GunAngle=4;
	public Gun gun;
	protected int coolingId;
	public   int gunFruitId=-1;
	public   int bladeFruitId=-1;
	 void setGunLength() {
		gun.setGunLength(sizeRate*baseGunLength);
	}
	public BattleMan(char bi, GrassSet gra, float x, float y,int force_in_battle) {
		super(bi, gra, x, y);
		// TODO Auto-generated constructor stub
		treadable=false;
		attack=0;
		this.force_in_battle = force_in_battle;
	}
	
	public void changeGun(int textureId) {
		if(textureId==-1)return;
		if(haveGun(textureId)){
			if(World.rpgMode){
				noGun();
			}
			return;
		}
		
		else{
			if(textureId== TexId.BOOMGUN)
				gun=new BoomGun(getEnemySet(),  gra, this, 5);
			else	if(textureId== TexId.SHUFUDAN)
				gun=new AutoBubbleGun(getEnemySet(),  gra, this, 5);
			else if(textureId== TexId.ZIDONGDAN)
				gun=new AutoBulletGun(getEnemySet(),  gra, this, 10);
			else  if(textureId== TexId.SHOTGUN)
				gun=new ShotGun(getEnemySet(),  gra, this, 15);
			else if(textureId== TexId.JUJI)// 
				gun=new TailGun(getEnemySet(),  gra, this, 3);
			else if(textureId== TexId.MISSILE)
				gun=new MissileGun(getEnemySet(),  gra, this, 4);
			else if(textureId== TexId.HOOKGUN)
				gun=new HookGun(getEnemySet(),  gra, this, 5);
			else if(textureId== TexId.PUTONGQIANG)
				gun=new Gun(getEnemySet(),  gra, this, 10);
			else if(textureId== TexId.GUANGDANQIANG)
				gun=new GuangDanGun(getEnemySet(),  gra, this, 4);
		}
		
		setGunLength();
		
		haveGun();
		gunFruitId=textureId;
	}

	public void changeBlade(int textureId) {
		if(textureId==-1)return;
		// TODO Auto-generated method stub
		if(World.rpgMode&&haveBlade(textureId)) {
			noBlade();
			
		} else {
			haveBlade();
			bladeFruitId=textureId;
		}
	}
	public void haveBlade() {
		super.haveBlade();
	}
	 public void noBlade() {
		super.noBlade();
		bladeFruitId=-1;
	}
		void haveGun(){
			super.haveGun();
		}
		void noGun(){
			super.noGun();
			gunFruitId=-1;
			gun=null;
		}
		public boolean haveGun(int textureId) {
			return gunFruitId==textureId;
		}
	public boolean haveBlade(int textureId) {
		return bladeFruitId==textureId;
	}
	
	
	protected void gunAngleAndCdCheck() {
		// TODO Auto-generated method stub
        if(coolingId>0)coolingId--;
        if(GunAngle!=_4){
        	if(gun!=null) {
        		 setGunAngle(GunAngle*180/3.14);//
				if(coolingId==0){
					gun.gunCheck(GunAngle);
					coolingId=gun.cd;
				}
			}
        }
	}
	public boolean culTreadSpeedAndCanBeTread(Creature c){
//		if(false)
		super.culTreadSpeedAndCanBeTread(c);
		return false;
	}
	int battleId;
	public void sendBattleMessage() {
//		if(battleId++>2){
//    		battleId=0;
    		Client.send(ConsWhenConnecting.THIS_IS_BATTLE_MESSAGE+
    				MenuActivity.userId+" "+(int)x+" "+(int)y+" "+(int)GunAngle+" "+gunFruitId+" "+fdirection+" "+bladeFruitId);
//    	}
	}
	public void timerTask(){
		super.timerTask();
		gunAngleAndCdCheck();
	}
	public void onlineActionCheck(String[] strSet) {
		// TODO Auto-generated method stub
		setPosition(Integer.parseInt(strSet[1]),Integer.parseInt(strSet[2]));
		GunAngle=Integer.parseInt(strSet[3]);
		changeGun(Integer.parseInt(strSet[4]));
		fdirection=Integer.parseInt(strSet[5]);
		changeBlade(Integer.parseInt(strSet[6]));
	}
	
}
