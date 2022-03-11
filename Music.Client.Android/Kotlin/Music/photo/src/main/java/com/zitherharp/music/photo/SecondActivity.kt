package com.zitherharp.music.photo

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.zitherharp.music.photo.databinding.ActivityMainBinding
import com.zitherharp.music.photo.databinding.SecondActivityBinding

class SecondActivity : AppCompatActivity() {
    private val binding: SecondActivityBinding by lazy { SecondActivityBinding.inflate(layoutInflater) }
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setContentView(root)
            navController = mainFragment.getFragment<NavHostFragment>().navController
            appBarConfiguration = AppBarConfiguration(navController.graph)
        }
    }

    override fun onSupportNavigateUp() =
        navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
}