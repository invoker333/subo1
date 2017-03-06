package Enviroments;

import java.util.ArrayList;
import java.util.Collections;

import javax.microedition.khronos.opengles.GL10;

import Element.Animation;
import Element.AnimationGrass;
import Element.Draw;
import Element.FireSet;
import Mankind.Baller;
import Mankind.Creature;
import Mankind.Creeper;
import Mankind.Emplacement;
import Mankind.EnemySet;
import Mankind.EpAuto;
import Mankind.FireBall;
import Mankind.Flyer;
import Mankind.Hedgehog;
import Mankind.JointCreature;
import Mankind.Player;
import Mankind.Spide;
import Mankind.TestEnemy;
import Mankind.Walker;
import Mankind.WeaponMan;

import com.mingli.toms.Log;
import com.mingli.toms.MusicId;
import com.mingli.toms.R;
import com.mingli.toms.World;

import element2.LightningSet;
import element2.ParticleSet;
import element2.SceneTail;
import element2.Set;
import element2.Tail;
import element2.TexId;


public class GrassSet extends Set{

	public int [][]map;
	public Grass [][]grassMap;
	private ArrayList<Grass>gList;
	private ArrayList<Grass>drawList;
	private ArrayList<Draw>effectList;
	public ArrayList<Animation>animationList; 
	private ArrayList<Fruit>coinList;
	private ArrayList<Fruit>fruitList;
	private ArrayList<Creature>enemyList;
	private int grassId;//哪些可有跳上去
	private float speedBack=0.2f;//速度恢复系数
	private int mapHeight;//格子高度数
	private float grid;//格子子大小
	private int mapWidth;
//	private float sx;
//	private float sy;

	int index=0;
	private int zero=-2;
	private Goal goal;
//	private Goal goal=new Goal('2', 0,1111);
	public float[] bendDownData;
	public float[] bendUpData;
	private Tail grassTail;
	private float viewGrid;//隐藏格子
	public  boolean isCastle;
	private World world;

	private void newMapData(char[] b) {
		char bi = 0;
		for(int i=0;i<b.length;i++){//新建
			bi=b[i];
			if (bi == 9&&mapHeight==0) {
				mapWidth++;
			} else if (bi == 13) {
				mapHeight++;
			}
		}
		mapWidth+=1;//最后一个换行符 导致tab少一个
		
		isCastle=mapHeight>mapWidth;
		
		map=new int[mapWidth][mapHeight];//


		for(int i=0;i<mapWidth;i++){
			for(int j=0;j<mapHeight;j++){
				map[i][j]=zero;//整体赋值
			}
		}
	}
	//	float[] grassData(int x,int y,int xTime){
//		float a,b;
//		float []data;
//		y=mapHeight-1-y;//0对应15 计数器比索引多一
//		data=new float[]{
//				a=x*grid,
//				b=y*grid,
//				a+grid*(xTime+1),
//				b+grid,
//		};
//		map[x][y]=index++;//
//		map[x+xTime][y]=index;//
//		return data;
//	}
	float[] grassData(int x,int y){
		float a,b;
		float []data;
		y=mapHeight-1-y;//0对应15 计数器比索引多一
		data=new float[]{
				a=x*grid,
				b=y*grid,
				a+grid,
				b+grid,
		};
		map[x][y]=index++;//
		return data;
	}

