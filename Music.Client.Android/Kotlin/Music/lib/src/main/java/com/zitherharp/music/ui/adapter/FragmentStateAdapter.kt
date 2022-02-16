package com.zitherharp.music.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class FragmentStateAdapter: FragmentStateAdapter {
    private var context: Context
    private var tabNames: List<String>
    private var fragments: List<Fragment>

    constructor(fragment: Fragment,
                fragmentStates: MutableMap<String, Fragment>): super(fragment) {
        this.context = fragment.requireContext()
        this.tabNames = fragmentStates.keys.toList()
        this.fragments = fragmentStates.values.toList()
    }

    constructor(fragmentActivity: FragmentActivity,
                fragmentStates: MutableMap<String, Fragment>): super(fragmentActivity) {
        this.context = fragmentActivity.baseContext
        this.tabNames = fragmentStates.keys.toList()
        this.fragments = fragmentStates.values.toList()
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    override fun getItemCount() = tabNames.size

    override fun createFragment(position: Int) = fragments[position]

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