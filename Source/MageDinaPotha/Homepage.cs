using MetroFramework;
using MetroFramework.Controls;
using MetroFramework.Forms;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Windows.Forms;

namespace MageDinaPotha {
    public partial class Homepage : MetroForm {

        protected Database database;
        private Core core;
        private App_Settings settings;
        protected Rahasa rahasa;

        private DateTime dt, selected_date;

        private MetroLabel[] day_labels;

        private Entries dayclick_entry;

        private string[] day_name = new string[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

        private string username;

        private bool with_in_form = false;

        public Homepage(string username) {
            InitializeComponent();

            this.username = username;

            rahasa = new Rahasa();
            core = new Core();
            database = new Database(core.database_file, "Be Great In What You Do.");

            dt = DateTime.Now;//Current Date

            init_calender();
            plot_calender(dt.Day, dt.Month, dt.Year);
        }

        private void set_selected_date(DateTime dateT) {
            lblSelectedDate.Text = string.Format("{0}, {1} {2}, {3}", dateT.DayOfWeek, cmbMonth.Items[dateT.Month - 1], dateT.Day, dateT.Year);
            lblShort_date.Text = dateT.ToShortDateString();
        }

        private void clear_calender() {
            foreach (MetroLabel lbl in day_labels) {
                lbl.Text = string.Empty;
                lbl.BackColor = Color.Transparent;
                lbl.Cursor = Cursors.Default;
            }
        }

        private void init_calender() {
            day_labels = new MetroLabel[] {
                lblD_0, lblD_1, lblD_2, lblD_3, lblD_4, lblD_5, lblD_6, lblD_7, lblD_8, lblD_9, lblD_10, lblD_11, lblD_12, lblD_13, lblD_14, lblD_15,
                lblD_16, lblD_17, lblD_18, lblD_19, lblD_20, lblD_21, lblD_22, lblD_23, lblD_24, lblD_25, lblD_26, lblD_27, lblD_28, lblD_29, lblD_30,
                lblD_31, lblD_32, lblD_33, lblD_34, lblD_35, lblD_36, lblD_37, lblD_38, lblD_39, lblD_40, lblD_41
            };

            //Plot Years
            for (int i = dt.Year + 1; i >= 2017; i--) cmbYear.Items.Add(i);
            cmbYear.SelectedIndex = 1;
            //Assign Month
            cmbMonth.SelectedIndex = dt.Month - 1;
            set_selected_date(dt);
        }

        private void highlight_day(MetroLabel label, Color color) {
            label.BackColor = color;
        }

        private void plot_calender(int day, int month, int year) {
            DateTime date_time = new DateTime(year, month, day);

            clear_calender();

            int start_day_week = (int)(new DateTime(year, month, 1).DayOfWeek);
            int number_days_month = DateTime.DaysInMonth(year, month);
            int current_year = date_time.Year;
            int current_month = date_time.Month;

            this.Cursor = Cursors.WaitCursor;

            int count = 1;
            for (int i = start_day_week; i < (number_days_month+ start_day_week); i++, count++) {//Plot Date Numbers to the calender
                day_labels[i].Text = count.ToString();
                if (!string.IsNullOrWhiteSpace(day_labels[i].Text)) {
                    short multiple = -1;
                    DateTime dt_new = new DateTime(year, month, int.Parse(day_labels[i].Text));
                    string formatted = dt_new.ToString("yyyy-MM-dd");
                    day_labels[i].Cursor = Cursors.Hand;
                    
                    if (database.Count("textEntries", $"logged = '{formatted}' AND username = '{username}'") > 0) {
                        highlight_day(day_labels[i], ColorTranslator.FromHtml("#a8e4fc"));

                        List<Tuple<string, string>> text_entries = database.Select("textEntries", "*", $"logged = '{formatted}' AND username = '{username}'");

                        Entries selected_entry = new Entries() {
                            type = "Text", date = formatted, file_name = text_entries[3].Item2, uid = text_entries[0].Item2, label = i
                        };

                        day_labels[i].Click += (sender, e) => day_Click(sender, e, dt_new, selected_entry);
                        multiple++;
                        //continue;
                    }

                    if (database.Count("audioEntries", $"logged = '{formatted}' AND username = '{username}'") > 0) {
                        highlight_day(day_labels[i], ColorTranslator.FromHtml("#f1b9ff"));

                        List<Tuple<string, string>> audio_entries = database.Select("audioEntries", "*", $"logged = '{formatted}' AND username = '{username}'");

                        Entries selected_entry = new Entries() {
                            type = "Audio", date = formatted, file_name = audio_entries[3].Item2, uid = audio_entries[0].Item2, label = i
                        };

                        day_labels[i].Click += (sender, e) => day_Click(sender, e, dt_new, selected_entry);
                        multiple++;
                        //continue;
                    }

                    if (database.Count("videoEntries", $"logged = '{formatted}' AND username = '{username}'") > 0) {
                        highlight_day(day_labels[i], Color.Violet);

                        List<Tuple<string, string>> video_entries = database.Select("videoEntries", "*", $"logged = '{formatted}' AND username = '{username}'");

                        Entries selected_entry = new Entries() {
                            type = "Video", date = formatted, file_name = video_entries[3].Item2, uid = video_entries[0].Item2, label = i
                        };

                        day_labels[i].Click += (sender, e) => day_Click(sender, e, dt_new, selected_entry);
                        multiple++;
                        //continue;
                    }

                    if(multiple == -1) day_labels[i].Click += (sender, e) => day_Click(sender, e, dt_new);
                    else if (multiple > 0) highlight_day(day_labels[i], Color.Gold);                    
                }
            }

            set_selected_date(date_time);

            //Colour Today
            MetroLabel today = day_labels.FirstOrDefault(x => x.Text == dt.Day.ToString() && (dt.Month == month && dt.Year == year));
            if (today != null && today.BackColor == Color.Transparent) {
                highlight_day(today, ColorTranslator.FromHtml("#fff799"));
                set_selected_date(new DateTime(year, month, int.Parse(today.Text)));
            }

            this.Cursor = Cursors.Default;
        }
        
        private void day_Click(object sender, EventArgs e, DateTime date_time, Entries selected_entry = null) {
            set_selected_date(date_time);
            selected_date = date_time;

            pnlShow.Visible = false;
            //pnlAdd.Visible = false;

            if (selected_entry != null) {
                MetroLabel _sender = (MetroLabel)sender;

                lblEntryData.Text = selected_entry.type + " Diary Entry Recorded This Day.";
                lblDayStatus.Text = "DIARY ENTRY";

                if ( _sender.BackColor.Name == "Gold") {
                    lblEntryData.Text = "Multiple Entries Recorded This Day.";
                    lblDayStatus.Text = "DIARY ENTRIES";
                } 
                
                dayclick_entry = selected_entry;
                pnlShow.Visible = true;
                
                pnlAdd.Location = new Point(4, 118);
                //btnViewEntry.Text = selected_entry.type == "Text" ? "[R] Read Entry" : selected_entry.type == "Audio" ? "[L] Listen to Entry" : "[P] Play Entry";
            } else {
                pnlAdd.Visible = true;
                lblDayStatus.Text = "ADD DIARY ENTRY";
                lblEntryData.Text = "No Diary Entry Recorded This Day.";
                pnlAdd.Location = new Point(4, 138);
            }            
        }

        private void pictureBox1_Click(object sender, EventArgs e) {
            new AboutUs().ShowDialog();
        }

        private void cmbYear_SelectionChangeCommitted(object sender, EventArgs e) {
            plot_calender(1, cmbMonth.SelectedIndex + 1, (int)cmbYear.SelectedItem);
        }

        private void lblMoreTypes_Click(object sender, EventArgs e) {
            pnlLegends.Visible = !pnlLegends.Visible;
            lblMoreTypes.Text = pnlLegends.Visible ? "Entry Types                                                   Hide" : "Entry Types                                                   Show";
        }

        private void btnText_Click(object sender, EventArgs e) {
            Add_Text_Entry _t = new Add_Text_Entry(username, selected_date);
            _t.ShowDialog();
            with_in_form = _t.with_in_form;
            //this.Hide();
        }

        private void Homepage_FormClosed(object sender, FormClosedEventArgs e) {
            Application.Exit();
        }

        private void btnViewEntry_Click(object sender, EventArgs e) {

            new Show_Entries(username, selected_date).ShowDialog();
            //this.Hide();
            //if (dayclick_entry.type == "Text") {
            //    new Add_Text_Entry(username, selected_date, dayclick_entry.file_name).Show();
            //    this.Hide();
            //} else if (dayclick_entry.type == "Audio") {
            //    new Add_Audio_Entry(username, selected_date, dayclick_entry.file_name).Show();
            //    this.Hide();
            //} else if (dayclick_entry.type == "Video") {
            //    new Add_Video_Entry(username, selected_date, dayclick_entry.file_name).Show();
            //    this.Hide();
            //}
        }

        private void btnNext_Click(object sender, EventArgs e) {
            if ((cmbMonth.SelectedIndex + 2) <= cmbMonth.Items.Count - 1) cmbMonth.SelectedIndex += 1;
            else {
                cmbMonth.SelectedIndex = 0;
                if ((cmbYear.SelectedIndex + 1) <= cmbYear.Items.Count - 1) cmbYear.SelectedIndex += 1;
                else cmbYear.SelectedIndex = 0;
            }           

            plot_calender(1, cmbMonth.SelectedIndex + 1, (int)cmbYear.SelectedItem);
        }

        private void btnPrev_Click(object sender, EventArgs e) {
            if ((cmbMonth.SelectedIndex - 2) >= 0) cmbMonth.SelectedIndex -= 1;
            else {
                cmbMonth.SelectedIndex = 0;
                if ((cmbYear.SelectedIndex - 1) >= 0) cmbYear.SelectedIndex -= 1;
                else cmbYear.SelectedIndex = 0;
            }

            plot_calender(1, cmbMonth.SelectedIndex + 1, (int)cmbYear.SelectedItem);
        }

        private void logOutToolStripMenuItem_Click(object sender, EventArgs e) {
            settings = core.ReadSettings;
            App_Settings_Encrypted enc_settings = new App_Settings_Encrypted() {
                App = settings.App
            };

            Rahasa rahasa = new Rahasa();
            rahasa = new Rahasa(rahasa.generate_secure_yathura(settings.App.Key));

            enc_settings.User_Details = rahasa.Encrypt("{'Remember' : false,'Username' : '" + settings.User_Details.Username + "','Password' : '" + settings.User_Details.Password + "'}");

            string json_settings = JsonConvert.SerializeObject(enc_settings);

            rahasa = new Rahasa(core.yathura);

            string json = rahasa.Encrypt(json_settings.ToString());

            File.SetAttributes(core.settings_file, FileAttributes.Normal);
            File.WriteAllText(core.settings_file, json);
            File.SetAttributes(core.settings_file, FileAttributes.ReadOnly);
            core.showHide_Form(new LogIn(), this);
        }

        private void aboutUsToolStripMenuItem_Click(object sender, EventArgs e) {
            new AboutUs().ShowDialog();
        }

        private void btnAudio_Click(object sender, EventArgs e) {
            Add_Audio_Entry _a = new Add_Audio_Entry(username, selected_date);
            _a.ShowDialog();
            with_in_form = _a.with_in_form;
            //this.Hide();
        }

        private void btnRemoveEntry_Click(object sender, EventArgs e) {
            try {
                if (MetroMessageBox.Show(this, "Are You Sure You Want to Delete this Entry ?" + Environment.NewLine + "Once the Entry is Deleted It Can Never Be Retrieved.", "Mage Dina Potha 2", MessageBoxButtons.YesNo, MessageBoxIcon.Error) == DialogResult.Yes) {

                    DateTime dt_new = new DateTime(int.Parse(cmbYear.SelectedItem.ToString()), cmbMonth.SelectedIndex + 1, int.Parse(day_labels[dayclick_entry.label].Text));

                    if (dayclick_entry.type == "Text") {
                        if (database.Delete("textEntries", "username = '" + username + "' AND filename = '" + dayclick_entry.file_name + "'")) {
                            if (File.Exists(Path.Combine(core.text_entries, dayclick_entry.file_name + ".mdpt"))) {
                                File.Delete(Path.Combine(core.text_entries, dayclick_entry.file_name + ".mdpt"));
                                day_labels[dayclick_entry.label].BackColor = Color.Transparent;
                                day_labels[dayclick_entry.label].Click += (sender1, e1) => day_Click(sender1, e1, dt_new);
                                pnlControls.Size = new Size(256, 88);
                                return;
                            }
                        }
                    } else if (dayclick_entry.type == "Audio") {
                        if (database.Delete("audioEntries", "username = '" + username + "' AND filename = '" + dayclick_entry.file_name + "'")) {
                            if (File.Exists(Path.Combine(core.audio_entries, dayclick_entry.file_name + ".mdpa"))) {
                                File.Delete(Path.Combine(core.audio_entries, dayclick_entry.file_name + ".mdpa"));
                                day_labels[dayclick_entry.label].BackColor = Color.Transparent;
                                day_labels[dayclick_entry.label].Click += (sender1, e1) => day_Click(sender1, e1, dt_new);
                                pnlControls.Size = new Size(256, 88);
                                return;
                            }
                        }
                    } else if (dayclick_entry.type == "Video") {
                        if (database.Delete("videoEntries", "username = '" + username + "' AND filename = '" + dayclick_entry.file_name + "'")) {
                            if (File.Exists(Path.Combine(core.video_entries, dayclick_entry.file_name + ".mdpv"))) {
                                File.Delete(Path.Combine(core.video_entries, dayclick_entry.file_name + ".mdpv"));
                                day_labels[dayclick_entry.label].BackColor = Color.Transparent;
                                day_labels[dayclick_entry.label].Click += (sender1, e1) => day_Click(sender1, e1, dt_new);
                                pnlControls.Size = new Size(256, 88);
                                return;
                            }
                        }
                    }
                    MetroMessageBox.Show(this, "Some Unknown Error Occurred", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);
                }
            } catch (Exception ex) {
                MetroMessageBox.Show(this, "Some Unknown Error Occurred"+Environment.NewLine+ex.Message, "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        private void btnVideo_Click(object sender, EventArgs e) {
            Add_Video_Entry _v = new Add_Video_Entry(username, selected_date);
            _v.ShowDialog();
            with_in_form = _v.with_in_form;
            //this.Hide();
        }

        private void Homepage_Activated(object sender, EventArgs e) {
            if (with_in_form) plot_calender(dt.Day, dt.Month, dt.Year);
            with_in_form = false;
        }

        private void Homepage_VisibleChanged(object sender, EventArgs e) { }

        private void exportDiaryEntriesToolStripMenuItem_Click(object sender, EventArgs e) {
            if (core.ExportData(username)) MetroMessageBox.Show(this, "Diary Entries Exported", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Information);
            else MetroMessageBox.Show(this, "Diary Entries Not Exported", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);            
        }
    }
}