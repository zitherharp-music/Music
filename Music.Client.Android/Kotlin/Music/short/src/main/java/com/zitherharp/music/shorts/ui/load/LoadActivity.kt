package com.zitherharp.music.shorts.ui.load

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zitherharp.music.Extension.isNetworkConnected
import com.zitherharp.music.R
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Audio
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.MainActivity
import com.zitherharp.music.shorts.databinding.ActivityLoadBinding
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
                Audio.repository
                Artist.repository
                Short.repository
                withContext(Dispatchers.IO) {
                    startActivity(Intent(this@LoadActivity, MainActivity::class.java))
                    finish()
                }
            }
        } else {
            Toast.makeText(this, getString(R.string.no_connection), Toast.LENGTH_SHORT).show()
        }
    }
}