namespace MageDinaPotha {

    #region Application Settings
    public class App {
        public string Key { get; set; }
        public string Version { get; set; }
        public bool First_Time { get; set; }
    }

    public class UserDetails {
        public bool Remember { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
    }

    public class App_Settings {
        public App App { get; set; }
        public UserDetails User_Details { get; set; }
    }
    public class App_Settings_Encrypted {
        public App App { get; set; }
        public string User_Details { get; set; }
    }
    #endregion

    #region Entries
    public class Entries {
        public string type { get; set; }
        public string uid { get; set; }
        public string file_name { get; set; }
        public string date { get; set; }

        public int label { get; set; }
    }
    #endregion
}
