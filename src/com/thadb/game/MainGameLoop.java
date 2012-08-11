package com.thadb.game;

import java.io.IOException;
import java.util.Random;

import javax.swing.JOptionPane;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;

import Network.AbilityPacket;
import Network.KryoClient;
import Network.KryoServer;
import Network.PlayerPacket;
import Network.TCPClient;
import Network.TCPServer;

import com.thadb.game.Abilities.BigShot;
import com.thadb.game.Abilities.SingleShot;
import com.thadb.game.Entitys.Character;
import com.thadb.game.Entitys.Target;
import com.thadb.game.Enums.AbilitySlot;
import com.thadb.game.Enums.GameState;
import com.thadb.game.Enums.MapDirection;

public class MainGameLoop extends BasicGame {
	static int screenWidth = 600, screenHeight = 400;
	static boolean fullScreen = false;
	Input input;
	int multiPlayerID;
	public Camera camera;
	Map map;
	Entity bomb;
	Network network;
	EntityManager entityManager;
	AbilityManager characterAbilityManager;
	public Resource resource;
	public Entity character;
	MiniMap miniMap;
	Entity turret;
	Entity cursor;
	CastBar castBar;
	CooldownBar cooldownBar;


	private GameState gameState;
//TCPServer server;
	Music robotWalk;
	OptionFrame optionFrame;
	public GameOptions options;
	int delta;
	long currentTime = System.currentTimeMillis();
	long lastTime = System.currentTimeMillis();
	private GameContainer gameContainer;

	MainMenu mainMenu;
	private long lastPacketSentTime;
	//private playerMap;
	//TCPClient client;

	public MainGameLoop() {
		super("WackGame");
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		Random rand = new Random();
		multiPlayerID = rand.nextInt(Integer.MAX_VALUE-50000)+50000;//50000 should be the max entities.
		this.gameContainer = gc;
		this.gameContainer.setUpdateOnlyWhenVisible(false);
		gameContainer.setAlwaysRender(true);
		input = gc.getInput();
		gameState = GameState.MAIN_MENU;
		options = new GameOptions(this);
		optionFrame = new OptionFrame(options);
		resource = new Resource();
		camera = new Camera(this, 100, 300);
		mainMenu = new MainMenu(this);
		map = new Map(this, resource.map, resource.mapDraw, resource.mapHeight,
				3);

		entityManager = new EntityManager(this);
		Image scaledCursor = resource.cursor.getScaledCopy(options
				.getCursorScale());
		gameContainer.setMouseCursor(scaledCursor, scaledCursor.getWidth() / 2,
				scaledCursor.getHeight() / 2);

		character = new Character(this, resource.character, 500, 600, .5f);
		character.setUsesHeight(true);
		EntityController characterController = character.getEntityController();

		characterController.setRotationSpeed(.3f);
		characterController.setMoveSpeed(1.7f);

		
		characterAbilityManager = character.getAbilityManager();


		miniMap = new MiniMap(this, resource.map, (screenWidth / 5) * 4,
				(screenHeight / 5) * 4, screenWidth / 5, screenHeight / 5);

		camera.setCenteredOnEntity(true);
		camera.setTargetEntity(character);
		camera.setCameraMovesWithMouse(true);
		initializeCastbar();
		initializeCooldownBar();
		
		this.network = new Network(this);
		
	}
	
	
//	public void startClient(){
//		if (client==null){
//		try {
//			client = new TCPClient("UserName");
//			client.start();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		}else{
//			client.setStopped(false);
//		}
//		
//	}
//	
//	public void startServer(){
//		try {
//			server = new TCPServer();
//			server.start();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			JOptionPane.showMessageDialog(null, "Error Starting Server: "+e.getMessage());
//		}
//
//	}

	@SuppressWarnings("unused")
	private void addTargets(int numberOfTargets) {
		Random rand = new Random();
		for (int x = 0; x < numberOfTargets; x++) {
			int randX = rand.nextInt(map.getWidth());
			int randY = rand.nextInt(map.getHeight());
			Target target = new Target(this, resource.target, randX, randY, 1f);
			target.setUsesHeight(true);
			target.entityController.setRotationSpeed(.3f);
			target.entityController.setMoveSpeed(.2f);
			target.entityController.setMoving(true);
			entityManager.addEntity(target);
		}
	}

