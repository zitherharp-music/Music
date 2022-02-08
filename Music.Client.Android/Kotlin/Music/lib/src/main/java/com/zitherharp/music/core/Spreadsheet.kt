package com.zitherharp.music.core

import com.zitherharp.music.Language

abstract class Spreadsheet(open val id: String) {
    private lateinit var vietnameseName: String
    private lateinit var chineseName: String

    private lateinit var vietnameseDescription: String
    private lateinit var chineseDescription: String

    fun setName(language: Language, name: String) {
        when (language) {
            Language.CHINESE -> chineseName = name
            Language.VIETNAMESE -> vietnameseName = name
        }
    }

    fun setDescription(language: Language, description: String) {
        when (language) {
            Language.CHINESE -> chineseDescription = description
            Language.VIETNAMESE -> vietnameseDescription = description
        }
    }

    fun getName(language: Language): String {
        return when (language) {
            Language.CHINESE -> chineseName
            Language.VIETNAMESE -> vietnameseName
        }
    }

    fun getDescription(language: Language): String {
        return when (language) {
            Language.CHINESE -> chineseDescription
            Language.VIETNAMESE -> vietnameseDescription
        }
    }
}