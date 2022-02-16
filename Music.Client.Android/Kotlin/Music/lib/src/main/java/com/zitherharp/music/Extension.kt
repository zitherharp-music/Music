package com.zitherharp.music

import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
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