package com.zitherharp.music.shorts.ui.artist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.zitherharp.music.core.Language
import com.zitherharp.music.model.Artist
import com.zitherharp.music.model.Audio
import com.zitherharp.music.model.Short
import com.zitherharp.music.shorts.databinding.ArtistDetailActivityBinding
import com.zitherharp.music.shorts.extension.Extension.onChineseName
import com.zitherharp.music.shorts.extension.Extension.onDescription
import com.zitherharp.music.shorts.extension.Extension.onFavourite
import com.zitherharp.music.shorts.extension.Extension.onImage
import com.zitherharp.music.shorts.model.User
import com.zitherharp.music.shorts.ui.audio.AudioListFragment
import com.zitherharp.music.shorts.ui.shorts.ShortGridFragment
import com.zitherharp.music.ui.adapter.FragmentStateAdapter
import com.zitherharp.music.ui.fragment.EmptyFragment

class ArtistDetailActivity: AppCompatActivity() {
    private val binding: ArtistDetailActivityBinding by lazy { ArtistDetailActivityBinding.inflate(layoutInflater) }
    private lateinit var artist: Artist
    private lateinit var audios: List<Audio>
    private lateinit var shorts: List<Short>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        artist = Artist.repository[intent.getStringExtra(ArtistDetailActivity::class.java.name)]!!
        with(binding) {
            setContentView(root)
            audios = artist.getAudios()
            shorts = artist.getShorts()
            artistVietnameseName.text = artist.getName(Language.VIETNAMESE)
            artistChineseName.onChineseName(artist)
            artistImage.onImage(artist)
            artistDescription.onDescription(artist)
            favouriteArtist.onFavourite(User.ARTIST_ID, artist.id)
            FragmentStateAdapter(this@ArtistDetailActivity,
                HashMap<String, Fragment>().apply {
                    put("${getString(com.zitherharp.music.R.string.audio)} ${audios.size}", AudioListFragment(audios))
                    put("${getString(com.zitherharp.music.R.string.shorts)} ${shorts.size}", ShortGridFragment(shorts))
                })
                .attach(artistTabLayout, artistViewPager, 1)
        }
    }
}