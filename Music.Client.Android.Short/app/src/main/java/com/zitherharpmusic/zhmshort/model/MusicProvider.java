package com.zitherharpmusic.zhmshort.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.zitherharpmusic.zhmshort.model.Music.*;
import static com.zitherharpmusic.zhmshort.model.Video.*;

public class MusicProvider {
    private static List<Song> songs;
    private static List<Video> videos;
    private static List<Artist> artists;

    private static JSONArray jsonArray, valueArray;
    private static String primaryId, referenceId;
    private static String vietnameseName, pinyinName, simplifiedChineseName, traditionalChineseName;
    private static String vietnameseDescription, simplifiedChineseDescription, traditionalChineseDescription;

    public static void initialize() {
        try {
            loadSongs();
            loadVideos();
            loadArtists();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static String getSheetUrl(String tableName) {
        return "https://sheets.googleapis.com/v4/spreadsheets/1znQOtTDJz0UqDs0uB2MQZV3wN0l_J0TrU44d9chH2SI/values/" +
                tableName + "?key=AIzaSyAD91OiEeWRoqhsw0peq94qg5joZe47r_s";
    }

    private static JSONArray getJSONArray(String url, String arrayName) throws JSONException {
        JSONArray jsonArray = null;
        try {
            Scanner scanner = new Scanner(new URL(url).openStream());
            StringBuilder jsonString = new StringBuilder();
            jsonString.append(scanner.next());
            while (scanner.hasNextLine()) {
                jsonString.append(scanner.nextLine());
            }
            JSONObject jsonObj = new JSONObject(jsonString.toString());
            jsonArray = jsonObj.getJSONArray(arrayName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    private static String getString(JSONArray jsonArray, int index) throws JSONException {
        return !jsonArray.isNull(index) ? jsonArray.getString(index) : EMPTY_CHARACTER;
    }

    public static List<Song> getSongs() {
        return songs;
    }

    public static List<Video> getVideos() {
        return videos;
    }

    public static List<Artist> getArtists() {
        return artists;
    }

    private static void loadSongs() throws JSONException {
        if (songs == null) {
            Song song;
            songs = new ArrayList<>();
            jsonArray = getJSONArray(getSheetUrl("audio"), "values");
            for (int i = 0; i < jsonArray.length(); i++) {
                valueArray = jsonArray.getJSONArray(i);
                primaryId = getString(valueArray, PRIMARY_ID);
                referenceId = getString(valueArray, REFERENCE_ID);
                vietnameseName = getString(valueArray, VIETNAMESE_NAME);
                pinyinName = getString(valueArray, PINYIN_NAME);
                simplifiedChineseName = getString(valueArray, SIMPLIFIED_CHINESE_NAME);
                traditionalChineseName = getString(valueArray, TRADITIONAL_CHINESE_NAME);
                vietnameseDescription = getString(valueArray, VIETNAMESE_DESCRIPTION);
                simplifiedChineseDescription = getString(valueArray, SIMPLIFIED_CHINESE_DESCRIPTION);
                traditionalChineseDescription = getString(valueArray, TRADITIONAL_CHINESE_DESCRIPTION);
                if (!primaryId.equals(EMPTY_CHARACTER)) {
                    song = new Song(primaryId, referenceId);
                    song.setName(vietnameseName, pinyinName, simplifiedChineseName, traditionalChineseName);
                    song.setDescription(vietnameseDescription, simplifiedChineseDescription, traditionalChineseDescription);
                    songs.add(song);
                }
            }
        }
    }

    private static void loadVideos() throws JSONException {
        if (videos == null) {
            Video video;
            videos = new ArrayList<>();
            jsonArray = getJSONArray(getSheetUrl("short"), "values");
            String artistId, songId;
            for (int i = 0; i < jsonArray.length(); i++) {
                valueArray = jsonArray.getJSONArray(i);
                primaryId = getString(valueArray, PRIMARY_ID);
                artistId = getString(valueArray, ARTIST_ID);
                songId = getString(valueArray, SONG_ID);
                video = new Video(primaryId, artistId, songId);
                videos.add(video);
            }
        }
    }

    private static void loadArtists() throws JSONException {
        if (artists == null) {
            Artist artist;
            artists = new ArrayList<>();
            jsonArray = getJSONArray(getSheetUrl("artist"), "values");
            for (int i = 0; i < jsonArray.length(); i++) {
                valueArray = jsonArray.getJSONArray(i);
                primaryId = getString(valueArray, PRIMARY_ID);
                vietnameseName = getString(valueArray, VIETNAMESE_NAME);
                pinyinName = getString(valueArray, PINYIN_NAME);
                simplifiedChineseName = getString(valueArray, SIMPLIFIED_CHINESE_NAME);
                traditionalChineseName = getString(valueArray, TRADITIONAL_CHINESE_NAME);
                vietnameseDescription = getString(valueArray, VIETNAMESE_DESCRIPTION);
                simplifiedChineseDescription = getString(valueArray, SIMPLIFIED_CHINESE_DESCRIPTION);
                traditionalChineseDescription = getString(valueArray, TRADITIONAL_CHINESE_DESCRIPTION);
                artist = new Artist(primaryId);
                artist.setName(vietnameseName, pinyinName, simplifiedChineseName, traditionalChineseName);
                artist.setDescription(vietnameseDescription, simplifiedChineseDescription, traditionalChineseDescription);
                artists.add(artist);
            }
        }
    }
}
