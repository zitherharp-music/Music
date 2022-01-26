package com.zitherharpmusic.zhmshort.ui.artist;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.Artist;
import com.zitherharpmusic.zhmshort.ui.empty.EmptyFragment;
import com.zitherharpmusic.zhmshort.model.Song;
import com.zitherharpmusic.zhmshort.ui.song.SongListFragment;
import com.zitherharpmusic.zhmshort.model.Video;
import com.zitherharpmusic.zhmshort.ui.video.VideoGridFragment;

import java.util.List;

public class ArtistAdapter extends FragmentStateAdapter {
    private final FragmentActivity fragmentActivity;
    private final List<Song> songs;
    private final List<Video> videos;

    public ArtistAdapter(@NonNull FragmentActivity fragmentActivity, Artist artist) {
        super(fragmentActivity);
        this.fragmentActivity = fragmentActivity;
        songs = artist.getSongs();
        videos = artist.getVideos();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: if (songs.size() > 0) return SongListFragment.newInstance(songs); break;
            case 1: if (videos.size() > 0) return VideoGridFragment.newInstance(videos); break;
        }
        return EmptyFragment.newInstance(fragmentActivity.getString(R.string.empty));
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public void attach(TabLayout tabLayout, ViewPager2 viewPager) {
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(fragmentActivity.getString(R.string.audios) + " " + songs.size());
                    break;
                case 1:
                    tab.setText(fragmentActivity.getString(R.string.shorts) + " " + videos.size());
            }
        }).attach();
        tabLayout.bringToFront();
        tabLayout.getTabAt(1).select();
    }
}
