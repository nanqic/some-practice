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
    public partial class ArraySort : Form
    {
        public int[] arr = new int[10];
        Random r = new Random();
        public ArraySort()
        {
            InitializeComponent();
        }
        private void button1_Click(object sender, EventArgs e)
        {
            Array.Sort(arr);
            if (label5.Text == "")
            {
                showArr(label5, arr);
            }
        }

        private void button2_Click(object sender, EventArgs e)
        {
            Array.Sort(arr);
            Array.Reverse(arr);
            if (label6.Text == "")
            {
                showArr(label6, arr);
            }
        }

        private void ArraySort_Load(object sender, EventArgs e)
        {
            for (int i = 0; i < arr.Length; i++)
            {
                int temp = r.Next(1, 21);
                if (!arr.Contains(temp))
                {
                    arr[i] = temp;
                }
                else
                {
                    i--;
                }
            }
            showArr(label4, arr);
            

        }
        private void showArr(Label label, int[] arr)
        {//将数组信息显示再label上
            for (int i = 0; i < arr.Length; i++)
            {
                label.Text += arr[i].ToString()+" ";
            }
        }
    }
}
