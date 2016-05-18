/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.server;

import battleships.common.Table;
import battleships.communication.PlayerProxy;
import battleships.communication.Server;
import java.net.SocketException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Scanner;

/**
 *
 * @author POOP
 */
public class Game 
{
    private static Game instance;
    ArrayList<Player> players = new ArrayList<>();
    private Server gameServer;
    
    private String password;
    private int tableSize;
    private ArrayList<Integer> shipsAndSizes=new ArrayList<>();
    private int deployTime;
    private int RoundTime;
    private int maxNumOfPlayers;
    
    private State state;
    
    private Game() throws SocketException 
    {
         gameServer = new Server(this);
         addTableSize();
         addShipsSizes();
         addDeployTime();
         addRoundTime();
         addNumberOfPlayers();
    }
    
    public static Game instance() throws SocketException
    {
        if( instance == null )
            instance = new Game();
        return instance;
    }
    
    public void newPlayer(PlayerProxy pp, String name)
    {
        Player p = new Player(pp, name, this);
        players.add(p);        
    }
    
    public void sendMessageToAllPlayers(String message)
    {
        for(Player p : players)
            p.reportMessage(message);
    }
    
    public void addPassword(){
    	System.out.println("Password:");
    	if (password!=null){
    		System.out.println("Password already exists!");
    		/*System.out.println("Current password:");
    		Scanner in=new Scanner(System.in);
    		String oldPass=in.nextLine();
    		if (oldPass!=password){
    			System.out.println("Wrong password!");
    			return;
    		}*/
    	}
    	Scanner in=new Scanner(System.in);
    	String pass=in.nextLine();
    	password=pass;
    }
    
    public void addTableSize(){
    	System.out.println("Table size: ");
    	Scanner in=new Scanner(System.in);
    	tableSize=in.nextInt();
    }
    
    public void addShipsSizes(){
    	System.out.println("Number and size of ships: (<size> <number>)");
    	Scanner in=new Scanner(System.in);
    	
    	while(true){
    		Integer size=new Integer(in.nextInt());
    		if (size.intValue()==0) break;
    		shipsAndSizes.add(size);
    		
    		Integer number=new Integer(in.nextInt());
    		shipsAndSizes.add(number);
    	}
    }
    
    public void addDeployTime(){
    	System.out.println("Deploy time: ");
    	Scanner in=new Scanner(System.in);
    	deployTime=in.nextInt();    	
    }
    
    public void addRoundTime(){
    	System.out.println("Round time: ");
    	Scanner in=new Scanner(System.in);
    	RoundTime=in.nextInt();  
    }
    
    public void addNumberOfPlayers(){
    	System.out.println("Maximum number of players: ");
    	Scanner in=new Scanner(System.in);
    	int num=in.nextInt();  
    	if (num<2) System.out.println("Minimum number of players is 2!");
    	else maxNumOfPlayers=num;
    }
    
    public void changeState(State st){
    	state=st;
    }
    
    public State getState(){
    	return state;
    }
    
    public void deployshipsInformation(){
    	StringBuilder sb=new StringBuilder();
    	sb.append("D=").append(tableSize).append("; ");
    	
    	for (int i=0;(i-1)<shipsAndSizes.size();i++){
    		sb.append("S(")
    		.append(shipsAndSizes.get(i))
    		.append(")=")
    		.append(shipsAndSizes.get(i+1))
    		.append("; ");
    	}
    	
    	sendMessageToAllPlayers(sb.toString());
    }
    
    public static void main(String []args)
    {
        try 
        {
            Game.instance();
        }
        catch (SocketException ex) 
        {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
