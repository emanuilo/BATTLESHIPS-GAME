/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.communication;

import battleships.ErrorsServer.idNotFound;
import battleships.GUI.FirstWindow;
import battleships.GUI.SecondWindow;
import battleships.GUI.ThirdWindow;
import battleships.server.Game;
import java.io.IOException;
import java.net.SocketException;
import java.util.Hashtable;

/**
 *
 * @author POOP
 */
public class Server extends SocketCommunicator implements Runnable
{
    private final Hashtable<Integer, PlayerProxy> connectedPlayers = new Hashtable<>();
    private final Hashtable<String, Integer> usedNames=new Hashtable<>();
    private final Hashtable<Integer, String> nameGetter=new Hashtable<>();
    private final Thread serverThread = new Thread(this);
    private int clientID;
    private final Game game;
        
    public Server(Game _game) throws SocketException
    {
        super(SERVER_PORT);
        game = _game;
        serverThread.start();
    }
    public Thread getServerThread(){
    	return serverThread;
    }
    @Override
    public void run()
    {
        System.out.println("Game server started, listening on port " + SERVER_PORT + " ...");
        SecondWindow.getInstance().addConsoleText("Game server started, listening on port " + SERVER_PORT + " ...", 0);
        while( ! Thread.interrupted() )
        {
            try
            {
                String message = receive();
                try {
                	processMessage(message);
                	serverThread.yield();
				} catch (idNotFound e) {
					System.out.println(e);
				}
            }
            catch(IOException e) {  }
        }
        System.out.println("... game server ended.");
        SecondWindow.getInstance().addConsoleText("... game server ended.", 0);
        if(ThirdWindow.getInstance()!=null) ThirdWindow.getInstance().addConsoleText("... game server ended.", 0);
    }
    
    private void processMessage(String message) throws IOException, idNotFound
    {
    	System.out.println(message);
    	SecondWindow.getInstance().addConsoleText("RECEIVED: "+message, 0);
    	if(ThirdWindow.getInstance()!=null) ThirdWindow.getInstance().addConsoleText("RECEIVED: "+message, 0);
    	String []parts = message.trim().split(" ");
    	if( parts[0].equals( CommunicationCommands.JOIN_MESSAGE ) )
    		// ako je serveru stigla poruka JOIN
    	{	
    		
    		
    		String pass=game.getPass();
    		PlayerProxy pp = new PlayerProxy(this, receivePacket.getAddress(), receivePacket.getPort());
    		if (pass!=null && parts.length!=3)
    			pp.send(CommunicationCommands.PASSWORD_REQUIRED);
    		else if (pass==null || pass.equals(parts[2].substring(1, parts[2].length()))){
    			if(usedNames.get(parts[1])!= null){
    				pp.send(CommunicationCommands.DUPLICATE_NAME);
    				return;
    			}
    			clientID++;
    			connectedPlayers.put(clientID, pp);
    			usedNames.put(parts[1], clientID);
    			nameGetter.put(clientID, parts[1]);
    			game.newPlayer(pp, parts[1]);
    			pp.send(CommunicationCommands.WELCOME_MESSAGE + " " + clientID );
    			System.out.println("Added new player: " + parts[1]);
    		}
    		else 
    			pp.send(CommunicationCommands.ACCESS_DENIED);
    			
    	}
    	else if (parts[0].equals(CommunicationCommands.QUIT_MESSAGE)){
    		int id=Integer.parseInt(parts[1]);
    		PlayerProxy pp=connectedPlayers.get(id);
    		if (pp==null) throw new idNotFound();
    		connectedPlayers.remove(pp);
    		usedNames.remove(nameGetter.get(id));
    		nameGetter.remove(id);
    		pp.receivedMessage(parts[0]);
        	
    		//System.out.println("Server received: " + message);
        }
        else if (game.getState()!=null){
        	System.out.println(message);
        	int id=Integer.parseInt(parts[1]);
        	PlayerProxy pp=connectedPlayers.get(id);
        	if (pp==null)throw new idNotFound();
        	pp.receivedMessage(message);
        }
        else{
        	int id=Integer.parseInt(parts[1]);
        	PlayerProxy pp=connectedPlayers.get(id);
        	if (pp==null) throw new idNotFound();
        	pp.send("ERROR "+message);
        }
        	
    }
}
