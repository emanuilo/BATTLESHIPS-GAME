package battleships.ErrorsServer;

public class ETableSize extends Exception {
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Incorrect table size! (3<tableSize<100)\n";
	}
}
