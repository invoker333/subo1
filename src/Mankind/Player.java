package Mankind;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Element.Animation;
import Element.AnimationGravity;
import Element.AnimationMove;
import Enviroments.Goal;
import Enviroments.Grass;
import Enviroments.GrassSet;
import Enviroments.Toukui;
import Weapon.AutoBubble;
import Weapon.AutoBullet;
import Weapon.AutoBulletGun;
import Weapon.Gun;
import Weapon.HookGun;
import Weapon.BoomGun;
import Weapon.ShotGun;
import Weapon.TailGun;

import com.mingli.toms.Log;
import com.mingli.toms.MenuActivity;
import com.mingli.toms.MusicId;
import com.mingli.toms.R;
import com.mingli.toms.Render;
import com.mingli.toms.World;

import element2.ParticleBallRandom;
import element2.Tail;
import element2.TexId;
public class Player extends JointCreature {
    private static final int _4 = 4;
	private  final float baseGunLength = 64;
	public    float GunAngle=4;
    float hMax = getH();
    public  float px, py;
    private World world;

    public static float dpx;
    public static float dpy;
   
    
    private boolean doubleClicked;
    public static boolean[] downData = new boolean[7];
//    private ParticleSet ps;
    Creature controller;
    float speedMaxBack;
	public Goal goal;
    private boolean gotGoal;
    
    AutoBullet ab;
	public int autoBulletTime;
	private boolean tooLong;
	
	Tail footTail;
	
	private Shader shader;	
    public Player(char mapSign,GrassSet gra, World world,float x,float y) {
    	super(mapSign, gra, x, y);
    	shader=new Shader(0.05f, this);
    	
    	
        DEATHSPEED=super.DEATHSPEED/2;
        
        clothTail=new Tail(2,TexId.RAINBOW);
        footTail=new Tail(5,TexId.CANDLETAIL);
        footTail.width=4;
        {
        	clothMove=new AnimationGravity();//20170131
//        	float cw=8;
//        	clothMove.w=cw;
//        	clothMove.h=cw;
//        	clothMove.loadTexture();
        	clothMove.setPosition(x, y);
        }
        controller=this;
		vtDestory=-(float) Math.sqrt(2*getGra().getGrid()*getG());
		Log.i("vtDestory"+vtDestory);
//        vtDestory = -(float) Math.sqrt(2 * (getJumpHeight() - getGra().getGrid()) * getgMax()) - 2 * getgMax();
        E = Math.pow(vtDestory, 2);//�� ���ܹ�ʽΪE=v^2
        this.world = world;
        xMax =  gra.getGrid()*gra.getMapWidth() - Render.width - gra.getViewGrid();
        loadSound();
      
      

        
        initView();
        setTextureId(TexId.BUBBLE);
        
        
        speedMaxBack=10;
//        		getxSpeedMax();
        
        
        initGuideTail(gra);     	
        
        changeGun(gunFruitId);
        changeBlade(bladeFruitId);
        if(toukuiTime>0)this.changeToukui(0);
        if(gaoTime>0)this.changeGao(0);
    }
	public void changeToukui(int time) {
		// TODO Auto-generated method stub
		this.setToukuiTime(this.getToukuiTime() + time);
		this.getCap().setTextureId(TexId.TOUKUI);
	}
    public void changeGao(int time) {
		// TODO Auto-generated method stub
    	  Player.gaoTime += time;
    	  footTail.startTouch(x, y);
	}
	public void setEnemySet(EnemySet enemySet){
    	super.setEnemySet(enemySet);
    	 ab=new AutoBubble(enemySet,  this);
    }
    public void startTouch(float ex1,float ey1){
    	this.ex1 = ex1;
    	this.ey1 = ey1;
    	
    	if(autoBulletTime>0){
    		ab.tringerCheck(ex1, ey1);
    		autoBulletTime--;
    	}
    	
    	if(flyTime<1)return;
    	flyTime--;
    	touched=true;
    
    }

    
    public void endTouch(float ex1,float ey1){
    	if(touched)// else it will be set speed but not touch
    		if(!tooLong)
    			if(fallen)
    			setSpeed(xGuideSpeed, yGuideSpeed);
		touched=false;
		this.ex1 = ex1;
		this.ey1 = ey1;
    }
   