	public void loadTexture(){
		for(int i=0;i<getgList().size();i++){
			getgList().get(i).loadTexture();
		}

	}
	public byte []saveMap(){
		int  mapWidth = 0,mapHeight = 0;
		
		
		int xOffSet=0,yOffSet=0;// save map x&y 's offset
		int tempOS;
		
		Log.i(""+mapWidth+" "+mapHeight+" "+xOffSet+" "+yOffSet);
		for(Animation a:animationList){
			if((tempOS=(int)(a.startX/grid))<0){
				if (-tempOS>xOffSet)
					xOffSet = -tempOS;
			}else if(mapWidth<tempOS)mapWidth=tempOS;
			
			if((tempOS=(int)(a.startY/grid))<0){
				if (-tempOS>yOffSet)
					yOffSet = -tempOS;
			}else if(mapHeight<tempOS)mapHeight=tempOS;
		}
		Log.i(""+mapWidth+" "+mapHeight+" "+xOffSet+" "+yOffSet);
		
		
//		xOffSet++;yOffSet++;// it can't be lager because when it is less than 0 it can be bigger than it self if u use / caculate
		
		mapWidth+=xOffSet+1;mapHeight+=yOffSet+1;
		byte [][]mapdata=new byte[mapWidth][mapHeight];
		for(int i=0;i<mapWidth;i++){
			for(int j=0;j<mapHeight;j++){
				mapdata[i][j]=' ';// a value let mapsequences's length be constant
			}
		}
		
		
		for(Animation a:animationList){
			mapdata[(int) (a.startX/grid)+xOffSet][(int) (a.startY/grid)+yOffSet]=(byte) a.mapSign;
		}
		
		
		byte[]mapsequence=new byte[mapWidth*2  *mapHeight];
		
		for(int j=0;j<mapHeight;j++){
			for(int i=0;i<mapWidth;i++){
				mapsequence[2*(j*mapWidth+i)]=mapdata[i][mapHeight-1-j];
				mapsequence[2*(j*mapWidth+i)+1]='	';// tab
			}
			mapsequence[2*(j*mapWidth+mapWidth-1)+1]=13;// /r/n
		}
		
		
		return mapsequence;
	}
	public GrassSet(float grid,char[]b,LightningSet lns, World world){
		this.grid=grid;
		this.world = world;
		gList=new ArrayList<Grass>();
		ArrayList<Grass>burrorList=new ArrayList<Grass>();
		coinList=new ArrayList<Fruit>();
		enemyList=new ArrayList<Creature>();
		emplacementList = new ArrayList<Emplacement>();;
		drawList=new ArrayList<Grass>();
		effectList=new ArrayList<Draw>();
		animationList=new ArrayList<Animation>(); 
		fruitList=new ArrayList<Fruit>();
		char bi ;
		int x=0,y=0;//
		float edge=grid/8;
		newMapData(b);
		newBendData();
		for(int i=0;i<b.length;i++){
			bi= b[i];
			if(bi!=9)bendDataCheck(x,y);
			switch(bi){
				case 48:gList.add(new Grass(bi,grassData(x, y),TexId.SOIL));break;//0 一个数字 还有一个符号
				case 102:gList.add(new Fog(bi,grassData(x, y),TexId.FOG,edge, lns));break;//f
				case 103:gList.add(new Grass(bi,grassData(x, y),TexId.SOILGRASS));break;//g
				case 108:gList.add(new Grass(bi,grassData(x, y),TexId.STONEGRASS));break;//l lucus 灌木丛
				case 105:gList.add(new Grass(bi,grassData(x, y),TexId.BANK));break;//i iron 铁
				case '↑':gList.add(new GrassPrick(bi,grassData(x, y),90));break;//p 刺shang
//				8593				8595				8592				8594 shang xcia zou you
//				-111				-109				-112				-110 byte
				case '↓':gList.add(new GrassPrick(bi,grassData(x, y),270));break;//p 刺xia
				case '←':gList.add(new GrassPrick(bi,grassData(x, y),180));break;//p 刺zuo
				case '→':gList.add(new GrassPrick(bi,grassData(x, y),0));break;//p 刺you
				case 115:gList.add(new Grass(bi,grassData(x, y),TexId.STONE));break;//s
				case 116:gList.add(new BigGrass(bi,grassData(x, y),TexId.TREE,edge));break;//t
				case 119:gList.add(new Grass(bi,grassData(x, y),TexId.WOOD));break;//w
				case 122:gList.add(new Grass(bi,grassData(x, y),TexId.ZHUAN));break;//z
				case 85:int ran=(int) (Math.random()*2);
					Burrow bro=new Burrow(bi,grassData(x, y),TexId.BAMBOO,grid*3/32,0,ran);
					Burrow bro2=new Burrow(' ', grassData(x+1, y),TexId.BAMBOO,grid*3/32,1,ran);
					burrorList.add(bro);burrorList.add(bro2);
					gList.add(bro);gList.add(bro2);break;//U 表示地洞 竹子
				case 73:coinList.add(new GoreCoin( (x+0.5f)*grid,(mapHeight-y-0.5f)*grid));
					gList.add(new Grass(bi,grassData(x, y),TexId.BANK));break;//I 带金币的砖
				case 90:
					for(int j=0;j<5;j++) {coinList.add(new GoreCoin( (x+0.5f)*grid,(mapHeight-y-0.5f)*grid));}
					gList.add(new Grass(bi,grassData(x, y),TexId.ZHUAN));break;//Z 带金币的砖
				case 49: coinList.add(new Coin( (x+0.5f)*grid,(mapHeight-y-0.5f)*grid));break;//1
				case 50: goal=new Goal(bi, x*grid,(mapHeight-y-1)*grid);break;//2
				case 51: break;
				case 52:break;
				case 66:fruitList.add(new FruitGun(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid,TexId.B));break;//B boom
				case 67:fruitList.add(new ChanceFruit(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid));break;//C 加机会
				case 68:fruitList.add(new FruitGun(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid,TexId.D));break;//D drag hook
				case 71:fruitList.add(new Gao(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid,9999));break;//G
				case 72:fruitList.add(new sizeFruit(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid));break;//H
				case 75:fruitList.add(new FruitBlade( bi,(x+0.5f)*grid,(mapHeight-y-0.5f)*grid));break;//k
				case 'J':fruitList.add(new FruitFly( bi,(x+0.5f)*grid,(mapHeight-y-0.5f)*grid, 9999));break;// J jumppen qi guo
				case 77:fruitList.add(new FruitGun( bi,(x+0.5f)*grid,(mapHeight-y-0.5f)*grid,TexId.M));break;//M 
				case 79:fruitList.add(new FruitGun(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid,TexId.O));break;//O normal bullet
				case 83:fruitList.add(new FruitGun( bi,(x+0.5f)*grid,(mapHeight-y-0.5f)*grid,TexId.S));break;//S 
				case 84:fruitList.add(new Tomato(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid, 500));break;//T 加血道具
				case 110:fruitList.add(new Toukui(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid,9999));break;//n toukui
				case 'x':fruitList.add(new FruitAuto(bi, (x+0.5f)*grid,(mapHeight-y-0.5f)*grid,9999));break;//
				
				case 65:
					player = new Player(bi, this, world,(x+0.5f)*grid,(mapHeight-y)*grid);
//					setStartPosition(bi,(x+0.5f)*grid,(mapHeight-y)*grid);
					if(World.editMode){
						Creature c=new JointCreature(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid);
						c.name="初始位置";
						c.setLifeMax(999999999);
						c.setLife(999999999);
						enemyList.add(c);
					}
					break;
				case 'Y':
//					enemyList.add(	);
							player=new WeaponMan(bi,this,world,(x+0.5f)*grid,(mapHeight-y)*grid);
					break;
				case 69:
//					emplacementList.add(new Emplacement(this,(x+0.5f)*grid,(mapHeight-y)*grid));
					emplacementList.add(new Emplacement(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid));
					break;//E paotai
				case 70:
					enemyList.add(new FireBall(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid));
					effectList.add(new FireSet(5,(x+0.5f)*grid,grid));
					break;//F
				case 97:enemyList.add(new Walker(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid));break;		//a
				case 98:enemyList.add(new Flyer(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid));break;//b
				case 99:enemyList.add(new Creeper(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid));break;//c
				case 100:enemyList.add(new Baller(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid));break;//d
				case 101:enemyList.add(new Hedgehog(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid));break;//e
				case 106://j
//					float dy=Spide.dsmax;
/*					Creature[] spideMans = new Creature[]{//j
							new Walker(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid),
							new Walker(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid),
							new Walker(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid),
							new Walker(bi,this,(x+0.5f)*grid,(mapHeight-y)*grid),
							};
					for(Creature spideMan:spideMans){
						enemyList.add(spideMan);
						}
					emplacementList.add(new Spide(bi,this,(x+0.5f)*grid,(mapHeight-y-0.5f)*grid,spideMans));*/
					
					emplacementList.add(new Spide(bi,this,(x+0.5f)*grid,(mapHeight-y-0.5f)*grid));
					break;
				case 107:
					enemyList.add(new TestEnemy(this,(x+0.25f)*grid,(mapHeight-y-0.25f)*grid,new Creature[0]));
					enemyList.add(new TestEnemy(this,(x+0.25f)*grid,(mapHeight-y-0.75f)*grid,new Creature[0]));
					enemyList.add(new TestEnemy(this,(x+0.75f)*grid,(mapHeight-y-0.25f)*grid,new Creature[0]));
					enemyList.add(new TestEnemy(this,(x+0.75f)*grid,(mapHeight-y-0.75f)*grid,new Creature[0]));
					break;
				case 53: break;//5 结束标志
				case 13:x=0;y++;break;
				//一个是”\r“一个是“\n”
				case 10:break;
				case 9:x++;break;//tab
				case 0:break;
			}
		}
		enemyList.addAll(emplacementList);
		
		setGrassId(getgList().size());
		gList.add(top);
		
		sort();//添加并调整绘画顺序
		Collections.reverse(burrorList);//反续
		
		drawList.addAll(burrorList);
		
		newBendTail();
		
		loadTexture();
		
		
		loadSound();
		ps=new ParticleSet(this, 10);
		
		animationList.addAll(coinList);
		animationList.addAll(fruitList);
		animationList.addAll(enemyList);
		animationList.addAll(gList);
		animationList.add(goal);
		player.goal=goal;
	}
	public void toStartPosition(){
		for(int i=0;i<animationList.size();i++){
			Animation ani = animationList.get(i);
			ani.toStartPosition();
		}
	}

