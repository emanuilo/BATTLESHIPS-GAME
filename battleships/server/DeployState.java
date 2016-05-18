package battleships.server;

public class DeployState extends State {
	
	public DeployState(Game game){
		super(game);
	}
	
	public void behavior(String str, Player player){
		String []parts=str.trim().split(" ");
		if (parts[0].equals("CONFIRM_DEPLOY")){
			player.setConfirmed();
		}
		else if (parts.length>3); //throw except.
		
		String []ships=parts[2].replaceAll("[^0-9]", replacement);
	}
}
