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
    public partial class MovingLetter : Form
    {
        public MovingLetter()
        {
            InitializeComponent();
        }

        private void MovingLetter_Load(object sender, EventArgs e)
        {
            this.label.Location = new Point(200, this.label.Location.Y);
            timer1.Start();

        }

        private void timer1_Tick(object sender, EventArgs e)
        {
            while(label.Location.X == -label.Width)
            {
                this.label.Location = new Point(200, this.label.Location.Y);
            }
     
            label.Location = new Point(this.label.Location.X-5, this.label.Location.Y);
    
        }

        private void button1_Click(object sender, EventArgs e)
        {
            ColorDialog colorDialog = new ColorDialog();
            colorDialog.ShowDialog();
            this.label.ForeColor = colorDialog.Color;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            FontDialog fontDialog = new FontDialog();
            fontDialog.ShowDialog();
            this.label.Font = fontDialog.Font;
        }
    }
}
