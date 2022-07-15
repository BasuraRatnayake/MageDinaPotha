/*  MageDinaPotha 1.0 is covered by the MIT License.
    Copyright 2015 Basura Ratnayake (basuraratnayake@protonmail.com), All Rights Reserved. */
package magedinapotha;

import java.awt.Color;
import java.awt.Component;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class ViewTextEntry extends javax.swing.JFrame {

    Core core = new Core();
    public ViewTextEntry() {
        initComponents();
    }
    
    String date=null;
    public ViewTextEntry(String date) {
        initComponents();
        core.centerFrame(this);
        this.getContentPane().setBackground(Color.white);
        
        this.date=date;//Initialize Date
        getData();
        displayEntry();
    }
    private String dayName = "";
    protected String filename = null;//Store File Name
    protected String uCode = null;//Store unique code of the entry
    String[] dateBreak = new String[3];//Break Date into day,month,year
    private void getData(){//Get Data from the Database
        try {
            uCode=core.select("*", "textEntries", "logged = '"+date+"'").getString(1);//Get the Unique code
            filename=core.select("*", "textEntries", "logged = '"+date+"'").getString(3);//Get the filename            
           
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");//Format the Date to the format DD.MM.YYYY
            Date tDate=sdf.parse(date.replace("-", "/"));//Get Current Date
            
            sdf = new SimpleDateFormat("E");
            dayName=sdf.format(tDate);     
            
            dateBreak=date.split("-");          
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }          
        core.CloseConnection();
    }    
    private void displayEntry(){//Display the Data on the screen
        if(core.readTextFile("usr/entries/text/"+filename+".mdpe")){
            String[] nameOfMonths = new String[]{//Name of Months
                "January","February","March","April","May","June","July","August","September","October","November","December"
            };
            for(int i=0;i<core.getReadFileData().size();i++)//Read the file line by line
                txtText.setText(txtText.getText()+core.getReadFileData().get(i)+"\n");
            lblEntryDate.setText("       Date : "+dayName+", "+nameOfMonths[Integer.valueOf(dateBreak[1])]+" "+dateBreak[2]+", "+dateBreak[0]);
            lblWordCount.setText("Character Count : "+txtText.getText().length()+"     ");
        }
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
        setTitle("Mage Dina Potha 1.0 - View Text Entry");
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
        jLabel1.setText("Text Entry");
        jLabel1.setOpaque(true);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 620, 50));

        lblEntryDate.setBackground(new java.awt.Color(29, 131, 173));
        lblEntryDate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblEntryDate.setForeground(new java.awt.Color(255, 255, 255));
        lblEntryDate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEntryDate.setText("       Date : Sat, May 25, 2015");
        lblEntryDate.setOpaque(true);
        getContentPane().add(lblEntryDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 410, 30));

        txtText.setEditable(false);
        txtText.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        txtText.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtText.setToolTipText("");
        txtText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
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
        btnDecline.setText("|R| Remove Entry");
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
        btnAccept.setText("|H| Back to Home");
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
        int status = JOptionPane.showConfirmDialog((Component)null, "Do you really want to remove the recorded entry ?","Mage Dina Potha 1.0",JOptionPane.YES_NO_OPTION);
        HomePage home = new HomePage();
        if(status == 0){
            home.removeEntry(date);
            this.setVisible(false);
            home.setVisible(true);
        }
    }//GEN-LAST:event_btnDeclineActionPerformed

    private void btnAcceptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAcceptActionPerformed
        this.setVisible(false);
        HomePage home = new HomePage();
        home.setVisible(true);
    }//GEN-LAST:event_btnAcceptActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        this.setDefaultCloseOperation(0);
        this.setVisible(false);
        HomePage home = new HomePage();
        home.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(ViewTextEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewTextEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewTextEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewTextEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewTextEntry().setVisible(true);
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
