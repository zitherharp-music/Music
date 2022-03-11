package com.zitherharp.music.photo.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zitherharp.music.model.Photo
import com.zitherharp.music.photo.databinding.FragmentHomeBinding
import com.zitherharp.music.photo.ui.photo.PhotoGridAdapter

class MainHomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) =
        FragmentHomeBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            val context = activity as AppCompatActivity
            photoRefreshView.setOnRefreshListener {
                photoRefreshView.isRefreshing = false
                photoRecyclerView.adapter = PhotoGridAdapter(context, Photo.repository.values.shuffled())
            }
            photoRecyclerView.adapter = PhotoGridAdapter(context, Photo.repository.values.toList())
            photoRecyclerView.layoutManager = StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL)
        }
    }
}