package com.zitherharp.music.shorts.ui.shorts

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.Language
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.model.Short
import com.zitherharp.music.model.Short.Companion.getArtists
import com.zitherharp.music.shorts.databinding.ShortFullscreenContentBinding
import com.zitherharp.music.shorts.ui.artist.ArtistDetailActivity

class ShortFullscreenAdapter(private val activity: AppCompatActivity,
                             private val shorts: List<Short>):
    RecyclerView.Adapter<ShortFullscreenAdapter.ViewHolder>() {

    inner class ViewHolder(binding: ShortFullscreenContentBinding):
        RecyclerView.ViewHolder(binding.root) {
        val shortView = binding.shortView
        val artistImage = binding.artistImage
        val artistName = binding.artistName
        val audioName = binding.audioName
        val shortName = binding.shortName
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ShortFullscreenContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val short = shorts[position]
            with(shortView) {
                activity.lifecycle.addObserver(this)
                addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(short.id, 0F)
                    }
                    override fun onStateChange(youTubePlayer: YouTubePlayer,
                                               state: PlayerConstants.PlayerState) {
                        if (state == PlayerConstants.PlayerState.ENDED) {
                            youTubePlayer.play()
                        }
                    }
                })
            }
            with(short.getArtists()[0]) {
                artistImage.setImageUrl(getImageUrl(QQMusic.Image.SMALL))
                artistImage.setOnClickListener {
                    activity.startActivity(Intent(activity, ArtistDetailActivity::class.java).apply {
                        putExtra(ArtistDetailActivity::class.simpleName, id)
                    })
                }
                artistName.text = getName(Language.VIETNAMESE)
            }
            audioName.text = if (short.getArtists().isNotEmpty())
                short.getArtists()[0].getName(Language.VIETNAMESE)
            else "Bài hát của ${artistName.text}"
        }
    }

    override fun getItemCount() = shorts.size
}