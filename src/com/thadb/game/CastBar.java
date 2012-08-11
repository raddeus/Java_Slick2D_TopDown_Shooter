package com.thadb.game;

import org.newdawn.slick.Image;

public class CastBar {
	int x,y,height,width;
	Image foreground;
	Image background;
	MainGameLoop game;
	Entity caster;
	AbilityManager casterAbilityManager;
	float percent=.5f;
	public CastBar(Entity caster, MainGameLoop game, int x, int y, int width, int height){
		this.caster = caster;
		 this.casterAbilityManager = caster.getAbilityManager();
		this.game = game;
		this.x = x;
		this.y=y;
		this.height = height;
		this.width = width;
		foreground = game.resource.castForeground;
		background = game.resource.castBackground;
		
	}
	
	public void setPercent(float percent){
		this.percent = percent;
	}
	
	public void update(){
		
		this.percent = casterAbilityManager.getActiveAbilityPercent();

	}
	public void draw(){
		background.draw(x, y, width, height);
		foreground.draw(x+5, y+5, (width-10)*this.percent, height-10);
	}
}
