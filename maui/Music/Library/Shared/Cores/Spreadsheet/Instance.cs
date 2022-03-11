namespace ZitherHarp.Music.Shared.Cores;

public abstract partial class Spreadsheet
{
    internal sealed class Character
    {
        public const string Empty = "";
        public const string Split = "/";
        public const string Suffix = "; ";
        public const string Concat = " - ";
        public const string Combine = " & ";
    }

    internal abstract class Property
    {
        public const int PrimaryId = 0;
        public const int VietnameseName = 2;
        public const int ChineseName = 3;
        public const int VietnameseDescription = 4;
        public const int ChineseDescription = 5;
    }
}