/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.server;

import battleships.ErrorsServer.playerNotFound;
import battleships.common.Table;
import battleships.communication.CommunicationCommands;
import battleships.communication.PlayerProxy;
import battleships.communication.Server;
import java.net.SocketException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

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
    private int tableSize=1010;
    private ArrayList<Integer> shipsAndSizes=new ArrayList<>();
    private int deployTime=30000;
    private int roundTime=50000;
    private int maxNumOfPlayers=2;
    private int numberOfConfirmed;
    private int numOfRemainingPl;
    private int roundCounter;
    
    private BattleOverseer battleOverseer;
    private State state;
    ArrayList<String> fireUnion=new ArrayList<>();
    
    private Game() throws SocketException 
    {
         //addTableSize();
         addShipsSizes();
         /*addDeployTime();
         addRoundTime();
         addNumberOfPlayers();*/
         state=new WaitingForConfState(this);
         battleOverseer=new BattleOverseer(this);
         gameServer = new Server(this);
         //start overseer
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
        numOfRemainingPl++;
    }
    
    public void deleteAPlayer(Player playa) {
    	numOfRemainingPl--;
		players.remove(playa);
	}
    
    public void startTheGame(){
    	battleOverseer.start();
    	int b; 
    	b=5;
    }

    public void stopTheGame(){
    	battleOverseer.interrupt();
    	gameServer.getServerThread().interrupt();
    	for (Player pl : players) {
			pl.getPlayerThread().interrupt();
		}
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
    
    public int getRoundCounter() {
		return roundCounter;
	}
    
    public long getElapsedTime(){
    	return battleOverseer.getElapsedTime();
    }
    
    public void addTableSize(){
    	System.out.println("Table size: <xxyy>");
    	Scanner in=new Scanner(System.in);
    	tableSize=in.nextInt();
    }
    
    public int getTableSize(){
    	return tableSize;
    }
    
    public void addShipsSizes(){
    	System.out.println("Number and size of ships: (<size> <number>)");
    	Scanner in=new Scanner(System.in);
    	
    	shipsAndSizes.add(4);
    	shipsAndSizes.add(1);
    	
    	shipsAndSizes.add(3);
    	shipsAndSizes.add(2);
    	
    	shipsAndSizes.add(2);
    	shipsAndSizes.add(3);
    	
    	shipsAndSizes.add(1);
    	shipsAndSizes.add(3);
    	/*while(true){
    		Integer size=new Integer(in.nextInt());
    		if (size.intValue()==0) break;
    		shipsAndSizes.add(size);
    		
    		Integer number=new Integer(in.nextInt());
    		shipsAndSizes.add(number);
    	}*/
    }
    
    public void addDeployTime(){
    	System.out.println("Deploy time: ");
    	Scanner in=new Scanner(System.in);
    	deployTime=in.nextInt();    	
    }
    
    public void addRoundTime(){
    	System.out.println("Round time: ");
    	Scanner in=new Scanner(System.in);
    	roundTime=in.nextInt();  
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
    
    public int getDeployTime(){
    	return deployTime;
    }
    
    public int getRoundTime(){
    	return roundTime;
    }
    
    public String getShipSizesString(){
    	StringBuilder sb=new StringBuilder();
    	for (int i=0;i<(shipsAndSizes.size()-1);i=i+2){
    		sb.append("S(")
    		.append(shipsAndSizes.get(i))
    		.append(")=")
    		.append(shipsAndSizes.get(i+1))
    		.append(";");
    	}
    	return sb.toString();
    }
    
    public void deployShipsInformation(){
    	StringBuilder sb=new StringBuilder();
    	sb.append("D=").append(tableSize).append(";");
    	
    	sb.append(getShipSizesString());
    	 	
    	sendMessageToAllPlayers(sb.toString());
    }
    
    public void resendDeployShipsInformation(){
    	StringBuilder sb=new StringBuilder();
    	sb.append("D=").append(tableSize).append(";");
    	
    	sb.append(getShipSizesString());
    	
    	for (Player p:players){
    		if (!p.isConfirmed())
    			p.reportMessage(sb.toString());
    	}
    }
    
    public void deleteNotConfirmedPl(){
    	for (Player p:players)
    		if (!p.isConfirmed())
    			players.remove(p);
    }
    
    public void roundInformation(int rCounter){
    	roundCounter=rCounter;
    	StringBuilder sb=new StringBuilder();
    	sb.append("ROUND ")
    	.append(rCounter)
    	.append(" ")
    	.append(roundTime)
    	.append(" [");
    	for (Player p:players){
    		sb.append(p.getName());
    		if (players.indexOf(p)==players.size()-1)
    			sb.append(']');
    		else
    			sb.append("; ");
    	}
    	sendMessageToAllPlayers(sb.toString());
    }
    
    public void updateInformation(){
    	StringBuilder sb=new StringBuilder();
    	sb.append("UPDATE ")
    	.append("[");
    	
    	for(String singleHit:fireUnion){
    		sb.append(singleHit);
    		if (fireUnion.indexOf(singleHit)==fireUnion.size()-1)
    			sb.append(']');
    		else
    			sb.append(";");
    	}
    	if (fireUnion.size()==0) sb.append(']');
    	sendMessageToAllPlayers(sb.toString());
    }
    
    public int getNumOfRemaining(){
    	return numOfRemainingPl;
    }
    
    public int getNumOfConfirmed() {
		return numberOfConfirmed;
	}
    
    public void incNumOfConf(){
    	++numberOfConfirmed;
    }
    
    public void updating(){
    	for(Player player:players){
    		if(player.numOfActiveSegs()==0){
    			player.setLooser();
    			player.reportMessage(CommunicationCommands.GAME_OVER);
    			numOfRemainingPl--;
    		}
    	}
    	if (numOfRemainingPl==0)
    		sendMessageToAllPlayers(CommunicationCommands.NO_VICTORY);
    	else if (numOfRemainingPl==1){
    		Player winner=null;
    		for (Player player : players) {
				if(!player.amILooser())
					winner=player;
			}
    		for (Player player : players) {
				if(!player.amILooser())
					player.reportMessage(CommunicationCommands.VICTORY);
				else
					player.reportMessage(CommunicationCommands.GAME_WON+" "+winner.getName());
			}
    	}
    }
    
    public String getPass(){
    	return password;
    }
    
    public Player findAPlayer(String name) throws playerNotFound{
    	for (Player playa:players){
    		if (playa.getName().equals(name))
    			return playa;
    	}
    	throw new playerNotFound();
    }
    
    public int getMaxNumOfPl(){
    	return maxNumOfPlayers;
    }
    
    public static void main(String []args)
    {
        try 
        {
            Game game=Game.instance();
            
            while(true){
            	try {
					Thread.sleep(200);
				} catch (InterruptedException e) {}
            	if (game.players.size()==game.getMaxNumOfPl())
            		break;
            }
            
            game.startTheGame();
            
             try {
            	
				game.battleOverseer.join();
				game.stopTheGame();
			} catch (InterruptedException e) {}
            
        }
        catch (SocketException ex) 
        {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
