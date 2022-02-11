package com.zitherharp.music.shorts.ui.audio

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zitherharp.music.R
import com.zitherharp.music.model.Audio
import com.zitherharp.music.shorts.databinding.AudioListFragmentBinding

class AudioListDialog: BottomSheetDialogFragment {
    private lateinit var binding: AudioListFragmentBinding
    private var audios: List<Audio> = ArrayList()

    constructor(): super()

    constructor(audios: List<Audio>): super() {
        this.audios = audios
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        AudioListFragmentBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            if (audios.isNotEmpty()) {
                audioList.adapter = AudioListAdapter(view.context, audios)
            } else {
                status.text = getString(R.string.empty)
            }
        }
    }
}