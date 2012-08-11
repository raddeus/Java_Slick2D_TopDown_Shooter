package com.thadb.game.Abilities;

import org.newdawn.slick.Sound;

import Network.AbilityPacket;

import com.thadb.game.Ability;
import com.thadb.game.Entity;
import com.thadb.game.EntityController;
import com.thadb.game.EntityManager;
import com.thadb.game.MainGameLoop;
import com.thadb.game.Util;
import com.thadb.game.Entitys.Bullet;
import com.thadb.game.Enums.AbilitySlot;

public class SingleShot extends Ability{
EntityController parentController;
EntityController bulletController;
float scale;
Entity bullet;
float parentFireX, parentFireY;
	public SingleShot(Entity parent, float scale) {
		super(parent, AbilitySlot.PRIMARY);
		this.scale = scale;
		this.parent = parent;
		this.parentController = parent.getEntityController();
		this.locksMovement = false;
	}

	@Override
	public void fireAbility() {
		game.sendPacket(this.getAbilityPacket());
		bullet = new Bullet(game, game.resource.bullet, this.parent.getX(), this.parent.getY(), this.scale, .7f, 3.0f);
		bulletController = bullet.getEntityController();
		//bulletController.setRotation(Util.getRotationBetweenPoints(parent.getScreenX(), parent.getScreenY(), game.getMouseX(), game.getMouseY()));
		bulletController.setRotation(this.parent.getEntityController().getRotation());
		bulletController.setRotationLocked(true);
		bulletController.setMoving(true);
		bulletController.setMoveSpeed(2);
		EntityManager entityManager = game.getEntityManager();
		entityManager.addEntity(bullet);
		Sound gunshot = game.resource.gunshot;
		float soundPlayX = (parent.getScreenX()-MainGameLoop.getScreenWidth())/160;
		float soundPlayY = (parent.getScreenY()-MainGameLoop.getScreenHeight())/120;
		gunshot.playAt(soundPlayX,soundPlayY, 2);
	}

	@Override
	public void updateSpecific() {

	}
	
	private AbilityPacket getAbilityPacket() {
		AbilityPacket packet = new AbilityPacket();
		packet.setID(parent.getID());
		//packet.setaSlot(this.aSlot);
		return packet;
	}

}
