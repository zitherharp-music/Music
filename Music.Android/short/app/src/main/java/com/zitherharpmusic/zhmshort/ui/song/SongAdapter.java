package com.zitherharpmusic.zhmshort.ui.song;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.zitherharpmusic.zhmshort.R;
import com.zitherharpmusic.zhmshort.model.MusicUtils;
import com.zitherharpmusic.zhmshort.model.Song;
import com.zitherharpmusic.zhmshort.ui.empty.EmptyFragment;
import com.zitherharpmusic.zhmshort.model.Video;
import com.zitherharpmusic.zhmshort.ui.video.VideoGridFragment;

import java.util.List;

public class SongAdapter extends FragmentStateAdapter {
    private final FragmentActivity fragmentActivity;
    private final List<Song> songs;
    private final List<Video> videos;

    public SongAdapter(@NonNull FragmentActivity fragmentActivity, Song song) {
        super(fragmentActivity);
        this.fragmentActivity = fragmentActivity;
        this.songs = MusicUtils.findRecommendSongs(song);
        this.videos = song.getVideos();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                if (songs.size() > 0) {
                    return SongListFragment.newInstance(songs);
                }
                break;
            case 1:
                if (videos.size() > 0) {
                    return VideoGridFragment.newInstance(videos);
                }
                break;
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
                    tab.setText(fragmentActivity.getString(R.string.recommend) + " " + songs.size());
                    break;
                case 1:
                    tab.setText(fragmentActivity.getString(R.string.use) + " " + videos.size());
                    break;
            }
        }).attach();
        tabLayout.bringToFront();
        tabLayout.getTabAt(1).select();
    }
}
