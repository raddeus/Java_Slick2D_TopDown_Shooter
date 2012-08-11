package com.thadb.game;

import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import Network.PlayerPacket;

import com.thadb.game.Enums.EntityDirection;
import com.thadb.game.Enums.MapDirection;
import com.thadb.game.Enums.MapLayer;
import com.thadb.game.Enums.SpinDirection;

public abstract class Entity {

	protected float prevX;
	protected float prevY;
	protected float x;
	protected float y;
	protected float scale;
	protected float screenX;
	protected float screenY;

	private boolean delete = false, collided = false;

	protected EntityManager entityManager;
	private int duration = 0;
	private long creationTime = System.currentTimeMillis();
	protected EntityController entityController;
	protected AbilityManager abilityManager;
	protected int ID = 0;
	private Image image;
	protected MainGameLoop game;
	private boolean usesHeight = false;
	private float currentScale;
	private float heightFactor = 6;
	private boolean collidesWithPlayer;

	public Entity(MainGameLoop game, Image image, float x, float y, float scale) {
		this.entityController = new EntityController(this);
		this.game = game;
		this.entityManager = game.getEntityManager();
		this.image = image.getScaledCopy(1f);
		this.x = x;
		this.y = y;
		this.abilityManager = new AbilityManager(this);
		setScale(scale);
		setCurrentScale(scale);
	}
	public float distanceTo(Entity character) {

		float x1 = this.getX();
		float x2 = character.getX();
		float y1 = this.getY();
		float y2 = character.getY();
		
		float dX = (float) Math.pow(x2-x1, 2);
		float dY = (float) Math.pow(y2-y1, 2);
		return (float) Math.sqrt(dX+dY);
}

	public void rotate(SpinDirection spinDir) {
		image.rotate(spinDir.getSpinModifier()
				* entityController.getRotationSpeed() * game.getDelta());
		entityController.setRotation(image.getRotation());
	}

	public void rotate(SpinDirection spinDir, float speed) {
		image.rotate(spinDir.getSpinModifier() * speed * game.getDelta());
		entityController.setRotation(image.getRotation());
	}

	
	private float getHeight(){
		if (this.usesHeight){
		Color colorUnderPlayer = game.map.getColor(MapLayer.HEIGHT, x, y);
				return (float)(colorUnderPlayer.getBlue()/(255f*heightFactor ))+.7f;
		}else{
			return 0;
		}
	}
	

	private void setCurrentScale(float f) {
		this.currentScale = f;
		
	}

	public boolean checkCollision() {
		Color colorUnderPlayer = game.map.getColor(MapLayer.COLLISION, x, y);
		if (colorUnderPlayer.getAlpha() == 0) {
			
//			if(this.collidesWithPlayer){
//				return checkPlayerCollision();
//			}else{
			return false;
//			}
		} else {
			onCollide();
			return true;
		}
	}

	private boolean checkPlayerCollision() {
//		game.getPlayerMap(){
//			
//		}
		return false;
	}
	public void move(MapDirection d) {
		int degrees = d.getDegrees();
		entityController.setRotationTarget(d.getDegrees());
		if (!entityController.isMovementLocked()) {
			float hip = entityController.getMoveSpeed() * game.getDelta()
					* currentScale;
			x += hip * Math.sin(Math.toRadians(degrees));
			y -= hip * Math.cos(Math.toRadians(degrees));
			checkMapBounds();
			if (checkCollision()) {
				x = prevX;
				y = prevY;
			}
		}

	}

	public void move(EntityDirection moveDir) {
		float rotation = this.entityController.getRotation();
		this.move(moveDir, rotation);
	}

	public void move(EntityDirection moveDir, float rotation) {
		int moveModifier = moveDir.getMoveModifier();
		float hip = entityController.getMoveSpeed() * game.getDelta() * currentScale;
		x += moveModifier * hip * Math.sin(Math.toRadians(rotation));
		y -= moveModifier * hip * Math.cos(Math.toRadians(rotation));
		checkMapBounds();
		if (checkCollision()) {
			x = prevX;
			y = prevY;
		}
	}

	private void checkMapBounds() {
		if (x < 0) {
			x = 0;
			entityController.setDelete(true);
			onCollide();
		}
		if (y < 0) {
			y = 0;
			entityController.setDelete(true);
			onCollide();
		}
		if (x > game.map.getWidth()) {
			x = game.map.getWidth();
			entityController.setDelete(true);
			onCollide();
		}
		if (y > game.map.getHeight()) {
			y = game.map.getHeight();
			entityController.setDelete(true);
			onCollide();
		}
	}

