package com.zitherharp.music.photo.ui.user

import android.content.Context
import com.zitherharp.music.model.User

class User(context: Context): User(context) {

    companion object {
        const val PHOTO_ID = "photoId"
        const val ARTIST_ID = "artistId"
        const val SEARCH_QUERY = "searchQuery"
    }
}