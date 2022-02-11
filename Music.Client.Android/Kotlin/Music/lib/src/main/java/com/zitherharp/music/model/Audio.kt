package com.zitherharp.music.model

import com.zitherharp.music.Language
import com.zitherharp.music.core.Youtube

class Audio(id: String, val artistId: String): Youtube(id) {

    companion object {
        private const val ARTIST_ID = 1

        val repository: MutableMap<String, Audio> = HashMap()

        init {
            val jsonValues = getJsonValues(Audio::class.simpleName)
            for (i in 0 until jsonValues.length()) {
                jsonValue = jsonValues.getJSONArray(i)
                repository[jsonValue.requireString(ID)] = Audio(
                    jsonValue.requireString(ID),
                    jsonValue.requireString(ARTIST_ID)).apply {
                        setName(Language.VIETNAMESE, jsonValue.requireString(VIETNAMESE_NAME))
                        setName(Language.CHINESE, jsonValue.requireString(CHINESE_NAME))
                        setDescription(Language.VIETNAMESE, jsonValue.requireString(VIETNAMESE_DESCRIPTION))
                        setDescription(Language.CHINESE, jsonValue.requireString(CHINESE_DESCRIPTION))
                }
            }
        }
    }
}