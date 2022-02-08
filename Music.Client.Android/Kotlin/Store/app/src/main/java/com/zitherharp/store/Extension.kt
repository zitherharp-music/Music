package com.zitherharp.store

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.zitherharp.store.databinding.ItemDetailContentBinding
import com.zitherharp.store.model.Item
import kotlinx.coroutines.*
import java.net.URL
import java.sql.*


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

    fun Context.download(item: Item): Int {
        val request = DownloadManager.Request(Uri.parse(item.downloadUrl))
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        return downloadManager.apply {
            with(request) {
                setTitle(item.name)
                setDescription(item.description)
                setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS, "${item.name}.apk")
            }
        }.enqueue(request).toInt()
    }

    fun Context.shareItem(item: Item) {
        startActivity(Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT,
                "Cùng tôi tải về và trải nghiệm ${item.name} nhé! ${item.downloadUrl}")
        })
    }

    fun Context.reportItem(item: Item) {
        startActivity(Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:zh.zitherharp@gmail.com")
            putExtra(Intent.EXTRA_SUBJECT, "Phản hồi về ${item.name}")
        })
    }

    fun Context.isUpdateAvailable(item: Item): Boolean {
        Repository.itemMap[item.id]?.let {
            val version0 = item.field!!.version.split('.').map { it.toInt() }
            val version1 = packageManager.getPackageInfo(item.id, 0).versionName.split('.').map { it.toInt() }
            for (i in 0 until Integer.min(version0.size, version1.size)) {
                if (version0[i] > version1[i]) return true
            }
        }
        return false
    }

    fun FragmentActivity.getItemDetailDialog(item: Item): AlertDialog.Builder {
        return AlertDialog.Builder(this).apply {
            setView(ItemDetailContentBinding.inflate(layoutInflater).apply {
                feature.text = item.feature
                description.text = item.description
                item.field?.let {
                    provider.text = it.provider
                    size.text = String.format("${it.size} MB")
                    version.text = it.version
                    androidVersion.text = String.format("${it.getAndroidVersion()} trở lên")
                    updateTime.text = it.updateTime
                    releaseTime.text = it.releaseTime
                }
            }.root)
        }
    }

    fun FragmentActivity.showItemDetailDialog(item: Item) {
        val packageInfo = packageManager.getPackageInfo(item.id, 0)
        getItemDetailDialog(item).apply {
            setTitle("Về ứng dụng ${item.name}")
            setMessage("Cài đặt lần đầu: ${Date(Timestamp(packageInfo.firstInstallTime).time)}\n" +
                    "Cập nhật lần cuối: ${Date(Timestamp(packageInfo.lastUpdateTime).time)}")
            setPositiveButton("Đóng") { dlg, _ -> dlg.dismiss() }
        }.show()
    }

    fun FragmentActivity.showItemUpdateDialog(item: Item, isAutoDisplay: Boolean) {
        if (!isUpdateAvailable(item)) {
            if (!isAutoDisplay) return
            getItemDetailDialog(item).apply {
                setView(null)
                setTitle("Kiểm tra hoàn tất")
                setMessage("Không có bản cập nhật mới")
                setPositiveButton("Đóng") { dlg, _ -> dlg.dismiss() }
            }.show()
        } else {
            getItemDetailDialog(item).apply {
                setTitle("Phát hiện bản cập nhật mới")
                setMessage("Phiên bản: ${item.field?.version}")
                setNegativeButton("Để sau") { dlg, _ -> dlg.cancel() }
                setPositiveButton("Cập nhật") { _, _ ->
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.downloadUrl)))
                }
            }.show()
        }
    }
}