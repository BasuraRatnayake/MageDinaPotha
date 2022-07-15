namespace MageDinaPotha {
    partial class StartUp {
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(StartUp));
            this.metroLabel1 = new MetroFramework.Controls.MetroLabel();
            this.pictureBox1 = new System.Windows.Forms.PictureBox();
            this.lblVersion = new MetroFramework.Controls.MetroLabel();
            this.metroProgressSpinner1 = new MetroFramework.Controls.MetroProgressSpinner();
            this.pictureBox2 = new System.Windows.Forms.PictureBox();
            this.splash_timer = new System.Windows.Forms.Timer(this.components);
            this.panel2 = new System.Windows.Forms.Panel();
            this.metroLabel11 = new MetroFramework.Controls.MetroLabel();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).BeginInit();
            this.SuspendLayout();
            // 
            // metroLabel1
            // 
            this.metroLabel1.AutoSize = true;
            this.metroLabel1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(244)))), ((int)(((byte)(184)))), ((int)(((byte)(14)))));
            this.metroLabel1.ForeColor = System.Drawing.Color.Black;
            this.metroLabel1.Location = new System.Drawing.Point(292, 99);
            this.metroLabel1.Name = "metroLabel1";
            this.metroLabel1.Size = new System.Drawing.Size(110, 19);
            this.metroLabel1.TabIndex = 3;
            this.metroLabel1.Text = "Mage Dina Potha";
            this.metroLabel1.Theme = MetroFramework.MetroThemeStyle.Light;
            this.metroLabel1.UseCustomForeColor = true;
            this.metroLabel1.UseStyleColors = true;
            // 
            // pictureBox1
            // 
            this.pictureBox1.Image = ((System.Drawing.Image)(resources.GetObject("pictureBox1.Image")));
            this.pictureBox1.Location = new System.Drawing.Point(108, 60);
            this.pictureBox1.Name = "pictureBox1";
            this.pictureBox1.Size = new System.Drawing.Size(299, 50);
            this.pictureBox1.TabIndex = 5;
            this.pictureBox1.TabStop = false;
            // 
            // lblVersion
            // 
            this.lblVersion.FontSize = MetroFramework.MetroLabelSize.Small;
            this.lblVersion.FontWeight = MetroFramework.MetroLabelWeight.Regular;
            this.lblVersion.ForeColor = System.Drawing.Color.Black;
            this.lblVersion.Location = new System.Drawing.Point(110, 164);
            this.lblVersion.Name = "lblVersion";
            this.lblVersion.Size = new System.Drawing.Size(289, 30);
            this.lblVersion.TabIndex = 7;
            this.lblVersion.Text = "Version 2.0.0.0\r\nCopyright (c) 2017 Infinitum. All Rights Reserved.";
            this.lblVersion.Theme = MetroFramework.MetroThemeStyle.Light;
            this.lblVersion.UseCustomForeColor = true;
            // 
            // metroProgressSpinner1
            // 
            this.metroProgressSpinner1.Location = new System.Drawing.Point(465, 168);
            this.metroProgressSpinner1.Maximum = 100;
            this.metroProgressSpinner1.Name = "metroProgressSpinner1";
            this.metroProgressSpinner1.Size = new System.Drawing.Size(26, 28);
            this.metroProgressSpinner1.Style = MetroFramework.MetroColorStyle.Blue;
            this.metroProgressSpinner1.TabIndex = 8;
            this.metroProgressSpinner1.Theme = MetroFramework.MetroThemeStyle.Light;
            this.metroProgressSpinner1.UseSelectable = true;
            this.metroProgressSpinner1.Value = 80;
            // 
            // pictureBox2
            // 
            this.pictureBox2.Image = ((System.Drawing.Image)(resources.GetObject("pictureBox2.Image")));
            this.pictureBox2.Location = new System.Drawing.Point(16, 161);
            this.pictureBox2.Name = "pictureBox2";
            this.pictureBox2.Size = new System.Drawing.Size(89, 38);
            this.pictureBox2.SizeMode = System.Windows.Forms.PictureBoxSizeMode.StretchImage;
            this.pictureBox2.TabIndex = 10;
            this.pictureBox2.TabStop = false;
            // 
            // splash_timer
            // 
            this.splash_timer.Enabled = true;
            this.splash_timer.Interval = 3000;
            this.splash_timer.Tick += new System.EventHandler(this.splash_timer_Tick);
            // 
            // panel2
            // 
            this.panel2.Location = new System.Drawing.Point(23, 11);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(297, 42);
            this.panel2.TabIndex = 12;
            // 
            // metroLabel11
            // 
            this.metroLabel11.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(244)))), ((int)(((byte)(184)))), ((int)(((byte)(14)))));
            this.metroLabel11.Cursor = System.Windows.Forms.Cursors.Default;
            this.metroLabel11.ForeColor = System.Drawing.Color.White;
            this.metroLabel11.Location = new System.Drawing.Point(355, 50);
            this.metroLabel11.Name = "metroLabel11";
            this.metroLabel11.Size = new System.Drawing.Size(10, 19);
            this.metroLabel11.TabIndex = 58;
            this.metroLabel11.Text = "Mage Dina Potha";
            this.metroLabel11.Theme = MetroFramework.MetroThemeStyle.Light;
            this.metroLabel11.UseCustomForeColor = true;
            this.metroLabel11.UseStyleColors = true;
            // 
            // StartUp
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(514, 216);
            this.Controls.Add(this.metroLabel11);
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.pictureBox2);
            this.Controls.Add(this.metroProgressSpinner1);
            this.Controls.Add(this.lblVersion);
            this.Controls.Add(this.metroLabel1);
            this.Controls.Add(this.pictureBox1);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "StartUp";
            this.Resizable = false;
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
            this.Style = MetroFramework.MetroColorStyle.White;
            this.Text = "MageDinaPotha Loading";
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox1)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pictureBox2)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion
        private MetroFramework.Controls.MetroLabel metroLabel1;
        private System.Windows.Forms.PictureBox pictureBox1;
        private MetroFramework.Controls.MetroLabel lblVersion;
        private MetroFramework.Controls.MetroProgressSpinner metroProgressSpinner1;
        private System.Windows.Forms.PictureBox pictureBox2;
        private System.Windows.Forms.Timer splash_timer;
        private System.Windows.Forms.Panel panel2;
        private MetroFramework.Controls.MetroLabel metroLabel11;
    }
}