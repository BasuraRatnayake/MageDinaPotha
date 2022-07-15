/*  MageDinaPotha 1.0 is covered by the MIT License.
    Copyright 2015 Basura Ratnayake (basuraratnayake@protonmail.com), All Rights Reserved. */
package magedinapotha;

import java.awt.Color;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ViewAudioEntry extends javax.swing.JFrame {

    Core core = new Core();
    public ViewAudioEntry() {
        initComponents();
    }
    
    private String date=null;
    public ViewAudioEntry(String date){
        initComponents();
        core.centerFrame(this);
        this.getContentPane().setBackground(Color.white);
        
        this.date=date;//Initialize Date
        getData();
        
        levels = new JLabel[]{
            lblVL1,lblVL2,lblVL3,lblVL4,lblVL5,lblVL6,lblVL7,lblVL8,lblVL9,lblVL10,lblVL11,lblWordCount
        };
        btnStartRecord1.setVisible(false);
        displayEntry();
    }
    
    private JLabel[] levels = null;
    
    private String dayName="";
    protected String filename=null;//Store File Name
    protected String uCode=null;//Store unique code of the entry
    String[] dateBreak = new String[3];//Break Date into day,month,year
    private void getData(){//Get Data from the Database
        try{
            uCode=core.select("*", "audioEntries", "logged = '"+date+"'").getString(1);//Get the Unique code
            filename=core.select("*", "audioEntries", "logged = '"+date+"'").getString(3);//Get the filename            
           
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");//Format the Date to the format DD.MM.YYYY
            Date tDate=sdf.parse(date.replace("-", "/"));//Get Current Date
            
            sdf = new SimpleDateFormat("E");
            dayName=sdf.format(tDate);     
            
            dateBreak=date.split("-");          
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }catch (ParseException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }          
        core.CloseConnection();
    }    
    public void displayEntry() {//Display the Data on the screen
        String[] nameOfMonths = new String[]{//Name of Months
            "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
        };
        lblEntryDate.setText("       Date : " + dayName + ", " + nameOfMonths[Integer.valueOf(dateBreak[1])] + " " + dateBreak[2] + ", " + dateBreak[0]);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblWordCount = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblEntryDate = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblVL11 = new javax.swing.JLabel();
        lblVL10 = new javax.swing.JLabel();
        lblVL9 = new javax.swing.JLabel();
        lblVL8 = new javax.swing.JLabel();
        lblVL7 = new javax.swing.JLabel();
        lblVL6 = new javax.swing.JLabel();
        lblVL5 = new javax.swing.JLabel();
        lblVL4 = new javax.swing.JLabel();
        lblVL3 = new javax.swing.JLabel();
        lblVL2 = new javax.swing.JLabel();
        lblVL1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlRStart = new javax.swing.JPanel();
        btnStartRecord1 = new javax.swing.JButton();
        btnStartRecord = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mage Dina Potha 1.0 - View Audio Entry");
        setPreferredSize(new java.awt.Dimension(445, 458));
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
        lblWordCount.setOpaque(true);
        getContentPane().add(lblWordCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 180, 30));

        jLabel1.setBackground(new java.awt.Color(22, 108, 143));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Audio Entry");
        jLabel1.setOpaque(true);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 50));

        lblEntryDate.setBackground(new java.awt.Color(29, 131, 173));
        lblEntryDate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblEntryDate.setForeground(new java.awt.Color(255, 255, 255));
        lblEntryDate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEntryDate.setText("       Date : Sat, May 25, 2015");
        lblEntryDate.setOpaque(true);
        getContentPane().add(lblEntryDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 260, 30));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblVL11.setBackground(new java.awt.Color(255, 255, 255));
        lblVL11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204)));
        lblVL11.setOpaque(true);
        jPanel1.add(lblVL11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 240, 10));

        lblVL10.setBackground(new java.awt.Color(255, 255, 255));
        lblVL10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204)));
        lblVL10.setOpaque(true);
        jPanel1.add(lblVL10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 25, 220, 10));

        lblVL9.setBackground(new java.awt.Color(255, 255, 255));
        lblVL9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204)));
        lblVL9.setOpaque(true);
        jPanel1.add(lblVL9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 200, 10));

        lblVL8.setBackground(new java.awt.Color(255, 255, 255));
        lblVL8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204)));
        lblVL8.setOpaque(true);
        jPanel1.add(lblVL8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 55, 180, 10));

        lblVL7.setBackground(new java.awt.Color(255, 255, 255));
        lblVL7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204)));
        lblVL7.setOpaque(true);
        jPanel1.add(lblVL7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 160, 10));

        lblVL6.setBackground(new java.awt.Color(255, 255, 255));
        lblVL6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204)));
        lblVL6.setOpaque(true);
        jPanel1.add(lblVL6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 85, 140, 10));

        lblVL5.setBackground(new java.awt.Color(255, 255, 255));
        lblVL5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204)));
        lblVL5.setOpaque(true);
        jPanel1.add(lblVL5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 120, 10));

        lblVL4.setBackground(new java.awt.Color(255, 255, 255));
        lblVL4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204)));
        lblVL4.setOpaque(true);
        jPanel1.add(lblVL4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 115, 100, 10));

        lblVL3.setBackground(new java.awt.Color(255, 255, 255));
        lblVL3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204)));
        lblVL3.setOpaque(true);
        jPanel1.add(lblVL3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 80, 10));

        lblVL2.setBackground(new java.awt.Color(255, 255, 255));
        lblVL2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204)));
        lblVL2.setOpaque(true);
        jPanel1.add(lblVL2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 145, 60, 10));

        lblVL1.setBackground(new java.awt.Color(255, 255, 255));
        lblVL1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204)));
        lblVL1.setOpaque(true);
        jPanel1.add(lblVL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 160, 40, 10));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Volume Level");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, 260, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 260, 200));

        pnlRStart.setBackground(new java.awt.Color(255, 255, 255));
        pnlRStart.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnStartRecord1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/magedinapotha/btnAudioStop.png"))); // NOI18N
        btnStartRecord1.setFocusable(false);
        btnStartRecord1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartRecord1ActionPerformed(evt);
            }
        });
        pnlRStart.add(btnStartRecord1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 100, 70));

        btnStartRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/magedinapotha/btnPlay.png"))); // NOI18N
        btnStartRecord.setFocusable(false);
        btnStartRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartRecordActionPerformed(evt);
            }
        });
        pnlRStart.add(btnStartRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 100, 70));

        getContentPane().add(pnlRStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 440, 90));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartRecordActionPerformed
        core.playAudioFile("usr/entries/audio/"+filename+".wav",levels);
        core.startAudio();
        
        btnStartRecord.setVisible(false);
        btnStartRecord1.setVisible(true);
    }//GEN-LAST:event_btnStartRecordActionPerformed

    private void btnStartRecord1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartRecord1ActionPerformed
        for(int i=0;i<levels.length-1;i++)
            levels[i].setBackground(Color.white);
        
        core.stopAudio();
        
        btnStartRecord.setVisible(true);
        btnStartRecord1.setVisible(false);
    }//GEN-LAST:event_btnStartRecord1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try{
            core.stopAudio();
        }catch(Exception ex){}
        
        this.setDefaultCloseOperation(0);
        this.setVisible(false);
        
        HomePage home = new HomePage();
        home.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

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
            java.util.logging.Logger.getLogger(ViewAudioEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewAudioEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewAudioEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewAudioEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewAudioEntry().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStartRecord;
    private javax.swing.JButton btnStartRecord1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblEntryDate;
    private javax.swing.JLabel lblVL1;
    private javax.swing.JLabel lblVL10;
    private javax.swing.JLabel lblVL11;
    private javax.swing.JLabel lblVL2;
    private javax.swing.JLabel lblVL3;
    private javax.swing.JLabel lblVL4;
    private javax.swing.JLabel lblVL5;
    private javax.swing.JLabel lblVL6;
    private javax.swing.JLabel lblVL7;
    private javax.swing.JLabel lblVL8;
    private javax.swing.JLabel lblVL9;
    private javax.swing.JLabel lblWordCount;
    private javax.swing.JPanel pnlRStart;
    // End of variables declaration//GEN-END:variables
}
