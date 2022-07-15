using MetroFramework.Forms;
using Newtonsoft.Json;
using System;
using System.IO;
using System.Windows.Forms;

namespace MageDinaPotha {
    public partial class StartUp : MetroForm {

        protected string setting_file, database_file;

        protected Rahasa rahasa;
        protected Database database;
        protected Core core;

        private MetroForm child;

        public StartUp() {
            InitializeComponent();

            rahasa = new Rahasa();
            core = new Core();
            create_structure();
        }

        private void create_structure() {
            try {
                DirectoryInfo main_dir = Directory.CreateDirectory(core.magedinapotha);//Main Directory

                DirectoryInfo entry_dir = main_dir.CreateSubdirectory("Entries");//Diary Entry Directory
                entry_dir.CreateSubdirectory("Text");
                entry_dir.CreateSubdirectory("Audio");
                entry_dir.CreateSubdirectory("Video");

                main_dir.CreateSubdirectory("App Data");//Application ReadSettings

                setting_file = core.settings_file;//Setting File
                database_file = core.database_file;//Database File                

                rahasa = new Rahasa(core.yathura);

                //Create and Handle ReadSettings File
                if (!File.Exists(setting_file)) create_setting_file(setting_file);
                else check_settings_file(setting_file);

                lblVersion.Text = $"Version {Application.ProductVersion} "+ Environment.NewLine+"Copyright(c) 2017 Infinitum.All Rights Reserved.";      

                splash_timer.Start();
            } catch { }
        }

        private void create_database_file(string path) {
            database = new Database(path, "Be Great In What You Do.", true);
        }
        private void check_database_file(string path) { }

        private void create_setting_file(string path) {
            try {
                string s_key = rahasa.generateRandom();

                App_Settings_Encrypted settings = new App_Settings_Encrypted() {
                    App = new App() {
                        First_Time = true, Key = s_key, Version = Application.ProductVersion
                    }
                };

                rahasa = new Rahasa(rahasa.generate_secure_yathura(s_key));

                settings.User_Details = rahasa.Encrypt("{'Remember' : false,'Username' : '','Password' : ''}");

                string json_settings = JsonConvert.SerializeObject(settings);

                rahasa = new Rahasa(core.yathura);

                string json = rahasa.Encrypt(json_settings.ToString());
                File.WriteAllText(path, json);

                if (File.Exists(path)) {
                    File.SetAttributes(path, FileAttributes.ReadOnly);

                    //Create and Handle Database File
                    if (!File.Exists(database_file)) {
                        create_database_file(database_file);
                        child = new Register();
                    } 
                }
            } catch { }
        }
        private void check_settings_file(string setting_file) {
            File.SetAttributes(setting_file, FileAttributes.Normal);
            string app_data = rahasa.Decrypt(File.ReadAllText(setting_file));

            App_Settings app_sets = core.ReadSettings;

            if (!app_sets.App.First_Time) {//Not First Time
                if (app_sets.User_Details.Remember) {//Remember Data
                    if (!string.IsNullOrWhiteSpace(app_sets.User_Details.Username) && !string.IsNullOrWhiteSpace(app_sets.User_Details.Password)) {//Contains Username and password
                                                                                                                                                   
                        //Create and Handle Database File
                        if (!File.Exists(database_file)) create_database_file(database_file);
                        else check_database_file(database_file);                        

                        database = new Database(database_file, "Be Great In What You Do.");
                        if(database.Count("loginD", "username = '" + app_sets.User_Details.Username + "' AND password = '" + app_sets.User_Details.Password + "'") > 0) {
                            child = new Homepage(app_sets.User_Details.Username);
                            File.SetAttributes(setting_file, FileAttributes.ReadOnly);
                            return;
                        }                        
                    } else { //Reset Saved User Data
                        app_sets.User_Details.Remember = false;
                        app_sets.User_Details.Username = string.Empty;
                        app_sets.User_Details.Password = string.Empty;

                        File.WriteAllText(setting_file, rahasa.Encrypt(JsonConvert.SerializeObject(app_sets)));
                    }
                }

                child = new LogIn();
            } else child = new Register();            

            File.SetAttributes(setting_file, FileAttributes.ReadOnly);
        }

        private void splash_timer_Tick(object sender, EventArgs e) {
            core.showHide_Form(child, this);
            splash_timer.Stop();
        }
    }
}
