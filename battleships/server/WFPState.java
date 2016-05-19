package battleships.server;

public class WFPState extends State {

	public WFPState(Game game) {
		super(game);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String behavior(String s, Player player) {
		String []parts=s.trim().split(" ");
		if (parts[0].equals("CONFIRM_DEPLOY")){
			player.setConfirmed();
		}
		return s;
	}

}
