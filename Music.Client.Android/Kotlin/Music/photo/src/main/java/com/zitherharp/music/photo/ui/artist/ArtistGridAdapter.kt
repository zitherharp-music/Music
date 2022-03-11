package com.zitherharp.music.photo.ui.artist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.Language
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.model.Artist
import com.zitherharp.music.photo.databinding.ArtistGridContentBinding
import com.zitherharp.music.ui.adapter.RecyclerViewAdapter

class ArtistGridAdapter(private val context: Context,
                        private val artists: List<Artist>) :
    RecyclerViewAdapter<ArtistGridContent>(context, artists) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ArtistGridContent(
            ArtistGridContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ArtistGridContent, position: Int) {
        with(holder) {
            val artist = artists[position]
            artistImage.setImageUrl(artist.getImageUrl(QQMusic.Image.SMALL))
            artistChineseName.text = artist.getName(Language.CHINESE)
            artistVietnameseName.text = artist.getName(Language.VIETNAMESE)
        }
    }
}