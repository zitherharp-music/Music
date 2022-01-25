package com.zitherharpmusic.zhmshort.ui.user;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.Artist;
import com.zitherharpmusic.zhmshort.model.User;
import com.zitherharpmusic.zhmshort.ui.artist.ArtistListFragment;
import com.zitherharpmusic.zhmshort.ui.empty.EmptyFragment;
import com.zitherharpmusic.zhmshort.model.Song;
import com.zitherharpmusic.zhmshort.ui.song.SongListFragment;
import com.zitherharpmusic.zhmshort.model.Video;
import com.zitherharpmusic.zhmshort.ui.video.VideoGridFragment;

import java.util.List;

public class UserAdapter extends FragmentStateAdapter {
    private final Fragment fragment;
    private final List<Song> songs;
    private final List<Video> videos;
    private final List<Artist> artists;

    public UserAdapter(@NonNull Fragment fragment, User user) {
        super(fragment);
        this.fragment = fragment;
        songs = user.getSongs();
        videos = user.getVideos();
        artists = user.getArtists();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                if (songs.size() > 0) {
                    return SongListFragment.newInstance(songs);
                } else {
                    return EmptyFragment.newInstance(fragment.getString(R.string.empty));
                }
            case 1:
                if (videos.size() > 0) {
                    return VideoGridFragment.newInstance(videos);
                } else {
                    return EmptyFragment.newInstance(fragment.getString(R.string.empty));
                }
            case 2:
                if (artists.size() > 0) {
                    return ArtistListFragment.newInstance(artists);
                } else {
                    return EmptyFragment.newInstance(fragment.getString(R.string.empty));
                }
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void attach(TabLayout tabLayout, ViewPager2 viewPager) {
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(fragment.getString(R.string.audios) + " " + songs.size());
                    break;
                case 1:
                    tab.setText(fragment.getString(R.string.shorts) + " " + videos.size());
                    break;
                case 2:
                    tab.setText(fragment.getString(R.string.artists) + " " + artists.size());
                    break;
            }
        }).attach();
        viewPager.setSelected(true);
        tabLayout.bringToFront();
        tabLayout.getTabAt(1).select();
        viewPager.setSelected(true);
    }
}
