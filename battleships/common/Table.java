package battleships.common;
import java.util.*;
import battleships.Errors.*;


public class Table{
    int rows,columns;
    List<Ship> ships;
    
    public Table(int n, int m){
    	rows=n;
    	columns=m;
    	ships=new ArrayList<Ship>();
    }
    
    public void clean(){
    	ships.clear();
    }
    
    public boolean add(Ship s)throws ship_not_init{
    	if (s.IsInTable(rows, columns)==false) return false;
		for (int i=0;i<ships.size();i++){
			if (s.comp(ships.get(i))==false)
				return false;		
		}
		ships.add(s);
		return true;
    }
    
    public Ship getship(Coordinate co)throws failed_ship_get_coord{
    	for (int i=0;i<ships.size();i++){
    		try{
    			ships.get(i).ishit(co);
    			return ships.get(i);
    		}
    		catch(failed_ship_get_coord e){}
    		catch(ship_not_init e){}
    	}
    	throw new failed_ship_get_coord();
    }
    
    public int activecount(){
    	int s=0;
    	for (int i=0;i<ships.size();i++){
    		try{
    			s+=ships.get(i).activeseg();
    		}
    		catch(ship_not_init e){}
    	}
    	return s;
    }
    
}
