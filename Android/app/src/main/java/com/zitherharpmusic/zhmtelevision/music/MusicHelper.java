package com.zitherharpmusic.zhmtelevision.music;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

import static com.zitherharpmusic.zhmtelevision.music.Music.*;

public class MusicHelper {
    @NotNull
    public static String toString(@NotNull List<? extends Music> musics, Language language) {
        StringBuilder stringBuilder = new StringBuilder(EMPTY_CHARACTER);
        for (Music music : musics) {
            stringBuilder.append(music.toString(language)).append(SPACE_CHARACTER);
        }
        return stringBuilder.toString().trim();
    }

    @NotNull
    public static String getNames(@NotNull List<? extends Music> musics, Language language) {
        StringBuilder names = new StringBuilder();
        for (Music music : musics) {
            names.append(music.getName(language)).append(Music.SPLIT_CHARACTER);
        };
        return names.toString().length() != 0 ? names.toString().substring(0, names.toString().length() - 1) : EMPTY_CHARACTER;
    }

    public static Music getRandomItem(@NotNull List<? extends Music> musics) {
        return musics.get(new Random().nextInt(musics.size()));
    }

    @NotNull
    public static String getSongDetail(@NotNull Song song, Language language) {
        return "Đang phát: " + song.getName(language) + ENTER_CHARACTER +
                "Thể hiện: " + getNames(song.getArtists(), language);
    }
}
