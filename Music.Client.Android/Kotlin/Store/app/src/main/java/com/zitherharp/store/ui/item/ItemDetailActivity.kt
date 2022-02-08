package com.zitherharp.store.ui.item

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zitherharp.store.Extension.getItemDetailDialog
import com.zitherharp.store.R
import com.zitherharp.store.Repository
import com.zitherharp.store.databinding.ActivityItemBinding
import com.zitherharp.store.Extension.isUpdateAvailable
import com.zitherharp.store.Extension.reportItem
import com.zitherharp.store.Extension.shareItem
import com.zitherharp.store.model.Item

class ItemDetailActivity: AppCompatActivity() {
    private val binding: ActivityItemBinding by lazy { ActivityItemBinding.inflate(layoutInflater) }
    private lateinit var item: Item

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = Repository.itemMap[intent.getStringExtra(ItemDetailActivity::class.java.name)]!!
        with(binding) {
            setContentView(root)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            title = item.name
            //toolbarLayout.title = "Không có ảnh chụp màn hình"
            // TODO: Download url
            if (item.downloadUrl.isNotEmpty()) {
                actionFab.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.downloadUrl)))
                }
            } else {
                itemDetail.itemField.visibility = View.GONE
                actionFab.setOnClickListener {
                    Toast.makeText(this@ItemDetailActivity,
                        "Ứng dụng chưa được phát hành", Toast.LENGTH_SHORT).show()
                }
            }
            // TODO: Installed
            if (item.isInstalled) {
                actionFab.setImageResource(android.R.drawable.ic_media_play)
                actionFab.setOnClickListener {
                    if (!isUpdateAvailable(item)) {
                        startActivity(Intent(packageManager.getLaunchIntentForPackage(item.id)))
                    } else {
                        getItemDetailDialog(item).apply {
                            setTitle("Phát hiện bản cập nhật mới")
                            setMessage("Phiên bản: ${item.field?.version}")
                            setNegativeButton("Để sau") { _, _ ->
                                startActivity(Intent(packageManager.getLaunchIntentForPackage(item.id)))
                            }
                            setPositiveButton("Cập nhật") { _, _ ->
                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.downloadUrl)))
                            }
                        }.show()
                    }
                }
                actionFab.setOnLongClickListener {
                    startActivity(Intent(Intent.ACTION_DELETE, Uri.parse("package:${item.id}")))
                    true
                }
            } else {
                actionFab.setImageResource(android.R.drawable.stat_sys_download)
            }
            // TODO: Image
            item.image?.let {
                if (it.snapshotId.isNotEmpty()) {
                    imageStatus.visibility = View.GONE
                    imageGallery.setInAnimation(this@ItemDetailActivity, android.R.anim.fade_in)
                    imageGallery.setOutAnimation(this@ItemDetailActivity, android.R.anim.fade_out)
                    //imageGallery.setFactory { ImageView() }
                }
            }
            // TODO: Field
            with(itemDetail) {
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
            }
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.navigation_share -> shareItem(item)
            R.id.navigation_report -> reportItem(item)
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu) =
        super.onCreateOptionsMenu(menu).apply {
            menuInflater.inflate(R.menu.item_detail_menu, menu)
        }
}