    private void drawGuideTail(GL10 gl) {
		// TODO Auto-generated method stub
    	calcuPlayerSpeed(ex1, ey1);
    	setGuideSpeed(xGuideSpeed, yGuideSpeed);
    	
    	
    	float rangeTime=2;
    	
    	if(Math.abs(xGuideSpeed)>rangeTime*this.getxSpeedMax()
    			||Math.abs(yGuideSpeed)>rangeTime*this.getySpeedMax()){
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
  
	public void moveAction(float ex2, float ey2) {
//    	drager.setPosition(Render.px+ex2, Render.py+ey2);
    	
    	
    	final float length=40;
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
            turnDown();
            touched=false;
        }
        else if(dey >length)
        {
           downData[5]=true;
           if (controller.isJumpAble()) controller.jump(-vtDestory);
           if (isJumpAble()) jump(-vtDestory+1);
           touched=false;
           
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
		final int jumpH=64;
		
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
    Tail clothTail;
    Tail guideTail;
    Creature guideCre;
    
    AnimationMove clothMove;
   int wudiTimeBorn=180;
	private int wudiTime=wudiTimeBorn;// wudi time
    
    public void drawElement(GL10 gl) {
    	if(touched)drawGuideTail(gl);	
    	timerTask();
    	
    	shader.drawElement(gl);
     
   
       if(gun!=null)gun.drawElement(gl);
       if(flyTime>0){
    	   clothTail.startTouch(x, y);
           clothTail.tringer(clothMove.x, clothMove.y);
           clothTail.drawElement(gl);
       }
       
       if(gaoTime>0){
    	   footTail.tringer(x, y-getH());
//    	   footTail.drawElement(gl);
    	   footTail.drawScale(gl);
       }
       
       if(autoBulletTime>0)ab.drawElement(gl);
       
       
       if(wudiTime>0){
    	   gl.glColor4f(0.5f,0.5f,0.5f,0.5f);
       		super.drawElement(gl);
       		wudiTime--;
       }// draw as alpha as wudi
       else super.drawElement(gl);
    }
    
	private void initGuideTail(GrassSet gra) {
		// TODO Auto-generated method stub
    	guideTail=new Tail(60);
    	guideTail.width=8;
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
                gaoTime--;
                destory(goreId, x1, getMy1());//���ƻ� Ҫ��Ȼש��᲻��ʧ
                setySpeed((float) -Math.sqrt(Math.pow(ySpeed, 2) - E));//��������ʧ
              
                //p*v^2p*v^2=E��
            } else {
                getgList().get(goreId).setRgb((float) Math.random(),
                        (float) Math.random(), (float) Math.random());
                super.tooDown();
            }
        } else {
        	super.tooDown();
        }
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
    }
   

    
    void turnDown() {
    	Grass gras=getgList().get(getLandId());
        if (isJumpAble() && (gras.isIsburrow() ||gras.turnDown(this) || treader!=null)) {
            y -= 1;
            land();
            
//           gra.downHoleCheck((int) (x/gra.getGrid()),(int) ((y-gethEdge())/gra.getGrid()));
//           gra.downHoleCheck(getMx1(),getMy1()+1);
           	
        }
    }
//    protected void downCheck(int mx,int my){
//    	super.downCheck(mx,my);
//    }
//    public boolean dropCheck(float ex,float ey){
//    	ex/=gra.getGrid();
//    	ey/=gra.getGrid();
//    	Grass gr=gra.getFromXY((int) ex,(int) ey);
//    	
//    	if(gr.getTextureId()==TexId.BAMBOO)
//    		gr.setTextureId(TexId.BAMBOOHEART);
//    	
//    	if(gr.isCrossAble()){
//    		land();
//    		return true;
//    	}
//		return false;
//    	
//    }

    public void stopJump() {
        super.stopJump();
        if (getySpeed() > getG()) playSound();
    }


	public void jump(float rate) {
		super.jump(rate);
		playSound(EXjump);
//		Log.i("Player.jumpRate: "+rate);
//		Log.i("Player.jumpHeight: "+getJumpHeight());
//		Log.i("Player.ySpeed "+ySpeed);
	}
	public void setJumpHeight(int jumpHeight) {
		super.setJumpHeight(jumpHeight);
		Log.i("Player.jumpHeight: "+jumpHeight);
		Log.i("Player.ySpeedMax: "+getySpeedMax());
	}

    private void timerTask() {
    	
    	
    	 if (y < 0 && !isDead) {
//         	isDead = true;
    		 die();
         }
         if (!gotGoal && x > goal.x1 && x < goal.x2
                 && y > goal.y1 && y < goal.y2) {
             gotGoal = true;
             goal.playSound();
             world.succeed();
         }
         // randomColor();
         actCheck(controller);
         

     	gra.downHoleCheck(x, y);
         
//         actCheck(wheel);
    	clothMove(); 
	}
	private void clothMove() {
		final float dsmax=getH();
    	final float tanxingxishu= 0.1f;
    	final float zuni=0.9f;
    	clothMove.move();
    	clothMove.gravity();
   		this.stringCheck(clothMove, dsmax, tanxingxishu, zuni);
	}
	public void attacked(int attack) {
		if(wudiTime>0)return;
    	if(isDead)return;
    	super.attacked(attack);
        world.showLifeColumn(this);
        
        if (isDead) {
//        	///duoici
//        	world.gameOver();
        }
        
//        bloodSecondaryindex=0;
    }
	public void drawDeath(GL10 gl){
//		Log.i("alpha"+alpha+"DEATHSPEED"+DEATHSPEED);
		if(alpha>0&&alpha<=DEATHSPEED){
			if(!gotGoal)// to inlived death when got goal
			world.gameOver();
		}
		super.drawDeath(gl);
	}
	public void reLife(){
		wudiTime=wudiTimeBorn;
		isDead=false;
//		setGotGoal(false);
		setLife(getLifeMax());
		alpha=1;
		angle=0;
		
		
		Grass footGrass=gList.get(getLandId());
		setPosition(footGrass.data[0]+gra.getGrid()/2, footGrass.data[3]+gethEdge()*1.2f);
		
		
		world.relife();
	}
    public void die() {
    	if(isDead)return;
        jump();
        playSound(death);
        setRgb(1, 0, 0);
        super.die();
    }
   
    
	public void baseDrawElement(GL10 gl){
    	super.baseDrawElement(gl);
    }
   
  
	public void move(){
    	super.move();
    	if(!world.editMode){
    		moveView();
    		landView();
    	}
         setViewPot();
    }
	public void changeSize(float rate){
		super.changeSize(rate);
		if(gun!=null)gun.gunLength=sizeRate*baseGunLength;
	}
    private void setViewPot() {
		// TODO Auto-generated method stub
    	final float edge=64f;
    	float  width=Render.width;
    	float height=Render.height;
    	 float lField = -edge;// 64f
         float rField = width - lField;
         float dField = -edge;
         float uField = height - dField;
         gx1 = px + lField;
         gx2 = px + rField;
         gy1 = py + dField;
         gy2 = py + uField;
         
          lField = -width;// 64f
          rField = 2*width ;
          dField = -height;
          uField = 2*height;
         
         hx1 = px + lField;
         hx2 = px + rField;
         hy1 = py + dField;
         hy2 = py + uField;
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

    public float growSpeed;

//    boolean tread;// ��
	
    public static float jumpRate;
	public static float hx1;
	public static float hy1;
	public static float hx2;
	public static float hy2;

    public void gravityCheck() {
    	super.gravityCheck();
    	
        treadCheck();
    }
    void actCheck(Creature controller) {
        if (downData[0]) {
            downData[1] = false;// ��ֹ�����ж�
            if (doubleClicked)
                controller.changeSpeed(-2);
            else
            	controller. changeSpeed(-1);
            
            controller. turnLeft();
            if(controller!=this)  faceLeft();
            if (controller.getxSpeed() == getxSpeedMax() && isJumpAble()) playSound(brake);
        }
        if (downData[1]) {
            downData[0] = false;// ��ֹ�����ж�
            if (doubleClicked)
            	controller. changeSpeed(2);        	
            else
            	controller. changeSpeed(1);
            
            controller.turnRight();
           if(controller!=this)faceRight();
            
            if (controller.getxSpeed() == getxSpeedMin() && isJumpAble()) playSound(brake);
        }
        if (!downData[0] && !downData[1])
        	controller. stopMove();
        if (downData[2]) {
//            if (controller.isAttackAble()) {
            	controller.attack();
            	attack();
//            }
        } 
        if (downData[3] && controller.isJumpAble())
        	controller.jump(jumpRate);
        
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
       
    }
    private void treadCheck() {
    	  if(treader!=controller) 
          	  controller=this;//can't controll people who has been not  treaded
    	
        if(treadListCheck( enemyList))return;//caizhong le fan hui
        if(treadListCheck( friendList))return;
        
        world.haveTreadIcon(World.NOTREADICON);
     
        
//        if(downData[6])        
    }

    private boolean treadListCheck(ArrayList<Creature>enemyList) {
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
            	 
            	 
            	 
            	 ySpeed+=getG();// remove the g 's effect
            	 c.yStandCheck(this,gethEdge()+c.gethEdge(), 0.2f, 1);

            	 
            	 culxSpeed(c);
                 
                // c carry me so set c's speed as base speed instead of my speed
             
             
                
                if(treader!=c)enemySet.treaded(this, c, 0);// not tread one much time
                treader=c;
                fallen=true;
                 
              world.haveTreadIcon(World.TREADICON);
                 
                return true;
             } 
    	 }
    	
    	 treader = null;
    	 return false;
      
	}
	private void culxSpeed(Creature c) {
//		float rMin=getxSpeedMin()+c.getxSpeed();
//		float rMax=getxSpeedMax()+c.getxSpeed();
		float rs=xSpeed-c.getxSpeed();    //relativeSpeed;
		
		float am=c.getAm();
		
		if(fdirection==0){
//			if(rs>am)
//				xSpeed-=am;
//			else if(rs<-am)
//				xSpeed+=am;
//			else 
				xSpeed=c.getxSpeed(); 
		}else {
			if(fdirection==1){
				if(rs<getxSpeedMax()){
					xSpeed+=am;
				}
			}else if(fdirection==-1){
				if(rs>getxSpeedMin()){
					xSpeed-=am;
				}
			}
		}
	}
	protected void tooRight() {
        super.tooRight();
        
        
//		gList.get(getCollisionId()).setRgb((float) Math.random(),
//				(float) Math.random(), (float) Math.random());
    }