	private void newBendTail() {
		grassTail=new SceneTail(mapWidth,TexId.WOODROOT);
		grassTail.width=(int) (1*grid);
		for(int i=0;i<mapWidth;i++)
			grassTail.tringer(i*grid,bendUpData[i]);
	}

	private void bendDataCheck(int index,int indexY) {
		if(bendDownData[index]==0)			bendDownData[index]=(mapHeight-indexY+1)*grid;
	}
	private void newBendData() {
		final int h=2;
		bendDownData=new float[mapWidth];
//		bendDownData[0]=0;
		float grid=h*this.grid;
		for(int i=1;i<mapWidth;i++)
//			bData[i]= bData[i-1]+(float) ((Math.random()-0.5)*grid);
			bendDownData[i]=grid+this.grid*(float) Math.sin(i/3f);
		bendUpData=new float[mapWidth];
		bendUpData[0]=0;
		 grid=h*this.grid;
		for(int i=1;i<mapWidth;i++)
//			bData[i]= bData[i-1]+(float) ((Math.random()-0.5)*grid);
			bendUpData[i]=grid+this.grid*(float) Math.sin(i/3f);
	}

//	private void culBamboo1111111() {
//		// TODO Auto-generated method stub
//		Grass upg;
//		int id;
//		for(int i=0;i<mapWidth;i++){
//			upg=null;
//			for(int j=getMapHeight()-1;j>0;j--){
//				if((id=map[i][j])!=zero){
//					Grass cur=gList.get(id);
//					
//					if(upg!=null&&upg.isIsburrow()){
//						if(cur.getxState()==0){
//							cur.data[2]-=grid/2;
//						}
//						else if(cur.getxState()==1){
//							cur.data[0]+=grid/2;
//						}
//					}
//					upg=cur;
//				}else upg=null;
//			}
//		}
//	}
	public void downHoleCheck(float x,float y){
		int x1=(int) (x/grid);
		int y1=(int) (y/grid);
		if(x1<0||x1>=mapWidth||y1<0||y1>=mapHeight)return;
		
		int id;
		if((id=map[x1][y1])==zero)return;
		Grass grass=gList.get(id);
			if(grass.getTextureId()==TexId.BAMBOO)
				grass.setTextureId(TexId.BAMBOOHEART);
			
			
		if(x1+1<mapHeight){
			if((id=map[x1+1][y1])!=zero)
				if(gList.get(id).isIsburrow())
					if(gList.get(id).getxState()==0)x1++;
		}
		if(x1-1>=0){
			if((id=map[x1-1][y1])!=zero)
				if(gList.get(id).isIsburrow())
					if(gList.get(id).getxState()==1)x1--;
		}
		for(;y1>=0;y1--){
			if((id=map[x1][y1])==zero)return;
			grass=gList.get(map[x1][y1]);
			if(grass.getTextureId()==TexId.BAMBOO)
				grass.setTextureId(TexId.BAMBOOHEART);
		}
		
		
		
	}
	
