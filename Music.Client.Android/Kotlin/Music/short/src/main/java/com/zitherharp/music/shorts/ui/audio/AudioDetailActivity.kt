package com.zitherharp.music.shorts.ui.audio

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.zitherharp.music.R
import com.zitherharp.music.model.Audio
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.databinding.AudioDetailActivityBinding
import com.zitherharp.music.shorts.ui.shorts.ShortGridFragment
import com.zitherharp.music.ui.FragmentStateAdapter

class AudioDetailActivity: AppCompatActivity() {
    private val binding: AudioDetailActivityBinding by lazy { AudioDetailActivityBinding.inflate(layoutInflater) }
    private lateinit var audio: Audio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        audio = Audio.repository[intent.getStringExtra(AudioDetailActivity::class.simpleName)]!!
        with(binding) {
            setContentView(root)
            with(audio) {

            }
            AudioDetailAdapter(this@AudioDetailActivity,
                arrayOf(getString(R.string.recommend), getString(R.string.use)))
                .attach(audioTabLayout, audioViewPager, 1)
        }
    }

    inner class AudioDetailAdapter(fragmentActivity: FragmentActivity,
                                    tabNames: Array<String>): FragmentStateAdapter(fragmentActivity, tabNames) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return AudioListFragment(ArrayList())
                //1 -> return ShortGridFragment(arrayOf(Short("", "", "")))
            }
            TODO("Not yet implemented")
        }
    }
}