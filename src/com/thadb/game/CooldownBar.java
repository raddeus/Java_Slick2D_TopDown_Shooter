package com.thadb.game;

import org.newdawn.slick.Image;

public class CooldownBar {
	int x,y,height,width;
	Image foreground;
	Image background;
	MainGameLoop game;
	float percent=.5f;
	Ability parentAbility;
	
	public CooldownBar(MainGameLoop game, Ability parentAbility, int x, int y, int width, int height){
		this.parentAbility = parentAbility;
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
		
		this.percent = parentAbility.getCooldownPercentRemaining();

	}
	public void draw(){
		
		background.draw(x, y, width, height);
		if(percent >0){
		foreground.draw(x+5, y+5, (width-10)*this.percent, height-10);
		}
	}
}
