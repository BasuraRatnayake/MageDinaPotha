using System;
using System.Collections.Generic;
using System.Data.SQLite;

namespace MageDinaPotha {
    public sealed class Database {
        private SQLiteConnection connection;
        public string database { get; set; }
        public string username { get; set; }
        public string password { get; set; }

        private List<string> errors;

        public List<string> ErrorList(string newValue = "") {
            if (errors == null) errors = new List<string>();

            if (!string.IsNullOrWhiteSpace(newValue)) errors.Add(newValue);

            return errors;
        }

        private string CoreDatabase {
            get {
                string database = string.Empty;
                database = "CREATE TABLE IF NOT EXISTS loginD(" +
                    "username            VARCHAR2(20)       NOT NULL PRIMARY KEY," +
                    "password            VARCHAR2(100)      NOT NULL," +
                    "lastLogged          DATETIME           NOT NULL DEFAULT CURRENT_TIMESTAMP," +
                    "UNIQUE (username)" +
                ");";

                database += "CREATE TABLE IF NOT EXISTS personalD(" +
                    "username       VARCHAR2(20)    NOT NULL," +
                    "fname			VARCHAR2(20)	NOT NULL," +
                    "lname			VARCHAR2(20)	NOT NULL," +
                    "dob			DATE		    NOT NULL," +
                    "email			VARCHAR2(100)	NOT NULL," +
                    "FOREIGN KEY (username) REFERENCES loginD (username)" +
                ");";

                database += "CREATE TABLE IF NOT EXISTS textEntries(" +
                    "uID			INTEGER PRIMARY KEY   AUTOINCREMENT," +
                    "username		VARCHAR2(20)    NOT NULL," +
                    "logged			DATE            NOT NULL," +
                    "filename		VARCHAR2(10)	NOT NULL," +
                    "UNIQUE (uID)," +
                    "FOREIGN KEY (username) REFERENCES loginD (username)" +
                ");";

                database += "CREATE TABLE IF NOT EXISTS audioEntries(" +
                    "uID			INTEGER PRIMARY KEY   AUTOINCREMENT," +
                    "username		VARCHAR2(20)    NOT NULL," +
                    "logged			DATE            NOT NULL," +
                    "filename		VARCHAR2(10)	NOT NULL," +
                    "UNIQUE (uID)," +
                    "FOREIGN KEY (username) REFERENCES loginD (username)" +
                ");";

                database += "CREATE TABLE IF NOT EXISTS videoEntries(" +
                    "uID			INTEGER PRIMARY KEY   AUTOINCREMENT," +
                    "username		VARCHAR2(20)    NOT NULL," +
                    "logged			DATE            NOT NULL," +
                    "filename		VARCHAR2(10)	NOT NULL," +
                    "UNIQUE (uID)," +
                    "FOREIGN KEY (username) REFERENCES loginD (username)" +
                ");";
                return database;
            }           
        }

        public Database(string database, string password, bool create_database = false) {
            this.database = database;
            this.password = password;
            errors = new List<string>();

            try {
                string connectionString = string.Empty;
                if (create_database) {
                    connectionString = $"Data Source={database};Version=3;";
                    connection = new SQLiteConnection(connectionString);
                    connection.SetPassword(password.ToString());

                    if (this.EstablisConnection()) {
                        SQLiteCommand cmd = new SQLiteCommand(CoreDatabase, connection);
                        cmd.ExecuteNonQuery();

                        this.CloseConnection();
                    }
                } else {
                    connectionString = $"Data Source={database};Version=3;Password={password};";
                    connection = new SQLiteConnection(connectionString);
                }
            } catch { ErrorList("Unspecified Error Occurred."); }
        }

        private bool EstablisConnection() {
            try {
                connection.Open();
                return true;
            } catch (SQLiteException ex) {
                switch (ex.ErrorCode) {
                    case 0:
                        ErrorList("Internal Server Error Occurred");
                        break;
                    case 1045:
                        ErrorList("Invalid Login Data for Database");
                        break;
                }
                return false;
            }
        }
        public bool CloseConnection() {
            try {
                connection.Close();
                return true;
            } catch (SQLiteException ex) {
                ErrorList(ex.Message);
                return false;
            }
        }

        public bool Insert(string table, string values, string columns = "") {
            try {
                if (!string.IsNullOrWhiteSpace(columns)) columns = $"({columns})";

                string query = $"INSERT INTO {table} {columns} VALUES ({values})";

                if (this.EstablisConnection()) {
                    SQLiteCommand cmd = new SQLiteCommand(query, connection);
                    cmd.ExecuteNonQuery();

                    this.CloseConnection();
                    return true;
                }
            } catch (Exception ex) {
                ErrorList(ex.Message);
                return false;
            }
            return false;
        }

        public bool Update(string table, string values, string where = "") {
            try {
                if (!string.IsNullOrWhiteSpace(where)) where = $"WHERE {where}";

                string query = $"UPDATE {table} SET {values} {where}";

                if (this.EstablisConnection()) {
                    SQLiteCommand cmd = new SQLiteCommand();
                    cmd.CommandText = query;
                    cmd.Connection = connection;
                    cmd.ExecuteNonQuery();

                    this.CloseConnection();
                    return true;
                }
            } catch (Exception ex) {
                ErrorList(ex.Message);
                return false;
            }
            return false;
        }

        public bool Delete(string table, string where) {
            try {
                if (!string.IsNullOrWhiteSpace(where)) where = $"WHERE {where}";

                string query = $"DELETE FROM {table} {where}";

                if (this.EstablisConnection()) {
                    SQLiteCommand cmd = new SQLiteCommand(query, connection);
                    cmd.ExecuteNonQuery();

                    this.CloseConnection();
                    return true;
                }
            } catch (Exception ex) {
                ErrorList(ex.Message);
                return false;
            }
            return false;
        }

        //Select statement
        public List<Tuple<string, string>> Select(string table, string columns = "*", string where = "") {
            try {
                if (!string.IsNullOrWhiteSpace(where)) where = $"WHERE {where}";

                string query = $"SELECT {columns} FROM {table} {where}";
                List<Tuple<string, string>> list = new List<Tuple<string, string>>();

                if (this.EstablisConnection()) {
                    SQLiteCommand cmd = new SQLiteCommand(query, connection);
                    SQLiteDataReader dataReader = cmd.ExecuteReader();

                    while (dataReader.Read()) {
                        for (int i = 0; i < dataReader.FieldCount; i++)
                            list.Add(new Tuple<string, string>(dataReader.GetName(i), dataReader.GetValue(i).ToString()));
                    }

                    dataReader.Close();
                    this.CloseConnection();
                }
                return list;
            } catch (Exception ex) {
                ErrorList(ex.Message);
                return null;
            }
        }

        //Count statement
        public int Count(string table, string where = "") {
            try {
                if (!string.IsNullOrWhiteSpace(where)) where = $"WHERE {where}";

                string query = $"SELECT Count(*) FROM {table} {where}";
                int Count = -1;

                if (this.EstablisConnection()) {
                    SQLiteCommand cmd = new SQLiteCommand(query, connection);

                    Count = int.Parse(cmd.ExecuteScalar() + "");

                    this.CloseConnection();
                    return Count;
                }
                return Count;
            } catch (Exception ex) {
                ErrorList(ex.Message);
                return -1;
            }
        }
    }
}