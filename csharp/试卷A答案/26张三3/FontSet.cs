using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace _26张三3
{
    public partial class FontSet : Form
    {
        public FontSet()
        {
            InitializeComponent();
        }

        private void radioButton1_CheckedChanged(object sender, EventArgs e)
        {
            this.label1.Font = new Font("华文彩云", label1.Font.Size);
        }

        private void radioButton2_CheckedChanged(object sender, EventArgs e)
        {
            this.label1.Font = new Font("隶书", label1.Font.Size);
        }

        private void radioButton3_CheckedChanged(object sender, EventArgs e)
        {
            this.label1.Font = new Font(label1.Font.Name, 20.2f);
        }

        private void radioButton4_CheckedChanged(object sender, EventArgs e)
        {
            this.label1.Font = new Font(label1.Font.Name, 40.2f);
        }
    }
}
