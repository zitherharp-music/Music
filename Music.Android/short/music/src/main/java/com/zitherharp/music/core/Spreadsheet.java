package com.zitherharp.music.core;

import com.zitherharp.music.model.Artist;
import com.zitherharp.music.model.Audio;
import com.zitherharp.music.type.Language;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Spreadsheet {
    public String Id;
    public String VietnameseName;
    public String ChineseName;

    protected static final String emptyChar = "";
    protected static final String splitChar = "/";

    public String getName(@NotNull Language language) {
        switch (language) {
            case VIETNAMESE:
                return VietnameseName;
            case SIMPLIFIED_CHINESE:
                return ChineseName;
        }
        return null;
    }

    private static Element element;

    private static Element getElement() {
        if (element == null) {
            try {
                File file = new File("spreadsheet.xml");
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document document = documentBuilder.parse(file);
                element = document.getDocumentElement();
            } catch (IOException | SAXException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
        return element;
    }

    static class Repository {
        private static List<Audio> audios;

        public static List<Audio> getAudios() {
            if (audios == null) {
                Audio audio;
                JSONArray jsonArray;
                audios = new ArrayList<>();
                JSONArray valueArray = Serializer.getValues("audio", "values");
                for (int i = 0; i < valueArray.length(); i++) {
                    jsonArray = Serializer.getJsonArray(valueArray, i);
                    audio = new Audio();
                    audio.Id = Serializer.getString(jsonArray, 0);
                    audios.add(audio);
                }
            }
            return audios;
        }

        public static List<Artist> getArtists() {
            return null;
        }
    }

    static class Serializer {
        private static JSONArray getValues(String id, String range) {
            String key = getElement().getElementsByTagName("key").item(0).getNodeValue();
            String url = String.format("https://sheets.googleapis.com/v4/spreadsheets/%s/values/%s?key=%s", id, range, key);
            JSONArray jsonArray = null;
            try {
                Scanner scanner = new Scanner(new URL(url).openStream());
                StringBuilder jsonString = new StringBuilder(scanner.next());
                while (scanner.hasNextLine()) {
                    jsonString.append(scanner.nextLine());
                }
                JSONObject jsonObj = new JSONObject(jsonString.toString());
                jsonArray = jsonObj.getJSONArray("values");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return jsonArray;
        }

        private static JSONArray getJsonArray(@NotNull JSONArray jsonArray, int index) {
            JSONArray valueArray = null;
            try {
                valueArray = jsonArray.getJSONArray(index);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return valueArray;
        }

        private static String getString(@NotNull JSONArray jsonArray, int index) {
            String string = null;
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
    }
}
