package com.zitherharp.music

import android.content.Context
import android.graphics.BitmapFactory
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.zitherharp.music.model.Music
import com.zitherharp.music.model.Music.Resource.getColor
import kotlinx.coroutines.*
import java.net.URL

@DelicateCoroutinesApi
object Extension {
    fun Context.isNetworkConnected() =
        Runtime.getRuntime().exec("ping -c 1 google.com").waitFor() == 0

    fun ImageView.setImageUrl(url: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val image = BitmapFactory.decodeStream(
                URL(url).openConnection().apply { useCaches = true }.getInputStream())
            withContext(Dispatchers.Main) {
                setImageBitmap(image)
            }
        }
    }

    fun ImageView.setColorDrawable(music: Music) {
        DrawableCompat.wrap(drawable).run {
            DrawableCompat.setTint(this, ContextCompat.getColor(context, music.getColor()))
            setImageDrawable(this)
        }
    }
}