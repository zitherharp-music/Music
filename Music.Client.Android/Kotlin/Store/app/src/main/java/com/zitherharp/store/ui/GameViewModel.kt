package com.zitherharp.store.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Trống"
    }
    val text: LiveData<String> = _text
}