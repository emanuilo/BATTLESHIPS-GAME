package battleships.server;

import battleships.Errors.failed_ship_get_coord;
import battleships.Errors.ship_not_init;
import battleships.ErrorsServer.playerNotFound;
import battleships.common.Coordinate;
import battleships.communication.CommunicationCommands;

public class RoundState extends State {
	
	public RoundState(Game game){
		super(game);
	}
	
	public String behavior(String str, Player player){
		String []parts=str.trim().split(" ");
		if (parts[0].equals(CommunicationCommands.STATE_REQUEST)){
			System.out.println("R "+myGame.getRoundCounter()+" "+(myGame.getRoundTime()-myGame.getElapsedTime())/1000);
			return "R "+myGame.getRoundCounter()+" "+(myGame.getRoundTime()-myGame.getElapsedTime())/1000;
		}
		else if (parts[0].equals(CommunicationCommands.LAYOUT_MESSAGE))
				return CommunicationCommands.LAYOUT_REJECTED;
		else if (parts.length!=3 || !parts[0].equals(CommunicationCommands.FIRE))
			return "ERROR "+str;
		
		String []playersAndCoors=parts[2].split(";");
		playersAndCoors[0]=playersAndCoors[0].replaceAll("\\[","");
		playersAndCoors[playersAndCoors.length-1]=playersAndCoors[playersAndCoors.length-1].replaceAll("]","");
		if (playersAndCoors.length>player.numOfActiveSegs() || playersAndCoors[0].equals(""))
			return CommunicationCommands.FIRE_REJECTED;
		
		for (String st:playersAndCoors){
			String coordinate=st.substring(st.length()-4, st.length());
			String playerName=st.substring(1,st.length()-5);
			
			int x=myGame.getTableSize()/100;
			int y=myGame.getTableSize()%100;
			Coordinate objectCoor=Coordinate.create(coordinate);
			if (x<objectCoor.getr() || objectCoor.getr()<0 || y<objectCoor.getc() || objectCoor.getc()<0) //koordinata van opsega
				return CommunicationCommands.FIRE_REJECTED;
			
			try{
				Player victim=myGame.findAPlayer(playerName);
				if(victim.fire(objectCoor)==true){ //fire accepted - hit
					String fireOutcome="{"+playerName+"}"+coordinate+"H";
					myGame.fireUnion.add(fireOutcome);    //FIRE ACCEPTED
				}
				
			}catch(failed_ship_get_coord | ship_not_init err){ //fire accepted ali je miss
				String fireOutcome="{"+playerName+"}"+coordinate+"M";
				myGame.fireUnion.add(fireOutcome);  //FIRE ACCEPTED
			}catch (playerNotFound e) {
				return CommunicationCommands.FIRE_REJECTED;
			}
			
		}
		return CommunicationCommands.FIRE_ACCEPTED;
		

	}
}
