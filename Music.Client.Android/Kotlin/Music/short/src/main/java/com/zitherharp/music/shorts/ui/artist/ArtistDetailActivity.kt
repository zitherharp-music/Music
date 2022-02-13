package com.zitherharp.music.shorts.ui.artist

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.core.Language
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Audio
import com.zitherharp.music.model.Short
import com.zitherharp.music.R
import com.zitherharp.music.shorts.databinding.ArtistDetailActivityBinding
import com.zitherharp.music.shorts.ui.audio.AudioListFragment
import com.zitherharp.music.shorts.ui.shorts.ShortGridFragment
import com.zitherharp.music.ui.adapter.FragmentStateAdapter

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
            Artist.repository[intent.getStringExtra(ArtistDetailActivity::class.java.simpleName)]?.let {
                audios = it.getAudios()
                shorts = it.getShorts()
                artistImage.setImageUrl(it.getImageUrl(QQMusic.Image.MEDIUM))
                artistChineseName.text = it.getName(Language.CHINESE)
                artistVietnameseName.text = it.getName(Language.VIETNAMESE)
                with(artistDescription) {
                    setOnClickListener { _ ->
                        AlertDialog.Builder(context).apply {
                            setTitle(it.getName(Language.VIETNAMESE))
                            setMessage(it.getDescription(Language.VIETNAMESE))
                            setPositiveButton(getString(R.string.close)) { dlg, _ -> dlg.dismiss() }
                        }.show()
                    }
                }
            }
            ArtistDetailAdapter(this@ArtistDetailActivity,
                arrayOf("${getString(R.string.audio)} ${audios.size}",
                    "${getString(R.string.shorts)} ${shorts.size}"))
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