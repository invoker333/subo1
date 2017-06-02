package Element;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import Enviroments.Grass;
import Enviroments.GrassSet;

public class AnimationGrass extends AnimationMove {

	float xdata[];
	private float[] ydata;
	protected GrassSet gra;
	private int landId;// 降落的踏板的id号
	private int topId;
	private int collisionId;
	// private float xs;
	private float xEdgeNew;
	protected float xPro;
	protected float yPro;
	protected ArrayList<Grass> gList;

	float edge, edge2;
	private float wRate = 0.9f;
	private float hRate = 62 / 64f;
	protected float sin;
	protected float cos = 1;

	public void setPosition(float x, float y) {
		setxPro(x);
		setyPro(y);
		super.setPosition(x, y);
	}
	public void toStartPosition(){
		x=xPro=startX;
		y=yPro=startY;
		xSpeed=0;
		ySpeed=0;
	}
	protected AnimationGrass(GrassSet gra) {
		this.gra = gra;
		gList = gra.getgList();
	}

	public void sizeCheck() {
		wEdge = (int) (getW() * wRate);// 左边身体宽度
		sethEdge((int) (getH() * hRate));// 60/64f=15/16
		aniStepCheck();
	}

	protected void aniStepCheck() {
		// aniStep =agoMax[3]/speedSizeMax/speedMax;//
		// aniStep2[0] =aniStep;
	}

	public void gravity() {
		setyPro(yPro + getySpeed());
		gravityCheck();
		y = yPro;
	}

	protected void gravityCheck() {
		fallen = false;
		mapyCheck();
		
		bendDownCheck();
		//  shun xu bu neng huan  xia mian yao yong shang mian can shu
		bendUpCheck();
	}

	public void move() { // 在没有撞墙的情况下，一直处于移动状态（静止即移速为零）
		setxPro(xPro + xSpeed);
		moveCheck();
		x = xPro;
	}

	protected void moveCheck() {
		mapxCheck();
	}

	private int mx1;
	private int my1;
	int mx2;
	int my2;
	int xCheckId, yCheckId;
	private float xEdgeAgo;

	protected void mapyCheck() {
		edge = xPro - wEdge;
		edge2 = xPro + wEdge;
		if (getySpeed() > 0) {
			if ((mx1 = (int) (edge / gra.getGrid())) >= 0
					&& (mx2 = (int) (edge2 / gra.getGrid())) < gra.getMapWidth()) {
				if ((my1 = (int) ((yPro + gethEdge()) / gra.getGrid())) < gra
						.getMapHeight() && my1 >= 0)
					while (mx1 <= mx2) {
						if (gra.map[mx1][my1] != gra.getZero()) {
							if(upCheck(gra.map[mx1][my1]))
							break;
						}
						mx1++;
					}
			}
		} else if (getySpeed() < 0) {
			if ((mx1 = (int) (edge / gra.getGrid())) >= 0
					&& (mx2 = (int) (edge2 / gra.getGrid())) < gra.getMapWidth()) {
				if ((my1 = (int) ((yPro - gethEdge()) / gra.getGrid())) < gra
						.getMapHeight() && my1 >= 0)
					while (mx1 <= mx2) {
						if (gra.map[mx1][my1] != gra.getZero()) {
//							downCheck(mx1,my1);
							if(downCheck(gra.map[mx1][my1])){
							break;
							}
						}
						mx1++;
					}
			}
		}
	}

	protected void downCheck1(int mx1, int my1) {
		// TODO Auto-generated method stub
		downCheck(gra.map[mx1][my1]);
	}

	protected void mapxCheck() {
		edge = y - gethEdge();
		edge2 = y + gethEdge();
		if (getxSpeed() < 0) {
			if ((my1 = (int) (edge / gra.getGrid())) >= 0
					&& (my2 = (int) (edge2 / gra.getGrid())) < gra
							.getMapHeight()) {
				if ((mx1 = (int) ((xPro - wEdge) / gra.getGrid())) < gra
						.getMapWidth() && mx1 >= 0)// 不能越界
					while (my1 <= my2) {
						if (gra.map[mx1][my1] != gra.getZero()) {
							if(leftCheck(gra.map[mx1][my1]))
							break;
						}
						my1++;
					}
			}
		} else if (getxSpeed() > 0) {
			if ((my1 = (int) (edge / gra.getGrid())) >= 0
					&& (my2 = (int) (edge2 / gra.getGrid())) < gra
							.getMapHeight()) {
				if ((mx1 = (int) ((xPro + wEdge) / gra.getGrid())) < gra
						.getMapWidth() && mx1 >= 0) {
					while (my1 <= my2) {
						if (gra.map[mx1][my1] != gra.getZero()) {
							if(rightCheck(gra.map[mx1][my1]))
							break;
						}
						my1++;
					}
				}
			}

		}
	}

