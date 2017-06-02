package Mankind;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Element.AnimationMove;
import Element.Pifeng;
import Enviroments.Goal;
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
import aid.Client;
import aid.ConsWhenConnecting;
import aid.Log;

import com.mingli.toms.MenuActivity;
import com.mingli.toms.MusicId;
import com.mingli.toms.Render;
import com.mingli.toms.World;

import element2.Tail;
import element2.TexId;
public class Player extends BattleMan {
    private static final float DOUBLE_JUMP_RATE = 4f/3f;
	
    public  float px, py;
    public World world;

    public static float dpx;
    public static float dpy;
   
    
   
    
    Creature controller;
	public Goal goal;
    
	private boolean tooLong;
	

	
    public Player(char mapSign,GrassSet gra, World world,float x,float y) {
    	super(mapSign, gra, x, y, world.force_in_battle, MenuActivity.userId);
    	// this two value may not be intlized
    	
    	
    	this.world = world;
    	controller=this;
        loadSound();
        initView();
       
//        setTextureId(TexId.BUBBLE);
//        speedMaxBack=10;
//        		getxSpeedMax();
        initGuideTail(gra);     
        reSetDownDate();
        attack=World.baseAttack;
        lifeCount=1;
        treadable=false;
    }

	
	public void changeGun(int textureId) {
		Log.i("supergunFruitId"+super.gunFruitId);
		super.changeGun(textureId);
		extendsGunFruitId=textureId;
		Log.i("gunFruitId"+gunFruitId);
	}
	 public void changeBlade(int textureId) {
		super.changeBlade(textureId);
		extendsBladeFruitId=textureId;
		Log.i("BladeFruitId"+bladeFruitId);
	}
	private void extendsDate() {
		Log.i("GunBlade"+gunFruitId+" "+bladeFruitId);
		Log.i("superGunBlade"+super.gunFruitId+" "+super.bladeFruitId);
		  changeGun(extendsGunFruitId);
	        changeBlade(extendsBladeFruitId);
	        Log.i("GunBlade"+gunFruitId+" "+bladeFruitId);
			Log.i("superGunBlade"+super.gunFruitId+" "+super.bladeFruitId);
	        if(toukuiTime>0)this.changeToukui(0);
	        if(gaoTime>0)this.changeGao(0);
	}
	private void reSetDownDate() {
		// TODO Auto-generated method stub
		for(int i=0;i<downData.length;i++){
		downData[i]=false;
		}
	}

	public void setEnemySet(EnemySet enemySet){
    	super.setEnemySet(enemySet);
//    	 ab=new AutoBubble(enemySet,  gra, this);
    	 extendsDate();// avoid gun has not enemySet
    
//    	 testAutoBullet();
    }
    public void startTouch(float ex1,float ey1){
    	this.ex1 = ex1;
    	this.ey1 = ey1;
    	float ex = Render.px + ex1;
		float ey = Render.py + ey1;
//		   if(this.isBenti)
		   {
	        	for(Creature c:friendList){
	        		if(c instanceof BattleMan&&((BattleMan) c).downData==this.downData
	        				&&!c.isDead){
	        			float xx=c.x;float yy=c.y;
	        			if(c!=this&&
	        					Math.abs(xx-ex)<getW()&&
	        					Math.abs(yy-ey)<getH()){
	        				c.setPosition(x, y);
		        			setPosition(xx,yy+gethEdge()-c.gethEdge());
		        			return;
	        			}
	        		}
	        	}
	        }
    	if(gun!=null&&
    			(gun instanceof AutoBulletGun
    					||gun instanceof HookGun
    					)
    			){
    			if(coolingId==0){
    					gun.gunCheck(ex1, ey1);
    				coolingId=gun.cd;
    				return;
    			}
		}
    	else {
    		if(flyTime<1)return;
    		
    		flyTime--;
    		touched=true;
    	}
    
    }

    
    public void endTouch(float ex1,float ey1){
    	if(touched)// else it will be set speed but not touch
    		if(!tooLong) {
				//    			if(fallen)
    			setSpeed(xGuideSpeed, yGuideSpeed);
    			playSound(MusicId.land);
			}
		touched=false;
		this.ex1 = ex1;
		this.ey1 = ey1;
    }
   
