package battleships.GUI;



import java.awt.Color;
import java.awt.event.*;
import java.net.SocketException;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

import battleships.ErrorsServer.ENumOfPlayers;
import battleships.ErrorsServer.ETableSize;
import battleships.ErrorsServer.playerNotFound;
import battleships.server.Game;



import java.awt.Color;
import java.awt.Component;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Emanuilo
 */
public class SecondWindow extends javax.swing.JFrame {
	private static SecondWindow instance;
	
	private FirstWindow firstWindow;
	private JLabel[] labelArray;
	private JTextField[] textFieldsArray;
	private int numOfCategories;
	private String[] ships;
	private boolean done;
	private JButton kick;

	private SecondWindow(FirstWindow firstW) {
    	firstWindow=firstW;
        this.getContentPane().setBackground(new Color(0,0,0));
        initComponents();
        instance=this;
    }
    
    public static SecondWindow getInstance(){
        return instance;
    }
    
    public boolean isDone() {
    	return done;
    }
    
    public String[] getShips(){
    	return ships;
    }
    
    public JTable getPlayersTable(){
    	return playersTable;
    }
    
    public void setNumOfConn(String str){
    	numOfConnected.setText(str);
    }
    
    public void addConsoleText(String str, int gray){
    	console.setFont(new java.awt.Font("Tahoma", 0, 18));
    	if(gray==0)
    		console.setForeground(new java.awt.Color(255, 255, 255));  //white
    	else 
    		console.setForeground(Color.lightGray);
    	console.append(str+"\n");
    }
    
    public void setComboContent(){
    	Game game=null;
        try {
			game=Game.instance();
		} catch (SocketException e) {}
        String[] str=new String[game.getNumOfRemaining()];
        for(int i=0;i<str.length;i++){
        	str[i]=(i+1)+"";
        }
        
        combo.setModel(new javax.swing.DefaultComboBoxModel<>(str));

    }
    
