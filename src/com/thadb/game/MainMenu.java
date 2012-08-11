package com.thadb.game;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

import Network.TCPServer;

import com.thadb.game.Enums.ButtonType;
import com.thadb.game.Enums.GameState;

public class MainMenu {

	MainGameLoop game;
	Resource resource;
	Image playImage;
	Image exitImage;
	Image optionsImage;
	Image borderImage;
	Image titleImage;
	Image hostImage;
	Image joinImage;
	
	float buttonScale = .5f;
	
	ArrayList<MenuButton> buttonList = new ArrayList<MenuButton>();
	int buttonWidth;
	int buttonHeight;
	int columns;
	int rows;
	int totalVerticalPadding;
	int totalHorizontalPadding;
	int columnWidth, rowHeight;
	int hPadding, vPadding;
	int activeRow, activeColumn;
	private int mouseX=0, mouseY=0;
	private MenuButton lastActiveButton;
	private MenuButton activeButton;

	public MainMenu(MainGameLoop game) {
		this.game = game;
		this.resource = game.resource;

		calculateGrid();
		initializeImages();
		
	}

	private void initializeImages() {
		playImage = resource.playImage;
		exitImage = resource.exitImage;
		optionsImage = resource.optionsImage;
		borderImage = resource.castBackground;
		titleImage = resource.titleImage;
		hostImage = resource.hostImage;
		joinImage = resource.joinImage;
		
		addButton(playImage, ButtonType.PLAY,2, 3, 1, 2);
		addButton(optionsImage, ButtonType.OPTIONS, 3, 3, 1, 2);
		addButton(exitImage, ButtonType.EXIT, 4, 3, 1, 2);
		addButton(titleImage, ButtonType.PLAY, 0,1,1,6);
		addButton(hostImage, ButtonType.HOST, 3, 1, 1, 2);
		addButton(joinImage, ButtonType.JOIN, 4, 1, 1, 2);
	}

	private void calculateGrid() {
		 columns = 8;
		 rows = 6;
		 columnWidth = MainGameLoop.getScreenWidth()/columns;
		 rowHeight = MainGameLoop.getScreenHeight()/rows;
		 hPadding = 20;
		 vPadding = 10;
		 totalVerticalPadding = 0;
		 totalHorizontalPadding = 0;
	}


	
	public void addButton(Image image, ButtonType bType, int row, int col, int rowSpan, int colSpan){
		MenuButton button = new MenuButton(this, image,borderImage, bType, row, col, rowSpan, colSpan);
		buttonList.add(button);
	}
	
	
	public void draw() {
		if(buttonList!=null){
		for (MenuButton button : buttonList){
				button.draw();
		}
		}
	}

	public void update(GameContainer gc, int delta) {
		Input input = gc.getInput();
		this.mouseX = input.getMouseX();
		this.mouseY = input.getMouseY();


		MenuButton button = getButtonUnderMouse();
		if (button!=null){
			
			if (input.isMousePressed(0)){
				ButtonType bType = button.getButtonType();
				switch(bType){
				case PLAY:
					game.setGameState(GameState.PLAY);
					break;
				case OPTIONS:
					
					break;
				case JOIN:
					String serverIP = JOptionPane.showInputDialog(null,"SERVER IP :", "Address",
			             JOptionPane.PLAIN_MESSAGE); 
					System.out.println("Serv IP: "+serverIP);
					try {
						game.network.startClient(serverIP);
						game.setGameState(GameState.PLAY);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Error: Error Joining Game - "+e.getMessage());
					}
					
					break;
				case HOST:
					try {
						game.network.startServer();
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Error: Couldnt Start Server - "+e.getMessage());
					}
					try {
						game.network.startClient("localhost");
						game.setGameState(GameState.PLAY);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "Error: Error Starting Game Client - "+e.getMessage());
					}
					
					break;
				case EXIT:
					game.exit();
					break;
			
					
				}
				//game.setGameState(GameState.PLAY);
			}
			
			if(button != lastActiveButton){
				if(lastActiveButton != null){
				lastActiveButton.onMouseExit();
				}
			this.lastActiveButton = button;
			this.activeButton = button;
			button.onMouseEnter();
			}

		}else{
			if(this.lastActiveButton!=null){
				
				this.lastActiveButton.onMouseExit();
				this.lastActiveButton = activeButton;
				this.activeButton = null;
			}
		}
		activeRow = getGridRow(input.getMouseY());
		activeColumn = getGridColumn(input.getMouseX());
	}

private MenuButton getButtonUnderMouse() {
	if(buttonList!=null){
		for (MenuButton button : buttonList){
			if(button.existsAt(this.mouseX, this.mouseY)){
				return button;
			}
		}
		}
		return null;
	}


public int getGridRow(int y){
	return y/rowHeight;
}

public int getGridColumn(int x){
	return x/columnWidth;
}

public MenuButton getActiveButton() {
	return activeButton;
}

public void setActiveButton(MenuButton activeButton) {
	this.activeButton = activeButton;
}


}