    private void drawGuideTail(GL10 gl) {
		// TODO Auto-generated method stub
    	calcuPlayerSpeed(ex1, ey1);
    	setGuideSpeed(xGuideSpeed, yGuideSpeed);
    	
    	
    	final	float  rangeTime=1;
    	
    	if(!isJumpAble()||
    			Math.abs(xGuideSpeed)>rangeTime*this.getxSpeedMax()
    			||Math.abs(yGuideSpeed)>DOUBLE_JUMP_RATE*rangeTime*this.getySpeedMax()){
    		gl.glColor4f(1, 0, 0, 0.5f);
    		tooLong=true;
    	}
    	else{
    		gl.glColor4f(0, 1, 0, 0.5f);
    		tooLong=false;
    	}
    	guideTail.drawElement(gl);
    	gl.glColor4f(1, 1, 1, 1);
//    	drawGuideCre(gl);////20170116
	}
    
    
    float guideAlpha=0.5f;
   	private float guideAlphaSpeed=0.016f;
	private void drawGuideCre(GL10 gl) {
		// TODO Auto-generated method stub
    	if(guideAlpha<guideAlphaSpeed||guideAlpha>1-guideAlphaSpeed)
    	guideAlphaSpeed=-guideAlphaSpeed;
    	guideAlpha+=guideAlphaSpeed;
    	
    	
    	gl.glColor4f(guideAlpha, guideAlpha, guideAlpha, guideAlpha);
    	guideCre.drawElement(gl);
    	gl.glColor4f(1, 1, 1, 1);
	}
    
    
	float ex1,ey1;
//	AnimationMove drager=new AnimationMove();
  
	float testRate=1;//////
	public void moveAction(float ex2, float ey2) {
//    	drager.setPosition(Render.px+ex2, Render.py+ey2);
    	final float length=30;
    	float dey=ey2-ey1;
    	float dex=ex2-ex1;
    	if(World.editMode){
    		px-=dex;
    		py-=dey;
    	}
    	
    	
    	ex1=ex2;
    	ey1=ey2;
//    	Log.i("dey"+dey);
        if (dey < -length){
            touched=false;
            if(World.bigMode)if(testRate>0.5f)changeSize(testRate-=0.1f);///////
           if( turnDown()){}
           else {
        	   downData[5]=true;
        	   
        	   footTail.startTouch(x, y);
        	   
               if (controller.isJumpAble()) controller.jump(-vtDestory/getySpeedMax());
               if (isJumpAble()) jump(-vtDestory+1);
           }
        }
        else if(dey >length)
        {
        
           touched=false;
           if(World.bigMode)if(testRate<1.5f) changeSize(testRate+=0.1f);/////////
        }
//        if (ex2 - ex1 < -120) {
//            Render.width -= 12.8;
//            Render.height -= 7.68;
//        } else if (ex2 - ex1 > 120) {
//            Render.width += 12.8;
//            Render.height += 7.68;
//        }
    }
    private void calcuPlayerSpeed(float x, float y) {
		x=Render.px+x;
		y=Render.py+y;
		
//		if(x<this.x)
//			x=x-x%grid+-this.getwEdge()+1;//1 point let it stand zhenghao bu diao xiaqu
//		else
//			x=x-x%grid+grid+this.getwEdge()-1;
//		// jump to edge
//		y=y-y%grid+this.gethEdge();
		final int jumpH=16;
		
		float dx=x  -this.x;
		float dy=y   -this.y;
		float jumpY;
		
		double time;
		
		//		if(dx>0)time=dx/this.getSpeedMax();
//		else time=dx/this.getSpeedMin();
		if(dy>0){
			jumpY=dy+jumpH;
			yGuideSpeed=Math.sqrt(2f*this.getG()*jumpY);
//			Log.i("this.getySpeedMax()"+this.getySpeedMax());
			time=Math.sqrt(2*jumpY/this.getG())+Math.sqrt(2*jumpH/this.getG());			// jump time
			xGuideSpeed=dx/time;
		}
		else if(dy<=0){
//			if(this.dropCheck(ex,ey))return;
			final double ys=Math.sqrt(2*this.getG()*jumpH);
			yGuideSpeed=ys;
			///////////////////////
			jumpY=-dy+jumpH;
			time=Math.sqrt(2*jumpH/this.getG())+Math.sqrt(2*jumpY/this.getG());
			xGuideSpeed=dx/time;
		}
	}
	private double yGuideSpeed;
	private double xGuideSpeed;
    public void setGuideSpeed(double xSpeed, double ySpeed) {
		// TODO Auto-generated method stub
//    	Log.i("setXguideSpeed"+xSpeed);
    	guideCre.setPosition(x, y);
    	guideCre.setxSpeed((float) xSpeed);
    	guideCre.setySpeed((float) ySpeed);
    	
    	guideTail.startTouch(x, y);
    	
    	for(int i=0;i<guideTail.count;i++){
    		guideCre.move();
    		guideCre.gravity();
    		
    		// stop cuide tail
    		float dx = guideCre.x-x;
    		if(dx>0) {
				if(guideCre.x>Render.px+ex1)break;
			}
    		else if(guideCre.x<Render.px+ex1)break;
    		
    		
    		guideTail.tringer(guideCre.x,guideCre.y);
    	}
	}

//	private float angle;
    
