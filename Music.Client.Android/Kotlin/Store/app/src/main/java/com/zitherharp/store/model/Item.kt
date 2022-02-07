package com.zitherharp.store.model

import com.zitherharp.store.Repository

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
        var version: String,
        var size: Double,
        var minSdk: Double,
        var maxSdk: Double) {
        fun getSize() = "$size MB"
        fun getSdk() = "$minSdk -> $maxSdk"
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

