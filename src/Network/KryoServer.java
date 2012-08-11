package Network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

public class KryoServer {
	Server server;
	Kryo kryo;
	
	public KryoServer() throws IOException{
		 server = new Server();
		 kryo = server.getKryo();
		kryo.register(SomeRequest.class);
		kryo.register(SomeResponse.class);
		kryo.register(PlayerPacket.class);
		kryo.register(AbilityPacket.class);
		server.start();
			server.bind(54555, 54777);

		addListeners();
	}
	/**
	 * @param args
	 */
	public void addListeners(){
		
		
		server.addListener(new Listener() {
			   public void received (Connection connection, Object object) {
			      if (object instanceof SomeRequest) {
			         SomeRequest request = (SomeRequest)object;
			         System.out.println(request.text);

			         SomeResponse response = new SomeResponse();
			         response.text = "Thanks!";
			         connection.sendTCP(response);
			      }
			      if (object instanceof PlayerPacket) {
				         PlayerPacket playerPacket = (PlayerPacket)object;

for(Connection c : server.getConnections()){
	if (c.getID() != connection.getID()){
	c.sendTCP(playerPacket);
	}
}
				      }
			      
			      if (object instanceof AbilityPacket) {
				         AbilityPacket abilityPacket = (AbilityPacket)object;

for(Connection c : server.getConnections()){
	if (c.getID() != connection.getID()){
	c.sendTCP(abilityPacket);
	}
}
				      }
			      
			   }
			});

	}

}
