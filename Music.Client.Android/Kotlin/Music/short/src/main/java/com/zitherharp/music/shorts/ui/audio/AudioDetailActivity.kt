package com.zitherharp.music.shorts.ui.audio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.Language
import com.zitherharp.music.R
import com.zitherharp.music.core.Spreadsheet.Companion.getName
import com.zitherharp.music.core.Youtube
import com.zitherharp.music.model.Audio
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.databinding.AudioDetailActivityBinding
import com.zitherharp.music.shorts.ui.shorts.ShortGridFragment
import com.zitherharp.music.ui.FragmentStateAdapter

class AudioDetailActivity: AppCompatActivity() {
    private val binding: AudioDetailActivityBinding by lazy { AudioDetailActivityBinding.inflate(layoutInflater) }
    private lateinit var audios: List<Audio>
    private lateinit var shorts: List<Short>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        with(binding) {
            setContentView(root)
            Audio.repository[intent.getStringExtra(AudioDetailActivity::class.simpleName)]?.let {
                audios = emptyList()
                shorts = it.getShorts()
                audioImage.setImageUrl(it.getImageUrl(Youtube.Image.MQDEFAULT))
                audioChineseName.text = it.getName(Language.CHINESE)
                audioVietnameseName.text = it.getName(Language.VIETNAMESE)
                artistVietnameseName.text = it.getArtists().getName(Language.VIETNAMESE)
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
                0 -> return AudioListFragment(ArrayList())
                1 -> return ShortGridFragment(shorts)
            }
            TODO("Index $position out of bounds for ${tabNames.size}")
        }
    }
}