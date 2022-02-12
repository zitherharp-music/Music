package com.zitherharp.music.shorts.ui.user

import android.content.Context
import com.zitherharp.music.R
import java.util.*

data class User(val context: Context) {
    private val preferences =
        context.getSharedPreferences(User::class.java.name, Context.MODE_PRIVATE)

    val id = preferences.getString(ID, UUID.randomUUID().toString())
    val name = preferences.getString(NAME, context.getString(R.string.short_app_name))
    val shortId = preferences.getString(SHORT_ID, null)
    val audioId = preferences.getString(AUDIO_ID, null)
    val artistId = preferences.getString(ARTIST_ID, null)

    companion object {
        const val ID = "userId"
        const val NAME = "userName"
        const val SHORT_ID = "userShortId"
        const val AUDIO_ID = "userAudioId"
        const val ARTIST_ID = "userArtistId"
    }

    fun rename(name: String) {
        preferences.edit().apply {
            putString(NAME, name)
        }.apply()
    }

    fun put(key: String, value: String) {
        preferences.edit().apply {
            putString(key, value)
        }.apply()
    }
}