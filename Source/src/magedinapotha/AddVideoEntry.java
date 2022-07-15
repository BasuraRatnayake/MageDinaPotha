/*  MageDinaPotha 1.0 is covered by the MIT License.
    Copyright 2015 Basura Ratnayake (basuraratnayake@protonmail.com), All Rights Reserved. */
package magedinapotha;

import java.awt.Component;
import javax.swing.JOptionPane;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Dimension;
import java.awt.FlowLayout;

import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;
import com.xuggle.ferry.*;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.image.BufferedImage;
import java.io.File;

public class AddVideoEntry extends javax.swing.JFrame {
    public AddVideoEntry() {
        initComponents();
    }
    
    Core core = new Core();//Core Controls    
    private String[] date;//Date of Diary Entry
    protected String filename = null;//Filename
    private File videoFile = null;
    
    public AddVideoEntry(String[] date){
        initComponents();///Initialize GUI Componenets
        
        core.centerFrame(this);//Center the Frame
        this.getContentPane().setBackground(Color.white);//Set BAckground Color to White
        
        pnlRStart.setVisible(false);
        pnlStopR.setVisible(false);
        pnlSaveR.setVisible(false);
        
        this.date=date;//Initialize the date
        filename=core.generateRandomString();//Store Generated Random String
        
        lblEntryDate.setText("       Date : "+this.date[0]+", "+this.date[1]+" "+this.date[2]+", "+this.date[3]);//Set Date
        
        initWebCam(438,290);//Initialize Panel Size
        startCam();//Power up the web cam
        
        videoFile = new File("usr/entries/video/"+filename+".ts");//Assign File Path to FileName
    }
    private Dimension ds = null;
    private Dimension cs = null;
    private Webcam cam = null;
    private WebcamPanel camPanel = null;
    
    private void initWebCam(int width,int height){
        ds = new Dimension(width,height);//Get the Dimension
        cs =  WebcamResolution.VGA.getSize();//Get Webcam Dimension
        cam = Webcam.getDefault();
        camPanel = new WebcamPanel(cam, ds, false);//Initialize the camPanel
        
        cam.setViewSize(cs);//Set Resolution Webcam Size
        camPanel.setFillArea(true);
        
        pnlWebcam.setLayout(new FlowLayout());
        pnlWebcam.add(camPanel);
    }
    private void startCam(){//Start Webcam
        this.setCursor(Cursor.WAIT_CURSOR);
        Thread thread = new Thread(){
            public void run(){
                camPanel.start();//Starts preview camPanel         
                if(cam.isOpen() && !pnlStopR.isVisible())
                    pnlRStart.setVisible(true);
                else
                    pnlRStart.setVisible(false);
            }
        };
        thread.setDaemon(true);
        thread.start();
        this.setCursor(Cursor.DEFAULT_CURSOR);
    }
    private void stopCam(){//Stop Webcam
        camPanel.stop();
        cam.close();
    }    
    
    private void startRecording(){//Start Video Recording
        Thread thread = new Thread(){
            public void run(){                
                core.startVideoRecording(cam,cs,videoFile,lblWordCount);
            }
        };
        thread.setDaemon(true);
        thread.start();
        
        try{
            Thread.sleep(1000);
        }catch(InterruptedException ex){
           JOptionPane.showMessageDialog(null, ex.toString());
        }    
        pnlRStart.setVisible(false);
        pnlStopR.setVisible(true);
    }
    private void stopRecording(){//Stop Video Recording
        this.setCursor(Cursor.WAIT_CURSOR);//Set cursor to wait
        core.stopVideoRecording();//Stop the recording
        this.setCursor(Cursor.DEFAULT_CURSOR);
        
        pnlSaveR.setVisible(true);
        pnlStopR.setVisible(false);
    }
    
