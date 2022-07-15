/*  MageDinaPotha 1.0 is covered by the MIT License.
    Copyright 2015 Basura Ratnayake (basuraratnayake@protonmail.com), All Rights Reserved. */
package magedinapotha;

import java.sql.ResultSet;

public interface Database {
    
    public boolean EstablishConnection();//Establishes Connection with the database
    public void CloseConnection();//Close established database connection
    
    public boolean CreateCoreDatabase();//Create Database when first run
   
    public String getLoginTable();//User Login Details Table
    public String getPersonalTable();//User Personal Information
    public String getTextEntries();//Stored Text Entries
    public String getAudioEntries();//Stored Audio Entries
    public String getVideoEntries();//Stored Video Entries
    
    public ResultSet executeQuery(String query);//Execute SQL Query
    public boolean updateQuery(String query);//Update Tables
    
    public boolean insertInto(String table,String columns,String values);//Full Insert Statement
    public boolean insertInto(String table,String values);//No Columns Needed
    
    public ResultSet select(String table);//Get All Columns
    public ResultSet select(String columns, String table);//Get Specified Columns and functions
    public ResultSet select(String columns, String table, String where);//Get records according to specified condition;
}