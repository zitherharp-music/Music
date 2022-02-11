package com.zitherharp.music.core

abstract class Youtube(id: String): Spreadsheet(id) {
    var duration = 0
    var viewCount = 0
    var commentCount = 0

    companion object {
        const val DURATION = 4
    }

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
