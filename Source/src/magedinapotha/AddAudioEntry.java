/*  MageDinaPotha 1.0 is covered by the MIT License.
    Copyright 2015 Basura Ratnayake (basuraratnayake@protonmail.com), All Rights Reserved. */
package magedinapotha;

import java.awt.Color;
import java.awt.Cursor;
import javax.swing.JOptionPane;
import java.io.*;
import javax.swing.JLabel;

public class AddAudioEntry extends javax.swing.JFrame {
    
    public AddAudioEntry(){
        initComponents();
    }
    
    Core core = new Core();//Core Controls  
    
    private String[] date;//Date of Diary Entry
    protected String filename = null;//Filename
    private File audioFile = null;//Actual Audio File
    
    private JLabel[] levels = null;//Microphone Sound Levels
    
    private final int refreshTime = 1000;//Refresh Rate 1 Second
    
    public AddAudioEntry(String[] date){//Get the Date of the entry
        initComponents();///Initialize GUI Componenets
        
        core.centerFrame(this);//Center the Frame
        this.getContentPane().setBackground(Color.white);//Set BAckground Color to White
        
        pnlSaveR.setVisible(false);//Set Save Panel Invisible
        pnlStopR.setVisible(false);//Set Stop Panel Invisible
        
        levels = new JLabel[]{//Initialize the sound level labels
            lblVL1,lblVL2,lblVL3,lblVL4,lblVL5,lblVL6,lblVL7,lblVL8,lblVL9,lblVL10,lblVL11
        };
        
        this.date=date;//Initialize the date
        filename=core.generateRandomString();//Store Generated Random STring
        
        lblEntryDate.setText("       Date : "+this.date[0]+", "+this.date[1]+" "+this.date[2]+", "+this.date[3]);//Set Date
    }
    
    private void saveDataToFile(){//Save Diary Entry to file
        core.EstablishConnection();//Opens Database connection
        if (core.insertInto("audioEntries", "null,'" + date[4] + "','" + filename + "'")){//Add Audio Entry to Table   
            
            filename = "usr/entries/audio/" + filename;//Assign File Path to FileName
            audioFile = new File(filename+".wav");//Make New File
            
            if(core.saveAudioFile(audioFile)){//Create Audio File
                JOptionPane.showMessageDialog(null, "Dairy Entry Added");
                
                this.setVisible(false);//Hide the current JFrame
                
                HomePage home = new HomePage();
                home.setVisible(true);//Make Homepage Visible
            }
        }
        core.CloseConnection();//Close Database connectionx
    }
    
    private void startRecording(){//Start Audio Recording
        Thread recordThread = new Thread(new Runnable(){//Declare the thread
            public void run(){
                core.startAudioRecording(lblWordCount,levels);//Run Start Recording function
            }
        });         
        recordThread.start();//Start the thread
        
        pnlRStart.setVisible(false);//Hide Start Panel
        pnlStopR.setVisible(true);//Show Stop Panel
        
        try{
            Thread.sleep(refreshTime);//Set a sleep time
        }catch(InterruptedException ex){
           JOptionPane.showMessageDialog(null, ex.toString());
        } 
    }
    private void stopRecording(){//Stop Audio Recording
        this.setCursor(Cursor.WAIT_CURSOR);//Set Cursor to Wait
        core.stopAudioRecording();
        
        this.setCursor(Cursor.DEFAULT_CURSOR);//Set Cursor to Normal
        
        pnlSaveR.setVisible(true);//Show Save Panel
        pnlStopR.setVisible(false);//Hide Stop Panel
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblWordCount = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblEntryDate = new javax.swing.JLabel();
        pnlSaveR = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnDiscard = new javax.swing.JButton();
        pnlStopR = new javax.swing.JPanel();
        btnStopRecord = new javax.swing.JButton();
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
        btnStartRecord = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mage Dina Potha 1.0 - Record Audio Entry");
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
        jLabel1.setText("Record Audio Entry");
        jLabel1.setOpaque(true);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 440, 50));

        lblEntryDate.setBackground(new java.awt.Color(29, 131, 173));
        lblEntryDate.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        lblEntryDate.setForeground(new java.awt.Color(255, 255, 255));
        lblEntryDate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblEntryDate.setText("       Date : Sat, May 25, 2015");
        lblEntryDate.setOpaque(true);
        getContentPane().add(lblEntryDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 260, 30));

        pnlSaveR.setBackground(new java.awt.Color(255, 255, 255));
        pnlSaveR.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSave.setBackground(new java.awt.Color(0, 153, 204));
        btnSave.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        btnSave.setForeground(new java.awt.Color(255, 255, 255));
        btnSave.setText("<html><body>\n<center>Save Recorded Entry</center>\n</body></html>");
        btnSave.setBorderPainted(false);
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        pnlSaveR.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 100, 70));

        btnDiscard.setBackground(new java.awt.Color(255, 0, 0));
        btnDiscard.setFont(new java.awt.Font("Arial", 1, 11)); // NOI18N
        btnDiscard.setForeground(new java.awt.Color(255, 255, 255));
        btnDiscard.setText("<html><body>\n<center>Discard Recorded Entry</center>\n</body></html>");
        btnDiscard.setBorderPainted(false);
        btnDiscard.setFocusable(false);
        btnDiscard.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiscardActionPerformed(evt);
            }
        });
        pnlSaveR.add(btnDiscard, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, 70));

        getContentPane().add(pnlSaveR, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 440, 90));

        pnlStopR.setBackground(new java.awt.Color(255, 255, 255));
        pnlStopR.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnStopRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/magedinapotha/btnStop.png"))); // NOI18N
        btnStopRecord.setFocusable(false);
        btnStopRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStopRecordActionPerformed(evt);
            }
        });
        pnlStopR.add(btnStopRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 100, 70));

        getContentPane().add(pnlStopR, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 440, 90));

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

        btnStartRecord.setIcon(new javax.swing.ImageIcon(getClass().getResource("/magedinapotha/btnStart.png"))); // NOI18N
        btnStartRecord.setFocusable(false);
        btnStartRecord.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartRecordActionPerformed(evt);
            }
        });
        pnlRStart.add(btnStartRecord, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 100, 70));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("<html>\n<body>Record the details of your day by clicking <br/> the <strong>'Start Recording'</strong> button.\n</body>");
        jLabel3.setFocusable(false);
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        pnlRStart.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 0, 300, 90));

        getContentPane().add(pnlRStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 440, 90));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStopRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopRecordActionPerformed
        stopRecording();
    }//GEN-LAST:event_btnStopRecordActionPerformed
    private void btnStartRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartRecordActionPerformed
        startRecording();
    }//GEN-LAST:event_btnStartRecordActionPerformed
    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveDataToFile();
    }//GEN-LAST:event_btnSaveActionPerformed
    private void btnDiscardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiscardActionPerformed
        core.discardEntry(this);
    }//GEN-LAST:event_btnDiscardActionPerformed
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        core.closeForm(this);
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
            java.util.logging.Logger.getLogger(AddAudioEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddAudioEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddAudioEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddAudioEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddAudioEntry().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDiscard;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnStartRecord;
    private javax.swing.JButton btnStopRecord;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
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
    private javax.swing.JPanel pnlSaveR;
    private javax.swing.JPanel pnlStopR;
    // End of variables declaration//GEN-END:variables
}