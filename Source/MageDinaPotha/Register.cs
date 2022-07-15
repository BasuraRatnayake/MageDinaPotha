using MetroFramework;
using MetroFramework.Controls;
using MetroFramework.Forms;
using Newtonsoft.Json;
using System;
using System.Drawing;
using System.IO;
using System.Net.Mail;
using System.Security.Cryptography;
using System.Windows.Forms;

namespace MageDinaPotha {
    public partial class Register : MetroForm {

        protected Database database;
        private Core core;
        private App_Settings settings;

        public Register() {
            InitializeComponent();
            core = new Core();
            mtTip.ToolTipIcon = ToolTipIcon.Error;
            mtTip.BackColor = Color.Red;
        }

        private void register_user() {
            try {
                database = new Database(core.database_file, "Be Great In What You Do.");

                settings = core.ReadSettings;

                bool okay = true;

                string username = txtUsername.Text;
                string password = core.GetMd5Hash(MD5.Create(), txtPassword.Text);
                string email = txtEmail.Text;
                string first = txtFirst.Text;
                string last = txtLast.Text;
                string dob = dtDob.Value.ToString();

                #region Validations
                foreach (Control ctrl in this.Controls) {
                    if(ctrl is MetroTextBox) {
                        if (ctrl.Text.Length < 5) {
                            mtTip.SetToolTip(ctrl, "Must Contain Atleast 5 Characters");
                            core.change_colour(ctrl, Color.Red, Color.White);
                            okay = false;
                        }
                    }                    
                }

                try {
                    MailAddress m = new MailAddress(txtEmail.Text);
                } catch (FormatException) {
                    mtTip.SetToolTip(txtEmail, "Invalid Email Address.");
                    core.change_colour((Control)txtEmail, Color.Red, Color.White);
                    okay = false;
                }
                if(txtPassword.Text != txtPasswordC.Text) {
                    mtTip.SetToolTip(txtPassword, "Password and Confirm Password Doesn't Match");
                    mtTip.SetToolTip(txtPasswordC, "Password and Confirm Password Doesn't Match");
                    core.change_colour((Control)txtPassword, Color.Red, Color.White);
                    okay = false;
                }
                #endregion

                if (!okay) return;

                bool login_data = database.Insert("loginD", $"'{username}', '{password}', CURRENT_TIMESTAMP");
                bool personal_data = database.Insert("personalD", $"'{username}', '{first}', '{last}', '{dob}', '{email}'");

                if (login_data && personal_data) {
                    settings.App.First_Time = false;

                    App_Settings_Encrypted enc_settings = new App_Settings_Encrypted() {
                        App = settings.App
                    };

                    Rahasa rahasa = new Rahasa();
                    rahasa = new Rahasa(rahasa.generate_secure_yathura(settings.App.Key));

                    enc_settings.User_Details = rahasa.Encrypt("{'Remember' : false,'Username' : '"+username+"','Password' : '"+password+"'}");

                    string json_settings = JsonConvert.SerializeObject(enc_settings);

                    rahasa = new Rahasa(core.yathura);

                    string json = rahasa.Encrypt(json_settings.ToString());

                    File.SetAttributes(core.settings_file, FileAttributes.Normal);
                    File.WriteAllText(core.settings_file, json);
                    File.SetAttributes(core.settings_file, FileAttributes.ReadOnly);

                    MetroMessageBox.Show(this, "You Account is Now Registered." + Environment.NewLine + "Try Logging In.", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Information);
                    core.showHide_Form(new LogIn(), this);
                } else {
                    if(database.Delete("personalD", $"username = '{username}'")) {
                        if(database.Delete("loginD", $"username = '{username}'")) 
                            MetroMessageBox.Show(this, "An Unknown Error Occurred."+Environment.NewLine+"Try Again.", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);                        
                    }                    
                }
            } catch { }
        }

        private void btnRegister_Click(object sender, EventArgs e) {
            register_user();
        }

        private void txtFirst_TextChanged(object sender, EventArgs e) {
            core.change_colour((Control)sender, Color.White, Color.Black);
            mtTip.RemoveAll();
        }

        private void btnImport_Click(object sender, EventArgs e) {
            if (core.ImportData()) {
                MetroMessageBox.Show(this, "Diary Entries Exported", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Information);
                core.showHide_Form(this, new LogIn());
            } else MetroMessageBox.Show(this, "Diary Entries Not Exported", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);
        }
    }
}
