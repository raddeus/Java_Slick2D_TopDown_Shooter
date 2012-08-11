package com.thadb.game;

import java.util.HashMap;

import com.thadb.game.Enums.AbilitySlot;

public class AbilityManager {
Entity parent;
HashMap<AbilitySlot, Ability> abilityMap = new HashMap<AbilitySlot, Ability>();
Ability activeAbility = null;

	public AbilityManager(Entity parent){
		this.parent = parent;
	}
	
	public void setAbility(Ability a, AbilitySlot slot){
		abilityMap.put(slot, a);
	}
	public Ability getAbility(AbilitySlot slot){
		Ability a = abilityMap.get(slot);
		if(a!=null){
			return a;
		}else{
			return null;
		}
	}

	public void updateAbilities() {
		for (Ability a : abilityMap.values()){
			if (a != null){
				a.update();
			}
		}
		
	}

	public float getActiveAbilityPercent() {
		if (hasActiveAbility()){
			return activeAbility.getPercentCompleted();
		}else{
			return 0;
		}
		
	}

	public boolean hasActiveAbility(){
		return this.activeAbility!=null;
	}
	
	public void setActiveAbility(Ability ability) {
		if(!hasActiveAbility() || ability == null)
		this.activeAbility = ability;
	}

	public void start(Ability a) {
		if(!hasActiveAbility()){
			a.start();
		}
		
	}
}
