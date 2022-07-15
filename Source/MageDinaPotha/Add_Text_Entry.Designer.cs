namespace MageDinaPotha {
    partial class Add_Text_Entry {
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
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(Add_Text_Entry));
            this.panel2 = new System.Windows.Forms.Panel();
            this.lbl_Date = new MetroFramework.Controls.MetroLabel();
            this.textEditor = new YARTE.UI.HtmlEditor();
            this.metroLabel47 = new MetroFramework.Controls.MetroLabel();
            this.SuspendLayout();
            // 
            // panel2
            // 
            this.panel2.Location = new System.Drawing.Point(0, 0);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(734, 5);
            this.panel2.TabIndex = 11;
            // 
            // lbl_Date
            // 
            this.lbl_Date.AutoSize = true;
            this.lbl_Date.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(244)))), ((int)(((byte)(184)))), ((int)(((byte)(14)))));
            this.lbl_Date.Cursor = System.Windows.Forms.Cursors.Default;
            this.lbl_Date.FontSize = MetroFramework.MetroLabelSize.Tall;
            this.lbl_Date.ForeColor = System.Drawing.Color.Black;
            this.lbl_Date.Location = new System.Drawing.Point(185, 25);
            this.lbl_Date.Name = "lbl_Date";
            this.lbl_Date.Size = new System.Drawing.Size(115, 25);
            this.lbl_Date.TabIndex = 15;
            this.lbl_Date.Text = "( 2017-06-02 )";
            this.lbl_Date.Theme = MetroFramework.MetroThemeStyle.Light;
            this.lbl_Date.UseCustomForeColor = true;
            this.lbl_Date.UseStyleColors = true;
            // 
            // textEditor
            // 
            this.textEditor.Html = resources.GetString("textEditor.Html");
            this.textEditor.Location = new System.Drawing.Point(23, 82);
            this.textEditor.Name = "textEditor";
            this.textEditor.ReadOnly = false;
            this.textEditor.ShowToolbar = true;
            this.textEditor.Size = new System.Drawing.Size(689, 459);
            this.textEditor.TabIndex = 57;
            // 
            // metroLabel47
            // 
            this.metroLabel47.AutoSize = true;
            this.metroLabel47.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(244)))), ((int)(((byte)(184)))), ((int)(((byte)(14)))));
            this.metroLabel47.Cursor = System.Windows.Forms.Cursors.Default;
            this.metroLabel47.ForeColor = System.Drawing.Color.Black;
            this.metroLabel47.Location = new System.Drawing.Point(23, 50);
            this.metroLabel47.Name = "metroLabel47";
            this.metroLabel47.Size = new System.Drawing.Size(178, 19);
            this.metroLabel47.TabIndex = 58;
            this.metroLabel47.Text = "Press F12 to Save Diary Entry";
            this.metroLabel47.Theme = MetroFramework.MetroThemeStyle.Light;
            this.metroLabel47.UseCustomForeColor = true;
            this.metroLabel47.UseStyleColors = true;
            // 
            // Add_Text_Entry
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(729, 558);
            this.Controls.Add(this.metroLabel47);
            this.Controls.Add(this.textEditor);
            this.Controls.Add(this.lbl_Date);
            this.Controls.Add(this.panel2);
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.KeyPreview = true;
            this.MaximizeBox = false;
            this.Name = "Add_Text_Entry";
            this.Resizable = false;
            this.SizeGripStyle = System.Windows.Forms.SizeGripStyle.Hide;
            this.Text = "Write Text Entry";
            this.FormClosed += new System.Windows.Forms.FormClosedEventHandler(this.Add_Text_Entry_FormClosed);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Panel panel2;
        private MetroFramework.Controls.MetroLabel lbl_Date;
        private YARTE.UI.HtmlEditor textEditor;
        private MetroFramework.Controls.MetroLabel metroLabel47;
    }
}