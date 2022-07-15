/*  MageDinaPotha 1.0 is covered by the MIT License.
    Copyright 2015 Basura Ratnayake (basuraratnayake@protonmail.com), All Rights Reserved. */
package magedinapotha;

import java.awt.Color;
import javax.swing.JOptionPane;

public class AddTextEntry extends javax.swing.JFrame{   
        
    public AddTextEntry() {
        initComponents();
    }
    
    Core core = new Core();//Core Controls
    private String[] date;//Date of Diary Entry
    protected String filename = null;//Filename
    private boolean changeHappened = false;//If any change occurs    
    
    public AddTextEntry(String[] date){//Get the Date of the entry
        initComponents();///Initialize GUI Componenets
        
        core.centerFrame(this);//Center the Frame
        this.getContentPane().setBackground(Color.white);//Set BAckground Color to White
        
        this.date=date;//Initialize the date
        filename=core.generateRandomString();//Store Generated Random STring
        
        lblEntryDate.setText("       Date : "+this.date[0]+", "+this.date[1]+" "+this.date[2]+", "+this.date[3]);//Set Date
    }
    
    private void saveDataToFile(){//Save Diary Entry to file
        if(txtText.getText().length() > 10){//Check if atleast 10 characters are typed into the text box
            
            core.EstablishConnection();//Opens Database connection            
            if(core.insertInto("textEntries", "null,'"+date[4]+"','"+filename+"'"))//Insert record into the database
                
                if(core.writeTextFile("usr/entries/text/"+filename+".mdpe",txtText.getText())){//Write the data into the file
                    JOptionPane.showMessageDialog(null,"Dairy Entry Added");
                    
                    this.setVisible(false);//Hide the current JFrame
                
                    HomePage home = new HomePage();
                    home.setVisible(true);//Make Homepage Visible
                }
            core.CloseConnection();//Close Database connection
        }        
    }       
   
    private void countCharacters(){//Count Entered Character
        int count = txtText.getText().trim().length();//Get Length of Character
        lblWordCount.setText("Character Count : "+count+"     ");//Assign Character length
        if(txtText.getText() != "How was your day....?" || txtText.getText() != "")//If user typed something
            changeHappened=true;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblWordCount = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblEntryDate = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtText = new javax.swing.JTextPane();
        btnAddTEntry = new javax.swing.JButton();
        btnDecline = new javax.swing.JButton();
        btnAccept = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mage Dina Potha 1.0 - Record Text Entry");
        setPreferredSize(new java.awt.Dimension(625, 490));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblWordCount.setBackground(new java.awt.Color(0, 153, 204));
        lblWordCount.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblWordCount.setForeground(new java.awt.Color(255, 255, 255));
        lblWordCount.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblWordCount.setText("Character Count : 0     ");
        lblWordCount.setOpaque(true);
        getContentPane().add(lblWordCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 210, 30));

        jLabel1.setBackground(new java.awt.Color(22, 108, 143));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Record Text Entry");
        jLabel1.setOpaque(true);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 50));

        lblEntryDate.setBackground(new java.awt.Color(29, 131, 173));
        lblEntryDate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblEntryDate.setForeground(new java.awt.Color(255, 255, 255));
        lblEntryDate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEntryDate.setText("       Date : Sat, May 25, 2015");
        lblEntryDate.setOpaque(true);
        getContentPane().add(lblEntryDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 410, 30));

        txtText.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtText.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtText.setText("How was your day....?");
        txtText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTextKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(txtText);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 600, 320));

        btnAddTEntry.setBackground(new java.awt.Color(29, 131, 173));
        btnAddTEntry.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnAddTEntry.setForeground(new java.awt.Color(255, 255, 255));
        btnAddTEntry.setText("| T | Text Entry");
        btnAddTEntry.setBorderPainted(false);
        btnAddTEntry.setFocusable(false);
        getContentPane().add(btnAddTEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 150, 30));

        btnDecline.setBackground(new java.awt.Color(255, 0, 0));
        btnDecline.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnDecline.setForeground(new java.awt.Color(255, 255, 255));
        btnDecline.setText("|D| Discard Entry");
        btnDecline.setBorderPainted(false);
        btnDecline.setFocusable(false);
        btnDecline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeclineActionPerformed(evt);
            }
        });
        getContentPane().add(btnDecline, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 420, 150, 30));

        btnAccept.setBackground(new java.awt.Color(29, 131, 173));
        btnAccept.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnAccept.setForeground(new java.awt.Color(255, 255, 255));
        btnAccept.setText("|S| Save Entry");
        btnAccept.setBorderPainted(false);
        btnAccept.setFocusable(false);
        btnAccept.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAcceptActionPerformed(evt);
            }
        });
        getContentPane().add(btnAccept, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 420, 150, 30));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDeclineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeclineActionPerformed
        core.discardEntry(this);
    }//GEN-LAST:event_btnDeclineActionPerformed
    private void txtTextKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTextKeyPressed
        countCharacters();
    }//GEN-LAST:event_txtTextKeyPressed
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        core.discardEntry(this);
    }//GEN-LAST:event_formWindowClosing
    private void btnAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptActionPerformed
        saveDataToFile();
    }//GEN-LAST:event_btnAcceptActionPerformed

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
            java.util.logging.Logger.getLogger(AddTextEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddTextEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddTextEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddTextEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddTextEntry().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAccept;
    private javax.swing.JButton btnAddTEntry;
    private javax.swing.JButton btnDecline;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEntryDate;
    private javax.swing.JLabel lblWordCount;
    private javax.swing.JTextPane txtText;
    // End of variables declaration//GEN-END:variables
}
