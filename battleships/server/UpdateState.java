package battleships.server;

import battleships.communication.CommunicationCommands;

public class UpdateState extends State {
	
	public UpdateState(Game game){
		super(game);
	}

	public String behavior(String str, Player player){
		String []parts=str.trim().split(" ");
		if (parts[0].equals(CommunicationCommands.STATE_REQUEST))
			return "U";
		else if (parts[0].equals(CommunicationCommands.LAYOUT_MESSAGE))
				return CommunicationCommands.LAYOUT_REJECTED;
		else if (parts[0]==CommunicationCommands.FIRE)   //isteklo vreme za RoundState
			return CommunicationCommands.FIRE_REJECTED;
		else
			return "ERROR "+str;
		
	}
}