    protected void tooLeft() {
        super.tooLeft();
        
        
//		gList.get(getCollisionId()).setRgb((float) Math.random(),
//				(float) Math.random(), (float) Math.random());
    }

    void destory(int grassId, int x1, int my1) {
//		int tringerCount=Math.abs((int)(getVt()/vmax));//���ܶ�����
        gra.particleCheck(grassId, 5, this);
        getGra().toNull(grassId, x1, my1);
        playSound(destorySound);
    }

    float vtDestory;//�ƻ�����С�ٶ�
    private double E;//�ƻ�����С����
	private int coolingId;
	private Creature treader;
	private float mh1;
	private float mh2;

    protected void tooHigh() {

        int goreId;
        int x1 = (int) (x / getGra().getGrid());
        if ((goreId = getGra().map[x1][getMy1()]) == getGra().getZero()) {
            x1 = getMx1();
            goreId = getTopId();
        }

        if (toukuiTime > 0) destory(goreId, x1, getMy1());//���ƻ� Ҫ��Ȼש��᲻��ʧ
        else {
            getGra().up(goreId, xSpeed,ySpeed);
            goreEnemyCheck();
//            goreCoinCheck();
        }

        super.tooHigh();
//		if(getVt()>-vtDestory)
    }

    private void randomColor() {
        getgList().get(getLandId()).setRgb((float) Math.random(),
                (float) Math.random(), (float) Math.random());
        getgList().get(getTopId()).setRgb((float) Math.random(),
                (float) Math.random(), (float) Math.random());

    }

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

