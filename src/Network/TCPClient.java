package Network;
import java.io.*;
import java.net.*;
import javax.swing.*;

import static java.lang.System.out;

public class  TCPClient
{
   
	String uname;
    PrintWriter pw;
    BufferedReader br;
    Socket client;
    NetworkThread networkThread;
	private boolean stopped;
    

    public TCPClient(String uname) throws Exception 
    {
        this.uname = uname;

    }

public void sendFloat(float f){
	System.out.println("Sending "+f);
pw.println(f);
}

    public void start() throws Exception 
    {this.uname = uname;
    client = new Socket("localhost",9020);
    br = new BufferedReader(new InputStreamReader(client.getInputStream())) ;
    pw = new PrintWriter(client.getOutputStream(),true);
    networkThread = new NetworkThread(); 
    	networkThread.start();
    	networkThread.setStopped(false);
        pw.println(uname + "\n");  // send name to server
         // create thread for listening for messages
        // take username from user
//        String name = JOptionPane.showInputDialog(null,"Enter your name :", "Username",
//             JOptionPane.PLAIN_MESSAGE); 
//        try 
//        {
//            new TCPClient(name);
//        } catch(Exception ex) {
//            out.println( "Error --> " + ex.getMessage());
//            networkThread.setStopped(true);
//        }

    } // end of main

    public void stop(){
    	
    }
    // inner class for Messages Thread
    class  NetworkThread extends Thread 
    {
        private boolean stopped = true;

		public void run() 
        {
            String line;
            try 
            {
            	while(true){
                while(!stopped) 
                {
                    line = br.readLine();
                  System.out.println(line + "\n");
                 // this.sleep(500);
                 // pw.print()
                }
            	}
            } catch(Exception ex) 
            {   
            }
        }

		public void setStopped(boolean b) {
			this.stopped  = b;
			
		}
    }
	public void setStopped(boolean b) {
		this.stopped = b;
		
	}
} 