	private void initializeCooldownBar() {
		int width = MainGameLoop.getScreenWidth() / 8;
		int height = width / 4;
		int y = MainGameLoop.getScreenHeight() - height;
		int x = MainGameLoop.getScreenWidth() / 4 - width / 2;
		cooldownBar = new CooldownBar(this, character.abilityManager.getAbility(AbilitySlot.SECONDARY), x, y, width, height);

	}

	private void initializeCastbar() {
		int width = MainGameLoop.getScreenWidth() / 4;
		int height = width / 8;
		int y = MainGameLoop.getScreenHeight() - height;
		int x = MainGameLoop.getScreenWidth() / 2 - width / 2;
		castBar = new CastBar(character, this, x, y, width, height);
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		if (this.gameState == GameState.PLAY) {

			this.delta = delta;
if (input.isKeyPressed(Input.KEY_Y)){
	network.sendPacket(character.getPlayerPacket());
}
			// NORTH
			if (input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_A)
					&& !input.isKeyDown(Input.KEY_S)
					&& !input.isKeyDown(Input.KEY_D)) {
				character.move(MapDirection.NORTH);
			}
			// NORTHEAST
			if (input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_A)
					&& !input.isKeyDown(Input.KEY_S)
					&& input.isKeyDown(Input.KEY_D)) {
				character.move(MapDirection.NORTHEAST);
			}
			// EAST
			if (!input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_A)
					&& !input.isKeyDown(Input.KEY_S)
					&& input.isKeyDown(Input.KEY_D)) {
				character.move(MapDirection.EAST);
			}
			// SOUTHEAST
			if (!input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_A)
					&& input.isKeyDown(Input.KEY_S)
					&& input.isKeyDown(Input.KEY_D)) {
				character.move(MapDirection.SOUTHEAST);
			}
			// SOUTH
			if (!input.isKeyDown(Input.KEY_W) && !input.isKeyDown(Input.KEY_A)
					&& input.isKeyDown(Input.KEY_S)
					&& !input.isKeyDown(Input.KEY_D)) {
				character.move(MapDirection.SOUTH);
			}
			// SOUTHWEST
			if (!input.isKeyDown(Input.KEY_W) && input.isKeyDown(Input.KEY_A)
					&& input.isKeyDown(Input.KEY_S)
					&& !input.isKeyDown(Input.KEY_D)) {
				character.move(MapDirection.SOUTHWEST);
			}
			// WEST
			if (!input.isKeyDown(Input.KEY_W) && input.isKeyDown(Input.KEY_A)
					&& !input.isKeyDown(Input.KEY_S)
					&& !input.isKeyDown(Input.KEY_D)) {
				character.move(MapDirection.WEST);
			}
			// NORTHWEST
			if (input.isKeyDown(Input.KEY_W) && input.isKeyDown(Input.KEY_A)
					&& !input.isKeyDown(Input.KEY_S)
					&& !input.isKeyDown(Input.KEY_D)) {
				character.move(MapDirection.NORTHWEST);
			}

			if (input.isKeyPressed(Input.KEY_ESCAPE)) {
				setGameState(GameState.MAIN_MENU);
			}

			if (input.isMouseButtonDown(0)) {
				Ability a = characterAbilityManager
						.getAbility(AbilitySlot.PRIMARY);
				characterAbilityManager.start(a);
				
			}

			if (input.isMouseButtonDown(1)) {
				Ability a = characterAbilityManager
						.getAbility(AbilitySlot.SECONDARY);
				characterAbilityManager.start(a);
			}

			if (input.isKeyPressed(Input.KEY_O)) {
				optionFrame.setVisible(true);
			}

			entityManager.updateEntities();
			characterAbilityManager.updateAbilities();
			character.update();
			camera.update();
			castBar.update();
			cooldownBar.update();
			// target.update();
			//
			// if (!character.getEntityController().isMoving()){
			// System.out.println("volume: "+robotWalk.getVolume());
			// robotWalk.setVolume(.1f);
			// }else{
			// robotWalk.setVolume(10);
			// }
			
			sendPacket(200);

		} else if (this.gameState == GameState.MAIN_MENU) {
			mainMenu.update(gc, delta);
		} else if (this.gameState == GameState.OPTIONS) {

		}

	}

	private void sendPacket(int packetsPerSecond) {
	long currentTime = System.currentTimeMillis();
	long timeSinceLastPacket = currentTime - lastPacketSentTime;
		if (timeSinceLastPacket > (1000/packetsPerSecond)){
			network.sendPacket(character.getPlayerPacket());
			lastPacketSentTime = System.currentTimeMillis();
		}
		
	}

	public void render(GameContainer gc, Graphics g) throws SlickException {
		if (this.gameState == GameState.PLAY) {
			map.drawMap();
			entityManager.drawEntitys();
			character.draw();
			castBar.draw();
			cooldownBar.draw();
		} else if (this.gameState == GameState.MAIN_MENU) {
			mainMenu.draw();
		} else if (this.gameState == GameState.OPTIONS) {

		}
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new MainGameLoop());
		app.setDisplayMode(screenWidth, screenHeight, fullScreen);
		app.start();
	}

	public Input getInput() {
		return input;
	}

	public void setInput(Input input) {
		this.input = input;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public float getDelta() {
		return delta;
	}

	public float getMapWidth() {
		return 0;
	}

	public float getMapHeight() {
		return 0;
	}

	public static int getScreenWidth() {
		return screenWidth;
	}

	public static void setScreenWidth(int screenWidth) {
		MainGameLoop.screenWidth = screenWidth;
	}

	public static int getScreenHeight() {
		return screenHeight;
	}

	public static void setScreenHeight(int screenHeight) {
		MainGameLoop.screenHeight = screenHeight;
	}

	public int getMouseX() {
		return input.getMouseX();
	}

	public int getMouseY() {
		return input.getMouseY();
	}

	public void resetCursor() {
		Image scaledCursor = resource.cursor.getScaledCopy(options
				.getCursorScale());
		try {
			gameContainer.setMouseCursor(scaledCursor,
					scaledCursor.getWidth() / 2, scaledCursor.getHeight() / 2);
		} catch (SlickException e) {
			e.printStackTrace();
		}

	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;

	}

	public void exit() {
		gameContainer.exit();
		
	}

	public void handlePlayerPacket(PlayerPacket playerPacket) {
		if (!entityManager.entityExists(playerPacket.getID())){
		System.out.println("trying to add entity into game from network");
	Entity newPlayer = new Character(this, resource.character, 500, 600, .5f);
newPlayer.setX(playerPacket.getX());
newPlayer.setY(playerPacket.getY());
newPlayer.getEntityController().setRotation(playerPacket.getRotation());
newPlayer.getEntityController().setRotationLocked(true);
newPlayer.setUsesHeight(true);
newPlayer.setID(playerPacket.getID());
AbilityManager newPlayerAbilityManager = newPlayer.getAbilityManager();

this.entityManager.addEntity(newPlayer, playerPacket.getID());
		}else{
			entityManager.updateEntity(playerPacket.getID(), playerPacket);
		}
	}

	public int getMultiPlayerID() {
		return multiPlayerID;
	}

//	public void setMultiPlayerID(int multiPlayerID) {
//		this.multiPlayerID = multiPlayerID;
//	}

	public void getPlayerMap() {
	//	return this.playerMap;
	
	}

	public void sendPacket(AbilityPacket abilityPacket) {
		network.sendPacket(abilityPacket);
		
	}

	public void handleAbilityPacket(AbilityPacket abilityPacket) {
		int ID = abilityPacket.getID();
		if (ID != this.multiPlayerID){
		Entity abilityEntity = this.entityManager.getEntity(ID);
		Ability ability = abilityEntity.getAbilityManager().getAbility(AbilitySlot.PRIMARY);
		ability.setParent(abilityEntity);
		ability.fireAbility();
		}
	}

	public Network getNetwork() {
		return this.network;
	}


	
	

}