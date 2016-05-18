package battleships.common;

import battleships.Errors.*;
import java.util.*;
import java.io.*;

public class Ship {
	private class Seg{
		Coordinate coor;
		int status;  //0-Destroyed, 1-Alive
		Seg(){
			status=1;
			coor=null;
		}
	}
	private Seg[] parts;
	private boolean init;
	
	public Ship(int size){
		parts=new Seg[size];
		for (int i=0;i<parts.length;i++)
			parts[i]=new Seg();
		init=false;
	}
	
	public int getSize(){
		return parts.length;
	}
	public String toString(){
		if (!init) return "";
		StringBuilder str= new StringBuilder();
		str.append("S("+parts.length+")=[");
		for (int i=0;i<parts.length;i++){
			str.append(parts[i].coor.toString());
			if ((i+1)<parts.length)
				str.append(",");
		}
		str.append("]");
		return str.toString();
	}
	
	public void place(char c, Coordinate co) throws failed_ship_placement{
		int a,b;
		a=co.getr();
		b=co.getc();
		if (c=='v' || c=='V'){
			for (int i=0;i<parts.length;i++){
				parts[i].coor=new Coordinate(a,b);
				a++;
			}
		}
		else{
			if (c=='h'||c=='H'){
				for (int i=0;i<parts.length;i++){
					parts[i].coor=new Coordinate(a,b);
					b++;
				}
			}
			else 
				throw new failed_ship_placement();
		}
		init=true;
	}
	
	public static List<Ship> create (String s){
		List<Ship> l=new ArrayList<Ship>();
		String []parts1=s.split(";");
		for(String str:parts1){
			String []parts2=str.split("=",2);
			int a=Integer.parseInt(parts2[0].replaceAll("[^0-9]", ""));
			int b=Integer.parseInt(parts2[1].replaceAll("[^0-9]", ""));
			for (int i=0;i<b;i++){
				l.add(new Ship(a));
			}
		}
		return l;
	}
	
	public boolean comp(Ship s)throws ship_not_init{
		if ((init==false)||(s.init==false))
				throw new ship_not_init();
		int a1,b1,a2,b2,k=1;
		for (int i=0; i<parts.length;i++){
			a1=parts[i].coor.getr();
			b1=parts[i].coor.getc();
			for (int j=0; j<s.parts.length;j++){
				a2=s.parts[j].coor.getr();
				b2=s.parts[j].coor.getc();
				if ((a1==a2) && (b1==b2)){
					k=0;
					break;
				}
				if ((a1==a2) && ((b1==(b2+1)) || (b1==(b2-1)))){
					k=0;
					break;
				}
				if ((b1==b2) && ((a1==(a2+1)) || (a1==(a2-1)))){
					k=0;
					break;
				}
				if (((b1==(b2+1))||(b1==(b2-1)))&&((a1==(a2+1))||(a1==(a2-1)))){
					k=0;
					break;
				}
			}
			if (k==0)
				return false;
		}
		return true;
	}
	
	public boolean hit(Coordinate co) throws ship_not_init{
		if (init==false) throw new ship_not_init();
		int a1,a2,b1,b2;
		a1=co.getr();
		b1=co.getc();
		for (int i=0;i<parts.length;i++){
			a2=parts[i].coor.getr();
			b2=parts[i].coor.getc();
			if ((a1==a2)&&(b1==b2)){
				parts[i].status=0;
				return true;
			}
		}
		return false;
	}
	
	public boolean ishit(Coordinate co) throws failed_ship_get_coord, ship_not_init{
		if (init==false) throw new ship_not_init();
		int a1,a2,b1,b2;
		a1=co.getr();
		b1=co.getc();
		for (int i=0;i<parts.length;i++){
			a2=parts[i].coor.getr();
			b2=parts[i].coor.getc();
			if ((a1==a2)&&(b1==b2)){
				if (parts[i].status==1)
					return false;
				return true;
			}
		}
		throw new failed_ship_get_coord();
	}
	
	public int activeseg() throws ship_not_init{
		if (init==false) throw new ship_not_init();
		int s=0;
		for (int i=0;i<parts.length;i++)
			s+=parts[i].status;
		return s;
	}
	
	public boolean IsInTable(int n, int m) throws ship_not_init{
		if (init==false) throw new ship_not_init();
		if (init==false) return false;
		int a1,b1;
		for (int i=0;i<parts.length;i++){
			a1=parts[i].coor.getr();
			b1=parts[i].coor.getc();
			if (a1<0 || a1>=n)
				return false;
			if(b1<0 || b1>=m)
				return false;
		}
		return true;
	}
}
