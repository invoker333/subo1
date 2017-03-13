package Element;

import javax.microedition.khronos.opengles.GL10;

import com.mingli.toms.Music;
import com.mingli.toms.World;

import element2.SoundDraw;

public class Draw	implements SoundDraw {
	private boolean running;	
	private boolean  living;
	private int textureId;
	protected int soundId;
	protected Music music=World.music;
	public void loadTexture() {
		setTexture();
	}
	public void resume(){				
	}
	public void pause(){		
		setRunning(false);
	}
	public void quitgame(){
		setRunning(false);
	}
	public void drawElement(GL10 gl) {
	}
	public boolean isRunning() {
		return running;
	}
	public void setRunning(boolean living) {
		this.running = living;
	}
	@Override
	public void loadSound() {
		// TODO Auto-generated method stub
	}public void loadSound(int id) {
		// TODO Auto-generated method stub
		soundId=id;
	}
	@Override
	public void playSound() {
		music.playSound(soundId, 0);
	}

	@Override
	public void setTexture() {
		// TODO Auto-generated method stub
		
	}
	public boolean isLiving() {
		return living;
	}
	public void setLiving(boolean living) {
		this.living = living;
	}
	public int getSoundId() {
		return soundId;
	}
	public void setSoundId(int soundId) {
		this.soundId = soundId;
	}
	@Override
	public void playSound(int soundId) {
		music.playSound(soundId, 0);
	}
	public int getTextureId() {
		return textureId;
	}
	public void setTextureId(int textureId) {
		this.textureId = textureId;
	}
}
