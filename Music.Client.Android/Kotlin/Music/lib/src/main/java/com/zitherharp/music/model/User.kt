package com.zitherharp.music.model

import android.content.Context
import com.zitherharp.music.R
import com.zitherharp.music.core.Preference
import java.util.*

abstract class User(context: Context): Preference(context) {

    companion object {
        const val ID = "userId"
        const val NAME = "userName"
    }

    val id = preferences.getString(ID, UUID.randomUUID().toString())
    val name = preferences.getString(NAME, context.getString(R.string.music_app_name))
}