	private boolean leftCheck(int i) {
		xdata = gList.get(i).data;// 第i组数据
		xEdgeAgo=x-wEdge;
		xEdgeNew = xPro - wEdge;// 人物左侧
		if (edge2 > xdata[1] && edge < xdata[3])// 相交
			if (xEdgeAgo >= xdata[2] && xEdgeNew < xdata[2])// 接触踏板 到陷入踏板深度
			{
				if (gList.get(i).tooLeft(this)) {
					setCollisionId(i);
					tooLeft();
					return true;
				}
			}
		return false;
	}

	float climbBuffer = 5;
	private float yEdgeNew;
	private float yEdgeAgo;

	private boolean rightCheck(int i) {
		xdata = gList.get(i).data;// 第i组数据
		xEdgeAgo=x+wEdge;
		xEdgeNew = xPro + wEdge;// 右侧比左边少 但是判断是一样的
		if (edge2 > xdata[1] && edge < xdata[3])// 相交
			if (xEdgeNew > xdata[0] && xEdgeAgo <= xdata[0])// 接触踏板 到陷入踏板深度
			{
				if (gList.get(i).tooRight(this)) {
					setCollisionId(i);
					tooRight();
					return true;
				}
			}
		return false;
	}

	protected void tooLeft() {
		autoClimb();
		scaleTringer(2*getW()-maxScaleLengthX);

		setxPro(xdata[2] + wEdge);
		speedBackTooLeft();
	}

	 protected void speedBackTooLeft() {
		// TODO Auto-generated method stub
		if (getxSpeed() < -backSpeed)
			setxSpeed(gra.getSpeedBack() * Math.abs(getxSpeed()));// 没有这句就特别卡
		else
			setxSpeed(0);
//			setxSpeed(-1);
		// 会引起一直跳
//			setxSpeed(-1 * backSpeed);
	}
	protected void autoClimb() {
	}
	private float backSpeed=5;

	protected void tooRight() {
		autoClimb();
		scaleTringer(2*getW()-maxScaleLengthX);
		
		setxPro(xdata[0] - wEdge);// 牵扯到向下取整
		speedBackTooRight();
	}

	 protected void speedBackTooRight() {
		// TODO Auto-generated method stub
		if (getxSpeed() > backSpeed)
			setxSpeed(gra.getSpeedBack() * -Math.abs(getxSpeed()));
		else
//			setxSpeed(1);
			setxSpeed(0);
//			setxSpeed(1 * backSpeed);

	}
	protected void jumpCheck() {
		for (int i = 0; i < gra.getGrassId(); i++) {
			if (x + wEdge > gList.get(i).data[0]
					&& x - wEdge < gList.get(i).data[2]) {
				upCheck(i);
			}
		}
	}

	protected boolean upCheck(int i) {
		ydata = gList.get(i).data;// 第i组数据
		yEdgeAgo=y+gethEdge();
		yEdgeNew = yPro + gethEdge();// 头顶
		if (edge2 > ydata[0] && edge < ydata[2])// 相交
			if (yEdgeNew > ydata[1] && yEdgeAgo <= ydata[1])// 接触踏板 到陷入踏板深度
			{

				if (gList.get(i).tooHigh(this)) {
					setTopId(i);
					tooHigh();// 速度不等于零正常检测
					return true;
				}
			}
		return false;
	}

	protected void tooHigh() {
		scaleTringer(getW());
		setyPro(ydata[1] - gethEdge());
		setySpeed((gra.getSpeedBack() * -getySpeed()));
	}


	

	protected void landCheck() {
		for (int i = 0; i < gList.size(); i++) {
			if (x + wEdge > gList.get(i).data[0]
					&& x - wEdge < gList.get(i).data[2]) {// 相交
				downCheck(i);
			}
		}
	}

