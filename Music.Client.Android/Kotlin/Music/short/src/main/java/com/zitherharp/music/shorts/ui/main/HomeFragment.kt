package com.zitherharp.music.shorts.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zitherharp.music.model.Short
import com.zitherharp.music.model.Short.Companion.getShorts
import com.zitherharp.music.R
import com.zitherharp.music.shorts.databinding.FragmentHomeBinding
import com.zitherharp.music.shorts.ui.shorts.ShortFullscreenFragment
import com.zitherharp.music.shorts.model.User
import com.zitherharp.music.ui.adapter.FragmentStateAdapter
import com.zitherharp.music.ui.fragment.EmptyFragment

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?) =
        FragmentHomeBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            val followShorts = User(view.context).artistId.getShorts()
            val followFragment = if (followShorts.isNotEmpty())
                ShortFullscreenFragment(followShorts) else EmptyFragment()
            val recommendShorts = Short.repository.values.shuffled().subList(0, 50)
            val recommendFragment = ShortFullscreenFragment(recommendShorts)
            FragmentStateAdapter(this@HomeFragment,
                HashMap<String, Fragment>().apply {
                    put("Theo dõi", followFragment)
                    put("Đề xuất", recommendFragment)
                })
                .attach(tabLayout, viewPager, 1)
        }
    }
}