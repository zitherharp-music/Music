package com.zitherharp.music.shorts.ui.shorts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.PagerSnapHelper
import com.zitherharp.music.R
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.databinding.ShortFullscreenActivityBinding

class ShortFullscreenActivity: AppCompatActivity() {
    private val binding: ShortFullscreenActivityBinding by lazy { ShortFullscreenActivityBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        val shortPosition = intent.getIntExtra(ShortFullscreenActivity::class.simpleName, 0)
        val shorts = intent.getSerializableExtra(ShortFullscreenActivity::class.qualifiedName) as Array<Short>
        with(binding.shortFullscreen) {
            shortList.adapter = ShortFullscreenAdapter(this@ShortFullscreenActivity, shorts)
            if (shorts.isNotEmpty()) {
                shortList.scrollToPosition(shortPosition)
                PagerSnapHelper().attachToRecyclerView(shortList)
            } else {
                status.text = getString(R.string.empty)
            }
        }
    }
}