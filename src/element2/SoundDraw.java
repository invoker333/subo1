package element2;

import javax.microedition.khronos.opengles.GL10;

public interface SoundDraw {
	public void loadSound();
	public void playSound();
	public void playSound(int soundId);
	public void setTexture();
	public void drawElement(GL10 gl);
}
