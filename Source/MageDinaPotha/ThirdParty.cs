using MetroFramework.Forms;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Diagnostics;
using System.Drawing;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace MageDinaPotha {
    public partial class ThirdParty : MetroForm {
        public ThirdParty() {
            InitializeComponent();
        }

        private void prepare_license() {
            var versionInfo = FileVersionInfo.GetVersionInfo(Assembly.GetEntryAssembly().Location);

            var companyName = versionInfo.CompanyName;
        }
    }
}
