package com.zitherharp.music.core

abstract class Youtube(id: String, protected val duration: Int = 0) : Spreadsheet(id) {
    enum class Image {
        DEFAULT,
        MQDEFAULT,
        HQDEFAULT,
        SDDEFAULT,
        MAXRESDEFAULT
    }

    fun getImageUrl(image: Image) =
        "https://i.ytimg.com/vi/$id/${image.name.lowercase()}.jpg"

    fun getShareUrl(isEmbed: Boolean) =
        if (!isEmbed) "https://youtu.be/$id" else "https://www.youtube.com/embed/$id"
}
