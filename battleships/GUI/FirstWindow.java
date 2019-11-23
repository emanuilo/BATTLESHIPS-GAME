package battleships.GUI;


import java.awt.Color;

/**
 *
 * @author Emanuilo
 */
public class FirstWindow extends javax.swing.JFrame {
	private static FirstWindow instance;
	
    private int port;
    private int numOfPl;
    private int categories;
    private int deploy;
    private int round;
    private int tableSize;
    private String pass;
   
    public FirstWindow() {
        this.getContentPane().setBackground(new Color(0,0,0));
        initComponents();
        instance=this;
    }

    public static FirstWindow getInstance(){
        return instance;
    }
    
    private void initComponents() {

    	PortTextF = new javax.swing.JTextField();
        PortLabel = new javax.swing.JLabel();
        NumOfPlTextF = new javax.swing.JTextField();
        NumOfPlLable = new javax.swing.JLabel();
        CategoriesTextF = new javax.swing.JTextField();
        CategoriesLabel = new javax.swing.JLabel();
        DeployTTextF = new javax.swing.JTextField();
        RoundTTextF = new javax.swing.JTextField();
        DeployTLable = new javax.swing.JLabel();
        RoundTLabel = new javax.swing.JLabel();
        PassLabel = new javax.swing.JLabel();
        StartButton = new javax.swing.JButton();
        logo = new javax.swing.JLabel();
        PassTextF = new javax.swing.JPasswordField();
        TableSizeLabel = new javax.swing.JLabel();
        TableSizeTextF = new javax.swing.JTextField();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(1012, 701));
        setSize(new java.awt.Dimension(1012, 701));
        getContentPane().setLayout(null);

