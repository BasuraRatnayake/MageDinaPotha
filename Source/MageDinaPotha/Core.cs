using MetroFramework.Forms;
using Newtonsoft.Json;
using System;
using System.Drawing;
using System.IO;
using System.Security;
using System.Security.Cryptography;
using System.Text;
using System.IO.Compression;
using System.Windows.Forms;

namespace MageDinaPotha {
    public sealed class Core {

        #region Paths
        public string magedinapotha {
            get {
                return Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.UserProfile), "MageDinaPotha");
            }
        }

        public string app_settings {
            get {
                return Path.Combine(magedinapotha, "App Data");
            }
        }

        public string entries {
            get {
                return Path.Combine(magedinapotha, "Entries");
            }
        }
        public string database_file {
            get {
                return Path.Combine(entries, "diary_entries.db");
            }
        }

        public string settings_file {
            get {
                return Path.Combine(app_settings, "settings.config");
            }
        }

        public string text_entries {
            get {
                return Path.Combine(entries, "Text");
            }
        }
        public string audio_entries {
            get {
                return Path.Combine(entries, "Audio");
            }
        }
        public string video_entries {
            get {
                return Path.Combine(entries, "Video");
            }
        }
        #endregion
        
        protected Rahasa rahasa;

        public Core() {
            rahasa = new Rahasa();
        }

        public void change_colour(Control ctrl, Color back, Color fore) {
            ctrl.BackColor = back;
            ctrl.ForeColor = fore;
        }
        
        public SecureString yathura {
            get {
                return rahasa.generate_secure_yathura("Become the Greatest You Can Be.");
            }
        }

        public string GetMd5Hash(MD5 md5Hash, string input) {
            byte[] data = md5Hash.ComputeHash(Encoding.UTF8.GetBytes(input));

            StringBuilder sBuilder = new StringBuilder();

            for (int i = 0; i < data.Length; i++) sBuilder.Append(data[i].ToString("x2"));            

            return sBuilder.ToString();
        }

        public bool VerifyMd5Hash(MD5 md5Hash, string input, string hash) {
            string hashOfInput = GetMd5Hash(md5Hash, input);

            StringComparer comparer = StringComparer.OrdinalIgnoreCase;

            if (0 == comparer.Compare(hashOfInput, hash)) return true;
            else return false;            
        }

        private void DirectoryCopy(string sourceDirName, string destDirName, bool copySubDirs) {
            DirectoryInfo dir = new DirectoryInfo(sourceDirName);
            dir.Attributes |= FileAttributes.Normal;

            if (!dir.Exists) throw new DirectoryNotFoundException("Source directory does not exist or could not be found: " + sourceDirName);            

            DirectoryInfo[] dirs = dir.GetDirectories();
            if (!Directory.Exists(destDirName)) Directory.CreateDirectory(destDirName);            

            FileInfo[] files = dir.GetFiles();
            foreach (FileInfo file in files) {
                string temppath = Path.Combine(destDirName, file.Name);
                file.CopyTo(temppath, true);
                File.SetAttributes(temppath, FileAttributes.Normal);
            }

            if (copySubDirs) {
                foreach (DirectoryInfo subdir in dirs) {
                    string temppath = Path.Combine(destDirName, subdir.Name);
                    DirectoryCopy(subdir.FullName, temppath, copySubDirs);
                }
            }
        }

        public bool ExportData(string username) {
            try {
                if (Directory.Exists(magedinapotha)) {
                    SaveFileDialog _savefd = new SaveFileDialog();
                    _savefd.Title = "Select Destination to Save Export";
                    _savefd.FileName = $"{username}{DateTime.Now.ToShortDateString().Replace("/", "_")}.zip";
                    _savefd.Filter = "Zip Files | *.zip";
                    _savefd.DefaultExt = "zip";
                    _savefd.ShowDialog();

                    string _tempdest = Path.Combine(Path.GetTempPath(), "MageDinaPotha");
                    try {
                        if (Directory.Exists(_tempdest)) Directory.Delete(_tempdest, true);                        
                        Directory.CreateDirectory(_tempdest);
                    } catch { }

                    DirectoryCopy(magedinapotha, _tempdest, true);

                    if (File.Exists(_savefd.FileName)) File.Delete(_savefd.FileName);

                    ZipFile.CreateFromDirectory(_tempdest, _savefd.FileName, CompressionLevel.Optimal, true);
                    if (File.Exists(_savefd.FileName)) return true;
                }
            } catch { }
            return false;
        }

        public bool ImportData() {
            try {
                OpenFileDialog _openfd = new OpenFileDialog();
                _openfd.Title = "Select Saved Diary Entries";
                _openfd.Filter = "Zip Files | *.zip";
                _openfd.DefaultExt = "zip";
                _openfd.CheckFileExists = true;
                _openfd.ShowDialog();

                if (Directory.Exists(magedinapotha)) Directory.Delete(magedinapotha, true);                

                ZipFile.ExtractToDirectory(_openfd.FileName, Environment.GetFolderPath(Environment.SpecialFolder.UserProfile));
                if(Directory.Exists(magedinapotha)) return true;
            } catch { }
            return false;
        }

        public App_Settings ReadSettings {
            get {
                rahasa = new Rahasa(yathura);
                File.SetAttributes(app_settings, FileAttributes.Normal);
                string app_data = rahasa.Decrypt(File.ReadAllText(settings_file));

                App_Settings app_sets = null;
                try {
                    app_sets = JsonConvert.DeserializeObject<App_Settings>(app_data);
                } catch {
                    dynamic a_data = JsonConvert.DeserializeObject<App_Settings_Encrypted>(app_data);

                    App app = new App() {
                        First_Time = a_data.App.First_Time, Key = a_data.App.Key, Version = a_data.App.Version
                    };

                    rahasa = new Rahasa(rahasa.generate_secure_yathura(a_data.App.Key));//Decrypt User Data

                    a_data = JsonConvert.DeserializeObject(rahasa.Decrypt(a_data.User_Details).ToString());
                    app_sets = new App_Settings() {
                        App = app,
                        User_Details = new UserDetails() {
                            Username = a_data.Username, Password = a_data.Password, Remember = a_data.Remember
                        }
                    };
                }

                File.SetAttributes(app_settings, FileAttributes.ReadOnly);
                return app_sets;
            }
        }

        public void showHide_Form(MetroForm form, MetroForm parent) {
            form.Show();
            parent.Hide();

            form.FormClosed += parent_FormClosed;
        }

        private void parent_FormClosed(object sender, FormClosedEventArgs e) {
            Application.Exit();
        }
    }
}