	protected boolean downCheck(int i) {
		Grass grass;
		ydata = (grass=gList.get(i)).data;// 第i组数据
		yEdgeAgo = y-gethEdge();
		yEdgeNew = yPro - gethEdge();// 脚底
		if (edge2 > ydata[0] && edge < ydata[2])// 相交
			if (yEdgeNew < ydata[3] && yEdgeAgo >= ydata[3] )// 接触踏板
			{// 人的腰部<=踏板+腰部高度
				if(grass.tooDown(this)){
					
					setLandId(i);
					tooDown();
					return true;
				}
				// playSound();
			}
		return false;
	}

	protected void tooDown() {
		scaleTringer(getW());
		setyPro(ydata[3] +gethEdge());
		fallen = true;
		
	}

	

	protected boolean fallen;// 接触墙面（包括地面）
	
	protected void bendUpCheck() {
		float bData[] = gra.bendUpData;
		
		int xSmall = (int) (x / gra.getGrid());// grid x y
		if (xSmall < 0 || xSmall >= bData.length - 1)
			return;
		float y1 = bData[xSmall];
		float y2 = bData[xSmall + 1];
		float dy = y2 - y1;
		float x=this.x-1;if(x<0)x=0;
		float dx = x % gra.getGrid();
		
//			stand(dy, );
//			setySpeed((gra.getSpeedBack() * -getySpeed()));

	}
	protected void bendDownCheck() {
		float bData[] = gra.bendDownData;
		int xSmall = (int) (x / gra.getGrid());// grid x y
		if (xSmall < 0 || xSmall >= bData.length - 1)
			return;
		float y1 = bData[xSmall];
		float y2 = bData[xSmall + 1];
		float dy = y2 - y1;
		{// 坡度可能改变
			double length = Math.sqrt(dy * dy + Math.pow(gra.getGrid(), 2));
			sin = (float) (dy / length);
			cos = (float) (gra.getGrid() / length);
		}
		
		float x=this.x-1;if(x<0)x=0;
		float dx = x % gra.getGrid();
//		if(){
//			stand());
//			fallen = true;
//		}
		
	}

	private void stand(float yPro) 
		{// 人的腰部<=踏板+腰部高度
			setyPro(yPro);
			fallInBend();
		
	}

	protected void fallInBend() {
		float xSpeTemp = xSpeed * cos;
		float ySpeTemp = ySpeed * sin;
		xSpeed = ySpeTemp * cos + xSpeTemp * cos;
		ySpeed = xSpeTemp * sin + ySpeTemp * sin;
	}


	public void drawElement(GL10 gl) {
		move();
		gravity();
		setAngle(getAngle() + rotateSpeed);
		gl.glTranslatef(x, y, 0);
		gl.glRotatef(getAngle(), 0, 0, 1);
		baseDrawElement(gl);
		gl.glRotatef(-getAngle(), 0, 0, 1);
		gl.glTranslatef(-x, -y, 0);
		// gl.glLoadIdentity();

	}
	


	public int getLandId() {
		return landId;
	}

	public void setLandId(int landId) {
		this.landId = landId;
	}

	public ArrayList<Grass> getgList() {
		return gList;
	}

	public void setgList(ArrayList<Grass> gList) {
		this.gList = gList;
	}

	public float getyPro() {
		return yPro;
	}

	public void setyPro(float yPro) {
		this.yPro = yPro;
	}

	public float getxPro() {
		return xPro;
	}

	public void setxPro(float xPro) {
		this.xPro = xPro;
	}

	public int getCollisionId() {
		return collisionId;
	}

	public void setCollisionId(int collisionId) {
		this.collisionId = collisionId;
	}


	public float getwRate() {
		return wRate;
	}

	public void setwRate(float wRate) {
		this.wRate = wRate;
	}

	public int getTopId() {
		return topId;
	}

	public void setTopId(int topId) {
		this.topId = topId;
	}



	public float gethRate() {
		return hRate;
	}

	public void sethRate(float hRate) {
		this.hRate = hRate;
	}


	public void sethEdge(int hEdge) {
		this.hEdge = hEdge;
	}

	public int getMx1() {
		return mx1;
	}

	public void setMx1(int mx1) {
		this.mx1 = mx1;
	}

	public int getMy1() {
		return my1;
	}

	public void setMy1(int my1) {
		this.my1 = my1;
	}

	public void attacked(int i) {
		// return false;
		// TODO Auto-generated method stub

	}

	
}
