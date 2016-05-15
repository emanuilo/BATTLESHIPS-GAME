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
    
    
    public static final void main(String[] args){
    	Coordinate c1=Coordinate.create("0109");
    	Coordinate c2=Coordinate.create("0300");
    	System.out.println(c1.toString()+" "+c1.getr()+" "+c1.getc());
    	List<Ship> ll=Ship.create("S(2)=2");
    	System.out.println("Velicina: "+ll.size());
    	try{
    		ll.get(0).place('H', c1);
    		ll.get(1).place('V', c2);
    		Ship s=new Ship(2);
    		s.place('V',c1);
    		System.out.println(s.comp(ll.get(0)));
    		System.out.println(s.comp(ll.get(1)));
    		System.out.println(ll.toString());
    		System.out.println("Akctive seg: "+s.activeseg());
    		s.hit(c1);
    		System.out.println("Akctive seg: "+s.activeseg());
    		s.hit(Coordinate.create("0212"));
    		System.out.println("Akctive seg: "+s.activeseg());
    		System.out.println("is c1: "+s.ishit(c1));
    		//System.out.println("is c2: "+s.ishit(c2));
    		Table t=new Table(5,11);
    		System.out.println("add1: "+t.add(ll.get(0)));
    		System.out.println("Active t count: "+t.activecount());
    		System.out.println("add2: "+t.add(ll.get(1)));
    		System.out.println("Active t count: "+t.activecount());
    		System.out.println("add3: "+t.add(s));
    		System.out.println("Active t count: "+t.activecount());
    		System.out.println("Get ship1: "+ t.getship(c1).toString());
    		System.out.println("Get ship2: "+ t.getship(Coordinate.create("0400")).toString());
    		t.clean();
    		System.out.println("Active t count: "+t.activecount());
    	}
    	catch(Exception e){System.out.println(e.toString());}
    	
    }
}
