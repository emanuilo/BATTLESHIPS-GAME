package battleships.GUI;

import java.awt.Color;
import java.net.SocketException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;

import battleships.common.Coordinate;
import battleships.server.Game;

import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Emanuilo
 */
public class ThirdWindow extends javax.swing.JFrame implements Runnable{
	private final Thread myThread=new Thread(this);
	
	private static ThirdWindow instance;
	private Game game;
	private boolean deployFlag;
	private boolean roundFlag;
	private int roundCnt;
	
	private javax.swing.JPanel jPanel1;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JLabel background;
	private javax.swing.JLabel time;
	private javax.swing.JLabel deploy_round;
	private javax.swing.JTextArea console;
    private javax.swing.JScrollPane consolePane;
	
	private Table[] arrayOfTables;
	
    public ThirdWindow() {
    	initComponents();
    	instance=this;
    }
    
    public void addConsoleText(String str, int gray){
    	console.setFont(new java.awt.Font("Tahoma", 0, 18));
    	//if(gray==0)
    		console.setForeground(new java.awt.Color(255, 255, 255));  //white
    	//else 
    	//	console.setForeground(Color.lightGray);
    	console.append(str+"\n");
    }
    
    public static ThirdWindow getInstance(){
        return instance;
    }

    public void setDeploy_RoundLabel(String str){
    	deploy_round.setText(str);
    }
    
    public void setTime(int t){
    	time.setText(t+"");
    }
    
    public void setShips(ArrayList<Coordinate> listOfCoors, String player_name, int activeSegments){
    	for (int i=0; i<arrayOfTables.length;i++){
    		if(arrayOfTables[i].getName().equals(player_name))
    			arrayOfTables[i].drawShips(listOfCoors, activeSegments);
    	}
    }
    
    public void drawHitsAndMisses(ArrayList<Coordinate> hits, ArrayList<Coordinate> misses, String player_name, int activeSegments) {
    	for (int i=0; i<arrayOfTables.length;i++){
    		if(arrayOfTables[i].getName().equals(player_name))
    			arrayOfTables[i].drawHitsAndMisses(hits, misses, activeSegments);
    	}
	}
    
    public void disableTheTable(String player_name) {
    	for (int i=0; i<arrayOfTables.length;i++){
    		if(arrayOfTables[i].getName().equals(player_name))
    			arrayOfTables[i].disableTheTable(player_name);
    	}
    }
    
    private void initComponents() {
    	consolePane = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();
        
        console.setColumns(20);
        console.setRows(5);
        console.setEditable(false);
        console.setOpaque(false);
        console.setBackground(new Color(0,0,0,0));
        consolePane.setViewportView(console);
        consolePane.setOpaque(false);
        consolePane.getViewport().setOpaque(false);

        getContentPane().add(consolePane);
        consolePane.setBounds(0, 550, 1030, 150);
        
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        background=new javax.swing.JLabel();
        time=new javax.swing.JLabel();
        deploy_round=new javax.swing.JLabel("DEPLOY TIME: ");
        
		try {
			game=Game.instance();
		} catch (SocketException e) {}
		int numberOfTables=game.getNumOfRemaining();
		int tableSize=game.getTableSize();
		
        
        arrayOfTables=new Table[numberOfTables];
        for(int i=0;i<numberOfTables;i++){
        	arrayOfTables[i]=new Table(tableSize, game.players.get(i).getName());
        }
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1048, 748));
        setMinimumSize(new java.awt.Dimension(1048, 748));
        setPreferredSize(new java.awt.Dimension(1048, 748));
        setSize(new java.awt.Dimension(1048, 748));
        getContentPane().setLayout(null);

        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        jPanel1.setOpaque(false);
        
        jScrollPane1.setPreferredSize(new java.awt.Dimension(150, 354));
        
        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        Group grupa=jPanel1Layout.createSequentialGroup().addGap(33, 33, 33);
        for(int i=0;i<numberOfTables;i++){
        	grupa.addComponent(arrayOfTables[i], javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE);
            if(i != (numberOfTables-1))grupa.addGap(40,40,40);
        }        
        grupa.addGap(33, 33, 33);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(grupa));
        
        
        Group grupa2=jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        for(int i=0;i<numberOfTables;i++){
        	grupa2.addComponent(arrayOfTables[i], javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE);
        }
        
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(10,10,10)
                    .addGroup(grupa2))
            );
        
        jScrollPane1.setViewportView(jPanel1);

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(120, 20, 800, 470);
        
        time.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        time.setForeground(new java.awt.Color(255, 255, 255));
        time.setText("00");
        getContentPane().add(time);
        time.setBounds(550, 510, 137, 22);

        deploy_round.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        deploy_round.setForeground(new java.awt.Color(255, 255, 255));
        deploy_round.setText("DEPLOY TIME: ");
        getContentPane().add(deploy_round);
        deploy_round.setBounds(380, 510, 137, 22);

        background.setIcon(new javax.swing.ImageIcon("background 3.jpg")); // NOI18N
        getContentPane().add(background);
        background.setBounds(1, 0, 1048, 748);
        
        pack();
        setLocationRelativeTo(null);
        setSize(new java.awt.Dimension(1048, 748));
        
    }// </editor-fold>                        

    public void setDeployFlag(){
    	deployFlag=true;
    }
    
    public void setRoundFlag() {
		roundFlag=true;
	}
    
    public void _start(){
    	myThread.start();
    }
    
    public void wakeUp(int roundCounter){
    	roundCnt=roundCounter;
    	roundFlag=true;
    	synchronized (myThread) {
			myThread.notify();
		}
    }
    
    public void stop(){
    	myThread.interrupt();
    }
    
    public void run(){
    	while(game.getRemainingTime()>0){
    		time.setText(game.getRemainingTime()+"");
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {}
    	}
    	
    	while(!myThread.interrupted()){
    		while(!roundFlag)
    			try {
    				synchronized (myThread) {
    					myThread.wait();
    				}
    			} catch (InterruptedException e) {}
    		
    		deploy_round.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    		deploy_round.setForeground(new java.awt.Color(255, 255, 255));
    		deploy_round.setText("ROUND "+roundCnt+" TIME :");
    		deploy_round.setBounds(320, 510, 250, 22);
    		
    		while(game.getRemainingTime()>0){
    			time.setText(game.getRemainingTime()+"");
    			try {
    				Thread.sleep(1000);
    			} catch (InterruptedException e) {}
    		}
    	}
    }
    
    public static void entry() {
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
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThirdWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
   
    // End of variables declaration                   
}
