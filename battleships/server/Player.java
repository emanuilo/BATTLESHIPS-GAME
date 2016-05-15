/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.server;

import battleships.common.Table;
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
                System.out.println(msg);
                
            }
        } catch(InterruptedException e) { }
    }

    public void reportMessage(String message)
    {
        try 
        {
            playerProxy.send(message);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
