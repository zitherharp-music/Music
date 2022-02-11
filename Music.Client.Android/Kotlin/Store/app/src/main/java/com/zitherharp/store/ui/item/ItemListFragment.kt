package com.zitherharp.store.ui.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zitherharp.store.R
import com.zitherharp.store.databinding.FragmentItemListBinding
import com.zitherharp.store.model.Item

class ItemListFragment(private val items: List<Item>) : Fragment() {
    private lateinit var binding: FragmentItemListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        return FragmentItemListBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            itemList.adapter = ItemListAdapter(view.context, items)
            if (items.isNotEmpty()) {
                status.text = ""
            } else {
                status.text = getString(R.string.empty)
            }
        }
    }
}