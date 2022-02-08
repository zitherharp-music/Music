package com.zitherharp.music.shorts.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zitherharp.music.shorts.databinding.FragmentHomeBinding
import com.zitherharp.music.shorts.ui.shorts.ShortFullscreenContentFragment

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with (binding) {
            HomeAdapter(this@HomeFragment,
                arrayOf("Theo dõi", "Đề xuất")).attach(tabLayout, viewPager)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    inner class HomeAdapter(fragment: Fragment,
                            private val tabsName: Array<String>): FragmentStateAdapter(fragment) {
        fun attach(tabLayout: TabLayout, viewPager: ViewPager2) {
            tabLayout.apply {
                viewPager.adapter = this@HomeAdapter
                TabLayoutMediator(this, viewPager) {
                        tab: TabLayout.Tab, position: Int -> tab.text = tabsName[position]
                }.attach()
            }.bringToFront()
        }

        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return ShortFullscreenContentFragment()
                1 -> return ShortFullscreenContentFragment()
            }
            return Fragment()
        }

        override fun getItemCount() = tabsName.size
    }
}