/*  MageDinaPotha 1.0 is covered by the MIT License.
    Copyright 2015 Basura Ratnayake (basuraratnayake@protonmail.com), All Rights Reserved. */
package magedinapotha;

import java.awt.Color;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class HomePage extends javax.swing.JFrame {
    
    Core core = new Core();       
    
    public HomePage() {
        initComponents();
        initCalender();
        this.getContentPane().setBackground(Color.white);
        core.centerFrame(this);
        pnlAddEntry.setVisible(false);
        pnlViewEntry.setVisible(false);
        
        plotCalender(currentDate[0],currentDate[1]-1,currentDate[2]);//plot the calender
    }
    
    //Calender Controls - Start
    private JLabel[] days;//Day Labels 
    private JLabel clickedLabel = null;//Creates a sample label
    
    private String[] dayNames;//Names of the days
    private String[] selectedDate = new String [5];//Stores Selected Day and Day Name    
    private String[] nameOfMonths = new String [12];//Name of the Months
    
    private Date date;//Get Current Date
    private SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");//Format the Date to the format DD.MM.YYYY
    
    private boolean[] entryType = new boolean[]{false,false,false};//Entry Type
    
    int[] currentDate = new int [3];//Store Current Date
    
    private String tEDat = null;//Text Entries
    private String aEDat = null;//Audio Entries
    private String vEDat = null;//Video Entries
    
    private void initCalender(){//Initialize the Calender Controls
        days = new JLabel []{//Initialize Day Labels
            lblSlot1,lblSlot2,lblSlot3,lblSlot4,lblSlot5,lblSlot6,lblSlot7,lblSlot8,lblSlot9,lblSlot10,lblSlot11,lblSlot12,
            lblSlot13,lblSlot14,lblSlot15,lblSlot16,lblSlot17,lblSlot18,lblSlot19,lblSlot20,lblSlot21,lblSlot22,lblSlot23,
            lblSlot24,lblSlot25,lblSlot26,lblSlot27,lblSlot28,lblSlot29,lblSlot30,lblSlot31,lblSlot32,lblSlot33,lblSlot34,
            lblSlot35,lblSlot36,lblSlot37
        };
        dayNames = new String[]{//Initialize Names of Days
            "Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"
        };
        nameOfMonths = new String[]{//Initialize Name of Months
            "January","February","March","April","May","June","July","August","September","October","November","December"
        };
        for(int i=0;i<days.length;i++)//Set Label Name
            days[i].setName("lblSlot"+(i+1));
        
        date=new Date();//Get Current Date
        ft = new SimpleDateFormat ("dd/MM/yyyy");//Format the Date to the format DD.MM.YYYY   
        
        currentDate=new int[]{//Current Date Array
            Integer.valueOf(ft.format(date).split("/")[0]),//Get Day
            Integer.valueOf(ft.format(date).split("/")[1]),//Get Month
            Integer.valueOf(ft.format(date).split("/")[2])//Get Year               
        };
        cbMonthChange.setSelectedIndex(currentDate[1]-1);//Set Current Month
        for (int i = currentDate[2]; i > 1939; i--)//Populate Years
            cbYearChange.addItem("   " + i);           
        
        tEDat = getEntries(currentDate[2], currentDate[1]-1, "textEntries");//Text Entries
        aEDat = getEntries(currentDate[2], currentDate[1]-1, "audioEntries");//Audio Entries
        vEDat = getEntries(currentDate[2], currentDate[1]-1, "videoEntries");//Video Entries
    }
    
    private String getDayName(int day){//Returns Day from Date
        day = day%7;//Get the modulus of the day        
        switch(day){
            case 0:
                return dayNames[6];
            case 1:
                return dayNames[0];
            case 2:
                return dayNames[1]; 
            case 3:
                return dayNames[2];          
            case 4:
                return dayNames[3];
            case 5:
                return dayNames[4];     
            case 6:
                return dayNames[5];
        }
        return "";
    }
    private boolean decideDayOfWeek(String label){//Gets day of the week from calender  
        for(int i=0;i<days.length;i++){//Loop through the labels
            if(days[i].getName() == label){//Check if the label exists in the days array
                clickedLabel = days[i];
                break;
            }
        }
        if(clickedLabel.getText() == "")//To Avoid Action when empty block clicked
            return false;
        int day = Integer.valueOf(clickedLabel.getText());//Get the label number
        
        selectedDate[0] = getDayName(day).substring(0, 3);//Day Name   
        selectedDate[1] = Integer.toString(day);//Day No
        selectedDate[2] = nameOfMonths[cbMonthChange.getSelectedIndex()];//Month
        selectedDate[3] = cbYearChange.getSelectedItem().toString().trim();//Year
        selectedDate[4] = selectedDate[3]+"-"+cbMonthChange.getSelectedIndex()+"-"+ selectedDate[1];//Year
        
        lblFullDate.setText(getDayName(day+2)+", "+selectedDate[2]+" "+selectedDate[1]+", "+selectedDate[3]);
        
        //Get Date from Clicked Label
        date = new Date();//Get Current Date
        ft = new SimpleDateFormat ("dd/MM/yyyy");//Format the Date to the format DD.MM.YYYY 
        
        date.setDate(day);//Set Date
        date.setMonth(cbMonthChange.getSelectedIndex());//Set Month
        
        int year = Integer.valueOf(cbYearChange.getSelectedItem().toString().trim());
        
        int yearLast = year%100;
        int yearFirst = (year/1000) -1;
        
        year = (yearFirst*100)+yearLast;//Set year in the format millenium + year
        
        date.setYear(year);//Set Year
        
        lblVDate.setText("Date : "+ft.format(date));//Set Date of Add
        lblADate.setText("Date : "+ft.format(date));
        
        entryType = new boolean[]{false,false,false};
        
        try{//Check if text Entries present 
            entryType[0] = core.select("*", "textEntries", "logged = '"+selectedDate[4]+"'").getBoolean(1);
        }catch (SQLException ex){  
            try{//Check if audio entries present
                entryType[1] = core.select("*", "audioEntries", "logged = '"+selectedDate[4]+"'").getBoolean(1);   
            }catch(SQLException ex2){ 
                try{//Check if video entries present
                    entryType[2] = core.select("*", "videoEntries", "logged = '"+selectedDate[4]+"'").getBoolean(1);   
                }catch(SQLException ex3){}
            }
        }        
        core.CloseConnection();
        
        boolean entryAvailable=false;//Entry Availability    
        if(entryType[0] || entryType[1] || entryType[2])
            entryAvailable=true;        
        
        if(entryAvailable){
            pnlAddEntry.setVisible(false);
            pnlViewEntry.setVisible(true);
        }else{
            pnlAddEntry.setVisible(true);
            pnlViewEntry.setVisible(false);            
        }
        return true;
    } 
    
    private String getEntries(int year, int month, String table){
        String string = "";
        try{     
            if(core.select("Count(uID)", table,"logged BETWEEN '"+year+"-"+month+"-1' AND '"+year+"-"+month+"-31'").getInt(1) > 0){
                core.CloseConnection();
                core.result = core.select("logged", table, "logged BETWEEN '"+year+"-"+month+"-1' AND '"+year+"-"+month+"-31'");
                while (core.result.next())
                    string += "," + core.result.getString(1);   
                string=string.substring(1, string.length());
            }           
        core.CloseConnection();
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null, ex.toString());
        }
        return string;
    }
    private void colourEntries(String entries, int dayStart,int type){
        if(entries.length() > 0){//If Text Entries Available
            String[] text = null;//Store Entries
            String[] eDate = null;//Stores day from Entries
            Color[] color = new Color[]{//Colours
                new Color(168, 228, 252),//Text Entries
                new Color(241, 185, 255),//Audio Entries
                new Color(253, 189, 133)//Video Entries
            };            
            text=entries.split(",");//Seperate items
            for(int j=0;j<text.length;j++){//Loop through entries
                eDate=text[j].split("-");//Get just the day
                for(int k=dayStart;k<days.length;k++){//Loop through the calender   
                    if(eDate[2].equals(days[k].getText())){//Check id date match with the calender date
                        days[k].setBackground(color[type]);               
                        break;
                    }
                }
            }
        }
    }
    
    private void plotCalender(int day, int month, int year){//Plot the Date Numbers to the Calender        
        emptyCalender();//Empty the calender control
        
        Calendar cal = Calendar.getInstance();//Initialize the calender        
        cal.set(year, month, 1);//Set Calender to Specified Date
        
        int numberOfDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);//Get Number of days in month
        int dayStart = cal.get(Calendar.DAY_OF_WEEK)-1; 
        
        if((month != currentDate[1]-1) || (year != currentDate[2]))//Set colour to the current date
            days[currentDate[0]+dayStart-1].setBackground(Color.white);
        else            
            days[currentDate[0]+dayStart-1].setBackground(new Color(255, 247, 153));   
        
        int count=1;
        for(int i=dayStart;i<numberOfDays+dayStart;i++,count++){//Plot Date Numbers to the calender
            days[i].setText(String.valueOf(count));
        }
        
        colourEntries(tEDat, dayStart,0);
        colourEntries(aEDat, dayStart,1);
        colourEntries(vEDat, dayStart,2);
        
        cbMonthChange.addActionListener(new java.awt.event.ActionListener(){//Assign Action Listener to cbMonthChange
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tEDat = getEntries(Integer.valueOf(cbYearChange.getSelectedItem().toString().trim()), cbMonthChange.getSelectedIndex(), "textEntries");//Text Entries
                aEDat = getEntries(Integer.valueOf(cbYearChange.getSelectedItem().toString().trim()), cbMonthChange.getSelectedIndex(), "audioEntries");//Audio Entries
                vEDat = getEntries(Integer.valueOf(cbYearChange.getSelectedItem().toString().trim()), cbMonthChange.getSelectedIndex(), "videoEntries");//Video Entries
                plotCalender(currentDate[0],cbMonthChange.getSelectedIndex(),Integer.valueOf(cbYearChange.getSelectedItem().toString().trim()));//plot the calender
            }
        });
        cbYearChange.addActionListener(new java.awt.event.ActionListener(){//Assign Action Listener to cbYearChange
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tEDat = getEntries(Integer.valueOf(cbYearChange.getSelectedItem().toString().trim()), cbMonthChange.getSelectedIndex(), "textEntries");//Text Entries
                aEDat = getEntries(Integer.valueOf(cbYearChange.getSelectedItem().toString().trim()), cbMonthChange.getSelectedIndex(), "audioEntries");//Audio Entries
                vEDat = getEntries(Integer.valueOf(cbYearChange.getSelectedItem().toString().trim()), cbMonthChange.getSelectedIndex(), "videoEntries");//Video Entries
                plotCalender(currentDate[0],cbMonthChange.getSelectedIndex(),Integer.valueOf(cbYearChange.getSelectedItem().toString().trim()));//plot the calender
            }
        });
        //plotCalender(currentDate[0],cbMonthChange.getSelectedIndex(),Integer.valueOf(cbYearChange.getSelectedItem().toString().trim()));//plot the calender
    }
    
    private void emptyCalender(){//Empty the calender controls
        lblFullDate.setText("");
        for(int j=0;j<days.length;j++){
            days[j].setText("");  
            days[j].setBackground(Color.white);
        }
    }
    
    public void removeEntry(String sDate){
        String tableName = "textEntries";
        String filename = "usr/entries/";
        String fileType = ".mdpe";
        
        if(entryType[1]){//Determine Table name
            tableName = "audioEntries";
            fileType = ".wav";
        }else if(entryType[2]){
            tableName = "videoEntries";
            fileType = ".ts";
        }else if(entryType[0]){
            tableName = "textEntries";
            fileType = ".mdpe";
        }
        
        filename += tableName.replace("Entries", "")+"/";//Establish File Path
        
        try{
            core.result = core.select("*", tableName, "logged = '"+sDate+"'");//Get Entry Details
            if(core.result.getBoolean(1)){//If Entry Present
                filename+= core.result.getString(3);//Get Filename                
                core.CloseConnection();//Close Database Connection
                core.EstablishConnection();
                if(core.updateQuery("DELETE FROM "+tableName+" WHERE logged = '"+sDate+"';"))//Delete Database File
                    if(core.deleteFile(filename+fileType)){//Delete File                        
                        JOptionPane.showMessageDialog(null, "Record Removed Permanently.");
                        core.CloseConnection();//Close Database Connection
                        plotCalender(currentDate[0],cbMonthChange.getSelectedIndex(),Integer.valueOf(cbYearChange.getSelectedItem().toString().trim()));
                    }
            }else
                JOptionPane.showMessageDialog(null, "No Entry Found");                
        }catch (SQLException ex){            
            JOptionPane.showMessageDialog(null, ex.toString());
        }
    }
    //Calender Controls - End
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblSlot1 = new javax.swing.JLabel();
        lblSlot2 = new javax.swing.JLabel();
        lblSlot3 = new javax.swing.JLabel();
        lblSlot4 = new javax.swing.JLabel();
        lblSlot5 = new javax.swing.JLabel();
        lblSlot6 = new javax.swing.JLabel();
        lblSlot7 = new javax.swing.JLabel();
        lblSlot8 = new javax.swing.JLabel();
        lblSlot9 = new javax.swing.JLabel();
        lblSlot10 = new javax.swing.JLabel();
        lblSlot11 = new javax.swing.JLabel();
        lblSlot12 = new javax.swing.JLabel();
        lblSlot13 = new javax.swing.JLabel();
        lblSlot14 = new javax.swing.JLabel();
        lblSlot15 = new javax.swing.JLabel();
        lblSlot16 = new javax.swing.JLabel();
        lblSlot17 = new javax.swing.JLabel();
        lblSlot18 = new javax.swing.JLabel();
        lblSlot19 = new javax.swing.JLabel();
        lblSlot20 = new javax.swing.JLabel();
        lblSlot21 = new javax.swing.JLabel();
        lblSlot22 = new javax.swing.JLabel();
        lblSlot23 = new javax.swing.JLabel();
        lblSlot24 = new javax.swing.JLabel();
        lblSlot25 = new javax.swing.JLabel();
        lblSlot26 = new javax.swing.JLabel();
        lblSlot27 = new javax.swing.JLabel();
        lblSlot28 = new javax.swing.JLabel();
        lblSlot29 = new javax.swing.JLabel();
        lblSlot30 = new javax.swing.JLabel();
        lblSlot31 = new javax.swing.JLabel();
        lblSlot32 = new javax.swing.JLabel();
        lblSlot33 = new javax.swing.JLabel();
        lblSlot34 = new javax.swing.JLabel();
        lblSlot35 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        btnForward = new javax.swing.JButton();
        cbYearChange = new javax.swing.JComboBox();
        cbMonthChange = new javax.swing.JComboBox();
        lblFullDate = new javax.swing.JLabel();
        lblSlot36 = new javax.swing.JLabel();
        lblSlot37 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlAddEntry = new javax.swing.JPanel();
        lblADate = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnAddVEntry = new javax.swing.JButton();
        btnAddTEntry = new javax.swing.JButton();
        btnAddAEntry = new javax.swing.JButton();
        pnlViewEntry = new javax.swing.JPanel();
        lblVDate = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnShowEntry = new javax.swing.JButton();
        btnRemoveEntry = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mage Dina Potha 1.0 - Control Panel");
        setPreferredSize(new java.awt.Dimension(645, 460));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(22, 108, 143));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("DIARY ENTRIES");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 640, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 640, 67));

        lblSlot1.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot1.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot1.setText("1");
        lblSlot1.setName(""); // NOI18N
        lblSlot1.setOpaque(true);
        lblSlot1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot1, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 164, 42, 38));

        lblSlot2.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot2.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot2.setText("2");
        lblSlot2.setOpaque(true);
        lblSlot2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot2, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 164, 44, 38));

        lblSlot3.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot3.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot3.setText("3");
        lblSlot3.setOpaque(true);
        lblSlot3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot3, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 164, 44, 38));

        lblSlot4.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot4.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot4.setText("4");
        lblSlot4.setOpaque(true);
        lblSlot4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot4, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 164, 44, 38));

        lblSlot5.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot5.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot5.setText("5");
        lblSlot5.setOpaque(true);
        lblSlot5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot5, new org.netbeans.lib.awtextra.AbsoluteConstraints(257, 164, 44, 38));

        lblSlot6.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot6.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot6.setText("6");
        lblSlot6.setToolTipText("");
        lblSlot6.setOpaque(true);
        lblSlot6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot6, new org.netbeans.lib.awtextra.AbsoluteConstraints(302, 164, 44, 38));

        lblSlot7.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot7.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot7.setText("7");
        lblSlot7.setOpaque(true);
        lblSlot7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot7, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 164, 43, 38));

        lblSlot8.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot8.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot8.setText("8");
        lblSlot8.setOpaque(true);
        lblSlot8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot8, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 203, 42, 39));

        lblSlot9.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot9.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot9.setText("9");
        lblSlot9.setOpaque(true);
        lblSlot9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot9, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 203, 44, 39));

        lblSlot10.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot10.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot10.setText("10");
        lblSlot10.setOpaque(true);
        lblSlot10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot10, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 203, 44, 39));

        lblSlot11.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot11.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot11.setText("11");
        lblSlot11.setOpaque(true);
        lblSlot11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot11, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 203, 44, 39));

        lblSlot12.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot12.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot12.setText("12");
        lblSlot12.setOpaque(true);
        lblSlot12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot12, new org.netbeans.lib.awtextra.AbsoluteConstraints(257, 203, 44, 39));

        lblSlot13.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot13.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot13.setText("13");
        lblSlot13.setOpaque(true);
        lblSlot13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot13, new org.netbeans.lib.awtextra.AbsoluteConstraints(302, 203, 44, 39));

        lblSlot14.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot14.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot14.setText("14");
        lblSlot14.setOpaque(true);
        lblSlot14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot14, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 203, 43, 39));

        lblSlot15.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot15.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot15.setText("15");
        lblSlot15.setOpaque(true);
        lblSlot15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot15, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 243, 42, 39));

        lblSlot16.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot16.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot16.setText("16");
        lblSlot16.setOpaque(true);
        lblSlot16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot16, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 243, 44, 39));

        lblSlot17.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot17.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot17.setText("17");
        lblSlot17.setOpaque(true);
        lblSlot17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot17, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 243, 44, 39));

        lblSlot18.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot18.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot18.setText("18");
        lblSlot18.setOpaque(true);
        lblSlot18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot18, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 243, 44, 39));

        lblSlot19.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot19.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot19.setText("19");
        lblSlot19.setOpaque(true);
        lblSlot19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot19, new org.netbeans.lib.awtextra.AbsoluteConstraints(257, 243, 44, 39));

        lblSlot20.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot20.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot20.setText("20");
        lblSlot20.setOpaque(true);
        lblSlot20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot20, new org.netbeans.lib.awtextra.AbsoluteConstraints(302, 243, 44, 39));

        lblSlot21.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot21.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot21.setText("21");
        lblSlot21.setOpaque(true);
        lblSlot21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot21, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 243, 43, 39));

        lblSlot22.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot22.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot22.setText("22");
        lblSlot22.setOpaque(true);
        lblSlot22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot22, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 283, 42, 39));

        lblSlot23.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot23.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot23.setText("23");
        lblSlot23.setOpaque(true);
        lblSlot23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot23, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 283, 44, 39));

        lblSlot24.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot24.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot24.setText("24");
        lblSlot24.setOpaque(true);
        lblSlot24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot24, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 283, 44, 39));

        lblSlot25.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot25.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot25.setText("25");
        lblSlot25.setOpaque(true);
        lblSlot25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot25, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 283, 44, 39));

        lblSlot26.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot26.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot26.setText("26");
        lblSlot26.setOpaque(true);
        lblSlot26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot26, new org.netbeans.lib.awtextra.AbsoluteConstraints(257, 283, 44, 39));

        lblSlot27.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot27.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot27.setText("27");
        lblSlot27.setOpaque(true);
        lblSlot27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot27, new org.netbeans.lib.awtextra.AbsoluteConstraints(302, 283, 44, 39));

        lblSlot28.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot28.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot28.setText("28");
        lblSlot28.setOpaque(true);
        lblSlot28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot28, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 283, 43, 39));

        lblSlot29.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot29.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot29.setText("29");
        lblSlot29.setOpaque(true);
        lblSlot29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot29, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 323, 42, 39));

        lblSlot30.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot30.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot30.setText("30");
        lblSlot30.setOpaque(true);
        lblSlot30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot30, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 323, 44, 39));

        lblSlot31.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot31.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot31.setText("31");
        lblSlot31.setOpaque(true);
        lblSlot31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot31, new org.netbeans.lib.awtextra.AbsoluteConstraints(167, 323, 44, 39));

        lblSlot32.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot32.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot32.setOpaque(true);
        lblSlot32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot32, new org.netbeans.lib.awtextra.AbsoluteConstraints(212, 323, 44, 39));

        lblSlot33.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot33.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot33.setOpaque(true);
        lblSlot33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot33, new org.netbeans.lib.awtextra.AbsoluteConstraints(257, 323, 44, 39));

        lblSlot34.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot34.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot34.setOpaque(true);
        lblSlot34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot34, new org.netbeans.lib.awtextra.AbsoluteConstraints(302, 323, 44, 39));

        lblSlot35.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot35.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot35.setOpaque(true);
        lblSlot35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot35, new org.netbeans.lib.awtextra.AbsoluteConstraints(347, 323, 43, 39));

        btnBack.setBackground(new java.awt.Color(29, 131, 173));
        btnBack.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("<");
        btnBack.setBorder(null);
        btnBack.setFocusPainted(false);
        btnBack.setOpaque(false);
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        getContentPane().add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(19, 115, 60, 49));

        btnForward.setBackground(new java.awt.Color(29, 131, 173));
        btnForward.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        btnForward.setForeground(new java.awt.Color(255, 255, 255));
        btnForward.setText(">");
        btnForward.setBorder(null);
        btnForward.setFocusPainted(false);
        btnForward.setOpaque(false);
        btnForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForwardActionPerformed(evt);
            }
        });
        getContentPane().add(btnForward, new org.netbeans.lib.awtextra.AbsoluteConstraints(389, 116, 62, 49));

        cbYearChange.setBackground(new java.awt.Color(22, 108, 143));
        cbYearChange.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        cbYearChange.setForeground(new java.awt.Color(255, 255, 255));
        cbYearChange.setBorder(null);
        cbYearChange.setFocusable(false);
        cbYearChange.setOpaque(false);
        getContentPane().add(cbYearChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(344, 85, 99, 30));

        cbMonthChange.setBackground(new java.awt.Color(22, 108, 143));
        cbMonthChange.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        cbMonthChange.setForeground(new java.awt.Color(255, 255, 255));
        cbMonthChange.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        cbMonthChange.setBorder(null);
        cbMonthChange.setFocusable(false);
        cbMonthChange.setOpaque(false);
        getContentPane().add(cbMonthChange, new org.netbeans.lib.awtextra.AbsoluteConstraints(226, 85, 118, 30));

        lblFullDate.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblFullDate.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblFullDate.setText("Thursday, December 26, 2015");
        getContentPane().add(lblFullDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(23, 90, 195, -1));

        lblSlot36.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot36.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot36.setOpaque(true);
        lblSlot36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot36, new org.netbeans.lib.awtextra.AbsoluteConstraints(79, 363, 42, 39));

        lblSlot37.setBackground(new java.awt.Color(255, 255, 255));
        lblSlot37.setFont(new java.awt.Font("Arial", 1, 24)); // NOI18N
        lblSlot37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSlot37.setOpaque(true);
        lblSlot37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSlot7MouseClicked(evt);
            }
        });
        getContentPane().add(lblSlot37, new org.netbeans.lib.awtextra.AbsoluteConstraints(122, 363, 44, 39));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/magedinapotha/Calender.jpg"))); // NOI18N
        jLabel2.setText("jLabel2");
        jLabel2.setPreferredSize(new java.awt.Dimension(430, 350));
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 340));

        pnlAddEntry.setBackground(new java.awt.Color(255, 255, 255));
        pnlAddEntry.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(22, 108, 143)));
        pnlAddEntry.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblADate.setBackground(new java.awt.Color(22, 108, 143));
        lblADate.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblADate.setForeground(new java.awt.Color(255, 255, 255));
        lblADate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblADate.setText("Date: 21/10/2015");
        lblADate.setFocusable(false);
        lblADate.setOpaque(true);
        pnlAddEntry.add(lblADate, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 30));

        jLabel4.setBackground(new java.awt.Color(23, 113, 149));
        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("ADD DIARY ENTRY");
        jLabel4.setOpaque(true);
        pnlAddEntry.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 170, 30));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("No Diary Entry Recored");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        pnlAddEntry.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 34, 160, 20));

        btnAddVEntry.setBackground(new java.awt.Color(29, 131, 173));
        btnAddVEntry.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnAddVEntry.setForeground(new java.awt.Color(255, 255, 255));
        btnAddVEntry.setText("|V| Video Entry");
        btnAddVEntry.setBorderPainted(false);
        btnAddVEntry.setFocusable(false);
        btnAddVEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddVEntryActionPerformed(evt);
            }
        });
        pnlAddEntry.add(btnAddVEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 190, 150, 30));

        btnAddTEntry.setBackground(new java.awt.Color(29, 131, 173));
        btnAddTEntry.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnAddTEntry.setForeground(new java.awt.Color(255, 255, 255));
        btnAddTEntry.setText("|T| Text Entry");
        btnAddTEntry.setBorderPainted(false);
        btnAddTEntry.setFocusable(false);
        btnAddTEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTEntryActionPerformed(evt);
            }
        });
        pnlAddEntry.add(btnAddTEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 150, 30));

        btnAddAEntry.setBackground(new java.awt.Color(29, 131, 173));
        btnAddAEntry.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnAddAEntry.setForeground(new java.awt.Color(255, 255, 255));
        btnAddAEntry.setText("|A| Audio Entry");
        btnAddAEntry.setBorderPainted(false);
        btnAddAEntry.setFocusable(false);
        btnAddAEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAEntryActionPerformed(evt);
            }
        });
        pnlAddEntry.add(btnAddAEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 150, 30));

        getContentPane().add(pnlAddEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 170, 230));

        pnlViewEntry.setBackground(new java.awt.Color(255, 255, 255));
        pnlViewEntry.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(22, 108, 143)));
        pnlViewEntry.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblVDate.setBackground(new java.awt.Color(22, 108, 143));
        lblVDate.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblVDate.setForeground(new java.awt.Color(255, 255, 255));
        lblVDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblVDate.setText("Date: 21/10/2015");
        lblVDate.setFocusable(false);
        lblVDate.setOpaque(true);
        pnlViewEntry.add(lblVDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 170, 30));

        jLabel7.setBackground(new java.awt.Color(23, 113, 149));
        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("DIARY ENTRY");
        jLabel7.setOpaque(true);
        pnlViewEntry.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 170, 30));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Diary Entry Recorded");
        jLabel8.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        pnlViewEntry.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(3, 34, 160, 20));

        btnShowEntry.setBackground(new java.awt.Color(29, 131, 173));
        btnShowEntry.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnShowEntry.setForeground(new java.awt.Color(255, 255, 255));
        btnShowEntry.setText("| S | Show Entry");
        btnShowEntry.setBorder(null);
        btnShowEntry.setBorderPainted(false);
        btnShowEntry.setFocusable(false);
        btnShowEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowEntryActionPerformed(evt);
            }
        });
        pnlViewEntry.add(btnShowEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 150, 30));

        btnRemoveEntry.setBackground(new java.awt.Color(29, 131, 173));
        btnRemoveEntry.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnRemoveEntry.setForeground(new java.awt.Color(255, 255, 255));
        btnRemoveEntry.setText("| R | Remove Entry");
        btnRemoveEntry.setBorder(null);
        btnRemoveEntry.setBorderPainted(false);
        btnRemoveEntry.setFocusable(false);
        btnRemoveEntry.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveEntryActionPerformed(evt);
            }
        });
        pnlViewEntry.add(btnRemoveEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 150, 30));

        getContentPane().add(pnlViewEntry, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 170, 190));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/magedinapotha/Legends.jpg"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 310, 170, 113));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSlot7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSlot7MouseClicked
        decideDayOfWeek(evt.getComponent().getName());
    }//GEN-LAST:event_lblSlot7MouseClicked

    private void btnForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForwardActionPerformed
        int count = cbMonthChange.getSelectedIndex();
        count++;
        if(count > 11){
            count=0;
        }
        cbMonthChange.setSelectedIndex(count);
    }//GEN-LAST:event_btnForwardActionPerformed

    private void btnAddAEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAEntryActionPerformed
        AddAudioEntry AAE = new AddAudioEntry(selectedDate);
        this.setVisible(false);
        AAE.show();
                
    }//GEN-LAST:event_btnAddAEntryActionPerformed

    private void btnAddTEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTEntryActionPerformed
        AddTextEntry ATE = new AddTextEntry(selectedDate);
        this.setVisible(false);
        ATE.show();
    }//GEN-LAST:event_btnAddTEntryActionPerformed

    private void btnAddVEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddVEntryActionPerformed
        AddVideoEntry AVE = new AddVideoEntry(selectedDate);
        this.setVisible(false);
        AVE.show();
    }//GEN-LAST:event_btnAddVEntryActionPerformed

    private void btnShowEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowEntryActionPerformed
        if(entryType[0]){//If Text Entry Available
            ViewTextEntry VTE = new ViewTextEntry(selectedDate[4]);
            this.setVisible(false);
            VTE.show();
        }else if(entryType[1]){
            ViewAudioEntry VAE = new ViewAudioEntry(selectedDate[4]);
            this.setVisible(false);
            VAE.show();
        }else if(entryType[2]){
            ViewVideoEntry VVE = new ViewVideoEntry(selectedDate[4]);
            this.setVisible(false);
            VVE.show();
        }
    }//GEN-LAST:event_btnShowEntryActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        int count = cbMonthChange.getSelectedIndex();
        count--;
        if(count < 0){
            count=11;
        }
        cbMonthChange.setSelectedIndex(count);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnRemoveEntryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveEntryActionPerformed
        removeEntry(selectedDate[4]);        
        pnlAddEntry.setVisible(false);
        pnlViewEntry.setVisible(false);
    }//GEN-LAST:event_btnRemoveEntryActionPerformed

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
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HomePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddAEntry;
    private javax.swing.JButton btnAddTEntry;
    private javax.swing.JButton btnAddVEntry;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnForward;
    private javax.swing.JButton btnRemoveEntry;
    private javax.swing.JButton btnShowEntry;
    private javax.swing.JComboBox cbMonthChange;
    private javax.swing.JComboBox cbYearChange;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblADate;
    private javax.swing.JLabel lblFullDate;
    private javax.swing.JLabel lblSlot1;
    private javax.swing.JLabel lblSlot10;
    private javax.swing.JLabel lblSlot11;
    private javax.swing.JLabel lblSlot12;
    private javax.swing.JLabel lblSlot13;
    private javax.swing.JLabel lblSlot14;
    private javax.swing.JLabel lblSlot15;
    private javax.swing.JLabel lblSlot16;
    private javax.swing.JLabel lblSlot17;
    private javax.swing.JLabel lblSlot18;
    private javax.swing.JLabel lblSlot19;
    private javax.swing.JLabel lblSlot2;
    private javax.swing.JLabel lblSlot20;
    private javax.swing.JLabel lblSlot21;
    private javax.swing.JLabel lblSlot22;
    private javax.swing.JLabel lblSlot23;
    private javax.swing.JLabel lblSlot24;
    private javax.swing.JLabel lblSlot25;
    private javax.swing.JLabel lblSlot26;
    private javax.swing.JLabel lblSlot27;
    private javax.swing.JLabel lblSlot28;
    private javax.swing.JLabel lblSlot29;
    private javax.swing.JLabel lblSlot3;
    private javax.swing.JLabel lblSlot30;
    private javax.swing.JLabel lblSlot31;
    private javax.swing.JLabel lblSlot32;
    private javax.swing.JLabel lblSlot33;
    private javax.swing.JLabel lblSlot34;
    private javax.swing.JLabel lblSlot35;
    private javax.swing.JLabel lblSlot36;
    private javax.swing.JLabel lblSlot37;
    private javax.swing.JLabel lblSlot4;
    private javax.swing.JLabel lblSlot5;
    private javax.swing.JLabel lblSlot6;
    private javax.swing.JLabel lblSlot7;
    private javax.swing.JLabel lblSlot8;
    private javax.swing.JLabel lblSlot9;
    private javax.swing.JLabel lblVDate;
    private javax.swing.JPanel pnlAddEntry;
    private javax.swing.JPanel pnlViewEntry;
    // End of variables declaration//GEN-END:variables
}