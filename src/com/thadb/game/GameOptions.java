package com.thadb.game;


public class GameOptions {
	float bombScale = 2.2f;
	float cursorScale = 1f;
	int bulletsPerSecond = 10;
	MainGameLoop game;
	
public GameOptions(MainGameLoop game){
	this.game = game;
}

public float getBombScale() {
	return bombScale;
}

public void setBombScale(float bombScale) {
	this.bombScale = bombScale;
}

public float getCursorScale() {
	return cursorScale;
}

public void setCursorScale(float cursorScale) {
	this.cursorScale = cursorScale;
	game.resetCursor();
}

public int getBulletsPerSecond() {
	return bulletsPerSecond;
}

public void setBulletsPerSecond(int bulletsPerSecond) {
	this.bulletsPerSecond = bulletsPerSecond;
}
	


}