    private void initComponents() {
    	combo = new javax.swing.JComboBox<>();
    	
    	kick=new JButton("KICK");
    	kick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kickActionPerformed(evt);
            }
        });
    	getContentPane().add(kick);
        kick.setBounds(800, 370, 79, 25);
        
    	consolePane = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();
        
    	connectedLabel=new JLabel();
    	connectedLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
    	connectedLabel.setForeground(new java.awt.Color(255, 255, 255));
    	connectedLabel.setText("Connected players: ");
    	getContentPane().add(connectedLabel);
    	connectedLabel.setBounds(500, 60, 190, 40);
    	
    	numOfConnected=new JLabel();
    	numOfConnected.setFont(new java.awt.Font("Tahoma", 1, 22)); // NOI18N
    	numOfConnected.setForeground(new java.awt.Color(255, 255, 255));
    	numOfConnected.setText("0/"+firstWindow.getNumOfPl());
        getContentPane().add(numOfConnected);
        numOfConnected.setBounds(690, 66, 82, 27);
        
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
    	
    	numOfCategories=firstWindow.getCategories();
    	labelArray=new JLabel[numOfCategories];
    	textFieldsArray=new JTextField[numOfCategories];
    	for(int i=0; i<numOfCategories;i++){
    		labelArray[i]=new JLabel();
    		textFieldsArray[i]=new JTextField();
    	}
    	
        jPanel1 = new javax.swing.JPanel();
        
        confirm_start = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane2.setOpaque(false);
        jScrollPane2.getViewport().setOpaque(false);
        
        _background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1030, 701));
        setMinimumSize(new java.awt.Dimension(1030, 701));
        setPreferredSize(new java.awt.Dimension(1030, 701));
        setSize(new java.awt.Dimension(1030, 701));
        getContentPane().setLayout(null);

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridLayout(numOfCategories, 2, 0, 10));

        for(int i=0;i<numOfCategories;i++){
        	labelArray[i].setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        	labelArray[i].setForeground(new java.awt.Color(255, 255, 255));
        	labelArray[i].setText("Size "+(i+1)+" ships:");
        	jPanel1.add(labelArray[i]);
            jPanel1.add(textFieldsArray[i]);
            textFieldsArray[i].addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    textFieldsMouseClicked(evt);
                }
            });
        }

        getContentPane().add(jPanel1);
        jPanel1.setBounds(50, 120, 331, numOfCategories*35+(numOfCategories-2)*4);

        confirm_start.setText("CONFIRM");
        confirm_start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirm_startActionPerformed(evt);
            }
        });
        getContentPane().add(confirm_start);
        confirm_start.setBounds(50, 440, 87, 25);

        int numOfPl=firstWindow.getNumOfPl();
        Object[][] forTable=new Object[numOfPl][4];
        for(int i=0; i<numOfPl;i++){
        	forTable[i][0]=null;
        	forTable[i][1]=null;
        	forTable[i][2]=null;
        	forTable[i][3]="";
        }
        
        playersTable = new javax.swing.JTable();
        Color bg = new Color(0f,0f,0f,0f);
        playersTable.setBackground(bg);
        playersTable.setOpaque(false);
        playersTable.setForeground(Color.white);
        playersTable.setFont(new java.awt.Font("Tahoma", 1, 18));
        
        
        playersTable.setModel(new javax.swing.table.DefaultTableModel(
            forTable,
            new String [] {
                "No.", "Name", "IP Address", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
        playersTable.setRowHeight(30);
        jScrollPane2.setViewportView(playersTable);

        getContentPane().add(jScrollPane2);
        jScrollPane2.setBounds(500, 100, 500, 250);
        
        
        getContentPane().add(combo);
        combo.setBounds(890, 370, 79, 25);
        
        _background.setIcon(new javax.swing.ImageIcon("background 3.jpg")); // NOI18N
        _background.setMaximumSize(new java.awt.Dimension(1012, 701));
        _background.setMinimumSize(new java.awt.Dimension(1012, 701));
        _background.setPreferredSize(new java.awt.Dimension(1012, 701));
        getContentPane().add(_background);
        _background.setBounds(0, 0, 1030, 701);

        setSize(new java.awt.Dimension(1048, 748));
        setLocationRelativeTo(null);
    }// </editor-fold>                        

    private void kickActionPerformed(java.awt.event.ActionEvent evt) {                                      
        try {
        	int choice=Integer.parseInt(combo.getSelectedItem().toString());
        	String player=playersTable.getValueAt(choice+1, 1).toString();
			Game.instance().deleteAPlayer(Game.instance().findAPlayer(player));
		} 
        catch (SocketException e) {}
        catch (playerNotFound e) {}
    }
    
    private void textFieldsMouseClicked(MouseEvent evt) {
    	for(int i=0; i<numOfCategories; i++){
    		if(textFieldsArray[i].getText().equals("Input required!")){
    			textFieldsArray[i].setText("");
    			textFieldsArray[i].setForeground(new java.awt.Color(0,0,0));
    		}
    	}
	}
    
    private void confirm_startActionPerformed(java.awt.event.ActionEvent evt) {                                         
        if(confirm_start.getText().equals("CONFIRM")){
        	ships=new String[numOfCategories];
        	for(int i=0;i<numOfCategories;i++){
        		ships[i]=textFieldsArray[i].getText();
        		if(ships[i].equals("")){
        			textFieldsArray[i].setForeground(Color.red);
        			textFieldsArray[i].setText("Input required!");
        			ships=null;
        			return;
        		}
        	}
        	done=true;
        	confirm_start.setText("START");
        	confirm_start.setEnabled(false);
        }
        else{
        	try {
				setVisible(false);
		        ThirdWindow.entry();
		        Game.instance().setReadyForDeploy();
		        
			} catch (SocketException e) {}
        }
    }
    
    public void allPlayersConnected(){
    	confirm_start.setEnabled(true);
    }
   
    public static void entry(FirstWindow first_window){
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SecondWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SecondWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SecondWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SecondWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SecondWindow(first_window).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify  
    private javax.swing.JComboBox<String> combo;
    private javax.swing.JTextArea console;
    private javax.swing.JScrollPane consolePane;
    private javax.swing.JButton confirm_start;
    private javax.swing.JLabel _background;
    private javax.swing.JLabel connectedLabel;
    private javax.swing.JLabel numOfConnected;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable playersTable;
    
    // End of variables declaration                   
}
