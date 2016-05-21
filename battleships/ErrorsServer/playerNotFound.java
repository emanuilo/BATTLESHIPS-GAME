package battleships.ErrorsServer;

public class playerNotFound extends Exception {

	@Override
	public String toString() {
		return "Player not found!\n";
	}

}