    private void saveDataToFile(){//Save Diary Entry to file
        core.EstablishConnection();//Opens Database connection
        if (core.insertInto("videoEntries", "null,'" + date[4] + "','" + filename + "'")) {//Add Audio Entry to Table      

            JOptionPane.showMessageDialog(null, "Dairy Entry Added");

            this.setVisible(false);

            HomePage home = new HomePage();
            home.setVisible(true);
        }
        core.CloseConnection();//Close Database connectionx
        stopCam();
    }
    
    private void discardEntry(){//Discard the entry and return to the homepage
        int status = JOptionPane.showConfirmDialog((Component)null, "Do you really want to discard the recorded entry ?","Mage Dina Potha 1.0",JOptionPane.YES_NO_OPTION);
        if(status == 0){            
            stopCam();
            if(core.isFilePresent(videoFile))
                core.deleteFile("usr/entries/video/"+filename+".ts");
            
            this.setVisible(false);
            HomePage home = new HomePage();
            home.setVisible(true); 
        }
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
        lblVL4 = new javax.swing.JLabel();
        lblVL3 = new javax.swing.JLabel();
        lblVL2 = new javax.swing.JLabel();
        lblVL1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlRStart = new javax.swing.JPanel();
        btnStartRecord = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        pnlWebcam = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mage Dina Potha 1.0 - Record Video Entry");
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
        jLabel1.setText("Record Video Entry");
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

        getContentPane().add(pnlSaveR, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 440, 90));

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

        getContentPane().add(pnlStopR, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 440, 90));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblVL4.setBackground(new java.awt.Color(255, 255, 255));
        lblVL4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204)));
        lblVL4.setOpaque(true);
        jPanel1.add(lblVL4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 25, 80, 10));

        lblVL3.setBackground(new java.awt.Color(255, 255, 255));
        lblVL3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204)));
        lblVL3.setOpaque(true);
        jPanel1.add(lblVL3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 60, 10));

        lblVL2.setBackground(new java.awt.Color(0, 153, 204));
        lblVL2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204)));
        lblVL2.setOpaque(true);
        jPanel1.add(lblVL2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 55, 40, 10));

        lblVL1.setBackground(new java.awt.Color(0, 153, 204));
        lblVL1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 204)));
        lblVL1.setOpaque(true);
        jPanel1.add(lblVL1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, 20, 10));

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Volume Level");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 120, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 330, 120, 120));

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

        getContentPane().add(pnlRStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 440, 90));

        pnlWebcam.setBackground(new java.awt.Color(0, 102, 153));
        getContentPane().add(pnlWebcam, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 82, 438, 290));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        saveDataToFile();
    }//GEN-LAST:event_btnSaveActionPerformed
    private void btnDiscardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiscardActionPerformed
        discardEntry();
    }//GEN-LAST:event_btnDiscardActionPerformed

    private void btnStopRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStopRecordActionPerformed
        stopRecording();
    }//GEN-LAST:event_btnStopRecordActionPerformed

    private void btnStartRecordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartRecordActionPerformed
        startRecording();
    }//GEN-LAST:event_btnStartRecordActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        int status = JOptionPane.showConfirmDialog((Component)null, "Do you really want to discard the recorded entry ?","Mage Dina Potha 1.0",JOptionPane.YES_NO_OPTION);
        if(status == 0){
            if(core.isFilePresent(videoFile)){
                core.deleteFile("usr/entries/video/"+filename+".ts");
            }
            
            this.setDefaultCloseOperation(0);
            this.setVisible(false);
            
            HomePage home = new HomePage();
            home.setVisible(true);
            stopCam();
        }
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
            java.util.logging.Logger.getLogger(AddVideoEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddVideoEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddVideoEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddVideoEntry.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddVideoEntry().setVisible(true);
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
    private javax.swing.JLabel lblVL2;
    private javax.swing.JLabel lblVL3;
    private javax.swing.JLabel lblVL4;
    private javax.swing.JLabel lblWordCount;
    private javax.swing.JPanel pnlRStart;
    private javax.swing.JPanel pnlSaveR;
    private javax.swing.JPanel pnlStopR;
    private javax.swing.JPanel pnlWebcam;
    // End of variables declaration//GEN-END:variables
}
