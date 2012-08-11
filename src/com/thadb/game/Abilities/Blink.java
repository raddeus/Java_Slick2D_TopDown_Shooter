package com.thadb.game.Abilities;

import org.newdawn.slick.Sound;

import com.thadb.game.Ability;
import com.thadb.game.Entity;
import com.thadb.game.EntityController;
import com.thadb.game.Enums.AbilitySlot;

public class Blink extends Ability{
Entity parent;
	public Blink(Entity parent) {
		super(parent, AbilitySlot.ESCAPE);
		this.parent = parent;
	}

	@Override
	public void fireAbility() {
		EntityController parentController = parent.getEntityController();
		parentController.setScaling(true);
		parentController.setScaleTarget(.2f);
		
		
		//scaledown and rotate
		//move forward
										
	//	parent.scaleDown();
		Sound woosh = game.resource.wooshSound;
		woosh.play();
	}

	@Override
	public void updateSpecific() {
//Need to animate here.
		
	}

}
