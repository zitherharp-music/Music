package com.zitherharp.music.shorts.ui.shorts

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.Language
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.core.Spreadsheet.Companion.getName
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Audio
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.databinding.ShortFullscreenContentBinding
import com.zitherharp.music.shorts.ui.artist.ArtistDetailActivity
import com.zitherharp.music.shorts.ui.artist.ArtistListDialog
import com.zitherharp.music.shorts.ui.audio.AudioListDialog
import com.zitherharp.music.ui.RecyclerViewAdapter

class ShortFullscreenAdapter(private val activity: AppCompatActivity,
                             private val shorts: List<Short>):
    RecyclerViewAdapter<ShortFullscreenAdapter.ViewHolder>(activity.baseContext, shorts) {

    inner class ViewHolder(binding: ShortFullscreenContentBinding):
        RecyclerView.ViewHolder(binding.root) {
        val shortView = binding.shortView
        val artistImage = binding.artistImage
        val artistName = binding.artistName
        val audioName = binding.audioName
        //val shortName = binding.shortName
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
                    setOnClickListener { onArtistClickListener(activity.supportFragmentManager, getArtists()) }
                }
                with(artistName) {
                    text = getArtists().getName(Language.VIETNAMESE, " & ")
                    setOnClickListener { onArtistClickListener(activity.supportFragmentManager, getArtists()) }
                }
                with(audioName) {
                    isSelected = true
                    text = if (getAudios().isNotEmpty())
                        getAudios().getName(Language.VIETNAMESE, " & ") else "Bài hát của ${artistName.text}"
                    setOnClickListener { onAudioClickListener(activity.supportFragmentManager, getAudios()) }
                }
            }
        }
    }

    private fun onAudioClickListener(manager: FragmentManager, audios: List<Audio>) {
        if (audios.size == 1) {
            activity.startActivity(
                Intent(activity, ArtistDetailActivity::class.java).apply {
                    putExtra(ArtistDetailActivity::class.simpleName, audios.first().id)
                })
        } else if (audios.size > 1) {
            AudioListDialog(audios).showNow(manager, AudioListDialog::class.simpleName)
        }
    }

    private fun onArtistClickListener(manager: FragmentManager, artists: List<Artist>) {
        if (artists.size == 1) {
            activity.startActivity(
                Intent(activity, ArtistDetailActivity::class.java).apply {
                    putExtra(ArtistDetailActivity::class.simpleName, artists.first().id)
                })
        } else if (artists.size > 1) {
            ArtistListDialog(artists).showNow(manager, ArtistListDialog::class.simpleName)
        }
    }
}