package com.zitherharp.store.ui.application

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.zitherharp.store.BuildConfig
import com.zitherharp.store.Repository
import com.zitherharp.store.databinding.FragmentApplicationBinding
import com.zitherharp.store.ui.item.ItemListFragment

class ApplicationFragment : Fragment() {
    private lateinit var binding: FragmentApplicationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentApplicationBinding.inflate(layoutInflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with (binding) {
            ApplicationAdapter(this@ApplicationFragment,
                arrayOf("Khám phá", "Đã cài đặt")).attach(tabLayout, viewPager)
        }
    }

    inner class ApplicationAdapter(
        fragment: Fragment, private val tabNames: Array<String>): FragmentStateAdapter(fragment) {
        fun attach(tabLayout: TabLayout, viewPager: ViewPager2) {
            viewPager.adapter = this
            TabLayoutMediator(tabLayout, viewPager) {
                    tab: TabLayout.Tab, position: Int -> tab.text = tabNames[position]
            }.attach()
            tabLayout.bringToFront()
        }

        override fun createFragment(position: Int): Fragment {
            val applications = Repository.itemMap.values.filter { it.id != BuildConfig.APPLICATION_ID }
            when (position) {
                0 -> return ItemListFragment(applications.filter { !it.isInstalled })
                1 -> return ItemListFragment(applications.filter { it.isInstalled })
            }
            return Fragment()
        }

        override fun getItemCount() = tabNames.size
    }
}