package com.zitherharp.music.photo.ui.artist

import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.photo.databinding.ArtistGridContentBinding

class ArtistGridContent(binding: ArtistGridContentBinding): RecyclerView.ViewHolder(binding.root) {
    val artistImage = binding.artistImage
    val artistChineseName = binding.artistChineseName
    val artistVietnameseName = binding.artistVietnameseName
}