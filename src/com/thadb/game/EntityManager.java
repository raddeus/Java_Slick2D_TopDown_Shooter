package com.thadb.game;

import java.util.ArrayList;
import java.util.HashMap;

import Network.PlayerPacket;

public class EntityManager {
	MainGameLoop game;
	private int entityCount = 0;
	// List of entity's to be removed next frame
	ArrayList<Integer> entityRemoveList = new ArrayList<Integer>();

	ArrayList<Entity> entityAddList = new ArrayList<Entity>();
	// Map of all entity's in the game
	HashMap<Integer, Entity> entityMap = new HashMap<Integer, Entity>();

	public EntityManager(MainGameLoop game) {
		this.game = game;
	}

	public int getEntityCount() {
		return entityCount;
	}

	public void setEntityCount(int entityCount) {
		this.entityCount = entityCount;
	}
	
	
	public void addEntity(Entity entity, int id) {
		entityCount++;
		entity.setID(id);
		entityAddList.add(entity);
		
	}

	
	public void addEntity(Entity entity) {
		entityCount++;
		entity.setID(entityCount);
		entityAddList.add(entity);
	}

	public void removeEntity(int id) {
		entityRemoveList.add(id);
	}

	public void drawEntitys() {
		for (Entity entity : entityMap.values()) {
			entity.draw();
		}
	}

	public void updateEntities() {
		for (Entity entity : entityMap.values()) {
			entity.update();
			if (entity.isDelete() || entity.isCollided()) {
				removeEntity(entity.getID());
				if (entity.isCollided()) {
					entity.onCollide();
				}
			}
		}

		for (int i : entityRemoveList) {
			entityMap.remove(i);
		}
		entityRemoveList.clear();

		for (Entity e : entityAddList) {
			entityMap.put(e.getID(), e);
		}
		entityAddList.clear();

	}

	public boolean entityExists(int id) {
		return entityMap.containsKey(id);
	}

	public void updateEntity(int id, PlayerPacket packet) {
		Entity e = entityMap.get(id);
		e.setX(packet.getX());
		e.setY(packet.getY());
		e.entityController.setRotation(packet.getRotation());
		
	}

	public Entity getEntity(int iD) {
		return entityMap.get(iD);
	}




}
