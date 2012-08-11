package com.thadb.game.Abilities;

import org.newdawn.slick.Sound;

import com.thadb.game.Ability;
import com.thadb.game.Entity;
import com.thadb.game.EntityController;
import com.thadb.game.EntityManager;
import com.thadb.game.MainGameLoop;
import com.thadb.game.Util;
import com.thadb.game.Entitys.Bullet;
import com.thadb.game.Enums.AbilitySlot;

public class BigShot extends Ability{
Entity parent;
EntityController parentController;
float scale;
	public BigShot(Entity parent, float scale) {
		super(parent, AbilitySlot.SECONDARY);
		this.scale = scale;
		this.parent = parent;
		this.parentController = parent.getEntityController();
		this.locksMovement = true;
	}

	@Override
	public void fireAbility() {
		Entity bullet = new Bullet(game, game.resource.bullet, game.character.getX(), game.character.getY(), game.options.getBombScale()*this.scale, 3.0f, 1.0f);
		EntityController bulletController = bullet.getEntityController();
		bulletController.setMoving(true);
		bulletController.setMoveSpeed(.1f);
		bulletController.setRotationLocked(true);
		EntityManager entityManager = game.getEntityManager();
		entityManager.addEntity(bullet);
		
		bulletController.setRotation(Util.getRotationBetweenPoints(parent.getScreenX(), parent.getScreenY(), game.getMouseX(), game.getMouseY()));
		
		Sound gunshot = game.resource.missileLaunch;
		float soundPlayX = (parent.getScreenX()-MainGameLoop.getScreenWidth())/160;
		float soundPlayY = (parent.getScreenY()-MainGameLoop.getScreenHeight())/120;
		

		gunshot.playAt(soundPlayX,soundPlayY, 2);
		
//    	}
		
	}

	@Override
	public void updateSpecific() {
		// TODO Auto-generated method stub
		
	}

}
