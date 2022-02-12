package com.zitherharp.music.shorts

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentManager
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Audio
import com.zitherharp.music.shorts.ui.artist.ArtistDetailActivity
import com.zitherharp.music.shorts.ui.artist.ArtistListDialog
import com.zitherharp.music.shorts.ui.audio.AudioDetailActivity
import com.zitherharp.music.shorts.ui.audio.AudioListDialog

object Extension {
    fun Context.onArtistClickListener(manager: FragmentManager, artists: List<Artist>) {
        if (artists.size == 1) {
            startActivity(
                Intent(this, ArtistDetailActivity::class.java).apply {
                    putExtra(ArtistDetailActivity::class.java.simpleName, artists.first().id)
                })
        } else if (artists.size > 1) {
            ArtistListDialog(artists).showNow(manager, ArtistListDialog::class.java.simpleName)
        }
    }

    fun Context.onAudioClickListener(manager: FragmentManager, audios: List<Audio>) {
        if (audios.size == 1) {
            startActivity(
                Intent(this, AudioDetailActivity::class.java).apply {
                    putExtra(AudioDetailActivity::class.java.simpleName, audios.first().id)
                })
        } else if (audios.size > 1) {
            AudioListDialog(audios).showNow(manager, AudioListDialog::class.java.simpleName)
        }
    }
}