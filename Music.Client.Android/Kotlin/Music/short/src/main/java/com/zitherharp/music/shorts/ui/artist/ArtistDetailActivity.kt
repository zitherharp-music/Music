package com.zitherharp.music.shorts.ui.artist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.zitherharp.music.Extension.setImageUrl
import com.zitherharp.music.Extension.showImageDialog
import com.zitherharp.music.core.Language
import com.zitherharp.music.core.QQMusic
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Audio
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.R
import com.zitherharp.music.shorts.databinding.ArtistDetailActivityBinding
import com.zitherharp.music.shorts.ui.audio.AudioListFragment
import com.zitherharp.music.shorts.ui.shorts.ShortGridFragment
import com.zitherharp.music.shorts.ui.user.User
import com.zitherharp.music.ui.adapter.FragmentStateAdapter
import com.zitherharp.music.ui.fragment.EmptyFragment

class ArtistDetailActivity: AppCompatActivity() {
    private val binding: ArtistDetailActivityBinding by lazy { ArtistDetailActivityBinding.inflate(layoutInflater) }
    private val user: User by lazy { User(this) }
    private lateinit var artist: Artist
    private lateinit var audios: List<Audio>
    private lateinit var shorts: List<Short>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        artist = Artist.repository[intent.getStringExtra(ArtistDetailActivity::class.java.simpleName)]!!
        with(binding) {
            setContentView(root)
            audios = artist.getAudios()
            shorts = artist.getShorts()
            with(artistImage) {
                setImageUrl(artist.getImageUrl(QQMusic.Image.MEDIUM))
                setOnClickListener {
                    it.showImageDialog(artist.getImageUrl(QQMusic.Image.LARGE))
                }
            }
            artistChineseName.text = artist.getName(Language.CHINESE)
            artistVietnameseName.text = artist.getName(Language.VIETNAMESE)
            with(artistDescription) {
                setOnClickListener { _ ->
                    AlertDialog.Builder(context).apply {
                        setTitle(artist.getName(Language.VIETNAMESE))
                        setMessage(artist.getDescription(Language.VIETNAMESE))
                        setPositiveButton(getString(com.zitherharp.music.R.string.close)) { dlg, _ -> dlg.dismiss() }
                    }.show()
                }
            }
            with(favouriteArtist) {
                if (!user.contains(User.ARTIST_ID, artist.id)) {
                    setImageResource(R.drawable.ic_short_favorite_border_24)
                    setOnClickListener {
                        user.add(User.ARTIST_ID, artist.id)
                        setImageResource(R.drawable.ic_short_favorite_24)
                        Toast.makeText(context, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    setImageResource(R.drawable.ic_short_favorite_24)
                    setOnClickListener {
                        user.add(User.ARTIST_ID, artist.id)
                        AlertDialog.Builder(context).apply {
                            setMessage("Xoá nghệ sĩ này ra khỏi danh sách yêu thích?")
                            setNegativeButton("Huỷ") { dlg, _ -> dlg.dismiss() }
                            setPositiveButton("OK") { _, _ ->
                                user.remove(User.ARTIST_ID, artist.id)
                                setImageResource(R.drawable.ic_short_favorite_border_24)
                                Toast.makeText(context, "Đã xoá thành công", Toast.LENGTH_SHORT).show()
                            }
                        }.show()
                    }
                }
            }
            ArtistDetailAdapter(this@ArtistDetailActivity,
                arrayOf("${getString(com.zitherharp.music.R.string.audio)} ${audios.size}",
                    "${getString(com.zitherharp.music.R.string.shorts)} ${shorts.size}"))
                .attach(artistTabLayout, artistViewPager, 1)
        }
    }

    inner class ArtistDetailAdapter(fragmentActivity: FragmentActivity,
                                    tabNames: Array<String>): FragmentStateAdapter(fragmentActivity, tabNames) {
        override fun createFragment(position: Int): Fragment {
            when (position) {
                0 -> if (audios.isNotEmpty()) return AudioListFragment(audios) else EmptyFragment()
                1 -> if (shorts.isNotEmpty()) return ShortGridFragment(shorts) else EmptyFragment()
            }
            return EmptyFragment()
        }
    }
}