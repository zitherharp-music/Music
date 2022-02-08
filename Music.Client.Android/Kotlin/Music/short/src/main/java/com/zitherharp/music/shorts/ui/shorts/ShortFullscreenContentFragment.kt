package com.zitherharp.music.shorts.ui.shorts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zitherharp.music.shorts.databinding.ShortFullscreenContentBinding

class ShortFullscreenContentFragment: Fragment() {
    private lateinit var binding: ShortFullscreenContentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        return ShortFullscreenContentBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}