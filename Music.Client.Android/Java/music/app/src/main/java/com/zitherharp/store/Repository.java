package com.zitherharp.store;

import androidx.annotation.NonNull;

import com.zitherharp.store.model.Application;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Repository {
    @NonNull
    public static List<Application> getApplications() {
        Application application;
        JSONArray jsonValue, jsonValues = getJSONArray();
        List<Application> applications = new ArrayList<>();
        for (int i = 0; i < jsonValues.length(); i++) {
            jsonValue = getJSONArray(jsonValues, i);
            application = new Application();
            application.id = getString(jsonValue, 0);
            application.iconId = getString(jsonValue, 1);
            application.name = getString(jsonValue, 2);
            application.description = getString(jsonValue, 3);
            application.version = getString(jsonValue, 4);
            application.size = getInt(jsonValue, 5);
            application.link = getString(jsonValue, 6);
        }
        return applications;
    }

    private static JSONArray getJSONArray() {
        JSONArray jsonArray = null;
        String url = "https://sheets.googleapis.com/v4/spreadsheets/" +
                "1znQOtTDJz0UqDs0uB2MQZV3wN0l_J0TrU44d9chH2SI/values/" +
                "application?key=AIzaSyAD91OiEeWRoqhsw0peq94qg5joZe47r_s";
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
        String string = "";
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
