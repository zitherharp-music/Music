package com.zitherharpmusic.zhmshort.service;

import com.zitherharpmusic.zhmshort.model.Artist;
import com.zitherharpmusic.zhmshort.model.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MusicService {
    private static final int PRIMARY_ID = 0;
    private static final int REFERENCE_ID = 1;
    private static final int VIETNAMESE_NAME = 2;
    private static final int SIMPLIFIED_CHINESE_NAME = 3;
    private static final int TRADITIONAL_CHINESE_NAME = 4;
    private static final int PINYIN_NAME = 5;
    private static final int VIETNAMESE_DESCRIPTION = 6;
    private static final int SIMPLIFIED_CHINESE_DESCRIPTION = 7;
    private static final int TRADITIONAL_CHINESE_DESCRIPTION = 8;
    private static final int DURATION = 6;

    private static List<Song> songs;
    private static List<Artist> artists;

    private static JSONArray getJSONArray(String url, String arrayName) {
        JSONArray jsonArray = null;
        try {
            Scanner scanner = new Scanner(new URL(url).openStream());
            StringBuilder jsonString = new StringBuilder(scanner.next());
            while (scanner.hasNextLine()) {
                jsonString.append(scanner.nextLine());
            }
            JSONObject jsonObject = new JSONObject(jsonString.toString());
            jsonArray = jsonObject.getJSONArray(arrayName);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}
