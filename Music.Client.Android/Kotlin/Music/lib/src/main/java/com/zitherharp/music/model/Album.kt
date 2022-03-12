package com.zitherharp.music.model

import com.zitherharp.music.core.QQMusic

class Album(id: String): QQMusic(id) {

    override fun getImageUrl(image: Image) =
        "https://y.qq.com/music/photo_new/T002R${image.pixel}x${image.pixel}M000$id.jpg"
}