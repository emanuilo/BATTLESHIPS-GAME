package battleships.server;

import battleships.communication.CommunicationCommands;

public class WaitingForConfState extends State {

	public WaitingForConfState(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String behavior(String s, Player player) {
		String []parts=s.trim().split(" ");
		if (parts[0].equals("CONFIRM_DEPLOY")){
			player.setConfirmed();
			return null;
		}
		else if(parts[0].equals(CommunicationCommands.STATE_REQUEST)){
			StringBuilder sb=new StringBuilder();
			sb.append("WFP ")
			.append(myGame.getNumOfConfirmed())
			.append("/")
			.append(myGame.players.size());
			return sb.toString();
		}
		else if (parts[0].equals(CommunicationCommands.LAYOUT_MESSAGE))
			return new DeployState(myGame).behavior(s, player);
		return null;
	}

}
