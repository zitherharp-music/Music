package com.zitherharp.music.model

import com.zitherharp.music.Language
import com.zitherharp.music.core.QQMusic

class Artist(id: String): QQMusic(id) {

    override fun getImageUrl(image: Image) =
        "https://y.qq.com/music/photo_new/T001R${image.size}x${image.size}M000$id.jpg"

    companion object {
        val repository: MutableMap<String, Artist> = HashMap()

        init {
            val jsonValues = getJsonValues(Artist::class.simpleName)
            for (i in 0 until jsonValues.length()) {
                jsonValue = jsonValues.getJSONArray(i)
                repository[jsonValue.requireString(ID)] = Artist(
                    jsonValue.requireString(ID)).apply {
                        setName(Language.VIETNAMESE, jsonValue.requireString(VIETNAMESE_NAME))
                        setName(Language.CHINESE, jsonValue.requireString(CHINESE_NAME))
                        setDescription(Language.VIETNAMESE, jsonValue.requireString(VIETNAMESE_DESCRIPTION))
                        setDescription(Language.CHINESE, jsonValue.requireString(CHINESE_DESCRIPTION))
                }
            }
        }

        fun Artist.getShorts(): List<Short> {
            val shorts = ArrayList<Short>()
            for (short in Short.repository.values) {
                for (artistId in short.artistId.split(SPLIT_CHAR)) {
                    if (artistId == id) {
                        shorts.add(short)
                    }
                }
            }
            return shorts
        }

        fun Artist.getAudios(): List<Audio> {
            val audios = ArrayList<Audio>()
            for (audio in Audio.repository.values) {
                for (artistId in audio.artistId.split(SPLIT_CHAR)) {
                    if (artistId == id) {
                        audios.add(audio)
                    }
                }
            }
            return audios
        }
    }
}