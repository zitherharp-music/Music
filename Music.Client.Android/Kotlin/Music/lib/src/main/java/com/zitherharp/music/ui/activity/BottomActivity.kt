package com.zitherharp.music.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationView
import com.zitherharp.music.R

class BottomActivity: AppCompatActivity() {
    protected lateinit var navigationController: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom)
        supportActionBar?.hide()
    }
}