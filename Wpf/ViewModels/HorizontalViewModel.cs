using Library.Enums;
using Library.Utils;
using System;

namespace Wpf.ViewModels;

internal class HorizontalViewModel : MainViewModel
{
    private static HorizontalViewModel? instance;
    public static new HorizontalViewModel Instance => instance ??= new();

    public string VoteSyntax
    {
        get
        {
            return Language switch
            {
                Language.Vietnamese => $"Soạn tin: ZHM { Songs.GetRandom().Id.GetNumberId() }",
                _ => throw new NotImplementedException()
            };
        }
    }
}