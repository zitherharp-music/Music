package com.zitherharp.music.photo.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.zitherharp.music.photo.R
import com.zitherharp.music.photo.databinding.UserFragmentBinding

class UserFragment: Fragment() {
    private val binding: UserFragmentBinding by lazy { UserFragmentBinding.inflate(layoutInflater) }
    private val user: User by lazy { User(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            val context = activity as AppCompatActivity
            userName.text = getString(com.zitherharp.music.R.string.music_app_name)
            userId.text = String.format("@zitherharpmusic")
        }
    }

}