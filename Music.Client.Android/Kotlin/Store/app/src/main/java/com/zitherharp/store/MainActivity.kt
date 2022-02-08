package com.zitherharp.store

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.zitherharp.store.Extension.reportItem
import com.zitherharp.store.Extension.shareItem
import com.zitherharp.store.Extension.showItemDetailDialog
import com.zitherharp.store.Extension.showItemUpdateDialog
import com.zitherharp.store.databinding.ActivityMainBinding
import com.zitherharp.store.model.Item

class MainActivity: AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var item: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setContentView(root)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            val navController = findNavController(R.id.main_activity_fragment)
            val appBarConfiguration = AppBarConfiguration(
                setOf(R.id.navigation_application, R.id.navigation_game)
            )
            navView.setupWithNavController(navController)
            setupActionBarWithNavController(navController, appBarConfiguration)
        }
        item = Repository.itemMap[packageName]!!
        showItemUpdateDialog(item, false)
    }

    override fun onCreateOptionsMenu(menu: Menu) =
        super.onCreateOptionsMenu(menu).apply {
            menuInflater.inflate(R.menu.main_option_menu, menu)
        }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.navigation_setting -> startActivity(Intent(this, SettingActivity::class.java))
            R.id.navigation_share -> shareItem(item)
            R.id.navigation_detail -> showItemDetailDialog(item)
            R.id.navigation_update -> showItemUpdateDialog(item, true)
            R.id.navigation_report -> reportItem(item)
        }
        return true
    }
}