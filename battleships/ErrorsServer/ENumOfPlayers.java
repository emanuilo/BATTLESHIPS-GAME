package battleships.ErrorsServer;

public class ENumOfPlayers extends Exception {
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Minimum number of players is 2!\n";
	}
}
