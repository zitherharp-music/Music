package com.zitherharp.music.shorts.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zitherharp.music.shorts.databinding.NotificationFragmentBinding
import com.zitherharp.music.ui.adapter.FragmentStateAdapter
import com.zitherharp.music.ui.fragment.EmptyFragment

class NotificationFragment: Fragment() {
    private lateinit var binding: NotificationFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        NotificationFragmentBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            NotificationAdapter(this@NotificationFragment,
                arrayOf("Cập nhật"))
                .attach(notificationTabLayout, notificationViewPager)
        }
    }

    inner class NotificationAdapter(fragment: Fragment, tabNames: Array<String>): FragmentStateAdapter(fragment, tabNames) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
            }
            return EmptyFragment()
        }
    }
}