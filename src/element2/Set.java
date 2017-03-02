package element2;

import Element.Draw;



public class Set  extends Draw {

//	protected int textureIds[];
	private boolean running;	
	private boolean  living;
	public void resume(){		
		if(!isRunning()){
			setRunning(true);
//			if(!isLiving())
//				new Thread(this).start();
		}
	}
	public void run() {
		setLiving(true);
	}
	public boolean isRunning() {
		return running;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}
	public boolean isLiving() {
		return living;
	}
	public void setLiving(boolean living) {
		this.living = living;
	}
}
