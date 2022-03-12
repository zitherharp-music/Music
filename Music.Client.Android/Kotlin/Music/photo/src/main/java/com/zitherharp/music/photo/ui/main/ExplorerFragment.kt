package com.zitherharp.music.photo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.zitherharp.music.model.Artist
import com.zitherharp.music.photo.R
import com.zitherharp.music.photo.databinding.ExplorerFragmentBinding
import com.zitherharp.music.photo.ui.artist.ArtistGridAdapter

class ExplorerFragment: Fragment() {
    private lateinit var binding: ExplorerFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        ExplorerFragmentBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            val context = activity as AppCompatActivity
            // TODO: Check the status of filter view
            fun checkFilter() {
                when (itemFilterView.checkedChipId) {
                    R.id.album_filter -> {
                        itemRecyclerView.adapter = null
                        itemExplorerStatus.visibility = View.VISIBLE
                    }
                    R.id.artist_filter, R.id.all_filter -> {
                        itemExplorerStatus.visibility = View.GONE
                        itemRecyclerView.adapter = ArtistGridAdapter(
                            context, Artist.repository.values.shuffled().subList(0, 10))
                    }
                }
            }
            itemFilterView.setOnCheckedChangeListener { _, _ -> checkFilter() }
            itemRefreshView.setOnRefreshListener {
                checkFilter()
                itemRefreshView.isRefreshing = false
            }
            itemRecyclerView.layoutManager = GridLayoutManager(context, 2)
            itemRecyclerView.adapter = ArtistGridAdapter(context, Artist.repository.values.shuffled().subList(0, 10))
        }
    }
}