    Tail guideTail;
    Creature guideCre;
  


//	private boolean isBenti=true;
	private static int relifeLandId;
//	 private Pifeng pifeng2;
	 static int lifeCount=1;
	 
    public void drawElement(GL10 gl) {
    	if(touched)drawGuideTail(gl);	
    	super.drawElement(gl);
     
     
    }
    
	private void initGuideTail(GrassSet gra) {
		// TODO Auto-generated method stub
    	guideTail=new Tail(60);
    	guideTail.w=8;
    	guideCre=new JointCreature(gra);
//    	guideCre.w=w;
//    	guideCre.h=h;
//    	guideCre.setwRate(getwRate());
//    	guideCre.sethRate(gethRate());
//    	guideCre.sizeCheck();
	}
	
   
   

	protected void changeToLandData(){
    	super.changeToLandData();
    	 downData[5]=false;// remove destory jump
    }
    
   

    
	private void initView() {
        if (getGra().getMapWidth() > getGra().getMapHeight()) {//����
        	 if (startX - 0 < getGra().getGrid() * getGra().getMapWidth()//�����
                     - startX) {
                 setMyView(5 / 16f, 8 / 16f, 7 / 16f, 3 / 4f);
//                 px = x - mw1;
                 faceRight();
             } else {
                 setMyView(8 / 16f, 11 / 16f, 7 / 16f, 3 / 4f);
//                 px = x - 8 / 16f;
                 faceLeft();
             }
         } else {
             if (startY - 0 < getGra().getMapHeight() * getGra().getGrid()
                     - startY) {
                setMyView(7 / 16f, 9 / 16f, 5 / 16f, 8 / 16f);
//                py = y - mh1;
                faceRight();
            } else {
                setMyView(7 / 16f, 9 / 16f, 8 / 16f, 11 / 16f);
//                py = y - mh2;
                faceRight();
            }
        }
        xMax =  gra.getGrid()*gra.getMapWidth() - Render.width - gra.getViewGrid();
        yMax =  gra.getGrid()*gra.getMapHeight() - Render.height - gra.getViewGrid();
        Log.i("gra.getViewGrid()"+gra.getViewGrid());
    }
   

    
    boolean turnDown() {
    	Grass gras=getgList().get(getLandId());
        if (isJumpAble() && (gras.turnDown(this) )) {
            y -= 1;
            land();
            return true;
        }
        return false;
    }

    public void stopJump() {
        super.stopJump();
        if (getySpeed() > getG()) playSound();
    }


	public void jump(float rate) {

		
		world.sendMessage(World.JUMP);
		if(doubleClicked)rate*=DOUBLE_JUMP_RATE;
		super.jump(rate);
		playSound(MusicId.land);
		
//		Log.i("Player.jumpRate: "+rate);
//		Log.i("Player.jumpHeight: "+getJumpHeight());
//		Log.i("Player.ySpeed "+ySpeed);
	}


    public void succeed() {
          goal.picked();
          world.succeed();
    }

    public void timerTask() {
    	sendBattleMessage();
    	
    	if(isDead)return;
    	
//    	setViewPot();
    	
    	 if (y < 0 ) {
    		 die();
         }
         if (goal.showable&&goal.hasFirstBlood
        		 && x > goal.x1 && x < goal.x2
                 && y > goal.y1 && y < goal.y2) {
        	 succeed();
         }
         actCheck(controller);
        
         
     	gra.downHoleCheck(x, y);
     	gra.downHoleCheck(x-gra.getGrid(), y);
	}
	
	
	public BattleMan clone(){
		BattleMan p=new BattleMan(mapSign, gra, x, y, -222, -222){
			 public void sendBattleMessage() {}//cloner dong't send message
			 public void playSound(){};
			 public void die(){
				 super.die();
				 lifeCount--;
				 }
			 public void timerTask(){
				 super.timerTask();
			        if(getxPro()<Player.hx1)setxPro((float) (Player.hx1+Math.random()*Render.width));
			          else if(getxPro()>Player.hx2)setxPro((float) (Player.hx2-Math.random()*Render.width));
			          if(getyPro()<Player.hy1)setyPro((float) (Player.hy1+Render.height*Math.random()));
			           else if(getyPro()>Player.hy2)setyPro((float) (Player.hy2-Render.height*Math.random()));
			 }
		};
		p.downData=this.downData;//can be controlled same time
		p.setEnemySet(enemySet);
		p.setFriendSet(friendSet);
//		p.goal=goal;
//		p.isBenti=false;
		return p;
	}
	
