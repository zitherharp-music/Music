package com.zitherharp.store.model

data class Item(
    var id: String,
    var name : String,
    var feature: String,
    var description: String,
    var downloadUrl: String,
    var field: Field? = null,
    var image: Image? = null,
    var contact: Contact? = null,
    var isInstalled: Boolean = false) {

    data class Field(
        var provider: String,
        var size: Double,
        var version: String,
        var minSdkVersion: Int,
        var updateTime: String,
        var releaseTime: String) {
        fun getAndroidVersion(): String {
            when(minSdkVersion) {
                9 -> return "2.3"
                10 -> return "2.3.3"
                11 -> return "3.0"
                12 -> return "3.1"
                13 -> return "3.2"
                14 -> return "4.0"
                15 -> return "4.0.3"
                16 -> return "4.1"
                17 -> return "4.2"
                18 -> return "4.3"
                19 -> return "4.4"
                20 -> return "4.4W"
                21 -> return "5.0"
                22 -> return "5.1"
                23 -> return "6.0"
                24 -> return "7.0"
                25 -> return "7.1"
                26 -> return "8.0"
                27 -> return "8.1"
                28 -> return "9.0"
                29 -> return "10.0"
                30 -> return "11.0"
                31 -> return "12.0"
            }
            return minSdkVersion.toString()
        }
    }

    data class Image(
        var iconId: String,
        var videoId: String,
        var snapshotId: String) {
        fun getIconUrl(size: Int? = null): String {
            val iconUrl = "https://yt3.ggpht.com/$iconId"
            return if (size == null) iconUrl else "$iconUrl=s$size-c-k-c0x00ffffff-no-rj"
        }
    }

    data class Contact(
        var email: String,
        var website: String,
        var youtubeId: String,
        var facebookId: String) {
        fun getYoutubeUrl() = "https://www.youtube.com/channel/$youtubeId"
        fun getFacebookUrl() = "https://www.facebook.com/$facebookId"
    }
}

