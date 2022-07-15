/*  
    MageDinaPotha 1.0 is covered by the MIT License.
    Copyright 2015 Basura Ratnayake (basuraratnayake@protonmail.com), All Rights Reserved.
*/
package magedinapotha;

import com.github.sarxos.webcam.Webcam;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Core extends Thread implements Database{    
    //Database Implementation - Start    
    private Connection connection = null;
    private Statement stmt = null;
    public ResultSet result = null;
    
    protected final String databaseName = "usr/storedEntries.db";//Database and Path
    
    public boolean EstablishConnection(){//Creates the database connection
        try {
            Class.forName("org.sqlite.JDBC").newInstance();//SQLite Class
            connection = DriverManager.getConnection("jdbc:sqlite:"+databaseName);//Database initialization
            stmt=connection.createStatement();//Establish Statement
            return true;
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString());            
        }catch(ClassNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex.toString());            
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString());            
        }
        return false;
    }    
    public void CloseConnection(){//Close the Database connection      
        try{
            if(result != null)//If result set is not empty
                result.close();//Close the opened result set
            stmt.close();//Close the Statement
            connection.close();//Close the database connection
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString());            
        }
    }
    
    public String getLoginTable(){//Login Details Table
        return 
            "CREATE TABLE IF NOT EXISTS loginD(" +
                "uniqCode            INTEGER PRIMARY KEY AUTOINCREMENT," +
                "password            VARCHAR2(20)    NOT NULL," +
                "secQuestion         VARCHAR2(20)    NOT NULL," +
                "answer              VARCHAR2(20)    NOT NULL," +
                "lastLogged          DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP"+
            ")";
    }
    public String getPersonalTable(){//Personal Details Table
        return 
            "CREATE TABLE IF NOT EXISTS personalD(" +
                "uniqCode		INTEGER PRIMARY KEY," +
                "fname			VARCHAR2(20)	NOT NULL," +
                "lname			VARCHAR2(20)	NOT NULL," +
                "dob			DATE		NOT NULL," +
                "country		VARCHAR2(20)	NOT NULL," +
                "sex			VARCHAR2(1)     DEFAULT '1'," +
                "email			VARCHAR2(40)	NOT NULL," +
                "mobile			VARCHAR2(20)	NOT NULL," +
                "phone			VARCHAR2(20)	NOT NULL," +
                "FOREIGN KEY (uniqCode) REFERENCES loginD (uniqCode)" +
            ")";
    }
    public String getTextEntries(){//Text Entry Table
        return 
            "CREATE TABLE IF NOT EXISTS textEntries(" +
                "uID			INTEGER PRIMARY KEY   AUTOINCREMENT," +
                "logged			DATE            NOT NULL," +
                "filename		VARCHAR2(10)	NOT NULL," +
                "UNIQUE (logged)" +
            ")";
    }
    public String getAudioEntries(){//Audio Entry Table
        return 
            "CREATE TABLE IF NOT EXISTS audioEntries(" +
                "uID			INTEGER PRIMARY KEY   AUTOINCREMENT," +
                "logged			DATE            NOT NULL," +
                "filename		VARCHAR2(10)	NOT NULL," +
                "UNIQUE (logged)" +
            ")";
    }
    public String getVideoEntries(){//Video Entry Table
        return 
            "CREATE TABLE IF NOT EXISTS videoEntries(" +
                "uID			INTEGER PRIMARY KEY   AUTOINCREMENT," +
                "logged			DATE            NOT NULL," +
                "filename		VARCHAR2(10)	NOT NULL," +
                "UNIQUE (logged)" +
            ")";
    }
    
    public boolean CreateCoreDatabase(){//Create the core tables of the database
        try {
            if(EstablishConnection()){//If connection established    
                updateQuery(getLoginTable());//Creates Personal Table         
                updateQuery(getPersonalTable());//Creates Personal Table
                updateQuery(getTextEntries());//Create Text Entry Table
                updateQuery(getAudioEntries());//Creates Audio Entry Table                
                updateQuery(getVideoEntries());//Create Video Entry Table  
                
                CloseConnection();//Close Database Connectio
                return true;
            }
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return false;
    }
    
    public ResultSet executeQuery(String query){//Execute Query and Return Result        
        if(EstablishConnection()){//If Connection Established
            try{            
                result=stmt.executeQuery(query);//Assign Result to Result Set
            }catch(SQLException ex){
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        }
        return result;
    }  
    public boolean updateQuery(String query){//Perform Update Statements
        try{            
            stmt.executeUpdate(query);//Execute Query
            return true;
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return false;
    }
    
    public boolean insertInto(String table,String columns,String values){//
        String query="INSERT INTO "+table+" ("+columns+") VALUES ("+values+")";
        if(updateQuery(query))//Execute Update
            return true;
        return false;
    }
    public boolean insertInto(String table,String values){
        String query="INSERT INTO "+table+" VALUES ("+values+")";
        if(updateQuery(query))//Execute Update
            return true;
        return false;
    }
    
    public ResultSet select(String table){//Get All Columns
        return executeQuery("SELECT * FROM "+table);
    }
    public ResultSet select(String columns,String table){//Get Specified Columns
        return executeQuery("SELECT "+columns+" FROM "+table);
    }
    public ResultSet select(String columns, String table, String where){//Get records according to specified condition;
        return executeQuery("SELECT "+columns+" FROM "+table+" WHERE "+where);
    }
    //Database Implementation - End
    
    //Core Functionality - Start
    public String stripLine(String line){//Break String
        String[] data = line.trim().split("=");//Break String by =
        return data[1].replace(" ", "");//Remove Whitespaces
    }
    
    private static final String string = "0123456789abcdefghijklmnopqrstuvwxyz";//A-Z and 0-9 Character String
    private Random random = new Random();//New Random Object
    public String generateRandomString(){//Generate a 8 character random string
        StringBuilder sb = new StringBuilder(8);//String Builder
        String text = null;
        for(int i=0;i<8;i++){//Loop for 8 times
            text=String.valueOf(string.charAt(random.nextInt(string.length())));//Obtain Random Character
            if(text.matches("[0-9]*") && i==0)//Check to see if the first character is not a number
                text=String.valueOf(string.charAt(random.nextInt(string.length())));//Obtain Random Character
           sb.append(text);//Add Character to the String builder
        }
        return sb.toString();//Return String Builder STring
    }
    //Core Functionality - End
    
    //Diary Entries - Start
    public final void discardEntry(JFrame frame){//Discard the entry and return to the homepage
        int status = JOptionPane.showConfirmDialog((Component)null, "Do you really want to discard the recorded entry ?","Mage Dina Potha 1.0",JOptionPane.YES_NO_OPTION);
        if(status == 0){//If Yes Clicked
            frame.setVisible(false);
            frame.setDefaultCloseOperation(0);//Disable normal close operation
            
            HomePage home = new HomePage();
            home.setVisible(true);            
        }
    }
    public final void closeForm(JFrame frame){//Close the Jframe
        frame.setVisible(false);//Hide the current JFrame
        frame.setDefaultCloseOperation(0);//Disable normal close operation
        
        HomePage home = new HomePage();
        home.setVisible(true);//Show Homepage
    } 
    //Diary Entries - Stop
    
    //Thread Code - Start
    private boolean[] makeThreadActive=new boolean [2];
    //0 - Splash Screen, 
    public void setThreadItem(String boolName,boolean value){//Determine Which Part of the thread to run
        switch(boolName){//Select Which thread to run
            case "Splash"://If the Thread is to be run in the splash screen
                makeThreadActive[0]=value;
                break;
            case "Audio"://If the Thread is to be run in the AddAudioEntry Screen
                makeThreadActive[1]=value;
                break;
        }
    }
    
    //Splash Screen - Start 
    private JLabel lblSplashProgress = new JLabel();
    private JLabel lblSplashText = new JLabel();
    private Core core;//Object of Core Itself
    private Splash splash;//Splash Object
    public void setSplashLabel(JLabel progress,JLabel text,Core c,Splash s){//Get the Frame, Core Object, and the Labels
        lblSplashProgress = progress;
        lblSplashText = text;
        core = c;
        splash = s;
    }
    private void setSplashProgress(){//Show Splash Screen Progress and Run Thread
        try{
            int width=0;//Width of the progress bar
            for(int i=0;i<40;i+=25){//Loop through the progress
                if(width> 390){//Stop the thread          
                    splash.setVisible(false);//close the splash screen
                    if(readTextFile("usr/settings.config")){//Reads Configuration File
                        if(stripLine(getReadFileData().get(0)).matches("1")){//If Application run first time       
                            FirstRun f = new FirstRun();
                            f.show();                            
                        }else{
                            SignIn l = new SignIn();
                            l.show();
                        }
                    }            
                    core.stop();//Stop the thread
                }
                
                width=lblSplashProgress.getWidth()+i;
                lblSplashProgress.setSize(width, 37); 
                
                Thread.sleep(250);//Sleep the thread for 250 miliseconds
                
                if(width < 40){//Show different loading sections
                    if(width>10 & width<30)
                        lblSplashText.setText("Retrieving Stored Encrypted Data");
                    else if(width>30 && width<50)
                        lblSplashText.setText("Loading Core Functions");
                    else if(width>50)
                        lblSplashText.setText("Loading Diary Entries");
                }   
            }
        }catch(InterruptedException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
    //Splash Screen - End

    public void run(){//Main Thread Run Function
        while(true){
            if(makeThreadActive[0]){//If Splash
                setSplashProgress();
            }
        }
    }    
    //Thread Code - End
    
    //IO Operations - Start
    private ArrayList<String> readFileData = new ArrayList<String>();//Data from Read File Stored Here
    
    public ArrayList<String> getReadFileData(){//Return the Read File Array List
        return readFileData;
    }
    public boolean isFilePresent(File file){//Check if file is available   
        if(file.exists())//If File Exists
            return true;
        return false;
    }    
    
    public boolean readTextFile(String filename){//Read Text File
        File file = new File(filename);//Assign filename and path to file
        BufferedReader bufferReader = null;//Reader
        
        readFileData.clear();//Empty the ArrayList
        
        try{
            if(isFilePresent(file)){//If File Present
                
                bufferReader = new BufferedReader(new FileReader(file));//Make Buffer Reader from file
                
                String line = "";
                
                while((line = bufferReader.readLine()) != null)//While read line is not empty
                    readFileData.add(line);//Add Line to the ArrayList
                
                bufferReader.close();//Close the Reader
                return true;
            }else
                file.createNewFile();//Create a new file if file not present          
        }catch(FileNotFoundException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }catch(IOException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return false;
    }
    public boolean appendTextFile(String filename,String data){//Append Data to File
        File file = new File(filename); //Assign filename and path to file
        try {         
            if(!isFilePresent(file))//check if file exists
                file.createNewFile();//Creates New File
            
            FileWriter writer = new FileWriter(file.getName(),true);//Writter
            BufferedWriter bufferWritter = new BufferedWriter(writer);//Buffer Reader    
            
    	    bufferWritter.write(data);//Append the Data            
    	    bufferWritter.close();//Close the buffer writer
            
            writer.close();//Close the file writer            
            return true;
        }catch(IOException ex) {            
            JOptionPane.showMessageDialog(null, ex.toString());
        }            
        return false;
    }
    public boolean writeTextFile(String filename,String data){//Write data to a new file
        File file = new File(filename); //Assign filename and path to file
        try {            
            FileWriter writer = new FileWriter(file);//Writer
            BufferedWriter bufferWriter = new BufferedWriter(writer);//Buffer Writer
            
            writer.write(data);//Write Data
            
            bufferWriter.close();
            writer.close();               
            return true;
        }catch(IOException ex) {
            JOptionPane.showMessageDialog(null, ex.toString());
        }             
        return false;
    }
    
    public boolean deleteFile(String filename){//Delete file
        File file = new File(filename);//Assign filename and path to file
        if(isFilePresent(file))//If File present
            if(file.delete())//Delete File
                return true;
        return false;
    }
    //IO Operations - End
    
    //Audio and Video Core - Start
    //Video - Start
    private boolean isVideoRunning;//Video Running
    public void startVideoRecording(Webcam cam,Dimension cs,File file,JLabel label){
        IMediaWriter writer = ToolFactory.makeWriter("usr/entries/video/"+file.getName());
        writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, cs.width, cs.height);

        long start = System.currentTimeMillis();
        
        int count = 0;
        
        isVideoRunning=true;
        while(isVideoRunning){
            try{                
                BufferedImage image = ConverterFactory.convertToType(cam.getImage(), BufferedImage.TYPE_3BYTE_BGR);//Make Image
                IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);//Assign Format
                
                IVideoPicture frame = converter.toPicture(image, (System.currentTimeMillis() - start) * 1000);//Get Current Frame
                frame.setKeyFrame(count == 0);//First Frame
                frame.setQuality(0);//Assign Quality
                
                writer.encodeVideo(0, frame);//Encode Images to a video
                
                count++;               
                
                label.setText(getAudioTime());
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, ex.toString());
            }
        }
        writer.close();//Close the Encoder
    }    
    public void stopVideoRecording(){//Stop Audio Recording
        isVideoRunning=false;//Stop the Running Thread
    }   
    //Video - End    
    
    //Auido - Start
    private static final int BUFFER_SIZE = 4096;//Buffer Size
    private ByteArrayOutputStream recordBytes;//Recorded Data
    private TargetDataLine audioLine;//Data Line
    private AudioFormat format;//Audio Format
 
    private boolean isAudioRunning;//Audio Running
    
    private int audioMiliSeoconds = 0;
    private int audioSeconds = 0;
    private int audioMinutes = 0;
    private int audioHours = 0;
    
    private String getAudioTime(){//Set Audio Record Duration
        if(audioMiliSeoconds > 59){
            audioSeconds++;
            audioMiliSeoconds=0;
        }
        
        if(audioSeconds > 59)
            audioMinutes++;
        
        if(audioMinutes > 59)
            audioHours++;
        
        audioMiliSeoconds+=8;
        return "Duration : "+audioHours+" : "+audioMinutes+" : "+audioSeconds+" : "+audioMiliSeoconds+"   ";//Set Duration
    }    
    
    //Calculate Audio Level - Start
    public float getAudioLevel(){//Return Audio Level of the Microphone
        return level;
    }
    private float level;
    final static float MAX_8_BITS_SIGNED = Byte.MAX_VALUE;
    final static float MAX_8_BITS_UNSIGNED = 0xff;
    final static float MAX_16_BITS_SIGNED = Short.MAX_VALUE;
    final static float MAX_16_BITS_UNSIGNED = 0xffff;
    private void calculateLevel (byte[] buffer, int readPoint, int leftOver) {
        //Function taken from http://stackoverflow.com/questions/5800649/detect-silence-when-recording
        //Copyright and Ownership belongs to http://stackoverflow.com/users/617271/olyanren
        int max = 0;
        boolean use16Bit = (getAudioFormat().getSampleSizeInBits() == 16);
        boolean signed = (getAudioFormat().getEncoding() ==
                          AudioFormat.Encoding.PCM_SIGNED);
        boolean bigEndian = (getAudioFormat().isBigEndian());
        if (use16Bit) {
            for (int i=readPoint; i<buffer.length-leftOver; i+=2) {
                int value = 0;
                // deal with endianness
                int hiByte = (bigEndian ? buffer[i] : buffer[i+1]);
                int loByte = (bigEndian ? buffer[i+1] : buffer [i]);
                if (signed) {
                    short shortVal = (short) hiByte;
                    shortVal = (short) ((shortVal << 8) | (byte) loByte);
                    value = shortVal;
                } else {
                    value = (hiByte << 8) | loByte;
                }
                max = Math.max(max, value);
            } // for
        } else {
            // 8 bit - no endianness issues, just sign
            for (int i=readPoint; i<buffer.length-leftOver; i++) {
                int value = 0;
                if (signed) {
                    value = buffer [i];
                } else {
                    short shortVal = 0;
                    shortVal = (short) (shortVal | buffer [i]);
                    value = shortVal;
                }
                max = Math.max (max, value);
            } // for
        } // 8 bit
        // express max as float of 0.0 to 1.0 of max value
        // of 8 or 16 bits (signed or unsigned)
        if (signed) {
            if (use16Bit) { level = (float) max / MAX_16_BITS_SIGNED; }
            else { level = (float) max / MAX_8_BITS_SIGNED; }
        } else {
            if (use16Bit) { level = (float) max / MAX_16_BITS_UNSIGNED; }
            else { level = (float) max / MAX_8_BITS_UNSIGNED; }
        }
    }
    //Calculate Audio Level - End    
    
    private void setAudioRunning(boolean run){//Set Running
        isAudioRunning=run;
    }
    private boolean getAudioRunning(){//Get Audio Running
        return isAudioRunning;
    }
    private final AudioFormat getAudioFormat(){//WAV File Format
        float sampleRate = 16000;
        int sampleSizeInBits = 8;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
    }
    
    private void setAudioLevel(JLabel label, double aLevel){//Set Audio Level Labels
        if(getAudioLevel() >= aLevel)//if Microphone volume 
            label.setBackground(new Color(0,153,204));
        else
            label.setBackground(new Color(255,255,255)); 
    }
    public void startAudioRecording(JLabel label,JLabel[] lView){
        try{
            format = getAudioFormat();//Assign Audio Format
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);//Get Data Line            
            
            if(!AudioSystem.isLineSupported(info))//checks if system supports the data line
                JOptionPane.showMessageDialog(null, "Your Recording Device is Not Supported");
            
            audioLine = AudioSystem.getTargetDataLine(format);
            
            audioLine.open(format);//Open Audio line with specified audio format
            audioLine.start();//Open the Microphone
            
            byte[] buffer = new byte[BUFFER_SIZE];//New buffer array
            int bytesRead = 0;
            
            recordBytes = new ByteArrayOutputStream();
            
            setAudioRunning(true);            
            while(getAudioRunning()){//While stopped not clicked
                bytesRead = audioLine.read(buffer, 0, buffer.length);
                recordBytes.write(buffer, 0, bytesRead);//Assign Read bytes to the byte array
                
                calculateLevel(buffer,0,0);//Calculate Levels          
                setAudioLevel(lView[0], 0.09);//Level Low
                setAudioLevel(lView[1], 0.1);//Level 1
                setAudioLevel(lView[2], 0.11);//Level 2
                setAudioLevel(lView[3], 0.125);//Level 3
                setAudioLevel(lView[4], 0.142);//Level 4
                setAudioLevel(lView[5], 0.166);//Level Normal
                setAudioLevel(lView[6], 0.2);//Level 6
                setAudioLevel(lView[7], 0.25);//Level 7
                setAudioLevel(lView[8], 0.33);//Level 8
                setAudioLevel(lView[9], 0.5);//Level 9
                setAudioLevel(lView[10], 1);//Level High
                
                label.setText(getAudioTime());//Set Audio Record Time
            }
        }catch(LineUnavailableException ex){
            JOptionPane.showMessageDialog(null, ex+": Your Recording Device is Not Supported");
        }
    }    
    public void stopAudioRecording(){//Stop Audio Recording
        setAudioRunning(false);//Stop the Running Thread
         
        if(audioLine != null){//Close the Audio Lines
            audioLine.drain();
            audioLine.close();
        }
    }
    public boolean saveAudioFile(File wavFile){//Save the Recorded Audio File
        try{
            byte[] audioData = recordBytes.toByteArray();//Get Recorded Data
            ByteArrayInputStream aInputStream = new ByteArrayInputStream(audioData);//Get Byte Input Array
            AudioInputStream audioInputStream = new AudioInputStream(aInputStream, format,audioData.length / format.getFrameSize());
            
            AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, wavFile);//Assign Byte array to the stream
            
            audioInputStream.close();
            recordBytes.close();
            return true;
        }catch (IOException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return false;
    }
    
    private Clip clip = null;//Audio Clip
    public void playAudioFile(String filename, JLabel[] lView){//Initialize Audio File
        try {
            File audioFile = new File(filename);//Initialize Filename
            AudioInputStream aiStream;//Audio Ream Stream
            DataLine.Info line;
            byte[] buffer = new byte[BUFFER_SIZE];//New Byte Array

            aiStream = AudioSystem.getAudioInputStream(audioFile);//Get Stream from file
            format = aiStream.getFormat();//Get Format from stream
            line = new DataLine.Info(Clip.class, format);

            double durationInSeconds = (aiStream.getFrameLength() + 0.0) / format.getFrameRate();

            aiStream.read(buffer);
            calculateLevel(buffer, 0, 0);
            
            lView[11].setText("Seconds: " + (int) durationInSeconds + "     ");
            setAudioLevel(lView[0], 0.09);//Level Low
            setAudioLevel(lView[1], 0.1);//Level 1
            setAudioLevel(lView[2], 0.11);//Level 2
            setAudioLevel(lView[3], 0.125);//Level 3
            setAudioLevel(lView[4], 0.142);//Level 4
            setAudioLevel(lView[5], 0.166);//Level Normal
            setAudioLevel(lView[6], 0.2);//Level 6
            setAudioLevel(lView[7], 0.25);//Level 7
            setAudioLevel(lView[8], 0.33);//Level 8
            setAudioLevel(lView[9], 0.5);//Level 9
            setAudioLevel(lView[10], 1);//Level High

            clip = (Clip) AudioSystem.getLine(line);
            clip.open(aiStream);
        }catch (UnsupportedAudioFileException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }catch (IOException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }catch (LineUnavailableException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
    public void startAudio(){//Play the Audio File
        clip.start();
    }
    public void stopAudio(){//Stop the Playing Audio File
        clip.stop();
    }
    //Audio - End
    //Audio and Video Core - End
    
    //Look and Feel - Start
    public void centerFrame(JFrame frame){//Center JFrame
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xAxis = screenSize.width/2-frame.getSize().width/2;//Get X Axis
        int yAxis = screenSize.height/2-frame.getSize().height/2;//Get Y Axis
        frame.setLocation(xAxis, yAxis);//Set Frame Location from new X and Y axis
    }
    //Look and Feel - End
}