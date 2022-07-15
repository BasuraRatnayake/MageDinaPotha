using MetroFramework.Controls;
using MetroFramework.Forms;
using System;
using System.Collections.Generic;
using System.Windows.Forms;

namespace MageDinaPotha {
    public partial class Show_Entries : MetroForm {

        private string username = string.Empty;
        private DateTime date_time;

        protected Database database;
        private Core core;
        protected Rahasa rahasa;

        public Show_Entries() {
            InitializeComponent();

            Get_Diary_Entries();
        }

        public Show_Entries(string username, DateTime date_time) {
            InitializeComponent();

            this.username = username;
            this.date_time = date_time;

            lbl_Date.Text = "( "+ date_time.ToShortDateString().Replace("-", "/") + " )";

            rahasa = new Rahasa();
            core = new Core();
            database = new Database(core.database_file, "Be Great In What You Do.");

            Get_Diary_Entries();
        }

        private void Get_Diary_Entries() {

            string formatted = date_time.ToString("yyyy-MM-dd");

            if (database.Count("textEntries", $"logged = '{formatted}' AND username = '{username}'") > 0) {

                List<Tuple<string, string>> text_entries = database.Select("textEntries", "*", $"logged = '{formatted}' AND username = '{username}'");

                for (int i = 0; i < text_entries.Count/4; i++) {
                    int uid_index = i * 4;
                    int file_index = uid_index+3;

                    Entries selected_entry = new Entries() {
                        type = "Text", date = formatted, file_name = text_entries[file_index].Item2, uid = text_entries[uid_index].Item2
                    };

                    grid_entries.Rows.Add(selected_entry.uid, "Text", selected_entry.file_name, "16:01");
                }
            }

            if (database.Count("audioEntries", $"logged = '{formatted}' AND username = '{username}'") > 0) {

                List<Tuple<string, string>> audio_entries = database.Select("audioEntries", "*", $"logged = '{formatted}' AND username = '{username}'");

                for (int i = 0; i < audio_entries.Count / 4; i++) {
                    int uid_index = i * 4;
                    int file_index = uid_index + 3;

                    Entries selected_entry = new Entries() {
                        type = "Audio", date = formatted, file_name = audio_entries[file_index].Item2, uid = audio_entries[uid_index].Item2
                    };

                    grid_entries.Rows.Add(selected_entry.uid, "Audio", selected_entry.file_name, "16:01");
                }
            }

            if (database.Count("videoEntries", $"logged = '{formatted}' AND username = '{username}'") > 0) {

                List<Tuple<string, string>> video_entries = database.Select("videoEntries", "*", $"logged = '{formatted}' AND username = '{username}'");

                for (int i = 0; i < video_entries.Count / 4; i++) {
                    int uid_index = i * 4;
                    int file_index = uid_index + 3;

                    Entries selected_entry = new Entries() {
                        type = "Video", date = formatted, file_name = video_entries[file_index].Item2, uid = video_entries[uid_index].Item2
                    };

                    grid_entries.Rows.Add(selected_entry.uid, "Video", selected_entry.file_name, "16:01");
                }
            }
        }

        private void Show_Entries_FormClosing(object sender, FormClosingEventArgs e) {
            //core.showHide_Form(new Homepage(username), this);
        }

        private void grid_entries_CellDoubleClick(object sender, DataGridViewCellEventArgs e) {
            MetroGrid data = (MetroGrid)sender;
            string entry_id = data.SelectedCells[0].FormattedValue.ToString();
            string entry_type = data.SelectedCells[1].FormattedValue.ToString();
            string entry_filename = data.SelectedCells[2].FormattedValue.ToString();

            if (entry_type == "Text") new Add_Text_Entry(username, date_time, entry_filename).ShowDialog();
            else if (entry_type == "Audio") new Add_Audio_Entry(username, date_time, entry_filename).ShowDialog();
            else if (entry_type == "Video") new Add_Video_Entry(username, date_time, entry_filename).ShowDialog();
        }
    }
}
