package com.zitherharp.music.model

import com.zitherharp.music.core.Spreadsheet

class Photo(id: String): Spreadsheet(id) {
    lateinit var weiboId: String

}