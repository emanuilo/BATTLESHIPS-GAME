package battleships.Errors;

public class err_coord_len extends Exception {
	public String toString(){
		return "Size of coordinate text is not valid.\n";
	}
}
