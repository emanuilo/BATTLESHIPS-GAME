package battleships.server;

public abstract class State {
	protected Game myGame;
	
	public State(Game game){
		myGame=game;
	}
	
	public abstract String behavior(String s, Player player);
}
