package battleships.GUI;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;

import battleships.common.Coordinate;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Emanuilo
 */
public class Table extends javax.swing.JPanel {
	private JPanel table;
	private JLabel name;
	private JLabel operational;
	private JLabel numOfOper;
	private JLabel picture;
	private JLabel looser;
	private JLabel[][] box;
	
	private String _name;
	private int tableSize;
	private int maxOperativeSegs;
	
    public Table(int size, String n) {
    	tableSize=size;
    	_name=n;
        initComponents();
    }

    public String getName(){
    	return _name;
    }
    
    public void drawShips(ArrayList<Coordinate> listOfCoors, int activeSegments){
    	for(int i=0;i<listOfCoors.size();i++){
    		if(box!=null)
    			box[listOfCoors.get(i).getr()][listOfCoors.get(i).getc()].setBackground(new Color(190,190,190));
    	}
    	numOfOper.setText(activeSegments+"/"+activeSegments);
    	maxOperativeSegs=activeSegments;
    }
    
    public void drawHitsAndMisses(ArrayList<Coordinate> hits, ArrayList<Coordinate> misses, int activeSegments) {
    	for(int i=0;i<hits.size();i++){
    		if(box!=null)
    			box[hits.get(i).getr()][hits.get(i).getc()].setBackground(Color.red);
    	}
    	
    	for(int i=0;i<misses.size();i++){
    		if(box!=null)
    			box[misses.get(i).getr()][misses.get(i).getc()].setBackground(new Color(0,0,128));
    	}
    	numOfOper.setText(activeSegments+"/"+maxOperativeSegs);
	} 
    
    public void disableTheTable(String player_name) {
    	for(int i=0;i<tableSize;i++){
        	for(int j=0;j<tableSize;j++){
        		box[i][j].setOpaque(false);
        		box[i][j].setBorder(null);
        	}
    	}
    	table.setBackground(Color.black);
    	table.setLayout(null);
    	looser.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        looser.setForeground(new java.awt.Color(255, 255, 255));
        looser.setText("LOOSER");
        table.add(looser);
        looser.setBounds(90, 130, 220, 80);
	}
    
    private void initComponents() {
    	picture=new JLabel();
    	name=new JLabel();
    	
    	numOfOper=new JLabel();
    	
    	operational=new JLabel();
    	
    	looser=new JLabel();
    	
        table = new javax.swing.JPanel();

        this.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        this.setLayout(null);
        
        table.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        table.setMinimumSize(new java.awt.Dimension(330, 330));
        table.setLayout(new java.awt.GridLayout(tableSize, tableSize));
        this.add(table);
        table.setBounds(10, 90, 330, 330);
        
        box=new JLabel[tableSize][tableSize];
        for(int i=0;i<tableSize;i++){
        	for(int j=0;j<tableSize;j++){
        		box[i][j]=new JLabel("");
        		box[i][j].setFocusable(true);
	            box[i][j].setOpaque(true);
	            box[i][j].setBackground(new Color(72,61,139));
	            box[i][j].setBorder(javax.swing.BorderFactory.createLineBorder(Color.black, 1));
	            table.add(box[i][j]);
        	}
        }
        /*box[5][6].setBackground(Color.red);
        box[5][7].setBackground(Color.red);
        box[4][7].setBackground(new Color(190,190,190));
        box[2][4].setBackground(new Color(190,190,190)); //siva
        box[3][4].setBackground(new Color(190,190,190));
        box[3][5].setBackground(new Color(0,0,128));*/   //promasaj

        name.setForeground(new java.awt.Color(255, 255, 255));
        name.setFont(new java.awt.Font("Tahoma", 1, 15));
    	name.setText(_name);
        this.add(name);
        name.setBounds(10, 20, 56, 19);
        
        numOfOper.setForeground(new java.awt.Color(255, 255, 255));
        numOfOper.setFont(new java.awt.Font("Tahoma", 1, 15));
    	numOfOper.setText("0/0");
    	this.add(numOfOper);
    	numOfOper.setBounds(110, 50, 56, 19);
        
    	operational.setForeground(new java.awt.Color(255, 255, 255));
        operational.setFont(new java.awt.Font("Tahoma", 1, 15));
    	operational.setText("Operational: ");
    	this.add(operational);
    	operational.setBounds(10, 50, 100, 19);
       
        this.add(picture);
        
        BufferedImage image=null;
		try {
			image=ImageIO.read(new File(_name+".png"));
			picture.setIcon(new javax.swing.ImageIcon(_name+".png"));
		} catch (IOException e) {
			picture.setIcon(new javax.swing.ImageIcon("default.jpg"));
		}		
        picture.setBounds(271, 10, 64, 64);

        this.setOpaque(false);
        this.setSize(350, 430);
    }

    /*public static void main(String args[]) {
     
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ThirdWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThirdWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThirdWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThirdWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThirdWindow().setVisible(true);
            }
        });
    }*/           
}

