package com.zitherharp.store.ui.item

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.zitherharp.store.Extension.getItemDetailView
import com.zitherharp.store.R
import com.zitherharp.store.Repository
import com.zitherharp.store.databinding.ActivityItemBinding
import com.zitherharp.store.Extension.isUpdateAvailable
import com.zitherharp.store.Extension.shareItem
import com.zitherharp.store.Extension.showItemUpdateDialog
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
            //toolbarLayout.title = "Chưa cài đặt"
            if (item.isInstalled) {
                actionFab.setImageResource(android.R.drawable.ic_media_play)
                actionFab.setOnClickListener {
                    if (!isUpdateAvailable(item)) {
                        startActivity(Intent(packageManager.getLaunchIntentForPackage(item.id)))
                    } else {
                        showItemUpdateDialog(item, false)
                    }
                }
                actionFab.setOnLongClickListener {
                    startActivity(Intent(Intent.ACTION_DELETE, Uri.parse("package:${item.id}")))
                    true
                }
            } else {
                actionFab.setImageResource(android.R.drawable.stat_sys_download)
                actionFab.setOnClickListener {
                    if (item.downloadUrl.isNotEmpty()) {
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.downloadUrl)))
                    } else {
                        Toast.makeText(this@ItemDetailActivity,
                            "Ứng dụng hiện tại không khả dụng", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            imageGallery.setInAnimation(this@ItemDetailActivity, android.R.anim.fade_in)
            imageGallery.setOutAnimation(this@ItemDetailActivity, android.R.anim.fade_out)
            //imageGallery.setFactory { ImageView() }
            with(itemDetail) {
                feature.text = item.feature
                description.text = item.description
                item.field?.let {
                    provider.text = it.provider
                    version.text = it.version
                    size.text = it.getSize()
                    sdk.text = it.getSdk()
                }
            }
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.navigation_share -> shareItem(item)
            R.id.navigation_report -> startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(item.downloadUrl)))
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu) =
        super.onCreateOptionsMenu(menu).apply {
            menuInflater.inflate(R.menu.item_detail_menu, menu)
        }
}