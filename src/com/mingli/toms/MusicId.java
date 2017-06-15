package com.mingli.toms;


public class MusicId {
	public static int baller;
	public static int guangdanqiang;
	public static int sword;
	public static int brake01;
	public static int land;
	public static int gameover;
	public static int wood2;
	public static int firecolumn;
	public static int walker;
	public static int emplacementAttack;
	public static int flyer;
	public static int hedgehog;
	public static int creeper4;
	public static int zhizhu;
	public static int coin;
	public static int magic;
	public static int finalcoin;
	public static int gore;
	public static int bomb;
	public static int shotGun;
	public static int boomgun;
	public static int juji;
	public static int lightNing;
	public static int bubble;
	public static int missile;
	public static int light;
	public static int fresh;

	public static void loadSoundId(Music music) {
		// TODO Auto-generated method stub
		fresh=music.loadSound(R.raw.fresh);
		light=music.loadSound(R.raw.fog);
		bubble=music.loadSound(R.raw.bubble);
		lightNing=music.loadSound(R.raw.lightning);
		baller=music.loadSound(R.raw.baller);
		guangdanqiang=music.loadSound(R.raw.gunlight);
		sword=music.loadSound(R.raw.sword);
		brake01=music.loadSound(R.raw.brake01);
		land=music.loadSound(R.raw.jumpsoft);
		gameover=music.loadSound(R.raw.gameover);
		wood2=music.loadSound(R.raw.wood2);
		firecolumn=music.loadSound(R.raw.firecolumn);
		walker=music.loadSound(R.raw.walker);
		emplacementAttack=music.loadSound(R.raw.emplacement);
		flyer=music.loadSound(R.raw.bird);
		hedgehog=music.loadSound(R.raw.hedgehog);
		creeper4=music.loadSound(R.raw.crepper6);
		zhizhu=music.loadSound(R.raw.zhizhu);
		coin=music.loadSound(R.raw.coin);
		magic=music.loadSound(R.raw.magic);
		finalcoin=music.loadSound(R.raw.finalcoin);
		gore=music.loadSound(R.raw.gore);
		bomb=music.loadSound(R.raw.bomb2);
		shotGun=music.loadSound(R.raw.shotgun);
		boomgun=music.loadSound(R.raw.boomshot);
		juji=music.loadSound(R.raw.juji3);
		missile=music.loadSound(R.raw.missile);
	}
	
}
