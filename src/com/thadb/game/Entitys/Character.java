package com.thadb.game.Entitys;

import org.newdawn.slick.Image;

import com.thadb.game.Ability;
import com.thadb.game.Entity;
import com.thadb.game.MainGameLoop;
import com.thadb.game.Abilities.BigShot;
import com.thadb.game.Abilities.SingleShot;
import com.thadb.game.Enums.AbilitySlot;

public class Character extends Entity {
	Ability secondary;
	Ability primary;
	
	public Character(MainGameLoop game, Image image, float x, float y,
			float scale) {
		super(game, image, x, y, scale);
		primary = new SingleShot(this, 1.8f);
		secondary = new BigShot(this, 3f);
		secondary.setCooldown(2500);
		secondary.setCastTime(1000);
		primary.setCastTime(200);
		primary.setCooldown(50);
		
		this.abilityManager.setAbility(primary, AbilitySlot.PRIMARY);
		this.abilityManager.setAbility(secondary, AbilitySlot.SECONDARY);
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
