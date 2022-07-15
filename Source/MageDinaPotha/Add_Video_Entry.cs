using MetroFramework.Forms;
using System;
using System.Drawing;
using System.Windows.Forms;
using AForge.Video;
using AForge.Video.DirectShow;
using Accord.Video.FFMPEG;
using MetroFramework;
using System.IO;
using System.Security.Cryptography;
using CSCore.SoundIn;
using CSCore.Codecs.WAV;
using CSCore.SoundOut;
using CSCore;
using CSCore.Codecs.MP3;
using System.Runtime.InteropServices;
using System.Diagnostics;

namespace MageDinaPotha {
    public partial class Add_Video_Entry : MetroForm {
        [DllImport("User32.dll")]
        public static extern Int32 SetForegroundWindow(int hWnd);

        private string username, temp_file, play_file, file_name;

        protected Database database;
        private Core core;
        protected Rahasa rahasa;

        private bool entry_added = false;

        private VideoCaptureDevice videoSource;
        private VideoFileWriter writer;

        private Bitmap image;

        private int width, height;

        private DateTime date_time, start_time;
        private TimeSpan end_time;

        private WasapiCapture capture = null;
        private WaveWriter w = null;

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
                    //writer.WriteAudioFrame(capData.Data);
                };

                capture.Start();
            } catch (Exception ex) {
                MetroMessageBox.Show(this, "The following error ocurred:" + Environment.NewLine + ex.Message + Environment.NewLine + "If the problem persists, Contact www.infinitum.lk", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);
                //core.showHide_Form(new Homepage(username), this);
            }
        }

        private void search_devices() {
            try {
                writer = new VideoFileWriter();
                FilterInfoCollection videosources = new FilterInfoCollection(FilterCategory.VideoInputDevice);

                if (videosources != null && videosources.Count > 0) {
                    videoSource = new VideoCaptureDevice(videosources[0].MonikerString);

                    if (videoSource.VideoCapabilities.Length > 0) {
                        string highestSolution = "0;0";
                        for (int i = 0; i < videoSource.VideoCapabilities.Length; i++) {
                            if (videoSource.VideoCapabilities[i].FrameSize.Width > Convert.ToInt32(highestSolution.Split(';')[0])) {
                                highestSolution = videoSource.VideoCapabilities[i].FrameSize.Width.ToString() + ";" + i.ToString();
                                height = videoSource.VideoCapabilities[i].FrameSize.Height;
                                width = videoSource.VideoCapabilities[i].FrameSize.Width;
                            }
                        }
                        image = new Bitmap(width, height);
                        videoSource.VideoResolution = videoSource.VideoCapabilities[Convert.ToInt32(highestSolution.Split(';')[1])];

                        videoSource.NewFrame += new AForge.Video.NewFrameEventHandler(videoSource_NewFrame);
                        videoSource.Start();
                    } else {
                        MetroMessageBox.Show(this, "No Video Recording Device Found.", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);
                        this.Hide();
                    }                
                } else {
                    MetroMessageBox.Show(this, "No Video Recording Device Found.", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);
                    this.Hide();
                    //core.showHide_Form(new Homepage(username), this);
                }
            } catch {
                MetroMessageBox.Show(this, "No Video Recording Device Found.", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.Hide();
                //core.showHide_Form(new Homepage(username), this);
            }
        }

        public Add_Video_Entry(string username, DateTime date_time, string file_name = null) {
            try {
                InitializeComponent();

                with_in_form = false;

                rahasa = new Rahasa();
                core = new Core();
                database = new Database(core.database_file, "Be Great In What You Do.");
                this.username = username;

                this.date_time = date_time;
                lbl_Date.Text = "( " + date_time.ToShortDateString().Replace("-", "/") + " )";

                CheckForIllegalCrossThreadCalls = false;

                this.file_name = file_name;

                if (!string.IsNullOrWhiteSpace(file_name)) {
                    lbl_Date.Visible = false;
                    this.Text = "Video Entry";
                    lblButDesc.Text = "Posted On : " + date_time.ToShortDateString().Replace("-", "/");

                    this.Cursor = Cursors.WaitCursor;
                    App_Settings app_set = core.ReadSettings;
                    rahasa = new Rahasa(rahasa.generate_secure_yathura(app_set.App.Key));

                    string file = Path.Combine(core.video_entries, file_name + ".mdpv");
                    if (File.Exists(file)) {
                        video_player.Visible = true;
                        play_file = Path.GetTempFileName();
                        play_file = Path.ChangeExtension(play_file, "avi");
                        File.Copy(file, play_file, true);

                        rahasa.DecryptFile(play_file);

                        video_player.URL = play_file;
                        video_player.enableContextMenu = false;

                        video_player.PlayStateChange += Video_player_PlayStateChange;
                        video_player.stretchToFit = true;
                        video_player.settings.enableErrorDialogs = false;
                    }
                    this.Cursor = Cursors.Default;
                } else search_devices();
            } catch(Exception ex) {
                MetroMessageBox.Show(this, "The following error ocurred:" + Environment.NewLine + ex.Message + Environment.NewLine + "If the problem persists, Contact www.infinitum.lk", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.Hide();
                //core.showHide_Form(new Homepage(username), this);
            }
        }

        private void Video_player_PlayStateChange(object sender, AxWMPLib._WMPOCXEvents_PlayStateChangeEvent e) {
            if (e.newState == 3) {
                lblRecordTime.Text = video_player.currentMedia.durationString.Split(':').Length == 2 ? "00 : "+video_player.currentMedia.durationString.Replace(":", " : ") : video_player.currentMedia.durationString.Replace(":", " : ");
            }
        }

        private void Play_video_source_PlayingFinished(object sender, ReasonToFinishPlaying reason) {
        }

        private void video_NewFrame(object sender, NewFrameEventArgs eventArgs) {
            Bitmap bitmap = (Bitmap) eventArgs.Frame.Clone();
            picWebcam.Image = bitmap;
        }

        private string video_file;

        protected override bool ProcessCmdKey(ref Message msg, Keys keyData) {
            if (entry_added)
                return true;

            try {
                if (string.IsNullOrEmpty(file_name)) {
                    #region Commands
                    if (keyData == Keys.F4) {
                        video_file = core.GetMd5Hash(MD5.Create(), date_time.ToString("yyyy-MM-dd") + new Random().Next(0, 1000).ToString());
                        video_file = video_file.Substring(0, 10);

                        string file = Path.Combine(core.video_entries, video_file + ".mdpv");

                        writer.Open(file, width, height, 25, VideoCodec.WMV1, 1000000);
                        record_audio();

                        timer.Enabled = true;
                        lblButDesc.Text = "Press F6 to Stop Recording Diary Entry.";
                        start_time = DateTime.Now;
                    }

                    if (keyData == Keys.F6) {
                        if (w != null && capture != null) {
                            //stop recording
                            capture.Stop();
                            w.Dispose();
                            w = null;
                            capture.Dispose();
                            capture = null;
                        }

                        videoSource.Stop();
                        writer.Close();

                        timer.Stop();
                        pnlRecordIndicator.BackColor = Color.Transparent;
                        lblButDesc.Text = "Press F12 to Save or ESC to Delete Audio Entry.";
                    }

                    if (keyData == Keys.Escape) {
                        this.Hide();
                        //core.showHide_Form(new Homepage(username), this);
                    }

                    if (keyData == Keys.F12) {
                        lblButDesc.Text = "Please Wait a While, Processing Video.";
                        this.Cursor = Cursors.WaitCursor;
                        string formatted = date_time.ToString("yyyy-MM-dd");

                        App_Settings app_set = core.ReadSettings;
                        rahasa = new Rahasa(rahasa.generate_secure_yathura(app_set.App.Key));

                        string file = Path.Combine(core.video_entries, video_file + ".mdpv");

                        string Path_FFMPEG = "ffmpeg.exe";
                        string new_file = @Path.Combine(core.video_entries, video_file + "_t.avi");

                        Process proc = new Process();

                        try {
                            proc.StartInfo.Arguments = $"-i {file} -filter:v 'setpts = 10 * PTS' {file}";
                            proc.StartInfo.UseShellExecute = false;
                            proc.StartInfo.CreateNoWindow = true;
                            proc.StartInfo.RedirectStandardOutput = false;
                            proc.StartInfo.RedirectStandardError = false;
                            proc.StartInfo.FileName = Path_FFMPEG;
                            proc.Start();
                            proc.WaitForExit();

                            proc.StartInfo.Arguments = $"-i {temp_file} -i {file} -acodec copy -vcodec copy {new_file}";
                            proc.Start();
                            proc.WaitForExit();

                            if (File.Exists(new_file)) {
                                with_in_form = true;
                                File.Delete(file);
                                File.Delete(temp_file);
                                File.Copy(new_file, file);
                                File.Delete(new_file);
                            }
                        } catch (Exception ex){
                            MetroMessageBox.Show(this, "The following error ocurred:" + Environment.NewLine + ex.Message + Environment.NewLine + "If the problem persists, Contact www.infinitum.lk", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);
                            this.Hide();
                            //core.showHide_Form(new Homepage(username), this);
                        } finally {
                            proc.WaitForExit();
                            proc.Close();
                        }

                        if (database.Insert("videoEntries", $"NULL, '{username}', '{formatted}', '{video_file}'")) {
                            if (File.Exists(file)) {
                                rahasa.EncryptFile(file);
                                this.Hide();
                                //core.showHide_Form(new Homepage(username), this);
                            }
                        }

                        this.Cursor = Cursors.Default;
                    }
                    #endregion
                } else videoSource.Start();                
            } catch (Exception ex) {
                MetroMessageBox.Show(this, "The following error ocurred:" + Environment.NewLine + ex.Message + Environment.NewLine + "If the problem persists, Contact www.infinitum.lk", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.Hide();
                //core.showHide_Form(new Homepage(username), this);
            }

            return base.ProcessCmdKey(ref msg, keyData);
        }

        void videoSource_NewFrame(object sender, AForge.Video.NewFrameEventArgs eventArgs) {
            image = (Bitmap)eventArgs.Frame.Clone();
            picWebcam.BackgroundImage = (Bitmap)eventArgs.Frame.Clone();

            if (timer.Enabled) writer.WriteVideoFrame(image);
        }

        private void timer_Tick(object sender, EventArgs e) {
            pnlRecordIndicator.BackColor = pnlRecordIndicator.BackColor == Color.Transparent ? Color.Firebrick : Color.Transparent;
            end_time = DateTime.Now.Subtract(start_time);

            lblRecordTime.Text = string.Format("{0} : {1} : {2}", Math.Floor(end_time.TotalHours) < 10 ? "0" + Math.Floor(end_time.TotalHours) : Math.Floor(end_time.TotalHours).ToString(), Math.Floor(end_time.TotalMinutes) < 10 ? "0" + Math.Floor(end_time.TotalMinutes) : Math.Floor(end_time.TotalMinutes).ToString(), Math.Floor(end_time.TotalSeconds) < 10 ? "0" + Math.Floor(end_time.TotalSeconds) : Math.Floor(end_time.TotalSeconds).ToString());
        }

        private void Add_Video_Entry_FormClosed(object sender, FormClosedEventArgs e) {
            try {
                if (videoSource != null && writer != null) {
                    if (videoSource.IsRunning) videoSource.Stop();

                    if (writer.IsOpen) writer.Close();

                    if (w != null && capture != null) {
                        //stop recording
                        capture.Stop();
                        w.Dispose();
                        w = null;
                        capture.Dispose();
                        capture = null;
                    }
                } else {
                    if (File.Exists(play_file)) File.Delete(play_file);

                    video_player.close();
                    video_player.Dispose();
                }

                //core.showHide_Form(new Homepage(username), this);
            } catch (Exception ex) {
                MetroMessageBox.Show(this, "The following error ocurred:" + Environment.NewLine + ex.Message + Environment.NewLine + "If the problem persists, Contact www.infinitum.lk", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);
                this.Hide();
                //core.showHide_Form(new Homepage(username), this);
            }
        }
    }
}