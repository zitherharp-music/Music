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
import com.zitherharp.music.core.Language
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.core.Spreadsheet.Companion.getName
import com.zitherharp.music.model.Audio.Companion.toString
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.databinding.ShortFullscreenContentBinding
import com.zitherharp.music.ui.adapter.RecyclerViewAdapter
import com.zitherharp.music.shorts.Extension.onArtistClickListener
import com.zitherharp.music.shorts.Extension.onAudioClickListener
import com.zitherharp.music.R
import com.zitherharp.music.shorts.ui.user.User

class ShortFullscreenAdapter(private val activity: AppCompatActivity,
                             private val shorts: List<Short>):
    RecyclerViewAdapter<ShortFullscreenAdapter.ViewHolder>(activity.baseContext, shorts) {

    inner class ViewHolder(binding: ShortFullscreenContentBinding):
        RecyclerView.ViewHolder(binding.root) {
        val shortView = binding.shortView
        val artistImage = binding.artistImage
        val artistName = binding.artistName
        val audioName = binding.audioName
        val shortName = binding.shortName
        val favouriteButton = binding.favouriteButton
        val commentButton = binding.commentButton
        val shareButton = binding.shareButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ShortFullscreenContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            val short = shorts[position]
            with(short) {
                with(shortView) {
                    activity.lifecycle.addObserver(this)
                    addYouTubePlayerListener(object: AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            setOnFocusChangeListener { _, hasFocus ->
                                if (hasFocus) youTubePlayer.play() else youTubePlayer.pause()
                            }
                            youTubePlayer.loadVideo(short.id, 0F)
                            youTubePlayer.pause()
                        }
                        override fun onStateChange(youTubePlayer: YouTubePlayer,
                                                   state: PlayerConstants.PlayerState) {
                            if (state == PlayerConstants.PlayerState.ENDED) {
                                youTubePlayer.play()
                            }
                        }
                    })
                }
                with(artistImage) {
                    setImageUrl(getArtists().first().getImageUrl(QQMusic.Image.SMALL))
                    setOnClickListener { context.onArtistClickListener(activity.supportFragmentManager, getArtists()) }
                }
                with(artistName) {
                    text = getArtists().getName(Language.VIETNAMESE)
                    setOnClickListener { context.onArtistClickListener(activity.supportFragmentManager, getArtists()) }
                }
                with(audioName) {
                    isSelected = true
                    text = if (getAudios().isNotEmpty())
                        getAudios().toString(Language.VIETNAMESE) else "Bài hát của ${artistName.text}"
                    setOnClickListener { context.onAudioClickListener(activity.supportFragmentManager, getAudios()) }
                }
                with(favouriteButton) {
                    User(context).run {
                        shortId?.let {
                            if (!it.contains(short.id)) {
                                setImageResource(com.zitherharp.music.shorts.R.drawable.ic_short_favorite_border_24)
                            } else {
                                setImageResource(com.zitherharp.music.shorts.R.drawable.ic_short_favorite_24)

                            }
                        }
                    }
                }
                with(shareButton) {
                    setOnClickListener { _ ->
                        context.startActivity(Intent.createChooser(
                            Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, getShareUrl())
                            }, context.getString(R.string.share)))
                    }
                }
            }
        }
    }
}