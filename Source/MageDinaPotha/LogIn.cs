using MetroFramework.Forms;
using Newtonsoft.Json;
using System;
using System.Drawing;
using System.IO;
using System.Security.Cryptography;
using System.Windows.Forms;

namespace MageDinaPotha {
    public partial class LogIn : MetroForm {

        protected Database database;
        private Core core;
        private App_Settings settings;

        public LogIn() {
            InitializeComponent();

            core = new Core();
            database = new Database(core.database_file, "Be Great In What You Do.");
            settings = core.ReadSettings;
        }

        private void login() {
            string username = txtUsername.Text;
            string password = core.GetMd5Hash(MD5.Create(), txtPassword.Text);

            if(database.Count("loginD", $"username = '{username}' AND password = '{password}'") > 0) {
                App_Settings_Encrypted enc_settings = new App_Settings_Encrypted() {
                    App = settings.App
                };

                Rahasa rahasa = new Rahasa();
                rahasa = new Rahasa(rahasa.generate_secure_yathura(settings.App.Key));

                string remember = chkRemember.Checked.ToString().ToLower();

                enc_settings.User_Details = rahasa.Encrypt("{'Remember' : "+ chkRemember.Checked.ToString().ToLower() + ",'Username' : '" + settings.User_Details.Username + "','Password' : '" + settings.User_Details.Password + "'}");

                string json_settings = JsonConvert.SerializeObject(enc_settings);

                rahasa = new Rahasa(core.yathura);

                string json = rahasa.Encrypt(json_settings.ToString());

                File.SetAttributes(core.settings_file, FileAttributes.Normal);
                File.WriteAllText(core.settings_file, json);
                File.SetAttributes(core.settings_file, FileAttributes.ReadOnly);

                core.showHide_Form(new Homepage(username), this);
            } else {
                mtTip.SetToolTip(txtUsername, "Incorrect Username/Password");
                core.change_colour((Control)txtUsername, Color.Red, Color.White);

                mtTip.SetToolTip(txtPassword, "Incorrect Username/Password");
                core.change_colour((Control)txtPassword, Color.Red, Color.White);
            }
        }


        private void btnLogin_Click(object sender, EventArgs e) {
            login();
        }
    }
}