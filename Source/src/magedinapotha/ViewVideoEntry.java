/*  MageDinaPotha 1.0 is covered by the MIT License.
    Copyright 2015 Basura Ratnayake (basuraratnayake@protonmail.com), All Rights Reserved. */
package magedinapotha;

import java.awt.Color;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;


public class ViewVideoEntry extends javax.swing.JFrame {
    Core core = new Core();
    public ViewVideoEntry() {
        initComponents();
    }
    
    String date=null;
    public ViewVideoEntry(String date) {
        initComponents();
        
        core.centerFrame(this);
        this.getContentPane().setBackground(Color.white);
        
        this.date=date;//Initialize Date
        getData();        
        displayEntry();
    }
    private String dayName="";
    protected String filename=null;//Store File Name
    protected String uCode=null;//Store unique code of the entry
    String[] dateBreak = new String[3];//Break Date into day,month,year
    private void getData(){//Get Data from the Database
        try{
            uCode=core.select("*", "videoEntries", "logged = '"+date+"'").getString(1);//Get the Unique code
            filename=core.select("*", "videoEntries", "logged = '"+date+"'").getString(3);//Get the filename            
           
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
    private void displayEntry() {//Display the Data on the screen
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
        pnlRStart = new javax.swing.JPanel();
        btnStartRecord = new javax.swing.JButton();
        pnlWebcam = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mage Dina Potha 1.0 - View Video Entry");
        setPreferredSize(new java.awt.Dimension(446, 502));
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
        lblWordCount.setText("Duration : 0:0:0     ");
        lblWordCount.setOpaque(true);
        getContentPane().add(lblWordCount, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 50, 180, 30));

        jLabel1.setBackground(new java.awt.Color(22, 108, 143));
        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Video Entry");
        jLabel1.setOpaque(true);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 50));

        lblEntryDate.setBackground(new java.awt.Color(29, 131, 173));
        lblEntryDate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblEntryDate.setForeground(new java.awt.Color(255, 255, 255));
        lblEntryDate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEntryDate.setText("       Date : Sat, May 25, 2015");
        lblEntryDate.setOpaque(true);
        getContentPane().add(lblEntryDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 260, 30));

        pnlRStart.setBackground(new java.awt.Color(255, 255, 255));
        pnlRStart.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnStartRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/magedinapotha/btnPlay.png"))); // NOI18N
        btnStartRecord.setFocusable(false);
        btnStartRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartRecordActionPerformed(evt);
            }
        });
        pnlRStart.add(btnStartRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 100, 70));

        getContentPane().add(pnlRStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 440, 90));

        pnlWebcam.setBackground(new java.awt.Color(0, 102, 153));
        getContentPane().add(pnlWebcam, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 82, 438, 290));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try{
            core.stopAudio();
        }catch(Exception ex){}
        
        this.setDefaultCloseOperation(0);
        this.setVisible(false);
        
        HomePage home = new HomePage();
        home.setVisible(true);
    }//GEN-LAST:event_formWindowClosing

    private void btnStartRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartRecordActionPerformed
        try{
            Process p1 = Runtime.getRuntime().exec("/C:/Program Files (x86)/VideoLAN/VLC/vlc.exe"+" usr/entries/video/"+filename+".ts");
            this.setVisible(false);
            if(p1.waitFor() == 0){
                this.setVisible(true);
            }
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        } catch (InterruptedException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }//GEN-LAST:event_btnStartRecordActionPerformed

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
            java.util.logging.Logger.getLogger(ViewVideoEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewVideoEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewVideoEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewVideoEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewVideoEntry().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStartRecord;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lblEntryDate;
    private javax.swing.JLabel lblWordCount;
    private javax.swing.JPanel pnlRStart;
    private javax.swing.JPanel pnlWebcam;
    // End of variables declaration//GEN-END:variables
}