	public void fenshen(int count){
		if(isDead)return;
		
		int lifeCount=Player.lifeCount;// new player lead to life count ==1
		for(int i=0;i<count;i++){
			JointCreature c;
			friendList.add(c=this.clone());
//			final int distance=200;
//			c.setPosition((float) (x+distance*2*(Math.random()-0.5)), (float) (y+distance*2*(Math.random()-0.5)));
			
			final int v=10;
			c.setSpeed((float) (xSpeed+v*2*(Math.random()-0.5)), (float) (ySpeed+v*2*(Math.random()-0.5)));
			c.changeSize((float) (0.5+0.5*Math.random()));
			c.cap.setTextureId(TexId.BLANK);
		}
		Player.lifeCount=lifeCount+count;// new player lead to life count ==1
	}
	public void setFriendSet(EnemySet fs){
		super.setFriendSet(fs);
		//TODO
	}
	
	public void drawDeath(GL10 gl){
//		Log.i("alpha"+alpha+"DEATHSPEED"+DEATHSPEED);
		if(alpha>0&&alpha<=DEATHSPEED){
			if(goal.hasFirstBlood){// to inlived death when got goal
				if(lifeCount<1)
				world.gameOver();
			}
		}
		super.drawDeath(gl);
	}
	
	public void reLife(int time){
		super.reLife( time);
		
		Grass footGrass=gList.get(relifeLandId);
		setPosition(footGrass.data[0]+gra.getGrid()/2, footGrass.data[3]+gethEdge()*1.2f);
		lifeCount++;
		world.relife();
	}

    public void die() {
    	if(isDead)return;
    	
    	playSound(MusicId.fresh);
//    	Log.i("flyTime toukuiTime gaoTime"+flyTime+" "+toukuiTime+" "+gaoTime);
    	
    	if(!World.rpgMode){
    		noGun();
    		noBlade();
    	  	if(flyTime>0||toukuiTime>0||gaoTime>0){
        		flyTime=0;
        		toukuiTime=0;this.getCap().setTextureId(TexId.CAP);
        		gaoTime=0;foot.setTextureId(TexId.FOOT);foot1.setTextureId(TexId.FOOT);
        		
        		
        		setLife(getLifeMax());
        		wudiTime=wudiTimeBorn;
        		Log.i("after"+flyTime+" "+toukuiTime+" "+gaoTime);
        		return;// do not die as super
        	}
    	}
    	 super.die();
    	 
//        if(this.isBenti)
        	for(Creature c:friendList){
        		if(c instanceof BattleMan&&((BattleMan) c).downData==this.downData
        				&&!c.isDead){
        			float xx=c.x;float yy=c.y;
        			c.setPosition(x, y);
        			c.die();
        			setPosition(xx,yy+gethEdge()-c.gethEdge());
        			relifeJust();
        			return;
        		}
        	}
//       if(isJumpAble())
    	   jump();
        playSound(death);
        setRgb(1, 0, 0);
        relifeLandId=this.getLandId();
        lifeCount--;
    }
   
    
	public void baseDrawElement(GL10 gl){
    	super.baseDrawElement(gl);
    }
   
  
	public void move(){
    	super.move();
    }
	
	
	
	public void changeSize(float rate){
		super.changeSize(rate);
		
		xSpeed*=0.8f;
		
		if(gun!=null)
			setGunLength();
		}

