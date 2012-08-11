package com.thadb.game.Entitys;

import org.newdawn.slick.Image;

import com.thadb.game.Entity;
import com.thadb.game.MainGameLoop;
import com.thadb.game.Util;
import com.thadb.game.Enums.EntityDirection;

public class Target extends Entity {

	MainGameLoop game;
	
	public Target(MainGameLoop game, Image image, float x, float y, float scale) {
		super(game, image, x, y, scale);
		this.game = game;
		if(this.checkCollision()){
			this.setDelete(true);
		}
		// TODO Auto-generated constructor stub
	}

public void update(){

	//abilityManager.updateAbilities();
	screenX = this.x - game.camera.getX();
	screenY = this.y - game.camera.getY();
	prevX = x;
	prevY = y;
	this.entityController.setRotation(Util.getRotationBetweenEntities(this, game.character));
	if (this.distanceTo(game.character) > 500){
		this.entityController.setMoveSpeed(.2f);
	this.move(EntityDirection.FORWARD);
	}else if (this.distanceTo(game.character) < 1000){
		this.entityController.setMoveSpeed(1f);
		this.move(EntityDirection.BACKWARD);
	}

	
}


	@Override
	public void onCollide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void updateSpecific() {
		// TODO Auto-generated method stub
		
	}
	
	

}
