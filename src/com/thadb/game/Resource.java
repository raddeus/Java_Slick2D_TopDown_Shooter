package com.thadb.game;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;


public class Resource {
	Image plane = null;
	Image map = null;
	public Image bullet = null;
	Image bombImage = null;
	public Image explosion = null;
	Image turret = null;
	Image cursor = null;
	Image ship = null;
	public Sound explosionSound = null;
	public Sound wooshSound = null;
	public Sound blip = null;
	public Sound blip2 = null;
	public Sound gunshot = null;
	public Sound missileLaunch = null;
	public Image castForeground;
	public Image castBackground;
	public Image playImage;
	public Image exitImage;
	public Image optionsImage;
	public Image hostImage;
	public Image joinImage;
	public Image titleImage;
	public Image mapDraw;
	public Image mapHeight;
	public Image character;
	public Music robotWalk;
	public Image target;
	
	public Image turretBase;
	public Image turretGun;
	public Resource(){
		try {
			initialize();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void initialize() throws SlickException{
		plane = new Image("data/plane.png");
		character = new Image("data/character.png");
		bullet = new Image("data/bulletDouble.png");
		bombImage = new Image("data/bomb.png");
		explosion = new Image("data/explosion.png");
		turret = new Image("data/turret.png");
		cursor = new Image("data/cursor.png");
		ship = new Image("data/ship.png");
		castForeground = new Image("data/pbarFor.png");
		castBackground = new Image("data/pbarBack.png");
		explosionSound = new Sound("data/explosionmono.ogg");
		wooshSound = new Sound("data/whoosh.wav");
		blip = new Sound("data/blip.ogg");
		blip2 = new Sound("data/blip2.ogg");
		playImage = new Image("data/playImage.png");
		optionsImage = new Image("data/optionsImage.png");
		exitImage = new Image("data/exitImage.png");
		hostImage = new Image("data/hostImage.png");
		joinImage = new Image("data/joinImage.png");
		titleImage = new Image("data/titleImage.png");
		gunshot = new Sound("data/gunshot.ogg");
		missileLaunch = new Sound("data/missileLaunch.ogg");
		robotWalk = new Music("data/robotWalk.ogg");
		map = new Image("data/mapcollision.png");
		target = new Image("data/target.png");
		mapDraw = new Image("data/mapDraw.png");
		mapHeight = new Image("data/heightMap.png");
		turretBase = new Image("data/turretBase.png");
		turretGun = new Image("data/turretGun.png");
		
	}
}