    public void landView() {
        // py2=py;

//		if (getVt() <= 0) {
        float py1 = y - mh1;
        if (py1 < py) {
        	if (py1 < 0) py = 0;
        	else py = py1;
            return;
        }

        py1 = y - mh2;
        if (py1 > py) {
            py = py1;

//		}
            // dpy=py-py2;
        }
    }

    float xMax;
	private float mw1;
	private float mw2;

    public void moveView() {
        // px2=px;
//		if (speed < 0) {
        float px1 = x - mw1;
        if (px1 < px) {
        	// dpx=px-px2;
        	final float left=gra.getViewGrid();
        	 if (px1 < left) {
             	px = left;
             }
        	 else px = px1;
//            return;
        }
        
       
        
//		} else if (speed > 0) {
        float px2 = x - mw2;
        if (px2 > px) {
        	  if (px2 > xMax){
              	px = xMax;
              }
        	  else 	px = px2;
        }
      
//		}
//        px=(float) (x-0.5*Render.width);
    }
    


    public void setMyView(float pxL, float pxR, float pyD, float pyU) {
        mw1 = Render.width * pxL;
        mw2 = Render.width * pxR;
        mh1 = Render.height * pyD;
        mh2 = Render.height * pyU;
    }

    int death;
    private int brake;
    private int destorySound;
	public int bloodSecondaryindex;
	public int secondaryLife=getLifeMax();
	public Gun gun;

