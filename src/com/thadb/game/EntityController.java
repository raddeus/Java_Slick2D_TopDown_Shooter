package com.thadb.game;

public class EntityController {

	private boolean rotating=false, moving=false, movementLocked=false, rotationLocked=false;
	private float rotationSpeed = .05f;
	private float rotationTarget;
	private float rotation;
	private float moveSpeed = .4f;
	private boolean delete = false;
private boolean collided = false;
@SuppressWarnings("unused")
private Entity parentEntity;
private float scaleTarget;
//Flag to see if the entity needs to change scale;
private boolean scaling=false;

	
	public EntityController(Entity entity){
		this.parentEntity = entity;
	}

	public boolean isRotating() {
		return this.rotating;
	}
	public void setRotating(boolean rotating) {
		this.rotating = rotating;
	}
	public boolean isMoving() {
		return moving;
	}
	public void setMoving(boolean moving) {
		this.moving = moving;
	}
	public boolean isMovementLocked() {
		return movementLocked;
	}
	public void setMovementLocked(boolean movementLocked) {
		this.movementLocked = movementLocked;
	}
	public boolean isRotationLocked() {
		return rotationLocked;
	}
	public void setRotationLocked(boolean rotationLocked) {
		this.rotationLocked = rotationLocked;
	}
	public float getRotationSpeed() {
		return rotationSpeed;
	}
	public void setRotationSpeed(float rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}
	public float getRotationTarget() {
		return this.rotationTarget;
	}
	public void setRotationTarget(float rotationTarget) {
		this.rotationTarget = rotationTarget;
	}
	public float getRotation() {
		return rotation;
	}
	public void setScaleTarget(float scaleTarget){
		this.scaleTarget = scaleTarget;
	}
	public float getScaleTarget() {
		return scaleTarget;
	}

	public void setRotation(float rotation) {
		this.rotation = rotation;
		parentEntity.getImage().setRotation(rotation);
	}
	public float getMoveSpeed() {
		return moveSpeed;
	}
	public void setMoveSpeed(float moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
	public boolean isDelete() {
		return delete;
	}
	public void setDelete(boolean delete) {
		this.delete = delete;
	}
	public boolean isCollided() {
		return collided;
	}
	public void setCollided(boolean collided) {
		this.collided = collided;
	}

	public boolean isScaling() {
		// TODO Auto-generated method stub
		return this.scaling;
	}

	public void setScaling(boolean scaling) {
		this.scaling = scaling;
	}
	
	
}