    public void setViewPot() {
    	if(!World.editMode){
    		moveView();
    		landView();
    	}
    	
		// TODO Auto-generated method stub
    	final float edge=64f;
    	float  width=Render.width;
    	float height=Render.height;
    	 float lField = edge;// 64f
         float rField = width + lField;
         float dField = edge;
         float uField = height + dField;
         gx1 = px - lField;
         gx2 = px + rField;
         gy1 = py - dField;
         gy2 = py + uField;
         
       
        final  float actionWidth=Render.width/2;
         
         hx1 = gx1-actionWidth;
         hx2 = gx2+actionWidth;
         hy1 = gy1-actionWidth;
         hy2 = gy2+actionWidth;
         
         Render.px=px;
         Render.py=py;
	}

	public static float gx1, gx2=1280, gy1, gy2=800;

    public void resume() {
        super.resume();
        if (!isDead) {
            if (!isRunning()) {
                setRunning(true);
                if (!isLiving())
//                    new Thread(this).start();
                land();
            }
        }
//        if (blade != null) blade.resume();
    }

  

//    boolean tread;// ��
	
	public static float hx1;
	public static float hy1;
	public static float hx2;
	public static float hy2;

    public void gravityCheck() {
    	super.gravityCheck();
    	
        treadCheck();
    }
    
    int downIndex;
    
    void actCheck(Creature controller) {
    	
        if (!downData[0] && !downData[1]) {
			controller. stopMove();
			setDoubleClicked(false);
		}
        downIndex++;
//    	Log.i("downIndex+clicked"+downIndex+doubleClicked);
    	
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
//            if (controller.isAttackAble()) {
            	controller.attack();
            	attack();
//            }
        } 
        if (downData[3] && isJumpAble()) {
        	
    		float jumpRate = culJumpRate();
        	
			jump(jumpRate);
			controller.jump(jumpRate);
		}
        
        gunAngleAndCdCheck();
     
        if(downData[6]){
        	if (controller==this){        	
        		if(treader!=null) {
					this.controller=treader;
				}
        	}else {
        		this.controller=this;
        	}
        	downData[6]=false;
        }
        if(downData[7]){
        	if(!doubleClicked)setDoubleClicked(true);
        }else {
        	setDoubleClicked(false);
        }
       
    }
	
	private float culJumpRate() {
		curJumpProgress = jumpProgress;
		float jumpRate = jumpProgress  / 50f;
		if (jumpRate > 1)
			jumpRate = 1;
		return jumpRate;
	}
