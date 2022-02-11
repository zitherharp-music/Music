package com.zitherharp.music.shorts.ui.artist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zitherharp.music.model.Artist
import com.zitherharp.music.R
import com.zitherharp.music.shorts.databinding.ArtistListFragmentBinding

class ArtistListDialog: BottomSheetDialogFragment {
    private lateinit var binding: ArtistListFragmentBinding
    private var artists: List<Artist> = ArrayList()

    constructor(): super()

    constructor(artists: List<Artist>): super() {
        this.artists = artists
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        ArtistListFragmentBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            if (artists.isNotEmpty()) {
                artistList.adapter = ArtistListAdapter(view.context, artists)
            } else {
                status.text = getString(R.string.empty)
            }
        }
    }
}