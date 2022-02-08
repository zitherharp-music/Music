package com.zitherharp.music.model

import com.zitherharp.music.core.Youtube

class Short(id: String, duration: Int, private val artistId: String, private val audioId: String): Youtube(id, duration) {
}