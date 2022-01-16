﻿namespace Music.Television.Wpf.ViewModels;

internal class HorizontalViewModel : MainViewModel
{
    private static HorizontalViewModel? instance;
    public static HorizontalViewModel Instance => instance ??= new();

    public string? VoteSyntax => "Soạn tin: ZHM " + Songs.GetRandom().GetName(Language);
}