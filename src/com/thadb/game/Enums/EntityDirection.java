package com.thadb.game.Enums;

public enum EntityDirection {
	
	FORWARD(1), BACKWARD(-1);
	private final int moveModifier;
	
	EntityDirection(int modifier) {
this.moveModifier = modifier;
	}
	

	
	public int getMoveModifier(){
		return this.moveModifier;
	}

}
