package com.zitherharp.store.ui.load

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.zitherharp.store.Extension.isNetworkConnected
import com.zitherharp.store.MainActivity
import com.zitherharp.store.R
import com.zitherharp.store.Repository
import com.zitherharp.store.databinding.ActivityLoadBinding
import kotlinx.coroutines.*

@DelicateCoroutinesApi
class LoadActivity: AppCompatActivity() {
    private val binding: ActivityLoadBinding by lazy { ActivityLoadBinding.inflate(layoutInflater) }
    private var isFirstLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            setContentView(root)
            supportActionBar?.hide()
            root.setOnClickListener { onLoad() }
        }
        onLoad()
    }

    private fun onLoad() {
        if (!isFirstLoad) return
        if (isNetworkConnected()) {
            isFirstLoad = false
            GlobalScope.launch {
                for (item in Repository.itemMap.values) {
                    item.isInstalled = packageManager.getLaunchIntentForPackage(item.id) != null
                }
                withContext(Dispatchers.IO) {
                    startActivity(Intent(this@LoadActivity, MainActivity::class.java))
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.no_connection), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}