	void sort(){//添加并调整绘画顺序
		int size=gList.size();
		 isCastle = false;
		if(mapHeight>mapWidth)isCastle=true;
		ArrayList<Grass>leafList=new ArrayList<Grass>();
		float xMax = grid*mapWidth;
		for(int i=0;i<size;i++){
			Grass gra=gList.get(i);
			int textureId = gra.getTextureId();
			if (textureId == TexId.FOG||textureId==TexId.TREE) {
				leafList.add(gra);
			}else {
				if(isCastle)drawList.add(gra);
				else if(gra.data[0]!=0&&gra.data[2]!=xMax||gra.data[1]<=grid)
					drawList.add(gra);
			}
		}
		drawList.addAll(leafList);
		if(isCastle)viewGrid=0;
		else viewGrid=grid;
	}
	public void drawElement(GL10 gl) {
		grassTail.drawElement(gl);
		if(gore) {
			topAction();
		}
		goal.drawElement(gl);
		for(int i=0;i<drawList.size();i++){
			Grass g=drawList.get(i);
			if(g.data[0]>=Player.gx1&&g.data[2]<=Player.gx2
					&&g.data[1]>=Player.gy1&&g.data[3]<=Player.gy2)
			{
				if(g.notBroken)
				g.drawElement(gl);
			}
		}
		gl.glColor4f(0.015f, 0.1f, 0.1f, 0.8f);
		for(int i=0;i<drawList.size();i++){
			Grass g=drawList.get(i);
			if(g.data[0]>=Player.gx1&&g.data[2]<=Player.gx2
					&&g.data[1]>=Player.gy1&&g.data[3]<=Player.gy2)
			{
				if(!g.notBroken)
				g.drawElement(gl);
			}
		}
		gl.glColor4f(1,1,1,1);
		
		for(Draw draw :effectList)
			draw.drawElement(gl);
		
		ps.drawElement(gl);

	}
	private void topAction() {
		ySpe-=g;
		xSpe-=a;
		goreHeight+=ySpe;
		top.data[1]+=ySpe;
		top.data[3]+=ySpe;
		top.data[0]+=xSpe;
		top.data[2]+=xSpe;

		if(goreHeight<=0){
			goreHeight=0;ySpe=0;
			gore=false;
			sourceGrass.setTextureId(getTextureId());//
//			top.setTextureId(TexId.BLANK);
			clone(top,sourceGrass);
		}
		top.syncTextureSize();
	}
	public void run1(){
		setLiving(true);
		while(isRunning()){
			topAction();
			World.timeRush();
		}
		setLiving(false);
	}


