/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.client;

import battleships.communication.Client;
import battleships.communication.CommunicationCommands;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author POOP
 */
public class BattleshipsPlayer 
{
    private Client client;
    
    public BattleshipsPlayer(InetAddress address) throws SocketException, IOException
    {
        String name = "Sir.Francis.Drake";
        client = new Client(address);
        
        client.send(CommunicationCommands.JOIN_MESSAGE + " " + name);
        
        try {
            Thread.sleep(2000); // cekaj 2 s
        } catch (InterruptedException ex) {
            Logger.getLogger(BattleshipsPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(client.receive());
        
        // server bi trebalo da odbaci ovu poruku
        client.send("Hi there!");
        
        while(true)
        {
            System.out.println(client.receive());
        }
    }
    
    public static void main(String []args)
    {
        try 
        {
            BattleshipsPlayer newPlayer = new BattleshipsPlayer(InetAddress.getByName("localhost"));
        }
        catch (SocketException ex) 
        {
            Logger.getLogger(BattleshipsPlayer.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (UnknownHostException ex) 
        {
            Logger.getLogger(BattleshipsPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) 
        {
            Logger.getLogger(BattleshipsPlayer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
