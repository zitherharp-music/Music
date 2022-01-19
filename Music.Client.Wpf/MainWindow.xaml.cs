using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Music.Client.Wpf
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        public MainWindow()
        {
            InitializeComponent();
            List<User> items = new();
            items.Add(new User() { Name = "John Doe", Description = "njt" });
            items.Add(new User() { Name = "Jane Doe", Description = "njt" });
            items.Add(new User() { Name = "Sammy Doe", Description = "njt" });
            lvUsers.ItemsSource = items;
            Hide();
            new Television.Views.MainWindow().Show();
        }

        class User
        {
            public string Name { get; set; }
            public string Description { get; set; }
        }
    }
}
