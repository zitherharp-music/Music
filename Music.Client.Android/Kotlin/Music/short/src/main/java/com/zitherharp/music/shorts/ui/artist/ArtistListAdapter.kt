package com.zitherharp.music.shorts.ui.artist

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.Language
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.model.Artist
import com.zitherharp.music.shorts.databinding.ArtistListContentBinding
import com.zitherharp.music.ui.RecyclerViewAdapter

class ArtistListAdapter(private val context: Context,
                        private val artists: List<Artist>):
    RecyclerViewAdapter<ArtistListAdapter.ViewHolder>(context, artists) {

    inner class ViewHolder(binding: ArtistListContentBinding):
        RecyclerView.ViewHolder(binding.root) {
        val artistImage = binding.artistImage
        val artistChineseName = binding.artistChineseName
        val artistVietnameseName = binding.artistVietnameseName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ArtistListContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(artists[position]) {
                itemView.setOnClickListener {
                    context.startActivity(Intent(context, ArtistDetailActivity::class.java).apply {
                        putExtra(ArtistDetailActivity::class.simpleName, id)
                    })
                }
                artistImage.setImageUrl(getImageUrl(QQMusic.Image.SMALL))
                artistChineseName.text = getName(Language.CHINESE)
                artistVietnameseName.text = getName(Language.VIETNAMESE)
            }
        }
    }
}