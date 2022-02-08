package com.zitherharp.music.model

import com.zitherharp.music.core.QQMusic

class Artist(id: String) : QQMusic(id) {
    companion object {
        const val ID = 0
        const val PLAYLIST_ID = 1
        const val VIETNAMESE_NAME = 2
        const val CHINESE_NAME = 3
        const val VIETNAMESE_DESCRIPTION = 4
        const val CHINESE_DESCRIPTION = 5
    }

    override fun getImageUrl(image: Image) =
        "https://y.qq.com/music/photo_new/T001R${image.pixel}x${image.pixel}M000$id.jpg"
}