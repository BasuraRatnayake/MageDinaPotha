using MetroFramework;
using MetroFramework.Forms;
using System;
using System.Drawing;
using System.IO;
using System.Security.Cryptography;
using System.Windows.Forms;
using YARTE.UI.Buttons;

namespace MageDinaPotha {
    public partial class Add_Text_Entry : MetroForm {

        private DateTime date_time;
        private string username;

        protected Database database;
        private Core core;
        protected Rahasa rahasa;

        private bool entry_added = false;

        public bool with_in_form { get; set; }

        public Add_Text_Entry(string username, DateTime date_time, string file_name = null) {
            InitializeComponent();

            try {
                with_in_form = false;
                rahasa = new Rahasa();
                core = new Core();
                database = new Database(core.database_file, "Be Great In What You Do.");
                this.username = username;

                this.date_time = date_time;
                lbl_Date.Text = "( " + date_time.ToShortDateString().Replace("-", "/") + " )";

                if (file_name == null) {
                    PredefinedButtonSets.SetupDefaultButtons(textEditor);
                    textEditor.ShowToolbar = true;
                } else {
                    if (File.Exists(Path.Combine(core.text_entries, file_name + ".mdpt"))) {
                        textEditor.ShowToolbar = false;
                        textEditor.Size = new Size(689, 507);
                        textEditor.Location = new Point(18, 48);

                        this.Text = "Text Entry";

                        lbl_Date.Visible = false;

                        string entry = File.ReadAllText(Path.Combine(core.text_entries, file_name + ".mdpt"));

                        App_Settings app_set = core.ReadSettings;
                        rahasa = new Rahasa(rahasa.generate_secure_yathura(app_set.App.Key));

                        entry = rahasa.Decrypt(entry);

                        textEditor.Html = entry;
                        textEditor.ReadOnly = true;
                        metroLabel47.Text = "Posted On : " + date_time.ToShortDateString().Replace("-", "/");
                    } else {
                        if (database.Delete("textEntries", $"username = '{username}' AND filename = '{file_name}'")) {
                            MetroMessageBox.Show(this, "Diary Entry is Missing From the System, Only the reference is remaining." + Environment.NewLine + "If the problem persists, Contact www.infinitum.lk", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);
                            this.Hide();
                            //core.showHide_Form(new Homepage(username), this);
                        }
                    }
                }
            } catch (Exception ex) {
                MetroMessageBox.Show(this, "Some Known Error Occurred" + Environment.NewLine + ex.Message, "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.Hide();
                //core.showHide_Form(new Homepage(username), this);
            }            
        }

        protected override bool ProcessCmdKey(ref Message msg, Keys keyData) {
            if (!textEditor.ReadOnly) {
                if (entry_added)
                    return true;

                if (keyData == Keys.F12) {
                    string formatted = date_time.ToString("yyyy-MM-dd");
                    string filename = core.GetMd5Hash(MD5.Create(), formatted + new Random().Next(0, 1000).ToString());
                    filename = filename.Substring(0, 10);

                    App_Settings app_set = core.ReadSettings;
                    rahasa = new Rahasa(rahasa.generate_secure_yathura(app_set.App.Key));

                    string entry = rahasa.Encrypt(textEditor.Html);
                    string file = Path.Combine(core.text_entries, filename + ".mdpt");

                    if (database.Insert("textEntries", $"NULL, '{username}', '{formatted}', '{filename}'")) {
                        File.WriteAllText(file, entry);
                        if (File.Exists(file)) {
                            with_in_form = true;
                            entry_added = true;
                            this.Hide();
                            //core.showHide_Form(new Homepage(username), this);
                        }
                    }
                }
            } 
                        
            return base.ProcessCmdKey(ref msg, keyData);
        }

        private void Add_Text_Entry_FormClosed(object sender, FormClosedEventArgs e) {
            //core.showHide_Form(new Homepage(username), this);
        }
    }
}