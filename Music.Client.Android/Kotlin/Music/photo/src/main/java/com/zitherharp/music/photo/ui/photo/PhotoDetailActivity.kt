package com.zitherharp.music.photo.ui.photo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.*
import com.zitherharp.music.core.Spreadsheet.Companion.getName
import com.zitherharp.music.model.Photo
import com.zitherharp.music.photo.databinding.PhotoDetailActivityBinding

class PhotoDetailActivity: AppCompatActivity() {
    private val binding: PhotoDetailActivityBinding by lazy { PhotoDetailActivityBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        with(binding) {
            setContentView(root)
            Photo.repository[intent.getStringExtra(PhotoDetailActivity::class.simpleName)]?.let {
                // TODO: Photo
                photoImage.setImageUrl(it.getImageUrl(Pinterest.Image.LARGE))
                photoImage.setOnClickListener { _ ->
                    startActivity(Intent(this@PhotoDetailActivity, PhotoFullscreenActivity::class.java).apply {
                        putExtra(PhotoFullscreenActivity::class.simpleName, it.id)
                    })
                }
                photoChineseName.text = it.getName(Language.CHINESE).ifEmpty {
                    getString(com.zitherharp.music.R.string.no_information)
                }
                photoVietnameseName.text = it.getName(Language.VIETNAMESE).ifEmpty {
                    getString(com.zitherharp.music.R.string.no_information)
                }
                photoRecyclerView.adapter = PhotoGridAdapter(this@PhotoDetailActivity, Photo.repository.values.shuffled())
                photoRecyclerView.layoutManager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)
                photoMenuButton.setOnClickListener { _ ->
                    PhotoMenuDialog().showNow(supportFragmentManager, it.id)
                }
                // TODO: Artist
                val artists = it.getArtists()
                artistImage.setImageUrl(artists.first().getImageUrl(QQMusic.Image.SMALL))
                artistChineseName.text = artists.getName(Language.CHINESE)
                artistVietnameseName.text = artists.getName(Language.VIETNAMESE)
                // TODO: Other
                backButton.setOnClickListener { onBackPressed() }
            }
        }
    }
}