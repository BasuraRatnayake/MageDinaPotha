/*  MageDinaPotha 1.0 is covered by the MIT License.
    Copyright 2015 Basura Ratnayake (basuraratnayake@protonmail.com), All Rights Reserved. */
package magedinapotha;

import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class SignIn extends javax.swing.JFrame {     
    
    Core core = new Core();
    
    public SignIn() {
        initComponents();
        
        core.centerFrame(this);//Center the Frame
        this.getContentPane().setBackground(Color.white);
        
        getLoginDetails();        
    }
    
    protected String password;//Store Password
    private ArrayList<String> configuration = new ArrayList<String>();//Configuration File Data
    protected void getLoginDetails(){//Get Necessary Login Details
        try{
            background=txtPassword.getBackground();
            password=core.select("loginD").getString(2);//Get Password from the Database
            if(core.readTextFile("usr/settings.config"))//Reads Configuration File
                configuration=core.getReadFileData();//Initialize the configuration list                     
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString()); 
        }    
        core.CloseConnection();
    }
    
    private boolean rememberMe=false;//Remember me checkbox value
    private Color background=null;
    private void confirmLogin(){
        if(password.matches(txtPassword.getText())){  
            if(configuration.size() < 2)
                configuration.add(1, "Remember = 0");
            if(rememberMe){                
                configuration.set(1, "Remember = 1");
            }
            String data="";
            for(int i=0;i<configuration.size();i++)
                data+=configuration.get(i)+"\n";
            if(core.writeTextFile("usr/settings.config",data)){//Write Data Back to the file  
                this.setVisible(false);
                HomePage hp = new HomePage();
                hp.show();
            }
        }else{
            txtPassword.setBackground(Color.red);
            txtPassword.setForeground(Color.white);
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        chbRemember = new javax.swing.JCheckBox();
        btnSignIn = new javax.swing.JButton();
        SplashImage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mage Dina Potha 1.0 - Log In");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPassword.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblPassword.setText("Password");
        getContentPane().add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 190, -1, -1));

        txtPassword.setBackground(new java.awt.Color(29, 131, 173));
        txtPassword.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        txtPassword.setForeground(new java.awt.Color(255, 255, 255));
        txtPassword.setEchoChar('X');
        getContentPane().add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 180, 229, 36));

        chbRemember.setBackground(new java.awt.Color(255, 255, 255));
        chbRemember.setText("Remember password for this computer");
        chbRemember.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chbRememberMouseClicked(evt);
            }
        });
        getContentPane().add(chbRemember, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 240, -1, -1));

        btnSignIn.setBackground(new java.awt.Color(22, 108, 143));
        btnSignIn.setForeground(new java.awt.Color(255, 255, 255));
        btnSignIn.setText("Log In");
        btnSignIn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSignInActionPerformed(evt);
            }
        });
        getContentPane().add(btnSignIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 230, 89, 38));

        SplashImage.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        SplashImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/magedinapotha/Splash.jpg"))); // NOI18N
        getContentPane().add(SplashImage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 170));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSignInActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSignInActionPerformed
        confirmLogin();
    }//GEN-LAST:event_btnSignInActionPerformed

    private void chbRememberMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chbRememberMouseClicked
        rememberMe=chbRemember.isSelected();
    }//GEN-LAST:event_chbRememberMouseClicked

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
            java.util.logging.Logger.getLogger(SignIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SignIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SignIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SignIn.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignIn().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel SplashImage;
    private javax.swing.JButton btnSignIn;
    private javax.swing.JCheckBox chbRemember;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}
