package com.zitherharp.music.shorts.ui.user

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Artist.Companion.getArtists
import com.zitherharp.music.model.Audio
import com.zitherharp.music.model.Audio.Companion.getAudios
import com.zitherharp.music.model.Short
import com.zitherharp.music.model.Short.Companion.getShorts
import com.zitherharp.music.R
import com.zitherharp.music.shorts.databinding.UserDetailFragmentBinding
import com.zitherharp.music.shorts.ui.artist.ArtistListFragment
import com.zitherharp.music.shorts.ui.audio.AudioListFragment
import com.zitherharp.music.shorts.ui.shorts.ShortGridFragment
import com.zitherharp.music.ui.adapter.FragmentStateAdapter

class UserDetailFragment: Fragment() {
    private lateinit var binding: UserDetailFragmentBinding
    private lateinit var shorts: List<Short>
    private lateinit var audios: List<Audio>
    private lateinit var artists: List<Artist>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        UserDetailFragmentBinding.inflate(inflater, container, false).apply {
            binding = this
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            User(view.context).run {
                userId.text = String.format("ID: $id")
                userName.text = name
                shorts = shortId.getShorts()
                audios = audioId.getAudios()
                artists = artistId.getArtists()
                with (userName) {
                    setOnClickListener {
                        val editText = EditText(context).apply {
                            hint = name
                        }
                        AlertDialog.Builder(context).apply {
                            setView(editText)
                            setMessage("Đổi tên người dùng")
                            setNegativeButton(getString(R.string.cancel)) { dlg, _ -> dlg.cancel() }
                            setPositiveButton(getString(R.string.save)) { _, _ ->
                                run {
                                    val newName = editText.text.toString()
                                    if (newName.isNotBlank()) {
                                        rename(newName)
                                        userName.text = newName
                                        Toast.makeText(context, "Đổi tên thành công", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(context, "Tên không hợp lệ", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }.show()
                    }
                }
            }
            UserDetailAdapter(this@UserDetailFragment,
                arrayOf("${getString(R.string.audio)} ${audios.size}",
                    "${getString(R.string.shorts)} ${shorts.size}",
                    "${getString(R.string.artist)} ${artists.size}"))
                .attach(userTabLayout, userViewPager, 1)
        }
    }

    inner class UserDetailAdapter(fragment: Fragment, tabNames: Array<String>): FragmentStateAdapter(fragment, tabNames) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> return AudioListFragment(audios)
                1 -> return ShortGridFragment(shorts)
                2 -> return ArtistListFragment(artists)
            }
            TODO("Index $position out of bounds for ${tabNames.size}")
        }
    }
}