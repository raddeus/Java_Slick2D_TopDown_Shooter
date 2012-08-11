package com.thadb.game.Enums;

public enum MapDirection {

NORTH(360), NORTHEAST(45), EAST(90), SOUTHEAST(135), SOUTH(180), SOUTHWEST(225), WEST(270), NORTHWEST(315);
private int degrees;

MapDirection(int degrees){
	this.degrees = degrees;
}

public int getDegrees() {
	return degrees;
}

public void setDegrees(int degrees) {
	this.degrees = degrees;
}

}
