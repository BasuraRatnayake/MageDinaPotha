using MetroFramework;
using MetroFramework.Forms;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace MageDinaPotha {
    public partial class AboutUs : MetroForm {
        public AboutUs() {
            InitializeComponent();

            lblVersion.Text = $"Version  {Application.ProductVersion}  {Environment.NewLine}Beta Release";
        }

        private void metroLink1_Click(object sender, EventArgs e) { }

        private void metroLink1_Click_1(object sender, EventArgs e) {
            new ThirdParty().ShowDialog();
        }

        private void metroLink2_Click(object sender, EventArgs e) {
            try {
                System.Diagnostics.Process.Start("http://www.infinitum.lk/magedinapotha");
            } catch {
                MetroMessageBox.Show(this, "Diary Entry is Missing From the System, Only the reference is remaining." + Environment.NewLine + "If the problem persists, Contact www.infinitum.lk", "Mage Dina Potha 2", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
    }
}
