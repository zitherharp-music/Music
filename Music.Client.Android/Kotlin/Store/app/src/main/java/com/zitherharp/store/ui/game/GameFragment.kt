package com.zitherharp.store.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zitherharp.store.databinding.FragmentGameBinding

class GameFragment : Fragment() {
    private val binding: FragmentGameBinding by lazy { FragmentGameBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?) = binding.root
}