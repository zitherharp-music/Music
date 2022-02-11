package com.zitherharp.music.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

open class FragmentStateAdapter: FragmentStateAdapter {
    private var tabNames: Array<String>

    constructor(fragment: Fragment, tabNames: Array<String>): super(fragment) {
        this.tabNames = tabNames
    }

    constructor(fragmentActivity: FragmentActivity, tabNames: Array<String>): super(fragmentActivity) {
        this.tabNames = tabNames
    }

    override fun getItemCount() = tabNames.size

    override fun createFragment(position: Int): Fragment {
        TODO("Not yet implemented")
    }

    fun attach(tabLayout: TabLayout, viewPager: ViewPager2, tabSelectedIndex: Int = 0) {
        tabLayout.apply {
            viewPager.adapter = this@FragmentStateAdapter
            TabLayoutMediator(this, viewPager) {
                    tab: TabLayout.Tab, position: Int -> tab.text = tabNames[position]
            }.attach()
            getTabAt(tabSelectedIndex)?.select()
        }.bringToFront()
    }
}