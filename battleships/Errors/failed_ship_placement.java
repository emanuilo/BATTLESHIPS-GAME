package battleships.Errors;

public class failed_ship_placement extends Exception {
	public String toString(){
		return "Parameters for ship placement are invalid. \n";
	}
}
