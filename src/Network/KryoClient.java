package Network;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.thadb.game.MainGameLoop;
import com.thadb.game.Network;

public class KryoClient {
	Client client;
	Kryo kryo;
	MainGameLoop game;
	Network network;
	
	public KryoClient (MainGameLoop game, String serverIP) throws IOException{
		this.game = game;
		this.network = game.getNetwork();
		client = new Client();
		kryo = client.getKryo();
		kryo.register(SomeRequest.class);
		kryo.register(SomeResponse.class);
		kryo.register(PlayerPacket.class);
		kryo.register(AbilityPacket.class);
		client.start();

			client.connect(5000, serverIP, 54555, 54777);

			// TODO Auto-generated catch block
//		System.out.println("Client Error: "+e.getMessage());
//JOptionPane.showMessageDialog(null, e.getMessage());
		
		addListeners();
	}
	public void connect(){
		
	}
	
	private void addListeners() {
		client.addListener(new Listener() {
			   public void received (Connection connection, Object object) {
			      if (object instanceof SomeResponse) {
			         SomeResponse response = (SomeResponse)object;
			         System.out.println(response.text);
			      }else if (object instanceof PlayerPacket){
			    	  PlayerPacket playerPacket = (PlayerPacket)object;
			    	 network.receivePacket(playerPacket);
			      }else if(object instanceof AbilityPacket){
			    	  System.out.println("got an ability packet");
			    	  AbilityPacket abilityPacket = (AbilityPacket)object;
			    	  network.receivePacket(abilityPacket);
			      }
			   }
			});
	}

	public void sendRequest(){
		SomeRequest request = new SomeRequest();
		//request.text = "Here is the request!";
		client.sendTCP(request);
	}

	public void sendPacket(Object obj) {
		client.sendTCP(obj);		
	}
	

}
