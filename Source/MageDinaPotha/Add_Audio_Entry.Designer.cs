namespace MageDinaPotha {
    partial class Add_Audio_Entry {
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Add_Audio_Entry));
            this.panel2 = new System.Windows.Forms.Panel();
            this.lbl_Date = new MetroFramework.Controls.MetroLabel();
            this.lblButDesc = new MetroFramework.Controls.MetroLabel();
            this.lblRecordTime = new MetroFramework.Controls.MetroLabel();
            this.metroLabel2 = new MetroFramework.Controls.MetroLabel();
            this.btnPlay_Stop = new MetroFramework.Controls.MetroButton();
            this.pnlRecordIndicator = new System.Windows.Forms.Panel();
            this.timer = new System.Windows.Forms.Timer(this.components);
            this.SuspendLayout();
            // 
            // panel2
            // 
            this.panel2.Location = new System.Drawing.Point(0, 0);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(734, 5);
            this.panel2.TabIndex = 12;
            // 
            // lbl_Date
            // 
            this.lbl_Date.AutoSize = true;
            this.lbl_Date.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(244)))), ((int)(((byte)(184)))), ((int)(((byte)(14)))));
            this.lbl_Date.Cursor = System.Windows.Forms.Cursors.Arrow;
            this.lbl_Date.FontSize = MetroFramework.MetroLabelSize.Tall;
            this.lbl_Date.ForeColor = System.Drawing.Color.Black;
            this.lbl_Date.Location = new System.Drawing.Point(222, 25);
            this.lbl_Date.Name = "lbl_Date";
            this.lbl_Date.Size = new System.Drawing.Size(115, 25);
            this.lbl_Date.TabIndex = 16;
            this.lbl_Date.Text = "( 2017-06-02 )";
            this.lbl_Date.Theme = MetroFramework.MetroThemeStyle.Light;
            this.lbl_Date.UseCustomForeColor = true;
            this.lbl_Date.UseStyleColors = true;
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
            this.lblButDesc.TabIndex = 59;
            this.lblButDesc.Text = "Press F4 to Start Recording Diary Entry.";
            this.lblButDesc.Theme = MetroFramework.MetroThemeStyle.Light;
            this.lblButDesc.UseCustomForeColor = true;
            this.lblButDesc.UseStyleColors = true;
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
            this.lblRecordTime.TabIndex = 60;
            this.lblRecordTime.Text = "00 : 00 : 00";
            this.lblRecordTime.Theme = MetroFramework.MetroThemeStyle.Light;
            this.lblRecordTime.UseCustomForeColor = true;
            this.lblRecordTime.UseStyleColors = true;
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
            this.metroLabel2.TabIndex = 61;
            this.metroLabel2.Text = "Recording Time";
            this.metroLabel2.Theme = MetroFramework.MetroThemeStyle.Light;
            this.metroLabel2.UseCustomForeColor = true;
            this.metroLabel2.UseStyleColors = true;
            // 
            // btnPlay_Stop
            // 
            this.btnPlay_Stop.ForeColor = System.Drawing.Color.Black;
            this.btnPlay_Stop.Location = new System.Drawing.Point(23, 82);
            this.btnPlay_Stop.Name = "btnPlay_Stop";
            this.btnPlay_Stop.Size = new System.Drawing.Size(162, 23);
            this.btnPlay_Stop.TabIndex = 62;
            this.btnPlay_Stop.TabStop = false;
            this.btnPlay_Stop.Text = "Play Diary Entry";
            this.btnPlay_Stop.UseSelectable = true;
            this.btnPlay_Stop.Visible = false;
            this.btnPlay_Stop.Click += new System.EventHandler(this.btnPlay_Stop_Click);
            // 
            // pnlRecordIndicator
            // 
            this.pnlRecordIndicator.BackColor = System.Drawing.Color.Transparent;
            this.pnlRecordIndicator.Location = new System.Drawing.Point(502, 31);
            this.pnlRecordIndicator.Name = "pnlRecordIndicator";
            this.pnlRecordIndicator.Size = new System.Drawing.Size(15, 15);
            this.pnlRecordIndicator.TabIndex = 63;
            // 
            // timer
            // 
            this.timer.Interval = 1000;
            this.timer.Tick += new System.EventHandler(this.timer_Tick);
            // 
            // Add_Audio_Entry
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(644, 89);
            this.Controls.Add(this.pnlRecordIndicator);
            this.Controls.Add(this.btnPlay_Stop);
            this.Controls.Add(this.metroLabel2);
            this.Controls.Add(this.lblRecordTime);
            this.Controls.Add(this.lblButDesc);
            this.Controls.Add(this.lbl_Date);
            this.Controls.Add(this.panel2);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.Name = "Add_Audio_Entry";
            this.Resizable = false;
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
            this.Text = "Record Audio Entry";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.Add_Audio_Entry_FormClosed);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Panel panel2;
        private MetroFramework.Controls.MetroLabel lbl_Date;
        private MetroFramework.Controls.MetroLabel lblButDesc;
        private MetroFramework.Controls.MetroLabel lblRecordTime;
        private MetroFramework.Controls.MetroLabel metroLabel2;
        private MetroFramework.Controls.MetroButton btnPlay_Stop;
        private System.Windows.Forms.Panel pnlRecordIndicator;
        private System.Windows.Forms.Timer timer;
    }
}