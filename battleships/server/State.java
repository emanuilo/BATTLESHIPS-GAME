package battleships.server;

public abstract class State {
	protected Game myGame;
	
	public State(Game game){
		myGame=game;
	}
	
	public abstract void behavior(String s, Player player);
}
