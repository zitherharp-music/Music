package com.zitherharp.music.shorts.ui.audio

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.Extension.showImageDialog
import com.zitherharp.music.core.Language
import com.zitherharp.music.R
import com.zitherharp.music.core.Spreadsheet.Companion.getName
import com.zitherharp.music.core.Youtube
import com.zitherharp.music.model.Audio
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.Extension.onArtistClickListener
import com.zitherharp.music.shorts.databinding.AudioDetailActivityBinding
import com.zitherharp.music.shorts.ui.artist.ArtistListFragment
import com.zitherharp.music.shorts.ui.shorts.ShortGridFragment
import com.zitherharp.music.ui.adapter.FragmentStateAdapter
import com.zitherharp.music.ui.fragment.EmptyFragment

class AudioDetailActivity: AppCompatActivity() {
    private val binding: AudioDetailActivityBinding by lazy { AudioDetailActivityBinding.inflate(layoutInflater) }
    private lateinit var audio: Audio
    private lateinit var audios: List<Audio>
    private lateinit var shorts: List<Short>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        audio = Audio.repository[intent.getStringExtra(AudioDetailActivity::class.java.simpleName)]!!
        with(binding) {
            setContentView(root)
            audios = emptyList()
            shorts = audio.getShorts()
            with(audioImage) {
                setImageUrl(audio.getImageUrl(Youtube.Image.MQDEFAULT))
                setOnClickListener {
                    it.showImageDialog(audio.getImageUrl(Youtube.Image.SDDEFAULT))
                }
            }
            audioChineseName.text = audio.getName(Language.CHINESE)
            audioVietnameseName.text = audio.getName(Language.VIETNAMESE)
            with(artistName) {
                val artists = audio.getArtists()
                text = artists.getName(Language.VIETNAMESE)
                setOnClickListener { context.onArtistClickListener(supportFragmentManager, artists) }
            }
            with(shareAudio) {
                setOnClickListener { _ ->
                    context.startActivity(Intent.createChooser(
                        Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, audio.getShareUrl())
                        }, context.getString(R.string.share)))
                }
            }
            AudioDetailAdapter(this@AudioDetailActivity,
                arrayOf("${getString(R.string.recommend)} ${audios.size}", "${getString(R.string.use)} ${shorts.size}"))
                .attach(audioTabLayout, audioViewPager, 1)
        }
    }

    inner class AudioDetailAdapter(fragmentActivity: FragmentActivity,
                                    tabNames: Array<String>): FragmentStateAdapter(fragmentActivity, tabNames) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> if (audios.isNotEmpty()) return AudioListFragment(audios) else EmptyFragment()
                1 -> if (shorts.isNotEmpty()) return ShortGridFragment(shorts) else EmptyFragment()
            }
            return EmptyFragment()
        }
    }
}