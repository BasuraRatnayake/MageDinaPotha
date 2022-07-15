using MetroFramework.Forms;
using System;
using System.Drawing;
using System.Windows.Forms;
using CSCore;
using CSCore.Codecs.WAV;
using CSCore.SoundIn;
using System.IO;
using System.Security.Cryptography;
using CSCore.SoundOut;
using CSCore.Codecs.MP3;
using System.Media; //  write down it at the top of the FORM
using MetroFramework;

namespace MageDinaPotha {
    public partial class Add_Audio_Entry : MetroForm {
        private DateTime date_time, start_time;
        private string username, temp_file, file_name;

        protected Database database;
        private Core core;
        protected Rahasa rahasa;

        private bool entry_added = false;

        private WasapiCapture capture = null;
        private WaveWriter w = null;

        private TimeSpan end_time;

        private WaveFileReader wav_r;
        private SoundPlayer player;
        protected MemoryStream stream;

        private bool playing = false;

        public bool with_in_form { get; set; }

        private ISoundOut GetSoundOut() {
            if (WasapiOut.IsSupportedOnCurrentPlatform) return new WasapiOut();
            else return new DirectSoundOut();
        }

        private IWaveSource GetSoundSource(Stream stream) {
            return new DmoMp3Decoder(stream);
        }

