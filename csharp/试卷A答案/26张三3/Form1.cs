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
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            this.toolStripStatusLabel2.Text = DateTime.Now.ToString();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            new ArraySort().ShowDialog();
        }

        private void button2_Click(object sender, EventArgs e)
        {
            new MovingLetter().ShowDialog();
        }

        private void button3_Click(object sender, EventArgs e)
        {
            new DrawPicture().ShowDialog();
        }

        private void button4_Click(object sender, EventArgs e)
        {
            new FontSet().ShowDialog();
        }

        private void 考生信息ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.label1.Text = "26张三\n我是最棒的!\n";
        }

        private void 退出系统ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.Close();
        }

        private void 数组ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            new ArraySort().ShowDialog();
        }

        private void 移动的字母ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            new MovingLetter().ShowDialog();

        }

        private void 画图ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            new DrawPicture().ShowDialog();

        }

        private void 字体设置ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            new FontSet().ShowDialog();

        }

        private void 帮助主题ToolStripMenuItem_Click(object sender, EventArgs e)
        {
            this.label1.Text = "单击按钮即可查看实例!";
        }
    }
}
