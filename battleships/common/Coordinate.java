package battleships.common;

import java.util.logging.Level;
import java.util.logging.Logger;
import battleships.Errors.*;

import battleships.client.BattleshipsPlayer;

public class Coordinate {
    private int row;
    private int column;
    
    public Coordinate(int _row, int _column){
        row = _row; column = _column; 
    }
    
    public int getr(){return row;}
    
    public int getc(){return column;}
    
    public String toString(){
    	StringBuilder str=new StringBuilder();
    	if (row<10)
    		str.append("0");
    	str.append(row);
    	if (column<10)
    		str.append("0");
    	str.append(column);
    	return str.toString();
    }
    
    public static Coordinate create(String s){
    	try {
    		if (s.length()!=4) throw new err_coord_len();
    		String _row=s.substring(0,2);
    		String _column=s.substring(2,4);
    		return new Coordinate(Integer.parseInt(_row), Integer.parseInt(_column));
    	}
    	catch(NumberFormatException | IndexOutOfBoundsException | err_coord_len ex){
    		Logger.getLogger(BattleshipsPlayer.class.getName()).log(Level.SEVERE, null, ex);
    		return null;
    	}
    }
    
}
