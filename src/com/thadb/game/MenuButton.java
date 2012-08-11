package com.thadb.game;

import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;

import com.thadb.game.Enums.ButtonType;

public class MenuButton {
int row, col, rowSpan, colSpan;
int x,y, width, height;
Image image, borderImage;
MainMenu main;
Sound blip;
private boolean highlight = false;
private ButtonType buttonType;
public MenuButton(MainMenu main, Image image, Image borderImage, ButtonType buttonType, int row, int col, int rowSpan, int colSpan){
	this.main = main;
	this.image = image;
	this.buttonType = buttonType;
	this.borderImage = borderImage;
	this.row = row;
	this.col = col;
	this.rowSpan = rowSpan;
	this.colSpan = colSpan;
	this.x = main.columnWidth*this.col;
	this.y = main.rowHeight*this.row;
	this.width = main.columnWidth*this.colSpan;
	this.height = main.rowHeight*this.rowSpan;
	   blip = main.game.resource.blip2;
	
}

public int getRow() {
	return row;
}

public void setRow(int row) {
	this.row = row;
}

public int getCol() {
	return col;
}

public Image getImage() {
	return image;
}

public void setImage(Image image) {
	this.image = image;
}

public void setCol(int col) {
	this.col = col;
}

public int getRowSpan() {
	return rowSpan;
}

public void setRowSpan(int rowSpan) {
	this.rowSpan = rowSpan;
}

public int getColSpan() {
	return colSpan;
}

public void setColSpan(int colSpan) {
	this.colSpan = colSpan;
}

public void setHighlight(boolean b) {
	this.highlight = b;
	
}

public boolean isHighlighted() {
	return this.highlight;
}

public boolean existsAt(int mouseX, int mouseY) {
	if (((mouseX > this.x+main.hPadding)&&(mouseX<(this.x+this.width-main.hPadding)))&&((mouseY > this.y+main.vPadding)&&(mouseY<(this.y+this.height-main.vPadding)))){
		return true;
	}else{
		return false;
	}
}

public void onMouseEnter() {
	setHighlight(true);
  blip.play();
	
}

public void onMouseExit() {
	setHighlight(false);
}

public void draw() {
	if(this.isHighlighted()){
		borderImage.draw(getCol()*main.columnWidth,getRow()*main.rowHeight, main.columnWidth*getColSpan(), main.rowHeight*getRowSpan());
	}else{
		
	}
	image.draw(getCol()*main.columnWidth,getRow()*main.rowHeight, main.columnWidth*getColSpan(), main.rowHeight*getRowSpan());
}

public ButtonType getButtonType() {
return this.buttonType;
	
}

}
