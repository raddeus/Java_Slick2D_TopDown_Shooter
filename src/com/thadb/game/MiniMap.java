package com.thadb.game;
import org.newdawn.slick.Image;


public class MiniMap {
	
	private int x, y, width, height;
	@SuppressWarnings("unused")
	private MainGameLoop game;
	float scale;
	Image image;

	Image smallImage;
	
public MiniMap(MainGameLoop game, Image image, int x, int y, int width, int height){
	this.game = game;
	this.image = image;
	this.x = x;
	this.y = y;
	this.width = width;
	this.height = height;
	this.scale = .5f;

}

public void update(){
	
}
public void draw(){
	image.draw(x, y, width, height);
}

public void drawEntity(Entity entity){
	EntityController entityController = entity.getEntityController();
	 smallImage = entity.getImage().getScaledCopy(.25f);
	float drawX =this.x+(entity.getX()*.01f);
			float drawY=this.y+(entity.getY()*.01f);
	smallImage.draw(drawX, drawY, this.scale);
	smallImage.setCenterOfRotation(smallImage.getWidth() * this.scale / 2,
			smallImage.getHeight() * this.scale / 2);
	smallImage.setRotation(entityController.getRotation());

}

}