	private Grass top=new Grass(new float[]{0,0,0,0});
	Grass sourceGrass=new Grass(new float[]{0,0,0,0});
	private boolean gore;//顶
	private float goreHeight;
//	private float ySpeMax;
	float g,a;
	private float xSpe;
	private float ySpe;
	private int topId;
//	private float xMax;// x s max not grid index
	private ArrayList<Emplacement> emplacementList;
	public void loadSound() {
		setSoundId(MusicId.gore);
	}

	@Override
	public void playSound() {
		music.playSound(getSoundId(), 0);
	}
	public void up(int topId,float xSpe,float ySpe) {
		this.topId = topId;
		this.xSpe = xSpe;
		gore=true;			// gore start
		a=xSpe/6f;
		g=ySpe/6f;
		playSound();
		goreHeight=0;// height count =0

		sourceGrass.setTextureId(getTextureId());//还原原id

		sourceGrass= gList.get(topId);//  a new grass
		setTextureId(sourceGrass.getTextureId());// source's texid store in system

		clone(top,sourceGrass);//line1   top= new source grass		

		sourceGrass.setTextureId(TexId.BLANK);//line2  source grass hide on bush

	}

	private void clone(Grass top, Grass grass) {
		top.data=grass.data.clone();
		top.setTextureId(grass.getTextureId());
		top.setRgb(grass.getRed(), grass.getGreen(), grass.getBlue());
	}
	public int getGrassId() {
		return grassId;
	}
	public void setGrassId(int grassId) {
		this.grassId = grassId;
	}
	public float getSpeedBack() {
		return speedBack;
	}
	public void setSpeedBack(float speedBack) {
		this.speedBack = speedBack;
	}
	public void disappear(int i){
//		gCount--;
//		setGrassId(gCount);
//		data[i];
		getgList().remove(i);
	}
	public ArrayList<Grass> getgList() {
		return gList;
	}
	public void setgList(ArrayList<Grass> gList) {
		this.gList = gList;
	}

