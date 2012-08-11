package com.thadb.game.Entitys;

import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

import com.thadb.game.Entity;
import com.thadb.game.EntityController;
import com.thadb.game.MainGameLoop;


public class Bullet extends Entity{
float scale, volume, pitch;

	public Bullet(MainGameLoop game, Image image, float x, float y,
			float scale, float volume, float pitch) {
		super(game, image, x, y, scale);
		this.scale = scale;
		this.volume = volume;
		this.pitch = pitch;
		
		
	}

	@Override
	public void onCollide() {
		this.entityManager.removeEntity(this.ID);
		Entity explosion = new Explosion(this.game, game.resource.explosion, this.x,
				this.y, this.scale*1.5f);
		EntityController explosionController = explosion.getEntityController();
		explosionController.setRotation(getEntityController().getRotation());
		explosionController.setRotationLocked(true);
		explosionController.setMoving(false);
		explosion.setDuration((int) (500*this.scale));
		
		Sound explosionSound = game.resource.explosionSound;
		float soundPlayX = (this.screenX-MainGameLoop.getScreenWidth())/160;
		float soundPlayY = (this.screenY-MainGameLoop.getScreenHeight())/120;
		
	//explosionSound.playAt(soundPlayX/80, soundPlayY/60, 2);
		explosionSound.playAt(this.pitch, this.volume, soundPlayX, soundPlayY, 2);
		entityManager.addEntity(explosion);
	}

	@Override
	protected void updateSpecific() {
		// TODO Auto-generated method stub
		
	}

}
