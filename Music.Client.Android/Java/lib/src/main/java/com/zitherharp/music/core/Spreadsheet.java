package com.zitherharpmusic.android.core;

import com.zitherharpmusic.android.model.Artist;
import com.zitherharpmusic.android.model.Audio;

import java.util.List;

public class Spreadsheet {
    public String Id;
    public String VietnameseName;
    public String ChineseName;

    public static class Repository {
        private static List<Audio> audios;

        public static List<Audio> getAudios() {
            return null;
        }

        public List<Artist> getArtists() {
            return null;
        }
    }
}
