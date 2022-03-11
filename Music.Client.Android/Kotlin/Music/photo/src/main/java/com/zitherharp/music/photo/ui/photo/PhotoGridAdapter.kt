package com.zitherharp.music.photo.ui.photo

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.Pinterest
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.core.Spreadsheet.Companion.getId
import com.zitherharp.music.model.Photo
import com.zitherharp.music.photo.databinding.PhotoGridContentBinding
import com.zitherharp.music.ui.adapter.RecyclerViewAdapter

class PhotoGridAdapter(private val activity: AppCompatActivity,
                       private val photos: List<Photo>):
    RecyclerViewAdapter<PhotoGridContent>(activity, photos) {

    private val photoIds = photos.getId()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PhotoGridContent(
            PhotoGridContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: PhotoGridContent, position: Int) {
        with(holder) {
            val photo = photos[position]
            itemView.run {
                setOnClickListener {
                    context.startActivity(Intent(context, PhotoDetailActivity::class.java).apply {
                        putExtra(PhotoDetailActivity::class.simpleName, photoIds)
                        putExtra(PhotoDetailActivity::class.qualifiedName, position)
                    })
                }
                setOnLongClickListener {
                    PhotoMenuDialog().showNow(activity.supportFragmentManager, photo.id)
                    true
                }
            }
            photoImage.setImageUrl(photo.getImageUrl(Pinterest.Image.SMALL))
            artistImage.setImageUrl(photo.getArtists().first().getImageUrl(QQMusic.Image.SMALL))
            actionButton.setOnClickListener {
                if (photoImage.visibility == View.VISIBLE) {
                    photoImage.visibility = View.GONE
                    Snackbar.make(it, "Đã lưu!", Snackbar.LENGTH_SHORT).show()
                } else {
                    photoImage.visibility = View.VISIBLE
                    actionButton.setImageResource(com.zitherharp.music.R.drawable.ic_favourite_line_24)
                }
            }
        }
    }
}
