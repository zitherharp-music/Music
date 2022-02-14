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

    fun View.copyToClipboard(text: String) {
        (context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).run {
            setPrimaryClip(ClipData.newPlainText(context.packageName, text))
        }
        Toast.makeText(context, "Sao chép thành công", Toast.LENGTH_SHORT).show()
    }

    fun View.showImageDialog(url: String) {
        AlertDialog.Builder(context).apply {
            setView(ImageView(context).apply {
                setImageUrl(url)
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            })
        }.show()
    }

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