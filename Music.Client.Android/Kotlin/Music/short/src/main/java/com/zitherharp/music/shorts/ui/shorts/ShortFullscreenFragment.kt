package com.zitherharp.music.shorts.ui.shorts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.PagerSnapHelper
import com.zitherharp.music.R
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.databinding.ShortFullscreenFragmentBinding

class ShortFullscreenFragment(private val shorts: List<Short>): Fragment() {
    private lateinit var binding: ShortFullscreenFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        return ShortFullscreenFragmentBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            shortList.adapter = ShortFullscreenAdapter(activity as AppCompatActivity, shorts)
            if (shorts.isNotEmpty()) {
                PagerSnapHelper().attachToRecyclerView(shortList)
            } else {
                status.text = getString(R.string.empty)
            }
        }
    }
}