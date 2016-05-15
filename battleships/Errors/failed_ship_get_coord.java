package battleships.Errors;

public class failed_ship_get_coord extends Exception{
	public String toString(){
		return "Ship not on those coordinates.\n";
	}
}
