package com.thadb.game;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;

import com.thadb.game.Enums.MapLayer;


public class Map {
	Image collisionMap;
	Image drawMap;
	Image heightMap;
	
	MainGameLoop game;
	float scale;
	int height, width;
	
public Map(MainGameLoop game, Image collisionMap, Image mapDraw, Image heightMap, float scale){
	this.game = game;
	this.collisionMap = collisionMap;
	this.drawMap = mapDraw;
	this.heightMap = heightMap;
	this.scale = scale;
	this.height = (int) (collisionMap.getHeight()*scale);
	this.width = (int)(collisionMap.getWidth()*scale);
}

public float getScale() {
	return scale;
}

public void setScale(float scale) {
	this.scale = scale;
}

public int getHeight() {
	return height;
}

public void setHeight(int height) {
	this.height = height;
}

public int getWidth() {
	return width;
}

public Image getImage(){
	return collisionMap;
	//TODO remove this later.
}
public Image getImage(MapLayer mapLayer) {
	if(mapLayer == MapLayer.COLLISION){
	return collisionMap;
	}else
	if(mapLayer == MapLayer.HEIGHT){
	return heightMap;
	}else
	return drawMap;
	
}

public void setImage(Image image) {
	this.collisionMap = image;
}

public void setWidth(int width) {
	this.width = width;
}

public void draw(){
	collisionMap.draw(-game.camera.getX(), -game.camera.getY(), scale);
}

public void drawMap(){
	drawMap.draw(-game.camera.getX(), -game.camera.getY(), scale);
}

public Color getColor(MapLayer layer, float x, float y) {
	Image image = drawMap;
	switch(layer){
	case COLLISION:
		image = collisionMap;
		break;
	case HEIGHT:
		image = heightMap;
		break;
	case BACKGROUND:
		image = drawMap;
		break;
	}
return image.getColor((int)(x/this.getScale()), (int)(y/this.getScale()));
}


}
