package com.zitherharp.store.ui.empty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zitherharp.store.databinding.FragmentEmptyBinding

class EmptyFragment(private val text: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        return FragmentEmptyBinding.inflate(inflater, container, false).apply {
            status.text = text
        }.root
    }
}