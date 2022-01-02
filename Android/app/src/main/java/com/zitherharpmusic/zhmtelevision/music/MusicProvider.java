package com.zitherharpmusic.zhmtelevision.music;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.zitherharpmusic.zhmtelevision.music.Language.*;
import static com.zitherharpmusic.zhmtelevision.music.Music.*;

public class MusicProvider {
    private static List<Song> songs;
    private static List<Artist> artists;

    private static JSONArray songArray, artistArray;

    private static String primaryId, referenceId;
    private static String vietnameseName, pinyinName, simplifiedChineseName, traditionalChineseName;
    private static String vietnameseDescription, simplifiedChineseDescription, traditionalChineseDescription;
    private static int duration;

    public static void initialize() {
        songArray = getJSONArray(getJSONUrl("audio"), "values");
        artistArray = getJSONArray(getJSONUrl("artist"), "values");
    }

    @NotNull
    @Contract(pure = true)
    private static String getJSONUrl(String tableName) {
        return "https://sheets.googleapis.com/v4/spreadsheets/1znQOtTDJz0UqDs0uB2MQZV3wN0l_J0TrU44d9chH2SI/values/" +
                tableName + "?key=AIzaSyAD91OiEeWRoqhsw0peq94qg5joZe47r_s";
    }

    private static JSONArray getJSONArray(String url, String arrayName) {
        JSONArray jsonArray = null;
        try {
            Scanner scanner = new Scanner(new URL(url).openStream());
            StringBuilder jsonString = new StringBuilder(scanner.next());
            while (scanner.hasNextLine()) {
                jsonString.append(scanner.nextLine());
            }
            JSONObject jsonObj = new JSONObject(jsonString.toString());
            jsonArray = jsonObj.getJSONArray(arrayName);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    private static JSONArray getJSONArray(@NotNull JSONArray jsonArray, int index) {
        JSONArray valueArray = null;
        try {
            valueArray = jsonArray.getJSONArray(index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return valueArray;
    }

    private static String getString(@NotNull JSONArray jsonArray, int index) {
        String string = EMPTY_CHARACTER;
        try {
            if (!jsonArray.isNull(index)) {
                string = jsonArray.getString(index);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return string;
    }

    private static int getInt(@NotNull JSONArray jsonArray, int index) {
        int integer = 0;
        try {
            if (!jsonArray.isNull(index)) {
                integer = jsonArray.getInt(index);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return integer;
    }

    public static List<Song> getSongs() {
        if (songs == null) {
            Song song;
            JSONArray jsonArray;
            songs = new ArrayList<>();
            for (int i = 0; i < songArray.length(); i++) {
                jsonArray = getJSONArray(songArray, i);
                primaryId = getString(jsonArray, PRIMARY_ID);
                referenceId = getString(jsonArray, REFERENCE_ID);
                vietnameseName = getString(jsonArray, VIETNAMESE_NAME);
                pinyinName = getString(jsonArray, PINYIN_NAME);
                simplifiedChineseName = getString(jsonArray, SIMPLIFIED_CHINESE_NAME);
                traditionalChineseName = getString(jsonArray, TRADITIONAL_CHINESE_NAME);
                duration = getInt(jsonArray, Song.DURATION);
                if (!primaryId.equals(EMPTY_CHARACTER)) {
                    song = new Song(primaryId, referenceId, duration);
                    song.setName(PINYIN, pinyinName)
                            .setName(VIETNAMESE, vietnameseName)
                            .setName(SIMPLIFIED_CHINESE, simplifiedChineseName)
                            .setName(TRADITIONAL_CHINESE, traditionalChineseName);
                    songs.add(song);
                }
            }
        }
        return songs;
    }

    public static List<Artist> getArtists() {
        if (artists == null) {
            Artist artist;
            JSONArray jsonArray;
            artists = new ArrayList<>();
            for (int i = 0; i < artistArray.length(); i++) {
                jsonArray = getJSONArray(artistArray, i);
                primaryId = getString(jsonArray, PRIMARY_ID);
                pinyinName = getString(jsonArray, PINYIN_NAME);
                vietnameseName = getString(jsonArray, VIETNAMESE_NAME);
                simplifiedChineseName = getString(jsonArray, SIMPLIFIED_CHINESE_NAME);
                traditionalChineseName = getString(jsonArray, TRADITIONAL_CHINESE_NAME);
                if (!primaryId.equals(EMPTY_CHARACTER)) {
                    artist = new Artist(primaryId);
                    artist.setName(PINYIN, pinyinName)
                            .setName(VIETNAMESE, vietnameseName)
                            .setName(SIMPLIFIED_CHINESE, simplifiedChineseName)
                            .setName(TRADITIONAL_CHINESE, traditionalChineseName);
                    artists.add(artist);
                }
            }
        }
        return artists;
    }
}
