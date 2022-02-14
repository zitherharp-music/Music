package com.zitherharp.music.shorts.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.zitherharp.music.model.Short
import com.zitherharp.music.model.Short.Companion.getShorts
import com.zitherharp.music.R
import com.zitherharp.music.shorts.databinding.FragmentHomeBinding
import com.zitherharp.music.shorts.ui.shorts.ShortFullscreenFragment
import com.zitherharp.music.shorts.ui.user.User
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
            HomeAdapter(this@HomeFragment,
                arrayOf(getString(R.string.follow), getString(R.string.recommend)))
                .attach(tabLayout, viewPager, 1)
        }
    }

    inner class HomeAdapter(fragment: Fragment, tabNames: Array<String>): FragmentStateAdapter(fragment, tabNames) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> {
                    val shorts = User(context).artistId.getShorts()
                    if (shorts.isNotEmpty()) return ShortFullscreenFragment(shorts) else EmptyFragment()
                }
                1 -> return ShortFullscreenFragment(Short.repository.values.shuffled().subList(0, 50))
            }
            return ShortFullscreenFragment(Short.repository.values.shuffled().subList(0, 50))
        }
    }
}