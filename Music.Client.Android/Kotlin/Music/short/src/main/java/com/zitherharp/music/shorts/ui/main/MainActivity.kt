package com.zitherharp.music.shorts.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.zitherharp.music.shorts.R
import com.zitherharp.music.shorts.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        with(binding) {
            setContentView(root)
            val navController = mainFragment.getFragment<NavHostFragment>().navController
            val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_explore,
                R.id.navigation_notification, R.id.navigation_user))
            navigationView.setupWithNavController(navController)
            setupActionBarWithNavController(navController, appBarConfiguration)
        }
    }
}