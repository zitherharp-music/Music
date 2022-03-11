package com.zitherharp.music.model

import android.graphics.Color
import com.zitherharp.music.R

enum class Music {
    ALBUM,
    AUDIO,
    ARTIST,
    KARAOKE,
    PHOTO,
    SHORT,
    TELEVISION,
    VIDEO;

    companion object Resource {
        fun Music.getColor(): Int {
            return when (this) {
                AUDIO -> R.color.audio_app_color
                PHOTO -> R.color.photo_app_color
                SHORT -> R.color.short_app_color
                else -> Color.WHITE
            }
        }

        fun Music.getString(): Int {
            return when (this) {
                AUDIO -> R.string.audio_app_name
                PHOTO -> R.string.photo_app_name
                SHORT -> R.string.short_app_name
                else -> R.string.music_app_name
            }
        }
    }
}