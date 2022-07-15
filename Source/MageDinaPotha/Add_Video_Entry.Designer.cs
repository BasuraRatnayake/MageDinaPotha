namespace MageDinaPotha {
    partial class Add_Video_Entry {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing) {
            if (disposing && (components != null)) {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent() {
            this.components = new System.ComponentModel.Container();
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Add_Video_Entry));
            this.panel2 = new System.Windows.Forms.Panel();
            this.metroLabel2 = new MetroFramework.Controls.MetroLabel();
            this.lblRecordTime = new MetroFramework.Controls.MetroLabel();
            this.lblButDesc = new MetroFramework.Controls.MetroLabel();
            this.lbl_Date = new MetroFramework.Controls.MetroLabel();
            this.pnlRecordIndicator = new System.Windows.Forms.Panel();
            this.timer = new System.Windows.Forms.Timer(this.components);
            this.picWebcam = new System.Windows.Forms.PictureBox();
            this.video_player = new AxWMPLib.AxWindowsMediaPlayer();
            ((System.ComponentModel.ISupportInitialize)(this.picWebcam)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.video_player)).BeginInit();
            this.SuspendLayout();
            // 
            // panel2
            // 
            this.panel2.Location = new System.Drawing.Point(0, 0);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(734, 5);
            this.panel2.TabIndex = 13;
            // 
            // metroLabel2
            // 
            this.metroLabel2.AutoSize = true;
            this.metroLabel2.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(244)))), ((int)(((byte)(184)))), ((int)(((byte)(14)))));
            this.metroLabel2.Cursor = System.Windows.Forms.Cursors.Arrow;
            this.metroLabel2.ForeColor = System.Drawing.Color.Black;
            this.metroLabel2.Location = new System.Drawing.Point(519, 28);
            this.metroLabel2.Name = "metroLabel2";
            this.metroLabel2.Size = new System.Drawing.Size(102, 19);
            this.metroLabel2.TabIndex = 65;
            this.metroLabel2.Text = "Recording Time";
            this.metroLabel2.Theme = MetroFramework.MetroThemeStyle.Light;
            this.metroLabel2.UseCustomForeColor = true;
            this.metroLabel2.UseStyleColors = true;
            // 
            // lblRecordTime
            // 
            this.lblRecordTime.AutoSize = true;
            this.lblRecordTime.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(244)))), ((int)(((byte)(184)))), ((int)(((byte)(14)))));
            this.lblRecordTime.Cursor = System.Windows.Forms.Cursors.Default;
            this.lblRecordTime.FontSize = MetroFramework.MetroLabelSize.Tall;
            this.lblRecordTime.FontWeight = MetroFramework.MetroLabelWeight.Regular;
            this.lblRecordTime.ForeColor = System.Drawing.Color.Black;
            this.lblRecordTime.Location = new System.Drawing.Point(521, 43);
            this.lblRecordTime.Name = "lblRecordTime";
            this.lblRecordTime.Size = new System.Drawing.Size(100, 25);
            this.lblRecordTime.TabIndex = 64;
            this.lblRecordTime.Text = "00 : 00 : 00";
            this.lblRecordTime.Theme = MetroFramework.MetroThemeStyle.Light;
            this.lblRecordTime.UseCustomForeColor = true;
            this.lblRecordTime.UseStyleColors = true;
            // 
            // lblButDesc
            // 
            this.lblButDesc.AutoSize = true;
            this.lblButDesc.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(244)))), ((int)(((byte)(184)))), ((int)(((byte)(14)))));
            this.lblButDesc.Cursor = System.Windows.Forms.Cursors.Arrow;
            this.lblButDesc.ForeColor = System.Drawing.Color.Black;
            this.lblButDesc.Location = new System.Drawing.Point(23, 50);
            this.lblButDesc.Name = "lblButDesc";
            this.lblButDesc.Size = new System.Drawing.Size(240, 19);
            this.lblButDesc.TabIndex = 63;
            this.lblButDesc.Text = "Press F4 to Start Recording Diary Entry.";
            this.lblButDesc.Theme = MetroFramework.MetroThemeStyle.Light;
            this.lblButDesc.UseCustomForeColor = true;
            this.lblButDesc.UseStyleColors = true;
            // 
            // lbl_Date
            // 
            this.lbl_Date.AutoSize = true;
            this.lbl_Date.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(244)))), ((int)(((byte)(184)))), ((int)(((byte)(14)))));
            this.lbl_Date.Cursor = System.Windows.Forms.Cursors.Arrow;
            this.lbl_Date.FontSize = MetroFramework.MetroLabelSize.Tall;
            this.lbl_Date.ForeColor = System.Drawing.Color.Black;
            this.lbl_Date.Location = new System.Drawing.Point(220, 25);
            this.lbl_Date.Name = "lbl_Date";
            this.lbl_Date.Size = new System.Drawing.Size(115, 25);
            this.lbl_Date.TabIndex = 62;
            this.lbl_Date.Text = "( 2017-06-02 )";
            this.lbl_Date.Theme = MetroFramework.MetroThemeStyle.Light;
            this.lbl_Date.UseCustomForeColor = true;
            this.lbl_Date.UseStyleColors = true;
            // 
            // pnlRecordIndicator
            // 
            this.pnlRecordIndicator.BackColor = System.Drawing.Color.Transparent;
            this.pnlRecordIndicator.Location = new System.Drawing.Point(502, 31);
            this.pnlRecordIndicator.Name = "pnlRecordIndicator";
            this.pnlRecordIndicator.Size = new System.Drawing.Size(15, 15);
            this.pnlRecordIndicator.TabIndex = 67;
            // 
            // timer
            // 
            this.timer.Interval = 1000;
            this.timer.Tick += new System.EventHandler(this.timer_Tick);
            // 
            // picWebcam
            // 
            this.picWebcam.BackColor = System.Drawing.Color.Black;
            this.picWebcam.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Stretch;
            this.picWebcam.Location = new System.Drawing.Point(1, 87);
            this.picWebcam.Name = "picWebcam";
            this.picWebcam.Size = new System.Drawing.Size(642, 370);
            this.picWebcam.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.picWebcam.TabIndex = 0;
            this.picWebcam.TabStop = false;
            // 
            // video_player
            // 
            this.video_player.Enabled = true;
            this.video_player.Location = new System.Drawing.Point(1, 87);
            this.video_player.Name = "video_player";
            this.video_player.OcxState = ((System.Windows.Forms.AxHost.State)(resources.GetObject("video_player.OcxState")));
            this.video_player.Size = new System.Drawing.Size(642, 370);
            this.video_player.TabIndex = 69;
            this.video_player.Visible = false;
            // 
            // Add_Video_Entry
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(644, 461);
            this.Controls.Add(this.video_player);
            this.Controls.Add(this.picWebcam);
            this.Controls.Add(this.pnlRecordIndicator);
            this.Controls.Add(this.metroLabel2);
            this.Controls.Add(this.lblRecordTime);
            this.Controls.Add(this.lblButDesc);
            this.Controls.Add(this.lbl_Date);
            this.Controls.Add(this.panel2);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "Add_Video_Entry";
            this.Resizable = false;
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
            this.Text = "Record Video Entry";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.Add_Video_Entry_FormClosed);
            ((System.ComponentModel.ISupportInitialize)(this.picWebcam)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.video_player)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Panel panel2;
        private MetroFramework.Controls.MetroLabel metroLabel2;
        private MetroFramework.Controls.MetroLabel lblRecordTime;
        private MetroFramework.Controls.MetroLabel lblButDesc;
        private MetroFramework.Controls.MetroLabel lbl_Date;
        private System.Windows.Forms.Panel pnlRecordIndicator;
        private System.Windows.Forms.Timer timer;
        private System.Windows.Forms.PictureBox picWebcam;
        private AxWMPLib.AxWindowsMediaPlayer video_player;
    }
}