package com.zitherharp.music

import com.zitherharp.music.model.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL

object Repository {
    val artistMap: MutableMap<String, Artist> = HashMap()
    val audioMap: MutableMap<String, Audio> = HashMap()
    val shortMap: MutableMap<String, Short>? = null
        get() {
            return field ?: HashMap()
    }

    init {
        var jsonValue: JSONArray
        // TODO: Load artists
        var jsonValues = getJsonValues("artist")
        for (i in 0 until jsonValues.length()) {
            jsonValue = jsonValues.getJSONArray(i)
            artistMap[jsonValue.requireString(Artist.ID)] = Artist(jsonValue.requireString(Artist.ID)).apply {
                setName(Language.VIETNAMESE, jsonValue.requireString(Artist.VIETNAMESE_NAME))
                setName(Language.CHINESE, jsonValue.requireString(Artist.CHINESE_NAME))
                setDescription(Language.VIETNAMESE, jsonValue.requireString(Artist.VIETNAMESE_DESCRIPTION))
                setDescription(Language.CHINESE, jsonValue.requireString(Artist.CHINESE_DESCRIPTION))
            }
        }
        // TODO: Load audios
        jsonValues = getJsonValues("audio")
        for (i in 0 until jsonValues.length()) {
            jsonValue = jsonValues.getJSONArray(i)
            audioMap[jsonValue.requireString(Artist.ID)] = Audio(jsonValue.requireString(Audio.ID),
                jsonValue.requireInt(Audio.DURATION), jsonValue.requireString(Audio.ARTIST_ID)).apply {
                setName(Language.VIETNAMESE, jsonValue.requireString(Audio.VIETNAMESE_NAME))
                setName(Language.CHINESE, jsonValue.requireString(Audio.CHINESE_NAME))
            }
        }
    }

    private fun getJsonValues(name: String): JSONArray {
        val url = "https://sheets.googleapis.com/v4/spreadsheets/" +
                "1znQOtTDJz0UqDs0uB2MQZV3wN0l_J0TrU44d9chH2SI/values/" +
                "$name?key=AIzaSyAD91OiEeWRoqhsw0peq94qg5joZe47r_s"
        val jsonString = URL(url).readText()
        val jsonObject = JSONObject(jsonString)
        return jsonObject.getJSONArray("values")
    }

    private fun JSONArray.requireInt(index: Int) = try { getInt(index) } catch (e: Exception) { 0 }
    private fun JSONArray.requireString(index: Int) = try { getString(index) } catch (e: Exception) { "" }
}