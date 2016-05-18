package battleships.server;

import java.util.ArrayList;
import java.util.List;

import battleships.Errors.failed_ship_placement;
import battleships.common.Coordinate;
import battleships.common.Ship;
import battleships.communication.CommunicationCommands;

public class DeployState extends State {
	
	public DeployState(Game game){
		super(game);
	}
	
	@Override
	public void behavior(String str, Player player){
		String []parts=str.trim().split(" ");
		if (parts.length>3 || parts[0]!=CommunicationCommands.LAYOUT_MESSAGE); //throw except.
		making_and_placing(parts[2]);
		
	}
	
	public void making_and_placing(String str){
		Table table=new Table()
		char orientation = 0;
		String []parts=str.trim().split(";");
		List<Ship> ships=Ship.create(myGame.getShipSizesString());
		for(String singleShip:parts){
			String []parts2=singleShip.split("=");
			parts2[0].replaceAll("[^0-9]", "");
			String []coordinates=parts2[1].split(",");
			coordinates[0].replaceAll("[^0-9]", "");
			coordinates[coordinates.length-1].replaceAll("[^0-9]", "");
			
			if(Integer.parseInt(coordinates[1])-Integer.parseInt(coordinates[0])==1)
				orientation='H';
			else if(Integer.parseInt(coordinates[1])-Integer.parseInt(coordinates[0])==100)
				orientation='V';
			
			for(Ship sh:ships){
				if(sh.getSize()==Integer.parseInt(parts2[0])){
					try{
						sh.place(orientation,Coordinate.create(coordinates[0]));
					}catch(failed_ship_placement error){}
					
				}
					
					
			}
		}
	}
}
