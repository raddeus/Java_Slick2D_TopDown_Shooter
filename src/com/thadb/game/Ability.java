package com.thadb.game;

import Network.AbilityPacket;

import com.thadb.game.Enums.AbilitySlot;

public abstract class Ability {

	private float castTime = 1500;
	private float cooldown = 3000;
	private long startedCastTime;
	private long currentTime;
	private long lastSuccessfulCastTime;
	private float percentCompleted;
	private boolean ready = false;
	private EntityController parentController;
	private boolean casting = false;
	private AbilityManager abilityManager;
	float cooldownRemaining;
	
	protected boolean locksMovement = true;
	protected MainGameLoop game;
	protected Entity parent;
	private float cooldownPercentRemaining;
	private AbilitySlot aSlot;
	public Ability(Entity parent, AbilitySlot aSlot) {
		this.parent = parent;
		this.aSlot = aSlot;
		this.abilityManager = parent.getAbilityManager();
		this.parentController = parent.getEntityController();
		this.game = parent.getGame();
	}

	public void start() {
		if (!isCasting() && isReady()) {
			abilityManager.setActiveAbility(this);
			setCasting(true);
			if (locksMovement) {
				parentController.setMovementLocked(true);
			}
		}
	}
//	private AbilityPacket getAbilityPacket() {
//		AbilityPacket packet = new AbilityPacket();
//		packet.setID(game.getMultiPlayerID());
//		//packet.setaSlot(this.aSlot);
//		return packet;
//	}

	public void update() {
		if (isCasting()) {
			updateSpecific();
			currentTime = System.currentTimeMillis();
			long totalTimeCasting = currentTime - startedCastTime;
			this.percentCompleted = totalTimeCasting / getCastTime();
			parentController.setRotationTarget(Util.getRotationBetweenPoints(
					parent.getScreenX(), parent.getScreenY(), game.getMouseX(),
					game.getMouseY()));
			if (totalTimeCasting > this.getCastTime()) {
				this.finish();
			}
		} else {
			updateCooldown();
		}
	}

	public abstract void updateSpecific();

	public void cancel() {
		setCasting(false);
	}
	
	
	private void updateCooldown() {
		if (!isReady()) {
			currentTime = System.currentTimeMillis();
		 cooldownRemaining = (lastSuccessfulCastTime - currentTime)
					+ getCooldown();
		// System.out.println("cooldown % remaining: "+(float)(cooldownRemaining/cooldown));
		 setCooldownPercentRemaining((float)(cooldownRemaining/cooldown));
			if (cooldownRemaining <= 0) {
				this.setReady(true);
			}
		}
	}
	
	public void finish() {
		this.setCasting(false);
		this.lastSuccessfulCastTime = System.currentTimeMillis();
		parentController.setRotating(false);
		abilityManager.setActiveAbility(null);
		this.setReady(false);
		if (locksMovement) {
			parentController.setMovementLocked(false);
		}
		this.fireAbility();
	}
	
	
	private boolean isCasting() {
		return this.casting;
	}

	private void setCasting(boolean b) {
		this.casting = b;
		if (this.casting == true) {
			abilityManager.setActiveAbility(this);
			this.startedCastTime = System.currentTimeMillis();
		}
	}

	private boolean isReady() {
		return this.ready;
	}

	private void setReady(boolean b) {
		this.ready = b;
		if (ready) {
			this.percentCompleted = 0;
		}
	}


	private void setCooldownPercentRemaining(float l) {
		this.cooldownPercentRemaining = l;
	}

	@SuppressWarnings("unused")
	private float getCooldownPercentRemaining(float l) {
		return this.cooldownPercentRemaining;
	}

	public void setParent(Entity entity) {
		this.parent = entity;
		this.abilityManager = parent.getAbilityManager();
		this.parentController = parent.getEntityController();
	}

	float getCooldown() {
		return cooldown;
	}

	public void setCooldown(int cooldown) {
		this.cooldown = cooldown;
	}

	float getCastTime() {
		return castTime;
	}

	public void setCastTime(float castTime) {
		this.castTime = castTime;
	}

	public float getPercentCompleted() {
		return this.percentCompleted;
	}
	
	public abstract void fireAbility();

	public float getCooldownPercentRemaining() {
		return this.cooldownPercentRemaining;
	}

}