	private int EXjump;
	

	public  static int gunFruitId=-1;
	public  static int bladeFruitId=-1;
    private static int toukuiTime;
    private static int gaoTime;
    public static int flyTime;
	
	public boolean touched;

    private void goreEnemyCheck() {
        Creature e;
        for (int i = 0; i < enemyList.size(); i++) {
            e = enemyList.get(i);
            if (e.x + e.getW() > getGra().getTop().data[0]
                    && e.x - e.getW() < getGra().getTop().data[2]
                    && e.y + e.getH() > getGra().getTop().data[1]
                    && e.y - e.getH() < getGra().getTop().data[3]) {
                e.jump((e.getySpeed()/2));
                // e.setVt(e.getVt() + ySpe/20f);
                e.setxSpeed(e.getxSpeed() + getGra().getxSpe());
                e.attacked((int) (10 * (getGra().getxSpe() + getGra().getySpe())));
                // break;//��Сѹ��
            }

        }
    }

    public void loadSound() {
        brake = MusicId.brake01;
        setSoundId(EnemySet.WALKER);
        EXjump=MusicId.land;
        death = MusicId.gameover;
        destorySound = MusicId.wood2;
    }

    public void succeed1() {
//        world.succeed();
    }

    public void increaseScoreBy(int score) {
    	this.score+=score;
        world.increaseScore(score);
    }

    public boolean isPlayerDead() {
        return isDead;
    }

    public void setPlayerDead(boolean playerDead) {
        this.isDead = playerDead;
    }

    public int getToukuiTime() {
        return toukuiTime;
    }

    public void setToukuiTime(int toukuiTime) {
        this.toukuiTime = toukuiTime;
    }

    public int getGaoTime() {
        return gaoTime;
    }

 

	public boolean isGotGoal() {
		return gotGoal;
	}

	public void setGotGoal(boolean gotGoal) {
		this.gotGoal = gotGoal;
	}
	public ArrayList<Creature> getPlayerList() {
		return friendList;
	}
	public void setPlayerList(ArrayList<Creature> playerList) {
		this.friendList = playerList;
	}
	public EnemySet getEnemySet() {
		return enemySet;
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
	public void changeGun(int textureId) {
		if(textureId==-1)return;
		
		if(haveGun(textureId)){
			noGun();
			gunFruitId=-1;
			gun=null;
			return;
		}
		{
			if(textureId== TexId.S)
				gun=new ShotGun(getEnemySet(),  this, 10);
			else if(textureId== TexId.M)// 
				gun=new TailGun(getEnemySet(),  this, 10);
			else if(textureId== TexId.B)
				gun=new BoomGun(getEnemySet(),  this, gra, 5);
			else if(textureId== TexId.D)
				gun=new HookGun(getEnemySet(),  this, 10);
			else if(textureId== TexId.O)
				gun=new Gun(getEnemySet(),  this, 10);
		}
		
		gun.gunLength=sizeRate*baseGunLength;
		haveGun();
		gunFruitId=textureId;
	}
	public boolean haveGun(int textureId) {
		return gunFruitId==textureId;
	}
	public void changeBlade(int textureId) {
		if(textureId==-1)return;
		final int noBladeId=-1;
		// TODO Auto-generated method stub
		if(haveBlade(textureId)) {
			noBlade();
			bladeFruitId=noBladeId;
		} else {
			haveBlade();
			bladeFruitId=textureId;
		}
	}
	public void haveBlade() {
		super.haveBlade();
		world.haveBladeIcon(World.BLADEICON);
	}
	 public void noBlade() {
		super.noBlade();
		world.haveBladeIcon(World.NOBLADEICON);
	}
		void haveGun(){
			super.haveGun();
			world.haveGunIcon(World.GUNICON);
		}
		void noGun(){
			super.noGun();
			world.haveGunIcon(World.NOGUNICON);
		}
	 
	public boolean haveBlade(int textureId) {
		return bladeFruitId==textureId;
	}
	public void increaseCoinBy(int i) {
		// TODO Auto-generated method stub
		world.increaseCoin(i);
	}
	public void increaseChanceBy(int ch){
		world.increaseChance(ch);
	}
	public void addFlyTime(int time) {
		// TODO Auto-generated method stub
		flyTime+=time;
		clothMove.setPosition(x, y);
	}

	
}