        private void record_audio() {
            try {
                capture = new WasapiCapture();
                capture.Initialize();

                temp_file = Path.GetTempFileName();

                w = new WaveWriter(temp_file, capture.WaveFormat);
                capture.DataAvailable += (s, capData) => {
                    w.Write(capData.Data, capData.Offset, capData.ByteCount);
                    end_time = DateTime.Now.Subtract(start_time);

                    lblRecordTime.Text = string.Format("{0} : {1} : {2}", Math.Floor(end_time.TotalHours) < 10 ? "0" + Math.Floor(end_time.TotalHours) : Math.Floor(end_time.TotalHours).ToString(), Math.Floor(end_time.TotalMinutes) < 10 ? "0" + Math.Floor(end_time.TotalMinutes) : Math.Floor(end_time.TotalMinutes).ToString(), Math.Floor(end_time.TotalSeconds) < 10 ? "0" + Math.Floor(end_time.TotalSeconds) : Math.Floor(end_time.TotalSeconds).ToString());
                };

                capture.Start();
            } catch (Exception ex) {
                MetroMessageBox.Show(this, "The following error ocurred:" + Environment.NewLine + ex.Message + Environment.NewLine + "If the problem persists, Contact www.infinitum.lk", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.Hide();
                //core.showHide_Form(new Homepage(username), this);
            }
        }

        public Add_Audio_Entry(string username, DateTime date_time, string file_name = null) {
            InitializeComponent();

            try {
                with_in_form = false;
                rahasa = new Rahasa();
                core = new Core();
                database = new Database(core.database_file, "Be Great In What You Do.");
                this.username = username;

                this.date_time = date_time;
                lbl_Date.Text = "( " + date_time.ToShortDateString().Replace("-", "/") + " )";

                player = new SoundPlayer();

                CheckForIllegalCrossThreadCalls = false;

                if (!string.IsNullOrWhiteSpace(file_name)) {
                    lbl_Date.Visible = false;
                    this.Text = "Audio Entry";
                    lblButDesc.Text = "Posted On : " + date_time.ToShortDateString().Replace("-", "/");
                    btnPlay_Stop.Visible = true;
                    this.Size = new Size(644, 128);

                    this.Cursor = Cursors.WaitCursor;
                    App_Settings app_set = core.ReadSettings;
                    rahasa = new Rahasa(rahasa.generate_secure_yathura(app_set.App.Key));

                    string file = Path.Combine(core.audio_entries, file_name + ".mdpa");
                    if (File.Exists(file)) {
                        stream = rahasa.DecryptStream(file);

                        wav_r = new WaveFileReader(stream);

                        stream.Position = 0;
                        player = new SoundPlayer(stream);
                        player.Load();
                        
                        end_time = wav_r.GetLength();
                        wav_r.Dispose();
                        wav_r = null;

                        lblRecordTime.Text = string.Format("{0} : {1} : {2}", Math.Floor(end_time.TotalHours) < 10 ? "0" + Math.Floor(end_time.TotalHours) : Math.Floor(end_time.TotalHours).ToString(), Math.Floor(end_time.TotalMinutes) < 10 ? "0" + Math.Floor(end_time.TotalMinutes) : Math.Floor(end_time.TotalMinutes).ToString(), Math.Floor(end_time.TotalSeconds) < 10 ? "0" + Math.Floor(end_time.TotalSeconds) : Math.Floor(end_time.TotalSeconds).ToString());

                        player.Load();
                    }
                    this.Cursor = Cursors.Default;
                }
                   
            } catch (Exception ex) {
                MetroMessageBox.Show(this, "The following error ocurred:" + Environment.NewLine + ex.Message + Environment.NewLine + "If the problem persists, Contact www.infinitum.lk", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.Hide();
                //core.showHide_Form(new Homepage(username), this);
            }
        }

        protected override bool ProcessCmdKey(ref Message msg, Keys keyData) {
            if (entry_added) return true;

            try {
                if (string.IsNullOrEmpty(file_name)) {
                    #region Commands
                    if (keyData == Keys.F4) {
                        timer.Enabled = true;
                        timer.Start();
                        start_time = DateTime.Now;
                        record_audio();
                        lblButDesc.Text = "Press F6 to Stop Recording Diary Entry.";
                    }

                    if (keyData == Keys.F6) {
                        timer.Stop();
                        pnlRecordIndicator.BackColor = Color.Transparent;
                        lblButDesc.Text = "Press F12 to Save or ESC to Delete Audio Entry.";
                        if (w != null && capture != null) {
                            //stop recording
                            capture.Stop();
                            w.Dispose();
                            w = null;
                            capture.Dispose();
                            capture = null;
                        }
                    }

                    if (keyData == Keys.Escape) {
                        if (w != null && capture != null) {
                            capture.Stop();
                            w.Dispose();
                            w = null;
                            capture.Dispose();
                            capture = null;
                        }

                        if (File.Exists(temp_file)) File.Delete(temp_file);                        
                        this.Hide();
                        //core.showHide_Form(new Homepage(username), this);
                    }

                    if (keyData == Keys.F12) {
                        lblButDesc.Text = "Please Wait a While, Processing Audio.";
                        this.Cursor = Cursors.WaitCursor;
                        string formatted = date_time.ToString("yyyy-MM-dd");
                        string filename = core.GetMd5Hash(MD5.Create(), formatted+ new Random().Next(0, 1000).ToString());
                        filename = filename.Substring(0, 10); 

                        App_Settings app_set = core.ReadSettings;
                        rahasa = new Rahasa(rahasa.generate_secure_yathura(app_set.App.Key));

                        string file = Path.Combine(core.audio_entries, filename + ".mdpa");

                        if (database.Insert("audioEntries", $"NULL, '{username}', '{formatted}', '{filename}'")) {
                            if (File.Exists(temp_file)) {
                                File.Copy(temp_file, file);

                                if (File.Exists(file)) {
                                    with_in_form = true;
                                    rahasa.EncryptFile(file);
                                    this.Hide();
                                    //core.showHide_Form(new Homepage(username), this);
                                }
                            }
                        }

                        this.Cursor = Cursors.Default;
                    }
                    #endregion
                } 
            } catch (Exception ex) {
                MetroMessageBox.Show(this, "The following error ocurred:" + Environment.NewLine + ex.Message + Environment.NewLine + "If the problem persists, Contact www.infinitum.lk", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.Hide();
                //core.showHide_Form(new Homepage(username), this);
            }

            return base.ProcessCmdKey(ref msg, keyData);
        }

        private void Add_Audio_Entry_FormClosed(object sender, FormClosedEventArgs e) {
            try {
                if (w != null && capture != null) {
                    capture.Stop();
                    w.Dispose();
                    w = null;
                    capture.Dispose();
                    capture = null;
                }

                if(stream != null) {
                    stream.Dispose();
                    stream = null;
                    player.Stop();
                    player.Dispose();
                }

                if (File.Exists(temp_file)) File.Delete(temp_file);
                
                //core.showHide_Form(new Homepage(username), this);
            } catch (Exception ex) {
                MetroMessageBox.Show(this, "The following error ocurred:" + Environment.NewLine + ex.Message + Environment.NewLine + "If the problem persists, Contact www.infinitum.lk", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.Hide();
                //core.showHide_Form(new Homepage(username), this);
            }
        }        
        
        private void btnPlay_Stop_Click(object sender, EventArgs e) {
            try {
                playing = !playing;
                btnPlay_Stop.Text = playing ? "Stop Playing Entry" : "Play Diary Entry";

                if (playing) player.Play();
                else player.Stop();                
            } catch (Exception ex) {
                MetroMessageBox.Show(this, "The following error ocurred:" + Environment.NewLine + ex.Message + Environment.NewLine + "If the problem persists, Contact www.infinitum.lk", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.Hide();
                //core.showHide_Form(new Homepage(username), this);
            }
        }

        private void timer_Tick(object sender, EventArgs e) {
            pnlRecordIndicator.BackColor = pnlRecordIndicator.BackColor == Color.Transparent ? Color.Firebrick : Color.Transparent;
        }
    }
}