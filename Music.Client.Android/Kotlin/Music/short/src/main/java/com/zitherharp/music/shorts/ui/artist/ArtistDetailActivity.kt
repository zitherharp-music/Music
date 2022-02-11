package com.zitherharp.music.shorts.ui.artist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.Language
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Artist.Companion.getAudios
import com.zitherharp.music.model.Artist.Companion.getShorts
import com.zitherharp.music.shorts.databinding.ArtistDetailActivityBinding
import com.zitherharp.music.shorts.ui.audio.AudioListFragment
import com.zitherharp.music.shorts.ui.shorts.ShortGridFragment
import com.zitherharp.music.ui.FragmentStateAdapter

class ArtistDetailActivity: AppCompatActivity() {
    private val binding: ArtistDetailActivityBinding by lazy { ArtistDetailActivityBinding.inflate(layoutInflater) }
    private lateinit var artist: Artist

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        artist = Artist.repository[intent.getStringExtra(ArtistDetailActivity::class.simpleName)]!!
        with(binding) {
            setContentView(root)
            artistImage.setImageUrl(artist.getImageUrl(QQMusic.Image.MEDIUM))
            artistVietnameseName.text = artist.getName(Language.VIETNAMESE)
            artistChineseName.text = artist.getName(Language.CHINESE)
            //artistDescription.text = artist.getDescription(Language.VIETNAMESE)
            ArtistDetailAdapter(this@ArtistDetailActivity,
                arrayOf("Audio ${artist.getAudios().size}", "Short ${artist.getShorts().size}"))
                .attach(artistTabLayout, artistViewPager, 1)
        }
    }

    inner class ArtistDetailAdapter(fragmentActivity: FragmentActivity,
                                    tabNames: Array<String>): FragmentStateAdapter(fragmentActivity, tabNames) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return AudioListFragment(artist.getAudios())
                1 -> return ShortGridFragment(artist.getShorts().toTypedArray())
            }
            TODO("Not yet implemented")
        }
    }
}