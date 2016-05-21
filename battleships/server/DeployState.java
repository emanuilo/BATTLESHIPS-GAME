package battleships.server;

import java.util.ArrayList;
import java.util.List;

import battleships.Errors.failed_ship_placement;
import battleships.Errors.ship_not_init;
import battleships.common.Coordinate;
import battleships.common.Ship;
import battleships.common.Table;
import battleships.communication.CommunicationCommands;

public class DeployState extends State {
	
	public DeployState(Game game){
		super(game);
	}
	
	@Override
	public String behavior(String str, Player player){
		String []parts=str.trim().split(" ");
		if (parts[0].equals("CONFIRM_DEPLOY")){
			player.setConfirmed();
			return null;
		}
		else if(parts[0].equals(CommunicationCommands.STATE_REQUEST)){
			System.out.println("DS "+(myGame.getDeployTime()-myGame.getElapsedTime())/1000);
			return "DS "+(myGame.getDeployTime()-myGame.getElapsedTime())/1000;
		}
		else if (parts.length!=3 || !parts[0].equals(CommunicationCommands.LAYOUT_MESSAGE))
			return "ERROR "+str;
		Table table=making_and_placing(parts[2]);
		if (table!=null){
			player.setTable(table);
			return CommunicationCommands.LAYOUT_ACCEPTED;
		}
		else return CommunicationCommands.LAYOUT_REJECTED;
		
	}
	
	public Table making_and_placing(String str){
		int x=myGame.getTableSize()/100;
		int y=myGame.getTableSize()%100;
		boolean correctLayout=false;
		Table table=new Table(x,y);
		
		char orientation = 0;
		String []parts=str.trim().split(";");
		
		List<Ship> ships=Ship.create(myGame.getShipSizesString()); //pravljenje brodova na osnovu zadatih velicina i broja
				
		for(String singleShip:parts){ 					//postavljanje brodova na zadate koordinate
			correctLayout=false;
			String []parts2=singleShip.split("=");		
			parts2[0]=parts2[0].replaceAll("[^0-9]", "");     	//velicina broda
			
			String []coordinates=parts2[1].split(",");  //koordinate segmenata
			coordinates[0]=coordinates[0].replaceAll("[^0-9]", "");
			coordinates[coordinates.length-1]=coordinates[coordinates.length-1].replaceAll("[^0-9]", "");
			
			if(Integer.parseInt(coordinates[1])-Integer.parseInt(coordinates[0])==1)
				orientation='H';
			else if(Integer.parseInt(coordinates[1])-Integer.parseInt(coordinates[0])==100)
				orientation='V';
			
			for(Ship sh:ships){
				if(sh.getSize()==Integer.parseInt(parts2[0])){
					try{
						sh.place(orientation,Coordinate.create(coordinates[0]));
						if(table.add(sh)) 
							correctLayout=true;
						ships.remove(sh);
						break;
					}catch(failed_ship_placement | ship_not_init error){return null;}
				}
			}
		}
		if (correctLayout==true) return table;
		else return null;
	}
}
