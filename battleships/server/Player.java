/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.server;

import battleships.Errors.failed_ship_get_coord;
import battleships.Errors.ship_not_init;
import battleships.common.Coordinate;
import battleships.common.Ship;
import battleships.common.Table;
import battleships.communication.CommunicationCommands;
import battleships.communication.PlayerProxy;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author POOP
 */
public class Player implements Runnable
{
    private final Thread playerThread = new Thread(this);
    private final String name;
    private Table table;
    private final PlayerProxy playerProxy;
    private Game  myGame;
    private boolean confirmed=false;
    private boolean looser=false;
    
    public Player(PlayerProxy _playerProxy, String _name, Game game)
    {
        playerProxy = _playerProxy;
        name = _name;
        myGame=game;
        playerThread.start();
    }
    
    public void run()
    {
        try
        {
            while( ! Thread.interrupted() )
            {
                String msg = playerProxy.receive();
                if(msg.equals(CommunicationCommands.QUIT_MESSAGE))
                	deleteYourself();
                String returnMessage=myGame.getState().behavior(msg, this);
                if (returnMessage!=null) reportMessage(returnMessage);
                
            }
        } catch(InterruptedException e) { }
    }
    
    public void deleteYourself() {
		myGame.deleteAPlayer(this);
		reportMessage(CommunicationCommands.QUIT_RESPONSE);
	}
    
    public void setConfirmed(){
    	confirmed=true;
    	myGame.incNumOfConf();
    }
    
    public boolean isConfirmed(){
    	return confirmed;
    }
    
    public boolean setTable(Table t){
    	if (table==null){
    		table=t;
    		return true;
    	}
    	return false;
    }
    
    public String getName(){
    	return name;
    }
    
    public int numOfActiveSegs(){
    	return table.activecount();
    }

	public boolean fire(Coordinate co) throws failed_ship_get_coord, ship_not_init{
    	Ship ship=table.getship(co);
    	return ship.hit(co);
    }
    
    public void setLooser(){
    	looser=true;
    }
    
    public boolean amILooser() {
		return looser;
	}
    
    public void reportMessage(String message)
    {
        try 
        {
        	System.out.println(message);
            playerProxy.send(message);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
