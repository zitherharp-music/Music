package com.zitherharp.music.core

import android.content.Context
import android.content.SharedPreferences
import com.zitherharp.music.core.Spreadsheet.Companion.EMPTY_CHAR
import com.zitherharp.music.core.Spreadsheet.Companion.SPLIT_CHAR

abstract class Preference(protected val context: Context) {
    protected val preferences: SharedPreferences =
        context.getSharedPreferences(Preference::class.java.name, Context.MODE_PRIVATE)

    // TODO: Get
    protected fun getString(key: String) =
        preferences.getString(key, null)

    protected fun getStringArrayList(key: String) =
        ArrayList<String>().apply {
            getString(key)?.split(key)?.let { addAll(it) }
        }

    /**
    Check a [value] by the [key]
     */
    protected fun contains(key: String, value: String) =
        preferences.getString(key, null)?.contains(value) ?: false

    /**
    Add a value by the key
     */
    protected fun add(key: String, value: String) {
        preferences.edit().apply {
            val item = preferences.getString(key, null)
            if (!item.isNullOrEmpty()) {
                putString(key, item + value + SPLIT_CHAR)
            } else {
                putString(key, value + SPLIT_CHAR)
            }
        }.apply()
    }

    /**
    Remove a value by the key
     */
    protected fun remove(key: String, value: String) {
        preferences.edit().apply {
            val item = preferences.getString(key, null)
            if (!item.isNullOrEmpty()) {
                putString(key, item.replace(value + SPLIT_CHAR, EMPTY_CHAR))
            }
        }.apply()
    }

    /**
    Clear all values by the [key]
     */
    protected fun clear(key: String) {
        preferences.edit().apply {
            remove(key)
        }.apply()
    }

    /**
    Replace old value by new [value]
     */
    protected fun replace(key: String, value: String) {
        preferences.edit().apply {
            putString(key, value)
        }.apply()
    }
}
