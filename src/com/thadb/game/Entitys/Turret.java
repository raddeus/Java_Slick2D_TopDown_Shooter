package com.thadb.game.Entitys;

import org.newdawn.slick.Image;

import com.thadb.game.Entity;
import com.thadb.game.MainGameLoop;

public class Turret extends Entity{

	public Turret(MainGameLoop game, Image image, float x, float y, float scale) {
		super(game, image, x, y, scale);
		//Image should be in the constructor of each entity class.
		
		this.entityController.setMoving(false);
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
