package com.thadb.game.Enums;

public enum MapLayer {
BACKGROUND, COLLISION, OBJECT, HEIGHT
}

//Background - drawn at the very back, usually the graphic of the map.
//Collision - Not drawn, but updated, red = collision
//Object - drawn, used for above ground map components
//Height - Uses grayscale to determine height.