//    protected void attack() {
//    	super.attack();
//    	xSpeed+=direction*getxSpeedMax();
//    }
    private void treadCheck() {
    	  if(treader!=controller) 
          	  controller=this;//can't controll people who has been not  treaded
    	
        if(treadListCheck( enemySet))return;//caizhong le fan hui
        if(treadListCheck( friendSet))return;
      
       sendIcon(World.NOTREADICON);
    }

    private boolean treadListCheck(EnemySet es) {
    	ArrayList<Creature>enemyList=es.cList;
    	Creature c;
    	 for (int i = 0; i < enemyList.size(); i++) {
             c = enemyList.get(i);
             if(c.isDead)continue;
//             float enemyHead = c.y + c.gethEdge();
             float dy;
             final int footdepth=5;
             if (Math.abs(c.x - x) < c.getwEdge() + getwEdge()
                     && (dy=getyPro()-c.getyPro())<=gethEdge()+c.gethEdge()
                     && dy>Math.min(gethEdge(),c.gethEdge())) {
//            	   tread = true;
            	 float dYspeed=getySpeed() - c.getySpeed();
            	 if(dYspeed>footdepth)continue;//相对向上跳速度相差太大不踩
            	 
           
                if(treader!=c){
                	if(
                			!downData[5]&&
//                			(dYspeed>-6)//36
                			!c.treadable
                			)
						enemySet.treaded(this, c, 0);
					else 
					{
						downData[5]=false;
						enemySet.treaded(this, c, attack);// not tread one much time
						if(downData[3])jump(culJumpRate());
						else ySpeed=c.getySpeed()+11.5f;//128 de ping fang gen
						return true;
					}
                }
                treader=c;
                fallen=true;
             	
              if(c. culTreadSpeedAndCanBeTread(this))                // c carry me so set c's speed as base speed instead of my speed
              sendIcon(World.TREADICON);
                 
                return true;
             } 
    	 }
    	
    	 treader = null;
    	 return false;
      
	}
	 void sendIcon(int i) {
		world.sendMessage(i);
	}
	 public boolean culTreadSpeedAndCanBeTread(Creature c) {
		 return false;
	 }
	


 
	
	private Creature treader;
	private float mh1;
	private float mh2;

	
   


    public void changeState(int step) {

    }
 
   

    public void actionCheckDown(int buttonCheck) {
        switch (buttonCheck) {
            case 0:
                turnLeft();
                break;
            case 1:
                turnRight();
                break;
            case 2:
                if (isJumpAble())
                    jump();
                break;
            case 3:
                if (isAnimationFinished()) {
                    attack();
                }
                if (fdirection != 0 && getDirection() != fdirection) {
                    setDirection(fdirection);
                    attack();
                }
                break;

            // case 4:
            // setPosition(0, 0);
            // resetSpirit();
            // jump();
            // break;
        }
    }

    public void actionCheckUp(int buttonCheck) {
        switch (buttonCheck) {
            case 0:
            case 1:
                stopMove();
                break;
            case 2:
                break;
            case 3:
                setAttackAble(false);
                break;
        }
    }
    public void moveView() {
        float px1 = x - mw1;
        if (px1 < px) {
        	 px = px1;
        }
        
        float left=gra.getViewGrid();
        if (px < left) {
        	px = left;
        	float leftMax=left+getwEdge();
        	if(getxPro()<leftMax){
        		setxPro(leftMax);// too left
        		xSpeed=0;
        	}
        }
       
        
        float px2 = x - mw2;
        if (px2 > px) {
        	  px = px2;
        }
        if (px2 > xMax){
          	px = xMax;
          	float mapxMax=xMax+Render.width*(3/3f)-getwEdge();
          	if(getxPro()>mapxMax){
          		setxPro(mapxMax);//too right
          		xSpeed=0;
          	}
          }
        if(xMax<Render.width)px=0;//avoid tooHigh but viewport less thab
    }
    public void landView() {
        float py1 = y - mh1;
        if (py1 < py) {
        	py = py1;
        }
        if (py < 0) py = 0;

        py1 = y - mh2;
        if (py1 > py) {
            py = py1;
        }
        if (py1>yMax) {
        	py = yMax;
        }
    	if(yMax<Render.height)py=0;//avoid tooHigh but viewport less thab
    }
    float yMax;
    float xMax;
	private float mw1;
	private float mw2;

	

    public void setMyView(float pxL, float pxR, float pyD, float pyU) {
        mw1 = Render.width * pxL;
        mw2 = Render.width * pxR;
        mh1 = Render.height * pyD;
        mh2 = Render.height * pyU;
    }

	public int bloodSecondaryindex;
	public int secondaryLife=getLifeMax();
	

	

	public  static int extendsGunFruitId=-1;
	public  static int extendsBladeFruitId=-1;
    
	
	public boolean touched;
	public static float curJumpProgress;
	public static int jumpProgress=100;

   




    public void increaseScoreBy(int score) {
    	this.setScore(this.getScore() + score);
        world.increaseScore(score);
    }

    public boolean isPlayerDead() {
        return isDead;
    }

    public void setPlayerDead(boolean playerDead) {
        this.isDead = playerDead;
    }


	
	public void CircleDown(float rad) {
		// TODO Auto-generated method stub
		 GunAngle= rad;
		 setAnimationFinished(false);
//		 setGunAngle(angle*180/3.14);// 
	}
	public void CircleUp() {
		 GunAngle=_4;
		 setAnimationFinished(true);
	}
	public void haveBlade() {
		super.haveBlade();
		world.sendMessage(World.BLADEICON);
	}	

	 public void noBlade() {
		super.noBlade();
		extendsBladeFruitId=-1;
		world.sendMessage(World.NOBLADEICON);
	}
	void haveGun(){
		super.haveGun();
		world.sendMessage(World.GUNICON);
	}
	void noGun(){
		super.noGun();
		extendsGunFruitId=-1;
		gun=null;
		world.sendMessage(World.NOGUNICON);
	}
	public boolean haveBlade(int textureId) {
		return bladeFruitId==textureId;
	}
	public boolean haveGun(int textureId) {
		return gunFruitId==textureId;
	}
	public void increaseCoinBy(int i) {
		// TODO Auto-generated method stub
		world.increaseCoin(i);
	}
	public void increaseChanceBy(int ch){
		world.increaseChance(ch);
	}

	public void doubleDownCheck() {
		if(true)return;
		
//		if(downIndex<20)setDoubleClicked(true);
//		downIndex=0;
	}
	public void attacked(int attack) {
		if(wudiTime>0)return;
    	if(isDead)return;
    	super.attacked(attack);
        world.showLifeColumn(this);
    }
	public void useItemOnline(String itemName) {}
	
}
