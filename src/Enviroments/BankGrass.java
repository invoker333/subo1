package Enviroments;

import element2.TexId;

public class BankGrass extends Grass {

	public BankGrass(char mapSign, float[] data, int texId) {
		super(mapSign, data, texId);
		// TODO Auto-generated constructor stub
	}
	public void gored(){
		setTextureId(TexId.BANK);
	}

}
