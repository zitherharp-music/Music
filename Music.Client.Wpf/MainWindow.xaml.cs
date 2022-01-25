using System.Collections.Generic;
using System.Windows;

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
            public string? Name { get; set; }
            public string? Description { get; set; }
        }
    }
}
