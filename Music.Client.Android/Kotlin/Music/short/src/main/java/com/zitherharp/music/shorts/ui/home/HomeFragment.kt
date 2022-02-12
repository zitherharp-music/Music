package com.zitherharp.music.shorts.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.databinding.FragmentHomeBinding
import com.zitherharp.music.shorts.ui.shorts.ShortFullscreenFragment
import com.zitherharp.music.ui.adapter.FragmentStateAdapter

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
                arrayOf("Theo dõi", "Đề xuất")).attach(tabLayout, viewPager, 1)
        }
    }

    inner class HomeAdapter(fragment: Fragment,
                            tabNames: Array<String>): FragmentStateAdapter(fragment, tabNames) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return ShortFullscreenFragment(Short.repository.values.drop(120))
                1 -> return ShortFullscreenFragment(Short.repository.values.shuffled().subList(0, 50))
            }
            TODO("Index $position out of bounds for ${tabNames.size}")
        }
    }
}