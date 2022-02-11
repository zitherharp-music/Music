package com.zitherharp.music

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.*
import java.net.URL

@DelicateCoroutinesApi
object Extension {
    fun Context.isNetworkConnected() =
        Runtime.getRuntime().exec("ping -c 1 google.com").waitFor() == 0

    fun ImageView.setImageUrl(url: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val image = BitmapFactory.decodeStream(
                URL(url).openConnection().getInputStream())
            withContext(Dispatchers.Main) {
                setImageBitmap(image)
            }
        }
    }
}