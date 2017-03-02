package Element;

public class AnimationGravity extends AnimationMove{
	float g=1;
	public void gravity(){
		y += getySpeed();
		setySpeed(getySpeed()-g);
	}
}
