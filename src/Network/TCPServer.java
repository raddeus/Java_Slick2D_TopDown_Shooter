package Network;
// Chat Server runs at port no. 9020
import java.io.*;
import java.util.*;
import java.net.*;
import static java.lang.System.out;

public class  TCPServer extends Thread
{
  Vector<String> users = new Vector<String>();
  Vector<HandleClient> clients = new Vector<HandleClient>();

  int PORT = 9020;
  int NumClients = 10;
  ServerSocket server;

public TCPServer() throws IOException{
	  server = new ServerSocket(PORT,NumClients);
      out.println("Server Connected...");
}

  public void broadcast(String user, String message)  
  {
        // send message to all connected users
        for (HandleClient c : clients){
        	//System.out.println("name: "+message);
        	System.out.println("User: "+user+" Message: "+message);
        	
           if (!c.getUserName().equals(user))
           {
              c.sendMessage(user,message);
           }
        }
  }

  class HandleClient extends Thread 
  {
    String name = "";
    BufferedReader input;
    PrintWriter output;

    public HandleClient(Socket client) throws Exception 
    {
          // get input and output streams
        input = new BufferedReader(new InputStreamReader(client.getInputStream())); 
         output = new PrintWriter (client.getOutputStream(),true);
         // read name
         name  = input.readLine();
         System.out.println("name = "+name);
         users.add(name); // add to vector
         output.println(name);
         
         start();
    }

    public void sendMessage(String uname,String  msg)  
    {
        output.println( uname + ":" + msg);
    }

    public String getUserName() 
    {  
        return name; 
    }

    public void run()  
    {
    	System.out.println("handleClient started");
         String line;
         try    
         {
            while(true)   
            {
                line = input.readLine();
                if (line.equals("end")) 
                {
                    clients.remove(this);
                    users.remove(name);
                    break;
                }
                broadcast(name,line); // method  of outer class - send messages all
            }// end of while
         } // try
         catch(Exception e) 
         {
           System.out.println(e.getMessage());
         }
    } // end of run()
  } // end of inner class

@Override
public void run(){
	
      while( true) 
      {
    	  
         Socket client = null;
		try {
			client = server.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         HandleClient c = null;
		try {
			c = new HandleClient(client);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Adding new client");
         clients.add(c);
     }  // end of while
}
} // end of Server