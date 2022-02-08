package com.zitherharp.music.model

import com.zitherharp.music.core.Youtube

class Audio(id: String, duration: Int, private val artistId: String): Youtube(id, duration) {
    companion object {
        const val ID = 0
        const val ARTIST_ID = 1
        const val VIETNAMESE_NAME = 2
        const val CHINESE_NAME = 3
        const val DURATION = 4
    }
}