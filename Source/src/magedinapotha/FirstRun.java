/*  MageDinaPotha 1.0 is covered by the MIT License.
    Copyright 2015 Basura Ratnayake (basuraratnayake@protonmail.com), All Rights Reserved. */
package magedinapotha;

import java.awt.Color;
import java.awt.Component;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FirstRun extends javax.swing.JFrame {   
    
    Core core = new Core();
    public FirstRun(){
        initComponents();
        core.centerFrame(this);//Center the Frame
        this.getContentPane().setBackground(Color.white);
        
        btnBack.setVisible(false);
        pnlPersonal.setVisible(true);
        pnlContact.setVisible(false);
        pnlLogin.setVisible(false);
        btnContinue.setVisible(false);
        initControls();
    }

    private String[] personal = new String [6];//Personal Details    
    private String[] contact = new String [3];//Contact Details
    protected String[] login = new String [3];//Login Details
    private boolean[] status = new boolean[]{false,false,false};//Validates whether the values are valid
    
    private JTextField[] textFields;//All JTextFields
    private JComboBox[] comboBoxes;//All JCombo Boxes
    private String sex;//Get Value from Radio Buttons T - Male F - Female       
    private void initControls(){//Initialize the Controls
        textFields = new JTextField[]{//Initialize the JTextFields
            txtFName,txtLName,txtEmail,txtMobile,txtHome,txtPass,txtPassConfirm,txtAnswer
        };
        comboBoxes = new JComboBox[]{//Initialize the JComboBoxes
            cbCountry,cbDay,cbMonth,cbYear,cbSecQue
        };        
        
        for(int i=1940;i<2016;i++)//Populate the Year Field
            cbYear.addItem(i);
    }
    
    private void showHidePanel(JPanel[] panels,int[] panelD){//Show or hide registrations steps
        //panels 0 - Current Panel, 1 - Old Panel
        //panelD(Panel Details) 0 - x, 1 - y, 2 - w, 3 - h
        if(panels[0].isVisible()){
            panels[0].setVisible(false);
            
            panels[1].setVisible(true);
            panels[1].setBounds(panelD[0], panelD[1], panelD[2], panelD[3]); 
        }
        if(pnlPersonal.isVisible())
            btnBack.setVisible(false);
        else
            btnBack.setVisible(true);     
        
        if(pnlLogin.isVisible())
            btnContinueFinal.setText("Complete");
    }
    
    private boolean isValidPersonal(){//Validate Personal Details
        for(int i=0;i<2;i++){         
            if(textFields[i].getText().matches("[A-Z][a-zA-Z]*")){//Check if Fields match the regex
                status[i]=true;            
                textFields[i].setForeground(Color.black);
            }else{
                status[i]=false;  
                textFields[i].setText("can only contain letters");
                textFields[i].setForeground(Color.red);                
            }
        }
        if(status[0] && status[1]){//If both the fields are valid
            String dob=comboBoxes[3].getSelectedItem().toString()+"-"+(comboBoxes[2].getSelectedIndex()+1)+"-"+comboBoxes[1].getSelectedItem().toString();
            personal=new String[]{//Assign Component values to the personal array
                textFields[0].getText(),textFields[1].getText(),
                comboBoxes[0].getSelectedItem().toString(),dob,sex
            };
            return true;
        }
        return false;
    }
    private boolean isValidContact(){//Validate Contact Details
        Pattern pattern = null;
        Matcher matcher = null;
        for(int i=2;i<5;i++){
            if(i==2)
                pattern = Pattern.compile("^.+@.+\\..+$");//Validate the email 
            else if(i == 3 || i == 4)
                pattern = Pattern.compile("[0-9]+");//Validate phone numbers
            
            matcher = pattern.matcher(textFields[i].getText());
            
            if(matcher.matches()){
                status[i-2]=true;
            }else{
                status[i-2]=false;                    
                textFields[i].setText("Enter Valid Data");
                textFields[i].setForeground(Color.red);  
            }
        }
        if(status[0] && status[1] && status[2]){
            contact=new String[]{
                textFields[2].getText(),textFields[3].getText(),
                textFields[4].getText()
            };
            return true;
        }
        return false;
    }
    private boolean isValidLogin(){//Validate Login Details
        if(textFields[5].getText() == textFields[6].getText())//Validate password field
            status[0] = true;
        
        if(textFields[7].getText() != "")
            status[1] = true;
        
        if(status[0] && status[1]){
            login=new String[]{
                textFields[5].getText(),cbSecQue.getSelectedItem().toString(),
                txtAnswer.getText()
            };
            return true;
        }            
        return false;
    }
    
    private void finalizeRegistration(){//Finalize the registration process
        if(isValidLogin()){//Validate Login Details
            if(core.CreateCoreDatabase()){//Creates core database tables
                core.EstablishConnection();//Opens Database connection
                if(core.insertInto("loginD", "null,'"+login[0]+"','"+login[1]+"','"+login[2]+"',CURRENT_TIMESTAMP")){//Insert details to the login table 
                    try{
                        int code = core.select("uniqCode", "logind order by uniqCode limit 1").getInt(1);//Get the unique id of the newly added record
                        if(core.insertInto("personalD",code+",'"+personal[0]+"','"+personal[1]+"','"+personal[2]+"','"+
                        personal[3]+"','"+personal[4]+"','"+contact[0]+"','"+contact[1]+"','"+contact[2]+"'")){//Insert details to the personal table
                            if(core.writeTextFile("usr/settings.config","First Run = 0")){//Change First Run in settings.config to 0
                                JOptionPane.showConfirmDialog((Component)null,  "Your Account is Now Created.","Mage Dina Potha 1.0",JOptionPane.PLAIN_MESSAGE);
                                SignIn s = new SignIn();
                                this.setVisible(false);
                                s.show();
                            }
                        }
                    }catch (SQLException ex){
                        JOptionPane.showMessageDialog(null, ex.toString());
                    }
                }
                core.CloseConnection();
           }                  
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDatePickerUtil1 = new org.jdatepicker.util.JDatePickerUtil();
        jDateComponentFactory1 = new org.jdatepicker.JDateComponentFactory();
        jDateComponentFactory2 = new org.jdatepicker.JDateComponentFactory();
        pnlPersonal = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtFName = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtLName = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cbCountry = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        cbMonth = new javax.swing.JComboBox();
        cbDay = new javax.swing.JComboBox();
        cbYear = new javax.swing.JComboBox();
        radFemale = new javax.swing.JRadioButton();
        radMale = new javax.swing.JRadioButton();
        jLabel17 = new javax.swing.JLabel();
        pnlLogin = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPass = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtPassConfirm = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        cbSecQue = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        txtAnswer = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        pnlContact = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtMobile = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtHome = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();
        btnContinueFinal = new javax.swing.JButton();
        btnContinue = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mage Dina Potha 1.0 - Registration");
        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(390, 345));
        setResizable(false);
        setSize(new java.awt.Dimension(499, 400));
        getContentPane().setLayout(null);

        pnlPersonal.setBackground(new java.awt.Color(255, 255, 255));
        pnlPersonal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel7.setText("Registration Step 1 - Personal Details");
        pnlPersonal.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("First Name");
        pnlPersonal.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 42, 92, 20));

        txtFName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPassKeyTyped(evt);
            }
        });
        pnlPersonal.add(txtFName, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 200, 25));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Last Name");
        pnlPersonal.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 72, 92, 20));

        txtLName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPassKeyTyped(evt);
            }
        });
        pnlPersonal.add(txtLName, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 200, 25));

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Country");
        pnlPersonal.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 102, 92, 20));

        cbCountry.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Afghanistan (AF)", "Albania (AL)", "Algeria (DZ)", "American Samoa (AS)", "Andorra (AD)", "Angola (AO)", "Anguilla (AI)", "Antarctica (AQ)", "Antigua and Barbuda (AG)", "Argentina (AR)", "Armenia (AM)", "Aruba (AW)", "Australia (AU)", "Austria (AT)", "Azerbaijan (AZ)", "Bahamas (BS)", "Bahrain (BH)", "Bangladesh (BD)", "Barbados (BB)", "Belarus (BY)", "Belgium (BE)", "Belize (BZ)", "Benin (BJ)", "Bermuda (BM)", "Bhutan (BT)", "Bolivia (BO)", "Bosnia and Herzegovina (BA)", "Botswana (BW)", "Bouvet Island (BV)", "Brazil (BR)", "British Antarctic Territory (BQ)", "British Indian Ocean Territory (IO)", "British Virgin Islands (VG)", "Brunei (BN)", "Bulgaria (BG)", "Burkina Faso (BF)", "Burundi (BI)", "Cambodia (KH)", "Cameroon (CM)", "Canada (CA)", "Canton and Enderbury Islands (CT)", "Cape Verde (CV)", "Cayman Islands (KY)", "Central African Republic (CF)", "Chad (TD)", "Chile (CL)", "China (CN)", "Christmas Island (CX)", "Cocos [Keeling] Islands (CC)", "Colombia (CO)", "Comoros (KM)", "Congo - Brazzaville (CG)", "Congo - Kinshasa (CD)", "Cook Islands (CK)", "Costa Rica (CR)", "Croatia (HR)", "Cuba (CU)", "Cyprus (CY)", "Czech Republic (CZ)", "Côte d’Ivoire (CI)", "Denmark (DK)", "Djibouti (DJ)", "Dominica (DM)", "Dominican Republic (DO)", "Dronning Maud Land (NQ)", "East Germany (DD)", "Ecuador (EC)", "Egypt (EG)", "El Salvador (SV)", "Equatorial Guinea (GQ)", "Eritrea (ER)", "Estonia (EE)", "Ethiopia (ET)", "Falkland Islands (FK)", "Faroe Islands (FO)", "Fiji (FJ)", "Finland (FI)", "France (FR)", "French Guiana (GF)", "French Polynesia (PF)", "French Southern Territories (TF)", "French Southern and Antarctic Territories (FQ)", "Gabon (GA)", "Gambia (GM)", "Georgia (GE)", "Germany (DE)", "Ghana (GH)", "Gibraltar (GI)", "Greece (GR)", "Greenland (GL)", "Grenada (GD)", "Guadeloupe (GP)", "Guam (GU)", "Guatemala (GT)", "Guernsey (GG)", "Guinea (GN)", "Guinea-Bissau (GW)", "Guyana (GY)", "Haiti (HT)", "Heard Island and McDonald Islands (HM)", "Honduras (HN)", "Hong Kong SAR China (HK)", "Hungary (HU)", "Iceland (IS)", "India (IN)", "Indonesia (ID)", "Iran (IR)", "Iraq (IQ)", "Ireland (IE)", "Isle of Man (IM)", "Israel (IL)", "Italy (IT)", "Jamaica (JM)", "Japan (JP)", "Jersey (JE)", "Johnston Island (JT)", "Jordan (JO)", "Kazakhstan (KZ)", "Kenya (KE)", "Kiribati (KI)", "Kuwait (KW)", "Kyrgyzstan (KG)", "Laos (LA)", "Latvia (LV)", "Lebanon (LB)", "Lesotho (LS)", "Liberia (LR)", "Libya (LY)", "Liechtenstein (LI)", "Lithuania (LT)", "Luxembourg (LU)", "Macau SAR China (MO)", "Macedonia (MK)", "Madagascar (MG)", "Malawi (MW)", "Malaysia (MY)", "Maldives (MV)", "Mali (ML)", "Malta (MT)", "Marshall Islands (MH)", "Martinique (MQ)", "Mauritania (MR)", "Mauritius (MU)", "Mayotte (YT)", "Metropolitan France (FX)", "Mexico (MX)", "Micronesia (FM)", "Midway Islands (MI)", "Moldova (MD)", "Monaco (MC)", "Mongolia (MN)", "Montenegro (ME)", "Montserrat (MS)", "Morocco (MA)", "Mozambique (MZ)", "Myanmar [Burma] (MM)", "Namibia (NA)", "Nauru (NR)", "Nepal (NP)", "Netherlands (NL)", "Netherlands Antilles (AN)", "Neutral Zone (NT)", "New Caledonia (NC)", "New Zealand (NZ)", "Nicaragua (NI)", "Niger (NE)", "Nigeria (NG)", "Niue (NU)", "Norfolk Island (NF)", "North Korea (KP)", "North Vietnam (VD)", "Northern Mariana Islands (MP)", "Norway (NO)", "Oman (OM)", "Pacific Islands Trust Territory (PC)", "Pakistan (PK)", "Palau (PW)", "Palestinian Territories (PS)", "Panama (PA)", "Panama Canal Zone (PZ)", "Papua New Guinea (PG)", "Paraguay (PY)", "People's Democratic Republic of Yemen (YD)", "Peru (PE)", "Philippines (PH)", "Pitcairn Islands (PN)", "Poland (PL)", "Portugal (PT)", "Puerto Rico (PR)", "Qatar (QA)", "Romania (RO)", "Russia (RU)", "Rwanda (RW)", "Réunion (RE)", "Saint Barthélemy (BL)", "Saint Helena (SH)", "Saint Kitts and Nevis (KN)", "Saint Lucia (LC)", "Saint Martin (MF)", "Saint Pierre and Miquelon (PM)", "Saint Vincent and the Grenadines (VC)", "Samoa (WS)", "San Marino (SM)", "Saudi Arabia (SA)", "Senegal (SN)", "Serbia (RS)", "Serbia and Montenegro (CS)", "Seychelles (SC)", "Sierra Leone (SL)", "Singapore (SG)", "Slovakia (SK)", "Slovenia (SI)", "Solomon Islands (SB)", "Somalia (SO)", "South Africa (ZA)", "South Georgia and the South Sandwich Islands (GS)", "South Korea (KR)", "Spain (ES)", "Sri Lanka (LK)", "Sudan (SD)", "Suriname (SR)", "Svalbard and Jan Mayen (SJ)", "Swaziland (SZ)", "Sweden (SE)", "Switzerland (CH)", "Syria (SY)", "São Tomé and Príncipe (ST)", "Taiwan (TW)", "Tajikistan (TJ)", "Tanzania (TZ)", "Thailand (TH)", "Timor-Leste (TL)", "Togo (TG)", "Tokelau (TK)", "Tonga (TO)", "Trinidad and Tobago (TT)", "Tunisia (TN)", "Turkey (TR)", "Turkmenistan (TM)", "Turks and Caicos Islands (TC)", "Tuvalu (TV)", "U.S. Minor Outlying Islands (UM)", "U.S. Miscellaneous Pacific Islands (PU)", "U.S. Virgin Islands (VI)", "Uganda (UG)", "Ukraine (UA)", "Union of Soviet Socialist Republics (SU)", "United Arab Emirates (AE)", "United Kingdom (GB)", "United States (US)", "Unknown or Invalid Region (ZZ)", "Uruguay (UY)", "Uzbekistan (UZ)", "Vanuatu (VU)", "Vatican City (VA)", "Venezuela (VE)", "Vietnam (VN)", "Wake Island (WK)", "Wallis and Futuna (WF)", "Western Sahara (EH)", "Yemen (YE)", "Zambia (ZM)", "Zimbabwe (ZW)", "Åland Islands (AX)" }));
        cbCountry.setSelectedIndex(218);
        pnlPersonal.add(cbCountry, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 200, 25));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Sex");
        pnlPersonal.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 168, 92, -1));

        cbMonth.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" }));
        cbMonth.setPreferredSize(new java.awt.Dimension(80, 20));
        pnlPersonal.add(cbMonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, -1, 25));

        cbDay.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        pnlPersonal.add(cbDay, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, -1, 25));

        pnlPersonal.add(cbYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(229, 130, 81, 25));

        radFemale.setText("Female");
        radFemale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radFemaleActionPerformed(evt);
            }
        });
        pnlPersonal.add(radFemale, new org.netbeans.lib.awtextra.AbsoluteConstraints(165, 164, -1, -1));

        radMale.setSelected(true);
        radMale.setText("Male");
        radMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radMaleActionPerformed(evt);
            }
        });
        pnlPersonal.add(radMale, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 164, -1, -1));

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Date of Birth");
        pnlPersonal.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 135, 92, -1));

        getContentPane().add(pnlPersonal);
        pnlPersonal.setBounds(30, 70, 320, 190);

        pnlLogin.setBackground(new java.awt.Color(255, 255, 255));
        pnlLogin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel2.setText("Registration Step 3 - Login Details");
        pnlLogin.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, -1, -1));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Password");
        pnlLogin.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 92, 20));

        txtPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPassKeyTyped(evt);
            }
        });
        pnlLogin.add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 200, 25));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Confirm Password");
        pnlLogin.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 130, 20));

        txtPassConfirm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPassKeyTyped(evt);
            }
        });
        pnlLogin.add(txtPassConfirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 200, 25));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Security Question");
        pnlLogin.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 130, 20));

        cbSecQue.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "What is your pet name?", "What is your favorite colour?", "What is your favorite movie?", "In what town was your first job?" }));
        pnlLogin.add(cbSecQue, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 100, 200, 25));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Answer");
        pnlLogin.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 92, 20));

        txtAnswer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPassKeyTyped(evt);
            }
        });
        pnlLogin.add(txtAnswer, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 130, 200, 25));

        getContentPane().add(pnlLogin);
        pnlLogin.setBounds(260, 360, 380, 170);

        jLabel1.setBackground(new java.awt.Color(22, 108, 143));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("REGISTRATION PROCESS");
        jLabel1.setOpaque(true);
        getContentPane().add(jLabel1);
        jLabel1.setBounds(0, 0, 420, 50);

        pnlContact.setBackground(new java.awt.Color(255, 255, 255));
        pnlContact.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel13.setText("Registration Step 2 - Contact Details");
        pnlContact.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(57, 10, -1, -1));

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Email");
        pnlContact.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 42, 92, 20));

        txtEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPassKeyTyped(evt);
            }
        });
        pnlContact.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, 200, 25));

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Mobile");
        pnlContact.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 72, 92, 20));

        txtMobile.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPassKeyTyped(evt);
            }
        });
        pnlContact.add(txtMobile, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 70, 200, 25));

        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel16.setText("Home Phone");
        pnlContact.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 102, 92, 20));

        txtHome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPassKeyTyped(evt);
            }
        });
        pnlContact.add(txtHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 100, 200, 25));

        getContentPane().add(pnlContact);
        pnlContact.setBounds(370, 100, 320, 140);

        jPanel1.setBackground(new java.awt.Color(29, 131, 173));
        jPanel1.setPreferredSize(new java.awt.Dimension(34, 14));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        jPanel1.add(btnBack, new org.netbeans.lib.awtextra.AbsoluteConstraints(119, 11, 98, -1));

        btnContinueFinal.setText("Continue");
        btnContinueFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContinueFinalActionPerformed(evt);
            }
        });
        jPanel1.add(btnContinueFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(235, 11, 90, -1));

        btnContinue.setText("Complete");
        btnContinue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContinueActionPerformed(evt);
            }
        });
        jPanel1.add(btnContinue, new org.netbeans.lib.awtextra.AbsoluteConstraints(235, 11, 90, -1));

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 270, 420, 50);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void radMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radMaleActionPerformed
        radMale.setSelected(true);
        sex="M";
        if(radFemale.isSelected())
            radFemale.setSelected(false);
    }//GEN-LAST:event_radMaleActionPerformed

    private void radFemaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radFemaleActionPerformed
        radFemale.setSelected(true);
        sex="F";
        if(radMale.isSelected())
            radMale.setSelected(false);
    }//GEN-LAST:event_radFemaleActionPerformed
  
    private boolean regBack=true;
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        showHidePanel(new JPanel[]{pnlContact,pnlPersonal},new int[]{30, 70, 320, 190});   
        if(regBack){
            showHidePanel(new JPanel[]{pnlLogin,pnlContact},new int[]{20, 70, 343, 190}); 
            regBack=false;
        }else
            regBack=true;
    }//GEN-LAST:event_btnBackActionPerformed
    
    private boolean regContinue=false;
    private void btnContinueFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContinueFinalActionPerformed
        if(isValidPersonal()){
            regContinue=true;
            showHidePanel(new JPanel[]{pnlPersonal,pnlContact},new int[]{30, 70, 320, 190});
        }
        if(isValidContact() && regContinue){
            showHidePanel(new JPanel[]{pnlContact,pnlLogin},new int[]{20, 70, 343, 190});
            regContinue=false;
        }
        
        if(btnContinueFinal.getText() == "Complete"){
            btnContinueFinal.setVisible(false);
            btnContinue.setVisible(true);
            btnContinue.setBounds(235, 11, 90, 23); 
        }
    }//GEN-LAST:event_btnContinueFinalActionPerformed

    private void btnContinueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContinueActionPerformed
        finalizeRegistration();
    }//GEN-LAST:event_btnContinueActionPerformed

    private void txtPassKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPassKeyTyped
        evt.getComponent().setForeground(Color.black);   
    }//GEN-LAST:event_txtPassKeyTyped

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
            java.util.logging.Logger.getLogger(FirstRun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FirstRun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FirstRun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FirstRun.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FirstRun().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnContinue;
    private javax.swing.JButton btnContinueFinal;
    private javax.swing.JComboBox cbCountry;
    private javax.swing.JComboBox cbDay;
    private javax.swing.JComboBox cbMonth;
    private javax.swing.JComboBox cbSecQue;
    private javax.swing.JComboBox cbYear;
    private org.jdatepicker.JDateComponentFactory jDateComponentFactory1;
    private org.jdatepicker.JDateComponentFactory jDateComponentFactory2;
    private org.jdatepicker.util.JDatePickerUtil jDatePickerUtil1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnlContact;
    private javax.swing.JPanel pnlLogin;
    private javax.swing.JPanel pnlPersonal;
    private javax.swing.JRadioButton radFemale;
    private javax.swing.JRadioButton radMale;
    private javax.swing.JTextField txtAnswer;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFName;
    private javax.swing.JTextField txtHome;
    private javax.swing.JTextField txtLName;
    private javax.swing.JTextField txtMobile;
    private javax.swing.JTextField txtPass;
    private javax.swing.JTextField txtPassConfirm;
    // End of variables declaration//GEN-END:variables
}