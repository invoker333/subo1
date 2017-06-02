package Mankind;

import javax.microedition.khronos.opengles.GL10;

import aid.Client;
import aid.ConsWhenConnecting;
import aid.Log;

import com.mingli.toms.BattleActivity;
import com.mingli.toms.MenuActivity;
import com.mingli.toms.MusicId;
import com.mingli.toms.World;

import element2.Tail;
import element2.TexId;
import Element.AnimationMove;
import Element.Pifeng;
import Enviroments.Fruit;
import Enviroments.FruitSet;
import Enviroments.Grass;
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
	protected  int toukuiTime;
    protected  int gaoTime;
    public  int flyTime;
	public  boolean[] downData ;
	  boolean doubleClicked;
		private Pifeng pifeng;
		Tail footTail;
	  Shader shader;	
	  private void initEffect(float x, float y) {
			DEATHSPEED=super.DEATHSPEED/2;
			
			shader=new Shader(0.05f, this);
	        
			pifeng=new Pifeng(this,5);
			
	        footTail=new Tail(10,TexId.CANDLETAIL);
	        footTail.w=48;
		}
	public boolean isDoubleClicked() {
		return doubleClicked;
	}
	public void setDoubleClicked(boolean doubleClicked) {
		this.doubleClicked = doubleClicked;
		shader.backToMaster();
	}
	public void StopDoubleClick() {
		// TODO Auto-generated method stub
		setDoubleClicked(false);
	}

    int death;
    protected int brake;
    protected int destorySound;
    public void loadSound() {
        brake = MusicId.brake01;
        setSoundId(MusicId.walker);
        death = MusicId.gameover;
        destorySound = MusicId.wood2;
    }

	 void actCheck(Creature controller) {
	    	
	        if (!downData[0] && !downData[1]) {
				controller. stopMove();
				setDoubleClicked(false);
			}
	    	
	        if (downData[0]) {
	            downData[1] = false;// ��ֹ�����ж�
	            if (isDoubleClicked())
	                controller.changeSpeed(-1.5f);
	            else
	            	controller. changeSpeed(-1);
	            
	            controller. turnLeft();
	            if(controller!=this)  faceLeft();
	            if (controller.getxSpeed() == getxSpeedMax() && isJumpAble()) playSound(brake);
	        }
	        if (downData[1]) {
	            downData[0] = false;// ��ֹ�����ж�
	            if (isDoubleClicked())
	            	controller. changeSpeed(1.5f);        	
	            else
	            	controller. changeSpeed(1);
	            
	            controller.turnRight();
	           if(controller!=this)faceRight();
	            
	            if (controller.getxSpeed() == getxSpeedMin() && isJumpAble()) playSound(brake);
	        }
	    
	        if (downData[2]) {
//	            if (controller.isAttackAble()) {
	            	controller.attack();
	            	attack();
//	            }
	        } 
	        
	        gunAngleAndCdCheck();
	     
	        if(downData[7]){
	        	if(!doubleClicked)setDoubleClicked(true);
	        }else {
	        	setDoubleClicked(false);
	        }
	       
	    }
	 void setGunLength() {
		gun.setGunLength(sizeRate*baseGunLength);
	}
	public BattleMan(char bi, GrassSet gra, float x, float y,int force_in_battle,int userId) {
		super(bi, gra, x, y);
		this.userId = userId;
		// TODO Auto-generated constructor stub
		
		cloth.setTextureId(TexId.CLOTH);
		cap.setTextureId(TexId.CAP);
		expression.setTextureId(TexId.EXPRESSION);
		
		treadable=false;
		attack=0;
		this.force_in_battle = force_in_battle;
		downData = new boolean[8];
		
		initEffect(x, y);
		 initVtDestory();
	}
	   float vtDestory;//�ƻ�����С�ٶ�
	    private double E;//�ƻ�����С����
	private void initVtDestory() {
		// TODO Auto-generated method stub
		vtDestory=-(float) Math.sqrt(2*getGra().getGrid()*getG());
		Log.i("vtDestory"+vtDestory);
//        vtDestory = -(float) Math.sqrt(2 * (getJumpHeight() - getGra().getGrid()) * getgMax()) - 2 * getgMax();
        E = Math.pow(vtDestory, 2);//�� ���ܹ�ʽΪE=v^2
	}
    public int getToukuiTime() {
        return toukuiTime;
    }
     boolean crossToCheck(int goreId) {
		// TODO Auto-generated method stub
		Grass gg=gList.get(goreId);
		int dir=x>gg.x?1:-1;

		float max=gra.getGrid()/2+wEdge;
		float dw = dir*max-(x-gg.x)-xSpeed;// xSpeed 1 is more to avoid move inside grass
		
		float most = gra.getGrid()/4;
		if(Math.abs(dw)>most)return false;
		
		setxPro(getxPro() + dw);
		return true;
	}
    protected void tooHigh() {
        int goreId;
        int x1 = (int) (x / getGra().getGrid());
        if ((goreId = getGra().map[x1][getMy1()]) == getGra().getZero()) {
            x1 = getMx1();
            goreId = getTopId();
            if(crossToCheck(goreId))return;
        }

        if (toukuiTime > 0) destory(goreId, x1, getMy1());//���ƻ� Ҫ��Ȼש��᲻��ʧ
         {
//            getGra().up(goreId, xSpeed,ySpeed);
        	 getGra().up(goreId, 0,ySpeed);
            goreEnemyCheck();
//            goreCoinCheck();
        }

        super.tooHigh();
//		if(getVt()>-vtDestory)
    }
    private void goreEnemyCheck() {
        Creature e;
        AnimationMove goreAni = gra.goreAni;
        for (int i = 0; i < enemyList.size(); i++) {
            e = enemyList.get(i);
            if(!e.isDead)
            if (Math.abs(e.x-goreAni.x)<e.w+goreAni.w
            		&&Math.abs(e.y-goreAni.y)<e.h+goreAni.h) {
                // e.setVt(e.getVt() + ySpe/20f);
                e.setxSpeed(e.getxSpeed() + goreAni.getxSpeed());
                e.setySpeed(e.getySpeed() + goreAni.getySpeed());
                e.attacked((int) (10 * goreAni.getySpeed()));
            }

        }
    }
    boolean destory(int grassId, int x1, int my1) {
    	Grass g=gra.getgList().get(grassId);
    	if(!g.canBeBreak){
    		return false;
    	}
        gra.particleCheck(grassId, 5, this);
        getGra().toNull(grassId, x1, my1);
        playSound(destorySound);
        return true;
    }

	public void changeToukui(int time) {
		// TODO Auto-generated method stub
		this.setToukuiTime(this.getToukuiTime() + time);
		this.getCap().setTextureId(TexId.TOUKUI);
	}
    public void changeGao(int time) {
		// TODO Auto-generated method stub
    	  gaoTime += time;
    	  foot.setTextureId(TexId.GOLDENFOOT);
    	  foot1.setTextureId(TexId.GOLDENFOOT);
	}

    public void setToukuiTime(int toukuiTime) {
       this. toukuiTime = toukuiTime;
    }

    public int getGaoTime() {
        return gaoTime;
    }

	private void dropToCheck(int goreId) {
		// TODO Auto-generated method stub
		Grass gg=gList.get(goreId);
		int dir=x<gg.x?1:-1;
		
		double v = Math.sqrt(Math.abs(2*1*(x-gg.x)));
		float speed = (float) (dir*v);
		if(Math.abs(xSpeed)<speed)xSpeed=speed;
		
	}
 
	 protected void tooDown() {
	    	fallen=true;
	        if (getySpeed() < vtDestory) {
	            int goreId;
	            int x1 = (int) (x / getGra().getGrid());
	            if ((goreId = getGra().map[x1][getMy1()]) == getGra().getZero()) {
	                x1 = getMx1();
	                goreId = getLandId();
	            }
	            if (gaoTime>0&&downData[5]) {
	                boolean destoryed=destory(goreId, x1, getMy1());//���ƻ� Ҫ��Ȼש��᲻��ʧ
	              if(destoryed) {
	            	  setySpeed((float) -Math.sqrt(Math.pow(ySpeed, 2) - E));//��������ʧ
	            	  gaoTime--;
	            	  dropToCheck(goreId);
	              }
	                //p*v^2p*v^2=E��
	            } 
	            else {
	          	  //fog trick lightning
	                getgList().get(goreId).setRgb((float) Math.random(),
	                        (float) Math.random(), (float) Math.random());
	            }
	        } 
	        super.tooDown();
	        
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
				gun=new HookGun(getEnemySet(),  gra, this, 1);
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
		if(haveBlade(textureId)) {
			if(World.rpgMode){
				noBlade();
			}
			return;
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
	   int wudiTimeBorn=60;
		 public int wudiTime=wudiTimeBorn;// wudi time
	public void incWudiTime(int time) {
		// TODO Auto-generated method stub
		if((this.wudiTime+=time)>time) this.wudiTime=time;
		
	}
	public void attacked(int attack) {
		if(wudiTime>0)return;
    	if(isDead)return;
    	super.attacked(attack);
        
        if (isDead) {
//        	///duoici
        }
        
//        bloodSecondaryindex=0;
    }
    public void drawElement(GL10 gl) {
        if(gun!=null)gun.drawElement(gl);
        if(isDoubleClicked())shader.drawElement(gl);
        
        if(downData[5]
//     		   &&gaoTime>0
     		   ){
     	   footTail.tringer(x, y-getH());
     	   footTail.drawElement(gl);
//     	   footTail.drawScale(gl);
        }
        
        
        final float alpw=0.5f;// alpha wudi
        if(wudiTime>0){
     	   gl.glColor4f(alpw,alpw,alpw,alpw);
        		super.drawElement(gl);
        		gl.glColor4f(1,1,1,1);
        		wudiTime--;
        }// draw as alpha as wudi
        else{
     	   super.drawElement(gl);
        }
        if(flyTime>0){
     	   pifeng.timerTask();
     	   pifeng.drawElement(gl);
        }
    }
	public void addFlyTime(int time) {
		// TODO Auto-generated method stub
		flyTime+=time;
		pifeng.setPosition(x, y);
	}
	protected void gunAngleAndCdCheck() {
		// TODO Auto-generated method stub
        if(coolingId>0)coolingId--;
        if(GunAngle!=_4){
        	if(gun!=null) {
        		 setGunAngle(GunAngle*180/3.14);//
				if(coolingId<1){
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
	
	private int sendMesId;
	private String[] strOnlineMesSet;
	
	public void sendBattleMessage() {
//		if(battleId++>2){
//    		battleId=0;
		int gunAngle=(int) (GunAngle*100);
    		Client.sendUdp(ConsWhenConnecting.THIS_IS_BATTLE_MESSAGE+
    				MenuActivity.userId+" "+BattleActivity.roomId+" "+(int)x+" "+(int)y+" "+(int)xSpeed+" "+(int)ySpeed+" "+gunAngle+" "
    				+gunFruitId+" "+fdirection+" "+bladeFruitId+" "+sendMesId+++" "///////////
    				+downData[0]+" "+downData[1]+" "+downData[2]+" "+downData[7]+" "+isDead);
//    	}
	}
	public void timerTask(){
		super.timerTask();
		onlineAction();
		actCheck(this);
	}
	public void onlineActionCheck(String[] strSet) {
		this.strOnlineMesSet=strSet;
	}
	int idMaxREceive;
	private void onlineAction() {
		if(strOnlineMesSet==null)return;
		if(Integer.parseInt((strOnlineMesSet[10]))<idMaxREceive){
			
			return;
		}
		int x=Integer.parseInt((strOnlineMesSet[2]));
		int y=(Integer.parseInt(strOnlineMesSet[3]));
		int xSpeed=(Integer.parseInt(strOnlineMesSet[4]));
		int ySpeed=(Integer.parseInt(strOnlineMesSet[5]));
		speedMakeUp(x,y,xSpeed,ySpeed);
		
		GunAngle=(Integer.parseInt(strOnlineMesSet[6])/100f);
		changeGun(Integer.parseInt((strOnlineMesSet[7])));
//		fdirection=(Integer.parseInt(strOnlineMesSet[8]));
//			{
//				if(fdirection<0)turnLeft();
//				else if(fdirection>0)turnRight();
//				else stopMove();
//			}
		changeBlade(Integer.parseInt((strOnlineMesSet[9])));
		idMaxREceive=(Integer.parseInt(strOnlineMesSet[10]));
		
		downData[0]=Boolean.parseBoolean(strOnlineMesSet[11]);
		downData[1]=Boolean.parseBoolean(strOnlineMesSet[12]);
		downData[2]=Boolean.parseBoolean(strOnlineMesSet[13]);
		
		downData[7]=Boolean.parseBoolean(strOnlineMesSet[14]);
		
		strOnlineMesSet=null;
	}
	public void setJumpHeight(int jumpHeight) {
		super.setJumpHeight(jumpHeight);
		Log.i(this.getClass().getName()+"jumpHeight: "+jumpHeight);
		Log.i("			ySpeedMax: "+getySpeedMax());
	}
	private void speedMakeUp(int x, int y, int xSpeed, int ySpeed) {
		// TODO Auto-generated method stub
		
		float dx = x-this.x;
		float dy = y-this.y;
		float dySpeed=ySpeed-this.ySpeed;
//		float dxSpeed=xSpeed-this.xSpeed;
		
		
		float dyAbs = Math.abs(dy);
		if(dyAbs<5){// inner 5 wucha is pommit yunxude
		}	else
		if(dyAbs<64){
			if(Math.abs(dySpeed)>5){
				float dyToTop = ySpeed*ySpeed/2+dyAbs;//yspeed 顶点到现在位置的距离yinstance
				if(dy>0){
					this.ySpeed=(float) Math.sqrt(2*dyToTop);
				}else {
					this.ySpeed=-(float) Math.sqrt(Math.abs(2*dyToTop));
				}
			}else{}// inner 5 wucha is pommit yunxude
		}else this.yPro=y;

		float dxAbs = Math.abs(dx);
		if(dxAbs<10){// inner 5 wucha is pommit yunxude
			stopMove();//must
		}else 
		if(dxAbs<128){
//			if(Math.abs(dxSpeed)>5){
				float dxToGoal = xSpeed*xSpeed/2+dxAbs;//xspeed 顶点到现在位置的距离xinstance
				if(dx>0){
					this.xSpeed=(float) Math.sqrt(2*dxToGoal);
				}else {
					this.xSpeed=-(float) Math.sqrt(Math.abs(2*dxToGoal));
				}
//			}
		
		}else this.xPro=x;//abs >10
	}
	protected void relifeJust() {
		isDead=false;
		setLife(getLifeMax());
		alpha=1;
		angle=0;
	}
	public void reLife(){
		reLife(wudiTimeBorn);
	}
	public void reLife(int time){
		relifeJust();
		
		wudiTime=time;
//		setGotGoal(false);

		xSpeed=0;
	}
	  public float growSpeed;
	public void useItemOnline(String itemName) {
		// TODO Auto-generated method stub
		char itemMapSign=itemName.charAt(0);
		FruitSet.useItem(this,itemMapSign);
	}
	public void fenshen(int count) {
		// TODO Auto-generated method stub
		
	}
	public void sendUseitemMessage(Fruit f) {
		// TODO Auto-generated method stub
		Client.send(ConsWhenConnecting.USE_ITEM+MenuActivity.userId+" "+f.mapSign);
	}
	
}
