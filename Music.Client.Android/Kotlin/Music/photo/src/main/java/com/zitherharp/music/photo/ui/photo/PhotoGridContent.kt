package com.zitherharp.music.photo.ui.photo

import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.photo.databinding.PhotoGridContentBinding

class PhotoGridContent(binding: PhotoGridContentBinding): RecyclerView.ViewHolder(binding.root) {
    val artistImage = binding.artistImage
    val photoImage = binding.photoImage
    val photoVisibilityButton = binding.photoVisibilityButton
}