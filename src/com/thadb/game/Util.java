package com.thadb.game;

public class Util {

	private Util(){
		
	}
	
	public static float getRotationBetweenEntities(Entity source, Entity target) {
//		float x1 = source.getScreenX();
//		float x2 = target.getScreenX();
//		float y1 = source.getScreenY();
//		float y2 = target.getScreenY();
		float x1 = source.getX();
		float x2 = target.getX();
		float y1 = source.getY();
		float y2 = target.getY();
		return (getRotationBetweenPoints(x1, y1, x2, y2));
	}
	
	public static float getRotationBetweenPoints(float x1, float y1, float x2, float y2) {
		double dx = x1 - x2;
		// Minus to correct for coord re-mapping
		double dy = -(y1 - y2);// TODO figure out what im doing here.
		double inRads = Math.atan2(dy, dx);
		if (inRads < 0)
			inRads = Math.abs(inRads);
		else
			inRads = 2 * Math.PI - inRads;
		return (float) Math.toDegrees(inRads) - 90;
	}
	
	public static float getDistance(Entity e1, Entity e2) {

		float x1 = e1.getX();
		float x2 = e2.getX();
		float y1 = e1.getY();
		float y2 = e2.getY();
		return getDistance(x1,y1,x2,y2);
}
	public static float getDistance(float x1, float y1, float x2, float y2) {
		
		float dX = (float) Math.pow(x2-x1, 2);
		float dY = (float) Math.pow(y2-y1, 2);
		return (float) Math.sqrt(dX+dY);
}
}
