package battleships.GUI;

/**
 *
 * @author Emanuilo
 */
public class FourthWindow extends javax.swing.JFrame {
	private String winner_name;
	
    public FourthWindow(String w) {
		winner_name=w;
        initComponents();
    }

    private void initComponents() {

        winnerLogo = new javax.swing.JLabel();
        picture = new javax.swing.JLabel();
        playerName = new javax.swing.JLabel();
        autor = new javax.swing.JLabel();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1048, 748));
        setMinimumSize(new java.awt.Dimension(1048, 748));
        setPreferredSize(new java.awt.Dimension(1048, 748));
        setSize(new java.awt.Dimension(1048, 748));
        getContentPane().setLayout(null);

        winnerLogo.setIcon(new javax.swing.ImageIcon("winner_logo.png")); // NOI18N
        getContentPane().add(winnerLogo);
        winnerLogo.setBounds(190, 40, 260, 210);

        
        picture.setIcon(new javax.swing.ImageIcon(winner_name+".png")); // NOI18N
        getContentPane().add(picture);
        picture.setBounds(510, 120, 64, 64);

        playerName.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        playerName.setForeground(new java.awt.Color(255, 255, 255));
        playerName.setText(winner_name);
        getContentPane().add(playerName);
        playerName.setBounds(610, 130, 310, 50);

        autor.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        autor.setForeground(new java.awt.Color(255, 255, 255));
        autor.setText("by: Emanuilo Jovanovic");
        getContentPane().add(autor);
        autor.setBounds(720, 650, 300, 50);

        background.setIcon(new javax.swing.ImageIcon("background 3.jpg")); // NOI18N
        getContentPane().add(background);
        background.setBounds(1, 0, 1050, 750);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>                        

    public static void entry(String name) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FourthWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FourthWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FourthWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FourthWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FourthWindow(name).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel autor;
    private javax.swing.JLabel background;
    private javax.swing.JLabel picture;
    private javax.swing.JLabel playerName;
    private javax.swing.JLabel winnerLogo;
    // End of variables declaration                   
}