        PortTextF.setMinimumSize(new java.awt.Dimension(30, 22));
        PortTextF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                PortTextFMouseClicked(evt);
            }
        });
        getContentPane().add(PortTextF);
        PortTextF.setBounds(436, 333, 150, 30);

        PortLabel.setFont(new java.awt.Font("Tahoma", 0, 14));
        PortLabel.setForeground(new java.awt.Color(255, 255, 255));
        PortLabel.setText("Port:");
        getContentPane().add(PortLabel);
        PortLabel.setBounds(290, 340, 80, 16);

        NumOfPlTextF.setMinimumSize(new java.awt.Dimension(30, 22));
        NumOfPlTextF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                NumOfPlTextFMouseClicked(evt);
            }
        });
        getContentPane().add(NumOfPlTextF);
        NumOfPlTextF.setBounds(436, 370, 150, 30);
        
        NumOfPlLable.setFont(new java.awt.Font("Tahoma", 0, 14));
        NumOfPlLable.setForeground(new java.awt.Color(255, 255, 255));
        NumOfPlLable.setText("Number of players:");
        getContentPane().add(NumOfPlLable);
        NumOfPlLable.setBounds(290, 377, 160, 25);

        CategoriesTextF.setMinimumSize(new java.awt.Dimension(30, 22));
        CategoriesTextF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CategoriesTextFMouseClicked(evt);
            }
        });
        getContentPane().add(CategoriesTextF);
        CategoriesTextF.setBounds(436, 407, 150, 30);

        CategoriesLabel.setFont(new java.awt.Font("Tahoma", 0, 14));
        CategoriesLabel.setForeground(new java.awt.Color(255, 255, 255));
        CategoriesLabel.setText("Categories of ships:");
        getContentPane().add(CategoriesLabel);
        CategoriesLabel.setBounds(290, 414, 160, 25);

        DeployTTextF.setMinimumSize(new java.awt.Dimension(30, 22));
        DeployTTextF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DeployTTextFMouseClicked(evt);
            }
        });
        getContentPane().add(DeployTTextF);
        DeployTTextF.setBounds(436, 444, 150, 30);

        RoundTTextF.setMinimumSize(new java.awt.Dimension(30, 22));
        RoundTTextF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                RoundTTextFMouseClicked(evt);
            }
        });
        getContentPane().add(RoundTTextF);
        RoundTTextF.setBounds(436, 481, 150, 30);

        DeployTLable.setFont(new java.awt.Font("Tahoma", 0, 14));
        DeployTLable.setForeground(new java.awt.Color(255, 255, 255));
        DeployTLable.setText("Deploy time (s):");
        getContentPane().add(DeployTLable);
        DeployTLable.setBounds(290, 451, 160, 25);

        RoundTLabel.setFont(new java.awt.Font("Tahoma", 0, 14));
        RoundTLabel.setForeground(new java.awt.Color(255, 255, 255));
        RoundTLabel.setText("Round time (s):");
        getContentPane().add(RoundTLabel);
        RoundTLabel.setBounds(290, 488, 160, 16);

        PassLabel.setFont(new java.awt.Font("Tahoma", 0, 14));
        PassLabel.setForeground(new java.awt.Color(255, 255, 255));
        PassLabel.setText("Password:");
        getContentPane().add(PassLabel);
        PassLabel.setBounds(290, 562, 160, 16);

        StartButton.setText("START");
        StartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartButtonActionPerformed(evt);
            }
        });
        getContentPane().add(StartButton);
        StartButton.setBounds(471, 641, 73, 25);

        logo.setBackground(new java.awt.Color(102, 153, 0));
        logo.setIcon(new javax.swing.ImageIcon("maxresdefault2.png")); // NOI18N
        getContentPane().add(logo);
        logo.setBounds(180, 30, 810, 230);

        PassTextF.setToolTipText("");
        getContentPane().add(PassTextF);
        PassTextF.setBounds(436, 555, 150, 30);

        TableSizeLabel.setFont(new java.awt.Font("Tahoma", 0, 14));
        TableSizeLabel.setForeground(new java.awt.Color(255, 255, 255));
        TableSizeLabel.setText("Table size:");
        getContentPane().add(TableSizeLabel);
        TableSizeLabel.setBounds(290, 525, 160, 16);

        TableSizeTextF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TableSizeTextFMouseClicked(evt);
            }
        });
        getContentPane().add(TableSizeTextF);
        TableSizeTextF.setBounds(436, 518, 150, 30);

        background.setIcon(new javax.swing.ImageIcon("grey-gradient-abstract-hd-wallpaper-1920x1080-44311.png")); // NOI18N
        getContentPane().add(background);
        background.setBounds(0, 0, 1030, 700);

        setSize(new java.awt.Dimension(1048, 748));
        setLocationRelativeTo(null);
    }// </editor-fold>                        
 
    private void StartButtonActionPerformed(java.awt.event.ActionEvent evt) {                                            
        String Sport=PortTextF.getText();
        String SnumOfPl=NumOfPlTextF.getText();
        String Scategories=CategoriesTextF.getText();
        String Sdeploy=DeployTTextF.getText();
        String Sround=RoundTTextF.getText();
        String Spass=PassTextF.getText();
        String STableSize=TableSizeTextF.getText();
        
        
        if(Sport.equals("")){
            PortTextF.setForeground(new java.awt.Color(255,0,0));
            PortTextF.setText("Input required!");
            return;
        }
        if(SnumOfPl.equals("")){
            NumOfPlTextF.setForeground(new java.awt.Color(255,0,0));
            NumOfPlTextF.setText("Input required!");
            return;
        }
        if(Scategories.equals("")){
            CategoriesTextF.setForeground(new java.awt.Color(255,0,0));
            CategoriesTextF.setText("Input required!");
            return;
        }
        if(Sdeploy.equals("")){
            DeployTTextF.setForeground(new java.awt.Color(255,0,0));
            DeployTTextF.setText("Input required!");
            return;
        }
        if(Sround.equals("")){
            RoundTTextF.setForeground(new java.awt.Color(255,0,0));
            RoundTTextF.setText("Input required!");
            return;
        }
        if(STableSize.equals("")){
            TableSizeTextF.setForeground(new java.awt.Color(255,0,0));
            TableSizeTextF.setText("Input required!");
            return;
        }
        if(Spass.equals("")){
            pass=null;
        }
        else pass=Spass;
        
        port=Integer.parseInt(Sport);
        numOfPl=Integer.parseInt(SnumOfPl);
        categories=Integer.parseInt(Scategories);
        deploy=Integer.parseInt(Sdeploy);
        round=Integer.parseInt(Sround);
        tableSize=Integer.parseInt(STableSize);
      
        
        setVisible(false);
        SecondWindow.entry(this);
    }                                           

    private void PortTextFMouseClicked(java.awt.event.MouseEvent evt) {                                       
        if(PortTextF.getText().equals("Input required!")){
            PortTextF.setText("");
            PortTextF.setForeground(new java.awt.Color(0,0,0));
        }
    }                                      

    private void NumOfPlTextFMouseClicked(java.awt.event.MouseEvent evt) {                                          
        if(NumOfPlTextF.getText().equals("Input required!")){
            NumOfPlTextF.setText("");
            NumOfPlTextF.setForeground(new java.awt.Color(0,0,0));
        }
    }                                         

    private void CategoriesTextFMouseClicked(java.awt.event.MouseEvent evt) {                                             
        if(CategoriesTextF.getText().equals("Input required!")){
            CategoriesTextF.setText("");
            CategoriesTextF.setForeground(new java.awt.Color(0,0,0));
        }
    }                                            

    private void DeployTTextFMouseClicked(java.awt.event.MouseEvent evt) {                                          
        if(DeployTTextF.getText().equals("Input required!")){
            DeployTTextF.setText("");
            DeployTTextF.setForeground(new java.awt.Color(0,0,0));
        }
    }                                         

    private void RoundTTextFMouseClicked(java.awt.event.MouseEvent evt) {                                         
        if(RoundTTextF.getText().equals("Input required!")){
            RoundTTextF.setText("");
            RoundTTextF.setForeground(new java.awt.Color(0,0,0));
        }
    }                                        

    private void TableSizeTextFMouseClicked(java.awt.event.MouseEvent evt) {                                            
        if(TableSizeTextF.getText().equals("Input required!")){
            TableSizeTextF.setText("");
            TableSizeTextF.setForeground(new java.awt.Color(0,0,0));
        }        
    }                                           
    
    public int getPort() {
		return port;
	}

	public int getNumOfPl() {
		return numOfPl;
	}

	public int getCategories() {
		return categories;
	}

	public int getDeploy() {
		return deploy;
	}

	public int getRound() {
		return round;
	}

	public int getTableSize() {
		return tableSize;
	}

	public String getPass() {
		return pass;
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
            java.util.logging.Logger.getLogger(FirstWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FirstWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FirstWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FirstWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FirstWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel CategoriesLabel;
    private javax.swing.JTextField CategoriesTextF;
    private javax.swing.JTextField DeployTTextF;
    private javax.swing.JLabel DeployTLable;
    private javax.swing.JLabel NumOfPlLable;
    private javax.swing.JTextField NumOfPlTextF;
    private javax.swing.JLabel PassLabel;
    private javax.swing.JPasswordField PassTextF;
    private javax.swing.JLabel PortLabel;
    private javax.swing.JTextField PortTextF;
    private javax.swing.JLabel RoundTLabel;
    private javax.swing.JTextField RoundTTextF;
    private javax.swing.JButton StartButton;
    private javax.swing.JLabel TableSizeLabel;
    private javax.swing.JTextField TableSizeTextF;
    private javax.swing.JLabel logo;
    private javax.swing.JLabel background;
    
    // End of variables declaration                   
}

