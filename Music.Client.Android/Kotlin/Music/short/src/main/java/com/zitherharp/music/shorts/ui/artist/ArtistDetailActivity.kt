package com.zitherharp.music.shorts.ui.artist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.Language
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Audio
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.databinding.ArtistDetailActivityBinding
import com.zitherharp.music.shorts.ui.audio.AudioListFragment
import com.zitherharp.music.shorts.ui.shorts.ShortGridFragment
import com.zitherharp.music.ui.FragmentStateAdapter

class ArtistDetailActivity: AppCompatActivity() {
    private val binding: ArtistDetailActivityBinding by lazy { ArtistDetailActivityBinding.inflate(layoutInflater) }
    private lateinit var artist: Artist
    private lateinit var audios: List<Audio>
    private lateinit var shorts: List<Short>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        with(binding) {
            setContentView(root)
            artist = Artist.repository[intent.getStringExtra(ArtistDetailActivity::class.simpleName)]!!.apply {
                audios = getAudios()
                shorts = getShorts()
                artistImage.setImageUrl(getImageUrl(QQMusic.Image.MEDIUM))
                artistChineseName.text = getName(Language.CHINESE)
                artistVietnameseName.text = getName(Language.VIETNAMESE)
            }
            ArtistDetailAdapter(this@ArtistDetailActivity,
                arrayOf("Audio ${audios.size}", "Short ${shorts.size}"))
                .attach(artistTabLayout, artistViewPager, 1)
        }
    }

    inner class ArtistDetailAdapter(fragmentActivity: FragmentActivity,
                                    tabNames: Array<String>): FragmentStateAdapter(fragmentActivity, tabNames) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return AudioListFragment(audios)
                1 -> return ShortGridFragment(shorts)
            }
            TODO("Index $position out of bounds for ${tabNames.size}")
        }
    }
}