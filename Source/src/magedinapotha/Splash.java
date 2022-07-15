/*  MageDinaPotha 1.0 is covered by the MIT License.
    Copyright 2015 Basura Ratnayake (basuraratnayake@protonmail.com), All Rights Reserved. */
package magedinapotha;

public class Splash extends javax.swing.JFrame{        
    Core core = new Core();
    public Splash() {
        initComponents();
        core.centerFrame(this);//Center the Frame
        core.setThreadItem("Splash", true); //Set Thread Name 
        core.setSplashLabel(lblLoadingProgress,lblLoadingText,core,this);//Provide Splash Controls  
        core.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        SplashHolder = new javax.swing.JPanel();
        lblLoadingText = new javax.swing.JLabel();
        lblLoadingProgress = new javax.swing.JLabel();
        SplashImage = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MAGE DINA POTHA 1.0");
        setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
        setFocusTraversalPolicyProvider(true);
        setUndecorated(true);
        setResizable(false);
        setSize(new java.awt.Dimension(500, 250));

        SplashHolder.setPreferredSize(new java.awt.Dimension(500, 250));
        SplashHolder.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLoadingText.setForeground(new java.awt.Color(255, 255, 255));
        lblLoadingText.setText("Recorded Text Entries");
        SplashHolder.add(lblLoadingText, new org.netbeans.lib.awtextra.AbsoluteConstraints(96, 215, 370, 30));

        lblLoadingProgress.setBackground(new java.awt.Color(14, 79, 106));
        lblLoadingProgress.setForeground(new java.awt.Color(255, 255, 255));
        lblLoadingProgress.setMaximumSize(new java.awt.Dimension(411, 37));
        lblLoadingProgress.setOpaque(true);
        SplashHolder.add(lblLoadingProgress, new org.netbeans.lib.awtextra.AbsoluteConstraints(87, 212, 10, 37));

        SplashImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/magedinapotha/Splash.jpg"))); // NOI18N
        SplashHolder.add(SplashImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 250));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(SplashHolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(SplashHolder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Splash.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Splash().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel SplashHolder;
    private javax.swing.JLabel SplashImage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblLoadingProgress;
    private javax.swing.JLabel lblLoadingText;
    // End of variables declaration//GEN-END:variables
}
