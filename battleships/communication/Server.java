/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.communication;

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
    private final Thread serverThread = new Thread(this);
    private int clientID;
    private final Game game;
        
    public Server(Game _game) throws SocketException
    {
        super(SERVER_PORT);
        game = _game;
        serverThread.start();
    }
    
    @Override
    public void run()
    {
        System.out.println("Game server started, listening on port " + SERVER_PORT + " ...");
        while( ! Thread.interrupted() )
        {
            try
            {
                String message = receive();
                processMessage(message);
            }
            catch(IOException e) {  }
        }
        System.out.println("... game server ended.");
    }
    
    private void processMessage(String message) throws IOException
    {
    	String []parts = message.trim().split(" ");
    	if( parts[0].equals( CommunicationCommands.JOIN_MESSAGE ) )
    		// ako je serveru stigla poruka JOIN
    	{
    		PlayerProxy pp = new PlayerProxy(this, receivePacket.getAddress(), receivePacket.getPort());
    		clientID++;
    		connectedPlayers.put(clientID, pp);
    		game.newPlayer(pp, parts[1]);
    		pp.send(CommunicationCommands.WELCOME_MESSAGE + " " + clientID );
    		System.out.println("Added new player: " + parts[1]);
    	}
    	else if (parts[0].equals(CommunicationCommands.QUIT_MESSAGE)){
    		int id=Integer.parseInt(parts[1]);
    		PlayerProxy pp=connectedPlayers.get(id);
    		if (pp==null) ;//throw izuzetak;
    		pp.receivedMessage(" ");
        	
            // ako je serveru stigla neka druga poruka
            // treba razloziti poruku, utvrditi da li je validna, od kog igraca je potekla (id)
            // i onda treba proslediti odgovarajucem igracu putem njegovog playerProxy objekta.
            // U "pseudokodu": 
            
            // int id = extractID(message);
            // PlayerProxy pp = getPlayerProxy(id);
            // pp.receivedMessage( rest_of_the_message );

            System.out.println("Server received: " + message);
        }
        else if (game.getState()!=null){
        	int id=Integer.parseInt(parts[1]);
        	PlayerProxy pp=connectedPlayers.get(id);
        	if (pp==null) ;//throw izuzetak;
        	pp.receivedMessage(message);
        }
    }
}