	public float getGrid() {
		return grid;
	}

	public void setGrid(float grid) {
		this.grid = grid;
	}

	public int getMapWidth() {
		return mapWidth;
	}

	public void setMapWidth(int xLength) {
		this.mapWidth = xLength;
	}
	public int getMapHeight() {
		return mapHeight;
	}
	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}
	public ArrayList<Fruit> getCoinList() {
		return coinList;
	}
	public void setCoinList(ArrayList<Fruit> coinList) {
		this.coinList = coinList;
	}

	public ArrayList<Creature> getEnemyList() {
		return enemyList;
	}

	public void setEnemyList(ArrayList<Creature> enemyList) {
		this.enemyList = enemyList;
	}

	public int getZero() {
		return zero;
	}

//	public float getSx() {
//		return sx;
//	}
//
//	public void setSx(float sx) {
//		this.sx = sx;
//	}
//	private void setStartPosition(char bi,float sx, float sy) {
//		this.sx = sx;
//		this.sy = sy;
//	}
//	public float getSy() {
//		return sy;
//	}
//
//	public void setSy(float sy) {
//		this.sy = sy;
//	}
	public Grass getTop() {
		return top;
	}
	public void setTop(Grass top) {
		this.top = top;
	}
	public float getGoreHeight() {
		return goreHeight;
	}
	public void setGoreHeight(float goreHeight) {
		this.goreHeight = goreHeight;
	}
//	public float getySpeMax() {
//		return ySpeMax;
//	}
//	public void setySpeMax(float speedMax) {
//		this.ySpeMax = speedMax;
//	}
	public Goal getGoal() {
		return goal;
	}
	public void setGoal(Goal goal) {
		this.goal = goal;
	}
	public ArrayList<Fruit> getFruitList() {
		return fruitList;
	}
	public void setFruitList(ArrayList<Fruit> fruitList) {
		this.fruitList = fruitList;
	}
	public boolean isGore() {
		return gore;
	}
	public void setGore(boolean gore) {
		this.gore = gore;
	}

	public float getViewGrid() {
		return viewGrid;
	}
	public void setViewGrid(float viewGrid) {
		this.viewGrid = viewGrid;
	}
	public float getySpe() {
		return ySpe;
	}
	public void setySpe(float ySpe) {
		this.ySpe = ySpe;
	}
	public float getxSpe() {
		return xSpe;
	}
	public void setxSpe(float xSpe) {
		this.xSpe = xSpe;
	}
	public float getRate(int landId) {
		int textureId=gList.get(landId).getTextureId();
		if (textureId == TexId.ZHUAN) {
			return 0.5f;
		}else if(textureId==TexId.STONE) {
			return 0.4f;
		} else if(textureId==TexId.BANK) {
			return 0.3f;
		}
		return 0.6f;
	}
	public void toNull(int grassID, int mx1, int my1) {
		Grass grass=gList.get(grassID);

//		if(drawList.contains(grass))
		grass.notBroken=false;
		map[mx1][my1]=zero;
	}
	public ArrayList<Emplacement> getEmplacementList() {
		return emplacementList;
	}
	public void setEmplacementList(ArrayList<Emplacement> emplacementList) {
		this.emplacementList = emplacementList;
	}
	ParticleSet ps;
	public Player player;
	
	public void particleCheck(int tringerId, int i, Creature player) {
		// TODO Auto-generated method stub
		ps.tringerCheck(tringerId, i, player);
	}
	public Grass getFromXY(int ex, int ey) {
		// TODO Auto-generated method stub
		return gList.get(map[ex][ey]);
	}
}
