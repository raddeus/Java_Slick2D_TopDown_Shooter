package com.thadb.game;

import java.io.IOException;

import Network.AbilityPacket;
import Network.KryoClient;
import Network.KryoServer;
import Network.PlayerPacket;

public class Network {

	KryoServer server;
	KryoClient client;
	private MainGameLoop game;
	
	public Network(MainGameLoop game){
		this.game = game;
	}
	
	public void startClient(String serverIP) throws IOException{
		client = new KryoClient(game, serverIP);
	}
	
	public void startServer() throws IOException{
		server = new KryoServer();
	}

	public void sendPacket(Object obj) {
		int ID;
		if (client != null){
			if (obj instanceof PlayerPacket){
				PlayerPacket playerPacket = (PlayerPacket)obj;
				ID = playerPacket.getID();
			}else if (obj instanceof AbilityPacket){
				AbilityPacket abilityPacket = (AbilityPacket)obj;
				ID = abilityPacket.getID();
			}else{
				ID = 0;
			}
			
			if ((ID!=0)&&(ID!=game.getMultiPlayerID())){
			client.sendPacket(obj);
			}else{
				System.out.println("not sending packet to self");
			}
		}
	}
	
	public void receivePacket(Object obj){
		if (obj instanceof PlayerPacket){
		game.handlePlayerPacket((PlayerPacket) obj);
		}else if (obj instanceof AbilityPacket){
			game.handleAbilityPacket((AbilityPacket) obj);
		}
	}
	
}
