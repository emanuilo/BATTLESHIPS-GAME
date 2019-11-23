/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleships.server;

import battleships.ErrorsServer.ENumOfPlayers;
import battleships.ErrorsServer.ETableSize;
import battleships.ErrorsServer.playerNotFound;
import battleships.common.Table;
import battleships.communication.CommunicationCommands;
import battleships.communication.PlayerProxy;
import battleships.communication.Server;
import battleships.communication.SocketCommunicator;

import java.net.SocketException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTable;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import java.util.Scanner;
import battleships.GUI.*;
/**
 *
 * @author POOP
 */
public class Game 
{
    private static Game instance;
    public ArrayList<Player> players = new ArrayList<>();
    private Server gameServer;
    
    private String password;
    private int tableSize;
    private ArrayList<Integer> shipsAndSizes=new ArrayList<>();
    private int deployTime;
    private int roundTime;
    private int maxNumOfPlayers;
    private int numberOfConfirmed;
    private int numOfRemainingPl;
    private int roundCounter;
    
    private BattleOverseer battleOverseer;
    private State state;
    ArrayList<String> fireUnion=new ArrayList<>();
    
    private static SecondWindow secondWindow;
    private static FirstWindow firstWindow;
    
    private boolean readyForDeploy;
    
    private Game() throws SocketException{
    	 setPort();
         addTableSize();
         addShipsSizes();
         addDeployTime();
         addRoundTime();
         addNumberOfPlayers();
         addPassword();
         
         state=new WaitingForConfState(this);
         battleOverseer=new BattleOverseer(this);
         gameServer = new Server(this);
    }
    
    private void setPort() {
    	int b=firstWindow.getPort();
		SocketCommunicator.setPort(b);
	}

    public void setReadyForDeploy(){
    	readyForDeploy=true;
    }
    
	public static Game instance() throws SocketException{
        if( instance == null )
            instance = new Game();
        return instance;
    }
    
    public void newPlayer(PlayerProxy pp, String name){
    	if(players.size()==maxNumOfPlayers)
    		return;
        Player p = new Player(pp, name, this);
        players.add(p);
        
        JTable table=secondWindow.getPlayersTable();
        table.setValueAt(numOfRemainingPl+1+".", numOfRemainingPl, 0);
        table.setValueAt(p.getName(), numOfRemainingPl, 1);
        table.setValueAt(p.getAddress(), numOfRemainingPl, 2);
        secondWindow.setNumOfConn(players.size()+"/"+maxNumOfPlayers);
        
        numOfRemainingPl++;
        if(players.size()==maxNumOfPlayers)
        	secondWindow.allPlayersConnected();
        secondWindow.setComboContent();
    }
    
    public void deleteAPlayer(Player playa){
    	numOfRemainingPl--;
		players.remove(playa);
		if(ThirdWindow.getInstance()==null) updateTable();
	}
    
    public void updateTable(){
    	JTable table=secondWindow.getPlayersTable();
    	for(int i=0; i<players.size();i++){
    		table.setValueAt(i+1+".", i, 0);
            table.setValueAt(players.get(i).getName(), i, 1);
            table.setValueAt(players.get(i).getAddress(), i, 2);
            secondWindow.setNumOfConn(players.size()+"/"+maxNumOfPlayers);
    	}
    	for(int i=players.size();i<maxNumOfPlayers;i++){
    		table.setValueAt("", i, 0);
            table.setValueAt("", i, 1);
            table.setValueAt("", i, 2);
    	}
    }
    
    public void startTheGame(){
    	battleOverseer.start();
    }

    public void stopTheGame(){
    	battleOverseer.interrupt();
    	gameServer.getServerThread().interrupt();
    	for (Player pl : players) {
			pl.getPlayerThread().interrupt();
		}
    }
    
    public void sendMessageToAllPlayers(String message){
        for(Player p : players)
            p.reportMessage(message);
    }
    
    public void addPassword(){
    	password=firstWindow.getPass();
    }
    
    public int getRoundCounter() {
		return roundCounter;
	}
    
    public long getElapsedTime(){
    	return battleOverseer.getElapsedTime();
    }
    
    public long getRemainingTime(){
    	if(state instanceof DeployState){
    		return (deployTime-getElapsedTime())/1000;
    	}
    	else
    		return (roundTime-getElapsedTime())/1000;
    }
    
    public void addTableSize(){
    	tableSize=firstWindow.getTableSize();
    }
    
    public int getTableSize(){
    	return tableSize;
    }
    
    public void addShipsSizes(){
    	String[] temp=secondWindow.getShips();
    	for(int i=0;i<temp.length;i++){
    		shipsAndSizes.add(i+1);
    		shipsAndSizes.add(Integer.parseInt(temp[i]));
    	}
    }
    
    public void addDeployTime(){
    	deployTime=firstWindow.getDeploy()*1000;
    }
    
    public void addRoundTime(){
    	roundTime=firstWindow.getRound()*1000;
    }
    
    public void addNumberOfPlayers(){
    	maxNumOfPlayers=firstWindow.getNumOfPl();
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
    		if(!p.amILooser()){
    			sb.append(p.getName());
    			if (players.indexOf(p)==players.size()-1)
    				sb.append(']');
    			else
    				sb.append("; ");
    		}
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
    	fireUnion.clear();
    	
    	sendMessageToAllPlayers(sb.toString());
    }
    
    public void updating(){
    	for(Player player:players){
    		if(player.numOfActiveSegs()==0 && !player.amILooser()){
    			player.setLooser();
    			player.disableTheTable();
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
    			if(!player.amILooser()){
    				player.reportMessage(CommunicationCommands.VICTORY);
    				ThirdWindow thirdWindow=ThirdWindow.getInstance();
    				thirdWindow.setVisible(false);
    				FourthWindow.entry(player.getName());
    			}
    			else
    				player.reportMessage(CommunicationCommands.GAME_WON+" "+winner.getName());
    		}
    	}
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
        	FirstWindow.entry();
        	
        	secondWindow=SecondWindow.getInstance();
        	while(secondWindow==null || !secondWindow.isDone()){
            	try {
					Thread.sleep(20);
				} catch (InterruptedException e) {}
            	secondWindow=SecondWindow.getInstance();
            }
        	firstWindow=FirstWindow.getInstance();
            Game game=Game.instance();
            
            while(game.players.size()!=game.getMaxNumOfPl() || !game.readyForDeploy ){
            	try {
					Thread.sleep(20);
				} catch (InterruptedException e) {}
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
