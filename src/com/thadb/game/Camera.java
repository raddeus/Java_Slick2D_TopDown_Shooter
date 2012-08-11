package com.thadb.game;

public class Camera {
	private float x, y, centerX, centerY; //center of the camera map points.
	private int width, height;
	private MainGameLoop game;
	private Entity targetEntity;
	private float moveSpeed =1;
	private boolean centeredOnEntity = false;
	private boolean cameraMovesWithMouse = false;
	
public Camera(MainGameLoop game, float x, float y){
	this.game = game;
	this.x = x;
	this.y = y;
	this.width = MainGameLoop.getScreenWidth();
	this.height = MainGameLoop.getScreenHeight();
	this.centerX = (this.width/2)+this.x;
	this.centerY = (this.height/2)+this.y;
}
public void update(){
	if (this.centeredOnEntity && !this.cameraMovesWithMouse){
		centerCameraOnEntity(targetEntity);
	}else if(this.centeredOnEntity && this.cameraMovesWithMouse){
		int mouseX = game.getMouseX();
		int mouseY = game.getMouseY();
		int centerX= MainGameLoop.getScreenWidth()/2;
		int centerY = MainGameLoop.getScreenHeight()/2;
		float dX = mouseX - centerX, dY = mouseY - centerY; //distance from center of screen to mouse;
		centerCameraOnEntity(targetEntity, dX/1f, dY/.75f);

	}
}

public void centerCameraOnEntity(Entity entity){
	setCenterX(entity.getX());
	setCenterY(entity.getY());
	checkMapBounds();
}

public void centerCameraOnEntity(Entity entity, float offsetX, float offsetY){
	setCenterX(entity.getX()+offsetX);
	setCenterY(entity.getY()+offsetY);
	checkMapBounds();
}

private void checkMapBounds() {
	if (x < 0) {
	x = 0;
	}
	if (y < 0) {
		y=0;
	}
	if (x > game.map.getWidth()-this.getWidth()) {
		x= game.map.getWidth()-this.getWidth();
	}
	if (y > game.map.getHeight()-this.getHeight()) {
		y=game.map.getHeight()-this.getHeight();
	}
	
}

public Entity getTargetEntity() {
	return targetEntity;
}
public void setTargetEntity(Entity targetEntity) {
	this.targetEntity = targetEntity;
}
public boolean isCenteredOnEntity() {
	return centeredOnEntity;
}
public void setCenteredOnEntity(boolean centeredOnEntity) {
	this.centeredOnEntity = centeredOnEntity;
}

public float getX() {
	return x;
}

public void setX(float x) {
	this.x = x;
	this.centerX = x+(getWidth()/2);
}

public float getY() {
	return y;
}

public void setY(float y) {
	this.y = y;
	this.centerX = y+(getHeight()/2);
}

public float getCenterX() {
	return centerX;
}

public void setCenterX(float centerX) {
	this.centerX = centerX;
	this.x = centerX-(getWidth()/2);
}


public boolean isCameraMovesWithMouse() {
	return cameraMovesWithMouse;
}
public void setCameraMovesWithMouse(boolean cameraMovesWithMouse) {
	this.cameraMovesWithMouse = cameraMovesWithMouse;
}
public float getMoveSpeed() {
	return moveSpeed;
}
public void setMoveSpeed(float moveSpeed) {
	this.moveSpeed = moveSpeed;
}
public int getWidth() {
	return this.width;
}

public int getHeight(){
	return this.height;
}

public float getCenterY() {
	return centerY;
}

public void setCenterY(float centerY) {
	this.centerY = centerY;
	this.y = centerY-(getHeight()/2);
}



}
