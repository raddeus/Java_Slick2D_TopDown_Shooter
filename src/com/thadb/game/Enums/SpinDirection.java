package com.thadb.game.Enums;

public enum SpinDirection {

	
	LEFT(-1), RIGHT(1), NONE(0);
	private final int spinModifier;
	
	SpinDirection(int spin) {
this.spinModifier = spin;
	}
	

	
	public int getSpinModifier(){
		return spinModifier;
	}

}