	public void turn(float degrees) {
		float wantDir;
		float currDir;
		float directiondiff;
		float maxTurn;

		// want - this is your target direction \\
		wantDir = degrees;
		// max turn - this is the max number of degrees to turn \\
		maxTurn = 2f;
		// current - this is your current direction \\
		currDir = this.entityController.getRotation();

		if (wantDir >= (currDir + 180)) {
			currDir += 360;
		} else {
			if (wantDir < (currDir - 180)) {
				wantDir += 360;
			}
		}
		directiondiff = wantDir - currDir;

		if (directiondiff < -maxTurn) {
			this.rotate(SpinDirection.LEFT);
		}

		if (directiondiff > maxTurn) {
			this.rotate(SpinDirection.RIGHT);
		}

	}

	public void draw() {
		// System.out.println("rotation: "+entityController.getRotation());
		resetImageRotation(image);
		resetImageCenterOfRotation(image);
		drawImage(image);
	}

	private void drawImage(Image image) {
		if(!this.usesHeight()){
			setCurrentScale(scale);
		image.draw(screenX - (image.getWidth() * this.currentScale) / 2, screenY
				- (image.getHeight() * this.currentScale) / 2, this.currentScale);
		}else{
			setCurrentScale(scale*getHeight());
			image.draw(screenX - (image.getWidth() * this.currentScale) / 2, screenY
					- (image.getHeight() * this.currentScale) / 2, this.currentScale);
		}
	}

	private void resetImageRotation(Image image) {
		image.setRotation(this.entityController.getRotation());
	}

	private void resetImageCenterOfRotation(Image image) {
		image.setCenterOfRotation(image.getWidth() * this.currentScale / 2,
				image.getHeight() * this.currentScale / 2);
	}

	public void scaleUp() {
		currentScale += (currentScale >= 20.0f) ? 0 : 0.01f * game.getDelta();
		image.setCenterOfRotation(image.getWidth() / 2.0f * currentScale,
				image.getHeight() / 2.0f * currentScale);
	}

	public void scaleDown() {
		currentScale -= (currentScale <= .1f) ? 0 : 0.01f * game.getDelta();
		image.setCenterOfRotation(image.getWidth() / 2.0f * currentScale,
				image.getHeight() / 2.0f * currentScale);

	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getScreenX() {
		return screenX;
	}

	public void setScreenX(float screenX) {
		this.screenX = screenX;
	}

	public float getScreenY() {
		return screenY;
	}

	public void setScreenY(float screenY) {
		this.screenY = screenY;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
//		this.currentScale = scale;
//		image.setCenterOfRotation(image.getWidth() * this.scale / 2,
//				image.getHeight() * this.scale / 2);
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public MainGameLoop getGame() {
		return game;
	}

	public void setGame(MainGameLoop game) {
		this.game = game;
	}

	public abstract void onCollide();

	public void setDuration(int i) {
		duration = i;

	}

	public int getDuration() {
		return this.duration;
	}

	public void update() {

		abilityManager.updateAbilities();
		screenX = this.x - game.camera.getX();
		screenY = this.y - game.camera.getY();
		prevX = x;
		prevY = y;

		if (duration != 0) {
			long currentTime = System.currentTimeMillis();
			if ((currentTime - creationTime) > duration) {
				entityManager.removeEntity(this.ID);
				this.setDelete(true);
			}
		}

		if (!entityController.isRotationLocked()) {
			this.turn(entityController.getRotationTarget());
		}

		if (entityController.isMoving()) {
			this.move(EntityDirection.FORWARD);
		}
		
		if (entityController.isScaling()){
			this.scale(entityController.getScaleTarget());
		}
		
		updateSpecific();

	}

	protected abstract void updateSpecific();
	
	private void scale(float scaleTarget) {
		if (this.scale > scaleTarget){
		 scaleUp();
		}else if (this.scale < scaleTarget){
			scaleDown();
		}
		
	}

	private boolean usesHeight() {
		return usesHeight;
	}
	
	public void setUsesHeight(boolean b){
		this.usesHeight = b;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public EntityController getEntityController() {
		return this.entityController;
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

	public AbilityManager getAbilityManager() {
		return this.abilityManager;
	}
	public PlayerPacket getPlayerPacket() {
		PlayerPacket p = new PlayerPacket();
		p.setID(game.multiPlayerID);
		p.setX(this.getX());
		p.setY(this.getY());
		p.setRotation(this.getEntityController().getRotation());
		return p;
	}


}
